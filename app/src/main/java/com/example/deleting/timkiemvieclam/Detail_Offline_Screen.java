package com.example.deleting.timkiemvieclam;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deleting.timkiemvieclam.Database.MyDatabaseAccess;
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

public class Detail_Offline_Screen extends AppCompatActivity {
    TextView txtTenCongViec;
    TextView txtTenCongTy;
    TextView txtLocation;
    TextView txtSalary;
    TextView txtDate;
    TextView txtMoTa;
    TextView txtYeuCau;
    TextView txtNguoiLH;
    TextView txtEmail;
    TextView txtDiaChi;
    TextView txtUuDai;
    TextView txtLastDate;
    TextView txtEmailCty;
    TextView txtMoTaCty;
    TextView txtLink;
    TextView txtPhone;
    TextView txtWebsite;
    ImageView imgLoGo;
    CheckBox cbSave;
    Button Home;
    MyDatabaseAccess db = new MyDatabaseAccess(this);
    int Gender;
    String mangLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_offline_screen);

        txtTenCongViec = (TextView) findViewById(R.id.txtTenCongViec);
        txtTenCongTy = (TextView) findViewById(R.id.txtTenCongTy);
        txtLocation = (TextView) findViewById(R.id.txtLocation);
        txtSalary = (TextView) findViewById(R.id.txtSalary);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtMoTa = (TextView) findViewById(R.id.txtMoTa);
        txtYeuCau = (TextView) findViewById(R.id.txtYeuCau);
        txtNguoiLH = (TextView) findViewById(R.id.txtNguoiLH);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtDiaChi = (TextView) findViewById(R.id.txtDiaChi);
        txtUuDai = (TextView) findViewById(R.id.txtUudai);
        txtLastDate = (TextView) findViewById(R.id.txtLastDate);
        txtEmailCty = (TextView) findViewById(R.id.txtEmailCty);
        txtMoTaCty = (TextView) findViewById(R.id.txtMoTaCty);
        txtLink = (TextView) findViewById(R.id.txtLink);
        txtPhone = (TextView) findViewById(R.id.txtPhone);
        txtWebsite = (TextView) findViewById(R.id.txtWebsite);
        imgLoGo = (ImageView) findViewById(R.id.imgLogo);
        Home = (Button) findViewById(R.id.btnHome);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        View view =getSupportActionBar().getCustomView();
        TextView tvTitle = (TextView) findViewById(R.id.lvListJob);
        tvTitle.setText("Chi Tiết Công Việc");

        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent callerIntent = getIntent();
        //có intent rồi thì lấy Bundle dựa vào MyPackage
        Bundle packageFromCaller =
                callerIntent.getBundleExtra("Mypack");
        final int key = packageFromCaller.getInt("key");

        List<Job> contacts = db.getAllJob();
        for (Job job : contacts) {
           if (job.getJob_id() == key) {
               txtTenCongViec.setText(job.getJob_title());
               txtTenCongTy.setText(job.getJob_contact_company());
               txtLocation.setText(job.getLocation_name());

               if (job.getJob_fromsalary() == 0 && job.getJob_tosalary() == 0) {
                   txtSalary.setText("Lương Thỏa Thuận");
               } else {
                   txtSalary.setText(job.getJob_fromsalary() + " - " + job.getJob_tosalary() + " " + job.getSalary_unit());
               }

               txtDate.setText(job.getDate_view());
               txtLastDate.setText(job.getJob_lastdate());
               txtMoTa.setText(job.getJob_content());
               txtYeuCau.setText(job.getJob_requireskill());
               txtMoTaCty.setText(job.getEmp_desc());
               txtNguoiLH.setText(job.getJob_contact_name());
               txtEmailCty.setText(job.getJob_contact_email());
               txtEmail.setText(job.getJob_contact_emai2());
               txtDiaChi.setText(job.getJob_contact_address());
               txtUuDai.setText(job.getJob_addsalary());
               txtWebsite.setText(job.getEmp_website());
               txtLink.setText(job.getJob_url());
               txtPhone.setText(job.getJob_contact_phone());
               if (job.getShare_img().equals("")){
                   imgLoGo.setImageResource(R.drawable.ic_no_logo);
               }else {
                   Picasso.with(Detail_Offline_Screen.this).load(job.getShare_img()).into(imgLoGo);
               }

           }
        }

        txtWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent website = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(txtWebsite)));
                startActivity(website);
            }
        });

        txtLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent website = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(txtLink)));
                startActivity(website);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
