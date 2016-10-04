package com.jonasconstruction.inventoryscanner.jonasinventoryscanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by braba on 7/23/2015.
 */
public class ErrorLogActivity extends Activity {
    private static ListView _listViewErrorLog;
    static List<String> _recordData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_log);

        _listViewErrorLog = (ListView) findViewById(R.id.listViewErrorLog);
        /*
        _listViewErrorLog.isLongClickable();
        _listViewErrorLog.setItemsCanFocus(true);
        _listViewErrorLog.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        _listViewErrorLog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        //*/

        populateLog();
    }

    private void populateLog() {
        //
        DatabaseHandler db = new DatabaseHandler(this);
        db.populateErrorLogView();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, _recordData);
        _listViewErrorLog.setAdapter(arrayAdapter);
    }

    void setRecordData(List<String> recordData) {
        _recordData = recordData;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu, menu);
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
        }
        return true;
    }
}