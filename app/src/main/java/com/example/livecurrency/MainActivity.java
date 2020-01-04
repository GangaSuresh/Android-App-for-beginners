package com.example.livecurrency;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    double inr=0,dol=0,aed=0,euro=0;
    private static final String api_url= "https://openexchangerates.org/api/latest.json?app_id=832a326e3a5b49e0aa12cf3abfa693c1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText et1=findViewById(R.id.e1);
        final EditText et2=findViewById(R.id.e2);
        Button bt1=findViewById(R.id.b1);
        Button bt2=findViewById(R.id.b2);
        Button bt3=findViewById(R.id.b3);
        new GetWebpageTask().execute(api_url);






        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String content = et1.getText().toString();
                int a = Integer.parseInt(content);

                et2.setText(String.valueOf(a/dol));
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String content = et1.getText().toString();
                int a = Integer.parseInt(content);
                double t_dol=a/dol;
                et2.setText(String.valueOf(t_dol*aed));
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String content = et1.getText().toString();
                int a = Integer.parseInt(content);
                double  t_dol=a/dol;
                et2.setText(String.valueOf(t_dol*euro));
            }
        });



    }
    private String getWebsite(String adress){
        StringBuffer stringBuffer =new StringBuffer();
        BufferedReader reader=null;
        try {
            URL url = new URL(adress);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try{
                    reader.close();

                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return stringBuffer.toString();
    }

    public class GetWebpageTask extends AsyncTask<String,Void,String> {
        @Override
        protected void onPostExecute(String result){
            Log.i("chasing","sucess");
            try {
                JSONObject jsonObj=new JSONObject(result);
                JSONObject ratesObject=jsonObj.getJSONObject("rates");
                dol=ratesObject.getDouble("INR");
                euro=ratesObject.getDouble("EUR");
                aed=ratesObject.getDouble("AED");

            }catch (JSONException e){
                e.printStackTrace();
            }

            super.onPostExecute(result);
        }
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String...url){

           return getWebsite(url[0]);

        }


    }


        }



//    AsyncHttpClient client = new AsyncHttpClient();
//                client.get(api_url, new AsyncHttpResponseHandler() {
//
//@Override
//public void onStart() {
//        // called before request is started
//        super.onStart();
//        }
//
//@Override
//public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//        JSONArray arr= null;
//        try {
//        arr = new JSONArray(new String(responseBody));
//        } catch (JSONException e) {
//        e.printStackTrace();
//        }
//
//        //Toast.makeText(getApplicationContext(), "Yessss: "+arr.toString(), Toast.LENGTH_SHORT).show();
//        //Log.i("chasing",arr.toString());
//        System.out.println(arr.toString());
//
//        }
//
//@Override
//public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//        super.onFailure(statusCode, headers, responseBody, error);
//
//        }
//
//
//        });


