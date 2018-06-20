package com.suntechnologies.cabbie.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hareeshs on 20-06-2018.
 */

public class DesignationAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> destinationArrayList;

    public DesignationAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<String> stringArrayList) {
        super(context, resource);
        this.context =  context;
        this.destinationArrayList = stringArrayList;
    }

    @Override
    public int getCount() {
        return destinationArrayList.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return destinationArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        label.setTextSize(18);
        label.setPadding(0,0,10,0);
        label.setText(destinationArrayList.get(position));
        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        label.setTextSize(18);
        label.setPadding(10,0,10,0);
        label.setText(destinationArrayList.get(position));
        return label;
    }
}
