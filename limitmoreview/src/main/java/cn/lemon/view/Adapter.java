package cn.lemon.view;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;


/**
 * Created by linlongxin on 2016/7/21.
 */

public abstract class Adapter<T> {

    private final String TAG = "Adapter";
    private List<T> mData;
    private int count = 0;
    private LimitMoreView mView;

    public void attachView(LimitMoreView v) {
        mView = v;
    }

    public void addAll(List<T> data) {
        mData = data;
        count += data.size();
        mView.addAllViewItem();
    }

    public void addAll(T[] data) {
        this.addAll(Arrays.asList(data));
        count--;
    }

    public void add(T data) {
        mData.add(data);
        count++;
        mView.addView(onCreateItemView(mView, 0).mView);
    }

    public void remove(int position) {
        mData.remove(position);
        count--;
        mView.removeViewAt(position);
    }

    public void remove(int start, int end) {
        count = count - end + start;
        mView.removeViews(start, end - start);
    }

    public void remove(T object) {
        mView.removeViewAt(mData.indexOf(object));
        mData.remove(object);
        count--;
    }

    public void insert(T object, int position) {
        mData.add(position, object);
        count++;
        mView.addAllViewItem();
    }

    public void setVisibility(int start, int end, int visibility){
        for (int i = start; i < end; i ++){
            mView.getChildAt(i).setVisibility(visibility);
        }
    }


    public int getItemCount() {
        return count;
    }

    public int getItemType(int position) {
        return 0;
    }

    public abstract ItemView onCreateItemView(ViewGroup parent, int type);

    public abstract void onBindItemView(ItemView itemView, int position);

    public ItemView createItemView(ViewGroup parent, int type) {
        ItemView itemView = onCreateItemView(parent, type);
        itemView.itemType = type;
        return itemView;
    }

    public void bindItemView(ItemView itemView, int position) {
        onBindItemView(itemView, position);
        itemView.bindData(mData.get(position));
    }

    public static abstract class ItemView<T> {

        View mView;
        int itemType;

        public ItemView(View view) {
            mView = view;
            onCreateViewAfter();
        }

        public ItemView(ViewGroup parent, @LayoutRes int resLayout) {
            this(LayoutInflater.from(parent.getContext()).inflate(resLayout, parent, false));
        }

        public <I extends View> I findViewById(@IdRes int id) {
            return (I) mView.findViewById(id);
        }

        public View getView() {
            return mView;
        }

        public abstract void onCreateViewAfter();

        public abstract void bindData(T data);
    }
}
