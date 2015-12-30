# PullDownToRefreshUpToLoadMore
项目里面需要一个下拉刷新操作，我网上找一些源码，但都不怎么理想，后来发现了Demievil的RefreshLayout，个人觉得还不错，但是有一些缺点：上拉加载之后不能保持滚动的位置，头部和尾部如果我需要自定义样式的时候会很麻烦。于是我基于他的源码，我加入了一个文字自定义样式，可以在listview的头部和尾部显示一些文字提示给用户。本项目经过简单修改可以改变成更多样式。
------
<video id="video" controls="" preload="none" poster="http://media.w3.org/2010/05/sintel/poster.png">
      <source id="mp4" src="https://github.com/LiuTaw/PullDownToRefreshUpToLoadMore/raw/master/demo.mp4" type="video/mp4">
      <p>Your user agent does not support the HTML5 Video element.</p>
    </video>
    ![demo](https://github.com/LiuTaw/PullDownToRefreshUpToLoadMore/raw/master/demo.gif)
###Features:
> * Support listview
> * Support load more operation(Pull Up)
> * Retain loading selection
> * Start and stop refresh or load more manually
###How to use:
layout:
> <com.liutaw.pulldowntorefreshuptoloadmore.RefreshLayout
       android:id="@+id/refreshlayout"
       android:layout_width="match_parent"
       android:layout_height="match_parent">
       <ListView
           android:id="@+id/listview"
           android:layout_width="match_parent"
           android:layout_height="wrap_content">
       </ListView>
   </com.liutaw.pulldowntorefreshuptoloadmore.RefreshLayout>
   
bind the view:
>listView.setAdapter(adapter);
        refreshlayout.setChildView(listView);

set listeners:
>setOnRefreshListener (pull down to refresh)
setOnLoadListener (pull up to load more)

when finished:
>call  refreshlayout.stopRefreshAndLoading(); to stop refresh and load animation

you can change footer or header text at anytime when you need:
>refreshlayout.setRefreshing(false,"loading...");
refreshlayout.showFooter("footer text",false);


###Thanks:
####This project based on two libraries:
> * SwipeRefreshLayout
 android.support.v4.widget.SwipeRefreshLayout
> * https://github.com/Demievil/SwipeRefreshLayout Demievil gave me a hint to this code,thanks.
