package com.example.musicandroid.nav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.musicandroid.MainActivity;
import com.example.musicandroid.R;

public class LoginActivity extends AppCompatActivity {

    private EditText accountEdit;
    private EditText passwordEdit;
    private Button loginBtn;
    private Button registerBtn;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }


    public void initView(){
        accountEdit = findViewById(R.id.account);
        passwordEdit = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login);
        registerBtn = findViewById(R.id.register);
        checkBox = findViewById(R.id.remember_password);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account=accountEdit.getText().toString();
                String password=passwordEdit.getText().toString();
//                isValid(account);
//                isValid(password);
                //检查账号和密码是否符合要求，符合要求后去数据库中查找该账号的用户是否存在
                if (isValid(account) && isValid(password)){
                    //TODO  数据库检查,存在则跳转，并设置用户信息；不存在则告诉用户转好或者密码错误，重新编辑

                }


            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }



    // A placeholder username/password validation check
    private boolean isValid(String username) {
       if (username == null) {
           Toast.makeText(LoginActivity.this,"账号/密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (username.trim().length() < 6) {
            Toast.makeText(LoginActivity.this,"账号/密码长度不能小于6",Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


}
