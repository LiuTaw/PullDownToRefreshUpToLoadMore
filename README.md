# PullDownToRefreshUpToLoadMore

------
<video id="video" controls="" preload="none" poster="http://media.w3.org/2010/05/sintel/poster.png">
      <source id="mp4" src="https://github.com/LiuTaw/PullDownToRefreshUpToLoadMore/raw/master/demo.mp4" type="video/mp4">
      <p>Your user agent does not support the HTML5 Video element.</p>
    </video>
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

when done
>call  refreshlayout.stopRefreshAndLoading(); to stop refresh and load animation

you can change footer or header text at anytime when you need:
>refreshlayout.setRefreshing(false,"loading...");
refreshlayout.showFooter("footer text",false);


###Thanks:
####This project based on two libraries:
> * SwipeRefreshLayout
 android.support.v4.widget.SwipeRefreshLayout
> * https://github.com/Demievil/SwipeRefreshLayout Demievil gave me a hint to this code,thanks.
