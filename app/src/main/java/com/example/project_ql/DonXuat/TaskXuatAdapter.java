package com.example.project_ql.DonXuat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_ql.DonNhap.TaskNhapAdapter;
import com.example.project_ql.R;
import com.example.project_ql.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskXuatAdapter extends RecyclerView.Adapter<TaskXuatAdapter.TaskXuatViewHolder> implements Filterable {

    List<Task> list;
    List<Task> listOld;
    private TaskNhapAdapter.IListenerClickCheckBox iListenerClickCheckBox;

    public interface IListenerClickCheckBox {
        void onClickCheckBox(Task task, int position);
    }

    public void setData(List<Task> l, TaskNhapAdapter.IListenerClickCheckBox iListenerClickCheckBox) {
        this.list=l;
        this.listOld=list;
        this.iListenerClickCheckBox = iListenerClickCheckBox;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TaskXuatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_taskxuat,parent,false);
        return  new TaskXuatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskXuatViewHolder holder, int position) {
        Task task=list.get(position);
        if(task==null) return;

        holder.tv.setText(task.getName());
        holder.cb.setChecked(task.isCheck());
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iListenerClickCheckBox.onClickCheckBox(task,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list!=null) {
            return list.size();
        }
        return 0;
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
                    List<Task> lt=new ArrayList<>();
                    for (Task o: listOld) {
                        if(o.getName().toLowerCase().contains(strSearch.toLowerCase())) {
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
                list= (List<Task>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class TaskXuatViewHolder extends RecyclerView.ViewHolder{
        CheckBox cb;
        TextView tv;

        public TaskXuatViewHolder(@NonNull View itemView) {
            super(itemView);
            cb=itemView.findViewById(R.id.cb_taskxuat);
            tv=itemView.findViewById(R.id.tv_taskxuat);
        }
    }
}
