package com.daiyan.news.center.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daiyan.neteasenews.R;

/**
 * @Title:
 * @Description:
 * @author daiyan
 * @Company:
 * @date 2015年4月8日
 */
public class PlaceholderFragment extends Fragment {
	private static final String ARG_SECTION_NUMBER = "section_number";
	private static final String TYPE_ID = "type_id";

	public PlaceholderFragment() {
	}

	public static PlaceholderFragment newInstance(int sectionNumber, int typeId) {
		PlaceholderFragment fragment = new PlaceholderFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		args.putInt(TYPE_ID, typeId);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		TextView textView = (TextView) rootView.findViewById(R.id.section_label);
		textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
		return rootView;
	}

	/**
	 * Fragment被创建时首先被调用，并修改ActionBar title
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.i("method", "onAttach__PlaceholdFragment_设置ActionBar title");
//		((MainActivity) activity).onSectionAttached(null, getArguments().getInt(ARG_SECTION_NUMBER), getArguments().getInt(TYPE_ID));
	}
}
