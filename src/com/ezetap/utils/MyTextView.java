package com.ezetap.utils;

import android.content.Context;
//import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Custom TextView Class to load fonts from TTF
 * @author deepak
 */
public class MyTextView extends TextView {

	    public MyTextView(Context context) {
	        super(context);
//	        setCustomFont(context, null);
	    }

	    public MyTextView(Context context, AttributeSet attrs) {
	        super(context, attrs);
//	        setCustomFont(context, attrs);
	    }

	    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle);
//	        setCustomFont(context, attrs);
	    }

//	    public boolean setCustomFont(Context ctx, AttributeSet attrs) {
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
