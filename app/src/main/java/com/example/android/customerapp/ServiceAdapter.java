package com.example.android.customerapp;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {
    private Context context;
    public ServiceViewHolder holder;
    public TextView chatText;
    public ArrayList<String> chatlist;
    public ServiceAdapter(Context context,ArrayList<String> list) {
        this.context = context;
        this.chatlist=list;
        Log.e("0=",chatlist.get(0));
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
            chatText=(TextView) itemView.findViewById(R.id.chat_text);
        }

    }
    @Override
    public void onBindViewHolder(final ServiceViewHolder holder, final int position) {

        this.holder=holder;
        chatText.setText(chatlist.get(position));
    }
    @Override
    public int getItemCount() {
        return chatlist.size();
    }
}