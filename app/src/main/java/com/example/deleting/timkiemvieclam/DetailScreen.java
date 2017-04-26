package com.example.deleting.timkiemvieclam;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deleting.timkiemvieclam.Database.MyDatabaseAccess;
import com.example.deleting.timkiemvieclam.Util.ConnectivityReceiver;
import com.example.deleting.timkiemvieclam.Util.Initialization;
import com.example.deleting.timkiemvieclam.adapter.ListAdapter;
import com.example.deleting.timkiemvieclam.model.Job;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DetailScreen extends AppCompatActivity {
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
    String fromSalary, toSalary, linkImage, salaryUnit;
    int fromAge, toAge;
    int Gender, key;
    String data, apisearch;
    private static int SPLASH_TIME_OUT = 3000;
    public static String message;
    private static int tmp_check;
    private static Initialization initialization;
    final Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_screen);


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
        cbSave = (CheckBox) findViewById(R.id.cbSave);
        Home = (Button) findViewById(R.id.btnHome);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        View view = getSupportActionBar().getCustomView();
        TextView tvTitle = (TextView) findViewById(R.id.lvListJob);
        tvTitle.setText("Chi Tiết Công Việc");

        ImageButton imageButton = (ImageButton) view.findViewById(R.id.action_bar_back);
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
        key = packageFromCaller.getInt("key");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);


        new LoadDialog().execute();


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

        cbSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(DetailScreen.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
                builder.setTitle("Bạn muốn luu bài ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DetailScreen.this, "Luu thành công", Toast.LENGTH_SHORT).show();
                        cbSave.setChecked(true);

                        Job jobs = new Job();
                        jobs.setJob_id(key);
                        jobs.setJob_title(txtTenCongViec.getText().toString());
                        jobs.setJob_fromsalary(Long.parseLong(fromSalary));
                        jobs.setJob_tosalary(Long.parseLong(toSalary));
                        jobs.setJob_fromage(fromAge);
                        jobs.setJob_toage(toAge);
                        jobs.setJob_gender(Gender);
                        jobs.setJob_lastdate(txtLastDate.getText().toString());
                        jobs.setJob_content(txtMoTa.getText().toString());
                        jobs.setJob_requireskill(txtYeuCau.getText().toString());
                        jobs.setJob_contact_company(txtTenCongTy.getText().toString());
                        jobs.setJob_contact_address(txtDiaChi.getText().toString());
                        jobs.setJob_contact_email(txtEmailCty.getText().toString());
                        jobs.setJob_contact_emai2(txtEmail.getText().toString());
                        jobs.setLocation_name(txtLocation.getText().toString());
                        jobs.setEmp_desc(txtMoTaCty.getText().toString());
                        jobs.setEmp_website(txtWebsite.getText().toString());
                        jobs.setJob_url(txtLink.getText().toString());
                        jobs.setDate_view(txtDate.getText().toString());
                        jobs.setShare_img(linkImage);
                        jobs.setSalary_unit(salaryUnit);
                        jobs.setJob_contact_name(txtNguoiLH.getText().toString());
                        jobs.setJob_addsalary(txtUuDai.getText().toString());
                        jobs.setJob_contact_phone(txtPhone.getText().toString());
                        db.addJob(jobs);
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cbSave.setChecked(false);
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        });

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityReceiver sconn = new ConnectivityReceiver();
                initialization = new Initialization();
                if (tmp_check != 1) {
                    initialization.Create(DetailScreen.this);
                    Log.d("test", "data ban đã được tạo");
                    tmp_check = 1;
                } else {
                    Log.d("test", "data ban đã được tạo, không thể tạo lại");
                }

                boolean checkconn = sconn.isConnected(DetailScreen.this);
                bundle.putBoolean("check", checkconn);
                if (checkconn == true) {
                    Log.d("test", "connected");
                } else {
                    Log.d("test", "not connected");
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(DetailScreen.this, MainActivity.class);
                        intent.putExtra("data", bundle);
                        startActivity(intent);
                        finish();
                    }
                }, 0);
            }
        });

    }

    public void Parsejson(String input) {

        //Log.d("test","JSONNNNNNNNNNNNNNNNNNN: " + input);
                /**/
        try {
            JSONObject obj = new JSONObject(input);
            String job_content = obj.getString("JOB_CONTENT").replace("*", "\r\n* ").replace("<strong>", "").replace("</strong>", "").replace("<p>", "").replace("</p>", "").replace("<br/>", "").replace("<ul>", "").replace("</ul>", "").replace("<li>", "").replace("</li>", "").replace("<p style=\"text-align:justify\">", "").replace("&amp;", "").replace("<ol>", "").replace("</ol>", "").replace("<em>", "").replace("</em>", "").replace("+", "\r\n+").replace("&lt;", "(").replace("&gt;", ")").replace(":-", ":\r\n- ").replace(".-", ".\r\n- ").replace("<br />", "");
            String job_requireskill = obj.getString("JOB_REQUIRESKILL").replace("*", "\r\n* ").replace("<br/>", "").replace("<ul>", "").replace("</ul>", "").replace("<li>", "").replace("</li>", "").replace("<p>", "").replace("</p>", "").replace("<em>", "").replace("</em>", "").replace("<strong>", "").replace("</strong>", "").replace(":&nbsp;", "").replace("&nbsp", "").replace("&amp;", "").replace(";", "").replace(";-", "\r\n-").replace("+", "\r\n+").replace(":-", ":\r\n- ").replace(".-", ".\r\n- ").replace("<br />", "");
            String emp_desc = obj.getString("EMP_DESC").replace("*", "\r\n* ").replace("<br/>", "").replace("<ul>", "").replace("</ul>", "").replace("<li>", "").replace("</li>", "").replace("<p>", "").replace("</p>", "").replace("<em>", "").replace("</em>", "").replace("<strong>", "").replace("</strong>", "").replace(":&nbsp;", "").replace("&nbsp", "").replace("&amp;", "").replace(";", "").replace(";-", "\r\n-").replace("+", "\r\n+").replace(":-", ":\r\n- ").replace(".-", ".\r\n- ").replace("<br />", "").replace("$ndash", "");
            fromSalary = obj.getString("JOB_FROMSALARY");
            toSalary = obj.getString("JOB_TOSALARY");
            fromAge = obj.getInt("JOB_FROMAGE");
            toAge = obj.getInt("JOB_TOAGE");
            Gender = obj.getInt("JOB_GENDER");
            salaryUnit = obj.getString("JOB_SALARYUNIT").replace("1", "");

            //kiểm tra xem có tồn tại thuộc tính id hay không
            if (obj.has("JOB_TITLE"))
                txtTenCongViec.setText(obj.getString("JOB_TITLE"));

            if (obj.has("JOB_CONTACT_COMPANY"))
                txtTenCongTy.setText(obj.getString("JOB_CONTACT_COMPANY"));

            if (obj.has("LOCATION_NAME"))
                txtLocation.setText(obj.getString("LOCATION_NAME"));

            if (obj.has("JOB_FROMSALARY")) {
                if (fromSalary.equals("0") && toSalary.equals("0")) {
                    txtSalary.setText("Lương Thỏa Thuận");
                } else {
                    txtSalary.setText(fromSalary + " - " + toSalary + " " + salaryUnit);
                }
            }

            if (obj.has("DATE_VIEW"))
                txtDate.setText(obj.getString("DATE_VIEW"));

            if (obj.has("JOB_LASTDATE"))
                txtLastDate.setText(obj.getString("JOB_LASTDATE"));

            if (obj.has("JOB_CONTENT"))
                txtMoTa.setText(job_content);

            if (obj.has("JOB_REQUIRESKILL")) {
                String GioiTinh;
                if (Gender == 0) {
                    GioiTinh = "Nam";
                } else {
                    GioiTinh = "Nữ";
                }
                if (fromAge == 0 && toAge == 0) {
                    txtYeuCau.setText("- Giới tính: " + GioiTinh + "\r\n- Tuổi: " + "Không yêu cầu"  + "\r\n" + job_requireskill);
                } else {
                    txtYeuCau.setText("- Giới tính: " + GioiTinh + "\r\n- Tuổi: " + fromAge + " - " + toAge + "\r\n" + job_requireskill);
                }
            }

            if (obj.has("EMP_DESC"))
                txtMoTaCty.setText(emp_desc);

            if (obj.has("JOB_CONTACT_NAME"))
                txtNguoiLH.setText(obj.getString("JOB_CONTACT_NAME"));

            if (obj.has("JOB_CONTACT_EMAIL"))
                txtEmailCty.setText(obj.getString("JOB_CONTACT_EMAIL").replace("null", "Không có Email"));

            if (obj.has("JOB_CONTACT_EMAIL2"))
                txtEmail.setText(obj.getString("JOB_CONTACT_EMAIL2").replace("null", "Không có Email"));

            if (obj.has("JOB_CONTACT_ADDRESS"))
                txtDiaChi.setText(obj.getString("JOB_CONTACT_ADDRESS"));

            if (obj.has("JOB_ADDSALARY"))
                txtUuDai.setText(obj.getString("JOB_ADDSALARY").replace("null", "Không có Ưu Đãi"));

            if (obj.has("EMP_WEBSITE"))
                txtWebsite.setText(obj.getString("EMP_WEBSITE").replace("null", "Không có Website"));

            if (obj.has("JOB_URL"))
                txtLink.setText(obj.getString("JOB_URL"));

            if (obj.has("JOB_CONTACT_PHONE"))
                txtPhone.setText(obj.getString("JOB_CONTACT_PHONE").replace("null", "Không có Số Điện Thoại"));

            if (obj.has("SHARE_IMG")) {
                linkImage = obj.get("SHARE_IMG").toString();
                Picasso.with(this).load(obj.get("SHARE_IMG").toString()).into(imgLoGo);
            }else{
                imgLoGo.setImageResource(R.drawable.ic_no_logo);
                linkImage = "";
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public String GetJson(String input) {
        String json = "";
        try {
            XmlPullParserFactory fc = XmlPullParserFactory.newInstance();
            XmlPullParser parser = fc.newPullParser();
            parser.setInput(new StringReader(input));

            int eventType = -1;
            String nodeName;
            boolean found = false;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                eventType = parser.next();
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:// là tag mở
                        nodeName = parser.getName();
                        if (nodeName.equals("getJobDetail")) {
                            found = true;
                        } else if ((nodeName.equals("response")) && found) {
                            //Log.d("test","JSONNNNNNNNNNNNNNNNNN: "+parser.nextText());
                            json = parser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        nodeName = parser.getName();
                        if (nodeName.equals("getJobDetail")) {
                            found = false;
                        }
                        break;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        //Log.d("test","json: "+json);
        return json;
    }

    public String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Error downloading url", e.toString());
        } finally {
            try {
                if (iStream != null) {
                    iStream.close();
                }
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            } catch (Exception ignored) {
            }
        }
        return data;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class LoadDialog extends AsyncTask<Void, Void, String> {

        ProgressDialog Dialog;
        int position;

        @Override
        protected void onPreExecute() {
            apisearch = "http://api.careerbuilder.vn/?method=getJobDetail&token=a5ab26bde79eb7db6198530ddaff3e236&job_id=" + key;

            //Hiển thị Dialog loading...
            Dialog = new ProgressDialog(DetailScreen.this);
            Dialog.setTitle("Please Wait");
            Dialog.setMessage("Loading...");
            Dialog.setCancelable(false);
            Dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            String input = "";
            try {
                input = downloadUrl(apisearch);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Log.e("test", "da o day");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                //Log.e("test", "Loi o day");
                e.printStackTrace();
            }
            //Log.d("test", "Day la output: " + input);
            data = GetJson(input);
            return null;
        }

        @Override
        protected void onPostExecute(String avoid) {

            Parsejson(data);
            Toast.makeText(DetailScreen.this, "Load Xong", Toast.LENGTH_SHORT).show();

            Dialog.cancel();

        }
    }
}
