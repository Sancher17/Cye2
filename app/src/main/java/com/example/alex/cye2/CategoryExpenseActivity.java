package com.example.alex.cye2;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import static com.example.alex.cye2.CyeDBHelper.TABLE_EXPENSE;
import static com.example.alex.cye2.CyeDBHelper.TABLE_EXPENSE_CATEGORY;

public class CategoryExpenseActivity extends ListActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = getListView();


        try {

            SQLiteOpenHelper cyeDbHelper = new CyeDBHelper(this);
            db = cyeDbHelper.getReadableDatabase();
            Toast.makeText(this, " Версия БД - " + String.valueOf(db.getVersion()), Toast.LENGTH_LONG).show();

            cursor = db.query(TABLE_EXPENSE, new String[]{"_id", "name" }, null, null, null, null, null);

            CursorAdapter listAdapter = new SimpleCursorAdapter(CategoryExpenseActivity.this, android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"name"}, //это то что мы выведем на экран
                    new int[]{android.R.id.text1},
                    0);
            listView.setAdapter(listAdapter);



        } catch (SQLiteException e) {
            System.out.println("ОШИБКА  " + e);
        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

/*Свалка */
// иной вариант запроса
//cursor= db.rawQuery("SELECT * FROM expense;", null);
//
//            if(cursor.moveToFirst()){
//                do{
//                    String date = cursor.getString(0);
//                    String name = cursor.getString(1);
//                    int sum = cursor.getInt(2);
//                    textView.append("Date"+ date + "Name: " + name + " Sum: " + sum + "\n");
//                }
//                while(cursor.moveToNext());
//            }
