package com.example.deleting.timkiemvieclam.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.deleting.timkiemvieclam.Database.MyDatabaseAccess;
import com.example.deleting.timkiemvieclam.DetailScreen;
import com.example.deleting.timkiemvieclam.Detail_Offline_Screen;
import com.example.deleting.timkiemvieclam.R;
import com.example.deleting.timkiemvieclam.adapter.BookMarkAdapter;
import com.example.deleting.timkiemvieclam.adapter.ListAdapter;
import com.example.deleting.timkiemvieclam.adapter.SlidingMenuAdapter;
import com.example.deleting.timkiemvieclam.model.ItemSlideMenu;
import com.example.deleting.timkiemvieclam.model.Job;

import java.util.ArrayList;
import java.util.List;

public class Fragment3 extends Fragment {
    String arrayLocation[] = {"Lương", "Ngày"};
    Spinner spnlocation;
    ListView lv;
    BookMarkAdapter adapter;
    MyDatabaseAccess db;
    ArrayList<Job> arr = new ArrayList<Job>();
    Button btn;

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

        setHasOptionsMenu(true);

        getList();

        return rootView;
    }

    public void getList() {
        List<Job> contacts = db.getAllJob();

        for (Job job : contacts) {
            Job tmp = new Job();
            tmp.setJob_title(job.getJob_title());
            tmp.setJob_contact_company(job.getJob_contact_company());
            tmp.setLocation_name(job.getLocation_name());
            tmp.setJob_fromsalary(job.getJob_fromsalary());
            tmp.setJob_tosalary(job.getJob_tosalary());
            tmp.setSalary_unit(job.getSalary_unit());
            tmp.setDate_view(job.getDate_view());
            tmp.setJob_id(job.getJob_id());
            tmp.setShare_img(job.getShare_img());
            arr.add(tmp);
        }
        adapter = new BookMarkAdapter(getActivity(), R.layout.bookmark_item_listview, arr);
        lv.setAdapter(adapter);
        lv.setItemsCanFocus(false);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Detail_Offline_Screen.class);
                int key = arr.get(position).getJob_id();
                Bundle bundle = new Bundle();
                bundle.putInt("key", key);
                intent.putExtra("Mypack", bundle);
                startActivity(intent);
            }
        });

    }

    public void xulyXoa() {
        for (int i = lv.getChildCount() - 1; i >= 0; i--) {

            View v = lv.getChildAt(i);

            CheckBox chk = (CheckBox) v.findViewById(R.id.chkDelete);

            if (chk.isChecked()) {
                db.deleteContact(arr.get(i).getJob_id());
                arr.remove(arr.get(i));
            }
        }
        // Sau khi xóa xong thì gọi update giao diện
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_item_delte:
                xulyXoa();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
