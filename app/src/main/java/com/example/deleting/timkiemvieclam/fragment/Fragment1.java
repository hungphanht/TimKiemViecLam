package com.example.deleting.timkiemvieclam.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.deleting.timkiemvieclam.Database.MyDatabaseAccess;
import com.example.deleting.timkiemvieclam.DetailScreen;
import com.example.deleting.timkiemvieclam.ListScreen;
import com.example.deleting.timkiemvieclam.R;
import com.example.deleting.timkiemvieclam.model.Job;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fragment1 extends Fragment {
    final String DB_NAME = "myjob.db";
    SQLiteDatabase database;
    private static final String JOB_NAME = "^[á à ả ã ạ ă â ê ô ơ ư Ă Â Ê Ô Ơ Ư ắ ằ ẳ ẵ ặ ấ ầ ẩ ẫ ậ ế ề ể ễ ệ é è ẻ ẽ ẹ í ì ỉ ĩ ị ó ò ỏ õ ọ ố ồ ổ ỗ ộ ớ ờ ở ỡ ợ ú ù ủ ũ ụ ứ ừ ử ữ ự ý ỳ ỷ ỹ ỵ đA-Za-z0-9]{0,50}$";
    private Pattern pattern;
    private Matcher matcher;
    MyDatabaseAccess db;


    Spinner spinIndustry, spinLocation;
    Button btnFind, btnExit;
    EditText edtJobName;

    Intent intent;
    Bundle bundle;
    String idIndustry = "";
    String idLocation = "";

    public Fragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.seachbyjobname_layout, container, false);
        spinIndustry = (Spinner) rootView.findViewById(R.id.spinIndustry);
        spinLocation = (Spinner) rootView.findViewById(R.id.spinLocation);
        btnFind = (Button) rootView.findViewById(R.id.btnFind);
        btnExit = (Button) rootView.findViewById(R.id.btnExit);
        edtJobName = (EditText) rootView.findViewById(R.id.edtJobName);

        db = new MyDatabaseAccess(getActivity());
        getSpinIndustry();
        getSpinLocation();


        edtJobName.setImeActionLabel("Find", KeyEvent.KEYCODE_ENTER);
        edtJobName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    Check();
                    return true;
                }
                return false;
            }
        });

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();

            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
                            }
        });
        return rootView;
    }


    public void Check() {
        String edtjobname = edtJobName.getText().toString();
        edtjobname.trim();

        pattern = Pattern.compile(JOB_NAME);
        matcher = pattern.matcher(edtjobname);

        if (matcher.matches() == false) {
            edtJobName.setError("Có Kí tự đặc biệt. Mời bạn nhập lại!");
            edtJobName.requestFocus();
        } else {

            intent = new Intent(getActivity(), ListScreen.class);

            //Khai báo Bundle
            bundle = new Bundle();
            bundle.putString("key", edtjobname);
            bundle.putString("idIndustry", idIndustry);
            bundle.putString("idLocation", idLocation);
            intent.putExtra("Mypack", bundle);
            startActivity(intent);
        }
    }

    private void getSpinIndustry() {
        final ArrayList<String> listNameIndustry = db.getNameIndustry();
        final ArrayList<String> listIDIndustry = db.getIDIndustry();

        ArrayAdapter<String> adapterIndustry = new ArrayAdapter<String>(getActivity().getBaseContext(),
                android.R.layout.simple_spinner_item, listNameIndustry);
        adapterIndustry.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinIndustry.setAdapter(adapterIndustry);
        spinIndustry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idIndustry = listIDIndustry.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getSpinLocation() {
        final ArrayList<String> listNameLocation = db.getNameLocation();
        final ArrayList<String> listIDLocation = db.getIDLocation();
        ArrayAdapter<String> adapterLocation = new ArrayAdapter<String>(getActivity().getBaseContext(),
                android.R.layout.simple_spinner_item, listNameLocation);
        adapterLocation.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinLocation.setAdapter(adapterLocation);
        spinLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idLocation = listIDLocation.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}

