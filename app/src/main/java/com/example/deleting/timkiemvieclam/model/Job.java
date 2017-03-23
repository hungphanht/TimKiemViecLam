package com.example.deleting.timkiemvieclam.model;

import java.util.Date;

/**
 * Created by Deleting on 3/20/2017.
 */

public class Job {
    public int job_id;
    public String job_title;
    public int job_worrking_type;
    public float job_fromsalary;
    public float job_tosalary;
    public int job_fromage;
    public int job_toage;
    public int job_gender;// 0: boy; 1 girl;
    public Date job_lastdate;// ngày hết hạn nạp hồ sơ
    public String job_content;//mô tả công việc
    public String job_requireskill; //yêu cầu công việc
    public String job_contact_company;//tên công ty
    public String job_contact_address;//địa chỉ công ty
    public String job_contact_email ; //email lien he
    public String job_contact_emai2 ; //email lien he2
    public String location_name;// vi tri tuyen dung VD:Ha noi
    public String emp_desc; //mo ta ve cong ty;
    public String emp_website;
    public String job_url;//link bai dang tren careerbuilder
    public Date date_view;// ngay cap nhat bai viet;
    public String share_img;//link logo cong ty

    public Job(int job_id, String job_title, int job_worrking_type, float job_fromsalary, float job_tosalary, int job_fromage, int job_toage, int job_gender, Date job_lastdate, String job_content, String job_requireskill, String job_contact_company, String job_contact_address, String job_contact_email, String job_contact_emai2, String location_name, String emp_desc, String emp_website, String job_url, Date date_view, String share_img) {
//        this.job_id = job_id;
//        this.job_title = job_title;
//        this.job_worrking_type = job_worrking_type;
//        this.job_fromsalary = job_fromsalary;
//        this.job_tosalary = job_tosalary;
//        this.job_fromage = job_fromage;
//        this.job_toage = job_toage;
//        this.job_gender = job_gender;
//        this.job_lastdate = job_lastdate;
//        this.job_content = job_content;
//        this.job_requireskill = job_requireskill;
//        this.job_contact_company = job_contact_company;
//        this.job_contact_address = job_contact_address;
//        this.job_contact_email = job_contact_email;
//        this.job_contact_emai2 = job_contact_emai2;
//        this.location_name = location_name;
//        this.emp_desc = emp_desc;
//        this.emp_website = emp_website;
//        this.job_url = job_url;
//        this.date_view = date_view;
//        this.share_img = share_img;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }
    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public int getJob_worrking_type() {
        return job_worrking_type;
    }

    public void setJob_worrking_type(int job_worrking_type) {
        this.job_worrking_type = job_worrking_type;
    }

    public float getJob_fromsalary() {
        return job_fromsalary;
    }

    public void setJob_fromsalary(float job_fromsalary) {
        this.job_fromsalary = job_fromsalary;
    }

    public float getJob_tosalary() {
        return job_tosalary;
    }

    public void setJob_tosalary(float job_tosalary) {
        this.job_tosalary = job_tosalary;
    }

    public int getJob_fromage() {
        return job_fromage;
    }

    public void setJob_fromage(int job_fromage) {
        this.job_fromage = job_fromage;
    }

    public int getJob_toage() {
        return job_toage;
    }

    public void setJob_toage(int job_toage) {
        this.job_toage = job_toage;
    }

    public int getJob_gender() {
        return job_gender;
    }

    public void setJob_gender(int job_gender) {
        this.job_gender = job_gender;
    }

    public Date getJob_lastdate() {
        return job_lastdate;
    }

    public void setJob_lastdate(Date job_lastdate) {
        this.job_lastdate = job_lastdate;
    }

    public String getJob_content() {
        return job_content;
    }

    public void setJob_content(String job_content) {
        this.job_content = job_content;
    }

    public String getJob_requireskill() {
        return job_requireskill;
    }

    public void setJob_requireskill(String job_requireskill) {
        this.job_requireskill = job_requireskill;
    }

    public String getJob_contact_company() {
        return job_contact_company;
    }

    public void setJob_contact_company(String job_contact_company) {
        this.job_contact_company = job_contact_company;
    }

    public String getJob_contact_address() {
        return job_contact_address;
    }

    public void setJob_contact_address(String job_contact_address) {
        this.job_contact_address = job_contact_address;
    }

    public String getJob_contact_email() {
        return job_contact_email;
    }

    public void setJob_contact_email(String job_contact_email) {
        this.job_contact_email = job_contact_email;
    }

    public String getJob_contact_emai2() {
        return job_contact_emai2;
    }

    public void setJob_contact_emai2(String job_contact_emai2) {
        this.job_contact_emai2 = job_contact_emai2;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getEmp_desc() {
        return emp_desc;
    }

    public void setEmp_desc(String emp_desc) {
        this.emp_desc = emp_desc;
    }

    public String getEmp_website() {
        return emp_website;
    }

    public void setEmp_website(String emp_website) {
        this.emp_website = emp_website;
    }

    public String getJob_url() {
        return job_url;
    }

    public void setJob_url(String job_url) {
        this.job_url = job_url;
    }

    public Date getDate_view() {
        return date_view;
    }

    public void setDate_view(Date date_view) {
        this.date_view = date_view;
    }

    public String getShare_img() {
        return share_img;
    }

    public void setShare_img(String share_img) {
        this.share_img = share_img;
    }
}
