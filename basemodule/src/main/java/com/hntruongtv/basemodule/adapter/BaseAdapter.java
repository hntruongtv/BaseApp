package com.hntruongtv.basemodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Truong on 9/9/2017.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter {

    protected String TAG = this.getClass().getName();
    private List<T> mDataResource;
    protected Context mContext;
    protected LayoutInflater mInflater;

    private OnItemClickListener mOnItemClickListener;

    public T getItem(int pos) {
        if (mDataResource != null)
            return mDataResource.get(pos);
        return null;
    }

    public void setOnItemClickListener(OnItemClickListener pOnItemClickListener) {
        this.mOnItemClickListener = pOnItemClickListener;
    }

    public BaseAdapter(Context pContext, List<T> mDataResource) {
        this.mContext = pContext;
        this.mDataResource = mDataResource;
        this.mInflater = LayoutInflater.from(pContext);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder) holder).bindData(getItem(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(BaseAdapter.this, holder.getAdapterPosition());
                onItemClick(BaseAdapter.this, holder.getAdapterPosition());
            }
        });
    }

    protected void onItemClick(BaseAdapter adapter, int position) {

    }

    @Override
    public int getItemCount() {
        if (mDataResource != null)
            return mDataResource.size();
        return 0;
    }

    public List<T> getDataResource() {
        return mDataResource;
    }

    public ArrayList<T> getResource() {
        ArrayList<T> arr = new ArrayList<>();
        arr.addAll(mDataResource);
        return arr;
    }

    public interface OnItemClickListener {
        void onItemClick(BaseAdapter adapter, int position);
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindData(T data) {
        }
    }

    public void UpdateData(List<T> pDataResource) {
        if (pDataResource != null) {
            this.mDataResource = pDataResource;
            notifyDataSetChanged();
        }
    }

    public void clearAllItem() {
        this.mDataResource.clear();
        notifyDataSetChanged();
    }

    public void setLoadMore() {
        if (mDataResource != null && mDataResource.size() > 0 && mDataResource.get(getItemCount() - 1) != null) {
            Log.i("adatapter:", " show load more");
            mDataResource.add(null);
            notifyDataSetChanged();
        }
    }

    public void loadMoreDone() {
        if (mDataResource != null || mDataResource.contains(null)) {
            Log.i("adatapter:", " remove load more");
            mDataResource.remove(null);
            notifyDataSetChanged();
        }
    }
}
