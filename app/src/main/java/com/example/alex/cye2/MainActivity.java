package com.example.alex.cye2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private int mBalance = 1000;
    private int mExpense = 126;
    private long mDate = System.currentTimeMillis();

    private Button mExpenseButton;
    private Button mExpenseCategoryButton;

    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
    String dateString = sdf.format(mDate);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBalance = mBalance - mExpense;

        TextView textViewBalance = findViewById(R.id.textView_balance);
        textViewBalance.setText(String.valueOf(" " + mBalance));

        TextView textViewExpense = findViewById(R.id.textView_expenseCost);
        textViewExpense.setText(String.valueOf("- "+ mExpense));

        TextView textViewDataOfOperation = findViewById(R.id.textView_dataOfOperation);
        textViewDataOfOperation.setText(dateString);

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
}
