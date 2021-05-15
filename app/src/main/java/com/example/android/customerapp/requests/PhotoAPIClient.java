package com.example.android.customerapp.requests;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class PhotoAPIClient {

    private static final String TAG = "PhotoApiClient";

    private static PhotoAPIClient instance;
    private PhotoAPI photoAPI;

    public static PhotoAPIClient getInstance() {
        if (instance == null) {
            instance = new PhotoAPIClient();
        }
        return instance;
    }

    private PhotoAPIClient() {
        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://easycook-backend.s3.amazonaws.com/recipe/")
                .build();

        photoAPI = retrofit.create(PhotoAPI.class);

    }

    public Call<ResponseBody> getPhoto(String name) {
        return photoAPI.getPhoto(name);
    }
}
