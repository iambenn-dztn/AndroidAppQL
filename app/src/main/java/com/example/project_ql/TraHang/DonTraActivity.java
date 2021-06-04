package com.example.project_ql.TraHang;

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
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class DonTraActivity extends AppCompatActivity {

    DataBase database;
    RecyclerView rcvDonTra;
    Button btnTinh,btnLuu;
    TextView tvTongTien,tvTraGhiChu;
    RadioButton ratruno,ratien;
    EditText etTraKH, etConNo,etTienTra;
    DonTraAdapter donNhapAdapter;
    String[] arr=new String[200];
    String masp;
    String sl;
    int giaInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_tra);
        initView();
//        Hien thi danh sach sach cac san pham da chon
        Intent intent=getIntent();
        String st=intent.getStringExtra("stt");
        arr=st.split("%");
//set rcv danh sach san pham da chon
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(DonTraActivity.this);
        rcvDonTra.setLayoutManager(linearLayoutManager);

        donNhapAdapter=new DonTraAdapter(getList());
        rcvDonTra.setAdapter(donNhapAdapter);
        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(DonTraActivity.this, DividerItemDecoration.VERTICAL);
        rcvDonTra.addItemDecoration(itemDecoration);
//Database
        database=new DataBase(DonTraActivity.this, "quanlydiennuoc.sqlite",null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS tblDonNhap(ma INTEGER PRIMARY KEY AUTOINCREMENT, tennsx VARCHAR(200), tensp VARCHAR(400), soluong VARCHAR(400), tongtien INTEGER, datra INTEGER, conno INTEGER, ngay VARCHAR(200), ghichu VARCHAR(200))");
        database.QueryData("CREATE TABLE IF NOT EXISTS tblDaiLy(ma INTEGER PRIMARY KEY AUTOINCREMENT, tendaily VARCHAR(200), conno INTEGER)");
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
                        View view1 = rcvDonTra.getChildAt(i);
                        EditText soluongV = (EditText) view1.findViewById(R.id.soluongSpDonTra);
                        String soluong = soluongV.getText().toString();
                        if(i==arr.length-1) sl=sl+soluong;
                        else sl=sl+soluong+"%";
//Set gia cho tung item don nhap
                        View view2 = rcvDonTra.getChildAt(i);
                        TextView giaDN = (TextView) view2.findViewById(R.id.giaDonTra);
                        Cursor dataSp=database.GetData("SELECT * FROM tblSanPham WHERE ma="+sp.getMa()+";");
                        while (dataSp.moveToNext()) {
                            int giaban=dataSp.getInt(4);
                            giaInt=giaban*Integer.valueOf(soluong);
                            giaDN.setText(chuanTien(giaInt));
                        }
                        tongtien+=sp.getGiaban()*Integer.valueOf(soluong);
                    }
                    tvTongTien.setText(chuanTien(tongtien));
                    btnLuu.setEnabled(true);
                    if(etTraKH.getText().toString().equals("")) {
                        Toast.makeText(DonTraActivity.this,"Bạn chưa nhập tên đại lý.",Toast.LENGTH_SHORT).show();
                    }else if(ratruno.isChecked() && !etTraKH.getText().toString().equals("")) {
                            String tendl=etTraKH.getText().toString();

                                Cursor data=database.GetData("SELECT * FROM tblDaiLy WHERE tendaily='"+tendl+"';");
                                if(data.getCount()==0) {
                                    Toast.makeText(DonTraActivity.this,"Không tìm thấy đại lý "+tendl,Toast.LENGTH_SHORT).show();
                                }
                                while (data.moveToNext()) {
                                    if(data.getInt(2)-tongtien>0) {
                                        etConNo.setText(chuanTien(data.getInt(2)-tongtien));
                                        return;
                                    }else if(data.getInt(2)-tongtien==0) {
                                        etConNo.setText(chuanTien(data.getInt(2)-tongtien));
                                        return;
                                    }
                                    else {
                                        etConNo.setText("");
                                        Toast.makeText(DonTraActivity.this,"Số nợ của "+tendl+" hiện tại chỉ là: "+chuanTien(data.getInt(2)),Toast.LENGTH_SHORT).show();
                                    }
                                }
                    }else {
                        etConNo.setText("");
                    }
                } catch (Exception e) {
                    openDialogNhapThieu(Gravity.CENTER);
                }
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(!ratien.isChecked()&&!ratruno.isChecked()) {
                        Toast.makeText(DonTraActivity.this,"Chọn phương thức hoàn tiền.",Toast.LENGTH_SHORT).show();
                    }else if(ratruno.isChecked()&&!etConNo.equals("")) {
                        database.QueryData("UPDATE tblDaiLy SET conno="+chuanSo(etConNo.getText().toString())+" WHERE tendaily='"+etTraKH.getText().toString()+"'");
                        String[] arrmasp=masp.split("%");
                        String[] arrsl=sl.split("%");
                        for(int i=0;i<arrsl.length;i++) {
                            Cursor dataSp=database.GetData("SELECT * FROM tblSanPham WHERE ma="+arrmasp[i]+";");
                            while (dataSp.moveToNext()) {
                                database.QueryData("UPDATE tblSanPham SET soluong="+(dataSp.getInt(2)+Integer.valueOf(arrsl[i]))+" WHERE ma="+dataSp.getInt(0)+";");
                            }
                        }
                        if(etConNo.getText().toString().equals("0")) {
                            database.QueryData("DELETE FROM tblDaiLy WHERE tendaily='"+etTraKH.getText().toString()+"';");
                        }

                        Intent intent1=new Intent(DonTraActivity.this,MainActivity.class);
                        DonTraActivity.this.startActivity(intent1);
                    }
                    else if(ratien.isChecked()){
                        String[] arrmasp=masp.split("%");
                        String[] arrsl=sl.split("%");
                        for(int i=0;i<arrsl.length;i++) {
                            Cursor dataSp=database.GetData("SELECT * FROM tblSanPham WHERE ma="+arrmasp[i]+";");
                            while (dataSp.moveToNext()) {
                                database.QueryData("UPDATE tblSanPham SET soluong="+(dataSp.getInt(2)+Integer.valueOf(arrsl[i]))+" WHERE ma="+dataSp.getInt(0)+";");
                            }
                        }
                        Intent intent1=new Intent(DonTraActivity.this,MainActivity.class);
                        DonTraActivity.this.startActivity(intent1);
                    }
                }catch (Exception e) {
                    Toast.makeText(DonTraActivity.this,"Tính tiền trước đã.",Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    private void initView() {
        rcvDonTra=findViewById(R.id.rcv_dontra);
        btnTinh=findViewById(R.id.btnDonTraTinh);
        ratruno=findViewById(R.id.ra_truno);
        ratien=findViewById(R.id.ra_tien);
        btnLuu=findViewById(R.id.btnDonTraLuu);
        tvTongTien=findViewById(R.id.tvTraTong);
        etTraKH=findViewById(R.id.etTraKH);
        tvTraGhiChu=findViewById(R.id.tvTraGhiChu);
        etTienTra=findViewById(R.id.etTraTienHoan);
        etConNo=findViewById(R.id.tvTraConNo);
    }

    private List<SanPham> getList() {
        List<SanPham> list=new ArrayList<>();
        for(int i=0;i<arr.length;i++) {
            database=new DataBase(DonTraActivity.this, "quanlydiennuoc.sqlite",null, 1);
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

}