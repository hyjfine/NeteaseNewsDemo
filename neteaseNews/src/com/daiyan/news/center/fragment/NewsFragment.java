package com.daiyan.news.center.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.daiyan.neteasenews.R;

/**
 * @Title:
 * @Description:新闻Fragment
 * @author daiyan
 * @Company:
 * @date 2015年4月9日
 */
public class NewsFragment extends Fragment {
	private View view;
	private PagerSlidingTabStrip tabStrip;
	private ViewPager viewpager;
	private String[] titleStrings;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		titleStrings = getResources().getStringArray(R.array.news);
		view = inflater.inflate(R.layout.news_frag_layout, null);
		
		tabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
		viewpager = (ViewPager) view.findViewById(R.id.pager);
		
		viewpager.setAdapter(new MyAdapter(getFragmentManager()));
		tabStrip.setViewPager(viewpager);
		
		return view;
	}

	public class MyAdapter extends FragmentPagerAdapter {

		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return SuperAwesomeCardFragment.newInstance(arg0);
		}

		@Override
		public int getCount() {
			return titleStrings.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titleStrings[position];
		}

	}
}
