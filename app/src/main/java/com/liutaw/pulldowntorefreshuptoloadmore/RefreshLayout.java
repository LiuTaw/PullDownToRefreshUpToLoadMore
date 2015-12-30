package com.liutaw.pulldowntorefreshuptoloadmore;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;
import android.widget.TextView;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
public class RefreshLayout extends SwipeRefreshLayout {

    private final int mTouchSlop;
    private ListView mListView;
    private OnLoadListener mOnLoadListener;
    private float firstTouchY;
    private float lastTouchY;
    private boolean isLoading = false;

    View loadmore;
    TextView text_more;
    CircleProgressBar circleProgressBar;
    private boolean isLoadMoreOn = true;
    private View header;
    TextView headText;
    View view_head;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setChildView(ListView mListView) {
        setChildView(mListView, true);
        init();
    }

    private void init() {
        header = LayoutInflater.from(getContext()).inflate(R.layout.layout_header, null);
        getmListView().addHeaderView(header);
        getmListView().setHeaderDividersEnabled(false);

        headText = (TextView) header.findViewById(R.id.text_result);
        view_head = header.findViewById(R.id.view_head);
        hideHeader();
        hideFooter();
    }

    public View getHeader() {
        return header;
    }

    public void setHeader(View header) {
        this.header = header;
    }

    public void showHeader() {
        view_head.setVisibility(VISIBLE);
    }

    public void showHeader(String text) {
        showHeader();
        headText.setText(text);
    }

    public void setHeadText(String text) {
        headText.setText(text);
    }

    public void hideHeader() {
        view_head.setVisibility(GONE);
    }

    public void setRefreshing(boolean loading, String text) {
        setRefreshing(loading);
        showHeader(text);
    }

    public void showFooter() {
        getLoadmore().findViewById(R.id.footer_layout).setVisibility(VISIBLE);
    }

    public void showFooter(String text, boolean enable) {
        showFooter();
//        setLoadMoreText(text, enable);
        TextView textView = (TextView) getLoadmore().findViewById(R.id.text_more);
        textView.setText(text);
        textView.setEnabled(enable);
        setIsLoadMoreOn(enable);
    }

    public void hideFooter() {
        getLoadmore().findViewById(R.id.footer_layout).setVisibility(GONE);
    }

    int itemPosition = 0;
    int offsetY = 0;

    public void setChildView(ListView mListView, boolean isNeedHeader) {
        isLoadMoreOn = isNeedHeader;
        this.mListView = mListView;
        if (isNeedHeader) {
            mListView.setFooterDividersEnabled(false);
            loadmore = LayoutInflater.from(getContext()).inflate(R.layout.layout_loadmore, null);
            text_more = (TextView) loadmore.findViewById(R.id.text_more);
            circleProgressBar = (CircleProgressBar) loadmore.findViewById(R.id.load_progress_bar);
            text_more.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setLoading(true);
                }
            });
            this.mListView.addFooterView(loadmore);
        }
    }

    private boolean isPullingUp = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                firstTouchY = event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                lastTouchY = event.getRawY();
                isPullingUp = false;
                if (canLoadMore()) {
                    loadData();
                }
                break;
//            case MotionEvent.ACTION_MOVE:
//                if (canLoadMore()) {
//                    if (!isPullingUp) {
//                        text_more.setVisibility(GONE);
//                        circleProgressBar.setVisibility(VISIBLE);
//                        isPullingUp = true;
//                    }
//
////                    loadData();
//                }
//                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    private boolean canLoadMore() {
        return isLoadMoreOn && isBottom() && !isLoading && isPullingUp();
    }

    private boolean isBottom() {
        if (mListView == null) return false;
        if (mListView.getCount() > 0) {
            if (mListView.getLastVisiblePosition() == mListView.getAdapter().getCount() - 1 &&
                    mListView.getChildAt(mListView.getChildCount() - 1).getBottom() <= mListView.getHeight()) {
                return true;
            }
        }
        return false;
    }

    private boolean isPullingUp() {
        return (firstTouchY - lastTouchY) >= mTouchSlop;
    }

    private void loadData() {
        if (mOnLoadListener != null) {
            setLoading(true);
        }
    }

    //    private int selection=0;
    int index;
    int top;

    public void setLoading(boolean loading) {
        if (mListView == null) return;
        if (mOnLoadListener == null) return;
        isLoading = loading;
        if (loading) {
            if (isRefreshing()) {
//                setRefreshing(false);
                return;
            }
            index = mListView.getFirstVisiblePosition();
            View v = mListView.getChildAt(0);
            top = (v == null) ? 0 : (v.getTop() - mListView.getPaddingTop());

//            selection = mListView.getAdapter().getCount() - 1;
            text_more.setVisibility(GONE);
            circleProgressBar.setVisibility(VISIBLE);
            mOnLoadListener.onLoad();
        } else {
            mListView.setSelectionFromTop(itemPosition + 1, offsetY);
//            mListView.setSelection(selection);
            mListView.setSelectionFromTop(index, top);
            firstTouchY = 0;
            lastTouchY = 0;
            text_more.setVisibility(VISIBLE);
            circleProgressBar.setVisibility(GONE);
        }
    }

    public void setOnLoadListener(final OnLoadListener loadListener) {
        OnLoadListener injectListener = new OnLoadListener() {
            @Override
            public void onLoad() {
                showFooter();
                loadListener.onLoad();
            }
        };
        mOnLoadListener = loadListener;
    }

    public interface OnLoadListener {
        void onLoad();
    }

    @Override
    public void setRefreshing(boolean refreshing) {

        if (refreshing) {
            if (isLoading) {
                return;
            }
            if (loadmore != null) {
                loadmore.setVisibility(GONE);
            }
        } else {
            if (loadmore != null) {
                loadmore.setVisibility(VISIBLE);
            }
        }
        super.setRefreshing(refreshing);
    }

    public void stopRefreshAndLoading() {
        if (isRefreshing()) {
            setRefreshing(false);
        }
        if (isLoading) {
            setLoading(false);
        }
    }

    public void disableLoadMoreView() {
        loadmore.setVisibility(GONE);
    }

    public void setLoadMoreText(String text, boolean isClickable) {
        showFooter();
        if (text_more == null) return;
        text_more.setEnabled(isClickable);
        text_more.setText(text);
        if (!isClickable) {
            isLoadMoreOn = false;
        }
    }

    public boolean isLoadMoreOn() {
        return isLoadMoreOn;
    }

    public void setIsLoadMoreOn(boolean isLoadMoreOn) {
        this.isLoadMoreOn = isLoadMoreOn;
    }

    public ListView getmListView() {
        return mListView;
    }

    public View getLoadmore() {
        return loadmore;
    }

    @Override
    public void setOnRefreshListener(final OnRefreshListener listener) {
        OnRefreshListener temp = new OnRefreshListener() {
            @Override
            public void onRefresh() {
                listener.onRefresh();
                showHeader(getContext().getString(R.string.searching));
            }
        };
        super.setOnRefreshListener(temp);
    }
}