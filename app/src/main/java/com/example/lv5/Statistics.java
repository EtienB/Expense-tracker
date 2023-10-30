package com.example.lv5;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Statistics extends AppCompatActivity
{
    TextView tvRazno, tvPT, tvNamirnice, tvZabava, tvPokloni, tvRezije;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        tvRazno = findViewById(R.id.tvRazno);
        tvPT = findViewById(R.id.tvPT);
        tvNamirnice = findViewById(R.id.tvNamirnice);
        tvZabava = findViewById(R.id.tvZabava);
        tvPokloni = findViewById(R.id.tvPoklon);
        tvRezije = findViewById(R.id.tvRežije);
        pieChart = findViewById(R.id.piechart);

        // Creating a method setData()
        // to set the text in text view and pie chart
        setData();
    }

    private void setData()
    {
        int i = 1;
        float value=0, expensesTotal=0, razno=0, pt=0, nam=0, zab=0, pok=0, rez=0;
        String text,category="";
        StringBuilder sb = new StringBuilder();
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
                value=Float.parseFloat(json.getString("value"));
                category=json.getString("category");
                expensesTotal+=value;

                if(category.equals("Razno"))
                {
                    razno+=value;
                }
                else if(category.equals("Poslovni trošak"))
                {
                    pt+=value;
                }
                else if(category.equals("Namirnice"))
                {
                    nam+=value;
                }
                else if(category.equals("Zabava"))
                {
                    zab+=value;
                }
                else if(category.equals("Poklon"))
                {
                    pok+=value;
                }
                else
                {
                    rez+=value;
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
        }

        tvRazno.setText(Float.toString(razno)+" KN");
        tvPT.setText(Float.toString(pt)+" KN");
        tvNamirnice.setText(Float.toString(nam)+" KN");
        tvZabava.setText(Float.toString(zab)+" KN");
        tvPokloni.setText(Float.toString(pok)+" KN");
        tvRezije.setText(Float.toString(rez)+" KN");

        razno=razno/expensesTotal*100;
        pt=pt/expensesTotal*100;
        nam=nam/expensesTotal*100;
        zab=zab/expensesTotal*100;
        pok=pok/expensesTotal*100;
        rez=rez/expensesTotal*100;

        // Set the data and color to the pie chart
        pieChart.addPieSlice(
                new PieModel(
                        "Razno",
                        Math.round(razno),
                        Color.parseColor("#FFA726")));
        pieChart.addPieSlice(
                new PieModel(
                        "Poslovni troškovi",
                        Math.round(pt),
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "Namirnice",
                        Math.round(nam),
                        Color.parseColor("#EF5350")));
        pieChart.addPieSlice(
                new PieModel(
                        "Zabava",
                        Math.round(zab),
                        Color.parseColor("#29B6F6")));
        pieChart.addPieSlice(
                new PieModel(
                        "Pokloni",
                        Math.round(pok),
                        Color.parseColor("#FF6200EE")));
        pieChart.addPieSlice(
                new PieModel(
                        "Režije",
                        Math.round(rez),
                        Color.parseColor("#FF018786")));

        // To animate the pie chart
        pieChart.startAnimation();
    }
}
