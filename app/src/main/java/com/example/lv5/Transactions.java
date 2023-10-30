package com.example.lv5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class Transactions extends AppCompatActivity
{
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<Float> values = new ArrayList<Float>();
    ArrayList<String> dates = new ArrayList<String>();
    ArrayList<String> categories = new ArrayList<String>();
    ArrayList<JSONObject> array = new ArrayList<JSONObject>();

    RecyclerView recyclerView;
    ArrayList<DataClass> arrayMain = new ArrayList<DataClass>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        recyclerView=findViewById(R.id.RVid);
        RVAdapter adapter = new RVAdapter(arrayMain, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String text;
        int i = 1;
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
        JSONObject json = new JSONObject();

        while ((text = FileCommunication.readFromFile(this, String.format("income%d.json", i++))) != null)
        {
            try
            {
                json = new JSONObject(text);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            array.add(json);
        }

        i = 1;
        while ((text = FileCommunication.readFromFile(this, String.format("expense%d.json", i++))) != null)
        {
            try
            {
                json = new JSONObject(text);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            array.add(json);
        }

        Collections.sort(array, new Comparator<JSONObject>()
        {
            @Override
            public int compare(JSONObject lhs, JSONObject rhs)
            {
                try
                {
                    if(dateFormat.parse(lhs.getString("date")) == null || dateFormat.parse(lhs.getString("date"))==null)
                    {
                        //Log.println(Log.ERROR, "Error", "Not parsed.");
                        return 0;
                    }
                    else
                    {
                        long time1 = dateFormat.parse(lhs.getString("date")).getTime();
                        long time2 = dateFormat.parse(rhs.getString("date")).getTime();
                        return Long.compare(time1, time2);
                    }
                }
                catch (JSONException | ParseException e)
                {
                    e.printStackTrace();
                    return 0;
                }
            }
        });

        String a = "", c = "", d = "";
        float b=0;

        for(JSONObject obj : array)
        {
            try
            {
                a=obj.getString("name");
                b=Float.parseFloat(obj.getString("value"));
                c=obj.getString("date");
                if(obj.has("category")) {
                    d = obj.getString("category");
                } else {
                    d = "";
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            names.add(a);
            values.add(b);
            dates.add(c);
            categories.add(d);
        };
        getData();
    }

    public void getData()
    {
        for(int i=0; i<names.size(); i++)
        {
            DataClass val = new DataClass(names.get(i), values.get(i), dates.get(i), categories.get(i));
            arrayMain.add(val);
        }
    }
}