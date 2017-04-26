package com.example.deleting.timkiemvieclam.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deleting.timkiemvieclam.R;
import com.example.deleting.timkiemvieclam.model.Job;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BookMarkAdapter extends BaseAdapter {
    Activity context;
    ArrayList<Job> list;

    public BookMarkAdapter(Activity context, int bookmark_item_listview, ArrayList<Job> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.bookmark_item_listview, null);


        TextView txt1 = (TextView) row.findViewById(R.id.tvJobtitle);
        TextView txt2 = (TextView) row.findViewById(R.id.tvNamecompany);
        TextView txt3 = (TextView) row.findViewById(R.id.tvLocation);
        TextView txt4 = (TextView) row.findViewById(R.id.tvSalary);
        TextView txt5 = (TextView) row.findViewById(R.id.tvDate);
        TextView txt6 = (TextView) row.findViewById(R.id.tvLastDate);
        ImageView img = (ImageView) row.findViewById(R.id.imvLogo);
        final CheckBox chk = (CheckBox) row.findViewById(R.id.chkDelete);


        final Job p = list.get(position);

        txt1.setText(p.job_title);
        txt2.setText(p.job_contact_company);
        txt3.setText(p.location_name);
        if (p.job_fromsalary == 0 && p.job_tosalary == 0) {
            txt4.setText("Lương Thỏa Thuận");
        } else {
            if (p.salary_unit == null) {
                txt4.setText(String.valueOf(p.job_fromsalary) + " - " + String.valueOf(p.job_tosalary) + " vnd");
            } else {
                txt4.setText(String.valueOf(p.job_fromsalary) + " - " + String.valueOf(p.job_tosalary) + " " + p.salary_unit);
            }
        }
        txt5.setText(p.date_view);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
        String fmDate1 = df1.format(c.getTime());

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String s = p.job_lastdate;
        Date date, date1;
        try {
            date = df.parse(s);
            date1 = df.parse(fmDate1);
            String newDate = df.format(date);
            String newDate1 = df.format(date1);
            boolean after = date.after(date1);
            if (after == false) {
                txt6.setText(p.job_lastdate + " hết hạn");
                txt6.setTextColor(Color.RED);
            } else {
                txt6.setText(p.job_lastdate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d("aaaa", "name: " + p.job_title + " link: " + p.share_img);
        if (p.share_img.equals("")) {
            img.setImageResource(R.drawable.ic_no_logo);

        } else {
            Picasso.with(context).load(p.share_img).into(img);
        }
        return row;
    }
}


