package com.example.project_ql.NoDaiLy;

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
import com.example.project_ql.MainActivity;
import com.example.project_ql.NoNhaSanXuat.NoNSXActivity;
import com.example.project_ql.NoNhaSanXuat.NoNhaSXAdapter;
import com.example.project_ql.R;
import com.example.project_ql.model.DaiLy;
import com.example.project_ql.model.NhaSanXuat;

import java.util.ArrayList;
import java.util.List;

public class NoDaiLyActivity extends AppCompatActivity {

    DataBase database;
    NoDaiLyAdapter noDaiLyAdapter;
    RecyclerView rcvNoDaiLy;
    TextView tongno,tenDaiLyTra;
    EditText etSoTienDaiLyTra;
    Button btnTraNoDaiLy, btnVe;
    int madaily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_dai_ly);
        initView();
        Toast.makeText(NoDaiLyActivity.this,"Hãy chọn đại lý trước khi cập nhật nợ.",Toast.LENGTH_SHORT).show();

        //DB
        database=new DataBase(NoDaiLyActivity.this, "quanlydiennuoc.sqlite",null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS tblDaiLy(ma INTEGER PRIMARY KEY AUTOINCREMENT, tendaily VARCHAR(200), conno INTEGER);");
//RCV
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rcvNoDaiLy.setLayoutManager(linearLayoutManager);
        noDaiLyAdapter=new NoDaiLyAdapter();
        noDaiLyAdapter.setData(getListDaiLy(), new NoDaiLyAdapter.IListenerClickItem() {
            @Override
            public void onClickItem(DaiLy nsx, int position) {
                View v = rcvNoDaiLy.getChildAt(position);
                TextView tv = v.findViewById(R.id.tv_nodl_tendl);
            }
        });
        rcvNoDaiLy.setAdapter(noDaiLyAdapter);
        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        Tinh tong no
        int tongnoInt=0;
        Cursor dataDl=database.GetData("SELECT * FROM tblDaiLy");
        while (dataDl.moveToNext()) {
            int conno=dataDl.getInt(2);
            tongnoInt+=conno;
        }
        tongno.setText(chuanTien(tongnoInt));

        noDaiLyAdapter.setData(getListDaiLy(), new NoDaiLyAdapter.IListenerClickItem() {
            @Override
            public void onClickItem(DaiLy daiLy, int position) {
                btnTraNoDaiLy.setEnabled(true);
                View v=rcvNoDaiLy.getChildAt(position);
                madaily=daiLy.getMa();
                TextView tv=v.findViewById(R.id.tv_nodl_tendl);
                tenDaiLyTra.setText(tv.getText().toString()+" : ");
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
        btnTraNoDaiLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etSoTienDaiLyTra.getText().toString().equals("")) {
                    int conno;
                    Cursor dataNsx=database.GetData("SELECT * FROM tblDaiLy WHERE ma="+madaily+";");
                    while (dataNsx.moveToNext()) {
                        conno=dataNsx.getInt(2);
                        if(conno>=chuanSo(etSoTienDaiLyTra.getText().toString())) {
                            database.QueryData("UPDATE tblDaiLy SET conno="+(conno-chuanSo(etSoTienDaiLyTra.getText().toString()))+" WHERE ma="+madaily+";");
                            finish();
                            startActivity(getIntent());
                        }else {
                            Toast.makeText(NoDaiLyActivity.this,"Chỉ nợ "+conno+", không được trả nhiều hơn.",Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(NoDaiLyActivity.this,"Bạn chưa nhập số tiền trả.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private List<DaiLy> getListDaiLy() {
        List<DaiLy> list=new ArrayList<>();
        Cursor dataNsx=database.GetData("SELECT * FROM tblDaiLy");
        while (dataNsx.moveToNext()) {
            int ma=Integer.parseInt(dataNsx.getString(0));
            String tennsx=dataNsx.getString(1);
            int conno=dataNsx.getInt(2);
            list.add(new DaiLy(ma,tennsx,conno));
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
        rcvNoDaiLy=findViewById(R.id.rcv_nodaily);
        tongno=findViewById(R.id.tv_nodaily_tong);
        tenDaiLyTra=findViewById(R.id.tenDailyTra);
        etSoTienDaiLyTra=findViewById(R.id.etSoTienDailyTra);
        btnTraNoDaiLy=findViewById(R.id.btnDaiLyTraNo);
        btnVe=findViewById(R.id.btnVeDaiLy);
    }

}