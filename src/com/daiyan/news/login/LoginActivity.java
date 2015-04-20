package com.daiyan.news.login;

import com.daiyan.neteasenews.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private ImageView ivClose;
	private TextView tvSina;
	private TextView tvQQ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity_layout);

		initView();
	}

	private void initView() {
		ivClose = (ImageView) findViewById(R.id.iv_close);

		tvSina = (TextView) findViewById(R.id.tv_sina);
		tvQQ = (TextView) findViewById(R.id.tv_qq);

		ivClose.setOnClickListener(new MyOnclickListener());
		tvSina.setOnClickListener(new MyOnclickListener());
		tvQQ.setOnClickListener(new MyOnclickListener());
	}

	public class MyOnclickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			if (arg0.equals(ivClose)) {
				onBackPressed();
			} else if (arg0.equals(tvSina)) {
				Toast.makeText(getApplicationContext(), "Í¨¹ýÐÂÀËÕËºÅµÇÂ¼",Toast.LENGTH_SHORT).show();
			} else if (arg0.equals(tvQQ)) {
				Toast.makeText(getApplicationContext(), "Í¨¹ýQQÕËºÅµÇÂ¼",Toast.LENGTH_SHORT).show();
			}

		}

	}

	@Override
	public void onBackPressed() {
		this.finish();
		this.overridePendingTransition(R.anim.left_fade_in, R.anim.right_fade_out);
	}
}
