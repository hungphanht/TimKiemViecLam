package com.example.deleting.timkiemvieclam.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.deleting.timkiemvieclam.R;
import com.example.deleting.timkiemvieclam.Util.ExecutionSearch;


public class FragmentResurtRq extends Fragment {
    String keyword;
    int count, id;

    public FragmentResurtRq() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.statisticalrequiment_layout, container, false);
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
        getActivity().setTitle("Danh sách những yêu cầu");
        Bundle data = getArguments();
        if (data != null) {
            keyword = data.getString("keyworđ");
            count = data.getInt("count");
            id = data.getInt("id");

        } else {
            Toast.makeText(getActivity(), "Đã xảy ra lỗi vui lòng thử lại", Toast.LENGTH_LONG).show();
        }
        final ExecutionSearch exec = new ExecutionSearch();
        exec.start(getActivity(), keyword, id, count);

    }


}
