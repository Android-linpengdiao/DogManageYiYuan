package com.dog.manage.yiyuan.app.adapter;

import android.content.Context;
import android.view.View;

import com.base.BaseRecyclerAdapter;
import com.base.utils.GlideLoader;
import com.base.view.OnClickListener;
import com.dog.manage.yiyuan.app.R;
import com.dog.manage.yiyuan.app.databinding.ItemFrameBinding;

public class FrameItemAdapter extends BaseRecyclerAdapter<String, ItemFrameBinding> {

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public FrameItemAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.item_frame;
    }

    @Override
    protected void onBindItem(ItemFrameBinding binding, String dataBean, int position) {

        GlideLoader.LoderDrawable(mContext, position == 0 ? R.drawable.icon01 : R.drawable.icon05, binding.iconView);
        binding.titleView.setText(dataBean);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null)
                    onClickListener.onClick(view, position);
            }
        });
    }
}
