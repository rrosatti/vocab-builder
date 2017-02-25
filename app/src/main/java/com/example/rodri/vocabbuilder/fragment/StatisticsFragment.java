package com.example.rodri.vocabbuilder.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.adapter.StatisticsOptionsAdapter;
import com.example.rodri.vocabbuilder.other.SimpleDividerItemDecorator;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rodri on 2/4/2017.
 */

public class StatisticsFragment extends Fragment {

    private RecyclerView listOfStatisticsOptions;
    private List<String> listOptions;
    private StatisticsOptionsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_statistics, container, false);

        iniViews(v);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        listOptions = Arrays.asList(getActivity().getResources().getStringArray(R.array.statistics_list));
        adapter = new StatisticsOptionsAdapter(getActivity(), listOptions);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        listOfStatisticsOptions.setLayoutManager(llm);
        listOfStatisticsOptions.addItemDecoration(new SimpleDividerItemDecorator(getContext()));
        listOfStatisticsOptions.setAdapter(adapter);
    }

    private void iniViews(View v) {
        listOfStatisticsOptions = (RecyclerView) v.findViewById(R.id.tabStatistics_listOfStatisticsOptions);
    }
}
