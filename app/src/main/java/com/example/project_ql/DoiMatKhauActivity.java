package com.example.project_ql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DoiMatKhauActivity extends AppCompatActivity {

    EditText et1,et2,et3;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        initView();

        SharedPreferences setting=getSharedPreferences("PREFS",0);
        String pass=setting.getString("password","");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1=et1.getText().toString();
                String s2=et2.getText().toString();
                String s3=et3.getText().toString();
                if(s1.equals(pass)) {
                    if(s2.equals("")||s3.equals("")) {
                        Toast.makeText(DoiMatKhauActivity.this,"Bạn chưa nhập đủ mật khẩu mới",Toast.LENGTH_SHORT).show();
                    }else if(s3.equals(s2)) {
                        SharedPreferences setting=getSharedPreferences("PREFS",0);
                        SharedPreferences.Editor editor=setting.edit();
                        editor.putString("password", s2);
                        editor.apply();
                        Toast.makeText(DoiMatKhauActivity.this,"Thay đổi mật khẩu thành công ♥",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(DoiMatKhauActivity.this,"Mật khẩu mới không khớp.",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(DoiMatKhauActivity.this,"Sai mật khẩu.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initView() {
        et1=findViewById(R.id.doimk1);
        et2=findViewById(R.id.doimk2);
        et3=findViewById(R.id.doimk3);
        btn=findViewById(R.id.btndoimk);
    }
}