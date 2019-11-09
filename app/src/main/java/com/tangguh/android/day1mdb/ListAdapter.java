package com.tangguh.android.day1mdb;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.tangguh.android.day1mdb.R.layout.lv_employee;
import static com.tangguh.android.day1mdb.R.layout.lv_kantor;

public class ListAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String,String>> data;
    private int list_position=0;
    private static LayoutInflater inflater=null;
    private String PACKAGE_NAME;
    public ListAdapter(Activity a,ArrayList<HashMap<String,String>> d,int list_pos){
        data=d;
        activity=a;
        list_position=list_pos;
        inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        PACKAGE_NAME=activity.getPackageName();
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi =convertView;
        switch (list_position){
            case 1:
                if (convertView==null) {
                    vi=inflater.inflate(lv_employee,null);
                    TextView tvEmployeeName = vi.findViewById(R.id.tvEmployeeName);
                    TextView tvEmployeeNumber = vi.findViewById(R.id.tvEmployeeNumber);
                    TextView tvEmployeeAddress = vi.findViewById(R.id.tvEmployeeAddress);
                    TextView tvEmployeeGender = vi.findViewById(R.id.tvEmployeeGender);

                    HashMap<String,String> empList = new HashMap<String, String>();
                    empList = data.get(position);

                    tvEmployeeName.setText(empList.get("employee_name"));
                    tvEmployeeNumber.setText(empList.get("nomor_induk_pegawai"));
                    tvEmployeeAddress.setText(empList.get("address"));
                    tvEmployeeGender.setText(empList.get("gender"));
                    break;
                }
            case 2:
                if (convertView==null){
                    vi=inflater.inflate(lv_kantor,null);
                    TextView tv_name = vi.findViewById(R.id.tv_name);
                    TextView tv_email = vi.findViewById(R.id.tv_email);
                    TextView tv_cellphone = vi.findViewById(R.id.tv_cellphone);
//                    TextView tv_address = vi.findViewById(R.id.tv_address);
//                    TextView tv_desc = vi.findViewById(R.id.tv_desc);

                    HashMap<String,String> empList = new HashMap<String, String>();
                    empList = data.get(0);

                    tv_name.setText(empList.get("office_name"));
                    tv_email.setText(empList.get("email"));
                    tv_cellphone.setText(empList.get("cell_phone"));
//                    tv_address.setText(empList.get("office_address"));
//                    tv_desc.setText(empList.get("office_description"));
                    break;
                }
            default:
                break;
        }
        return vi;
    }
}
