package com.example.rodri.vocabbuilder.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.model.Game;
import com.example.rodri.vocabbuilder.util.Util;

import java.util.List;

/**
 * Created by rodri on 2/25/2017.
 */

public class GameHistoryAdapter extends RecyclerView.Adapter<GameHistoryAdapter.MyViewHolder> {

    private Activity activity;
    private List<Game> games;
    private Util util = new Util();

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtNumWords;
        public TextView txtCorrect;
        public TextView txtIncorrect;
        public TextView txtDate;

        public MyViewHolder(View view) {
            super(view);

            txtNumWords = (TextView) view.findViewById(R.id.listItemGame_txtTotalWords);
            txtCorrect = (TextView) view.findViewById(R.id.listItemGame_txtCorrect);
            txtIncorrect = (TextView) view.findViewById(R.id.listItemGame_txtIncorrect);
            txtDate = (TextView) view.findViewById(R.id.listItemGame_txtDate);
        }
    }

    public GameHistoryAdapter(Activity activity, List<Game> games) {
        this.activity = activity;
        this.games = games;
    }

    @Override
    public int getItemCount() {
        return (games.isEmpty() ? 0 : games.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_game, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Game game = games.get(position);

        holder.txtNumWords.setText(String.valueOf(game.getNumWords()));
        holder.txtCorrect.setText(String.valueOf(game.getCorrect()));
        holder.txtIncorrect.setText(String.valueOf(game.getIncorrect()));
        String date = getDate(game.getAddedAt());
        holder.txtDate.setText(date);
    }

    private String getDate(long time) {
        int[] dateArray = util.fromMillisecondsToSeparateDate(time);
        return dateArray[0] + "/" + dateArray[1] + "/" + dateArray[2];
    }
}
