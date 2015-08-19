package base;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author daiyan
 * 
 *         2015-8-19
 */
public class BaseDataAdpater<T> extends BaseAdapter {
	protected List<T> list;
	protected Context context;

	public BaseDataAdpater(List<T> lists, Context context) {
		this.list = lists;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list != null && list.size() > 0 ? list.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return list != null && list.size() > 0 ? position : 0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}

}
