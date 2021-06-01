package com.example.android.customerapp.viewmodels;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.customerapp.models.OrderItem;
import com.example.android.customerapp.requests.BackendAPIClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderViewModel extends ViewModel {
    public MediatorLiveData<List<OrderItem>> mOrderItemList;

    public OrderViewModel() {
        mOrderItemList = new MediatorLiveData<>();
    }

    public void getPhoto(OrderItem[] orderItems) {
        List<OrderItem> orderItemList = Arrays.asList(orderItems);
        for (OrderItem item : orderItemList) {
            if (item.getRecipe().getRecipeImage() != "No Image" && item.getRecipe().getRecipeImage() != null) {
                BackendAPIClient.getInstance().getImage(item.getRecipe().getRecipeImage()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.body() != null) {
                                byte[] bytes;
                                bytes = response.body().bytes();
                                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                OrderItem itemWithPhoto = item;
                                itemWithPhoto.setBitmap(bmp);
                                orderItemList.set(orderItemList.indexOf(item), itemWithPhoto);
                                mOrderItemList.setValue(orderItemList);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("API", t.getMessage());
                    }
                });
            }

        }
    }

//    public void getPhoto(OrderItem[] orderItems) {
//        List<OrderItem> items = Arrays.asList(orderItems);
//        for (OrderItem item : orderItems) {
//            if (item.getRecipeImage().contains("http")) {
//                String name = item.getRecipeImage().substring(item.getRecipeImage().lastIndexOf("/") + 1);
//                PhotoAPIClient.getInstance().getPhoto(name).enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        try {
//                            if (response.body() != null) {
//                                byte[] bytes;
//                                bytes = response.body().bytes();
//                                BitmapFactory.Options options = new BitmapFactory.Options();
//                                options.inSampleSize = 4;
//                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
//                                OrderItem itemWithPhoto = item;
//                                item.setBitmap(bitmap);
//                                items.set(items.indexOf(item),itemWithPhoto);
//                                orderItemList.setValue(items);
//                            }
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Log.e("PHOTO", t.getMessage());
//                    }
//                });
//            }
//
//        }
//
//    }

}
