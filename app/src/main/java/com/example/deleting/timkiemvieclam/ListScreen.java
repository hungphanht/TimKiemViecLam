package com.example.deleting.timkiemvieclam;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.deleting.timkiemvieclam.R;
import com.example.deleting.timkiemvieclam.adapter.JobAdapter;
import com.example.deleting.timkiemvieclam.model.Job;
import com.example.deleting.timkiemvieclam.model.Location;

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
import java.util.ArrayList;

/**
 * Created by SONY on 3/24/2017.
 */


public class ListScreen extends AppCompatActivity {
    String arrayLocation[] = {"Lương", "Ngày"};
    String arraySort[] = {"1-20", "20-40", "40-60"};
    Spinner spnlocation;
    Spinner spnslnews;
    ListView lv;
    ArrayList<Job> mangLV;

    String apisearch;
    TextView tvtest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_screen);
        tvtest = (TextView) findViewById(R.id.test);
        lv = (ListView) findViewById(R.id.listViewDanhSach);

        mangLV = new ArrayList<Job>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent callerIntent = getIntent();
        //có intent rồi thì lấy Bundle dựa vào MyPackage
        Bundle packageFromCaller =
                callerIntent.getBundleExtra("Mypack");
        String JobName = packageFromCaller.getString("JobName");
        String idIndustry = packageFromCaller.getString("idIndustry");
        String idLocation = packageFromCaller.getString("idLocation");

        apisearch = "http://api.careerbuilder.vn/?method=advanceSearchJobs&token=a5ab26bde79eb7db6198530ddaff3e236&arrParam={\"keyword\":\"" + JobName + "\",\"industry\":\"" + idIndustry + "\",\"location\":\"" + idLocation + "\"}";


        String input = "";
        try {
            input = downloadUrl(apisearch);
            Log.e("test", "da o day");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //Log.e("test", "Loi o day");
            e.printStackTrace();
        }
        //Log.d("test", "Day la output: " + input);
        String data = GetJson(input);
        Parsejson(data);

        spnlocation = (Spinner) findViewById(R.id.spnlocation);
        ArrayAdapter<String> adaptersort = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, arrayLocation
        );
        adaptersort.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnlocation.setAdapter(adaptersort);

           /*--------------------------------------*/
        spnslnews = (Spinner) findViewById(R.id.spnslnews);
        ArrayAdapter<String> adapternews = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, arraySort
        );
        adapternews.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnslnews.setAdapter(adapternews);


    }

    public void Parsejson(String input) {
        Log.d("test", input);
        String locationName, JobTitName1, JobTitName;

        if (input.isEmpty()) {
            return;
        }

        try {
            JSONArray arr = new JSONArray(input);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);

                JobTitName1 = obj.getString("JOB_TITLE").replace("<em>", "");
                JobTitName = JobTitName1.replace("</em>", "");
                locationName = obj.getString("LOCATION_NAME").replace("<br>", " -");

                mangLV.add(new Job(
                        JobTitName,
                        obj.getString("EMP_NAME"),
                        locationName,
                        obj.getLong("JOB_FROMSALARY"),
                        obj.getLong("JOB_TOSALARY"),
                        obj.getString("DATE_VIEW")
                ));
            }

            ListAdapter adapter = new ListAdapter(
                    getApplicationContext(),
                    R.layout.item_listview,
                    mangLV
            );
            lv.setAdapter(adapter);
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
                        if (nodeName.equals("advanceSearchJobs")) {
                            found = true;
                        } else if ((nodeName.equals("response")) && found) {
                            //Log.d("test","json: "+parser.nextText());
                            json = parser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        nodeName = parser.getName();
                        if (nodeName.equals("advanceSearchJobs")) {
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

}
