package com.ezetap.android.utils;

import android.graphics.Rect;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.TextView;

public class TextMaskingTransformationMethod implements TransformationMethod {

	public MASK_TYPE maskType;
	
	public TextMaskingTransformationMethod(MASK_TYPE maskType) {
		this.maskType = maskType;
	}

	public static enum MASK_TYPE{
		MOBILE_NUM,
	};
	@Override
	public CharSequence getTransformation(CharSequence source, View view) {
		return new MobileNumberCharSequence(source);
	}

	private class MobileNumberCharSequence implements CharSequence {
		private CharSequence actualString;

		public MobileNumberCharSequence(CharSequence source) {
			actualString = source;
		}

		public char charAt(int index) {
			if(maskType == MASK_TYPE.MOBILE_NUM){
				if (length() > 6 && index > 5 && length() - index < 5)
					return actualString.charAt(index);
				else
					return '#';
			} else {
				return actualString.charAt(index);
			}
		}

		public int length() {
			return actualString.length();
		}

		public CharSequence subSequence(int start, int end) {
			return actualString.subSequence(start, end);
		}
	}

	@Override
	public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction, Rect previouslyFocusedRect) {
	}

}
