package com.tangguh.android.day1mdb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class KantorActivity extends AppCompatActivity {
    String var_username,var_password;
    private RestProcess restProcess;
    ListView lvDataKantor;
    ListAdapter adapter;
    ArrayList<HashMap<String,String>> dataKantor_arrayList = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kantor);
        restProcess = new RestProcess();
        lvDataKantor = findViewById(R.id.v_kantor);
        getDataKantor(null);

    }
    private void getDataKantor(final View view){
        HashMap<String,String> apiData = restProcess.apiSettingLocal();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String base_url;

        base_url = apiData.get("str_ws_addr")+"/office";
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
                    displayDataKantor(view, resp_content);
                }catch (Throwable t){
                    Toast.makeText(KantorActivity.this, "Koneksi Gagal1 !",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(KantorActivity.this, "Koneksi Gagal2 !",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void displayDataKantor(View view, String resp_content){
        try {
            dataKantor_arrayList = restProcess.getJsonData(resp_content);
            if (dataKantor_arrayList.get(0).get("var_result").equals("1")){
                dataKantor_arrayList.remove(0);
                adapter = new ListAdapter(KantorActivity.this, dataKantor_arrayList,2);
                lvDataKantor.setAdapter(adapter);
                Toast.makeText(KantorActivity.this,"Koneksi Berhasil!",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(KantorActivity.this,"Koneksi Gagal3 !",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e){
            Toast.makeText(KantorActivity.this, "Koneksi Gagal4 !",Toast.LENGTH_LONG).show();
        }
    }
}
