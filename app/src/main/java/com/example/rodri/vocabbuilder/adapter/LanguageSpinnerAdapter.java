package com.example.rodri.vocabbuilder.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.rodri.vocabbuilder.R;

import java.util.List;

/**
 * Created by rodri on 3/9/2017.
 */

public class LanguageSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private Context context;
    private List<String> languages;
    private TypedArray flags;
    private LayoutInflater inflater;

    public LanguageSpinnerAdapter(Context context, List<String> languages) {
        this.context = context;
        this.languages = languages;
        this.flags = context.getResources().obtainTypedArray(R.array.language_flags);
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return (languages.isEmpty() ? 0 : languages.size());
    }

    @Override
    public Object getItem(int position) {
        return languages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = setCustomView(position);

        ImageView imgDropDown = (ImageView) view.findViewById(R.id.customSpinnerItem_imgDown);
        imgDropDown.setVisibility(View.GONE);

        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = setCustomView(position);

        ImageView imgDropDown = (ImageView) view.findViewById(R.id.customSpinnerItem_imgDown);
        imgDropDown.setVisibility(View.VISIBLE);

        return view;
    }

    private View setCustomView(int pos) {
        View view = inflater.inflate(R.layout.custom_spinner_item, null);

        ImageView imgFlag = (ImageView) view.findViewById(R.id.customSpinnerItem_imgFlag);
        TextView txtLang = (TextView) view.findViewById(R.id.customSpinnerItem_txtLang);

        imgFlag.setImageResource(flags.getResourceId(pos, 0));
        txtLang.setText(languages.get(pos));

        return view;

    }
}
