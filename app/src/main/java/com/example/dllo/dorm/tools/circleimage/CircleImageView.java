package com.example.dllo.dorm.tools.circleimage;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

/**
 * Created by CaoDongping on 9/6/16.
 */

public class CircleImageView extends de.hdodenhof.circleimageview.CircleImageView {
    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if (pressed) {
            setBorderColor(Color.BLUE);
        } else {
            setBorderColor(Color.WHITE);
        }
    }
}
