package com.example.project_ql.NoNhaSanXuat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_ql.DataBase.DataBase;
import com.example.project_ql.DonNhap.DonNhapActivity;
import com.example.project_ql.MainActivity;
import com.example.project_ql.R;
import com.example.project_ql.model.NhaSanXuat;

import java.util.ArrayList;
import java.util.List;

public class NoNSXActivity extends AppCompatActivity {

    DataBase database;
    NoNhaSXAdapter noNhaSXAdapter;
    RecyclerView rcvNoNsx;
    TextView tongno,tenNsxTra;
    EditText etSoTienTra;
    Button btnTraNoNsx, btnVe;
    int mansx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_n_s_x);
        initView();
        Toast.makeText(NoNSXActivity.this,"Hãy chọn nhà sản xuất bạn muốn trả nợ.",Toast.LENGTH_SHORT).show();
//DB
        database=new DataBase(NoNSXActivity.this, "quanlydiennuoc.sqlite",null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS tblNhaSanXuat(ma INTEGER PRIMARY KEY AUTOINCREMENT, tennsx VARCHAR(200), conno INTEGER);");
//RCV
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rcvNoNsx.setLayoutManager(linearLayoutManager);
        noNhaSXAdapter=new NoNhaSXAdapter();
        noNhaSXAdapter.setData(getListNSX(), new NoNhaSXAdapter.IListenerClickItem() {
            @Override
            public void onClickItem(NhaSanXuat nsx, int position) {
                View v = rcvNoNsx.getChildAt(position);
                TextView tv = v.findViewById(R.id.tv_no_tennsx);
            }
        });
        rcvNoNsx.setAdapter(noNhaSXAdapter);
        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        Tinh tong no
        int tongnoInt=0;
        Cursor dataNsx=database.GetData("SELECT * FROM tblNhaSanXuat");
        while (dataNsx.moveToNext()) {
            int conno=dataNsx.getInt(2);
            tongnoInt+=conno;
        }
        tongno.setText(chuanTien(tongnoInt));

        noNhaSXAdapter.setData(getListNSX(), new NoNhaSXAdapter.IListenerClickItem() {
            @Override
            public void onClickItem(NhaSanXuat nsx, int position) {
                btnTraNoNsx.setEnabled(true);
                View v=rcvNoNsx.getChildAt(position);
                mansx=nsx.getMa();
                TextView tv=v.findViewById(R.id.tv_no_tennsx);
                tenNsxTra.setText(tv.getText().toString()+" : ");
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
        btnTraNoNsx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etSoTienTra.getText().toString().equals("")) {
                    int conno;
                    Cursor dataNsx=database.GetData("SELECT * FROM tblNhaSanXuat WHERE ma="+mansx+";");
                    while (dataNsx.moveToNext()) {
                        conno=dataNsx.getInt(2);
                        if(conno>chuanSo(etSoTienTra.getText().toString())) {
                            database.QueryData("UPDATE tblNhaSanXuat SET conno="+(conno-chuanSo(etSoTienTra.getText().toString()))+" WHERE ma="+mansx+";");
                            finish();
                            startActivity(getIntent());
                        }else if(conno==chuanSo(etSoTienTra.getText().toString())) {
                            database.QueryData("DELETE FROM tblNhaSanXuat WHERE ma="+mansx+";");
                            finish();
                            startActivity(getIntent());
                        }
                        else {
                            Toast.makeText(NoNSXActivity.this,"Chỉ nợ "+conno+", không được trả nhiều hơn.",Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(NoNSXActivity.this,"Bạn chưa nhập số tiền trả.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private List<NhaSanXuat> getListNSX() {
        List<NhaSanXuat> list=new ArrayList<>();
        Cursor dataNsx=database.GetData("SELECT * FROM tblNhaSanXuat");
        while (dataNsx.moveToNext()) {
            int ma=Integer.parseInt(dataNsx.getString(0));
            String tennsx=dataNsx.getString(1);
            int conno=dataNsx.getInt(2);
            list.add(new NhaSanXuat(ma,tennsx,conno));
        }
        return list;
    }

    int chuanSo(String s) {
        int n=s.charAt(0)-'0';
        for(int i=1;i<s.length();i++) {
            if(s.charAt(i)!='.')
                n=n*10+s.charAt(i)-'0';
        }
        return n;
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
        rcvNoNsx=findViewById(R.id.rcv_nonsx);
        tongno=findViewById(R.id.tv_nonsx_tong);
        tenNsxTra=findViewById(R.id.tenNsxTra);
        etSoTienTra=findViewById(R.id.etSoTienTra);
        btnTraNoNsx=findViewById(R.id.btnTraNoNsx);
        btnVe=findViewById(R.id.btnVeNsx);
    }
}