package com.tangguh.android.day1mdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        final EditText etNewPass = findViewById(R.id.etNewPass);
        final EditText etRepeat = findViewById(R.id.etRepeat);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String var_newPass = etNewPass.getText().toString();
                String var_repeat = etRepeat.getText().toString();

                if (!(var_newPass.matches(var_repeat))){
                    Toast.makeText(ForgotPass.this,"Password baru tidak sama",Toast.LENGTH_LONG).show();
                } else if (var_newPass.isEmpty()&&var_repeat.isEmpty()){
                    Toast.makeText(ForgotPass.this,"Password tidak boleh kosong",Toast.LENGTH_LONG).show();
                } else {
                    Intent chngPass = new Intent(ForgotPass.this, MainActivity.class);
                    startActivity(chngPass);
                }
            }
        });
    }
}
