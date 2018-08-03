package com.wangbin.mydome.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wangbin.mydome.R;
import com.wangbin.mydome.bean.SearchBean;

import java.util.List;

/**
 * Created by  wangbin.
 * on 2018/7/17
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {


    private Context mContext;
    private List<SearchBean> mDatas;
    private OnItemClickListener mOnItemClickListener;


    public SearchAdapter(Context mContext, List<SearchBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_search, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.search.setText(mDatas.get(position).getName());
        if (mDatas.get(position).isFlag()) {
            holder.lin_search.setBackgroundColor(ContextCompat.getColor(mContext, R.color.selected_search));
        } else {
            holder.lin_search.setBackgroundColor(ContextCompat.getColor(mContext, R.color.drawback));
        }

        holder.lin_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void setOnItemClickLitener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView search;
        private LinearLayout lin_search;

        MyViewHolder(View itemView) {
            super(itemView);
            search = itemView.findViewById(R.id.item_search);
            lin_search = itemView.findViewById(R.id.lin_search);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
