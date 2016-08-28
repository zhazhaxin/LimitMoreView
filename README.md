#LimitMoreView

> 在开发中有的时候会遇到类似ListView列表显示，但是数据却只有几条的情况，
  这个时候如果使用ListView，那么效率和性能都不好，LimitMoreView提供了
  与ListView相似的API，用来解决这个问题。

```
    complie 'cn.Lemon:limitmoreview:0.1.1'
```

##使用

```
 mAdapter = new MyAdapter();
 mLimitMoreView.setAdapter(mAdapter);
 mAdapter.addAll(getVirtualData());
```

 - 自定义Adapter

```
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
```

 - 自定义Item类型

```
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
```

 - demo

<image src="demo.png" width="320" height="564">