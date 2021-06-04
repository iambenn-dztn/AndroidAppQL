package com.example.project_ql.DonXuat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_ql.DonNhap.DonNhapAdapter;
import com.example.project_ql.R;
import com.example.project_ql.model.SanPham;

import java.util.List;

public class DonXuatAdapter extends RecyclerView.Adapter<DonXuatAdapter.DonXuatViewHolder>{
    List<SanPham> list;

    public DonXuatAdapter(List<SanPham> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public DonXuatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donxuat,parent,false);
        return new DonXuatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonXuatViewHolder holder, int position) {
        SanPham dx=list.get(position);
        if(dx==null) return;
        holder.tvStt.setText(String.valueOf(position+1));
        holder.tvTen.setText(dx.getTen());
    }

    @Override
    public int getItemCount() {
        if(list != null) return  list.size();
        else return 0;
    }

    public class DonXuatViewHolder extends RecyclerView.ViewHolder {
        TextView tvStt, tvTen, tvSoluong;
        public DonXuatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStt=itemView.findViewById(R.id.sttDonXuat);
            tvTen=itemView.findViewById(R.id.tenSpDonXuat);
            tvSoluong=itemView.findViewById(R.id.soluongSpDonXuat);
        }
    }
}
