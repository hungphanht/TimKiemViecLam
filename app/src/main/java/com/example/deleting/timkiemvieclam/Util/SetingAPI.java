package com.example.deleting.timkiemvieclam.Util;

import android.util.Log;

/**
 * Created by Deleting on 3/30/2017.
 */

public class SetingAPI {
    private static final String API_SEARCH_ADVANCE = "advanceSearchJobs";
    public static final String API_JOB_DETAILS = "getJobDetail";
    public static String TOKEN = null;
    static {

        TOKEN = "a5ab26bde79eb7db6198530ddaff3e236";
    }

    public static String getBaseUrl(String api) {
        return "http://api.careerbuilder.vn/?method=" + api + "&token=" + TOKEN;
    }
    public static String getJobByAdvance(String param) {

        String url = new StringBuilder(String.valueOf(getBaseUrl(API_SEARCH_ADVANCE))).append("&arrParam=").append(param).toString();
        Log.d("test", "url:" + url.toString());
        return url;
    }

    public static String getJobDetailsUrl(int jobid) {

        String url = new StringBuilder(String.valueOf(getBaseUrl(API_JOB_DETAILS))).append("&job_id=").append(jobid).toString();
        Log.d("test", "url:" + url.toString());
        return url;
    }
}
