package com.example.rodri.vocabbuilder.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rodri.vocabbuilder.R;

/**
 * Created by rodri on 2/4/2017.
 */

public class PracticeFragment extends Fragment {

    private RadioGroup rGroup;
    private RadioButton rbt5;
    private RadioButton rbt10;
    private RadioButton rbt15;
    private RadioButton rbt20;
    private RadioButton rbt25;
    private RadioButton rbt30;
    private Button btPlay;
    private int numOfWords = 5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_practice, container, false);

        iniViews(v);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.tabPractice_rbt_5: {
                        numOfWords = 5;
                        break;
                    }
                    case R.id.tabPractice_rbt_10: {
                        numOfWords = 10;
                        break;
                    }
                    case R.id.tabPractice_rbt_15: {
                        numOfWords = 15;
                        break;
                    }
                    case R.id.tabPractice_rbt_20: {
                        numOfWords = 20;
                        break;
                    }
                    case R.id.tabPractice_rbt_25: {
                        numOfWords = 25;
                        break;
                    }
                    case R.id.tabPractice_rbt_30: {
                        numOfWords = 30;
                        break;
                    }
                }

            }
        });

        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Num of words: " + numOfWords, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void iniViews(View v) {
        rGroup = (RadioGroup) v.findViewById(R.id.tabPractice_rGroup);
        rbt5 = (RadioButton) v.findViewById(R.id.tabPractice_rbt_5);
        rbt10 = (RadioButton) v.findViewById(R.id.tabPractice_rbt_10);
        rbt15 = (RadioButton) v.findViewById(R.id.tabPractice_rbt_15);
        rbt20 = (RadioButton) v.findViewById(R.id.tabPractice_rbt_20);
        rbt25 = (RadioButton) v.findViewById(R.id.tabPractice_rbt_25);
        rbt30 = (RadioButton) v.findViewById(R.id.tabPractice_rbt_30);
        btPlay = (Button) v.findViewById(R.id.tabPractice_btPlay);
    }
}
