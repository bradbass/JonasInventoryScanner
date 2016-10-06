package com.jonasconstruction.inventoryscanner.jonasinventoryscanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Brad on 2/14/2015.
 *
 */
public class ReportActivity extends Activity {

    //DatabaseHandler db = new DatabaseHandler(this);
    private ListView _listView;
    private ListView _listViewCharge;
    private ListView _listViewUpload;
    private ListView _listViewTransfer;
    private ListView _listViewReceive;
    static Boolean _deleted = false;
    static String _tableName;
    static long _index;
    static String _record;
    static List<String> _recordData;
    static String _recordIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        /*TabHost th = (TabHost) findViewById(R.id.tabHost);
        LocalActivityManager lam = new LocalActivityManager(this, false);
        lam.dispatchCreate(savedInstanceState);
        th.setup(lam);

        TabHost.TabSpec chargeTab = th.newTabSpec("chargeTab");
        TabHost.TabSpec uploadTab = th.newTabSpec("uploadTab");
        TabHost.TabSpec transferTab = th.newTabSpec("transferTab");
        TabHost.TabSpec receiveTab = th.newTabSpec("receiveTab");

        chargeTab.setIndicator("CHARGE");
        chargeTab.setContent(new Intent(this, ReportActivityCharge.class));

        uploadTab.setIndicator("UPLOAD");
        uploadTab.setContent(new Intent(this, ReportActivityUpload.class));

        transferTab.setIndicator("TRANSFER");
        transferTab.setContent(new Intent(this, ReportActivityTransfer.class));

        receiveTab.setIndicator("RECEIVE");
        receiveTab.setContent(new Intent(this, ReportActivityReceive.class));

        th.addTab(chargeTab);
        th.addTab(uploadTab);
        th.addTab(transferTab);
        th.addTab(receiveTab);*/

        _listView = (ListView) findViewById(R.id.listView);
        _listView.isLongClickable();
        _listView.setItemsCanFocus(true);
        _listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        /*_listViewCharge = (ListView) findViewById(R.id.listViewCharge);
        _listViewCharge.isLongClickable();
        _listViewCharge.setItemsCanFocus(true);
        _listViewCharge.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);*/

        /*_listViewUpload = (ListView) findViewById(R.id.listViewUpload);
        _listViewUpload.isLongClickable();
        _listViewUpload.setItemsCanFocus(true);
        _listViewUpload.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);*/

        /*_listViewTransfer = (ListView) findViewById(R.id.listViewTrans);
        _listViewTransfer.isLongClickable();
        _listViewTransfer.setItemsCanFocus(true);
        _listViewTransfer.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);*/

        /*_listViewReceive = (ListView) findViewById(R.id.listViewReceive);
        _listViewReceive.isLongClickable();
        _listViewReceive.setItemsCanFocus(true);
        _listViewReceive.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);*/

        //populateListView();

        /*
        _listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                _record = _listView.getSelectedItem().toString();
                _index = _listView.getSelectedItemPosition();
                getTableName(position);
                popupAlertDialog();
                return false;
            }
        });//*/

        _listView.setOnItemClickListener((parent, view, position, id) -> {
            _record = (String) _listView.getItemAtPosition(position);
            _index = _listView.getItemIdAtPosition(position);

            if (!String.valueOf(_record.charAt(0)).equals("*")) {
                //getTableName((int) _index);
                popupAlertDialog();
                //populateListView();
            }
        });

        /*_listViewCharge.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _record = (String) _listViewCharge.getItemAtPosition(position);
                _index = _listViewCharge.getItemIdAtPosition(position);
                getTableName((int) _index);
                //popupAlertDialog();
                populateListViewCharge();
            }
        });*/

        /*_listViewUpload.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _record = (String) _listViewUpload.getItemAtPosition(position);
                _index = _listViewUpload.getItemIdAtPosition(position);
                getTableName((int) _index);
                populateListViewUpload();
            }
        });*/

        /*_listViewTransfer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _record = (String) _listViewTransfer.getItemAtPosition(position);
                _index = _listViewTransfer.getItemIdAtPosition(position);
                getTableName((int) _index);
                populateListViewTransfer();
            }
        });*/

        /*_listViewReceive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _record = (String) _listViewReceive.getItemAtPosition(position);
                _index = _listViewReceive.getItemIdAtPosition(position);
                getTableName((int) _index);
                populateListViewReceive();
            }
        });*/

