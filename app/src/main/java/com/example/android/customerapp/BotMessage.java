package com.example.android.customerapp;



public class BotMessage {
    public int id;
    public String message;
    public BotMessage(int id, String message){
        this.id=id;
        this.message=message;
    }
    public int getId(){
        return this.id;
    }
    public String getMessage(){
        return this.message;
    }
    public void setId(int id){
        this.id=id;
    }
    public void setMessage(String message){
        this.message=message;
    }

}
