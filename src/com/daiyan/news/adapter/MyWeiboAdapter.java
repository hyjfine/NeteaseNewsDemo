package com.daiyan.news.adapter;

import java.util.List;

import utils.StringUtils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import base.BaseDataAdpater;

import com.daiyan.neteasenews.R;
import com.sina.weibo.sdk.openapi.models.Status;

/**
 * @author daiyan
 *
 * 2015-8-19
 */
public class MyWeiboAdapter extends BaseDataAdpater<Status>{

	/**
	 * @param lists
	 * @param context
	 */
	public MyWeiboAdapter(List<Status> lists, Context context) {
		super(lists, context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Status status = list.get(position);
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.myinfo_item, null);
			holder.tvDate = (TextView)convertView.findViewById(R.id.tv_info_date);
			holder.tvSource = (TextView)convertView.findViewById(R.id.tv_info_source);
			holder.tvText = (TextView)convertView.findViewById(R.id.tv_info_content);
			holder.tvAddress = (TextView)convertView.findViewById(R.id.tv_info_location);
			holder.tvTransmit = (TextView)convertView.findViewById(R.id.tv_info_transmit);
			holder.tvComment = (TextView)convertView.findViewById(R.id.tv_info_comment);
			holder.tvPraise = (TextView)convertView.findViewById(R.id.tv_info_praise);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		if(status!=null){
			holder.tvDate.setText(status.created_at);
			String str = status.source;
			if(!TextUtils.isEmpty(status.source)){
				str = StringUtils.subSource(status.source);
			}
			holder.tvSource.setText(str);
			holder.tvText.setText(status.text);
			holder.tvTransmit.setText("×ª·¢("+status.reposts_count+")");
			holder.tvComment.setText("ÆÀÂÛ("+status.comments_count+")");
			holder.tvPraise.setText("ÔÞ("+status.attitudes_count+")");
		}
		
		return convertView;
	}
	
	private static class ViewHolder{
		TextView tvDate;
		TextView tvSource;
		TextView tvText;
		TextView tvAddress;
		TextView tvTransmit;
		TextView tvComment;
		TextView tvPraise;
	}

}
