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

		// ����΢�� SDK �ӿ�ʵ��
		mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constants.APP_KEY);
		// ע�������Ӧ�õ�΢���ͻ����У�ע��ɹ����Ӧ�ý���ʾ��΢����Ӧ���б��С�
		// ���ø��������ɷ���Ȩ����Ҫ�������룬������鿴 Demo ��ʾ
		// NOTE���������ǰע�ᣬ�������ʼ����ʱ�����Ӧ�ó����ʼ��ʱ������ע��
		mWeiboShareAPI.registerApp();

		// �� Activity �����³�ʼ��ʱ���� Activity ���ں�̨ʱ�����ܻ������ڴ治�㱻ɱ���ˣ���
		// ��Ҫ���� {@link IWeiboShareAPI#handleWeiboResponse} ������΢���ͻ��˷��ص����ݡ�
		// ִ�гɹ������� true�������� {@link IWeiboHandler.Response#onResponse}��
		// ʧ�ܷ��� false�������������ص�
		if (savedInstanceState != null) {
			mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
		}
		initView();
	}

	private void initView() {
		// ��ȡ΢���ͻ��������Ϣ�����Ƿ�װ��֧�� SDK �İ汾
		boolean isInstalledWeibo = mWeiboShareAPI.isWeiboAppInstalled();
		int supportApiLevel = mWeiboShareAPI.getWeiboAppSupportAPI();
		tvRegister = (TextView) findViewById(R.id.tv_reg_sina);
		btnShare = (TextView) findViewById(R.id.btn_share_sina);
		btnShare.setEnabled(false);
		tvRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// ע�ᵽ����΢��
				mWeiboShareAPI.registerApp();
				Toast.makeText(WeiboShareActivity.this, "ע��ɹ���", Toast.LENGTH_LONG).show();
				btnShare.setEnabled(true);
			}
		});

		btnShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Toast.makeText(WeiboShareActivity.this, "΢������---", Toast.LENGTH_LONG).show();
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

		// �ӵ�ǰӦ�û���΢�������з���󣬷��ص���ǰӦ��ʱ����Ҫ�ڴ˴����øú���
		// ������΢���ͻ��˷��ص����ݣ�ִ�гɹ������� true��������
		// {@link IWeiboHandler.Response#onResponse}��ʧ�ܷ��� false�������������ص�
		mWeiboShareAPI.handleWeiboResponse(intent, this);
	}

	/**
	 * ����΢�ͻ��˲���������ݡ� ��΢���ͻ��˻���ǰӦ�ò����з���ʱ���÷��������á�
	 * 
	 * @param baseRequest ΢���������ݶ���
	 * @see {@link IWeiboShareAPI#handleWeiboRequest}
	 */
	@Override
	public void onResponse(BaseResponse baseResp) {
		switch (baseResp.errCode) {
			case WBConstants.ErrorCode.ERR_OK:
				Toast.makeText(this, "����ɹ�", Toast.LENGTH_LONG).show();
				break;
			case WBConstants.ErrorCode.ERR_CANCEL:
				Toast.makeText(this, "ȡ������", Toast.LENGTH_LONG).show();
				break;
			case WBConstants.ErrorCode.ERR_FAIL:
				Toast.makeText(this, "����ʧ��" + "Error Message: " + baseResp.errMsg, Toast.LENGTH_LONG).show();
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
				Toast.makeText(this, "΢���ͻ��˲�֧�� SDK �����΢���ͻ���δ��װ��΢���ͻ����Ƿǹٷ��汾.", Toast.LENGTH_SHORT).show();
			}
		} else if (mShareType == SHARE_ALL_IN_ONE) {
			sendMultiMessage();
		}
	}

	private void sendMultiMessage() {
		// 1. ��ʼ��΢���ķ�����Ϣ
		WeiboMultiMessage weiboMsg = new WeiboMultiMessage();
		TextObject textObject = new TextObject();
		textObject.text = "������ʹ��Demo����΢����Ϣ";
		weiboMsg.mediaObject = textObject;
		// 2. ��ʼ���ӵ�������΢������Ϣ����
		SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
		// ��transactionΨһ��ʶһ������
		request.transaction = String.valueOf(System.currentTimeMillis());
		request.multiMessage = weiboMsg;
		// 3. ����������Ϣ��΢��������΢���������
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
	 * ������Ӧ�÷���������Ϣ��΢��������΢��������档 ��{@link IWeiboShareAPI#getWeiboAppSupportAPI()}
	 * < 10351 ʱ��ֻ֧�ַ�������Ϣ���� �ı���ͼƬ����ҳ�����֡���Ƶ�е�һ�֣���֧��Voice��Ϣ��
	 */
	private void sendSingleMessage() {
		// 1. ��ʼ��΢���ķ�����Ϣ
		WeiboMessage weiboMsg = new WeiboMessage();
		TextObject textObject = new TextObject();
		textObject.text = "������ʹ��Demo����΢����Ϣ";
		weiboMsg.mediaObject = textObject;
		// 2. ��ʼ���ӵ�������΢������Ϣ����
		SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
		// SendMultiMessageToWeiboRequest request = new
		// SendMultiMessageToWeiboRequest();
		// ��transactionΨһ��ʶһ������
		request.transaction = String.valueOf(System.currentTimeMillis());
		request.message = weiboMsg;
		// 3. ����������Ϣ��΢��������΢���������
		mWeiboShareAPI.sendRequest(WeiboShareActivity.this, request);
	}
}
