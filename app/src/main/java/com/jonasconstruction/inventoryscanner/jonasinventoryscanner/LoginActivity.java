package com.jonasconstruction.inventoryscanner.jonasinventoryscanner;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created with IntelliJ IDEA.
 * User: braba
 * Date: 24/09/13
 * Time: 6:18 PM
 */
@SuppressWarnings("FieldCanBeLocal")
public class LoginActivity extends Activity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    public static boolean _isValidLogin;
    public static boolean _isAdminLogin = false;

    //*
    private final String URL = "http://websvc.jonasportal.com/jonasAPI/japi.asmx?WSDL";
    private final String NAMESPACE = "jonas.jonasportal.com/";
    private final String METHOD_NAME = "Login";
    private final String SOAP_ACTION = "jonas.jonasportal.com/Login";
    //*/

    private final Crypter crypter = new Crypter();

    static EditText _loginUsername;
    static EditText _loginPassword;
    static EditText _securityToken;
    static CheckBox _rememberMe;
    static TextView _secTokenHeading;

    static String _uname;
    static String _pword;
    static String _secToken;
    //static String _currentDateTime;
    static String _currentDate;
    static String _currentTime;
    static String _resp;
    static String _message;

    private DatabaseHandler dbh;

    //JSONParser jp = new JSONParser();
    //JSONObject jo;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(" User Login");

        dbh = new DatabaseHandler(this);

        final Button loginBtn = (Button) findViewById(R.id.btnLogin);
        _loginUsername = (EditText) findViewById(R.id.loginUsername);
        _loginPassword = (EditText) findViewById(R.id.loginPassword);
        _securityToken = (EditText) findViewById(R.id.loginSecurityToken);
        final TextView resetLogin = (TextView) findViewById(R.id.forgot_password);
        _secTokenHeading = (TextView) findViewById(R.id.securityTokenHeading);
        _rememberMe = (CheckBox) findViewById(R.id.rememberMe);

        dbh.populateLogin();
        populateFields();
        //String secToken = _securityToken.getText().toString();
        if (!_securityToken.getText().toString().equals("")) {
        //if (secToken != "") {
            _securityToken.setEnabled(false);
        }

        loginBtn.setOnClickListener(view -> {
            _isAdminLogin = false;
            if (_loginUsername.getText().toString().equals("gjadmin")) {
                if (_loginPassword.getText().toString().equals("gjadmin")) {
                    adminLogin();
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect Password", LENGTH_LONG).show();
                }
            } else {
                rememberMe();
                new LoginOperation().execute();
            }
        });

        resetLogin.setOnClickListener(v -> {
            _securityToken.setEnabled(true);
            //this is for testing only
            //Toast.makeText(getApplicationContext(), "You clicked it!", LENGTH_LONG).show();
        });

        //forgotPassword.setVisibility(View.GONE);
    }

    //*
    private void adminLogin() {
        _isAdminLogin = true;
        Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
        this.finish();
    }//*/

    private void populateFields() {

        try {
            _pword = crypter.decode(_pword);
            _rememberMe.setChecked(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        _securityToken.setText(_secToken);
        _loginUsername.setText(_uname);
        _loginPassword.setText(_pword);
    }

    private void rememberMe() {
        // if checkbox enabled, saveToDb
        if (_rememberMe.isChecked()) {
            _secToken = _securityToken.getText().toString();
            _uname = _loginUsername.getText().toString();
            _pword = _loginPassword.getText().toString();

            _pword = crypter.encode(_pword);

            dbh.saveToDb(_uname, _pword, _secToken, 6);
        }
    }

    /*
    @SuppressLint("SimpleDateFormat")
    static void setDateTime() {
        // add DateTime to filename
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date = new SimpleDateFormat(Resources.getSystem().getString(R.string.filename_simple_date_format));
        date.setTimeZone(TimeZone.getDefault());
        _currentDateTime = date.format(currentLocalTime);
    }//*/

    public void setUname(String uname) {
        _uname = uname;
    }

    public void setPword(String pword) {
        _pword = pword;
    }

    public void setSecToken(String secToken) {
        _secToken = secToken;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // go back to home screen
        this.finish();
        return true;
    }

    class LoginOperation extends AsyncTask<String, Void, String> {

        private ProgressDialog Dialog = new ProgressDialog(LoginActivity.this);
        public SoapPrimitive response;

        @Override
        protected String doInBackground(String... params) {
            loginAction();
            //return String.valueOf(JSONFunctions.readJSONFeed(URL));
            return null;
        }

        protected void onPreExecute() {
            Dialog.setMessage("Attempting login...");
            try {
                Dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected void onPostExecute(String resultGot) {

            //<editor-fold desc="Description">
            /* JSON Example2
            try {
                JSONObject jsonObject = new JSONObject(resultGot.substring(3));
                String vehicleType = jsonObject.getString("vehicleType");
                JSONArray approvedOperators = new JSONArray(jsonObject.getString("approvedOperators"));
                //JSONObject jsonResponseData = new JSONObject(jsonObject.getString("vehicleType"));
                //JSONObject jsonResponseMsg = new JSONObject(jsonObject.getString("approvedOperators"));
                _isValidLogin = jsonObject.getBoolean("isAuthenticated");
            } catch (Exception e) {
                e.printStackTrace();
            }//*/
            //</editor-fold>

            try {
                Dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (_isValidLogin) {
                // write log file
                _currentDate = Helper.setDate();
                _currentTime = Helper.setTime();
                dbh.saveToDb(_currentDate, _currentTime, _uname, "User "
                        + _uname
                        + " logged in.", true);
                _securityToken.setEnabled(false);
                //launch homeActivity, if valid login
                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(i);
            } else {
                dbh.saveToDb(_currentDate, _currentTime, _uname, "User "
                        + _uname + " failed to login. Message: "
                        + _message, true);
                Toast.makeText(LoginActivity.this, "The login attempt failed. Message: "
                        + _message, LENGTH_LONG).show();
            }
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        private void loginAction(){
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            _resp="";
            _isValidLogin = false;

            //*
            EditText _loginUsername = (EditText) findViewById(R.id.loginUsername);
            _uname = _loginUsername.getText().toString();
            EditText _loginPassword = (EditText) findViewById(R.id.loginPassword);
            String pword = _loginPassword.getText().toString();
            EditText _securityToken = (EditText) findViewById(R.id.loginSecurityToken);
            String securityToken = _securityToken.getText().toString();
            //*/
            //final String[] resp = {"INVALID"};

            //request.addProperty("uname", "braba");
            //request.addProperty("pword", "password");
            //request.addProperty("clientID", "qa-brad");
            //*
            //Pass value for accountCode variable of the web service
            PropertyInfo securityTokenProp = new PropertyInfo();
            securityTokenProp.setType(PropertyInfo.STRING_CLASS);
            securityTokenProp.setName("securityToken");
            securityTokenProp.setValue(securityToken);
            request.addProperty(securityTokenProp);

            //Pass value for userName variable of the web service
            PropertyInfo unameProp = new PropertyInfo();
            unameProp.setType(PropertyInfo.STRING_CLASS);//Define the type of the variable
            unameProp.setName("username");//Define the variable name in the web service method
            unameProp.setValue(_uname);//set value for userName variable
            request.addProperty(unameProp);//Pass properties to the variable

            //Pass value for Password variable of the web service
            PropertyInfo passwordProp = new PropertyInfo();
            passwordProp.setType(PropertyInfo.STRING_CLASS);
            passwordProp.setName("password");
            passwordProp.setValue(pword);
            request.addProperty(passwordProp);
            //*/

            //<editor-fold desc="Description">
            /* JSON Example2
            JSONParser jp = new JSONParser();

            //* JSON Example
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("accountCode", clientID));
            params.add(new BasicNameValuePair("username", _uname));
            params.add(new BasicNameValuePair("password", pword));
            //params.add(new BasicNameValuePair("jsonParams", ""));

            //String urlLogin = "http://constdev.jonasportal.com/websvc/japi.svc/Login";
            String urlLogin = "http://docs.blackberry.com/sampledata.json";
            String altURL = "https://bugzilla.mozilla.org/rest/bug/35";
            String method = "";
            JSONArray ja = JSONFunctions.getJSONfromURL(urlLogin);
            JSONArray ja2 = jp.getJSONFromUrl(altURL);
            jo = jp.makeHttpRequest(urlLogin, method, params);

            String s;

            try {
                //s = jo.getString("info");
                s = ja.toString();
                Log.d("Msg", jo.getString("info"));
                if (s.equals("true")) {
                    _isValidLogin = true;
                }
            }catch (JSONException je) {
                je.printStackTrace();
            }
            //*/
            //</editor-fold>

            //* Soap
            final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            final HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try{

                //debugging
                androidHttpTransport.debug = true;

                androidHttpTransport.call(SOAP_ACTION, envelope);
                response = (SoapPrimitive)envelope.getResponse();

                _resp = response.toString();
                _message = Helper.ParseJSONMessage(_resp, "message");

                //TextView result = (TextView) findViewById(R.id.tv_status);
                //result.setText(response.toString());
            }
            catch (XmlPullParserException exppe) {
                Log.e(TAG, exppe.getDetail().toString(), exppe);
                Log.e(TAG, "" + androidHttpTransport.requestDump);
                Log.e(TAG, "" + androidHttpTransport.responseDump);
            }
            catch(Exception e){
                e.printStackTrace();
            } finally {
                Log.e(TAG, "" + androidHttpTransport.requestDump);
                Log.e(TAG, "" + androidHttpTransport.responseDump);
            }

            //validate login
            //String valid = ParseJSON(_resp, "isAuthenticated");
            //_isValidLogin = valid.equals("true");
            //_isValidLogin = Boolean.parseBoolean(ParseJSON(_resp, "isAuthenticated"));
            try {
                _isValidLogin = Boolean.parseBoolean(Helper.ParseJSON(_resp, "isAuthenticated"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}