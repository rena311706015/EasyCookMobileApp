package com.example.android.customerapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.customerapp.models.Member;
import com.example.android.customerapp.requests.BackendAPIClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    public static MediatorLiveData<String> token;

    public LoginViewModel() {
        token = new MediatorLiveData<>();
    }

    public void memberLogin(Member member) {
        BackendAPIClient.getInstance().memberLogin(member).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code()==200){
                    Log.e("Login",response.body().get("token").getAsString());
                    token.setValue(response.body().get("token").getAsString());
                }else{
                    Log.e("Login","Fail "+response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("API", t.getMessage());
            }
        });
    }

}
