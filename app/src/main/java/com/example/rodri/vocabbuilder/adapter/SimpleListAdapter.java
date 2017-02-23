package com.example.rodri.vocabbuilder.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rodri.vocabbuilder.R;

import java.util.List;

/**
 * Created by rodri on 2/23/2017.
 */

public class SimpleListAdapter extends RecyclerView.Adapter<SimpleListAdapter.MyViewHolder> {

    private Activity activity;
    private List<String> items;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtItem;

        public MyViewHolder(View view) {
            super(view);

            txtItem = (TextView) view.findViewById(R.id.customListItem_txtItem);
        }
    }

    public SimpleListAdapter(Activity activity, List<String> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return (items.isEmpty() ? 0 : items.size());
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String item = items.get(position);

        holder.txtItem.setText(item);
    }


}
