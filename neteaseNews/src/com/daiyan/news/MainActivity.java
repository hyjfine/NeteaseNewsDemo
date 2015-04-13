package com.daiyan.news;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.daiyan.neteasenews.R;
import com.daiyan.news.fragment.NavigationDrawerFragment;
import com.daiyan.news.fragment.center.NewsFragment;
import com.daiyan.news.fragment.center.PlaceholderFragment;
import com.daiyan.news.fragment.left.LeftFragment;
import com.daiyan.news.fragment.right.RightFragment;

/**
 * @Title:
 * @Description:
 * @author daiyan
 * @Company:
 * @date 2015年4月8日
 */
public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
	private CharSequence mTitle;
	private FragmentManager mFragmentManager;
	private LeftFragment mNavigationDrawerFragmentLeft;
	private RightFragment mNavigationDrawerFragmentRight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTitle = getTitle();
		mFragmentManager = getSupportFragmentManager();
		mNavigationDrawerFragmentLeft = (LeftFragment) mFragmentManager.findFragmentById(R.id.navigation_drawer_left);
		mNavigationDrawerFragmentRight = (RightFragment) mFragmentManager.findFragmentById(R.id.navigation_drawer_right);

		// Set up the drawer.
		mNavigationDrawerFragmentLeft.setUp(R.id.navigation_drawer_left, (DrawerLayout) findViewById(R.id.drawer_layout));
		mNavigationDrawerFragmentRight.setUp(R.id.navigation_drawer_right, (DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(String title, int position, int typeId) {
		if (typeId == R.id.navigation_drawer_left) {
			// update the main ActionBar title
			onSectionAttached(title, position);
			// update the main content by replacing fragments
			if (position == 0) {
				NewsFragment fragment = new NewsFragment();
				mFragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
			} else {
				mFragmentManager.beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(position + 1, typeId)).commit();
			}
		} else if (typeId == R.id.navigation_drawer_right) {
			// create new Activity
		}
	}

	/**
	 * 调整ActionBar的标题显示mTitle
	 * 
	 * @param title 显示在ActionBar上的title
	 * @param number Fragment条目position
	 */
	public void onSectionAttached(String title, int number) {
		mTitle = title;
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		// 设置ActionBar标题是否显示
		actionBar.setDisplayShowTitleEnabled(true);
		// 设置ActionBar左边默认的图标是否可用
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setTitle(mTitle);
		// 设置是否显示返回键
		// mActionBar.setHomeButtonEnabled(true);
		// mActionBar.setDisplayShowCustomEnabled(true);// ???
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragmentLeft.isDrawerOpen() && (!mNavigationDrawerFragmentRight.isDrawerOpen())) {
			getMenuInflater().inflate(R.menu.main_menu, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
