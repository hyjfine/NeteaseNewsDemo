package com.daiyan.news;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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

	private LeftFragment mNavigationDrawerFragment;
	private CharSequence mTitle;
	private RightFragment mNavigationDrawerFragmentRight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (LeftFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		mNavigationDrawerFragmentRight = (RightFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_right);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
		mNavigationDrawerFragmentRight.setUp(R.id.navigation_drawer_right, (DrawerLayout) findViewById(R.id.drawer_layout));

	}

	@Override
	public void onNavigationDrawerItemSelected(int position, int typeId) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		if (position == 0 && typeId == R.id.navigation_drawer) {// 暂时定死
			NewsFragment fragment = new NewsFragment();
			fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
		} else {
			fragmentManager.beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(position + 1, typeId)).commit();
		}

	}

	/**
	 * 调整ActionBar的标题显示mTitle
	 * 
	 * @param number
	 * @param typeId
	 */
	public void onSectionAttached(int number, int typeId) {
		Log.i("typeId", "typeId==" + typeId);
		if (typeId == R.id.navigation_drawer) {
			switch (number) {
			case 1:
				mTitle = getString(R.string.title_section1);
				break;
			case 2:
				mTitle = getString(R.string.title_section2);
				break;
			case 3:
				mTitle = getString(R.string.title_section3);
				break;
			case 4:
				mTitle = getString(R.string.title_section4);
				break;
			}
		} else if (typeId == R.id.navigation_drawer_right) {
			switch (number) {
			case 1:
				mTitle = getString(R.string.title_section1_r);
				break;
			case 2:
				mTitle = getString(R.string.title_section2_r);
				break;
			case 3:
				mTitle = getString(R.string.title_section3_r);
				break;
			}
		}

	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
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
