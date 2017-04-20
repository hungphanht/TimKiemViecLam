package com.example.deleting.timkiemvieclam.adapter;
/*
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deleting.timkiemvieclam.DetailScreen;
import com.example.deleting.timkiemvieclam.Detail_Offline_Screen;
import com.example.deleting.timkiemvieclam.R;
import com.example.deleting.timkiemvieclam.fragment.Fragment3;
import com.example.deleting.timkiemvieclam.model.Job;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.key;



public class BookMarkAdapter extends ArrayAdapter<Job> {

    public BookMarkAdapter(Context context, int resource, ArrayList<Job> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.bookmark_item_listview, null);
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
            if (p.job_fromsalary == 0 && p.job_tosalary == 0) {
                txt4.setText("Lương Thỏa Thuận");
            } else {
                if (p.salary_unit == null)
                {
                    txt4.setText(String.valueOf(p.job_fromsalary) + " - " + String.valueOf(p.job_fromsalary) + " vnd");
                } else {
                    txt4.setText(String.valueOf(p.job_fromsalary) + " - " + String.valueOf(p.job_fromsalary) + " " + p.salary_unit);
                }
            }

            TextView txt5 = (TextView) view.findViewById(R.id.tvDate);
            txt5.setText(p.date_view);

            ImageView img = (ImageView) view.findViewById(R.id.imvLogo);
            Picasso.with(getContext()).load(p.share_img).into(img);

            CheckBox chk = (CheckBox) view.findViewById(R.id.chkDelete);
            chk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setIcon(android.R.drawable.ic_delete);
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn có chắc chắn muốn xóa nhân viên này?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delete(nhanVien.id);
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

        }
        return view;
    }
}*/


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deleting.timkiemvieclam.Database.MyDatabaseAccess;
import com.example.deleting.timkiemvieclam.R;
import com.example.deleting.timkiemvieclam.fragment.Fragment3;
import com.example.deleting.timkiemvieclam.model.Job;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookMarkAdapter extends BaseAdapter{
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
        ImageView img = (ImageView) row.findViewById(R.id.imvLogo);
        final CheckBox chk = (CheckBox) row.findViewById(R.id.chkDelete);


        final Job p = list.get(position);

        txt1.setText(p.job_title);
        txt2.setText(p.job_contact_company);
        txt3.setText(p.location_name);
        if (p.job_fromsalary == 0 && p.job_tosalary == 0) {
            txt4.setText("Lương Thỏa Thuận");
        } else {
            if (p.salary_unit == null)
            {
                txt4.setText(String.valueOf(p.job_fromsalary) + " - " + String.valueOf(p.job_fromsalary) + " vnd");
            } else {
                txt4.setText(String.valueOf(p.job_fromsalary) + " - " + String.valueOf(p.job_fromsalary) + " " + p.salary_unit);
            }
        }
        txt5.setText(p.date_view);
        Picasso.with(context).load(p.share_img).into(img);
        return row;
    }
}


