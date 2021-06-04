package com.example.project_ql.SanPham;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project_ql.DataBase.DataBase;
import com.example.project_ql.MainActivity;
import com.example.project_ql.R;
import com.example.project_ql.SanPham.ChiTietSanPhamActivity;

public class SuaSanPhamActivity extends AppCompatActivity {

    TextView sten;
    EditText snhap,sban,ssl;
    Button btnLuu, btnVe;
    int ma;
    DataBase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_san_pham);
        initView();
        database=new DataBase(this, "quanlydiennuoc.sqlite",null, 1);
        Intent intent=this.getIntent();
        ma=intent.getIntExtra("ma",0);
        Cursor dataSp=database.GetData("SELECT * FROM tblSanPham WHERE ma="+ma+";");
        while (dataSp.moveToNext()) {
            sten.setText(dataSp.getString(1));
            ssl.setText(dataSp.getString(2));
            snhap.setText(dataSp.getString(3));
            sban.setText(dataSp.getString(4));
        }
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(snhap.getText().toString().equals("")||sban.getText().toString().equals("")||ssl.getText().toString().equals("")) {

                }else {
                    int soluong=Integer.valueOf(ssl.getText().toString());
                    int giaban=Integer.valueOf(sban.getText().toString());
                    int gianhap=Integer.valueOf(snhap.getText().toString());
                    database.QueryData("UPDATE tblSanPham SET gianhap="+gianhap+",giaban="+giaban+",soluong="+soluong+" WHERE ma="+ma+";");
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
        btnVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    private void initView() {
        sten=findViewById(R.id.sTen);
        snhap=findViewById(R.id.sGianhap);
        sban=findViewById(R.id.sGiaban);
        ssl=findViewById(R.id.sSoluong);
        btnLuu=findViewById(R.id.btnsLuu);
        btnVe=findViewById(R.id.btnsVe);
    }

}