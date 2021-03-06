package com.example.deleting.timkiemvieclam.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.deleting.timkiemvieclam.model.Industry;
import com.example.deleting.timkiemvieclam.model.Job;
import com.example.deleting.timkiemvieclam.model.Location;
import com.example.deleting.timkiemvieclam.model.keyword;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deleting on 3/20/2017.
 */

public class MyDatabaseAccess extends SQLiteOpenHelper {
    private static final int DB_VERSION = 4;
    private static String DB_NAME = "myjob.db";
    private static SQLiteDatabase db = null;
    //Table
    private static final String table_Location = "Locations";
    private static final String table_Industry = "Industrys";
    private static final String table_Job = "Job";
    private static final String table_Keyword = "Keywords";
    private static final String table_Job_Tmp = "JobRequiment";
    //colum

    private static final String col_location_id = "location_id";
    private static final String col_location_name = "location_name";
    private static final String col_Industry_id = "industry_id";
    private static final String col_Industry_name = "industry_name";

    private static final String col_job_id = "job_id";
    private static final String col_job_title = "job_title";
    private static final String col_job_fromsalary = "job_fromsalary";
    private static final String col_job_tosalary = "job_tosalary";
    private static final String col_job_fromage = "job_fromage";
    private static final String col_job_toage = "job_toage";
    private static final String col_job_gender = "job_gender";
    private static final String col_job_lastdate = "job_lastdate";
    private static final String col_job_content = "job_content";
    private static final String col_job_requireskill = "job_requireskill";
    private static final String col_job_contact_company = "job_contact_company";
    private static final String col_job_contact_address = "contact_address";
    private static final String col_job_contact_email = "job_contact_email";
    private static final String col_job_contact_email2 = "job_contact_email2";
    private static final String col_location = "location";
    private static final String col_emp_desc = "emp_desc";
    private static final String col_emp_website = "emp_website";
    private static final String col_job_url = "job_url";
    private static final String col_date_view = "date_view";
    private static final String col_share_img = "share_img";


    // colum keyword
    private static final String col_keyword_id = "keyword_id";
    private static final String col_keyword_name = "keyword_name";
    private static final String col_keyword_type = "keyword_type";

    private static final String col_salary_unit = "salary_unit";
    private static final String col_job_contact_name = "job_contact_name";
    private static final String col_job_addsalary = "job_addsalary";
    private static final String col_job_contact_phone = "job_contact_phone";

    Context context;

