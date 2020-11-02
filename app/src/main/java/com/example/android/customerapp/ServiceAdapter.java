package com.example.android.customerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashMap;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {
    private Context context;
    public ServiceViewHolder holder;
    public TextView chatText;
    public ImageView image1;
    public ImageView image2;
    public HashMap<Integer,String> chatListMap;
    public ServiceAdapter(Context context, HashMap<Integer,String> map) {
        this.context = context;
        this.chatListMap=map;
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.message, parent, false);
        return new ServiceViewHolder(view);
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder {
        public ServiceViewHolder(View itemView) {
            super(itemView);
            chatText=itemView.findViewById(R.id.chat_text);
            image1=itemView.findViewById(R.id.chat_image);
            image2=itemView.findViewById(R.id.chat_image2);
        }

    }
    @Override
    public void onBindViewHolder(final ServiceViewHolder holder,final int position) {
        if(position%2!=0){
            image1.setVisibility(View.GONE);
            image2.setVisibility(View.VISIBLE);
        }
        chatText.setText(chatListMap.get(position));
        chatText.bringToFront();  //強制移到最上層
    }
    @Override
    public int getItemCount() {
        return chatListMap.size();
    }
}