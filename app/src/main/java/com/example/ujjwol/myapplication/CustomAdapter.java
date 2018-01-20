package com.example.ujjwol.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ujjwol on 1/17/2018.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
   private Context context;
   private List<Notification_data> notification_data;

    public CustomAdapter(Context context, List<Notification_data> notification_data) {
        this.context = context;
        this.notification_data = notification_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(notification_data.get(position).getTitle());
        holder.content.setText(notification_data.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return notification_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView content;

        public ViewHolder(View itemView){
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.notification_title);
            content=(TextView)itemView.findViewById(R.id.notification_content);


        }

    }
}
