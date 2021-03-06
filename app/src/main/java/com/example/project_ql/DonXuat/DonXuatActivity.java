package com.example.project_ql.DonXuat;

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
import com.example.project_ql.DonNhap.DonNhapActivity;
import com.example.project_ql.DonNhap.DonNhapAdapter;
import com.example.project_ql.MainActivity;
import com.example.project_ql.R;
import com.example.project_ql.model.SanPham;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DonXuatActivity extends AppCompatActivity {

    DataBase database;
    RecyclerView rcvDonXuat;
    Button btnTinh,btnLuu;
    TextView tvTongTien,tvXuatGhiChu;
    EditText etXuatKH, etDaTra, etConNo;
    DonXuatAdapter donXuatAdapter;
    String[] arr=new String[200];
    String masp;
    String sl;
    int giaInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_xuat);
        initView();
//        Hien thi danh sach sach cac san pham da chon
        Intent intent=getIntent();
        String st=intent.getStringExtra("stt");
        arr=st.split("%");
//set rcv danh sach san pham da chon
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(DonXuatActivity.this);
        rcvDonXuat.setLayoutManager(linearLayoutManager);

        donXuatAdapter=new DonXuatAdapter(getList());
        rcvDonXuat.setAdapter(donXuatAdapter);
        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(DonXuatActivity.this, DividerItemDecoration.VERTICAL);
        rcvDonXuat.addItemDecoration(itemDecoration);
//Database
        database=new DataBase(DonXuatActivity.this, "quanlydiennuoc.sqlite",null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS tblDonNhap(ma INTEGER PRIMARY KEY AUTOINCREMENT, tennsx VARCHAR(200), tensp VARCHAR(400), soluong VARCHAR(400), tongtien INTEGER, datra INTEGER, conno INTEGER, ngay VARCHAR(200), ghichu VARCHAR(200))");
//Event button tinh
        btnTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int tongtien=0;
                    masp="";
                    sl="";
                    for(int i=0;i<arr.length;i++) {
                        SanPham sp=getItem(i);
                        if(i==arr.length-1) masp=masp+sp.getMa();
                        else masp=masp+sp.getMa()+"%";
//Set so luong item don nhap
                        View view1 = rcvDonXuat.getChildAt(i);
                        EditText soluongV = (EditText) view1.findViewById(R.id.soluongSpDonXuat);
                        String soluong = soluongV.getText().toString();
                        if(i==arr.length-1) sl=sl+soluong;
                        else sl=sl+soluong+"%";
//Set gia cho tung item don nhap
                        View view2 = rcvDonXuat.getChildAt(i);
                        TextView giaDN = (TextView) view2.findViewById(R.id.giaDonXuat);
                        Cursor dataSp=database.GetData("SELECT * FROM tblSanPham WHERE ma="+sp.getMa()+";");
                        while (dataSp.moveToNext()) {
                            int giaban=dataSp.getInt(4);
                            giaInt=giaban*Integer.valueOf(soluong);
                            giaDN.setText(chuanTien(giaInt));
                        }
                        tongtien+=sp.getGiaban()*Integer.valueOf(soluong);
                    }
                        if(tongtien!=0 && !etDaTra.getText().toString().equals("")) {
                            etDaTra.setText(chuanTien(Integer.valueOf(etDaTra.getText().toString())));
                           etConNo.setText(chuanTien(chuanSo(tvTongTien.getText().toString())-chuanSo(etDaTra.getText().toString())));
                        }else {
                            Toast.makeText(DonXuatActivity.this,"M???i nh???p s??? ti???n ???? tr??? tr?????c.",Toast.LENGTH_SHORT).show();
                        }
                    if(!etConNo.getText().toString().equals("")) {
                        btnLuu.setEnabled(true);
                    }else btnLuu.setEnabled(false);
                    tvTongTien.setText(String.valueOf(chuanTien(tongtien)));
                } catch (Exception e) {
                    openDialogNhapThieu(Gravity.CENTER);
                }
            }
        });
