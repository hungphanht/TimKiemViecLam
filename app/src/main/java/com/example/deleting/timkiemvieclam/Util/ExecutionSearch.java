package com.example.deleting.timkiemvieclam.Util;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

import com.example.deleting.timkiemvieclam.Database.MyDatabaseAccess;
import com.example.deleting.timkiemvieclam.R;
import com.example.deleting.timkiemvieclam.fragment.FragmentRq;
import com.example.deleting.timkiemvieclam.model.Job;
import com.example.deleting.timkiemvieclam.model.keyword;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

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
import java.net.URLEncoder;
import java.util.ArrayList;


/**
 * Created by Deleting on 3/30/2017.
 */
public class ExecutionSearch {
    public static ArrayList<Job> arrid = new ArrayList<Job>();
    public static String skill = "";
    public static keyword[] arrkeyword;
    String urlsearch;
    ArrayList<Job> arrskill;
    ArrayList<keyword> arrkey;
    MyDatabaseAccess myDatabaseAccess;
    int idtype;
    float[] yData = new float[6];
    String[] xData = new String[6];
    PieChart pieChart;
    TextView txtone,txttwo,txtthr,txtfor,txtfive,txtsix;
    ProgressDialog Dialog;
    public void start(final Activity activity, String key, int id, int limit) {


        String param = getJsonParam(key, limit);
        urlsearch = SetingAPI.getJobByAdvance(param);
        myDatabaseAccess = new MyDatabaseAccess(activity);
        pieChart = (PieChart) activity.findViewById(R.id.idPieChart);
        txtone = (TextView) activity.findViewById(R.id.txtone);
        txttwo = (TextView) activity.findViewById(R.id.txttow);
        txtthr = (TextView) activity.findViewById(R.id.txtthir);
        txtfor = (TextView) activity.findViewById(R.id.txtfor);
        txtfive = (TextView) activity.findViewById(R.id.txtfine);
        txtsix = (TextView) activity.findViewById(R.id.txtsix);
        idtype = id;
        int countkey = myDatabaseAccess.getCountKey(idtype);
        if(countkey==0){
            DialogData(activity,"Xin lỗi! chưa có dữ liệu cho công việc này");

        }else {
            activity.runOnUiThread((new Runnable() {

                @Override
                public void run() {
                    Dialog = new ProgressDialog(activity);
                    Dialog.setTitle("Đang tải giữ liệu");
                    Dialog.setMessage("Loading...");
                    Dialog.show();
                    // TODO Auto-generated method stub
                    new RequimentJob().execute(urlsearch);

                }
            }));
        }
    }

    public class RequimentJob extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String res = null;
            try {
                res = downloadUrl(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("test", res);
            return res;

        }

//       protected void onProgressUpdate(Void... values) {
//            super.onProgressUpdate(values);
//  ma      }

        protected void onPostExecute(String res) {
            ProssData(res);
            Dialog.dismiss();
        }
    }

