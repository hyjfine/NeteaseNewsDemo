package sina;

import com.daiyan.neteasenews.R;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.User;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

public class UserAPIActivity extends Activity {
	private Oauth2AccessToken mAccessToken;
	private UsersAPI mUsersAPI;
	private TextView tvUserName;
	private TextView tvUserUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userapi_layout);
		tvUserName = (TextView) findViewById(R.id.tv_username);
		tvUserUrl = (TextView) findViewById(R.id.tv_userurl);

		// ��ȡ��ǰ�ѱ������ Token
		mAccessToken = AccessTokenKeeper.readAccessToken(this);
		// ��ȡ�û���Ϣ�ӿ�
		mUsersAPI = new UsersAPI(this, Constants.APP_KEY, mAccessToken);
		if (mAccessToken != null && mAccessToken.isSessionValid()) {
			long uid = Long.parseLong(mAccessToken.getUid());
			mUsersAPI.show(uid, mRequestListener);
		}
	}

	public RequestListener mRequestListener = new RequestListener() {

		@Override
		public void onWeiboException(WeiboException arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onComplete(String response) {
			if (!TextUtils.isEmpty(response)) {
				User user = User.parse(response);
				if (user != null) {
					// ��ȡ�����û�����
					String userName = user.name;
					Constants.userName = userName;
					// �û�ͷ��
					String userUrl = user.avatar_large;
					tvUserName.setText(userName);
					tvUserUrl.setText(userUrl);
				}
			}
		}
	};
}
