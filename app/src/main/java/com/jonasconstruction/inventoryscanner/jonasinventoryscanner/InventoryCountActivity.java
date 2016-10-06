package com.jonasconstruction.inventoryscanner.jonasinventoryscanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by braba on 11/24/2015.
 */
public class InventoryCountActivity extends Activity {
    //DatabaseHandler db = new DatabaseHandler(this);
    private ListView _listView;
    private EditText _scanField;
    private static String _upc;
    private static Integer _qty;
    static List<String> _recordData;
    static long _index;
    static String _record;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_count);
        setTitle(" Inventory Count");

        final ImageButton scanUpcBtn = (ImageButton) findViewById(R.id.scanUpcBtn);

        _listView = (ListView) findViewById(R.id.inventoryCountList);
        _scanField = (EditText) findViewById(R.id.upcField);

        populateListView();

        _scanField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 1) {
                    save();
                    populateListView();
                }
            }
        });

        scanUpcBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Scan.ACTION);
            startActivityForResult(intent, 2);
        });

        _listView.setOnItemClickListener((parent, view, position, id) -> {
            _record = (String) _listView.getItemAtPosition(position);
            _index = _listView.getItemIdAtPosition(position);

            //TODO - either decrement the count or delete the whole record so they have to recount the part
            //dialog - decrement by 1 or remove part and recount
            AlertDialog.Builder aDB = new AlertDialog.Builder(this);
            aDB.setTitle("Part Correction");
            aDB.setMessage("A missing dependency has been detected.  Click YES to install.");
            aDB.setPositiveButton("Decrement", (dialog, which) -> {
                //decrement part count
            });
            aDB.setNeutralButton("Remove", (dialog, which) -> {
                //remove part count
            });
            aDB.setNegativeButton("Cancel", (dialog, which) -> {
                //cancel
            });
            aDB.show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // go back to home screen
        String toolbarItem = item.toString();
        switch (toolbarItem) {
            case "HOME":
                endActivity();
                break;
        }
        return true;
    }

    /**
     * When scanner returns a result, we verify ok then send to the text box.
     *
     * @param resultCode    resultCode
     * @param intent        intent
     */
    @SuppressWarnings("ConstantConditions")
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            String scanResult = intent.getStringExtra(Scan.BARCODE);
            //String barcodeFormat = intent.getStringExtra(Scan.Pro.FORMAT);
            // Handle successful scan
            //EditText code =(EditText)findViewById(R.id.partUpcField);
            _scanField.setText(scanResult);
            setUpc(scanResult);
        } else if (resultCode == RESULT_CANCELED) {
            // Handle cancel
            Toast.makeText(getApplicationContext(), getString(R.string.toast_failed_to_scan_message), LENGTH_LONG).show();
        }
    }//

    private void save() {
        DatabaseHandler _db = new DatabaseHandler(getApplicationContext());
        if(_upc == null || _upc.equals("")) {
            _upc = _scanField.getText().toString();
        }

        _qty = 0;

        //TODO - check if upc exists, if it does, increment the qty
        _db.getInventoryCountRecord(_upc);

        try {
            _qty = _qty + 1;
        } catch (Exception e) {
            //e.printStackTrace();
            _qty = 1;
        }
        //_upc = _scanField.getText().toString();

        _db.saveToDb(_upc, _qty);

        _scanField.setText(null);
        _upc = "";
    }

    /**
     * Initialise the static variable _upc
     *
     * @param scanResult	upc code returned from the scanner
     */
    void setUpc(String scanResult) {
        _upc = scanResult;
    }

    /**
     *
     * @param upc       upc code
     */
    public void setUPC(String upc) {
        _upc = upc;
    }

    public void setQty(Integer qty) {
        _qty = qty;
    }

    void setRecordData(List<String> recordData) {
        _recordData = recordData;
    }

    void populateListView() {
        DatabaseHandler db = new DatabaseHandler(this);
        db.populateInventoryCount();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, _recordData);
        _listView.setAdapter(arrayAdapter);
    }

    /**
     * closes the Activity.
     */
    private void endActivity() {
        Intent hi = new Intent(getApplicationContext(), HomeActivity.class);
        hi.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(hi);
        this.finish();
    }

    /**
     * When called, will pop an AlertDialog asking user if they are sure they want
     * to exit the screen.  This is attached to UI back button, BACK button and EXIT
     * button.  If user selects YES, we purge the database and send them back to the
     * main activity.
     */
    @Override
    public void onBackPressed() {
        endActivity();
    }
}
