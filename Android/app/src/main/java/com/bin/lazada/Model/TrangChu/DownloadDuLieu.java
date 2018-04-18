package com.bin.lazada.Model.TrangChu;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadDuLieu extends AsyncTask<String,Void,String> {
    StringBuilder dulieu;
    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            //connect dạng get
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.connect();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            //mở luồng outputstream và inputsteam
            connection.setDoOutput(true);
            connection.setDoInput(true);

            Uri.Builder uri = new Uri.Builder();
            uri.appendQueryParameter("maloaicha", strings[1]);
            String dulieupost = uri.build().getEncodedQuery();

            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);

            writer.write(dulieupost);
            writer.flush();
            writer.close();

            outputStreamWriter.close();
            outputStream.close();
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line  = "";
            dulieu = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                dulieu.append(line);
            }
            Log.d("kiemtra", dulieu.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dulieu.toString();
    }
}
