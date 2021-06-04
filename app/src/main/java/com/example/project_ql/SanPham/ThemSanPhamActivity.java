package com.example.project_ql.SanPham;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project_ql.DataBase.DataBase;
import com.example.project_ql.MainActivity;
import com.example.project_ql.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;

public class ThemSanPhamActivity extends AppCompatActivity {

    Button btnChonAnh;
    ImageView tAnh;
    DataBase db;
    Button btnThemSP;
    EditText tTen,tSoluong,tGianhap,tGiaban,tNhaphanphoi,tGhichu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);
        db=new DataBase(this, "quanlydiennuoc.sqlite",null, 1);
//        db.QueryData("DELETE * FROM tblSanPham");
        db.QueryData("CREATE TABLE IF NOT EXISTS tblSanPham(ma INTEGER PRIMARY KEY AUTOINCREMENT, ten VARCHAR(200), soluong INTEGER, gianhap INTEGER, giaban INTEGER, nhaphanphoi VARCHAR(200), ghichu VARCHAR(200), anh BLOB)");
        initView();

        btnChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
            }
        });

//        Nut them san pham
        btnThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tTen.getText().toString().equals("")||tGianhap.getText().toString().equals("")||tGiaban.getText().toString().equals("")){
                    openDialogNhapThieu(Gravity.CENTER);
                }else {
                    String ten=tTen.getText().toString();
                    int soluong;
                    if(tSoluong.getText().toString().equals("")) {
                        soluong=0;
                    }else soluong=Integer.valueOf(tSoluong.getText().toString());
                    int gianhap=Integer.valueOf(tGianhap.getText().toString());
                    int giaban=Integer.valueOf(tGiaban.getText().toString());
                    String nhaphanphoi=tNhaphanphoi.getText().toString();
                    String ghichu=tGhichu.getText().toString();
                    byte[] anh=chuyenAnhsangByte(tAnh);
                    if(anh==null) {
                        db.INSERT_SP(ten,soluong,gianhap,giaban,nhaphanphoi,ghichu,anhdefault());
                    }else
                        db.INSERT_SP(ten,soluong,gianhap,giaban,nhaphanphoi,ghichu,anh);
                    Intent intent=new Intent(ThemSanPhamActivity.this, MainActivity.class);
                    ThemSanPhamActivity.this.startActivity(intent);
                }
            }
        });

    }

//    Permission yeu cau quyen truy cap tu nguoi dung
    private void requestPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openImagePicker();
            }
            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(ThemSanPhamActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }

    private void openImagePicker() {
        TedBottomPicker.OnImageSelectedListener listener=new TedBottomPicker.OnImageSelectedListener() {
            @Override
            public void onImageSelected(Uri uri) {
                try {
                    Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    tAnh.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        TedBottomPicker tedBottomPicker=new TedBottomPicker.Builder(ThemSanPhamActivity.this).setOnImageSelectedListener(listener).create();
        tedBottomPicker.show(getSupportFragmentManager());
    }

    private void initView() {
        btnChonAnh=findViewById(R.id.btnChonAnh);
        tAnh=findViewById(R.id.tAnh);
        btnThemSP=findViewById(R.id.btnThemSP);
        tAnh=findViewById(R.id.tAnh);
        tTen=findViewById(R.id.tTen);
        tSoluong=findViewById(R.id.tSoluong);
        tGianhap=findViewById(R.id.tGianhap);
        tGiaban=findViewById(R.id.tGiaban);
        tNhaphanphoi=findViewById(R.id.tNhaphanphoi);
        tGhichu=findViewById(R.id.tGhichu);
    }
//Chuyen ImageView sangbitmap
    byte[] chuyenAnhsangByte(ImageView h) {
        try {
            BitmapDrawable drawable=(BitmapDrawable) h.getDrawable();
            Bitmap bmp=drawable.getBitmap();

            ByteArrayOutputStream stream=new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
            byte[] byteArray=stream.toByteArray();
            return byteArray;
        }catch (Exception e){

        }
        return null;
    }
//    Anh mac dinh
    byte[] anhdefault() {
        Drawable d= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            d = getDrawable(R.drawable.default_sp);
        }
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();
        return bitmapdata;
    }
//  Dialog
    private void openDialogNhapThieu(int gravity) {
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_nhap_thieu);

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
        Button btnok=dialog.findViewById(R.id.btnOk);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}