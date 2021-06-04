package com.example.project_ql.ThongKeNhap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.project_ql.DataBase.DataBase;
import com.example.project_ql.R;
import com.example.project_ql.model.ThongKeNhap;

import java.util.ArrayList;
import java.util.List;

public class ThongKeNhapActivity extends AppCompatActivity {
    DataBase database;
    RecyclerView rcvThongKeNhap;
    ThongKeNhapAdapter thongKeNhapAdapter;
    SearchView searchView;
    Button btnTinh;
    TextView tvTong;
    List<ThongKeNhap> list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_nhap);
        initView();
        list2=new ArrayList<>();
//        DB
        database=new DataBase(ThongKeNhapActivity.this, "quanlydiennuoc.sqlite",null, 1);


        //set rcv danh sach san pham da chon
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ThongKeNhapActivity.this);
        rcvThongKeNhap.setLayoutManager(linearLayoutManager);

        thongKeNhapAdapter=new ThongKeNhapAdapter(getList());
        rcvThongKeNhap.setAdapter(thongKeNhapAdapter);
        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(ThongKeNhapActivity.this, DividerItemDecoration.VERTICAL);
        rcvThongKeNhap.addItemDecoration(itemDecoration);

        database=new DataBase(ThongKeNhapActivity.this, "quanlydiennuoc.sqlite",null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS tblDonNhap(ma INTEGER PRIMARY KEY AUTOINCREMENT, tennsx VARCHAR(200), tensp VARCHAR(400), soluong VARCHAR(400), tongtien INTEGER, datra INTEGER, conno INTEGER, ngay VARCHAR(200), ghichu VARCHAR(200))");

        thongKeNhapAdapter.setData(getList(), new ThongKeNhapAdapter.IListenerClickCheckBox() {
            @Override
            public void onClickCheckBox(ThongKeNhap thongKeNhap, int position) {
                View v= rcvThongKeNhap.getChildAt(position);
                CheckBox cb= v.findViewById(R.id.cb_thongkenhap);
                if(cb.isChecked()) {
                    list2.add(thongKeNhap);
                    System.out.println(list2.size());
                }else {
                    list2.remove(thongKeNhap);
                    System.out.println(list2.size());
                }
            }
        });

        btnTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tong=0;
                for(int i=0;i<list2.size();i++) {
                    tong+=list2.get(i).getGiatridon();
                }
                tvTong.setText(chuanTien(tong));
                tong=0;
                list2.removeAll(list2);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_thongke_nhap,menu);

        SearchManager searchManager= (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView= (SearchView) menu.findItem(R.id.action_search3).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                thongKeNhapAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                thongKeNhapAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    private List<ThongKeNhap> getList() {
        List<ThongKeNhap> list=new ArrayList<>();
        Cursor dataNhap=database.GetData("SELECT * FROM tblDonNhap");
        while (dataNhap.moveToNext()) {
            String ngay=dataNhap.getString(7);
            String tennsx=dataNhap.getString(1);
            int giatri=dataNhap.getInt(4);
            list.add(new ThongKeNhap(false,ngay,tennsx,giatri));
        }
        return list;
    }


    private void initView() {
        rcvThongKeNhap=findViewById(R.id.rcv_tkn);
        btnTinh=findViewById(R.id.btn_tkn_tinh);
        tvTong=findViewById(R.id.tv_tkn_tong);
    }


}