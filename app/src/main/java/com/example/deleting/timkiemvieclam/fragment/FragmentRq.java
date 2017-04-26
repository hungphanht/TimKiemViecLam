package com.example.deleting.timkiemvieclam.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.deleting.timkiemvieclam.Database.MyDatabaseAccess;
import com.example.deleting.timkiemvieclam.R;
import com.example.deleting.timkiemvieclam.Util.ConnectivityReceiver;

import java.util.ArrayList;

/**
 * Created by Deleting on 4/12/2017.
 */

public class FragmentRq extends Fragment {
    MyDatabaseAccess MyDatabaseAccess;
    AutoCompleteTextView txtindustry;
    Button btnSearchRq;
    ListView lvIndustry;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapterIndustry;
    EditText txtsoluong;
    int idIndustry, count;
    String input;
    ArrayList<String> listNameIndustry;
    ArrayList<String> listIDIndustry;
    ConnectivityReceiver sconn = new ConnectivityReceiver();
    int countkey;
    boolean checkconn;

    public FragmentRq() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.searchrequiment_layout, container, false);
        return rootView;
    }

    public void onViewCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getActivity().setTitle("Tìm kiếm yêu cầu công việc");
        checkconn = sconn.isConnected(getActivity());
        addControls();
        addEvents();
    }

    public void addControls() {
        MyDatabaseAccess = new MyDatabaseAccess(getActivity());
        txtindustry = (AutoCompleteTextView) getActivity().findViewById(R.id.txtnamejob);
        lvIndustry = (ListView) getActivity().findViewById(R.id.lvindustry);
        btnSearchRq = (Button) getActivity().findViewById(R.id.btnsearchrq);
        lvIndustry = (ListView) getActivity().findViewById(R.id.lvindustry);
        txtsoluong = (EditText) getActivity().findViewById(R.id.txtsoluong);
        listNameIndustry = MyDatabaseAccess.getNameIndustry();
        listIDIndustry = MyDatabaseAccess.getIDIndustry();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_single_choice, listNameIndustry);
        txtindustry.setAdapter(adapter);
        adapterIndustry = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listNameIndustry);
        lvIndustry.setAdapter(adapterIndustry);

    }

    public void addEvents() {
        txtindustry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnSearchRq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtindustry.length() == 0) {
                    Toast.makeText(getActivity(), "Bạn chưa nhập từ khóa", Toast.LENGTH_LONG).show();
                } else {
                    input = txtindustry.getText().toString();
                    if (txtsoluong.length() != 0) {
                        count = Integer.parseInt(txtsoluong.getText().toString());
                    } else {
                        Toast.makeText(getActivity(), "Bạn chưa nhập số lượng, mặc định là 20", Toast.LENGTH_LONG).show();
                        count = 20;
                    }
                    Log.d("test", " txt luc nay: " + input + "count: " + count);
                    idIndustry = MyDatabaseAccess.getidIndustry(input);
                    Log.d("test", " id cua ban la: " + idIndustry);
                    Bundle bundle = new Bundle();
                    bundle.putString("keyworđ", input);
                    bundle.putInt("id", idIndustry);
                    bundle.putInt("count", count);

                    if (checkconn == true) {
                        FragmentResurtRq resurtRq = new FragmentResurtRq();
                        resurtRq.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.main_content, resurtRq);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else {
                        DialogData(getActivity(),"Không có kết nối Internet");
                    }


                }

            }
        });
        lvIndustry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String input = listNameIndustry.get(position);
                int idin = Integer.parseInt(listIDIndustry.get(position));
                Log.d("test", "name: " + input + "id: " + idin);
                Bundle bundle = new Bundle();
                bundle.putString("keyworđ", input);
                bundle.putInt("id", idin);
                bundle.putInt("count", count);
                if (checkconn == true) {
                    FragmentResurtRq resurtRq = new FragmentResurtRq();
                    resurtRq.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.main_content, resurtRq);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    DialogData(getActivity(),"Không có kết nối Internet");
                }
            }
        });
    }
    public void DialogData(final Activity activity, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("Thông báo");
        dialog.setMessage(message);
        dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               dialog.dismiss();
            }
        });
        dialog.show();

    }
}
