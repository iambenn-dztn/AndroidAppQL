package com.example.project_ql;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.example.project_ql.DataBase.DataBase;
import com.example.project_ql.model.SanPham;

public class ThongKeHangActivity extends AppCompatActivity {
    TextView tv;
    DataBase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_hang);
        tv=findViewById(R.id.tongtienhang);
        database=new DataBase(this, "quanlydiennuoc.sqlite",null, 1);
        int tong=0;
        Cursor dataSp=database.GetData("SELECT * FROM tblSanPham");
        while (dataSp.moveToNext()) {
            int soluong=dataSp.getInt(2);
            int gianhap=dataSp.getInt(3);
            tong+=soluong*gianhap;
        }
        tv.setText(chuanTien(tong));
    }

    String chuanTien(int n) {
        String t=String.valueOf(n);
        String kq="";
        String kq1="";
        int j=0;
        for(int i=t.length()-1;i>=0;i--) {
            j++;
            kq=kq+t.charAt(i);
            if(j%3==0 && j!=t.length()) kq+=".";
        }
        for(int i=kq.length()-1;i>=0;i--) {
            kq1+=kq.charAt(i);
        }
        return kq1;
    }
}