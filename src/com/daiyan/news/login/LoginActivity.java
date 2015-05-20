package com.daiyan.news.login;

import java.text.SimpleDateFormat;

import sina.AccessTokenKeeper;
import sina.Constants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daiyan.neteasenews.R;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

public class LoginActivity extends Activity {
	private ImageView ivClose;
	private TextView tvSina;
	private TextView tvQQ;

	private AuthInfo mAuthInfo;
	private SsoHandler mSsoHandler;
	private Oauth2AccessToken mAccessToken;

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

	private void initSina() {
		// ����΢��ʵ��
		// mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY,
		// Constants.REDIRECT_URL, Constants.SCOPE);
		// ������Ȩʱ���벻Ҫ���� SCOPE��������ܻ���Ȩ���ɹ�
		mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
		mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);
		// ��һ��������Ӧ�ã�AccessToken ������
		mAccessToken = AccessTokenKeeper.readAccessToken(this);
		if (mAccessToken.isSessionValid()) {
			showSinaToken(true);
		}
		// SSO ��Ȩ, ���ͻ���
		// mSsoHandler.authorizeClientSso(new AuthListener());
		// SSO ��Ȩ, ��Web��
		// mSsoHandler.authorizeWeb(new AuthListener());
		// SSO ��Ȩ, ALL IN ONE ����ֻ���װ��΢���ͻ�����ʹ�ÿͻ�����Ȩ,û�������Web��Ȩ
		mSsoHandler.authorize(new AuthListener());

	}

	public class MyOnclickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			if (arg0.equals(ivClose)) {
				onBackPressed();
			} else if (arg0.equals(tvSina)) {
				Toast.makeText(getApplicationContext(), "ͨ�������˺ŵ�¼", Toast.LENGTH_SHORT).show();
				initSina();
			} else if (arg0.equals(tvQQ)) {
				Toast.makeText(getApplicationContext(), "ͨ��QQ�˺ŵ�¼", Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * 1. SSO ��Ȩʱ����Ҫ�� {@link #onActivityResult} �е���
	 * {@link SsoHandler#authorizeCallBack} �� �ûص��Żᱻִ�С� 2. �� SSO
	 * ��Ȩʱ������Ȩ�����󣬸ûص��ͻᱻִ�С�
	 * @author daiyan
	 * 
	 */
	public class AuthListener implements WeiboAuthListener {

		@Override
		public void onCancel() {
			Toast.makeText(LoginActivity.this, "ȡ����Ȩ��", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onComplete(Bundle bundle) {
			// �� Bundle �н��� Token
			mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
			if (mAccessToken.isSessionValid()) {
				// ��ʾToken
				showSinaToken(false);
				// ����Token��SharedPreference
				AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken);
				Toast.makeText(LoginActivity.this, "��Ȩ�ɹ���", Toast.LENGTH_SHORT).show();
			} else {
				// ���¼�������������յ� Code��
				// 1. ����δ��ƽ̨��ע���Ӧ�ó���İ�����ǩ��ʱ��
				// 2. ����ע���Ӧ�ó��������ǩ������ȷʱ��
				// 3. ������ƽ̨��ע��İ�����ǩ��������ǰ���Ե�Ӧ�õİ�����ǩ����ƥ��ʱ��
				String code = bundle.getString("code");
				if (TextUtils.isEmpty(code)) {
					Toast.makeText(LoginActivity.this, "��Ȩʧ�ܣ�obtain code=" + code, Toast.LENGTH_LONG).show();
				}
			}

		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(LoginActivity.this, "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

	}

	/**
	 * ��ʾSinaToken
	 * @param hasExisted
	 */
	public void showSinaToken(boolean hasExisted) {
		String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date(mAccessToken.getExpiresTime()));
		String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
		String message = String.format(format, mAccessToken.getToken(), date);
		Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
		if (hasExisted) {
			message = getString(R.string.weibosdk_demo_token_has_existed) + "\n" + message;
			Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * �� SSO ��Ȩ Activity �˳�ʱ���ú���������
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	@Override
	public void onBackPressed() {
		this.finish();
		this.overridePendingTransition(R.anim.left_fade_in, R.anim.right_fade_out);
	}
}
