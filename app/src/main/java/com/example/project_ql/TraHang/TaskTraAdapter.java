package com.example.project_ql.TraHang;

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

public class TaskTraAdapter extends RecyclerView.Adapter<TaskTraAdapter.TaskTraViewHolder> implements Filterable {
    List<Task> list;
    List<Task> listOld;
    private TaskNhapAdapter.IListenerClickCheckBox iListenerClickCheckBox;

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
    public TaskTraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tasktra,parent,false);
        return  new TaskTraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskTraViewHolder holder, int position) {
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





    class TaskTraViewHolder extends RecyclerView.ViewHolder{
        CheckBox cb;
        TextView tv;

        public TaskTraViewHolder(@NonNull View itemView) {
            super(itemView);
            cb=itemView.findViewById(R.id.cb_tasktra);
            tv=itemView.findViewById(R.id.tv_tasktra);
        }
    }

}
