package com.example.gaocehnwei.cook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<DayInfo> list;

    public  interface OnItemClickListener {
        void onItemClick(View view, int position, DayInfo item);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public  RecyclerAdapter(Context context, List<DayInfo> list) {
        super();
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.recycleitem, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        holder.date = (TextView)view.findViewById(R.id.recyclerData);
        holder.description = (TextView)view.findViewById(R.id.recyclerDescription);
        holder.tempRange = (TextView)view.findViewById(R.id.recyclerTemp);
        return holder;
    }

     @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.date.setText(list.get(i).getDay());
        viewHolder.description.setText(list.get(i).getWeather());
        viewHolder.tempRange.setText(list.get(i).getTempRange());
        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2016/11/24 Auto-genrated method stub
                    onItemClickListener.onItemClick(viewHolder.itemView, i, list.get(i));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
        TextView date;
        TextView description;
        TextView tempRange;
    }
}