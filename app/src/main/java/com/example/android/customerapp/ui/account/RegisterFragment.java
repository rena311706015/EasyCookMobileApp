
package com.example.android.customerapp.ui.account;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.customerapp.R;
import com.example.android.customerapp.models.Member;
import com.example.android.customerapp.viewmodels.AccountViewModel;

public class RegisterFragment extends Fragment {

    private AccountViewModel accountViewModel;
    private Button registerButton;
    private EditText account,password,phone,email,name;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        accountViewModel =
                ViewModelProviders.of(this).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account_register, container, false);
        account=root.findViewById(R.id.registerAccount);
        password=root.findViewById(R.id.registerPassword);
        phone=root.findViewById(R.id.registerPhone);
        email=root.findViewById(R.id.registerEmail);
        name=root.findViewById(R.id.registerName);
        registerButton=root.findViewById(R.id.register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String json = BuildJson(account.getText().toString(),password.getText().toString(),phone.getText().toString(),email.getText().toString(),name.getText().toString());
//                POST post = new POST();
//                post.execute("http://140.118.9.145:8082/member/register",json);
//                post.setOnAsyncResponse(new POST.AsyncResponse() {
//                    @Override
//                    public void onDataReceivedSuccess(Member data) {
//                        Log.e("註冊成功","id:"+data.id);
//                    }
//                    @Override
//                    public void onDataReceivedFailed() {
//                        Log.e("註冊失敗","fail");
//                    }
//                });
            }
        });
        return root;

    }
    public String BuildJson(String account,String password, String phone, String email, String name){
        return "{"+"\"account\":\""+account+"\","
                + "\"password\":\""+password+"\""+","
                + "\"phone\""+":"+"\""+phone+"\""+","
                + "\"email\""+":"+"\""+email+"\""+","
                + "\"username\""+":"+"\""+name+"\""
                + "}";
    }

}