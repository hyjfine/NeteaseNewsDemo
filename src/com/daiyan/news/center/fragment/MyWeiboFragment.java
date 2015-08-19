package com.daiyan.news.center.fragment;

import sina.AccessTokenKeeper;
import sina.Constants;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daiyan.neteasenews.R;
import com.daiyan.news.MainActivity;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.StatusList;

/**
 * 查看我的微博内容
 * @author daiyan
 * 
 *         2015-8-17
 */
public class MyWeiboFragment extends Fragment {
	private static final String TAG = MyWeiboFragment.class.getName();
	private ListView mListView;
	private Oauth2AccessToken mAccessToken;
	private StatusesAPI mStatusesAPI;
	private View view;
	private MainActivity mActivity;
	private TextView tvResult;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mActivity = (MainActivity) this.getActivity();
		view = inflater.inflate(R.layout.myinfo_layout, null);
		mListView = (ListView) view.findViewById(R.id.lv_myinfo);
		tvResult = (TextView)view.findViewById(R.id.tv_myWeibo);
//		mListView.setAdapter(new MyAdapter());

		// 请求接口
		mAccessToken = AccessTokenKeeper.readAccessToken(mActivity);
		// 对statusAPI实例化
		mStatusesAPI = new StatusesAPI(mActivity, Constants.APP_KEY, mAccessToken);
		long uid = Long.parseLong(mAccessToken.getUid());
		mStatusesAPI.userTimeline(uid, Constants.userName, 0L, 0L, 4, 1, false, 0, false, mRequestListener);
		// mStatusesAPI.friendsTimeline(0L, 0L, 10, 1, false, 0, false,
		// mRequestListener);

		return view;
	}
	
	// 微博API回调接口
		private RequestListener mRequestListener = new RequestListener() {

			@Override
			public void onWeiboException(WeiboException e) {
				Log.i(TAG, e.getMessage());
				ErrorInfo info = ErrorInfo.parse(e.getMessage());
				Toast.makeText(mActivity, info.toString(), Toast.LENGTH_LONG).show();
				tvResult.setText(e.getMessage());
			}

			@Override
			public void onComplete(String response) {
				if (!TextUtils.isEmpty(response)) {
					Log.i(TAG, response);
					tvResult.setText(response);
					if (response.startsWith("{\"statuses\"")) {
						StatusList statuses = StatusList.parse(response);
						if (statuses != null && statuses.total_number > 0) {
							Toast.makeText(mActivity, "成功获取微博数量：" + statuses.statusList.size(), Toast.LENGTH_LONG).show();
						}
					}
				}
			}
		};
}
