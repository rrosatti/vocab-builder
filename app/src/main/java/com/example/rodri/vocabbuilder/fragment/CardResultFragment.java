package com.example.rodri.vocabbuilder.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.interfaces.IFlashCardInterface;

/**
 * Created by rodri on 2/21/2017.
 */

public class CardResultFragment extends Fragment {

    private Button btResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_card_result, container, false);

        iniViews(v);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        btResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((IFlashCardInterface) getActivity()).showResult();
                } catch (ClassCastException cce) {
                    cce.printStackTrace();
                }
            }
        });
    }

    private void iniViews(View view) {
        btResult = (Button) view.findViewById(R.id.fragmentCR_btResult);
    }
}
