package cn.lemon.limitmoreview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
        public final int TYPE_THREE = 3;

        @Override
        public int getItemType(int position) {
            if (position == getItemCount() - 1) {
                return TYPE_THREE;
            } else if (position % 2 == 0) {
                return TYPE_ONE;
            } else {
                return TYPE_TWO;
            }
        }

        @Override
        public ItemView onCreateItemView(ViewGroup parent, int type) {
            switch (type) {
                case TYPE_TWO:
                    return new ButtonItemView(parent);
                case TYPE_THREE:
                    return new SwitchItemView(parent);
                default:
                    return new TextItemView(parent);
            }

        }

        @Override
        public void onBindItemView(ItemView itemView, int position) {

        }

    }

    class TextItemView extends Adapter.ItemView<String> {

        private TextView mTextView;

        public TextItemView(ViewGroup parent) {
            super(parent, R.layout.item_text);
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

    class ButtonItemView extends Adapter.ItemView<String> {

        private Button button;

        public ButtonItemView(ViewGroup parent) {
            super(parent, R.layout.item_button);
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

    class SwitchItemView extends Adapter.ItemView {

        private TextView mTextView;

        public SwitchItemView(ViewGroup parent) {
            super(parent, R.layout.item_switch);
        }

        @Override
        public void onCreateViewAfter() {
            mTextView = (TextView) findViewById(R.id.sw);
        }

        @Override
        public void bindData(Object data) {
            getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTextView.getText().toString().equals("收起")) {
                        mAdapter.setVisibility(3,9,View.GONE);
                        mTextView.setText("打开");
                    } else {
                        mAdapter.setVisibility(3,9,View.VISIBLE);
                        mTextView.setText("收起");
                    }
                }
            });
        }
    }

}
