package com.example.lv5;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    TextView textViewBudget, textViewSaldo, textViewExpense, textViewIncome, textViewTransaction, textViewCategory, textViewValue, textViewDate;
    Button buttonBudget, buttonExpense, buttonIncome, buttonTransactions, buttonStatistics;
    float budget, saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        budget = preferences.getFloat("budget", 0);

        textViewBudget=findViewById(R.id.textViewBudget);
        textViewSaldo=findViewById(R.id.textViewSaldo);
        textViewExpense=findViewById(R.id.textViewExpense);
        textViewIncome=findViewById(R.id.textViewIncome);
        textViewTransaction=findViewById(R.id.textViewTransaction_final);
        textViewCategory=findViewById(R.id.textViewCategory_final);
        textViewValue=findViewById(R.id.textViewValue_final);
        textViewDate=findViewById(R.id.textViewDate);

        buttonBudget=findViewById(R.id.buttonEditBudget);
        buttonExpense=findViewById(R.id.buttonAddExpense);
        buttonIncome=findViewById(R.id.buttonAddIncome);
        buttonTransactions=findViewById(R.id.buttonTransactions);
        buttonStatistics=findViewById(R.id.button);

        textViewBudget.setText(Float.toString(budget)+" KN");

        int i = 1;
        float value=0,expensesTotal=0,incomeTotal=0;
        Date date= new Date();
        String text,name="",category="";
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
        while ((text = FileCommunication.readFromFile(this, String.format("expense%d.json", i++))) != null)
        {
            sb.append(text);
            sb.append("\\n");
        }
        String[] expenses=sb.toString().split("\\n");
        for(String s: expenses)
        {
            try
            {
                JSONObject json=new JSONObject(s);
                name=json.getString("name");
                value=Float.parseFloat(json.getString("value"));
                date=dateFormat.parse(json.getString("date"));
                category=json.getString("category");
                expensesTotal+=value;
            }
            catch(JSONException | ParseException e)
            {
                e.printStackTrace();
            }
        }
        textViewTransaction.setText(name);
        textViewValue.setText(Float.toString(value)+" KN");
        textViewDate.setText(dateFormat.format(date));
        textViewCategory.setText(category);
        saldo=budget-expensesTotal;
        textViewSaldo.setText(Float.toString(saldo)+" KN");
        textViewExpense.setText(Float.toString(expensesTotal)+" KN");

        i=1;
        sb.setLength(0);
        StringBuilder sb2 = new StringBuilder();

        while ((text = FileCommunication.readFromFile(this, String.format("income%d.json", i++))) != null)
        {
            sb2.append(text);
            sb2.append("\\n");
        }

        String[] income=sb2.toString().split("\\n");
        for(String s: income)
        {
            try
            {
                JSONObject json=new JSONObject(s);
                value=Float.parseFloat(json.getString("value"));
                incomeTotal+=value;
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
        }

        textViewIncome.setText(Float.toString(incomeTotal)+" KN");
    }

    //Activities
    public void editBudget(View view)
    {
        Intent intent=new Intent(this, Budget.class);
        intent.putExtra("budget",budget);
        startActivity(intent);
    }

    public void potrosnja(View view)
    {
        Intent intent=new Intent(this, Expense.class);
        startActivity(intent);
    }

    public void prihod(View view)
    {
        Intent intent=new Intent(this, Income.class);
        startActivity(intent);
    }

    public void transakcija(View view)
    {
        Intent intent=new Intent(this, Transactions.class);
        startActivity(intent);
    }

    public void statistika(View view)
    {
        Intent intent=new Intent(this, Statistics.class);
        startActivity(intent);
    }
}