package com.example.alex.cye2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.sql.SQLException;

import static com.example.alex.cye2.CyeDBHelper.DB_NAME;
import static com.example.alex.cye2.CyeDBHelper.KEY_DATE;
import static com.example.alex.cye2.CyeDBHelper.KEY_NAME;
import static com.example.alex.cye2.CyeDBHelper.KEY_SUM;
import static com.example.alex.cye2.CyeDBHelper.TABLE_EXPENSE;
import static com.example.alex.cye2.CyeDBHelper.TABLE_EXPENSE_CATEGORY;

/*http://startandroid.ru/ru/uroki/vse-uroki-spiskom/113-urok-54-kastomizatsija-spiska-sozdaem-svoj-adapter.html
список всех растрат в нужном нам виде
 */

public class ExpenseListActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;
    private SQLiteOpenHelper sqLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);

        ListView listView = (ListView) findViewById(R.id.listView_expenseList);

        sqLiteOpenHelper = new CyeDBHelper(this);
        db = getBaseContext().openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        db = sqLiteOpenHelper.getReadableDatabase();
        cursor = db.query(TABLE_EXPENSE, new String[]{"_id", KEY_DATE, KEY_NAME, KEY_SUM}, null, null, null, null, null);
        ListAdapter listAdapter = new SimpleCursorAdapter(this, R.layout.list_expenses,
                cursor, new String[]{"date", "name"}, //это то что мы выведем на экран
                new int[]{R.id.textView_listExpenses, R.id.textView_listExpenses2}, 0);
        listView.setAdapter(listAdapter);
        Toast.makeText(this, String.valueOf(db.getVersion()), Toast.LENGTH_LONG).show();


    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        cursor.close();
//        db.close();
//    }
}