    public void ProssData(String input) {
        Log.d("test", input);
        if (input.isEmpty()) {
            return;
        }
        try {


        } catch (Exception e) {
            e.printStackTrace();
        }


        String data = ParseXML(input);
        ArrayList<Job> mangid = Getjsonid(data);
        int countjob = myDatabaseAccess.getCountJobtmp();
        if (countjob == 0) {
            for (Job job : mangid) {
                int jobid = job.getJob_id();

                String urldetail = SetingAPI.getJobDetailsUrl(jobid);
                String inputdeltail = "";
                try {
                    inputdeltail = downloadUrl(urldetail);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String datadetail = ParseXML(inputdeltail);
                String out = GetJobskill(datadetail);
                job.job_id = jobid;
                job.job_requireskill = out;

                boolean add = myDatabaseAccess.addJobtmp(job);
                if (add == true) {
                    Log.d("test", "add job success");
                } else {
                    Log.d("test", "add job fail");
                }
            }
        } else {
            Log.d("test1", "không truy vấn nữa");

        }
        arrkeyword = new keyword[myDatabaseAccess.GetKeyword(idtype).size()];
        arrskill = myDatabaseAccess.getListJobtmp();
        arrkey = myDatabaseAccess.GetKeyword(idtype);
        for (int i = 0; i < arrkey.size(); i++) {
            keyword keywords = new keyword();
            String key = arrkey.get(i).getStrkey().toLowerCase();
            keywords.strkey = key;
            for (Job job : arrskill) {
                String skill = job.getJob_requireskill().toLowerCase();
                if (skill.contains(key) == true) {
                    keywords.soluong += 1;
                }
            }
            arrkeyword[i] = keywords;

        }
        if (arrkeyword.length > 1) {
            ArrayList<keyword> arrayList = SortbyDESC(arrkeyword);
            Log.d("test", "sau khi sap xep");
            int tong = 0;
            for (keyword listkey : arrayList) {
                tong+= listkey.getSoluong();
            }
            for (int i=0;i<6;i++){

                float tile =  (100f*arrayList.get(i).getSoluong()/(float)tong);
                yData[i] = tile;
                xData[i] =arrayList.get(i).getStrkey();

            }
            txtone.setText(xData[0].toString());
            txttwo.setText(xData[1].toString());
            txtthr.setText(xData[2].toString());
            txtfor.setText(xData[3].toString());
            txtfive.setText(xData[4].toString());
            txtsix.setText(xData[5].toString());

            Description des = new Description();
            des.setText("Danh sách yêu cầu công việc được yêu cầu nhiều nhất");
            pieChart.setRotationEnabled(true);
            pieChart.setHoleRadius(25f);
            pieChart.setTransparentCircleAlpha(0);
            pieChart.setCenterText("Requiment skill");
            pieChart.setCenterTextSize(10);
            pieChart.setDescription(des);
            addDataSet();


        }
        myDatabaseAccess.DeleteJobtmp();

    }


    //Sắp xếp theo số lượng xuất hiện keyword
    public ArrayList<keyword> SortbyDESC(keyword[] arrkeywork) {

        ArrayList<keyword> arrnew = new ArrayList<keyword>();
        keyword tmp = new keyword();
        for (int i = 0; i < arrkeywork.length; i++) {

            for (int j = i + 1; j < arrkeywork.length; j++) {

                if (arrkeywork[i].getSoluong() < arrkeywork[j].getSoluong()) {
                    tmp = arrkeywork[j];
                    arrkeywork[j] = arrkeywork[i];
                    arrkeywork[i] = tmp;
                }
            }
            arrnew.add(arrkeywork[i]);
        }
        return arrnew;
    }

    //Tải data

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

    //tách json tu xml
    public String ParseXML(String input) {
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
                        if (nodeName.equals("advanceSearchJobs") | nodeName.equals("getJobDetail")) {
                            found = true;
                        } else if ((nodeName.equals("response")) && found) {
                            //Log.d("test", "json: " + parser.nextText());
                            json = parser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        nodeName = parser.getName();
                        if (nodeName.equals("advanceSearchJobs") | nodeName.equals("getJobDetail")) {
                            found = false;
                        }
                        break;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("test", "json: " + json);
        return json;
    }

    //lấy id job dụa trên ip search
    public ArrayList<Job> Getjsonid(String input) {
        try {
            JSONArray arr = new JSONArray(input);
            for (int i = 0; i < arr.length(); i++) {
                Job tmp = new Job();
                JSONObject obj = arr.getJSONObject(i);
                Log.d("test", "Thong tin việc thứ:" + i);
                Log.d("test", "Id Viec: " + obj.getString("JOB_ID"));
                tmp.setJob_id(Integer.parseInt(obj.getString("JOB_ID")));
                arrid.add(tmp);
            }
            Log.d("test", arrid.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrid;

    }

    //dụa vào ip job lấy jobskill
    public String GetJobskill(String input) {
        String output = "";

        try {
            JSONObject obj = new JSONObject(input);
            output = obj.getString("JOB_REQUIRESKILL");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("test", output);
        skill = Xulychuoi(output);

        Log.d("test", "data da xu lu:" + skill);
        return skill;
    }

    //cắt những phần tử thừa
    public String Xulychuoi(String s) {
        String resurt = "";
        int start = 0, end = 0;
        start = s.indexOf("<ul>");
        end = s.indexOf("</ul>");
        if (start != 1 && end != -1) {
            resurt = s.substring(start + 4, end);
        } else {
            resurt = s;
        }
        Log.d("test", "ket thuc vi tri:" + end);
        return resurt;
    }

    //truyền tham số vào apidat
    public String getJsonParam(String keyword, int limit) {
        String paramString = "";
        JSONObject param = new JSONObject();
        try {
            param.put("KEYWORD", keyword);
            param.put("LIMIT", limit);
            paramString = URLEncoder.encode(param.toString(), "utf-8");
        } catch (Exception e) {
        }
        Log.d("test", "param:" + param.toString());
        return paramString;
    }


    private void addDataSet() {

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , i));
        }

        for(int i = 0; i < xData.length; i++){
            xEntrys.add(xData[i]);

        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Requiment Job");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        pieDataSet.setColors(colors);

        // add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
//        pieChart.setOn
    }
    public void DialogData(final Activity activity, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("Thông báo");
        dialog.setMessage(message);
        dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FragmentRq fragmentRq = new FragmentRq();
                FragmentManager fragmentManager = activity.getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_content, fragmentRq);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        dialog.show();

    }
}
