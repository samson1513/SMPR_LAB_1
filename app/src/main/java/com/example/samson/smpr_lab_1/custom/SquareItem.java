package com.example.samson.smpr_lab_1.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.TextView;

/**
 * Created by samson on 10.09.15.
 */
public class SquareItem extends TextView {
    public SquareItem(Context context) {
        super(context);
    }

    public SquareItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
