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
	/* �ָ��� */
	private String delimiter = " ";
	/* �����ַ�����󳤶Ȱ���"," */
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
		// ���ݱ仯����
		this.addTextChangedListener(new DivisionTextWatcher(this));
		// ��ȡ�������
		this.setOnFocusChangeListener(new DivisionFocusChangeListener());
	}

	/**
	 * �ı�����
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
			// ͳ�Ƹ���
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
	 * ��ȡ�������
	 * 
	 * @author Administrator
	 * 
	 */
	private class DivisionFocusChangeListener implements OnFocusChangeListener {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus) {
				// ���ý���
				setSelection(getText().toString().length());
			}
		}
	}

	/** �õ������ */
	public String getDelimiter() {
		return delimiter;
	}

	/** ���ü���� */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	/** ��ʽ����ӿո� */
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
	 * ��ȡ��������ֺŻ��һ���ַ�����
	 * @return
	 */
	public String getInputText() {
		return super.getText().toString().replace(getDelimiter(), "");
	}

}
