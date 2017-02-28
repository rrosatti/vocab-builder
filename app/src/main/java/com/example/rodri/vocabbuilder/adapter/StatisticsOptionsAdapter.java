package com.example.rodri.vocabbuilder.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.activity.GameHistoryActivity;
import com.example.rodri.vocabbuilder.activity.NeedToReviewActivity;

import java.util.List;

/**
 * Created by rodri on 2/23/2017.
 */

public class StatisticsOptionsAdapter extends RecyclerView.Adapter<StatisticsOptionsAdapter.MyViewHolder> {

    private Activity activity;
    private List<String> options;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtItem;
        public int pos;

        public MyViewHolder(View view) {
            super(view);

            txtItem = (TextView) view.findViewById(R.id.customListItem_txtItem);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (pos) {
                        case 0: {
                            Intent i = new Intent(activity, NeedToReviewActivity.class);
                            activity.startActivity(i);
                            break;
                        }
                        case 1: {
                            Intent i = new Intent(activity, GameHistoryActivity.class);
                            activity.startActivity(i);
                            break;
                        }
                    }
                }
            });
        }
    }

    public StatisticsOptionsAdapter(Activity activity, List<String> options) {
        this.activity = activity;
        this.options = options;
    }

    @Override
    public int getItemCount() {
        return (options.isEmpty() ? 0 : options.size());
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String item = options.get(position);

        holder.pos = position;
        holder.txtItem.setText(item);
    }


}
