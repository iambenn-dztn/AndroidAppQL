package com.example.project_ql.DonNhap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_ql.DataBase.DataBase;
import com.example.project_ql.DonXuat.DonXuatActivity;
import com.example.project_ql.MainActivity;
import com.example.project_ql.R;
import com.example.project_ql.model.SanPham;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DonNhapActivity extends AppCompatActivity {

    DataBase database;
    RecyclerView rcvDonNhap;
    Button btnTinh,btnLuu;
    TextView tvTongTien,tvNhapGhiChu;
    EditText etNhapKH, etDaTra, etConNo;
    DonNhapAdapter donNhapAdapter;
    String[] arr=new String[200];
    String masp;
    String sl;
    int giaInt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_nhap);
        initView();
        rcvDonNhap.setHasFixedSize(true);
        rcvDonNhap.setLayoutManager(new LinearLayoutManager(this));
//        Hien thi danh sach sach cac san pham da chon
        Intent intent=getIntent();
        String st=intent.getStringExtra("stt");
        arr=st.split("%");
//set rcv danh sach san pham da chon
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(DonNhapActivity.this);
        rcvDonNhap.setLayoutManager(linearLayoutManager);

        donNhapAdapter=new DonNhapAdapter(getList());
        rcvDonNhap.setAdapter(donNhapAdapter);
        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(DonNhapActivity.this, DividerItemDecoration.VERTICAL);
        rcvDonNhap.addItemDecoration(itemDecoration);
