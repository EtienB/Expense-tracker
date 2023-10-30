package com.example.lv5;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Income extends AppCompatActivity
{
    final Calendar myCalendar= Calendar.getInstance();
    EditText editTextDate;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        editTextDate=(EditText) findViewById(R.id.editTextDate);
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day)
            {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };

        editTextDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new DatePickerDialog(Income.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel()
    {
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.UK);
        editTextDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    public void spremi(View view)
    {
        String numberString = FileCommunication.readFromFile(this, "incomes");
        int number = 0;
        if(numberString == null){
            FileCommunication.writeToFile("incomes", String.valueOf(2), this);
            number = 1;
        } else {
            numberString = numberString.trim();
            Log.println(Log.INFO, "tag", String.valueOf(number));
            number = Integer.parseInt(numberString);
            FileCommunication.writeToFile("incomes", String.valueOf(number + 1), this);
        }

        EditText editTextName = findViewById(R.id.editTextValue);
        EditText editTextValue = findViewById(R.id.editTextValue2);
        editTextValue.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        Gson gson = new Gson();
        IncomeLog incomeLog = new IncomeLog(editTextName.getText().toString(), Float.parseFloat(editTextValue.getText().toString()), editTextDate.getText().toString());
        String json = gson.toJson(incomeLog);
        FileCommunication.writeToFile(String.format("income%d.json", number), json, this);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}