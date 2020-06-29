package com.example.musicandroid.nav;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.musicandroid.MainActivity;
import com.example.musicandroid.R;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {


    private String sex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }



    public void initView(){


        final Button birthBtn = findViewById(R.id.reg_birth);
        Button register = findViewById(R.id.reg_register);
        Button unregister = findViewById(R.id.reg_cancel);

        final EditText userName = findViewById(R.id.reg_name);
        final EditText password = findViewById(R.id.reg_password);
        final EditText phone = findViewById(R.id.reg_phone);
        final EditText email = findViewById(R.id.reg_email);
        final EditText singnature = findViewById(R.id.reg_signature);
        final EditText area = findViewById(R.id.reg_area);


        RadioGroup radioGroup = findViewById(R.id.reg_radio_group);
        RadioButton female = findViewById(R.id.reg_female);
        RadioButton male = findViewById(R.id.reg_male);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // int i 是id
                if (i == R.id.reg_female){
                    sex = "female";
                }else
                    sex = "male";

            }
        });


        //弹出日历DatePickerDialog   选择生日
        birthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DATE);

                new DatePickerDialog(RegisterActivity.this,R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        i1 ++;
                        String alarm_date=i+"年"+i1 +"月"+i2+"日";

                        birthBtn.setText(alarm_date);
                    }
                },year,month,day).show();

            }
        });


        //取消注册
        unregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //确定注册，存储数据到数据库
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String saveName = userName.getText().toString();
                String savePassword = password.getText().toString();
                String savePhone = phone.getText().toString();
                String saveEmail = email.getText().toString();
                String saveBirthday = (String) birthBtn.getText();
                String saveSignature = singnature.getText().toString();
                String saveArea = area.getText().toString();
                String saveSex = sex;
                //TODO 存储数据到数据库



                finish();
                Intent intent=new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
