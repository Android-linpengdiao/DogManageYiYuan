package com.dog.manage.yiyuan.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.base.BaseApplication;
import com.dog.manage.yiyuan.app.R;

public class TextBoldView extends androidx.appcompat.widget.AppCompatTextView {

    public TextBoldView(@NonNull Context context) {
        super(context);
    }

    public TextBoldView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public TextBoldView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void initView(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TextBoldView);
        boolean bold = typedArray.getBoolean(R.styleable.TextBoldView_text_bold, false);
        Typeface typeface = BaseApplication.getInstance().getTypeface();
        setTypeface(typeface);
    }
}
