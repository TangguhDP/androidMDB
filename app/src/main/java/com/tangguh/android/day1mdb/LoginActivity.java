package com.tangguh.android.day1mdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;


public class LoginActivity extends AppCompatActivity {

    String var_username,var_password;
    private RestProcess rest_Process;
    ArrayList<HashMap<String,String>> arrayLogin = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText edtUsername = findViewById(R.id.etUsername);
        final EditText edtPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView fPassword = findViewById(R.id.fPassword);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                var_username = edtUsername.getText().toString();
                var_password = edtPassword.getText().toString();

                if (var_username.isEmpty()) {
                    Toast.makeText(LoginActivity.this,"Username mohon diisi",Toast.LENGTH_LONG).show();
                }else if (var_password.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Password mohon diisi",Toast.LENGTH_LONG).show();
                }else if (var_password.length()<4){
                    Toast.makeText(LoginActivity.this,"Password kurang dari 4",Toast.LENGTH_LONG).show();
                }else{
                    loginProcess(v);
                }
            }
        });
        fPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fPassword = new Intent(LoginActivity.this,ForgotPass.class);
                startActivity(fPassword);
            }
        });
    }

    private void loginProcess(final View view){
        rest_Process = new RestProcess();
        HashMap<String,String> apiData = rest_Process.apiSettingLocal();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String base_url;

        base_url = apiData.get("str_ws_addr")+"/auth";
        params.put("var_cell_phone", var_username);
        params.put("var_password", var_password);

        client.setBasicAuth(apiData.get("str_ws_user"),apiData.get("str_ws_pass"));
        client.post(base_url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String resp_content = "";
                try {
                    resp_content = new String(responseBody,"UTF-8");
                } catch (UnsupportedEncodingException e){
                    e.printStackTrace();;
                } try {
                    displayLogin(view, resp_content);
                }catch (Throwable t){
                    Toast.makeText(LoginActivity.this, "Login Gagal!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(LoginActivity.this, "Credentials wrong",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void displayLogin(View view, String resp_content){
        try {
            arrayLogin = rest_Process.getJsonData(resp_content);
            if (arrayLogin.get(0).get("var_result").equals("1")){
                Intent main_intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(main_intent);
                finish();
            } else if (arrayLogin.get(0).get("var_result").equals("0")){
                Toast.makeText(LoginActivity.this, "Koneksi Gagal!",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e){
                Toast.makeText(LoginActivity.this, "Koneksi Gagal!",Toast.LENGTH_LONG).show();
        }
    }
}
