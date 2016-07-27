package cn.lemon.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by linlongxin on 2016/7/21.
 */

public class LimitMoreView extends LinearLayout {

    private final String TAG = "LimitMoreView";
    private Handler mHandler;
    private Adapter mAdapter;

    public LimitMoreView(Context context) {
        this(context, null, 0);
    }

    public LimitMoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LimitMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.setOrientation(VERTICAL);
        Util.initialize((Activity) context);
        mHandler = new Handler(Looper.getMainLooper());
    }


    /**
     * 先移除LimitMoreView中现有的view，添加adapter中所有的view
     */
    public void addAllViewItem() {
        if (mAdapter == null) {
            throw new RuntimeException("Adapter is null");
        }
        removeAllViews();
        Adapter.ItemView itemView;
        int itemType;
        for (int position = 0; position < mAdapter.getItemCount(); position++) {
            itemType = mAdapter.getItemType(position);  //根据position获取itemType
            itemView = mAdapter.createItemView(this, itemType);
            addView(itemView.mView);
            mAdapter.bindItemView(itemView, position);

            Log.i(TAG, "item type : " + itemType + " --- position : " + position);
        }
    }

    /**
     * 默认竖直方向布局
     *
     * @param orientation
     */
    public void setOrientation(int orientation) {
        super.setOrientation(orientation);
    }


    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        mAdapter.attachView(this);
        if (adapter.getItemCount() != 0) {
            addAllViewItem();
        }
    }

    /**
     * 设置多个子view的VISIBILITY
     * @param start
     * @param end
     * @param visibility
     */
    public void setVisibility(int start, int end, final int visibility){
        if(end > mAdapter.getItemCount() || start < 0){
            throw new IllegalAccessError("start must more than 0 and end must less than view count");
        }
        if (visibility == View.VISIBLE) {
            for (int i = start; i < end; i++) {
                final View view  = getChildAt(i);
                final ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX",-Util.getScreenWidth(),0f);
                animator.setDuration(300);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        view.setVisibility(visibility);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animator.start();
                    }
                }, 100 * (i - start));
            }
        } else if (visibility == View.GONE) {
            for (int i = end - 1; i >= start; i--) {
                final View view = getChildAt(i);
                final ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", -Util.getScreenWidth());
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(visibility);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animator.start();
                    }
                }, 100 * (end - i - 1));
            }
        }
    }

}
