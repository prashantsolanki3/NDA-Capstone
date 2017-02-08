package com.prashantsolanki.blackshift.trans.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.prashantsolanki.blackshift.trans.viewholder.QuoteVh;

import java.util.ArrayList;

import io.github.prashantsolanki3.snaplibrary.snap.adapter.SnapAdapter;
import io.github.prashantsolanki3.snaplibrary.snap.layout.viewholder.SnapViewHolder;
import io.github.prashantsolanki3.snaplibrary.snap.layout.wrapper.SnapLayoutWrapper;

/**
 * Created by prsso on 06-02-2017.
 */

public class AnimatedSnapAdapter<T> extends SnapAdapter<T> {
    public AnimatedSnapAdapter(@NonNull Context context, @NonNull Class modelClass, @LayoutRes int itemLayout, @NonNull Class<? extends SnapViewHolder> viewHolderClass) {
        super(context, modelClass, itemLayout, viewHolderClass);
    }

    public AnimatedSnapAdapter(@NonNull Context context, @NonNull Class modelClass, @LayoutRes int itemLayout, @NonNull Class<? extends SnapViewHolder> viewHolderClass, @NonNull RecyclerView recyclerView) {
        super(context, modelClass, itemLayout, viewHolderClass, recyclerView);
    }

    public AnimatedSnapAdapter(@NonNull Context context, @NonNull Class<T> modelClass, @LayoutRes int itemLayout, @NonNull Class<? extends SnapViewHolder> viewHolderClass, @NonNull RecyclerView recyclerView, @NonNull ViewGroup alternateView) {
        super(context, modelClass, itemLayout, viewHolderClass, recyclerView, alternateView);
    }

    public AnimatedSnapAdapter(@NonNull Context context, @LayoutRes int itemLayout, @NonNull Class<? extends SnapViewHolder> viewHolderClass) {
        super(context, itemLayout, viewHolderClass);
    }

    public AnimatedSnapAdapter(@NonNull Context context, @NonNull SnapLayoutWrapper wrapper) {
        super(context, wrapper);
    }

    public AnimatedSnapAdapter(@NonNull Context context, @NonNull SnapLayoutWrapper wrapper, @NonNull RecyclerView recyclerView) {
        super(context, wrapper, recyclerView);
    }

    public AnimatedSnapAdapter(@NonNull Context context, @NonNull SnapLayoutWrapper wrapper, @NonNull RecyclerView recyclerView, @NonNull ViewGroup alternateView) {
        super(context, wrapper, recyclerView, alternateView);
    }

    public AnimatedSnapAdapter(@NonNull Context context, @NonNull ArrayList<SnapLayoutWrapper> multiViewWrappers) {
        super(context, multiViewWrappers);
    }

    public AnimatedSnapAdapter(@NonNull Context context, @NonNull ArrayList<SnapLayoutWrapper> layoutWrappers, @NonNull RecyclerView recyclerView) {
        super(context, layoutWrappers, recyclerView);
    }

    public AnimatedSnapAdapter(@NonNull Context context, @NonNull ArrayList<SnapLayoutWrapper> layoutWrappers, @NonNull RecyclerView recyclerView, @NonNull ViewGroup alternateView) {
        super(context, layoutWrappers, recyclerView, alternateView);
    }

    @Override
    public void onViewDetachedFromWindow(SnapViewHolder holder) {
        ((QuoteVh) holder).clearAnimation();
    }


}
