package com.example.android.customerapp;


import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Post extends AsyncTask<String,String,String> {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public AsyncResponse asyncResponse;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        RequestBody body = RequestBody.create(JSON, params[1]);
        Request request = new Request.Builder()
                .url(params[0])
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String responsebody=response.body().string();
            Log.e("body",responsebody);
            return responsebody;
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public void setOnAsyncResponse(AsyncResponse asyncResponse)
    {
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null)
        {
            Gson gson = new Gson();
            ResponseData data = gson.fromJson(result, ResponseData.class);
            asyncResponse.onDataReceivedSuccess(data);
        }
        else {
            asyncResponse.onDataReceivedFailed();
        }
    }

    public interface AsyncResponse {
        void onDataReceivedSuccess(ResponseData data);
        void onDataReceivedFailed();
    }

}
