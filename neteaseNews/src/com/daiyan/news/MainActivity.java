package com.daiyan.news;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;

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
 * @date 2015��4��8��
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
		} else if (typeId == R.id.navigation_drawer_right) {
			// create new Activity
		}
	}

	/**
	 * ����ActionBar�ı�����ʾmTitle
	 * 
	 * @param title ��ʾ��ActionBar�ϵ�title
	 * @param number Fragment��Ŀposition
	 */
	public void onSectionAttached(String title, int number) {
		mTitle = title;
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		// actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		// ����ActionBar�����Ƿ���ʾ
		actionBar.setDisplayShowTitleEnabled(true);
		// ����ActionBar���Ĭ�ϵ�ͼ���Ƿ����
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(false);//�������ic_drawerͼƬ����ʾ
		actionBar.setLogo(R.drawable.night_biz_account_head_selector_qq);
		actionBar.setTitle(mTitle);
		// �����Ƿ���ʾ���ؼ�
		// mActionBar.setHomeButtonEnabled(true);
		// mActionBar.setDisplayShowCustomEnabled(true);// ???
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		restoreActionBar();
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// ��Բ���������˵���ťǿ����ʾ�˵���force to show overflow menu in actionbar for android 4.4 below
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
	
	//ʹMenuItem��ʾͼƬ  Ĭ��ֻ��ʾ���� 
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
	
}
