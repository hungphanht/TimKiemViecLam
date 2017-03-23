package com.example.deleting.timkiemvieclam.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.deleting.timkiemvieclam.R;
import com.example.deleting.timkiemvieclam.model.Job;

import java.util.ArrayList;


public class JobAdapter extends BaseAdapter {
    private Activity context = null;
    private ArrayList<Job> myArray;
    int layoutId;

    public JobAdapter(Activity context, int layoutId, ArrayList<Job> arr) {
        this.context = context;
        this.layoutId = layoutId;
        Log.e("texxt", arr.toString());
        this.myArray = arr;

    }
    @Override
    public int getCount() {
        return myArray.size();
    }

    @Override
    public Object getItem(int position) {
        return myArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview, null);
            holder = new ViewHolder();
            holder.tvJobtitle = (TextView) convertView.findViewById(R.id.tvJobtitle);
            holder.tvNamecompany = (TextView) convertView.findViewById(R.id.tvNamecompany);
            holder.tvLocation = (TextView) convertView.findViewById(R.id.tvLocation);
            holder.tvSalary = (TextView) convertView.findViewById(R.id.tvSalary);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            convertView.setTag(holder);
//            final Job emp = myArray.get(position);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvJobtitle.setText(myArray.get(position).getJob_title());
        holder.tvNamecompany.setText(myArray.get(position).getJob_contact_company());
        holder.tvLocation.setText(myArray.get(position).getLocation_name());
        if(myArray.get(position).getJob_fromsalary()==0 && myArray.get(position).getJob_tosalary()==0){
            holder.tvSalary.setText("Cạnh Tranh");
        }else if(myArray.get(position).getJob_fromsalary()==0){
            holder.tvSalary.setText(myArray.get(position).getJob_tosalary()+"");
        }else if(myArray.get(position).getJob_tosalary()==0){
            holder.tvSalary.setText(myArray.get(position).getJob_fromsalary()+"");
        }else{
            holder.tvSalary.setText(myArray.get(position).getJob_fromsalary()+" - "+myArray.get(position).getJob_tosalary());
        }

        holder.tvDate.setText(myArray.get(position).getDate_view()+"");

        return convertView;
    }
    class ViewHolder {
        TextView tvJobtitle ;
        TextView tvNamecompany ;
        TextView tvLocation ;
        TextView tvSalary ;
        TextView tvDate ;
    }
}
