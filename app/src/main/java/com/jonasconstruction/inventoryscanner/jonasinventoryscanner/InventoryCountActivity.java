package com.jonasconstruction.inventoryscanner.jonasinventoryscanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

/**
 * @author Brad Bass
 * @version 1.202
 *
 * InventoryCountActivity allows user to continuously scan UPC codes.  The program creates a record
 * for each unique UPC scanned and increments on subsequent scans.  It then displays a list of all
 * UPCs scanned with the count.  You will be able to click on a UPC and either decrement the count,
 * remove the record completely or cancel.
 *
 * @see android.os
 * @see android.app
 * @see android.content
 * @see android.view
 * @see android.widget
 * @see java.util
 */
public class InventoryCountActivity extends Activity implements AdapterView.OnItemSelectedListener {
    //DatabaseHandler db = new DatabaseHandler(this);
    // Spinner element
    private Spinner spinnerWhse;
    private ListView _listView;
    private EditText _scanField;
    private static String _upc;
    private static Integer _qty;
    private static String _whse;
    /**
     * The Record data.
     */
    static List<String> _recordData;
    /**
     * The Index.
     */
    static long _index;
    /**
     * The Record.
     */
    static String _record;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_count);
        setTitle(" Inventory Count");

        DatabaseHandler _db = new DatabaseHandler(getApplicationContext());

        final ImageButton scanUpcBtn = (ImageButton) findViewById(R.id.scanUpcBtn);

        _listView = (ListView) findViewById(R.id.inventoryCountList);
        _scanField = (EditText) findViewById(R.id.upcField);

        // Spinner element
        spinnerWhse = (Spinner) findViewById(R.id.spinnerWhseIC);

        // Spinner click listener
        spinnerWhse.setOnItemSelectedListener(this);

        // Loading spinner data from database
        loadSpinnerDataWhse();

        populateListView();

        _db.populateDefaults(6);
        populateDefaults();

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

    private void loadSpinnerDataWhse() {
        // calls method to load spinner
        loadSpinnerData("1");
        // load the spinner
        //spinnerWhse.setAdapter(dataAdapter);
    }

    /**
     * Function to load the spinner data from SQLite database
     *
     * @param tableName selected table
     */
    private void loadSpinnerData(String tableName) {
        // load WHSE, COST ITEM and COST TYPE spinners from DB
        //String spinner = tableName;
        // database handler
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        // Spinner Drop down elements
        List<String> labels = db.getAllLabels(tableName);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //spinnerWhse.setAdapter(dataAdapter);
        spinnerWhse.setAdapter(dataAdapter);

        //return dataAdapter;
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

    /**
     * save and increment the count.
     */
    private void save() {
        DatabaseHandler _db = new DatabaseHandler(getApplicationContext());
        if(_upc == null || _upc.equals("")) {
            _upc = _scanField.getText().toString();
        }

        _qty = 0;

        _db.getInventoryCountRecord(_upc);

        try {
            _qty = _qty + 1;
        } catch (Exception e) {
            //e.printStackTrace();
            _qty = 1;
        }
        //_upc = _scanField.getText().toString();
        _whse = spinnerWhse.getSelectedItem().toString();

        _db.saveToDb(_whse, _upc, _qty);

        _scanField.setText(null);
        _whse = "";
        _upc = "";
    }

    /**
     * Initialise the static variable _label
     *
     * @param label		Name that user selected from the spinner
     */
    void setLabel(String label) {

    }

    /**
     *
     * @param whse  warehouse
     */
    public void setWHSE(String whse) {
        _whse = whse;
    }

    /**
     * Initialise the static variable _upc
     *
     * @param scanResult upc code returned from the scanner
     */
    void setUpc(String scanResult) {
        _upc = scanResult;
    }

    /**
     * Sets upc.
     *
     * @param upc upc code
     */
    public void setUPC(String upc) {
        _upc = upc;
    }

    /**
     * Sets qty.
     *
     * @param qty the qty
     */
    public void setQty(Integer qty) {
        _qty = qty;
    }

    /**
     * Sets record data.
     *
     * @param recordData the record data
     */
    void setRecordData(List<String> recordData) {
        _recordData = recordData;
    }

    /**
     *
     * @param valueWhse the selected warehouse
     */
    @SuppressWarnings("ConstantConditions")
    private void setSpinnerWhse(String valueWhse) {
        //ArrayAdapter spinAdapter = (ArrayAdapter) spinnerWhse.getAdapter();
        //int spinnerPos = spinAdapter.getPosition(valueWhse);
        //spinnerWhse.setSelection(spinnerPos);
        int index = 0;

        for (int i=0;i<spinnerWhse.getCount();i++) {
            if (spinnerWhse.getItemAtPosition(i).toString().equalsIgnoreCase(valueWhse)) {
                index = i;
                i = spinnerWhse.getCount();
            }
        }
        spinnerWhse.setSelection(index);
    }

    /**
     * Populate list view.
     */
    void populateListView() {
        // TODO - add ability to switch between multiple warehouses.  When selecting a new warehouse
        // we will need to repopulate the list with the data related to selected warehouse.
        DatabaseHandler db = new DatabaseHandler(this);
        db.populateInventoryCount();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, _recordData);
        _listView.setAdapter(arrayAdapter);
    }

    void populateDefaults() {
        setSpinnerWhse(_whse);
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

    /**
     * When user selects a name from the spinner.
     *
     * @param parent    AdapterView
     * @param view      View
     * @param position  selected item position
     * @param id        selected item id
     */
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // modify for new spinners
        // On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();

        setLabel(label);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
