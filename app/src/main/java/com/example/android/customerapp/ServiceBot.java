package com.example.android.customerapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;

public class ServiceBot extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button confirmButton;
    private Button cancelButton;
    private Button corfirmRecipeButton;
    private Spinner spinner;
    private ServiceAdapter adapter;
    private ArrayList<String> botChatList;
    private String[] recipe;
    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private DatabaseReference dbRef2;
    private HashMap<Integer,String> hashMap;
    private int count=0,index=1;
    private String targetRecipe="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隱藏標題列
        setContentView(R.layout.activity_bot);
        getSupportActionBar().hide(); //隱藏標題列
        botChatList=new ArrayList<>();
        hashMap=new HashMap<>();
        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("messages");
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ServiceBot.this));

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    botChatList.add(ds.child("message").getValue().toString());
                }
                hashMap.put(0,botChatList.get(0));
                adapter = new ServiceAdapter(ServiceBot.this,hashMap);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });
        confirmButton=findViewById(R.id.confirm_button);
        cancelButton=findViewById(R.id.cancel_button);
        corfirmRecipeButton=findViewById(R.id.confirm_recipe_button);
        spinner=findViewById(R.id.spinner);
        dbRef2=db.getReference("recipes");
        dbRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipe=new String[3];
                int x=0;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    recipe[x]=(ds.child("recipeName").getValue().toString());
                    x+=1;
                }
                ArrayAdapter<String> recipeList = new ArrayAdapter<>(ServiceBot.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        recipe);
                spinner.setAdapter(recipeList);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                targetRecipe=recipe[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }
    public void confirm(View v) throws InterruptedException {
        Handler handler = new Handler();
        count+=2;
        if(count==6){
            hashMap.put(count-1,targetRecipe);
            setVisibility();       //隱藏下拉式選單
        }else{
            hashMap.put(count-1,"確定");
        }
        adapter.notifyItemInserted(hashMap.size() - 1);  //先顯示使用者的回答

        handler.postDelayed(new Runnable() {  //延遲訊息出現(像是機器人在想回答，而不是跟著使用者的訊息一起出來)
            public void run() {
                hashMap.put(count,botChatList.get(index)+targetRecipe);
                adapter.notifyItemInserted(hashMap.size() - 1);   //延遲1秒後顯示機器人的回答
                recyclerView.scrollToPosition(adapter.getItemCount()-1);  //滑到最後一個項目

                index+=1;
                if(count==4)setVisibility();  //顯示下拉式選單
            }
        }, 1000);
    }
    public void cancel(View v){
        AlertDialog.Builder a = new AlertDialog.Builder(ServiceBot.this);
        a.setTitle("提醒");
        a.setMessage("確定要退出嗎?");

        a.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int num) {
                Intent intent = new Intent(ServiceBot.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        a.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int num) {}
        });
        a.show();
    }
    public void setVisibility(){        //如果是Visible就轉為Gone，是Gone就轉為Visible
        if(confirmButton.getVisibility()!=View.GONE){
            confirmButton.setVisibility(View.GONE);
            cancelButton.setVisibility(View.GONE);
            corfirmRecipeButton.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);
        }else{
            confirmButton.setVisibility(View.VISIBLE);
            cancelButton.setVisibility(View.VISIBLE);
            corfirmRecipeButton.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
        }
    }
}
