package com.example.project_ql.ThongKeNhap;

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

import java.util.ArrayList;
import java.util.List;

public class ThongKeNhapAdapter extends RecyclerView.Adapter<ThongKeNhapAdapter.ThongKeNhapViewHolder> implements Filterable {

    List<ThongKeNhap> list;
    List<ThongKeNhap> listOld;
    private IListenerClickCheckBox iListenerClickCheckBox;

    public ThongKeNhapAdapter(List<ThongKeNhap> list) {
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
                    List<ThongKeNhap> lt=new ArrayList<>();
                    for (ThongKeNhap o: listOld) {
                        if(o.getTennsx().toLowerCase().contains(strSearch.toLowerCase())) {
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
                list= (List<ThongKeNhap>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface IListenerClickCheckBox {
        void onClickCheckBox(ThongKeNhap thongKeNhap, int position);
    }
    public void setData(List<ThongKeNhap> l, IListenerClickCheckBox iListenerClickCheckBox) {
        this.list=l;
        this.listOld=list;
        this.iListenerClickCheckBox = iListenerClickCheckBox;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ThongKeNhapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thongke_nhap,parent,false);
        return  new ThongKeNhapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThongKeNhapViewHolder holder, int position) {
        ThongKeNhap thongKeNhap=list.get(position);
        if(thongKeNhap==null) return;

        holder.tvNgay.setText(thongKeNhap.getNgay());
        holder.cb.setChecked(thongKeNhap.isCheck());
        holder.tvTen.setText(thongKeNhap.getTennsx());
        holder.tvGiatri.setText(chuanTien(thongKeNhap.getGiatridon()));
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iListenerClickCheckBox.onClickCheckBox(thongKeNhap,position);
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

    class ThongKeNhapViewHolder extends RecyclerView.ViewHolder {

        CheckBox cb;
        TextView tvNgay,tvTen,tvGiatri;

        public ThongKeNhapViewHolder(@NonNull View itemView) {
            super(itemView);
            cb=itemView.findViewById(R.id.cb_thongkenhap);
            tvNgay=itemView.findViewById(R.id.tv_tkn_ngay);
            tvTen=itemView.findViewById(R.id.tv_tkn_kh);
            tvGiatri=itemView.findViewById(R.id.tv_tkn_tien);
        }
    }
}
