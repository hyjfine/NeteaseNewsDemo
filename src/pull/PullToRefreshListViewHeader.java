/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */
package pull;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ant.liao.GifView;
import com.daiyan.neteasenews.R;

public class PullToRefreshListViewHeader extends LinearLayout {
	private LinearLayout mContainer;
	private ImageView mArrowImageView;
	private TextView mHintTextView;
	private int mState = STATE_NORMAL;

	private Animation mRotateUpAnim;
	private Animation mRotateDownAnim;

	private final int ROTATE_ANIM_DURATION = 180;

	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_REFRESHING = 2;
	private GifView gifView;

	public PullToRefreshListViewHeader(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public PullToRefreshListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	@SuppressLint("InflateParams")
	private void initView(Context context) {
		// 初始情况，设置下拉刷新view高度为0
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
		mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.app_pull_listview_header, null);
		addView(mContainer, lp);
		setGravity(Gravity.BOTTOM);
		mArrowImageView = (ImageView) findViewById(R.id.xlistview_header_arrow);
		gifView = (GifView) findViewById(R.id.gif_view);
		setGifView();
		mHintTextView = (TextView) findViewById(R.id.xlistview_header_hint_textview);
		// mProgressBar = (ProgressBar)
		// findViewById(R.id.xlistview_header_progressbar);
		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
	}

	/**
	 * 设置Gifview
	 */
	private void setGifView() {
		gifView.setGifImage(R.drawable.common_icon_refresh);
	}

	public void setState(int state) {
		if (state == mState)
			return;

		if (state == STATE_REFRESHING) { // 鏄剧ず杩涘害
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.INVISIBLE);
			// mProgressBar.setVisibility(View.VISIBLE);
			gifView.setVisibility(View.VISIBLE);
		} else { // 鏄剧ず绠ご鍥剧墖
			mArrowImageView.setVisibility(View.VISIBLE);
			// mProgressBar.setVisibility(View.INVISIBLE);
			gifView.setVisibility(View.INVISIBLE);
		}

		switch (state) {
			case STATE_NORMAL:
				if (mState == STATE_READY) {
					mArrowImageView.startAnimation(mRotateDownAnim);
				}
				if (mState == STATE_REFRESHING) {
					mArrowImageView.clearAnimation();
				}
				mHintTextView.setText(R.string.pull_to_refresh_label);
				// timeLayout.setVisibility(View.VISIBLE);
				break;
			case STATE_READY:
				if (mState != STATE_READY) {
					mArrowImageView.clearAnimation();
					mArrowImageView.startAnimation(mRotateUpAnim);
					mHintTextView.setText(R.string.release_to_refresh_label);
				}
				// timeLayout.setVisibility(View.GONE);
				break;
			case STATE_REFRESHING:
				mHintTextView.setText(R.string.pull_to_refreshing_label);
				// timeLayout.setVisibility(View.GONE);
				break;
			default:
		}
		mState = state;
	}

	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContainer.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

	public int getVisiableHeight() {
		return mContainer.getHeight();
	}
}
