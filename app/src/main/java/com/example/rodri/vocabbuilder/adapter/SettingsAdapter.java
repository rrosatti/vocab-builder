package com.example.rodri.vocabbuilder.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.activity.AboutActivity;
import com.example.rodri.vocabbuilder.activity.AccountActivity;

import java.util.List;

/**
 * Created by rodri on 3/3/2017.
 */

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.MyViewHolder> {

    private Activity activity;
    private List<String> settingsOptions;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtOption;
        public int pos;

        public MyViewHolder(View view) {
            super(view);

            txtOption = (TextView) view.findViewById(R.id.customListItem_txtItem);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (pos) {
                        case 0: {
                            Intent i = new Intent(activity, AccountActivity.class);
                            activity.startActivity(i);
                            break;
                        }
                        case 1: {
                            Intent i = new Intent(activity, AboutActivity.class);
                            activity.startActivity(i);
                            break;
                        }
                    }
                }
            });
        }

    }

    public SettingsAdapter(Activity activity, List<String> settingsOptions) {
        this.activity = activity;
        this.settingsOptions = settingsOptions;
    }

    @Override
    public int getItemCount() {
        return (settingsOptions.isEmpty() ? 0 : settingsOptions.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String option = settingsOptions.get(position);

        holder.pos = position;
        holder.txtOption.setText(option);
    }
}
