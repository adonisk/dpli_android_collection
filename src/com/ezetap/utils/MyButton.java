package com.ezetap.utils;

import android.content.Context;
//import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class MyButton extends Button {

	    public MyButton(Context context) {
	        super(context);
//	        setCustomFont(context);
	    }

	    public MyButton(Context context, AttributeSet attrs) {
	        super(context, attrs);
//	        setCustomFont(context);
	    }

	    public MyButton(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle);
//	        setCustomFont(context);
	    }

//		public boolean setCustomFont(Context ctx) {
//	        try {
//	        	Typeface typeFace = Typeface.createFromAsset(ctx.getAssets(), "font.ttf"); 	 
//		        int style = 0;
//	        	Typeface tf = getTypeface();
//	        	if(tf != null)
//	        		style = tf.getStyle();
//		        setTypeface(typeFace, style); 
//	        } catch (Exception e) {
//	            return false;
//	        }	        
//	        return true;
//	    }
	}
