package utils;

import com.daiyan.neteasenews.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * @Title:
 * @Description:Activity跳转工具类
 * @author daiyan
 * @Company:
 * @date 2015年4月17日
 */
public class IntentUtils {
	public static Handler handler = new Handler();

	/**
	 * 跳转方法
	 * 
	 * @param activity 当前Activity
	 * @param cls 要跳转的class
	 * @param bundle 传递的参数
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
