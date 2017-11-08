package com.example.alex.cye2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import static com.example.alex.cye2.CyeDBHelper.DB_NAME;
import static com.example.alex.cye2.CyeDBHelper.TABLE_EXPENSE;
import static com.example.alex.cye2.CyeDBHelper.TABLE_EXPENSE_CATEGORY;

public class MainActivity extends AppCompatActivity {

    private int mBalance = 100;
    private int mExpense = 0;
    private long mDate = System.currentTimeMillis();
    private static final String TAG = "MainActivity";
    private SQLiteDatabase db;

    private Button mExpenseButton;
    private Button mExpenseCategoryButton;

    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
    String dateString = sdf.format(mDate);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("MainActivity");

        Log.d(TAG, "onCreate called");
        setContentView(R.layout.activity_main);



        mExpenseButton = findViewById(R.id.button_expense);
        mExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
                startActivity(intent);
            }
        });

        mExpenseCategoryButton = findViewById(R.id.button_expenseCategory);
        mExpenseCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryExpenseActivity.class);
                startActivity(intent);
            }
        });


        Button mExpenseListActivity = findViewById(R.id.button);
        mExpenseListActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExpenseListActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart called");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
        /*обновляем состояние баланса в MainActivity */
        TextView textViewBalance = findViewById(R.id.textView_balance);
        balance();
        textViewBalance.setText(" " + String.valueOf(mBalance + mExpense));
        TextView textView = findViewById(R.id.textView_sumAllExpenses);
        textView.setText(String.valueOf(mExpense));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart called");
    }
/*кнопка - получаем сумму значений из таблицы EXPENSES столбца SUM*/
    public void buttonSumAllExpenses(View view) {
        TextView textView = findViewById(R.id.textView_sumAllExpenses);
        balance();
        textView.setText(String.valueOf(mExpense));
    }
/* метод для расчета БАЛАНСА*/
    public void balance (){
        db = getBaseContext().openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT SUM (sum) FROM "+TABLE_EXPENSE, null);
        if(cursor.moveToFirst())
            mExpense = cursor.getInt(0);
        else
            mExpense = -1;
        cursor.close();
        db.close();
    }


}
