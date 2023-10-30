package com.example.lv5;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Expense extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private Spinner kategorije;
    private static final String[] paths = {"Razno","Poslovni trošak","Namirnice","Zabava","Poklon","Režije"};
    final Calendar myCalendar= Calendar.getInstance();
    EditText editText;
    String kategorija="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        kategorije = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Expense.this, android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategorije.setAdapter(adapter);
        kategorije.setOnItemSelectedListener(this);

        editText=(EditText) findViewById(R.id.editTextDate);
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };

        editText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new DatePickerDialog(Expense.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel()
    {
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.UK);
        editText.setText(dateFormat.format(myCalendar.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
    {
        kategorija=paths[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        kategorija=paths[0];
    }

    public void spremi(View view)
    {
        String numberString = FileCommunication.readFromFile(this, "expenses");
        int number = 0;

        if(numberString == null)
        {
            FileCommunication.writeToFile("expenses", String.valueOf(2), this);
            number = 1;
        }
        else
        {
            numberString = numberString.trim();
            Log.println(Log.INFO, "tag", String.valueOf(number));
            number = Integer.parseInt(numberString);
            FileCommunication.writeToFile("expenses", String.valueOf(number + 1), this);
        }

        EditText editTextDate = findViewById(R.id.editTextDate);
        EditText editTextName = findViewById(R.id.editTextValue);
        EditText editTextValue = findViewById(R.id.editTextValue2);
        editTextValue.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        Gson gson = new Gson();
        ExpenseLog expenseLog = new ExpenseLog(editTextName.getText().toString(), Float.parseFloat(editTextValue.getText().toString()), editTextDate.getText().toString(), kategorija);
        String json = gson.toJson(expenseLog);
        FileCommunication.writeToFile(String.format("expense%d.json", number), json, this);

        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}