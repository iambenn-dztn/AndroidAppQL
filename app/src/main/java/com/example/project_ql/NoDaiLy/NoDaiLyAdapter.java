package com.example.project_ql.NoDaiLy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_ql.R;
import com.example.project_ql.model.DaiLy;

import java.util.List;

public class NoDaiLyAdapter extends RecyclerView.Adapter<NoDaiLyAdapter.NoDaiLyViewHolder>{

    List<DaiLy> list;
    private IListenerClickItem iListenerClickItem;

    public interface IListenerClickItem {
        void onClickItem(DaiLy daiLy, int position);
    }

    public void setData(List<DaiLy> list, IListenerClickItem iListenerClickItem) {
        this.list=list;
        this.iListenerClickItem = iListenerClickItem;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public NoDaiLyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_daily, parent, false);
        return new NoDaiLyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoDaiLyViewHolder holder, int position) {
        DaiLy daiLy=list.get(position);
        holder.tvStt.setText(String.valueOf(position+1));
        holder.tvTenDaily.setText(daiLy.getTen());
        holder.tvTongno.setText(chuanTien(daiLy.getConno()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iListenerClickItem.onClickItem(daiLy,position);
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
    public class NoDaiLyViewHolder extends RecyclerView.ViewHolder {

        TextView tvStt,tvTenDaily,tvTongno;
        public NoDaiLyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStt=itemView.findViewById(R.id.tv_nodl_stt);
            tvTenDaily=itemView.findViewById(R.id.tv_nodl_tendl);
            tvTongno=itemView.findViewById(R.id.tv_nodl_tongno);
        }

    }
}
