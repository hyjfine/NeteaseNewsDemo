package com.daiyan.news.controller;

import sina.AccessTokenKeeper;
import sina.Constants;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.daiyan.news.MainActivity;
import com.daiyan.news.center.fragment.MyWeiboFragment;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.StatusList;

/**
 * @author daiyan
 * 
 *         2015-8-19
 */
public class MyWeiboController {
	private MyWeiboFragment mFragment;
	private MainActivity mActivity;
	private int page = 1;// ���صڼ�ҳ
	private int pageSize = 5;// һҳ��������
	private Oauth2AccessToken mAccessToken;
	private StatusesAPI mStatusesAPI;
	private static final String TAG = MyWeiboController.class.getName();
	private boolean isLoadMore;
	private long uid;

	public MyWeiboController(MyWeiboFragment mFragment) {
		this.mFragment = mFragment;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * ������������
	 */
	public void requestData() {
		mActivity = (MainActivity) mFragment.getActivity();
		// ����ӿ�
		mAccessToken = AccessTokenKeeper.readAccessToken(mActivity);
		// ��statusAPIʵ����
		mStatusesAPI = new StatusesAPI(mActivity, Constants.APP_KEY, mAccessToken);
		uid = Long.parseLong(mAccessToken.getUid());
//		mStatusesAPI.userTimeline(uid, Constants.userName, 0L, 0L, pageSize, page, false, 0, false, mRequestListener);
		 mStatusesAPI.friendsTimeline(0L, 0L, pageSize, page, false, 0, false,
		 mRequestListener);
	}

	public void loadData(boolean isLoadMore) {
		this.isLoadMore = isLoadMore;
		if (isLoadMore) {
			page++;
		} else {
			page = 1;
		}
		mStatusesAPI.friendsTimeline(0L, 0L, pageSize, page, false, 0, false, mRequestListener);
//		mStatusesAPI.userTimeline(uid, Constants.userName, 0L, 0L, pageSize, page, false, 0, false, mRequestListener);
		
	}

	// ΢��API�ص��ӿ�
	private RequestListener mRequestListener = new RequestListener() {

		@Override
		public void onWeiboException(WeiboException e) {
			Log.i(TAG, e.getMessage());
			ErrorInfo info = ErrorInfo.parse(e.getMessage());
			Toast.makeText(mActivity, info.toString(), Toast.LENGTH_LONG).show();
		}

		@Override
		public void onComplete(String response) {
			if (!TextUtils.isEmpty(response)) {
//				Log.i(TAG, response);
				// tvResult.setText(response);
				if (response.startsWith("{\"statuses\"")) {
					StatusList statuses = StatusList.parse(response);
					if (statuses != null && statuses.total_number > 0) {
						Toast.makeText(mActivity, "�ɹ���ȡ΢��������" + statuses.statusList.size() + "pageNo:" + page, Toast.LENGTH_LONG).show();
						mFragment.updateList(statuses.statusList, isLoadMore);
						if (statuses.statusList == null || statuses.statusList.size() == 0) {
							if (isLoadMore) {
								if (page > 1) {
									page--;
									Toast.makeText(mActivity, "�Ѿ������һҳ�ˣ�", Toast.LENGTH_LONG).show();

								}
							}
						}
					}
				}
			}
		}
	};

}
