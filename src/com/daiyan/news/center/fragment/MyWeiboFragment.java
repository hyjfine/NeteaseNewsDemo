package com.daiyan.news.center.fragment;

import java.util.ArrayList;
import java.util.List;

import pull.PullToRefreshListView;
import pull.PullToRefreshListView.PullAndRefreshListViewListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daiyan.neteasenews.R;
import com.daiyan.news.adapter.MyWeiboAdapter;
import com.daiyan.news.controller.MyWeiboController;
import com.sina.weibo.sdk.openapi.models.Status;

/**
 * 查看我的微博内容
 * @author daiyan
 * 
 *         2015-8-17
 */
public class MyWeiboFragment extends Fragment {
	private PullToRefreshListView mListView;
	private View view;
	private TextView tvResult;
	private List<Status> mLists;
	private MyWeiboController mController;
	private MyWeiboAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mLists = new ArrayList<Status>();
		view = inflater.inflate(R.layout.myinfo_layout, null);
		tvResult = (TextView) view.findViewById(R.id.tv_myWeibo);
		
		mListView = (PullToRefreshListView) view.findViewById(R.id.lv_myinfo);
		mAdapter = new MyWeiboAdapter(mLists, this.getActivity());
		mListView.setAdapter(mAdapter);
		mListView.setHeaderDividersEnabled(false);
		mListView.setTimeTag(getClass().getName());
		mListView.setPullAndRefreshListViewListener(refreshListViewListener);

		//请求数据
		mController = new MyWeiboController(this);
		mController.requestData();
		loadData(false);

		return view;
	}
	PullAndRefreshListViewListener refreshListViewListener = new PullAndRefreshListViewListener() {

		@Override
		public void onRefresh() {
			loadData(false);
		}

		@Override
		public void onLoadMore() {
			loadData(true);
		}
	};

	private void loadData(boolean isLoadMore){
		mController.loadData(isLoadMore);
	}
	/**
	 * 拿到数据后刷新list
	 */
	public void updateList(List<Status> lists,boolean isLoadMore){
		if (!isLoadMore) {
            this.mLists.clear();
        }
		mLists.addAll(lists);
		mAdapter.notifyDataSetChanged();
		mListView.stopLoadMore();
		mListView.stopRefresh();
		if(this.mLists.size()<mController.getPageSize()){
			mListView.setPullLoadEnable(false);
		}else{
			mListView.setPullLoadEnable(true);
		}
	}
	

	

	
}
