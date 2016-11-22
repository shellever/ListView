package com.shellever.listview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RefreshPagingActivity extends AppCompatActivity {

    private static final int MSG_UPDATE_DATA = 1;

    private List<NewsBean> mNewsBeanList = new ArrayList<>();
    private MyDataAdapter adapter;

    private int visibleLastItemIndex;       // 用于保存可显示的最后一条数据的索引

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_paging);

        ListView mRefreshPagingLv = (ListView) findViewById(R.id.lv_refresh_paging);

        View footerView = getLayoutInflater().inflate(R.layout.loading, null);  // 实例化一个loading.xml布局文件
        mRefreshPagingLv.addFooterView(footerView);

        loadNews();

        adapter = new MyDataAdapter(this, mNewsBeanList);
        mRefreshPagingLv.setAdapter(adapter);

        mRefreshPagingLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if((adapter.getCount() == visibleLastItemIndex) && (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE)){
                    new Thread(new LoadingThread()).start();        // 启动子线程
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                visibleLastItemIndex = firstVisibleItem + visibleItemCount - 1;         // visibleLastItemIndex
            }
        });
    }

    private int index = 1;

    private void loadNews() {
        for (int i = 0; i < 12; i++) {
            NewsBean news = new NewsBean();
            news.setTitle("Title - " + index);
            news.setContent("The content - " + index);
            mNewsBeanList.add(news);
            index++;
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_UPDATE_DATA:
                    adapter.notifyDataSetChanged();     // 通知数据集改变消息
                    break;
            }
        }
    };


    private class LoadingThread implements Runnable{
        @Override
        public void run() {
            loadNews();                 // 生成另一页数据
            try {
                Thread.sleep(2000);     // 2sec
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(MSG_UPDATE_DATA);  // 发送数据更新消息
        }
    }


    private class MyDataAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private List<NewsBean> mNewsBeanList;

        public MyDataAdapter(Context context, List<NewsBean> newsBeanList) {
            mNewsBeanList = newsBeanList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mNewsBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return mNewsBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.refresh_paging_list_item, null);
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_news_title);
                holder.tv_content = (TextView) convertView.findViewById(R.id.tv_news_content);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            NewsBean news = mNewsBeanList.get(position);
            holder.tv_title.setText(news.getTitle());
            holder.tv_content.setText(news.getContent());

            return convertView;
        }

        private class ViewHolder {
            TextView tv_title;
            TextView tv_content;
        }
    }
}
