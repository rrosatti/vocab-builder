package com.example.rodri.vocabbuilder.adapter;

import android.app.Activity;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.model.DetailedWord;
import com.example.rodri.vocabbuilder.model.GameProgress;
import com.example.rodri.vocabbuilder.model.Word;

import java.util.List;

/**
 * Created by rodri on 2/22/2017.
 */

public class FlashCardResultAdapter extends RecyclerView.Adapter<FlashCardResultAdapter.MyViewHolder> {

    private Activity activity;
    private List<DetailedWord> detailedWords;
    private GameProgress gameProgress;
    private TypedArray flags;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgFlag;
        public TextView txtWord;
        public TextView txtTrans;
        public ImageView imgResult;

        public MyViewHolder(View v) {
            super(v);

            imgFlag = (ImageView) v.findViewById(R.id.listWordResult_imgFlag);
            txtWord = (TextView) v.findViewById(R.id.listWordResult_txtWord);
            txtTrans = (TextView) v.findViewById(R.id.listWordResult_txtTrans);
            imgResult = (ImageView) v.findViewById(R.id.listWordResult_imgResult);
        }
    }

    public FlashCardResultAdapter(Activity activity, List<DetailedWord> detailedWords, GameProgress gameProgress) {
        this.activity = activity;
        this.detailedWords = detailedWords;
        this.gameProgress = gameProgress;

        this.flags = activity.getResources().obtainTypedArray(R.array.language_flags);
    }

    @Override
    public int getItemCount() {
        return (detailedWords.isEmpty() ? 0 : detailedWords.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_word_result, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DetailedWord dWord = detailedWords.get(position);

        int flagId = (int) dWord.getLanguage().getId();
        holder.imgFlag.setImageResource(flags.getResourceId(flagId-1, 0));

        holder.txtWord.setText(dWord.getWord().getName());

        String trans = getTranslation(dWord.getWord());
        holder.txtTrans.setText(trans);

        int result = gameProgress.getWordResult(dWord.getWord().getId());
        int resultImgId = getResultImageId(result);
        holder.imgResult.setImageResource(resultImgId);
    }

    private String getTranslation(Word w) {
        String trans = "";
        if (!w.getTranslation1().isEmpty()) {
            trans = w.getTranslation1();
        } else if (!w.getTranslation2().isEmpty()) {
            trans = w.getTranslation2();
        } else {
            trans = w.getTranslation3();
        }

        return trans;
    }

    private int getResultImageId(int result) {
        if (result == 0) {
            return R.drawable.button_incorrect;
        } else {
            return R.drawable.button_correct;
        }
    }

}
