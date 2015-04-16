package com.daiyan.news.right.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.daiyan.neteasenews.R;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mDrawerListView = (ListView) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
		mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mDrawerLayout.closeDrawer(mDrawerListView);
			}
		});
		mDrawerListView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, new String[] { getString(R.string.title_section1_r), getString(R.string.title_section2_r), getString(R.string.title_section3_r) }));
		return mDrawerListView;
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
