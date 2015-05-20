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
		// 创建微博实例
		// mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY,
		// Constants.REDIRECT_URL, Constants.SCOPE);
		// 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
		mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
		mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);
		// 第一次启动本应用，AccessToken 不可用
		mAccessToken = AccessTokenKeeper.readAccessToken(this);
		if (mAccessToken.isSessionValid()) {
			showSinaToken(true);
		}
		// SSO 授权, 仅客户端
		// mSsoHandler.authorizeClientSso(new AuthListener());
		// SSO 授权, 仅Web端
		// mSsoHandler.authorizeWeb(new AuthListener());
		// SSO 授权, ALL IN ONE 如果手机安装了微博客户端则使用客户端授权,没有则进行Web授权
		mSsoHandler.authorize(new AuthListener());

	}

	public class MyOnclickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			if (arg0.equals(ivClose)) {
				onBackPressed();
			} else if (arg0.equals(tvSina)) {
				Toast.makeText(getApplicationContext(), "通过新浪账号登录", Toast.LENGTH_SHORT).show();
				initSina();
			} else if (arg0.equals(tvQQ)) {
				Toast.makeText(getApplicationContext(), "通过QQ账号登录", Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用
	 * {@link SsoHandler#authorizeCallBack} 后， 该回调才会被执行。 2. 非 SSO
	 * 授权时，当授权结束后，该回调就会被执行。
	 * @author daiyan
	 * 
	 */
	public class AuthListener implements WeiboAuthListener {

		@Override
		public void onCancel() {
			Toast.makeText(LoginActivity.this, "取消授权！", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onComplete(Bundle bundle) {
			// 从 Bundle 中解析 Token
			mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
			if (mAccessToken.isSessionValid()) {
				// 显示Token
				showSinaToken(false);
				// 保存Token到SharedPreference
				AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken);
				Toast.makeText(LoginActivity.this, "授权成功！", Toast.LENGTH_SHORT).show();
			} else {
				// 以下几种情况，您会收到 Code：
				// 1. 当您未在平台上注册的应用程序的包名与签名时；
				// 2. 当您注册的应用程序包名与签名不正确时；
				// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
				String code = bundle.getString("code");
				if (TextUtils.isEmpty(code)) {
					Toast.makeText(LoginActivity.this, "授权失败，obtain code=" + code, Toast.LENGTH_LONG).show();
				}
			}

		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(LoginActivity.this, "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

	}

	/**
	 * 显示SinaToken
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
	 * 当 SSO 授权 Activity 退出时，该函数被调用
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
