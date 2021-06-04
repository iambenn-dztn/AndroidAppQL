package com.example.project_ql.SanPham;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.project_ql.DataBase.DataBase;
import com.example.project_ql.Interface.MyOnItemClickListener;
import com.example.project_ql.MainActivity;
import com.example.project_ql.R;
import com.example.project_ql.model.SanPham;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link sanpham_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sanpham_frag extends Fragment  {

    DataBase database;
    private RecyclerView rcv;
    SearchView searchView;
    private View view;
    private MainActivity mainActivity;
    SanPhamAdapter spAdapter;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public sanpham_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sanpham_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static sanpham_frag newInstance(String param1, String param2) {
        sanpham_frag fragment = new sanpham_frag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        database=new DataBase(getContext(), "quanlydiennuoc.sqlite",null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS tblSanPham(ma INTEGER PRIMARY KEY AUTOINCREMENT, ten VARCHAR(200), soluong INTEGER, gianhap INTEGER, giaban INTEGER, nhaphanphoi VARCHAR(200), ghichu VARCHAR(200), anh BLOB)");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search,menu);
        SearchManager searchManager= (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView= (SearchView) menu.findItem(R.id.action_search1).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                spAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                spAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sanpham_frag, container, false);
        mainActivity= (MainActivity) getActivity();

        rcv=view.findViewById(R.id.rcv);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mainActivity);
        rcv.setLayoutManager(linearLayoutManager);

        spAdapter=new SanPhamAdapter(getList());
        rcv.setAdapter(spAdapter);
        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL);
        rcv.addItemDecoration(itemDecoration);

//        Gửi id sang activity ChiTietSanPham
        spAdapter.setMyOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void onClick(SanPham sp) {
                Intent myIntent = new Intent(getActivity(), ChiTietSanPhamActivity.class);
                myIntent.putExtra("ma", sp.getMa()); //Optional parameters
                getActivity().startActivity(myIntent);
            }
        });

//        Set onClick button Them
        Button btnThemSanPham=view.findViewById(R.id.btnThemSanPham);
        btnThemSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getContext(), ThemSanPhamActivity.class);
//                myIntent.putExtra("key", 1); //Optional parameters
                getContext().startActivity(myIntent);
            }
        });

        return view;

    }

    private List<SanPham> getList() {
        //int ma, String ten, int soluong, int gianhap, int giaban, String nhaphanphoi, String ghichu, int anh
        List<SanPham> list=new ArrayList<>();

        Cursor dataSp=database.GetData("SELECT * FROM tblSanPham");
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
        return list;
    }

}