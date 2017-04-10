package com.example.deleting.timkiemvieclam.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.deleting.timkiemvieclam.Database.MyDatabaseAccess;
import com.example.deleting.timkiemvieclam.R;
import com.example.deleting.timkiemvieclam.adapter.BookMarkAdapter;
import com.example.deleting.timkiemvieclam.model.Job;

import java.util.ArrayList;
import java.util.List;


public class Fragment3 extends Fragment {
    String arrayLocation[] = {"Lương", "Ngày"};
    String arraySort[] = {"1-20", "20-40", "40-60"};
    Spinner spnlocation;
    Spinner spnslnews;
    ListView lv;
    ArrayList<Job> mangLV;
    BookMarkAdapter adapter;
    MyDatabaseAccess db;

    public Fragment3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bookmark_layout, container, false);

        lv = (ListView) rootView.findViewById(R.id.listViewDanhSach);
        db = new MyDatabaseAccess(getActivity());

        spnlocation = (Spinner) rootView.findViewById(R.id.spnlocation);
        ArrayAdapter<String> adaptersort = new ArrayAdapter<String>(
                getActivity().getBaseContext(), android.R.layout.simple_spinner_item, arrayLocation
        );
        adaptersort.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnlocation.setAdapter(adaptersort);

           /*--------------------------------------*/
        spnslnews = (Spinner) rootView.findViewById(R.id.spnslnews);
        ArrayAdapter<String> adapternews = new ArrayAdapter<String>(
                getActivity().getBaseContext(), android.R.layout.simple_spinner_item, arraySort
        );
        adapternews.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnslnews.setAdapter(adapternews);

        List<Job> contacts = db.getAllJob();
        mangLV = new ArrayList<>();
        for (Job job : contacts) {
            mangLV.add(new Job(
                    job.getJob_title(),
                    job.getJob_contact_company(),
                    job.getLocation_name(),
                    job.getJob_fromsalary(),
                    job.getJob_tosalary(),
                    job.getDate_view(),
                    job.getJob_id(),
                    job.getShare_img()
            ));
        }
        adapter = new BookMarkAdapter(getActivity().getApplicationContext(), R.layout.bookmark_item_listview, mangLV);
        lv.setAdapter(adapter);


        return rootView;
    }
}
