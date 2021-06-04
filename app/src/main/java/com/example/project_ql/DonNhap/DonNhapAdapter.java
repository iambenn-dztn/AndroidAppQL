package com.example.project_ql.DonNhap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_ql.R;
import com.example.project_ql.model.SanPham;

import java.util.List;

public class DonNhapAdapter extends RecyclerView.Adapter<DonNhapAdapter.DonNhapViewHolder>{

    List<SanPham> list;

    public DonNhapAdapter(List<SanPham> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public DonNhapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donnhap,parent,false);
        return new DonNhapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonNhapViewHolder holder, int position) {
        SanPham dn=list.get(position);
        if(dn==null) return;
        holder.tvStt.setText(String.valueOf(position+1));
        holder.tvTen.setText(dn.getTen());
//        if(position==list.size()-1)
    }

    @Override
    public int getItemCount() {
        if(list != null) return  list.size();
        else return 0;
    }

    public class DonNhapViewHolder extends RecyclerView.ViewHolder {
        TextView tvStt, tvTen, tvSoluong;
        public DonNhapViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStt=itemView.findViewById(R.id.sttDonNhap);
            tvTen=itemView.findViewById(R.id.tenSpDonNhap);
            tvSoluong=itemView.findViewById(R.id.soluongSpDonNhap);
        }
    }

}
