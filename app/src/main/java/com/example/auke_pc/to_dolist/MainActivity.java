package com.example.auke_pc.to_dolist;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TodoDatabase db;
    private TodoAdapter adapter;
    Button button;
    ListView listView;
    EditText editText;
    CheckBox checkBox;
    String todo_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = TodoDatabase.getInstance(getApplicationContext());
        Cursor cursor = db.selectAll();

        listView = findViewById(R.id.listView);
        adapter = new TodoAdapter(this, cursor);
        listView.setAdapter(adapter);

        updateData();

        listView.setOnItemClickListener(new OnItemClickListener());
        listView.setOnItemLongClickListener(new OnItemLongClickListener());

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
                addItem();
            }
        });
    }

    private void addItem() {
        editText = findViewById(R.id.editText2);
        todo_item = editText.getText().toString();
        editText.setText("");
        db = TodoDatabase.getInstance(getApplicationContext());
        db.insert(todo_item, 0);

        updateData();

        String add = "Item added";
        Toast.makeText(MainActivity.this, (String) add, Toast.LENGTH_SHORT).show();
    }

    private void updateData() {
        db = TodoDatabase.getInstance(getApplicationContext());
        Cursor newCursor = db.selectAll();
        adapter.swapCursor(newCursor);
        listView.setAdapter(adapter);
    }

    private class OnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            checkBox = view.findViewById(R.id.checkBox);
            Cursor cursor = db.selectAll();
            cursor.move(position + 1);

            int long_id = cursor.getInt(cursor.getColumnIndex("_id"));
            if (checkBox.isChecked()) {
                db.update(long_id, 0);
            } else {
                db.update(long_id, 1);
            }


            updateData();
        }
    }

    private class OnItemLongClickListener implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
            Cursor cursor = db.selectAll();
            cursor.move(position + 1);

            int long_id = cursor.getInt(cursor.getColumnIndex("_id"));
            db.delete(long_id);

            updateData();

            String delete = "Item deleted";
            Toast.makeText(MainActivity.this, (String) delete, Toast.LENGTH_SHORT).show();

            return false;
        }
    }

    public void save() {

    }

}
