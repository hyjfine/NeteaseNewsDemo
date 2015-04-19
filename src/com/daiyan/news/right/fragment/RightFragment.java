package com.daiyan.news.right.fragment;

import utils.IntentUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.daiyan.neteasenews.R;
import com.daiyan.news.login.LoginActivity;

/**
 * @Title:
 * @Description:
 * @author daiyan
 * @Company:
 * @date 2015Äê4ÔÂ8ÈÕ
 */
public class RightFragment extends Fragment {
	private ListView mDrawerListView;
	private View mFragmentContainerView;
	private DrawerLayout mDrawerLayout;
	private LinearLayout mLayout;
	private ImageView ivPersonLogin;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mLayout = (LinearLayout) inflater.inflate(R.layout.right_fragment_layout, null);
		mDrawerListView = (ListView)mLayout.findViewById(R.id.lv_right_fragment);
		mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mDrawerLayout.closeDrawer(mLayout);
			}
		});
		mDrawerListView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, new String[] { getString(R.string.title_section1_r), getString(R.string.title_section2_r), getString(R.string.title_section3_r) }));
		ivPersonLogin = (ImageView)mLayout.findViewById(R.id.iv_person_login);
		ivPersonLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Bundle bundle = new Bundle();
				IntentUtils.startActivity(getActivity(), LoginActivity.class, bundle);
			}
		});
		return mLayout;
	}

	public void setUp(int fragmentId, DrawerLayout mDrawLayout) {
		mFragmentContainerView = getActivity().findViewById(fragmentId);
		mDrawerLayout = mDrawLayout;
		//default close fragment
		mDrawLayout.closeDrawer(mFragmentContainerView);
	}

	public void controlRightFragment() {
		if (!mDrawerLayout.isDrawerOpen(mFragmentContainerView)) {
			mDrawerLayout.openDrawer(mFragmentContainerView);
		} else {
			mDrawerLayout.closeDrawer(mFragmentContainerView);
		}

	}
}
