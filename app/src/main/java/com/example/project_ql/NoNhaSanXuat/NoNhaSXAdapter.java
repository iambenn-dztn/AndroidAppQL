package com.example.project_ql.NoNhaSanXuat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_ql.R;
import com.example.project_ql.model.NhaSanXuat;

import java.util.List;

public class NoNhaSXAdapter extends RecyclerView.Adapter<NoNhaSXAdapter.NhaSanXuatViewHolder>{
    List<NhaSanXuat> list;

    private IListenerClickItem iListenerClickItem;

    public interface IListenerClickItem {
        void onClickItem(NhaSanXuat nsx, int position);
    }

    public void setData(List<NhaSanXuat> list, IListenerClickItem iListenerClickItem) {
        this.list=list;
        this.iListenerClickItem = iListenerClickItem;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public NhaSanXuatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_nsx, parent, false);
        return new NhaSanXuatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhaSanXuatViewHolder holder, int position) {
        NhaSanXuat nsx=list.get(position);
        holder.tvStt.setText(String.valueOf(position+1));
        holder.tvTenNsx.setText(nsx.getTen());
        holder.tvTongno.setText(chuanTien(nsx.getConno()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iListenerClickItem.onClickItem(nsx,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list != null) return  list.size();
        else return 0;
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

    public class NhaSanXuatViewHolder extends RecyclerView.ViewHolder {

        TextView tvStt,tvTenNsx,tvTongno;
        public NhaSanXuatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStt=itemView.findViewById(R.id.tv_no_stt);
            tvTenNsx=itemView.findViewById(R.id.tv_no_tennsx);
            tvTongno=itemView.findViewById(R.id.tv_no_tongno);
        }

    }
}