//Database
        database=new DataBase(DonNhapActivity.this, "quanlydiennuoc.sqlite",null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS tblDonNhap(ma INTEGER PRIMARY KEY AUTOINCREMENT, tennsx VARCHAR(200), tensp VARCHAR(400), soluong VARCHAR(400), tongtien INTEGER, datra INTEGER, conno INTEGER, ngay VARCHAR(200), ghichu VARCHAR(200))");
//       Event button Luu
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etNhapKH.getText().toString().equals("")) {
                    Toast.makeText(DonNhapActivity.this, "Bạn chưa nhập tên nhà sản xuất.", Toast.LENGTH_SHORT).show();
                } else {
//          Set cac thuoc tinh cho doi tuong don nhap va INSERT vao tblDonNhap
                    String tennsx = etNhapKH.getText().toString();
                    String tensp = masp;
                    String soluong = sl;
                    int tongtien = chuanSo(tvTongTien.getText().toString());
                    int datra = chuanSo(etDaTra.getText().toString());
                    int conno = chuanSo(etConNo.getText().toString());
                    String ngay = "";
                    Date currentTime = Calendar.getInstance().getTime();
                    int y = currentTime.getYear() - 100;
                    int m = currentTime.getMonth() + 1;
                    int d = currentTime.getDate();
                    String ghichu = tvNhapGhiChu.getText().toString();
                    ngay += String.valueOf(d) + "/" + String.valueOf(m) + "/" + String.valueOf(y);
                    database.QueryData("INSERT INTO tblDonNhap VALUES(null,'" + tennsx + "','" + tensp + "','" + soluong + "'," + tongtien + "," + datra + "," + conno + ",'" + ngay + "','" + ghichu + "')");
//Them so luong san pham trong danh sach cho san pham vua nhap
                    String[] arrtensp = tensp.split("%");
                    String[] arrsl = soluong.split("%");
                    for (int i = 0; i < arrsl.length; i++) {
                        Cursor dataSp = database.GetData("SELECT * FROM tblSanPham WHERE ma=" + arrtensp[i] + ";");
                        while (dataSp.moveToNext()) {
                            database.QueryData("UPDATE tblSanPham SET soluong=" + (dataSp.getInt(2) + Integer.valueOf(arrsl[i])) + " WHERE ma=" + dataSp.getInt(0) + ";");
                        }
                    }
//                Thêm nợ cho nsx nếu nợ
                    if(conno!=0) {
                        Cursor dataNsx = database.GetData("SELECT * FROM tblNhaSanXuat;");
                        boolean kiemtra = false;
                        while (dataNsx.moveToNext()) {
                            if (dataNsx.getString(1).toLowerCase().equals(tennsx.toLowerCase())) {
                                database.QueryData("UPDATE tblNhaSanXuat SET conno=" + (dataNsx.getInt(2) + chuanSo(etConNo.getText().toString())) + " WHERE ma=" + dataNsx.getInt(0) + ";");
                                kiemtra = true;
                                break;
                            }
                        }
                        if (kiemtra == false) {
                            database.QueryData("INSERT INTO tblNhaSanXuat VALUES(null,'" + tennsx + "'," + conno + ")");
                        }

                        Intent myIntent = new Intent(DonNhapActivity.this, MainActivity.class);
//                myIntent.putExtra("key", 1); //Optional parameters
                        DonNhapActivity.this.startActivity(myIntent);
                    }else {
                        Intent myIntent = new Intent(DonNhapActivity.this, MainActivity.class);
//                myIntent.putExtra("key", 1); //Optional parameters
                        DonNhapActivity.this.startActivity(myIntent);
                    }

                }
            }
        });

        btnTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int tongtien=0;
                    masp="";
                    sl="";
                    for(int i=0;i<arr.length;i++) {
                        System.out.println(i);
                        SanPham sp=getItem(i);
                        System.out.println(sp.getTen());
                        if(i==arr.length-1) masp=masp+sp.getMa();
                        else masp=masp+sp.getMa()+"%";
//Set so luong item don nhap
                        View view1 = rcvDonNhap.getChildAt(i);
                        EditText soluongV = (EditText) view1.findViewById(R.id.soluongSpDonNhap);
                        String soluong = soluongV.getText().toString();
                        if(i==arr.length-1) sl=sl+soluong;
                        else sl=sl+soluong+"%";
//Set gia cho tung item don nhap
                        View view2 = rcvDonNhap.getChildAt(i);
                        TextView giaDN = (TextView) view2.findViewById(R.id.giaDonNhap);
                        Cursor dataSp=database.GetData("SELECT * FROM tblSanPham WHERE ma="+sp.getMa()+";");
                        while (dataSp.moveToNext()) {
                            int gianhap=dataSp.getInt(3);
                            giaInt=gianhap*Integer.valueOf(soluong);
                            giaDN.setText(chuanTien(giaInt));
                        }
                        tongtien+=sp.getGianhap()*Integer.valueOf(soluong);
                    }
                    try {
                        if(tongtien!=0 && !etDaTra.getText().toString().equals("")) {
                            etConNo.setText(chuanTien(chuanSo(tvTongTien.getText().toString())-Integer.valueOf(etDaTra.getText().toString())));
                            etDaTra.setText(chuanTien(Integer.valueOf(etDaTra.getText().toString())));
                        }else {
                            Toast.makeText(DonNhapActivity.this,"Mời nhập số tiền đã trả trước.",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e) {
                        System.out.println("jhihi");
                        System.out.println(e);
                    }
                    if(!etConNo.getText().toString().equals("")) {
                        btnLuu.setEnabled(true);
                    }else btnLuu.setEnabled(false);
                    tvTongTien.setText(String.valueOf(chuanTien(tongtien)));
                } catch (Exception e) {
                    System.out.println(e);
                    openDialogNhapThieu(Gravity.CENTER);
                }
            }
        });

    }

    private List<SanPham> getList() {
        List<SanPham> list=new ArrayList<>();
        for(int i=0;i<arr.length;i++) {
            database=new DataBase(DonNhapActivity.this, "quanlydiennuoc.sqlite",null, 1);
            Cursor dataSp=database.GetData("SELECT * FROM tblSanPham WHERE ma = "+arr[i]+";");
            while (dataSp.moveToNext()) {
                int ma=Integer.parseInt(dataSp.getString(0));
                String ten=dataSp.getString(1);
                int soluong=dataSp.getInt(2);
                int gianhap=dataSp.getInt(3);
                int giaban=dataSp.getInt(4);
                String nhaphanphoi=dataSp.getString(5);
                String ghichu=dataSp.getString(6);
                byte[] anh=dataSp.getBlob(7);
                list.add(new SanPham(ma,ten,soluong,gianhap,giaban,nhaphanphoi,ghichu,anh));
            }
        }
        return list;
    }

    private void initView() {
        rcvDonNhap=findViewById(R.id.rcv_donnhap);
        btnTinh=findViewById(R.id.btnDonNhapTinh);
        btnLuu=findViewById(R.id.btnDonNhapLuu);
        tvTongTien=findViewById(R.id.tvNhapTong);
        etNhapKH=findViewById(R.id.etNhapKH);
        tvNhapGhiChu=findViewById(R.id.tvNhapGhiChu);
        etDaTra=findViewById(R.id.tvNhapDaTra);
        etConNo=findViewById(R.id.tvNhapConNo);
    }

    public SanPham getItem(int position) {
        return getList().get(position);
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
    int chuanSo(String s) {
        int n=s.charAt(0)-'0';
        for(int i=1;i<s.length();i++) {
            if(s.charAt(i)!='.')
            n=n*10+s.charAt(i)-'0';
        }
        return n;
    }

    //  Dialog
    private void openDialogNhapThieu(int gravity) {
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_nhapthieu_soluong);

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