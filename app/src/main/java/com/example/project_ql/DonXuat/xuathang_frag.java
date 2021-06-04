package com.example.project_ql.DonXuat;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project_ql.DataBase.DataBase;
import com.example.project_ql.DonNhap.DonNhapActivity;
import com.example.project_ql.DonNhap.TaskNhapAdapter;
import com.example.project_ql.R;
import com.example.project_ql.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link xuathang_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class xuathang_frag extends Fragment {

    RecyclerView rcvTask1,rcvTask2;
    LinearLayout taskxuatShow;
    TextView tvDemXuat;
    ImageView imgXuat;
    SearchView searchView;
    Button btnXuat;
    TaskXuatAdapter taskXuatAdapter1;
    TaskXuatAdapter taskXuatAdapter2;

    DataBase database=new DataBase(getContext(), "quanlydiennuoc.sqlite",null, 1);

    boolean isExpand=true;

    List<Task> listTask1;
    List<Task> listTask2=new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public xuathang_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment xuathang_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static xuathang_frag newInstance(String param1, String param2) {
        xuathang_frag fragment = new xuathang_frag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search_xuat,menu);
        SearchManager searchManager= (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView= (SearchView) menu.findItem(R.id.action_search3).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                taskXuatAdapter1.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                taskXuatAdapter1.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_xuathang_frag, container, false);
        rcvTask1=v.findViewById(R.id.rcv_taskxuat1);
        rcvTask2=v.findViewById(R.id.rcv_taskxuat2);
        taskxuatShow=v.findViewById(R.id.taskxuat_show);
        tvDemXuat=v.findViewById(R.id.tv_demxuat);
        imgXuat=v.findViewById(R.id.imgXuat);
        btnXuat=v.findViewById(R.id.btnxuat);
        setDataTask1();
        setDataTask2();
        btnXuat.setEnabled(false);

        //Bat su kien nut Nhap hang
        btnXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String st="";
                for(int i=0;i<listTask2.size();i++) {
                    st=st+String.valueOf(listTask2.get(i).getStt())+"%";
                }
                Intent intent=new Intent(getContext(), DonXuatActivity.class);
                intent.putExtra("stt",st);
                getContext().startActivity(intent);
            }
        });

        return v;
    }

    public void setDataTask2() {
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(getContext());
        rcvTask2.setLayoutManager(linearLayoutManager1);
        rcvTask2.setNestedScrollingEnabled(false);
        rcvTask2.setFocusable(false);

        taskXuatAdapter2=new TaskXuatAdapter();
        taskXuatAdapter2.setData(listTask2, new TaskNhapAdapter.IListenerClickCheckBox() {
            @Override
            public void onClickCheckBox(Task task, int position) {
                listTask2.remove(task);
                taskXuatAdapter2.notifyItemRemoved(position);
                taskXuatAdapter2.notifyItemRangeRemoved(position,listTask2.size());

                task.setCheck(false);
                listTask1.add(task);
                taskXuatAdapter1.notifyDataSetChanged();
                showOrHiddenTask();
            }
        });
        rcvTask2.setAdapter(taskXuatAdapter2);
    }

    public void setDataTask1() {
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(getContext());
        rcvTask1.setLayoutManager(linearLayoutManager1);
        rcvTask1.setNestedScrollingEnabled(false);
        rcvTask1.setFocusable(false);

        taskXuatAdapter1=new TaskXuatAdapter();
        listTask1=getListTask();
        taskXuatAdapter1.setData(listTask1, new TaskNhapAdapter.IListenerClickCheckBox() {
            @Override
            public void onClickCheckBox(Task task, int position) {
                listTask1.remove(task);
                taskXuatAdapter1.notifyItemRemoved(position);
                taskXuatAdapter1.notifyItemRangeRemoved(position,listTask1.size());

                task.setCheck(true);
                listTask2.add(task);
                taskXuatAdapter2.notifyDataSetChanged();
                showOrHiddenTask();
            }
        });
        rcvTask1.setAdapter(taskXuatAdapter1);
    }

    List<Task> getListTask() {
        List<Task> list=new ArrayList<>();
        database=new DataBase(getContext(), "quanlydiennuoc.sqlite",null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS tblSanPham(ma INTEGER PRIMARY KEY AUTOINCREMENT, ten VARCHAR(200), soluong INTEGER, gianhap INTEGER, giaban INTEGER, nhaphanphoi VARCHAR(200), ghichu VARCHAR(200), anh BLOB)");
        Cursor dataSp=database.GetData("SELECT * FROM tblSanPham");
        while (dataSp.moveToNext()) {
            list.add(new Task(dataSp.getInt(0),dataSp.getString(1),false));
        }
        return list;
    }

    public void showOrHiddenTask() {
        if (listTask2!=null && listTask2.size()>0) {
            btnXuat.setEnabled(true);
            taskxuatShow.setVisibility(View.VISIBLE);
            tvDemXuat.setText(String.valueOf(listTask2.size()));
            imgXuat.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
        } else {
            btnXuat.setEnabled(false);
            taskxuatShow.setVisibility(View.VISIBLE);
            tvDemXuat.setText(String.valueOf(listTask2.size()));
            imgXuat.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
        }

        taskxuatShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isExpand==true) {
                    isExpand=false;
                    rcvTask2.setVisibility(View.GONE);
                    imgXuat.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
                }else {
                    isExpand=true;
                    rcvTask2.setVisibility(View.VISIBLE);
                    imgXuat.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                }
            }
        });

    }

}