//       Event button Luu
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etXuatKH.getText().toString().equals("")) {
                    Toast.makeText(DonXuatActivity.this,"B???n ch??a nh???p t??n ?????i l??.",Toast.LENGTH_SHORT).show();
                }else {
                    //          Set cac thuoc tinh cho doi tuong don nhap va INSERT vao tblDonNhap
                    String tendaily=etXuatKH.getText().toString();
                    String tensp=masp;
                    String soluong=sl;
                    int tongtien=chuanSo(tvTongTien.getText().toString());
                    int datra=chuanSo(etDaTra.getText().toString());
                    int conno=chuanSo(etConNo.getText().toString());
                    String ngay="";
                    Date currentTime = Calendar.getInstance().getTime();
                    int y=currentTime.getYear()-100;
                    int m=currentTime.getMonth()+1;
                    int d=currentTime.getDate();
                    String ghichu=tvXuatGhiChu.getText().toString();
                    ngay+=String.valueOf(d)+"/"+String.valueOf(m)+"/"+String.valueOf(y);
                    database.QueryData("INSERT INTO tblDonXuat VALUES(null,'"+tendaily+"','"+tensp+"','"+soluong+"',"+tongtien+","+datra+","+conno+",'"+ngay+"','"+ghichu+"')");
//Them so luong san pham trong danh sach cho san pham vua nhap
                    String[] arrtensp=tensp.split("%");
                    String[] arrsl=soluong.split("%");
                    boolean check=true;
                    for(int i=0;i<arrsl.length;i++) {
                        Cursor dataSp=database.GetData("SELECT * FROM tblSanPham WHERE ma="+arrtensp[i]+";");
                        while (dataSp.moveToNext()) {
                            int newSL=dataSp.getInt(2)-Integer.valueOf(arrsl[i]);
                            if(newSL<0) {
                                check=false;
                                break;
                            }
                        }
                    }
                    if(check) {
                        for(int i=0;i<arrsl.length;i++) {
                            Cursor dataSp=database.GetData("SELECT * FROM tblSanPham WHERE ma="+arrtensp[i]+";");
                            while (dataSp.moveToNext()) {
                                int newSL=dataSp.getInt(2)-Integer.valueOf(arrsl[i]);
                                database.QueryData("UPDATE tblSanPham SET soluong="+newSL+" WHERE ma="+dataSp.getInt(0)+";");
                                //                Th??m n??? cho nsx n???u n???
                                if(conno!=0) {
                                    Cursor dataDaiLy=database.GetData("SELECT * FROM tblDaiLy;");
                                    boolean kiemtra=false;
                                    while (dataDaiLy.moveToNext()) {
                                        if(dataDaiLy.getString(1).toLowerCase().equals(tendaily.toLowerCase())) {
                                            database.QueryData("UPDATE tblDaiLy SET conno="+(dataDaiLy.getInt(2)+chuanSo(etConNo.getText().toString()))+" WHERE ma="+dataDaiLy.getInt(0)+";");
                                            kiemtra=true;
                                            break;
                                        }
                                    }
                                    if(kiemtra==false) {
                                        database.QueryData("INSERT INTO tblDaiLy VALUES(null,'"+tendaily+"',"+conno+")");
                                    }
                                }

                                Intent myIntent = new Intent(DonXuatActivity.this, MainActivity.class);
//                myIntent.putExtra("key", 1); //Optional parameters
                                DonXuatActivity.this.startActivity(myIntent);
                            }
                        }
                    }else {
                        openDialogThieuHang(Gravity.CENTER);
                    }
                }
                }
        });

    }

    private List<SanPham> getList() {
        List<SanPham> list=new ArrayList<>();
        for(int i=0;i<arr.length;i++) {
            database=new DataBase(DonXuatActivity.this, "quanlydiennuoc.sqlite",null, 1);
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
        rcvDonXuat=findViewById(R.id.rcv_donxuat);
        btnTinh=findViewById(R.id.btnDonXuatTinh);
        btnLuu=findViewById(R.id.btnDonXuatLuu);
        tvTongTien=findViewById(R.id.tvXuatTong);
        etXuatKH=findViewById(R.id.etXuatKH);
        tvXuatGhiChu=findViewById(R.id.tvXuatGhiChu);
        etDaTra=findViewById(R.id.tvXuatDaTra);
        etConNo=findViewById(R.id.tvXuatConNo);
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
    //  Dialog
    private void openDialogThieuHang(int gravity) {
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_khong_du_sp);


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