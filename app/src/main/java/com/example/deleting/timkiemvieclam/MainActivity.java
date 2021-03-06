package com.example.deleting.timkiemvieclam;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.deleting.timkiemvieclam.adapter.SlidingMenuAdapter;
import com.example.deleting.timkiemvieclam.fragment.Fragment1;
import com.example.deleting.timkiemvieclam.fragment.Fragment2;
import com.example.deleting.timkiemvieclam.fragment.Fragment3;
import com.example.deleting.timkiemvieclam.fragment.FragmentRq;
import com.example.deleting.timkiemvieclam.model.ItemSlideMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    private List<ItemSlideMenu> listSliding;
    private SlidingMenuAdapter adapter;
    private ListView listViewSliding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    boolean check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get intent
        Intent intent = getIntent();

        Bundle data = intent.getBundleExtra("data");
        if (data != null) {
            check = data.getBoolean("check");
            Log.d("test", "ban da nhan: " + check);
        }else{
            check = false;
        }
        //Init component
        listViewSliding = (ListView) findViewById(R.id.lv_sliding_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listSliding = new ArrayList<>();
        //Add item for sliding list
        listSliding.add(new ItemSlideMenu(R.drawable.ic_searchjob, "Search by job name"));
        listSliding.add(new ItemSlideMenu(R.drawable.ic_searchcompany, "Search by Company name"));
        listSliding.add(new ItemSlideMenu(R.drawable.ic_seachrq, "Search by Requiment Job"));
        listSliding.add(new ItemSlideMenu(R.drawable.ic_bokmark, "Bookmark"));
        listSliding.add(new ItemSlideMenu(R.drawable.ic_action_about, "About"));
        adapter = new SlidingMenuAdapter(this, listSliding);
        listViewSliding.setAdapter(adapter);
        //Display icon to open/ close sliding list
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Set title

        //item selected
        //Close menu
        drawerLayout.closeDrawer(listViewSliding);
        //Display fragment 1 when start
        if (check == true) {
            setTitle(listSliding.get(0).getTitle());
            replaceFragment(0);
        } else if (check == false) {
            setTitle(listSliding.get(2).getTitle());
            replaceFragment(2);
        }
        //Hanlde on item click
        listViewSliding.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //item selected
                listViewSliding.setItemChecked(position, true);
                //Replace fragment
                replaceFragment(position);
                //Close menu
                drawerLayout.closeDrawer(listViewSliding);
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_opened, R.string.drawer_closed) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    //Create method replace fragment

    private void replaceFragment(int pos) {
        Fragment fragment = null;
        switch (pos) {
            case 0:
                fragment = new Fragment1();
                break;
            case 1:
                fragment = new Fragment2();
                break;
            case 2:
                fragment = new FragmentRq();
                break;
            case 3:
                fragment = new Fragment3();
                break;
            case 4:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
                builder.setTitle("About");
                builder.setMessage("Design by Team 4:\r\nĐinh Đình Hiệp\r\nLê Cao Thế\r\nPhan Văn Hùng\r\nPhạm Chí Hải");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setTitle("Tìm kiếm theo tên công việc");
                    }
                });
                builder.setCancelable(true);
                builder.create().show();
            default:
                fragment = new Fragment1();
                break;
        }

        if (null != fragment) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_content, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
