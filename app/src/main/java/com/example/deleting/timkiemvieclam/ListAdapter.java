package com.example.deleting.timkiemvieclam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.deleting.timkiemvieclam.model.Job;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ITLAB on 3/22/2017.
 */

public class ListAdapter extends ArrayAdapter<Job> {

    public ListAdapter(Context context, int resource, List<Job> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.item_listview, null);
        }
        Job p = getItem(position);
        if (p != null) {
            // Anh xa + Gan gia tri
            TextView txt1 = (TextView) view.findViewById(R.id.tvJobtitle);

            txt1.setText(p.job_title);
            TextView txt2 = (TextView) view.findViewById(R.id.tvNamecompany);
            txt2.setText(p.job_contact_company);
            TextView txt3 = (TextView) view.findViewById(R.id.tvLocation);
            txt3.setText(p.location_name);
            TextView txt4 = (TextView) view.findViewById(R.id.tvSalary);
            txt4.setText(String.valueOf(p.job_fromsalary) + " - " +String.valueOf(p.job_tosalary));
            TextView txt5 = (TextView) view.findViewById(R.id.tvDate);
            txt5.setText(p.date_view);
        }
        return view;
    }

}