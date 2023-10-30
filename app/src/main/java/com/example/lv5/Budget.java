package com.example.lv5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Budget extends AppCompatActivity
{
    TextView budget_old;
    EditText budget_new;
    Button button;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        Intent intent=getIntent();
        float budget=intent.getFloatExtra("budget",0);

        budget_old=findViewById(R.id.textViewBudget_view);
        budget_new=findViewById(R.id.editTextValue);
        budget_new.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        button=findViewById(R.id.buttonSaveBudget);

        budget_old.setText(Float.toString(budget));
    }

    public void spremi(View view)
    {
        float budget=Float.valueOf(String.valueOf(budget_new.getText())).floatValue();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat("budget", budget);
        editor.apply();

        Intent intent=new Intent(this, MainActivity.class);
        intent.putExtra("budget",budget);
        startActivity(intent);
    }
}