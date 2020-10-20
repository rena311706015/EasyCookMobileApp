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
import java.util.HashMap;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {
    private Context context;
    public ServiceViewHolder holder;
    private ArrayList<String> botchatlist;
    TextView chatText;
    public ServiceAdapter(Context context) {
        this.context = context;

    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chat_list, parent, false);
        botchatlist= new ArrayList<>();
        botchatlist.add("歡迎初次來到廚yee娘的世界，我將為你介紹一些便利的功能。");
        botchatlist.add("我們提供的食譜導覽服務，可以讓你輕鬆透過「對話」的方式知道做菜步驟，所以想邀請你開啟麥克風的權限喔！");
        for(String s: botchatlist){
            Log.e("content:",s);
        }

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
        Log.d("position",Integer.toString(position));
        Log.d("number",botchatlist.get(position));

        chatText.setText(botchatlist.get(position));
    }
    @Override
    public int getItemCount() {
        return 10;
    }
}
