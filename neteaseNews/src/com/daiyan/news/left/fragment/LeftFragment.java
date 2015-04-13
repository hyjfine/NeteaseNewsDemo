package com.daiyan.news.left.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daiyan.neteasenews.R;
import com.daiyan.news.NavigationDrawerFragment;

/**
 * @Title:
 * @Description:
 * @author daiyan
 * @Company:
 * @date 2015Äê4ÔÂ8ÈÕ
 */
public class LeftFragment extends NavigationDrawerFragment {
	private String[] strings;
	private int[] picIds;
	private LinearLayout layout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		strings = getResources().getStringArray(R.array.left_fragment);
		picIds = new int[] { R.drawable.night_biz_account_head_selector_netease, R.drawable.night_biz_account_head_selector_qq, R.drawable.night_biz_account_head_selector_renren, R.drawable.night_biz_account_head_selector_sina };
		layout = (LinearLayout) inflater.inflate(R.layout.left_fragment_layout, null);

		mDrawerListView = (ListView) layout.findViewById(R.id.lv_leftfragment);
		mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectItem(strings[position], position, mTypeId);
			}
		});
		mDrawerListView.setAdapter(new MyAdapter());
		mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);

		return layout;
	}

	public class MyAdapter extends BaseAdapter {
		private ViewHolder holder;

		@Override
		public int getCount() {
			return picIds.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.left_lv_item, null);
				holder = new ViewHolder();
				holder.ivLeft = (ImageView) convertView.findViewById(R.id.iv_left_item);
				holder.tvLeft = (TextView) convertView.findViewById(R.id.tv_left_item);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.ivLeft.setImageResource(picIds[position]);
			holder.tvLeft.setText(strings[position]);
			return convertView;
		}
	}

	public class ViewHolder {
		private ImageView ivLeft;
		private TextView tvLeft;
	}
}
