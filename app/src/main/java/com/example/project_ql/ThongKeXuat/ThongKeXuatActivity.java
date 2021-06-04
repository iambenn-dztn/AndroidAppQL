package com.example.project_ql.ThongKeXuat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.project_ql.DataBase.DataBase;
import com.example.project_ql.R;
import com.example.project_ql.ThongKeNhap.ThongKeNhapActivity;
import com.example.project_ql.ThongKeNhap.ThongKeNhapAdapter;
import com.example.project_ql.model.ThongKeNhap;
import com.example.project_ql.model.ThongKeXuat;

import java.util.ArrayList;
import java.util.List;

public class ThongKeXuatActivity extends AppCompatActivity {

    DataBase database;
    RecyclerView rcvThongKeXuat;
    ThongKeXuatAdapter thongKeXuatAdapter;
    SearchView searchView;
    Button btnTinh;
    TextView tvTong;
    List<ThongKeXuat> list2=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_xuat);
        initView();
//        DB
        database=new DataBase(ThongKeXuatActivity.this, "quanlydiennuoc.sqlite",null, 1);


        //set rcv danh sach san pham da chon
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ThongKeXuatActivity.this);
        rcvThongKeXuat.setLayoutManager(linearLayoutManager);

        thongKeXuatAdapter=new ThongKeXuatAdapter(getList());
        rcvThongKeXuat.setAdapter(thongKeXuatAdapter);
        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(ThongKeXuatActivity.this, DividerItemDecoration.VERTICAL);
        rcvThongKeXuat.addItemDecoration(itemDecoration);

        database=new DataBase(ThongKeXuatActivity.this, "quanlydiennuoc.sqlite",null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS tblDonXuat(ma INTEGER PRIMARY KEY AUTOINCREMENT, tendaily VARCHAR(200), tensp VARCHAR(400), soluong VARCHAR(400), tongtien INTEGER, datra INTEGER, conno INTEGER, ngay VARCHAR(200), ghichu VARCHAR(200))");

        thongKeXuatAdapter.setData(getList(), new ThongKeXuatAdapter.IListenerClickCheckBox() {
            @Override
            public void onClickCheckBox(ThongKeXuat thongKeNhap, int position) {
                View v=rcvThongKeXuat.getChildAt(position);
                CheckBox cb=v.findViewById(R.id.cb_thongkexuat);
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
        getMenuInflater().inflate(R.menu.menu_search_thongke_xuat,menu);

        SearchManager searchManager= (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView= (SearchView) menu.findItem(R.id.action_search4).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                thongKeXuatAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                thongKeXuatAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    private List<ThongKeXuat> getList() {
        List<ThongKeXuat> list=new ArrayList<>();
        Cursor dataXuat=database.GetData("SELECT * FROM tblDonXuat");
        while (dataXuat.moveToNext()) {
            String ngay=dataXuat.getString(7);
            String tennsx=dataXuat.getString(1);
            int giatri=dataXuat.getInt(4);
            list.add(new ThongKeXuat(false,ngay,tennsx,giatri));
        }
        return list;
    }


    private void initView() {
        rcvThongKeXuat=findViewById(R.id.rcv_tkx);
        btnTinh=findViewById(R.id.btn_tkx_tinh);
        tvTong=findViewById(R.id.tv_tkx_tong);
    }

}