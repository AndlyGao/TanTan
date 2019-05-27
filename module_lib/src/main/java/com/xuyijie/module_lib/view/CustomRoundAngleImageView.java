package com.xuyijie.module_lib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

public class CustomRoundAngleImageView extends AppCompatImageView {
    float width, height;

    public CustomRoundAngleImageView(Context context) {
        this(context, null);
        init(context, null);
    }

    public CustomRoundAngleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    public CustomRoundAngleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    private int radius;

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (width >= getRadius() && height > getRadius()) {
            Path path = new Path();
            //四个圆角
            path.moveTo(getRadius(), 0);
            path.lineTo(width - getRadius(), 0);
            path.quadTo(width, 0, width, getRadius());
            path.lineTo(width, height - getRadius());
            path.quadTo(width, height, width - getRadius(), height);
            path.lineTo(getRadius(), height);
            path.quadTo(0, height, 0, height - getRadius());
            path.lineTo(0, getRadius());
            path.quadTo(0, 0, getRadius(), 0);

            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }

}
