package com.app.retrofitdemo.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by admin on 11/16/2016.
 */

public class AwsomeTextView extends TextView {
    public AwsomeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public AwsomeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AwsomeTextView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        setTypeface(tf);
    }
}
