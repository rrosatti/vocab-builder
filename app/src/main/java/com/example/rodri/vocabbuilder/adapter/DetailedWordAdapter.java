package com.example.rodri.vocabbuilder.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.activity.EditWordActivity;
import com.example.rodri.vocabbuilder.database.MyDataSource;
import com.example.rodri.vocabbuilder.model.DetailedWord;
import com.example.rodri.vocabbuilder.model.GameLog;
import com.example.rodri.vocabbuilder.model.Word;

import java.util.List;

/**
 * Created by rodri on 2/4/2017.
 */

public class DetailedWordAdapter extends RecyclerView.Adapter<DetailedWordAdapter.MyViewHolder> {

    private Activity activity;
    private Fragment fragment;
    private List<DetailedWord> detailedWords;
    private TypedArray flags;

    // Custom Dialog
    private ImageView dialogImgLang;
    private TextView dialogTxtWord;
    private TextView dialogTxtTranslations;
    private TextView dialogTxtTotalPlays;
    private TextView dialogTxtCorrect;
    private TextView dialogTxtIncorrect;
    private TextView dialogTxtLast5games;
    private Button btEdit;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgFlag;
        public TextView txtWord;
        public TextView txtTranslation;
        public int pos; // pos in the ArrayList

        public MyViewHolder(View v) {
            super(v);

            imgFlag = (ImageView) v.findViewById(R.id.listItemWord_imgFlag);
            txtWord = (TextView) v.findViewById(R.id.listItemWord_txtWord);
            txtTranslation = (TextView) v.findViewById(R.id.listItemWord_txtTranslation);

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showDialog(pos);
                    return true;
                }
            });
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
        holder.pos = position;

        holder.txtWord.setText(detailedWord.getWord().getName());

        String trans = "";
        if (!detailedWord.getWord().getTranslation1().isEmpty()) {
            trans = detailedWord.getWord().getTranslation1();
        } else if (!detailedWord.getWord().getTranslation2().isEmpty()) {
            trans = detailedWord.getWord().getTranslation2();
        } else {
            trans = detailedWord.getWord().getTranslation3();
        }
        holder.txtTranslation.setText(trans);
    }

    @Override
    public int getItemCount() {
        return (detailedWords.isEmpty()) ? 0 : detailedWords.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private void showDialog(final int pos) {
        final DetailedWord dWord = detailedWords.get(pos);

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_detailed_word);
        dialog.setCancelable(true);
        
        iniCustomViews(dialog);
        Resources res = activity.getResources();

        // Display the word's details
        int flagId = (int) dWord.getLanguage().getId();
        dialogImgLang.setImageResource(flags.getResourceId(flagId-1, 0));

        dialogTxtWord.setText(dWord.getWord().getName());

        String translations = getTranslations(dWord.getWord());
        dialogTxtTranslations.setText(translations);

        String totalPlays = String.format(res.getString(R.string.text_total_plays),
                (dWord.getPerformance().getCorrect() + dWord.getPerformance().getIncorrect()));
        dialogTxtTotalPlays.setText(totalPlays);

        String correct = String.format(res.getString(R.string.text_correct), dWord.getPerformance().getCorrect());
        dialogTxtCorrect.setText(correct);

        String incorrect = String.format(res.getString(R.string.text_incorrect), dWord.getPerformance().getIncorrect());
        dialogTxtIncorrect.setText(incorrect);

        String last5Games = getLast5Games(dWord.getWord().getId());
        dialogTxtLast5games.setText(last5Games);

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, EditWordActivity.class);
                i.putExtra("wordId", dWord.getWord().getId());
                activity.startActivityForResult(i, 1);
                dialog.cancel();
            }
        });

        dialog.show();
        
    }
    
    private void iniCustomViews(Dialog dialog) {
        dialogImgLang = (ImageView) dialog.findViewById(R.id.dialogDetailedWord_imgLang);
        dialogTxtWord = (TextView) dialog.findViewById(R.id.dialogDetailedWord_txtWord);
        dialogTxtTranslations = (TextView) dialog.findViewById(R.id.dialogDetailedWord_txtTranslations);
        dialogTxtTotalPlays = (TextView) dialog.findViewById(R.id.dialogDetailedWord_txtTotalPlays);
        dialogTxtCorrect = (TextView) dialog.findViewById(R.id.dialogDetailedWord_txtCorrect);
        dialogTxtIncorrect = (TextView) dialog.findViewById(R.id.dialogDetailedWord_txtIncorrect);
        dialogTxtLast5games = (TextView) dialog.findViewById(R.id.dialogDetailedWord_txtLast5games);
        btEdit = (Button) dialog.findViewById(R.id.dialogDetailedWord_btEdit);
    }

    private String getTranslations(Word word) {
        String trans = "";
        if (!word.getTranslation1().isEmpty()) {
            trans += word.getTranslation1() + " | ";
        }
        if (!word.getTranslation2().isEmpty()) {
            trans += word.getTranslation2() + " | ";
        }
        if (!word.getTranslation3().isEmpty()) {
            trans += word.getTranslation3() + " | ";
        }

        // return the String without the " | " character at the end
        return trans.substring(0, trans.length() - 3);
    }

    private String getLast5Games(long wordId) {
        MyDataSource dataSource = new MyDataSource(activity);
        try {
            dataSource.open();

            List<GameLog> latestGames = dataSource.getMostRecentlyGames(wordId, 5);

            if (latestGames != null) {
                String last5Games = "";
                for (int i = 0; i < latestGames.size(); i++) {
                    if (latestGames.get(i).getResult() == 1) {
                        last5Games += "C - ";
                    } else {
                        last5Games += "I - ";
                    }
                }

                dataSource.close();
                // return the String without the last " - " at the end
                return last5Games.substring(0, last5Games.length() - 3);

            } else {
                dataSource.close();
                return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
            System.out.println("Something went wrong! getLast5Games()");
            return "";
        }
    }
}