    public MyDatabaseAccess(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    private static final String Create_table_Locations = "create table " + table_Location + "(" + col_location_id + " INTEGER PRIMARY KEY  , " + col_location_name + " nvarchar(50))";
    private static final String Create_table_Industry = "create table " + table_Industry + "(" + col_Industry_id + " INTEGER PRIMARY KEY , " + col_Industry_name + " nvarchar(50))";
    private static final String Create_table_Job = "create table " + table_Job + "(" + col_job_id + " INTEGER PRIMARY KEY , " + col_job_title + " nvarchar(150) , "
            + col_job_fromsalary + " float , " + col_job_tosalary + " float , " + col_job_fromage + " INTEGER , "
            + col_job_toage + " INTEGER , " + col_job_gender + " INTEGER , " + col_job_lastdate + " Date , "
            + col_job_content + " ntext , " + col_job_requireskill + " ntext , " + col_job_contact_company + " nvarchar(150) , "
            + col_job_contact_address + " nvarchar(150) , " + col_job_contact_email + " char(50) , "
            + col_job_contact_email2 + " char(50) , " + col_location + " nvarchar(50) , " + col_emp_desc + " ntext , "
            + col_emp_website + " char(100) , " + col_job_url + " char(100), " + col_date_view + " date, "
            + col_share_img + " char(100), " + col_salary_unit + " char(50), " + col_job_contact_name + " nvarchar(100), "
            + col_job_addsalary + " nvarchar(150), " + col_job_contact_phone + " nvarchar(50))";

    private static final String Create_table_Keyword = "create table " + table_Keyword + " ( " + col_keyword_id + " INTEGER PRIMARY KEY  ," + col_keyword_name + " nvarchar(50), " + col_keyword_type + " INTEGER )";

    private static final String Create_table_Jobtmp = " create table " + table_Job_Tmp + " ( " + col_job_id + " INTEGER PRIMARY KEY  ," + col_job_requireskill + " ntext )";

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("test", Create_table_Locations);
        Log.d("test", Create_table_Industry);
        Log.d("test", Create_table_Job);
        Log.d("test", Create_table_Keyword);
        Log.d("test", Create_table_Jobtmp);
        db.execSQL(Create_table_Locations);
        db.execSQL(Create_table_Industry);
        db.execSQL(Create_table_Job);
        db.execSQL(Create_table_Keyword);
        db.execSQL(Create_table_Jobtmp);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_Location);
        db.execSQL("DROP TABLE IF EXISTS " + table_Industry);
        db.execSQL("DROP TABLE IF EXISTS " + table_Job);
        db.execSQL("DROP TABLE IF EXISTS " + table_Keyword);
        db.execSQL("DROP TABLE IF EXISTS " + table_Job_Tmp);
        //db.execSQL("DROP TABLE IF EXISTS " + table_BookMark);
        onCreate(db);
    }

    public boolean addJob(Job job) {
        //Log.i(TAG, "MyDatabaseHelper.addNote ... " + note.getNoteTitle());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(col_job_id, job.getJob_id());
        values.put(col_job_title, job.getJob_title());
        values.put(col_job_fromsalary, job.getJob_fromsalary());
        values.put(col_job_tosalary, job.getJob_tosalary());
        values.put(col_job_fromage, job.getJob_fromage());
        values.put(col_job_toage, job.getJob_toage());
        values.put(col_job_gender, job.getJob_gender());
        values.put(col_job_lastdate, job.getJob_lastdate());
        values.put(col_job_content, job.getJob_content());
        values.put(col_job_requireskill, job.getJob_requireskill());
        values.put(col_job_contact_company, job.getJob_contact_company());
        values.put(col_job_contact_address, job.getJob_contact_address());
        values.put(col_job_contact_email, job.getJob_contact_email());
        values.put(col_job_contact_email2, job.getJob_contact_emai2());
        values.put(col_location, job.getLocation_name());
        values.put(col_emp_desc, job.getEmp_desc());
        values.put(col_emp_website, job.getEmp_website());
        values.put(col_job_url, job.getJob_url());
        values.put(col_date_view, job.getDate_view());
        values.put(col_share_img, job.getShare_img());
        values.put(col_salary_unit, job.getSalary_unit());
        values.put(col_job_contact_name, job.getJob_contact_name());
        values.put(col_job_addsalary, job.getJob_addsalary());
        values.put(col_job_contact_phone, job.getJob_contact_phone());
        Log.d("test","sdt"+job.getJob_contact_phone());
        // Trèn một dòng dữ liệu vào bảng.


        long rowId = db.insertWithOnConflict(table_Job, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        db.close();
        if (rowId != -1)
            return true;
        return false;
        // Đóng kết nối database.

    }


    public List<Job> getAllJob() {
        //Log.i(TAG, "MyDatabaseHelper.getAllNotes ... " );

        List<Job> jobList = new ArrayList<Job>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + table_Job;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Job job = new Job();
                job.setJob_id(cursor.getInt(0));
                job.setJob_title(cursor.getString(1));
                job.setJob_fromsalary(cursor.getLong(2));
                job.setJob_tosalary(cursor.getLong(3));
                job.setJob_fromage(cursor.getInt(4));
                job.setJob_toage(cursor.getInt(5));
                job.setJob_gender(cursor.getInt(6));
                job.setJob_lastdate(cursor.getString(7));
                job.setJob_content(cursor.getString(8));
                job.setJob_requireskill(cursor.getString(9));
                job.setJob_contact_company(cursor.getString(10));
                job.setJob_contact_address(cursor.getString(11));
                job.setJob_contact_email(cursor.getString(12));
                job.setJob_contact_emai2(cursor.getString(13));
                job.setLocation_name(cursor.getString(14));
                job.setEmp_desc(cursor.getString(15));
                job.setEmp_website(cursor.getString(16));
                job.setJob_url(cursor.getString(17));
                job.setDate_view(cursor.getString(18));
                job.setShare_img(cursor.getString(19));
                job.setSalary_unit(cursor.getString(20));
                job.setJob_contact_name(cursor.getString(21));
                job.setJob_addsalary(cursor.getString(22));
                job.setJob_contact_phone(cursor.getString(23));
                // Thêm vào danh sách.
                jobList.add(job);
            } while (cursor.moveToNext());
        }

        // return note list
        return jobList;
    }
    
    public Integer deleteJob (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table_Job, col_job_id + " = ?",
                new String[] { Integer.toString(id) });
    }

    public Integer deleteAllJob () {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table_Job, null, null);
    }

    public int getJobsCount() {
        String countQuery = "SELECT  * FROM " + table_Job;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    // ------------------------ "Location" table methods ----------------//
    public boolean addLocation(Location location) {
        ContentValues values = new ContentValues();
        values.put(col_location_id, location.getLocation_id());
        Log.d("test", "id = " + location.getLocation_id());
        values.put(col_location_name, location.getLocation_name());
        Log.d("test", "name =" + location.getLocation_name());
        SQLiteDatabase database = this.getWritableDatabase();
//        database.insert(table_Location, null, values);
//        database.close();
//        database.insertWithOnConflict(table_Location, null, values,SQLiteDatabase.CONFLICT_REPLACE);
//        database.close();
        long rowId = database.insertWithOnConflict(table_Location, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        database.close();
        if (rowId != -1)
            return true;
        return false;

    }

    public ArrayList<String> getIDLocation() {
        ArrayList<String> listID = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + table_Location;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("location_id"));
                    listID.add(name);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return listID;
    }

    public ArrayList<String> getNameLocation() {
        ArrayList<String> listName = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + table_Location;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("location_name"));
                    listName.add(name);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return listName;
    }


    public int getLocationCount() {
        String sql = "select * from " + table_Location;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        return cursor.getCount();
    }

    // ------------------------ "Industry" table methods ----------------//

    public boolean addIndustry(Industry industry) {
        ContentValues values = new ContentValues();
        values.put(col_Industry_id, industry.getIndustry_id());
        values.put(col_Industry_name, industry.getIndustry_name());
        Log.d("test", "industry id: " + industry.getIndustry_id());
        Log.d("test", "industry name: " + industry.getIndustry_name());
        SQLiteDatabase database = this.getWritableDatabase();
        long rowId = database.insertWithOnConflict(table_Industry, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        database.close();
        if (rowId != -1)
            return true;
        return false;
    }

    public ArrayList<String> getIDIndustry() {
        ArrayList<String> listID = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + table_Industry;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("industry_id"));
                    listID.add(name);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return listID;
    }

    public ArrayList<String> getNameIndustry() {
        ArrayList<String> listName = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + table_Industry;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("industry_name"));
                    listName.add(name);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return listName;
    }

    public int getidIndustry(String industry_name) {
        int id = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT " + col_Industry_id + " FROM " + table_Industry + " where " + col_Industry_name + " = '" + industry_name + "'";
            Log.d("test", selectQuery);
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    id = cursor.getInt(cursor.getColumnIndex(col_Industry_id));
                    Log.d("test", "hung" + id);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return id;
    }

    public int getCountIndustry() {
        String sql = "select * from " + table_Industry;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        return cursor.getCount();
    }


    // ------------------------ " Keyword " table methods ----------------//
    public boolean addKeyword(keyword keyword) {
        ContentValues values = new ContentValues();
        values.put(col_keyword_id, keyword.getId());
        values.put(col_keyword_name, keyword.getStrkey());
        values.put(col_keyword_type, keyword.getType());
        SQLiteDatabase database = this.getWritableDatabase();
        Log.d("test", keyword.getStrkey());
        long rowId = database.insertWithOnConflict(table_Keyword, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        database.close();
        if (rowId != -1)
            return true;
        return false;
    }

    public ArrayList<keyword> GetKeyword(int type) {

        ArrayList<keyword> arr = new ArrayList<keyword>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + table_Keyword + " where " + col_keyword_type + " ='" + type + "'";
            Log.d("test", selectQuery);
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    keyword tmp = new keyword();
                    int id = cursor.getInt(cursor.getColumnIndex(col_keyword_id));
                    String key = cursor.getString(cursor.getColumnIndex(col_keyword_name));
                    Log.d("test", "id = " + id + " -  key: " + key + " - type: " + type);
                    tmp.setId(id);
                    tmp.setStrkey(key);
                    arr.add(tmp);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }

        return arr;
    }

    public int getCountKeyword() {
        String sql = "select * from " + table_Keyword;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        return cursor.getCount();
    }
    public int getCountKey(int id) {
        String sql = "select * from " + table_Keyword + " where "+ col_keyword_type + " = '" + id +"'" ;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        return cursor.getCount();
    }

    // ------------------------ " Job tmp" table methods ----------------//

    public boolean addJobtmp(Job job) {
        long rowId = -1;
        ContentValues values = new ContentValues();
        values.put(col_job_id, job.getJob_id());
        Log.d("test", "them rq: " + job.getJob_id());
        values.put(col_job_requireskill, job.getJob_requireskill());
        SQLiteDatabase database = this.getWritableDatabase();
        rowId = database.insertWithOnConflict(table_Job_Tmp, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        database.close();
        if (rowId != -1)
            return true;
        return false;
    }

    public ArrayList<Job> getListJobtmp() {
        ArrayList<Job> arr = new ArrayList<Job>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + table_Job_Tmp;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Job tmp = new Job();
                    int id = cursor.getInt(cursor.getColumnIndex(col_job_id));
                    String requireskill = cursor.getString(cursor.getColumnIndex(col_job_requireskill));


                    Log.d("test", "id = " + id + " -  requireskill: " + requireskill);
                    tmp.setJob_id(id);
                    tmp.setJob_requireskill(requireskill);
                    arr.add(tmp);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }

        return arr;
    }

    public int getCountJobtmp() {
        String sql = "select * from " + table_Job_Tmp;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        return cursor.getCount();
    }

    public void DeleteJobtmp() {

        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "Delete  FROM " + table_Job_Tmp;
        db.execSQL(Query);
        db.close();

    }

    boolean isDatabaseExist() {
        return (db != null);
    }


}
