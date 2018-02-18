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

public class SuggestionCustomAdapter extends RecyclerView.Adapter<SuggestionCustomAdapter.ViewHolder>{
    private Context context;
    private List<Suggestion_data> suggestion_data;

    public SuggestionCustomAdapter(Context context, List<Suggestion_data> suggestion_data) {
        this.context = context;
        this.suggestion_data = suggestion_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_card,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.suggester.setText(suggestion_data.get(position).getSuggester()+":");
        holder.title.setText(suggestion_data.get(position).getTitle());
        holder.content.setText(suggestion_data.get(position).getContent());
    }






    @Override
    public int getItemCount() {
        return suggestion_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView suggester;
        public TextView title;
        public TextView content;

        public ViewHolder(View itemView){
            super(itemView);
            suggester=(TextView) itemView.findViewById(R.id.tv_suggester);
            title=(TextView)itemView.findViewById(R.id.suggestion_title);
            content=(TextView)itemView.findViewById(R.id.suggestion_content);


        }

    }
}
