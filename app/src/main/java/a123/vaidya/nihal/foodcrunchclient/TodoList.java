package a123.vaidya.nihal.foodcrunchclient;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.hoang8f.widget.FButton;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TodoList extends AppCompatActivity {
    DbHelper dbHelper;
    ArrayAdapter<String> mAdapter;
    ListView lstTask;
    info.hoang8f.widget.FButton buttonclear;
    private FloatingActionButton fabSettings;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/restaurant_font.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        setContentView(R.layout.activity_todo_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar23);
        setSupportActionBar(toolbar);


        dbHelper = new DbHelper(this);
        buttonclear = (FButton)findViewById(R.id.btndeletelist);
        lstTask = (ListView)findViewById(R.id.lstTask);
        buttonclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog dialog = new AlertDialog.Builder(TodoList.this)
                        .setTitle("WANT TO DELETE ALL ITEMS IN LIST?")
                        .setCancelable(false)
                        .setIcon(R.drawable.ic_delete_forever_black_24dp)
                        .setMessage("THIS CANNOT BE UNDONE ")
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper.clearTask();
                                loadTaskList();
                                Toast.makeText(TodoList.this,"The list was cleared!",
                                        Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("CANCEL",null)
                        .create();
                dialog.show();
            }
        });
        loadTaskList();
    }

    private void loadTaskList() {
        ArrayList<String> taskList = dbHelper.getTaskList();
        if(mAdapter==null){
            mAdapter = new ArrayAdapter<String>(this,R.layout.row,R.id.task_title,taskList);
            lstTask.setAdapter(mAdapter);
        }
        else{
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todolistmenu,menu);

        //Change menu icon color
        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_task:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("ADD NEW FOOD")
                        .setCancelable(false)
                        .setIcon(R.drawable.ic_menu_black_24dp)
                        .setMessage("WHAT IS YOUR WISHLIST?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                dbHelper.insertNewTask(task);
                                loadTaskList();
                                Toast.makeText(TodoList.this,"The item    "+task+"    was added!!",
                                        Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .create();
                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteTask(final View view){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("ARE YOU SURE YOU WANT TO DELETE?")
                .setCancelable(false)
                .setIcon(R.drawable.ic_delete_forever_black_24dp)
                .setMessage("THIS CANNOT BE UNDONE ")
                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        View parent = (View)view.getParent();
                        TextView taskTextView = (TextView)parent.findViewById(R.id.task_title);
                        Log.e("String", (String) taskTextView.getText());
                        String task = String.valueOf(taskTextView.getText());
                        dbHelper.deleteTask(task);
                        loadTaskList();
                        Toast.makeText(TodoList.this,"The item    "+task+"    was deleted!!",
                                Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("CANCEL",null)
                .create();
        dialog.show();

    }
}
