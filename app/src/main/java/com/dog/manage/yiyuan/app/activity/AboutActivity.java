package com.dog.manage.yiyuan.app.activity;

import android.os.Bundle;

import com.dog.manage.yiyuan.app.R;
import com.dog.manage.yiyuan.app.databinding.ActivityAboutBinding;

public class AboutActivity extends BaseActivity {

    private ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewData(R.layout.activity_about);
        addActivity(this);
    }
}