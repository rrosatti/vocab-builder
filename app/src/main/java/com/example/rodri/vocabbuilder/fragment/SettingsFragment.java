package com.example.rodri.vocabbuilder.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.adapter.SettingsAdapter;
import com.example.rodri.vocabbuilder.database.MyDataSource;
import com.example.rodri.vocabbuilder.model.Login;
import com.example.rodri.vocabbuilder.other.SimpleDividerItemDecorator;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rodri on 2/4/2017.
 */

public class SettingsFragment extends Fragment {

    private static final String MY_PREFERENCES = "com.example.rodri.vocabbuilder";
    private static final String AUTO_LOGIN = "com.example.rodri.vocabbuilder.autologin";
    private static final String USER_ID = "com.example.rodri.vocabbuilder.userid";

    private Switch switchNotification;
    private Switch switchAutoLogin;
    private RecyclerView listOfSettingsOptions;
    private List<String> listOptions;
    private SharedPreferences sharedPreferences;
    private MyDataSource dataSource;
    private SettingsAdapter adapter;
    private boolean autoLogin;
    private boolean notify;
    private long userId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_settings, container, false);

        iniViews(v);
        dataSource = new MyDataSource(getActivity());
        listOptions = Arrays.asList(getActivity().getResources().getStringArray(R.array.settings_options));
        sharedPreferences = getActivity().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

        autoLogin = sharedPreferences.getBoolean(AUTO_LOGIN, false);
        switchAutoLogin.setChecked(autoLogin);

        userId = Login.getInstance().getUserId();

        notify = checkUserNotificationOption();
        switchNotification.setChecked(notify);

        adapter = new SettingsAdapter(getActivity(), listOptions);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        listOfSettingsOptions.setLayoutManager(llm);
        listOfSettingsOptions.addItemDecoration(new SimpleDividerItemDecorator(getActivity()));
        listOfSettingsOptions.setAdapter(adapter);

        switchAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                autoLogin = isChecked;
                saveAutoLogin();
            }
        });

        switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                notify = isChecked;
                saveNotification();
            }
        });

        return v;
    }

    private void iniViews(View view) {
        switchNotification = (Switch) view.findViewById(R.id.tabSettings_switchNotification);
        switchAutoLogin = (Switch) view.findViewById(R.id.tabSettings_switchAutoLogin);
        listOfSettingsOptions = (RecyclerView) view.findViewById(R.id.tabSettings_listOfSettings);
    }

    private boolean checkUserNotificationOption() {
        try {
            dataSource.open();

            return dataSource.isNotificationActive(userId);

        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
            return false;
        }
    }

    private void saveNotification() {
        try {
            dataSource.open();

            int iNotify = 0;
            if (notify) iNotify = 1;

            dataSource.updateUserNotification(userId, iNotify);

        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
        }
    }

    private void saveAutoLogin() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(AUTO_LOGIN, autoLogin);
        editor.putLong(USER_ID, userId);

        editor.apply();
    }

}
