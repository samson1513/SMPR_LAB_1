package com.example.samson.smpr_lab_1.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by samson on 11.09.15.
 */
public class SquareMatrix extends GridView {
    public SquareMatrix(Context context) {
        super(context);
    }

    public SquareMatrix(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareMatrix(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = Math.min(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(size, size);
    }
}
