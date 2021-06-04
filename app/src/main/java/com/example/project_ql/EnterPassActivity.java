package com.example.project_ql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterPassActivity extends AppCompatActivity {

    String pass;
    EditText et;
    Button btn;
    int dem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pass);
        getSupportActionBar().hide();
        initView();
        dem=1;
        SharedPreferences setting=getSharedPreferences("PREFS",0);
        pass=setting.getString("password","");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t=et.getText().toString();
                if(dem==3) {
                    openDialogNhapThieu(Gravity.CENTER);
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                            System.exit(0);
                        }
                    },2000);

                }
                if(t.equals(pass)) {
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    dem++;
                    Toast.makeText(EnterPassActivity.this, "Mật khẩu sai",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        et=findViewById(R.id.etPass);
        btn=findViewById(R.id.btnEt);
    }

    //  Dialog
    private void openDialogNhapThieu(int gravity) {
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sai_mat_khau);

        Window window=dialog.getWindow();
        if(window==null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute=window.getAttributes();
        windowAttribute.gravity=gravity;
        window.setAttributes(windowAttribute);

        if(Gravity.BOTTOM==gravity) {
            dialog.setCancelable(true);
        }else {
            dialog.setCancelable(false);
        }
        Button btnok=dialog.findViewById(R.id.btnSaiMK);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
                System.exit(0);
            }
        });
        dialog.show();
    }
}