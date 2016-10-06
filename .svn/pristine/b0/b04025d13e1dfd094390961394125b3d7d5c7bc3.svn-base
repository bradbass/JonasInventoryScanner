package com.jonasconstruction.inventoryscanner.jonasinventoryscanner;

import android.app.Activity;
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
 * Created by braba on 5/21/2015.
 */
public class LogActivity extends Activity {

    private static ListView _listViewLog;
    static List<String> _recordData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        _listViewLog = (ListView) findViewById(R.id.listViewLog);
        _listViewLog.isLongClickable();
        _listViewLog.setItemsCanFocus(true);
        _listViewLog.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        _listViewLog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO - drill down on log item
            }
        });

        populateLog();
    }

    private void populateLog() {
        //
        DatabaseHandler db = new DatabaseHandler(this);
        db.populateLogView();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, _recordData);
        _listViewLog.setAdapter(arrayAdapter);
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