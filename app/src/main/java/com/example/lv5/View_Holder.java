package com.example.lv5;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class View_Holder extends RecyclerView.ViewHolder
{
    TextView name,value,date,category;

    View_Holder(View itemView)
    {
        super(itemView);
        name=(TextView) itemView.findViewById(R.id.Name);
        value=(TextView) itemView.findViewById(R.id.Value);
        date=(TextView) itemView.findViewById(R.id.Date);
        category=(TextView) itemView.findViewById(R.id.Category);
    }
}
