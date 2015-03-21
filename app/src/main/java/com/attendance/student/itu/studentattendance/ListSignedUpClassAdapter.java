package com.attendance.student.itu.studentattendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nafiurrashid on 2/24/15.
 */
class ListSignedUpClassAdapter extends BaseAdapter {

    Context context;

    protected List<SignedUpClass> listClass;
    LayoutInflater inflater;

    ListSignedUpClassAdapter(Context context, List<SignedUpClass> listClass) {
        this.listClass = listClass;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }




    public int getCount() {
        return listClass.size();
    }

    public SignedUpClass getItem(int position) {
        return listClass.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.single_row, parent, false);

            holder.txtModel = (TextView) convertView.findViewById(R.id.textView1);
          //  holder.txtColor = (TextView) convertView.findViewById(R.id.textView2);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SignedUpClass car = listClass.get(position);
        holder.txtModel.setText(car.getCourse_name());
       // holder.txtColor.setText(car.getProfessor_name());


        return convertView;
    }

    private class ViewHolder {
        TextView txtModel;
        TextView txtColor;

    }

}
