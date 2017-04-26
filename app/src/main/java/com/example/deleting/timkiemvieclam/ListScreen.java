package com.example.deleting.timkiemvieclam;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deleting.timkiemvieclam.adapter.ListAdapter;
import com.example.deleting.timkiemvieclam.model.Job;

import org.json.JSONArray;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by SONY on 3/24/2017.
 */


public class ListScreen extends AppCompatActivity {
    String arraySort[] = {"Lương", "Ngày"};
    String arrayNews[] = {"1-20", "20-40", "40-60"};
    Spinner spnSort;
    Spinner spnNews;
    static ListView lv;
    static ArrayList<Job> mangLV;
    static ListAdapter adapter;
    String apisearch;
    static int Job_ID;
    static String logo;
    static String apilogo;
    String key,idIndustry, idLocation;
    String  DateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_screen);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        View view =getSupportActionBar().getCustomView();
        TextView tvTitle = (TextView) findViewById(R.id.lvListJob);
        tvTitle.setText("Danh Sách Công Việc");

		ImageButton imageButton = (ImageButton) view.findViewById(R.id.action_bar_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        lv = (ListView) findViewById(R.id.listViewDanhSach);
        mangLV = new ArrayList<Job>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent callerIntent = getIntent();
        //có intent rồi thì lấy Bundle dựa vào MyPackage
        Bundle packageFromCaller =
                callerIntent.getBundleExtra("Mypack");
         key = packageFromCaller.getString("key");
         idIndustry = packageFromCaller.getString("idIndustry");
         idLocation = packageFromCaller.getString("idLocation");

        //new LoadDialog().execute();



        spnSort = (Spinner) findViewById(R.id.spnlocation);
        ArrayAdapter<String> adaptersort = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, arraySort
        );
        adaptersort.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnSort.setAdapter(adaptersort);
        spnSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spnSort.getSelectedItem().toString().equals("Lương")) {
                    arrangeSalary();
                } else if (spnSort.getSelectedItem().toString().equals("Ngày")) {
                    arrayDate();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

           /*--------------------------------------*/
        spnNews = (Spinner) findViewById(R.id.spnslnews);
        ArrayAdapter<String> adapternews = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, arrayNews
        );
        adapternews.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnNews.setAdapter(adapternews);
        spnNews.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spnNews.getSelectedItem().equals("1-20")) {
                    for (int i = lv.getCount() - 1; i >= 0; i--) {
                        mangLV.remove(i);
                    }
                    new LoadDialog().execute();
                } else if (spnNews.getSelectedItem().equals("20-40")) {
                    for (int i = lv.getCount() - 1; i >= 0; i--) {
                        mangLV.remove(i);
                    }
                    if (idIndustry == "") {
                        apisearch = "http://api.careerbuilder.vn/?method=advanceSearchJobs&token=a5ab26bde79eb7db6198530ddaff3e236&arrParam={\"keyword\":\"" + key + "\",\"location\":\"" + idLocation + "\",\"start\":\"" + 20 + "\",\"limit\":\"" + 20 + "\"}";
                    } else {
                        apisearch = "http://api.careerbuilder.vn/?method=advanceSearchJobs&token=a5ab26bde79eb7db6198530ddaff3e236&arrParam={\"keyword\":\"" + key + "\",\"industry\":\"" + idIndustry + "\",\"location\":\"" + idLocation + "\",\"start\":\"" + 20 + "\",\"limit\":\"" + 20 + "\"}";
                    }
                    test();
                    arrangeSalary();
                } else if (spnNews.getSelectedItem().equals("40-60")) {
                    for (int i = lv.getCount() - 1; i >= 0; i--) {
                        mangLV.remove(i);
                    }
                    if (idIndustry == "") {
                        apisearch = "http://api.careerbuilder.vn/?method=advanceSearchJobs&token=a5ab26bde79eb7db6198530ddaff3e236&arrParam={\"keyword\":\"" + key + "\",\"location\":\"" + idLocation + "\",\"start\":\"" + 40 + "\",\"limit\":\"" + 40 + "\"}";
                    } else {
                        apisearch = "http://api.careerbuilder.vn/?method=advanceSearchJobs&token=a5ab26bde79eb7db6198530ddaff3e236&arrParam={\"keyword\":\"" + key + "\",\"industry\":\"" + idIndustry + "\",\"location\":\"" + idLocation + "\",\"start\":\"" + 40 + "\",\"limit\":\"" + 20 + "\"}";
                    }
                    test();
                    arrangeSalary();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListScreen.this, DetailScreen.class);
                Bundle bundle = new Bundle();
                bundle.putInt("key", mangLV.get(position).job_id);
                intent.putExtra("Mypack", bundle);
                startActivity(intent);
            }
        });
    }
    public void convert() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String s = DateView;
        Date date;
        try {
            date = df.parse(s);
            String newDate  = df.format(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
    }
    public void arrayDate() {
       for (int i = 0; i < lv.getCount(); i ++){
           Collections.sort(mangLV, new Comparator<Job>() {
               @Override
               public int compare(Job o1, Job o2) {
                   if(o1.getDate_view() == null || o2.getDate_view() == null)
                       return 0;
                   return o2.getDate_view().compareTo(o1.getDate_view());
               }
           });
           adapter.notifyDataSetChanged();
       }
    }
    public void arrangeSalary() {
        for (int i = 0; i < lv.getCount(); i++) {
            Collections.sort(mangLV, new Comparator<Job>() {
                @Override
                public int compare(Job o1, Job o2) {
                    long salary1 = o1.getJob_tosalary();
                    long salary2 = o2.getJob_tosalary();
                    if (salary1 < salary2) {
                        return 1;
                    } else if (salary1 == salary2) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            });
            adapter.notifyDataSetChanged();
        }
    }



    public void test() {
        String input = "";
        try {
            input = downloadUrl(apisearch);
            Log.e("test", "da o day");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //Log.e("test", "Loi o day");
            e.printStackTrace();
        }

        String data = GetJson(input);
        Parsejson(data);
        adapter.notifyDataSetChanged();
    }

    private String Parsejson(String input) {
        Log.d("test", input);
        String locationName, JobTitName, DateView;



        try {
            JSONArray arr = new JSONArray(input);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);


                JobTitName = obj.getString("JOB_TITLE").replace("</em>", "").replace("<em>", "");
                locationName = obj.getString("LOCATION_NAME").replace("<br>", " -");
                DateView = obj.getString("DATE_VIEW").substring(0, 10);
                Job_ID = obj.getInt("JOB_ID");



                apilogo = "http://api.careerbuilder.vn/?method=getJobDetail&token=a5ab26bde79eb7db6198530ddaff3e236&job_id=" + Job_ID;

                String input1 = "";
                try {
                    input1 = downloadUrl(apilogo);
                    //Log.e("test", "da o day");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    //Log.e("test", "Loi o day");
                    e.printStackTrace();
                }
                //Log.d("test", "Day la output: " + input);
                String data1 = GetJson(input1);
                Parsejson1(data1);

                Job list = new Job();
                list.setJob_title(JobTitName);
                list.setJob_contact_company(obj.getString("EMP_NAME"));
                list.setLocation_name(locationName);
                list.setJob_fromsalary(obj.getLong("JOB_FROMSALARY"));
                list.setJob_tosalary(obj.getLong("JOB_TOSALARY"));
                list.setDate_view(DateView);
                list.setJob_id(Job_ID);
                list.setShare_img(logo);
                mangLV.add(list);
                //Log.d("test","Logooooooooooo" + obj.get("SHARE_IMG"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return input;
    }

    public static void Parsejson1(String input1) {
        //Log.d("test","AAA" +input1);

        try {

            JSONObject obj1 = new JSONObject(input1);
            if (obj1.has("SHARE_IMG")) {
                logo = obj1.getString("SHARE_IMG");
            } else {
                logo = "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String GetJson(String input) {
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
                        if (nodeName.equals("advanceSearchJobs") || nodeName.equals("getJobDetail")) {
                            found = true;
                        } else if (((nodeName.equals("response")) || nodeName.equals("response")) && found) {
                            //Log.d("test","json: "+parser.nextText());
                            json = parser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        nodeName = parser.getName();
                        if (nodeName.equals("advanceSearchJobs") || nodeName.equals("getJobDetail")) {
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

    public static String downloadUrl(String strUrl) throws IOException {
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

    private class LoadDialog extends AsyncTask<Void, Void, String> {

        ProgressDialog Dialog;
        int position;

        @Override
        protected void onPreExecute() {
            if (idIndustry == "") {
                apisearch = "http://api.careerbuilder.vn/?method=advanceSearchJobs&token=a5ab26bde79eb7db6198530ddaff3e236&arrParam={\"keyword\":\"" + key + "\",\"location\":\"" + idLocation + "\",\"start\":\"" + 0 + "\",\"limit\":\"" + 20 + "\"}";
            } else {
                apisearch = "http://api.careerbuilder.vn/?method=advanceSearchJobs&token=a5ab26bde79eb7db6198530ddaff3e236&arrParam={\"keyword\":\"" + key + "\",\"industry\":\"" + idIndustry + "\",\"location\":\"" + idLocation + "\",\"start\":\"" + 0 + "\",\"limit\":\"" + 20 + "\"}";
            }
            //Hiển thị Dialog loading...
            Dialog = new ProgressDialog(ListScreen.this);
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
                Log.e("test", "da o day");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                //Log.e("test", "Loi o day");
                e.printStackTrace();
            }

            String data = GetJson(input);
            Parsejson(data);
            return null;
        }

        @Override
        protected void onPostExecute(String avoid) {
            adapter = new ListAdapter(
                    getApplicationContext(),
                    R.layout.item_listview,
                    mangLV
            );
            lv.setAdapter(adapter);
            arrangeSalary();
            Dialog.cancel();

        }
    }

}
