package com.daiyan.news.fragment.right;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.daiyan.neteasenews.R;
import com.daiyan.news.fragment.NavigationDrawerFragment;

/**
 * @Title:
 * @Description:
 * @author daiyan
 * @Company:
 * @date 2015Äê4ÔÂ8ÈÕ
 */
public class RightFragment extends NavigationDrawerFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mDrawerListView = (ListView) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
		mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			}
		});
		mDrawerListView.setAdapter(new ArrayAdapter<String>(getActionBar().getThemedContext(), android.R.layout.simple_list_item_1, android.R.id.text1, new String[] { getString(R.string.title_section1_r), getString(R.string.title_section2_r), getString(R.string.title_section3_r) }));
		mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
		setUserLearnedDrawer(true);
		return mDrawerListView;
	}
}
