package com.tangguh.android.day1mdb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class AboutActivity extends AppCompatActivity {

    private RestProcess restProcess;
    private WebView wvAbout;
    ArrayList<HashMap<String,String>> dataAbout_arrayList = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        restProcess = new RestProcess();
        wvAbout = findViewById(R.id.wv_about);
        getAbout(null);
    }

    private void getAbout(final View view){
        HashMap<String,String> apiData = new HashMap<String, String>();
        apiData = restProcess.apiSettingLocal();
        AsyncHttpClient client = new AsyncHttpClient();
        String base_url;
        base_url = apiData.get("str_ws_addr")+"/about_apps";
        client.setBasicAuth(apiData.get("str_ws_user"),apiData.get("str_ws_pass"));
        client.post(base_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String resp_content = "";
                try {
                    resp_content = new String(responseBody,"UTF-8");
                } catch (UnsupportedEncodingException e){
                    e.printStackTrace();;
                } try {
                    displayAbout(view, resp_content);
                }catch (Throwable t){
                    Toast.makeText(AboutActivity.this, "Koneksi Gagal1 !",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(AboutActivity.this, "Koneksi Gagal2 !",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void displayAbout(View view, String resp_content){
        try {
            dataAbout_arrayList = restProcess.getJsonData(resp_content);
            if (dataAbout_arrayList.get(0).get("var_result").equals("1")){
                String strHtml = dataAbout_arrayList.get(1).get("about_apps");
                wvAbout.loadData(strHtml,"text/html",null);
                Toast.makeText(AboutActivity.this,"Koneksi Berhasil!",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(AboutActivity.this,"Koneksi Gagal3 !",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e){
            Toast.makeText(AboutActivity.this, "Koneksi Gagal4 !",Toast.LENGTH_LONG).show();
        }
    }
}
