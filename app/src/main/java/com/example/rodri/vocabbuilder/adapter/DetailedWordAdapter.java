package com.example.rodri.vocabbuilder.adapter;

import android.app.Activity;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.model.DetailedWord;

import java.util.List;

/**
 * Created by rodri on 2/4/2017.
 */

public class DetailedWordAdapter extends RecyclerView.Adapter<DetailedWordAdapter.MyViewHolder> {

    private Activity activity;
    private Fragment fragment;
    private List<DetailedWord> detailedWords;
    private TypedArray flags;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgFlag;
        public TextView txtWord;
        public TextView txtTranslation;

        public MyViewHolder(View v) {
            super(v);

            imgFlag = (ImageView) v.findViewById(R.id.listItemWord_imgFlag);
            txtWord = (TextView) v.findViewById(R.id.listItemWord_txtWord);
            txtTranslation = (TextView) v.findViewById(R.id.listItemWord_txtTranslation);
        }
    }

    public DetailedWordAdapter (Activity activity, Fragment fragment, List<DetailedWord> detailedWords) {
        this.activity = activity;
        this.fragment = fragment;
        this.detailedWords = detailedWords;
        this.flags = activity.getResources().obtainTypedArray(R.array.language_flags);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_word, parent, false);

        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DetailedWord detailedWord = detailedWords.get(position);

        int flagId = (int) detailedWord.getLanguage().getId();
        holder.imgFlag.setImageResource(flags.getResourceId(flagId-1, 0));

        holder.txtWord.setText(detailedWord.getWord().getName());
        holder.txtTranslation.setText(detailedWord.getWord().getTranslation1());
    }

    @Override
    public int getItemCount() {
        return (detailedWords.isEmpty()) ? 0 : detailedWords.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
