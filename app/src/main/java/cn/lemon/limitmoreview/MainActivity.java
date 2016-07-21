package cn.lemon.limitmoreview;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.lemon.view.Adapter;
import cn.lemon.view.LimitMoreView;

public class MainActivity extends AppCompatActivity {

    private LimitMoreView mLimitMoreView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLimitMoreView = (LimitMoreView) findViewById(R.id.limit_more_view);

        mAdapter = new MyAdapter();
        mLimitMoreView.setAdapter(mAdapter);
        mAdapter.addAll(getVirtualData());
    }

    public List<String> getVirtualData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("item position : " + i);
        }
        return data;
    }


    class MyAdapter extends Adapter<String> {

        public final int TYPE_ONE = 1;
        public final int TYPE_TWO = 2;

        @Override
        public int getItemType(int position) {
            if (position % 2 == 0) {
                return TYPE_ONE;
            } else {
                return TYPE_TWO;
            }
        }

        @Override
        public ItemView onCreateItemView(ViewGroup parent, int type) {
            if (type == TYPE_ONE) {
                return new MyItemView(parent, R.layout.item);
            } else {
                return new TypeItemView(parent, R.layout.item_type);
            }

        }

        @Override
        public void onBindItemView(ItemView itemView, int position) {

        }

    }

    class MyItemView extends Adapter.ItemView<String> {

        private TextView mTextView;

        public MyItemView(ViewGroup parent, @LayoutRes int resLayout) {
            super(parent, resLayout);
        }

        @Override
        public void onCreateViewAfter() {
            mTextView = findViewById(R.id.text);
        }

        @Override
        public void bindData(String data) {
            mTextView.setText(data);
        }
    }

    class TypeItemView extends Adapter.ItemView<String> {

        private Button button;

        public TypeItemView(ViewGroup parent, @LayoutRes int resLayout) {
            super(parent, resLayout);
        }

        @Override
        public void onCreateViewAfter() {
            button = findViewById(R.id.button);
        }

        @Override
        public void bindData(String data) {
            button.setText(data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLimitMoreView.destroy();
    }
}
