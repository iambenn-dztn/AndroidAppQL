package com.example.project_ql.SanPham;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_ql.Interface.MyOnItemClickListener;
import com.example.project_ql.R;
import com.example.project_ql.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamViewHolder> implements Filterable {

    List<SanPham> listSp;
    List<SanPham> listOldSp;
    MyOnItemClickListener myOnItemClickListener;

    public SanPhamAdapter() {
    }

    public void setMyOnItemClickListener(MyOnItemClickListener myOnItemClickListener) {
        this.myOnItemClickListener = myOnItemClickListener;
    }

    public SanPhamAdapter(List<SanPham> listSp) {
        this.listSp = listSp;
        this.listOldSp=listSp;
    }

    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham,parent,false);
        return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
        SanPham sp=listSp.get(position);
        if(sp==null) return;
        holder.tvName.setText(sp.getTen());
        holder.tvGia.setText(chuanTien(sp.getGiaban()));
        holder.tvSoluong.setText(chuanTien(sp.getSoluong()));

        Bitmap bitmap= BitmapFactory.decodeByteArray(sp.getAnh(),0,sp.getAnh().length);
        holder.imgSp.setImageBitmap(bitmap);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myOnItemClickListener.onClick(listSp.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listSp != null) return  listSp.size();
        else return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence contraint) {
                String strSearch =contraint.toString();
                if(strSearch.isEmpty()) {
                    listSp=listOldSp;
                }else {
                    List<SanPham> lt=new ArrayList<>();
                    for (SanPham o: listOldSp) {
                        if(o.getTen().toLowerCase().contains(strSearch.toLowerCase())) {
                            lt.add(o);
                        }
                    }
                    listSp=lt;
                }

                FilterResults filterResults=new FilterResults();
                filterResults.values=listSp;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listSp= (List<SanPham>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class SanPhamViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvGia;
        TextView tvSoluong;
        ImageView imgSp;
        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tenSp);
            tvGia=itemView.findViewById(R.id.giaSp);
            tvSoluong=itemView.findViewById(R.id.slSp);
            imgSp=itemView.findViewById(R.id.imgSp);
        }
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

}