        //populateListViewCharge();
        //populateListViewUpload();
        //populateListViewTransfer();
        //populateListViewReceive();
        populateListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.charge_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // go back to home screen
        String toolbarItem = item.toString();
        if (toolbarItem.equals("HOME")) {
            Intent hi = new Intent(getApplicationContext(), HomeActivity.class);
            hi.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(hi);
            this.finish();
        } else if (toolbarItem.equals("SEND")) {
            send();
            //this.finish();
        }
        return true;
    }

    private void send() {
        //send all
        ChargeActivity ca = new ChargeActivity();
        UploadActivity ua = new UploadActivity();
        TransferActivity ta = new TransferActivity();
        ReceivePO rpo = new ReceivePO();
        try {
            ca.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ua.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ta.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rpo.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getTableName(Integer position) {
        //use index to read reportData table and get tableName
        DatabaseHandler db = new DatabaseHandler(this);
        db.getTableName(position);
    }

    void setTableName(String tableName) {
        _tableName = tableName;
    }

    void setRecordData(List<String> recordData) {
        _recordData = recordData;
    }

    void setRecordIndex(String recordIndex) {
        _recordIndex = recordIndex;
    }

    void populateListView() {
        DatabaseHandler db = new DatabaseHandler(this);
        db.populateReport();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, _recordData);
        _listView.setAdapter(arrayAdapter);
    }

    /*
    void populateListViewCharge() {
        DatabaseHandler db = new DatabaseHandler(this);
        db.populateReportCharge();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, _recordData);
        _listViewCharge.setAdapter(arrayAdapter);
    }

    void populateListViewUpload() {
        DatabaseHandler db = new DatabaseHandler(this);
        db.populateReportUpload();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, _recordData);
        _listViewUpload.setAdapter(arrayAdapter);
    }

    void populateListViewTransfer() {
        DatabaseHandler db = new DatabaseHandler(this);
        db.populateReportTransfer();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, _recordData);
        _listViewTransfer.setAdapter(arrayAdapter);
    }

    void populateListViewReceive() {
        DatabaseHandler db = new DatabaseHandler(this);
        db.populateReportReceive();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, _recordData);
        _listViewReceive.setAdapter(arrayAdapter);
    }
    //*/

    //add alert dialog with 2 options - Edit and Delete
    private void popupAlertDialog() {
        //final DatabaseHandler dbh = new DatabaseHandler(getApplicationContext());
        AlertDialog.Builder aDB = new AlertDialog.Builder(this);
        aDB.setTitle("Edit/Delete Current Record?");
        aDB.setMessage("Would you like to Edit or Delete the current record?");
        aDB.setNegativeButton("EDIT", (dialog, which) -> {
            //launch corresponding activity and populate fields
            editRecord();
        });
        aDB.setNeutralButton("DELETE", (dialog, which) -> {
            //remove the individual record
            getTableName((int) _index);
            deleteOne(_tableName,  Integer.valueOf(_recordIndex));
            //makeText(getApplicationContext(), "This record has been deleted!", LENGTH_LONG).show();
        });
        aDB.setPositiveButton("CANCEL", (dialog, which) -> {
            //cancel - do nothing
        });
        aDB.show();
    }

    private void editRecord() {
        //DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        getTableName((int) _index);
        switch (_tableName) {
            case "chrgData":
                //launch intent and populate the correct record
                ChargeActivity cAct = new ChargeActivity();
                cAct.set_fromReport(true);
                cAct.set_index(Integer.parseInt(_recordIndex));
                Intent ca = new Intent(getApplicationContext(), ChargeActivity.class);
                ca.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ca);
                this.finish();
                break;
            case "uploadData":
                UploadActivity uAct = new UploadActivity();
                uAct.set_fromReport(true);
                uAct.set_index(Integer.parseInt(_recordIndex));
                Intent ua = new Intent(getApplicationContext(), UploadActivity.class);
                ua.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ua);
                this.finish();
                break;
            case "transferData":
                TransferActivity tAct = new TransferActivity();
                tAct.set_fromReport(true);
                tAct.set_index(Integer.parseInt(_recordIndex));
                Intent ta = new Intent(getApplicationContext(), TransferActivity.class);
                ta.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ta);
                this.finish();
                break;
            case "receiveData":
                ReceivePO rAct = new ReceivePO();
                rAct.set_fromReport(true);
                rAct.set_index(Integer.parseInt(_recordIndex));
                Intent ra = new Intent(getApplicationContext(), ReceivePO.class);
                ra.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ra);
                this.finish();
                break;
            default:
                break;
        }
    }

    private void deleteOne(final String tableName, final Integer index) {
        //
        final DatabaseHandler dbh = new DatabaseHandler(getApplicationContext());
        AlertDialog.Builder aDB = new AlertDialog.Builder(this);
        aDB.setTitle("Delete Current Record?");
        aDB.setMessage("Are you sure you want to delete the current record?");
        aDB.setPositiveButton("YES", (dialog, which) -> {
            // When user clicks OK, the db is purged and user is sent back to main activity.
            try {
                dbh.deleteOne(tableName, index+1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //dbh.deleteOne("reportData", (int) _index);
            //clearVars();
            _deleted = true;
            populateListView();
            //makeText(getApplicationContext(), "This record has been deleted!", LENGTH_LONG).show();
        });
        aDB.setNegativeButton("NO", (dialog, which) -> {
            // If user clicks NO, dialog is closed.
            dialog.cancel();
        });
        aDB.show();
    }
}