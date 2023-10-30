package com.example.lv5;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RVAdapter extends RecyclerView.Adapter<View_Holder>
{
    SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
    ArrayList<DataClass> nList;
    Context context;

    public RVAdapter(ArrayList<DataClass> data, Application application)
    {
        this.nList=data;
        this.context=application;
    }

    @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        View_Holder holder = new View_Holder(view);
        return holder;
    }

    public void onBindViewHolder(@NonNull View_Holder holder, int position)
    {
        holder.name.setText(nList.get(position).name);
        if(nList.get(position).category.equals(""))
        {
            holder.value.setTextColor(Color.parseColor("#2ECC40"));
            holder.value.setText("+"+Float.toString(nList.get(position).value)+" KN");
        }
        else
        {
            holder.value.setTextColor(Color.parseColor("#FF4136"));
            holder.value.setText("-"+Float.toString(nList.get(position).value)+" KN");
        }
        holder.date.setText(nList.get(position).date);
        holder.category.setText(nList.get(position).category);
    }

    @Override
    public int getItemCount()
    {
        return nList.size();
    }
}
