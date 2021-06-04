package com.example.project_ql.ThongKeXuat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_ql.R;
import com.example.project_ql.model.ThongKeNhap;
import com.example.project_ql.model.ThongKeXuat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ThongKeXuatAdapter extends RecyclerView.Adapter<ThongKeXuatAdapter.ThongKeXuatViewHolder> implements Filterable {

    List<ThongKeXuat> list;
    List<ThongKeXuat> listOld;
    private ThongKeXuatAdapter.IListenerClickCheckBox iListenerClickCheckBox;

    public ThongKeXuatAdapter(List<ThongKeXuat> list) {
        this.list = list;
        this.listOld=list;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence contraint) {
                String strSearch =contraint.toString();
                if(strSearch.isEmpty()) {
                    list=listOld;
                }else {
                    List<ThongKeXuat> lt=new ArrayList<>();
                    for (ThongKeXuat o: listOld) {
                        if(o.getTendaily().toLowerCase().contains(strSearch.toLowerCase())) {
                            lt.add(o);
                        }
                    }
                    list=lt;
                }

                FilterResults filterResults=new FilterResults();
                filterResults.values=list;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list= (List<ThongKeXuat>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface IListenerClickCheckBox {
        void onClickCheckBox(ThongKeXuat thongKeXuat, int position);
    }
    public void setData(List<ThongKeXuat> l, ThongKeXuatAdapter.IListenerClickCheckBox iListenerClickCheckBox) {
        this.list=l;
        this.listOld=list;
        this.iListenerClickCheckBox = iListenerClickCheckBox;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThongKeXuatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thongke_xuat,parent,false);
        return  new ThongKeXuatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThongKeXuatViewHolder holder, int position) {
        ThongKeXuat thongKeXuat=list.get(position);
        if(thongKeXuat==null) return;

        holder.tvNgay.setText(thongKeXuat.getNgay());
        holder.cb.setChecked(thongKeXuat.isCheck());
        holder.tvTen.setText(thongKeXuat.getTendaily());
        holder.tvGiatri.setText(chuanTien(thongKeXuat.getGiatridon()));
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iListenerClickCheckBox.onClickCheckBox(thongKeXuat,position);
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
    public int getItemCount() {
        if(list!=null) {
            return list.size();
        }
        return 0;
    }


    class ThongKeXuatViewHolder extends RecyclerView.ViewHolder {

        CheckBox cb;
        TextView tvNgay,tvTen,tvGiatri;

        public ThongKeXuatViewHolder(@NonNull View itemView) {
            super(itemView);
            cb=itemView.findViewById(R.id.cb_thongkexuat);
            tvNgay=itemView.findViewById(R.id.tv_tkx_ngay);
            tvTen=itemView.findViewById(R.id.tv_tkx_kh);
            tvGiatri=itemView.findViewById(R.id.tv_tkx_tien);
        }
    }
}
