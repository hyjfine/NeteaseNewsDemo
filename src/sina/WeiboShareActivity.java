package sina;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.daiyan.neteasenews.R;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler.Response;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;

public class WeiboShareActivity extends Activity implements Response {
	public static final int SHARE_CLIENT = 1;
	public static final int SHARE_ALL_IN_ONE = 2;

	private int mShareType = SHARE_CLIENT;

	private IWeiboShareAPI mWeiboShareAPI;
	private TextView tvRegister;
	private TextView btnShare;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.weibo_share_activity);

		// 创建微博 SDK 接口实例
		mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constants.APP_KEY);
		// 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
		// 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
		// NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
		mWeiboShareAPI.registerApp();

		// 当 Activity 被重新初始化时（该 Activity 处于后台时，可能会由于内存不足被杀掉了），
		// 需要调用 {@link IWeiboShareAPI#handleWeiboResponse} 来接收微博客户端返回的数据。
		// 执行成功，返回 true，并调用 {@link IWeiboHandler.Response#onResponse}；
		// 失败返回 false，不调用上述回调
		if (savedInstanceState != null) {
			mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
		}
		initView();
	}

	private void initView() {
		// 获取微博客户端相关信息，如是否安装、支持 SDK 的版本
		boolean isInstalledWeibo = mWeiboShareAPI.isWeiboAppInstalled();
		int supportApiLevel = mWeiboShareAPI.getWeiboAppSupportAPI();
		tvRegister = (TextView) findViewById(R.id.tv_reg_sina);
		btnShare = (TextView) findViewById(R.id.btn_share_sina);
		btnShare.setEnabled(false);
		tvRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 注册到新浪微博
				mWeiboShareAPI.registerApp();
				Toast.makeText(WeiboShareActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
				btnShare.setEnabled(true);
			}
		});

		btnShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Toast.makeText(WeiboShareActivity.this, "微博分享---", Toast.LENGTH_LONG).show();
				// Intent i = new Intent(UserAPIActivity.this,
				// WBShareActivity.class);
				// i.putExtra(WBShareActivity.KEY_SHARE_TYPE,
				// WBShareActivity.SHARE_ALL_IN_ONE);
				// startActivity(i);
				sendMessage();
			}
		});
	}

	/**
	 * @see {@link Activity#onNewIntent}
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		// 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
		// 来接收微博客户端返回的数据；执行成功，返回 true，并调用
		// {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
		mWeiboShareAPI.handleWeiboResponse(intent, this);
	}

	/**
	 * 接收微客户端博请求的数据。 当微博客户端唤起当前应用并进行分享时，该方法被调用。
	 * 
	 * @param baseRequest 微博请求数据对象
	 * @see {@link IWeiboShareAPI#handleWeiboRequest}
	 */
	@Override
	public void onResponse(BaseResponse baseResp) {
		switch (baseResp.errCode) {
			case WBConstants.ErrorCode.ERR_OK:
				Toast.makeText(this, "分享成功", Toast.LENGTH_LONG).show();
				break;
			case WBConstants.ErrorCode.ERR_CANCEL:
				Toast.makeText(this, "取消分享", Toast.LENGTH_LONG).show();
				break;
			case WBConstants.ErrorCode.ERR_FAIL:
				Toast.makeText(this, "分享失败" + "Error Message: " + baseResp.errMsg, Toast.LENGTH_LONG).show();
				break;
		}
	}

	private void sendMessage() {
		if (mShareType == SHARE_CLIENT) {
			if (mWeiboShareAPI.isWeiboAppSupportAPI()) {
				int supportApi = mWeiboShareAPI.getWeiboAppSupportAPI();
				if (supportApi >= 10351 /* ApiUtils.BUILD_INT_VER_2_2 */) {
					sendMultiMessage();
				} else {
					sendSingleMessage();
				}
			} else {
				Toast.makeText(this, "微博客户端不支持 SDK 分享或微博客户端未安装或微博客户端是非官方版本.", Toast.LENGTH_SHORT).show();
			}
		} else if (mShareType == SHARE_ALL_IN_ONE) {
			sendMultiMessage();
		}
	}

	private void sendMultiMessage() {
		// 1. 初始化微博的分享消息
		WeiboMultiMessage weiboMsg = new WeiboMultiMessage();
		TextObject textObject = new TextObject();
		textObject.text = "我正在使用Demo发送微博消息";
		weiboMsg.mediaObject = textObject;
		// 2. 初始化从第三方到微博的消息请求
		SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
		// 用transaction唯一标识一个请求
		request.transaction = String.valueOf(System.currentTimeMillis());
		request.multiMessage = weiboMsg;
		// 3. 发送请求消息到微博，唤起微博分享界面
		if (mShareType == SHARE_CLIENT) {
			mWeiboShareAPI.sendRequest(WeiboShareActivity.this, request);
		} else if (mShareType == SHARE_ALL_IN_ONE) {
			AuthInfo authInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
			Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(getApplicationContext());
			String token = "";
			if (accessToken != null) {
				token = accessToken.getToken();
			}
			mWeiboShareAPI.sendRequest(this, request, authInfo, token, new WeiboAuthListener() {

				@Override
				public void onWeiboException(WeiboException arg0) {
				}

				@Override
				public void onComplete(Bundle bundle) {
					// TODO Auto-generated method stub
					Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
					AccessTokenKeeper.writeAccessToken(getApplicationContext(), newToken);
					Toast.makeText(getApplicationContext(), "onAuthorizeComplete token = " + newToken.getToken(), 0).show();
				}

				@Override
				public void onCancel() {
				}
			});
		}
	}

	/**
	 * 第三方应用发送请求消息到微博，唤起微博分享界面。 当{@link IWeiboShareAPI#getWeiboAppSupportAPI()}
	 * < 10351 时，只支持分享单条消息，即 文本、图片、网页、音乐、视频中的一种，不支持Voice消息。
	 */
	private void sendSingleMessage() {
		// 1. 初始化微博的分享消息
		WeiboMessage weiboMsg = new WeiboMessage();
		TextObject textObject = new TextObject();
		textObject.text = "我正在使用Demo发送微博消息";
		weiboMsg.mediaObject = textObject;
		// 2. 初始化从第三方到微博的消息请求
		SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
		// SendMultiMessageToWeiboRequest request = new
		// SendMultiMessageToWeiboRequest();
		// 用transaction唯一标识一个请求
		request.transaction = String.valueOf(System.currentTimeMillis());
		request.message = weiboMsg;
		// 3. 发送请求消息到微博，唤起微博分享界面
		mWeiboShareAPI.sendRequest(WeiboShareActivity.this, request);
	}
}
