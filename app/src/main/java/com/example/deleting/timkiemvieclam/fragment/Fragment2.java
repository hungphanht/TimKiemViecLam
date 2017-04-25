package com.example.deleting.timkiemvieclam.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

import com.example.deleting.timkiemvieclam.Database.MyDatabaseAccess;
import com.example.deleting.timkiemvieclam.DetailScreen;
import com.example.deleting.timkiemvieclam.ListScreen;
import com.example.deleting.timkiemvieclam.R;
import com.example.deleting.timkiemvieclam.Util.ConnectivityReceiver;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Fragment2 extends Fragment {
    private static final String JOB_NAME = "^[á à ả ã ạ ă â ê ô ơ ư Ă Â Ê Ô Ơ Ư ắ ằ ẳ ẵ ặ ấ ầ ẩ ẫ ậ ế ề ể ễ ệ é è ẻ ẽ ẹ í ì ỉ ĩ ị ó ò ỏ õ ọ ố ồ ổ ỗ ộ ớ ờ ở ỡ ợ ú ù ủ ũ ụ ứ ừ ử ữ ự ý ỳ ỷ ỹ ỵ đA-Za-z0-9]{0,50}$";
    private Pattern pattern;
    private Matcher matcher;
    MyDatabaseAccess db;

    Spinner spinLocation;
    Button btnFind, btnExit;
    EditText edtJobName;

    String idLocation = "";

    Intent intent;
    Bundle bundle;

    URI uri = null;

    public Fragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.searchbycompaname_layout, container, false);
        ConnectivityReceiver sconn = new ConnectivityReceiver();
        final boolean checkconn = sconn.isConnected(getActivity());

        spinLocation = (Spinner) rootView.findViewById(R.id.spinLocation);
        edtJobName = (EditText) rootView.findViewById(R.id.edtJobName);
        btnExit = (Button) rootView.findViewById(R.id.btnExit);
        btnFind = (Button) rootView.findViewById(R.id.btnFind);

        db = new MyDatabaseAccess(getActivity());
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
                if (checkconn == true) {
                    Check();
                } else {
                    Toast.makeText(getActivity(), "không có kết nối mạng", Toast.LENGTH_SHORT).show();
                }
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

        try {
            uri = new URI(edtjobname.replaceAll(" ", "%20"));
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        pattern = Pattern.compile(JOB_NAME);
        matcher = pattern.matcher(edtjobname);

        if (matcher.matches() == false) {
            edtJobName.setError("Có ký tự đặc biệt. Mời bạn nhập lại");
            edtJobName.requestFocus();
        } else {
            intent = new Intent(getActivity(), ListScreen.class);
            //Khai báo Bundle
            bundle = new Bundle();
            bundle.putString("key", String.valueOf(uri));
            bundle.putString("idIndustry", "");
            bundle.putString("idLocation", idLocation);
            intent.putExtra("Mypack", bundle);
            startActivity(intent);
        }
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
