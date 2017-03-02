package com.example.rodri.vocabbuilder.adapter;

import android.app.Activity;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.model.DetailedWord;
import com.example.rodri.vocabbuilder.model.SpacedRepetition;
import com.example.rodri.vocabbuilder.model.Word;
import com.example.rodri.vocabbuilder.util.DateUtil;

import java.util.List;

/**
 * Created by rodri on 3/1/2017.
 */

public class ReviewWordAdapter extends RecyclerView.Adapter<ReviewWordAdapter.MyViewHolder> {

    private Activity activity;
    private List<DetailedWord> detailedWords;
    private TypedArray flagImages;
    private long currentDate;
    private DateUtil dateUtil = new DateUtil();

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgLang;
        public TextView txtWord;
        public ProgressBar progressReviewMeter;
        public int pos;

        public MyViewHolder(View view) {
            super(view);

            imgLang = (ImageView) view.findViewById(R.id.listItemReviewWord_imgLang);
            txtWord = (TextView) view.findViewById(R.id.listItemReviewWord_txtWord);
            progressReviewMeter = (ProgressBar) view.findViewById(R.id.listItemReviewWord_progressReview);

        }
    }

    public ReviewWordAdapter(Activity activity, List<DetailedWord> detailedWords) {
        this.activity = activity;
        this.detailedWords = detailedWords;
        this.flagImages = activity.getResources().obtainTypedArray(R.array.language_flags);
        this.currentDate = dateUtil.getCurrentDate();
    }

    @Override
    public int getItemCount() {
        return (detailedWords.isEmpty() ? 0 : detailedWords.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_review_word, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DetailedWord dWord = detailedWords.get(position);
        Word word = dWord.getWord();
        SpacedRepetition sRepetition = dWord.getSpacedRepetition();

        holder.pos = position;

        int flagId = (int) dWord.getLanguage().getId() - 1;
        holder.imgLang.setImageResource(flagImages.getResourceId( flagId, 1));

        holder.txtWord.setText(word.getName());

        System.out.println("Current: " + currentDate);
        System.out.println("word: " + word.getName() + " time: " + sRepetition.getNext_review());
        long dayInMillis = 86400000;
        long nextReview = sRepetition.getNext_review();

        if (nextReview <= currentDate ) {
            holder.progressReviewMeter.setProgress(100);
        } else if (currentDate < nextReview && (nextReview - currentDate) <= 3*dayInMillis) {
            holder.progressReviewMeter.setProgress(80);
            holder.progressReviewMeter.setProgressDrawable(
                    activity.getDrawable(R.drawable.custom_progress_bar_horizontal_dark_orange));
        } else if (3*dayInMillis < nextReview && (nextReview - currentDate) <= 10*dayInMillis ) {
            holder.progressReviewMeter.setProgress(65);
            holder.progressReviewMeter.setProgressDrawable(
                    activity.getDrawable(R.drawable.custom_progress_bar_horizontal_light_orange));
        } else if (10*dayInMillis < nextReview && (nextReview - currentDate) <= 20*dayInMillis) {
            holder.progressReviewMeter.setProgress(45);
            holder.progressReviewMeter.setProgressDrawable(
                    activity.getDrawable(R.drawable.custom_progress_bar_horizontal_dark_blue));
        } else if (20*dayInMillis < nextReview && (nextReview-currentDate) <= 30*dayInMillis) {
            holder.progressReviewMeter.setProgress(10);
            holder.progressReviewMeter.setProgressDrawable(
                    activity.getDrawable(R.drawable.custom_progress_bar_horizontal_light_blue));
        }

    }
}
