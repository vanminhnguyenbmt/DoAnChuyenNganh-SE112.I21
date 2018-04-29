package com.bin.lazada.ConnectInternet;

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
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadJSON extends AsyncTask<String,Void,String> {

    String duongdan;
    List<HashMap<String, String>> attrs;
    StringBuilder dulieu;
    boolean method = true;

    public DownloadJSON(String duongdan)
    {
        this.duongdan = duongdan;
        method = true;
    }

    public DownloadJSON(String duongdan, List<HashMap<String, String>> attrs)
    {
        this.duongdan = duongdan;
        this.attrs = attrs;
        method = false;
    }
    @Override
    protected String doInBackground(String... strings) {
        String data = "";
        try {
            //Tạo connection tới đường dẫn
            URL url = new URL(duongdan);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            if(!method){
                data = methodPost(httpURLConnection);
            }else {
                data = methodGet(httpURLConnection);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    //Tạo phương thức get
    private String methodGet(HttpURLConnection httpURLConnection)
    {
        String data = "";
        InputStream inputStream = null;
        try {
            httpURLConnection.connect();

            //tạo luồn để đọc dữ liệu
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);

            //ghi dữ liệu
            dulieu = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) !=null)
            {
                dulieu.append(line);
            }
            data = dulieu.toString();

            //đóng luồng
            bufferedReader.close();
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private String methodPost(HttpURLConnection httpURLConnection)
    {
        String data = "";
        String key = "";
        String value = "";
        try {
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            Uri.Builder builder = new Uri.Builder();

            //khai báo biến count để nó chỉ đếm 1 lần, nếu khai báo trong for thì mỗi vòng lặp nó phải đếm lại
            int count = attrs.size();
            for (int i = 0; i < count; i++)
            {
                //vòng lặp for này để lấy ra key và value tương ứng
                for(Map.Entry<String, String> values : attrs.get(i).entrySet())
                {
                     key = values.getKey();
                     value = values.getValue();
                }

                builder.appendQueryParameter(key, value);
            }

            String query = builder.build().getEncodedQuery();

            //mở luồng để ghi dữ liệu
            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);

            writer.write(query);
            writer.flush();
            writer.close();
            outputStreamWriter.close();
            outputStream.close();

            data = methodGet(httpURLConnection);

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
