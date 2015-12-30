package com.liutaw.pulldowntorefreshuptoloadmore;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.liutaw.pulldowntorefreshuptoloadmore.listview.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RefreshLayout refreshlayout;
    ListView listView;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new MyAdapter(this);
        refreshlayout = (RefreshLayout) this.findViewById(R.id.refreshlayout);
        listView = (ListView) this.findViewById(R.id.listview);
        listView.setAdapter(adapter);
        refreshlayout.setChildView(listView);

        //refresh listener
        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clearData();
                index = 0;
                refresh();
            }
        });

        refreshlayout.setRefreshing(true, getString(R.string.waiting));
        refreshlayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                refresh();
            }
        });
        refresh();//first refresh
    }

    int index = 0;
    public void refresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<String> lists = new ArrayList<String>();
                for (int i=0;i<10;i++) {
                    lists.add(index+"new news title " + i);
                }
                index++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addData(lists);
                        refreshlayout.stopRefreshAndLoading();
                        refreshlayout.setRefreshing(false, getString(R.string.find) + lists.size() + getString(R.string.data));
                        if (index>2) {
                            refreshlayout.showFooter(getString(R.string.nomoredata),false);
                        } else {
                            refreshlayout.showFooter(getString(R.string.load_more),true);
                        }
                    }
                });
            }
        }).start();
    }

    public void loadMore() {

    }
}
