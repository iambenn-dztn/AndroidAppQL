package com.example.project_ql.TraHang;

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

public class DonTraAdapter extends RecyclerView.Adapter<DonTraAdapter.DonTraViewHolder> {

    List<SanPham> list;

    public DonTraAdapter(List<SanPham> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public DonTraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dontra,parent,false);
        return new DonTraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonTraViewHolder holder, int position) {
        SanPham dn=list.get(position);
        if(dn==null) return;
        holder.tvStt.setText(String.valueOf(position+1));
        holder.tvTen.setText(dn.getTen());
    }

    @Override
    public int getItemCount() {
        if(list != null) return  list.size();
        else return 0;
    }

    public class DonTraViewHolder extends RecyclerView.ViewHolder {
        TextView tvStt, tvTen, tvSoluong;
        public DonTraViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStt=itemView.findViewById(R.id.sttDonTra);
            tvTen=itemView.findViewById(R.id.tenSpDonTra);
            tvSoluong=itemView.findViewById(R.id.soluongSpDonTra);
        }
    }
}
