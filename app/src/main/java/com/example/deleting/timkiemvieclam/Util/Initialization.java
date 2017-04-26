package com.example.deleting.timkiemvieclam.Util;

import android.app.Activity;
import android.util.Log;

import com.example.deleting.timkiemvieclam.Database.MyDatabaseAccess;
import com.example.deleting.timkiemvieclam.model.Industry;
import com.example.deleting.timkiemvieclam.model.Job;
import com.example.deleting.timkiemvieclam.model.Location;
import com.example.deleting.timkiemvieclam.model.keyword;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Deleting on 3/23/2017.
 */

public class Initialization{
    private MyDatabaseAccess myDatabaseAccess;
    public void Create(Activity activity){
        myDatabaseAccess = new MyDatabaseAccess(activity);
        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parserloc = pullParserFactory.newPullParser();
            XmlPullParser parserindus = pullParserFactory.newPullParser();
            XmlPullParser parserkey = pullParserFactory.newPullParser();
            InputStream in_loc = activity.getApplicationContext().getAssets().open("locations.xml");
            InputStream in_indus = activity.getApplicationContext().getAssets().open("industrys.xml");
            InputStream in_key = activity.getApplicationContext().getAssets().open("Keyword.xml");
            parserloc.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parserloc.setInput(in_loc, null);
            parserindus.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parserindus.setInput(in_indus, null);
            parserkey.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parserkey.setInput(in_key, null);

            ArrayList<Location> locations = parseXML(parserloc);
            for (Location location : locations) {
                boolean add = myDatabaseAccess.addLocation(location);
                if (add == true) {
                    Log.d("test", "add location success");
                } else {
                    Log.d("test", "add location fail");
                }
            }
            Log.d("test", "tao da toi day");
            ArrayList<Industry> industries = parseXML(parserindus);
            for (Industry industry : industries) {
                Log.d("test", "tao da toi day2");
                boolean add = myDatabaseAccess.addIndustry(industry);
                if (add == true) {
                    Log.d("test", "add industry success");
                } else {
                    Log.d("test", "add industry fail");
                }
            }
            ArrayList<keyword> keywords = parseXML(parserkey);
            for (keyword keyword : keywords){
                boolean add = myDatabaseAccess.addKeyword(keyword);
                if (add == true) {
                    Log.d("test", "add keyword success");
                } else {
                    Log.d("test", "add keyword fail");
                }
            }
            int leght = myDatabaseAccess.getLocationCount();
            Log.d("test", "do dai cua location " + leght);
            int leght1 = myDatabaseAccess.getCountIndustry();
            Log.d("test", "do dai cua industry " + leght1);

            Log.d("test", "so luong key: " + myDatabaseAccess.getCountKeyword());


        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            myDatabaseAccess.close();
        }

    }
    public ArrayList parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList arr = null;
        int eventType = parser.getEventType();
        Location locations = null;
        Industry industrys = null;
        keyword keywords = null;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    arr = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("location")) {
                        locations = new Location();
                        locations.location_id = Integer.parseInt(parser.getAttributeValue(null, "id"));
                    } else if (name.equals("industry")) {
                        industrys = new Industry();
                        industrys.industry_id = Integer.parseInt(parser.getAttributeValue(null, "id"));
                        Log.d("test",industrys.industry_id+"");
                    }else if(name.equals("keyword")){
                        Log.d("test", "da vao key");
                        keywords = new keyword();
                        keywords.id = Integer.parseInt(parser.getAttributeValue(null, "id"));
                    } else if (locations != null) {
                        if (name.equals("location_name")) {
                            locations.location_name = parser.nextText();
                        }
                    } else if (industrys != null) {
                        if (name.equals("industry_name")) {
                            industrys.industry_name = parser.nextText();
                        }
                    }else if (keywords !=null){
                        if (name.equals("keyword_name")) {
                            keywords.strkey = parser.nextText();
                        }
                        if(name.equals("keyword_type")) {
                            keywords.type = Integer.parseInt(parser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("location") && locations != null) {
                        arr.add(locations);
                        Log.d("test", "location: " + locations);
                    } else if (name.equalsIgnoreCase("industry") && industrys != null) {
                        arr.add(industrys);
                        Log.d("test", "industry: " + industrys);
                    }else if (name.equalsIgnoreCase("keyword") && keywords != null) {
                        arr.add(keywords);
                        Log.d("test", "key: " + keywords);
                    }
            }
            eventType = parser.next();
        }
        return arr;
    }
}
