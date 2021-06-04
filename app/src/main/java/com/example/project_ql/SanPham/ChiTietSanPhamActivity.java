package com.example.project_ql.SanPham;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_ql.DataBase.DataBase;
import com.example.project_ql.MainActivity;
import com.example.project_ql.R;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    int ma;
    TextView ctTen;
    TextView ctSoluong,ctGianhap,ctGiaban,ctNhaphanphoi,ctGhichu;
    ImageView ctAnh;
    Button btnCtSua,btnCtXoa;
    DataBase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        initView();
//        DB
        database=new DataBase(this, "quanlydiennuoc.sqlite",null, 1);


        Intent intent=this.getIntent();
        ma=intent.getIntExtra("ma",0);
        Cursor dataSp=database.GetData("SELECT * FROM tblSanPham WHERE ma="+ma+";");
        while (dataSp.moveToNext()) {
            int gianhap=dataSp.getInt(3);
            int giaban=dataSp.getInt(4);

            ctTen.setText(dataSp.getString(1));
            ctSoluong.setText(dataSp.getString(2));
            ctGianhap.setText(chuanTien(gianhap));
            ctGiaban.setText(chuanTien(giaban));
            ctNhaphanphoi.setText(dataSp.getString(5));
            ctGhichu.setText(dataSp.getString(6));

            Bitmap bitmap= BitmapFactory.decodeByteArray(dataSp.getBlob(7),0,dataSp.getBlob(7).length);
            ctAnh.setImageBitmap(bitmap);
        }

        btnCtXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogYesNo(Gravity.CENTER);
            }
        });
        btnCtSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(ChiTietSanPhamActivity.this, SuaSanPhamActivity.class);
                intent1.putExtra("ma",ma);
                ChiTietSanPhamActivity.this.startActivity(intent1);
            }
        });

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

    private void initView() {
        ctTen=findViewById(R.id.ctTen);
        ctSoluong=findViewById(R.id.ctSoluong);
        ctGianhap=findViewById(R.id.ctGianhap);
        ctGiaban= findViewById(R.id.ctGiaban);
        ctNhaphanphoi=findViewById(R.id.ctNhaphanphoi);
        ctGhichu=findViewById(R.id.ctGhichu);
        ctAnh=findViewById(R.id.ctAnh);
        btnCtSua=findViewById(R.id.btnCtSua);
        btnCtXoa=findViewById(R.id.btnCtXoa);
    }
    //  Dialog
    private void openDialogYesNo(int gravity) {
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_yes_no);

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

        Button btnYes=dialog.findViewById(R.id.btnYes);
        Button btnNo=dialog.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.QueryData("DELETE FROM tblSanPham WHERE ma="+ma+";");
                Intent intent=new Intent(ChiTietSanPhamActivity.this, MainActivity.class);
                ChiTietSanPhamActivity.this.startActivity(intent);
            }
        });

        dialog.show();
    }

}