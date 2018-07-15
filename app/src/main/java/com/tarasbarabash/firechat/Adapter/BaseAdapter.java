package com.tarasbarabash.firechat.Adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tarasbarabash.firechat.Model.Profile;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.Utils.FirebaseUtils;
import com.tarasbarabash.firechat.Utils.TimeUtils;

import java.text.DateFormat;
import java.util.ArrayList;

/**
 * Created by Taras
 * 03.05.2018, 14:36.
 */

public abstract class BaseAdapter<B extends ViewDataBinding, O>
        extends RecyclerView.Adapter<BaseViewHolder<B, O>> {
    private static final String TAG = BaseAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<O> mList;

    public BaseAdapter(Context context, ArrayList<O> list) {
        mContext = context;
        if (list == null) mList = new ArrayList<>();
        else mList = list;
    }

    @Override
    public BaseViewHolder<B, O> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        B binding = DataBindingUtil.inflate(layoutInflater, getLayout(viewType), parent, false);
        return getViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<B, O> holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public Context getContext() {
        return mContext;
    }

    public abstract BaseViewHolder<B, O> getViewHolder(B binding);

    public abstract int getLayout(int viewType);

    public ArrayList<O> getList() {
        return mList;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void setList(ArrayList<O> list) {
        mList = list;
    }

    public ArrayList<O> swapList(ArrayList<O> newList) {
        Log.i(TAG, "swapList: " + newList);
        ArrayList<O> oldList = mList;
        mList = newList;
        this.notifyDataSetChanged();
        return oldList;
    }
}
