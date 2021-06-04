package com.example.project_ql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreatPassActivity extends AppCompatActivity {

    EditText et1,et2;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_pass);
        initView();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t1=et1.getText().toString();
                String t2=et2.getText().toString();
                if(t1.equals("") || t2.equals("")) {
                    Toast.makeText(CreatPassActivity.this, "Phải nhập đủ mật khẩu.",Toast.LENGTH_SHORT).show();
                }else {
                    if(t1.equals(t2)) {
                        SharedPreferences setting=getSharedPreferences("PREFS",0);
                        SharedPreferences.Editor editor=setting.edit();
                        editor.putString("password", t1);
                        editor.apply();
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(CreatPassActivity.this, "Mật khẩu không khớp",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void initView() {
        btn=findViewById(R.id.btnCrPas);
        et1=findViewById(R.id.crPass);
        et2=findViewById(R.id.crRePass);
    }
}