package com.daiyan.news.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

/**
 * @author daiyan
 * 
 *         2015-8-18
 */
public class DivisionEditText extends EditText {
	/* 分隔符 */
	private String delimiter = " ";
	/* 输入字符串最大长度包括"," */
	private int maxLength = 13;
	private String text = "";

	public DivisionEditText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public DivisionEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public DivisionEditText(Context context) {
		super(context);
		init();
	}

	private void init() {
		// 内容变化监听
		this.addTextChangedListener(new DivisionTextWatcher(this));
		// 获取焦点监听
		this.setOnFocusChangeListener(new DivisionFocusChangeListener());
	}

	/**
	 * 文本监听
	 * 
	 * @author Administrator
	 * 
	 */
	private class DivisionTextWatcher implements TextWatcher {

		public DivisionTextWatcher(EditText editText) {
		}

		@Override
		public void afterTextChanged(Editable s) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// 统计个数
			int len = s.length();
			if (count > 1) {
				return;
			}
			if (len <= 3) {
				return;
			} else if ((len > 3 || len > 9)&&len<maxLength) {
				text = fomatString(s.toString());
			}else{
				text = s.toString().substring(0, maxLength);
			}
			setText(text);
			setSelection(text.length());
		}
	}

	/**
	 * 获取焦点监听
	 * 
	 * @author Administrator
	 * 
	 */
	private class DivisionFocusChangeListener implements OnFocusChangeListener {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus) {
				// 设置焦点
				setSelection(getText().toString().length());
			}
		}
	}

	/** 得到间隔符 */
	public String getDelimiter() {
		return delimiter;
	}

	/** 设置间隔符 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	/** 格式化添加空格 */
	public String fomatString(String str) {
		char[] chars = str.replace(getDelimiter(), "").toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			if (i == 3 || i == 7) {
				sb.append(getDelimiter());
				sb.append(chars[i]);
			} else {
				sb.append(chars[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * 截取输入的数字号获得一串字符数字
	 * @return
	 */
	public String getInputText() {
		return super.getText().toString().replace(getDelimiter(), "");
	}

}
