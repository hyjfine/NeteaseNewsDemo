package com.daiyan.news;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Toast;

import com.daiyan.neteasenews.R;
import com.daiyan.news.center.fragment.NewsFragment;
import com.daiyan.news.center.fragment.PlaceholderFragment;
import com.daiyan.news.left.fragment.LeftFragment;
import com.daiyan.news.right.fragment.RightFragment;

/**
 * @Title:
 * @Description:
 * @author daiyan
 * @Company:
 * @date 2015年4月8日
 */
public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
	private CharSequence mTitle;
	private CharSequence originTitle;
	private FragmentManager mFragmentManager;
	private LeftFragment mNavigationDrawerFragmentLeft;
	private RightFragment mNavigationDrawerFragmentRight;
	private Toolbar mToolbar;
	private DrawerLayout mDrawLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTitle = getTitle();
		originTitle = getTitle();
		setToolBar();
		mFragmentManager = getSupportFragmentManager();
		mNavigationDrawerFragmentLeft = (LeftFragment) mFragmentManager.findFragmentById(R.id.navigation_drawer_left);
		mNavigationDrawerFragmentRight = (RightFragment) mFragmentManager.findFragmentById(R.id.navigation_drawer_right);

		// Set up the drawer.
		mDrawLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mNavigationDrawerFragmentLeft.setUp(R.id.navigation_drawer_left, mDrawLayout, mToolbar);
		mNavigationDrawerFragmentRight.setUp(R.id.navigation_drawer_right, mDrawLayout);

		getOverflowMenu();

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
		} else if (typeId == R.id.navigation_drawer_right) { // create a new Act
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
		// actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		// 设置ActionBar标题是否显示
		actionBar.setDisplayShowTitleEnabled(true);
		// 设置ActionBar左边默认的图标是否可用
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(false);// 设置左边ic_drawer图片不显示
		actionBar.setLogo(R.drawable.night_biz_account_head_selector_qq);
		actionBar.setTitle(mTitle);
		// 设置是否显示返回键
		// mActionBar.setHomeButtonEnabled(true);
		// mActionBar.setDisplayShowCustomEnabled(true);// ???
	}

	public void restoreToolbar() {
		if (mTitle.equals(originTitle)) {// first create
			mToolbar.setTitle("");
			mToolbar.setLogo(R.drawable.base_common_default_icon_small);
			return;
		}
		if (mTitle.equals("新闻")) {
			mToolbar.setTitle("");
			mToolbar.setLogo(R.drawable.base_common_default_icon_small);
		} else {
			mToolbar.setTitle(mTitle);
			mToolbar.setLogo(android.R.color.transparent);

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		restoreToolbar();
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// int id = item.getItemId();
		// if (id == R.id.action_settings) {
		// return true;
		// }
		return super.onOptionsItemSelected(item);
	}

	// 针对部分有物理菜单按钮强制显示菜单键force to show overflow menu in actionbar for android
	// 4.4 below
	private void getOverflowMenu() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 使MenuItem显示图片 默认只显示文字
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	private void setToolBar() {
		mToolbar = (Toolbar) this.findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);

		mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.action_settings:
					Toast.makeText(MainActivity.this, "action_settings", Toast.LENGTH_SHORT).show();
					break;
				case R.id.action_person_icon:
					Toast.makeText(MainActivity.this, "action_person_icon", Toast.LENGTH_SHORT).show();
					mNavigationDrawerFragmentRight.controlRightFragment();
					break;
				default:
					break;
				}
				return true;
			}

		});
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
}
