package com.example.alex.cye2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import static com.example.alex.cye2.CyeDBHelper.DB_NAME;
import static com.example.alex.cye2.CyeDBHelper.KEY_DATE;
import static com.example.alex.cye2.CyeDBHelper.KEY_NAME;
import static com.example.alex.cye2.CyeDBHelper.KEY_SUM;
import static com.example.alex.cye2.CyeDBHelper.TABLE_EXPENSE;
import static com.example.alex.cye2.CyeDBHelper.TABLE_EXPENSE_CATEGORY;

public class ExpenseActivity extends AppCompatActivity {

    private Button mButtonAddCategoryExpense;
    private Button mButtonAddExpense;
    private String mNewCategoryExpense;
    private SQLiteDatabase db;
    private Cursor cursor;
    private SQLiteOpenHelper cyeDbHelper;
    private CursorAdapter listAdapter;
    private long mDate = System.currentTimeMillis();
    private int sum;

    //final String LOG_TAG = "myLogs";
    //Log.d(LOG_TAG, "scroll: firstVisibleIt );

    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
    String dateString = sdf.format(mDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        // ListView - должен выводить список категорий расходо / expense_category
        final ListView listView = (ListView) findViewById(R.id.listView_categoryExpense);

        try {
            cyeDbHelper = new CyeDBHelper(this);
            db = cyeDbHelper.getReadableDatabase();
            Toast.makeText(this, " Версия БД - " + String.valueOf(db.getVersion()), Toast.LENGTH_LONG).show();

            cursor = db.query(TABLE_EXPENSE_CATEGORY, new String[]{"_id", "name"}, null, null, null, null, null);

            listAdapter = new SimpleCursorAdapter(ExpenseActivity.this, android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"name"}, //это то что мы выведем на экран
                    new int[]{android.R.id.text1},
                    0);
            listView.setAdapter(listAdapter);

        } catch (SQLiteException e) {
            System.out.println("ОШИБКА  " + e);
        }

        //кнопка добавить категорию
        final EditText editCategoryExpense = findViewById(R.id.editText_addCategoryExpense);
        final String enteringText = editCategoryExpense.getText().toString();
        mButtonAddCategoryExpense = findViewById(R.id.button_addCategoryExpense);
        mButtonAddCategoryExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (enteringText.length() > 3) {
                        db = getBaseContext().openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
                        ContentValues expenseValues = new ContentValues();
                        expenseValues.put(KEY_NAME, enteringText);
                        db.insert(TABLE_EXPENSE_CATEGORY, null, expenseValues);

                        //Обновляем ListView чтобы увидеть последнюю добавленную категорию
                    /*Начало*/
                        db = cyeDbHelper.getReadableDatabase();
                        cursor = db.query(TABLE_EXPENSE_CATEGORY, new String[]{"_id", "name"}, null, null, null, null, null);
                        listAdapter = new SimpleCursorAdapter(ExpenseActivity.this, android.R.layout.simple_list_item_1,
                                cursor, new String[]{"name"}, //это то что мы выведем на экран
                                new int[]{android.R.id.text1}, 0);
                        listView.setAdapter(listAdapter);
                    /*конец*/
                        //Прячем клавиатуру
                    /*начало*/
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(mButtonAddCategoryExpense.getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    /*конец*/
                    } else {
                        Toast.makeText(ExpenseActivity.this, " Название категории не может быть таким коротким ", Toast.LENGTH_LONG).show();
                    }


                } catch (SQLiteException e) {
                    System.out.println("ОШИБКА  " + e);
                }

            }
        });


        /* кнопка добавить расход / добавлять будем в таблицу expense*
        /* НАЧАЛО */
        final EditText editSum = findViewById(R.id.editText_addSum);


        mButtonAddExpense = findViewById(R.id.button_expense2);
        mButtonAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                db = getBaseContext().openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
                //из виджета переводим в String
                final String value = editSum.getText().toString();
                //из String в int
                sum = Integer.parseInt(value);

                //обработка имени
                // 1. из editText
                    /*,  enteringText   */

                // 2. из ListView
                     /*  нужно обработать слушателя из ListView */
                final String[] itemClick = new String[1];
                listView.setOnItemClickListener(new ListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {

                        Toast.makeText(getApplicationContext(), "Потратил"+((TextView) itemClicked).getText(), Toast.LENGTH_SHORT).show();
                        TextView textView = (TextView) itemClicked;
                        itemClick[0] = textView.getText().toString();


                    }
                });

                ContentValues expenseValues = new ContentValues();
                //  expenseValues.put(KEY_DATE, dateString);
                expenseValues.put(KEY_NAME, String.valueOf(itemClick));
                /// expenseValues.put(KEY_SUM, sum); //введите сумму
                db.insert(TABLE_EXPENSE, null, expenseValues);





                //если ввели категорию в editText то она добавиться в таблицу TABLE_EXPENSE_CATEGORY


                //Прячем клавиатуру
                    /*начало*/
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(mButtonAddCategoryExpense.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
                    /*конец*/


            }

        });
        /*конец */

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}







/*СТарая реализация*/
    /*Создаем Массив (категории растрат) и связываем с ListView*
    НАчало */
//    ListView listView = (ListView) findViewById(R.id.listView_categoryExpense);
//    final EditText editText = (EditText) findViewById(R.id.editText_addCategoryExpense);
//    //создаем пустой массив для хранения категорий расходов
//    final ArrayList<String> categoryExpense = new ArrayList<String>();
//    //создаем адаптер для связи ArrayLIst c ListView
//    final ArrayAdapter adapter;
//        adapter = new ArrayAdapter<>(ExpenseActivity.this, android.R.layout.simple_list_item_1, categoryExpense);
//        //привяжем массив через адаптер к ListView
//        listView.setAdapter(adapter);
        /*конец*/

//выводит добавленную категорию в ListView
//                categoryExpense.add(0, editText.getText().toString());
//                adapter.notifyDataSetChanged();
//                editText.setText("");
