package com.example.android.customerapp.viewmodels;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.customerapp.models.OrderItem;
import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.requests.PhotoAPIClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.customerapp.BR.recipe;

public class OrderViewModel extends ViewModel {
    public MediatorLiveData<List<OrderItem>> orderItemList;

    public OrderViewModel() {
        orderItemList = new MediatorLiveData<>();
    }
    public void getPhoto(OrderItem[] orderItems) {
        List<OrderItem> items = Arrays.asList(orderItems);
        for (OrderItem item : orderItems) {
            if (item.getRecipeImage().contains("http")) {
                String name = item.getRecipeImage().substring(item.getRecipeImage().lastIndexOf("/") + 1);
                PhotoAPIClient.getInstance().getPhoto(name).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.body() != null) {
                                byte[] bytes;
                                bytes = response.body().bytes();
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inSampleSize = 4;
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                                OrderItem itemWithPhoto = item;
                                item.setBitmap(bitmap);
                                items.set(items.indexOf(item),itemWithPhoto);
                                orderItemList.setValue(items);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("PHOTO", t.getMessage());
                    }
                });
            }

        }

    }

}
