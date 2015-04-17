package utils;

import com.daiyan.neteasenews.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * @Title:
 * @Description:Activity��ת������
 * @author daiyan
 * @Company:
 * @date 2015��4��17��
 */
public class IntentUtils {
	public static Handler handler = new Handler();

	/**
	 * ��ת����
	 * 
	 * @param activity ��ǰActivity
	 * @param cls Ҫ��ת��class
	 * @param bundle ���ݵĲ���
	 */
	public static void startActivity(final Activity activity, final Class<?> cls, final Bundle bundle) {

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (null != activity) {
					Intent intent = new Intent(activity, cls);
					intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					if (bundle != null) {
						intent.putExtras(bundle);
					}
					activity.startActivity(intent);
					activity.overridePendingTransition(R.anim.right_fade_in, R.anim.left_fade_out);
				}
			}
		}, 300);
	}
}
