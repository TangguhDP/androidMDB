package com.tangguh.android.day1mdb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailKantorActivity extends AppCompatActivity {
    TextView tvName, tvEmail, tvCellphone, tvAddress, tvDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kantor);
        tvName = findViewById(R.id.tv_name);
        tvEmail = findViewById(R.id.tv_email);
        tvCellphone = findViewById(R.id.tv_cellphone);
        tvAddress = findViewById(R.id.tv_address);
        tvDesc = findViewById(R.id.tv_desc);
    }
}
