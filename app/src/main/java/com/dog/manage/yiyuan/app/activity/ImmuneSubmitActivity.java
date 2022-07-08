package com.dog.manage.yiyuan.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.base.manager.LoadingManager;
import com.dog.manage.yiyuan.app.R;
import com.dog.manage.yiyuan.app.databinding.ActivityImmuneInfoBinding;
import com.dog.manage.yiyuan.app.databinding.ActivityImmuneSubmitBinding;
import com.dog.manage.yiyuan.app.model.LicenceInfo;
import com.okhttp.ResultClient;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;

import okhttp3.Call;
import okhttp3.Request;

public class ImmuneSubmitActivity extends BaseActivity {

    private ActivityImmuneSubmitBinding binding;
    private LicenceInfo licenceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewData(R.layout.activity_immune_submit);

        binding.dogOwnerInfoView.binding.itemInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (licenceInfo == null) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("userId", licenceInfo.getUserId());
                bundle.putInt("dogId", licenceInfo.getDogId());
                openActivity(DogUserActivity.class, bundle);
            }
        });
        binding.dogDetailsView.binding.itemInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (licenceInfo == null) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("dogId", licenceInfo.getDogId());
                openActivity(DogDetailsActivity.class, bundle);
            }
        });

        String noseprint = getIntent().getStringExtra("noseprint");
        getDogByNoseprint(noseprint);
    }

    private void getDogByNoseprint(String noseprint) {
        SendRequest.getDogByNoseprint(noseprint,
                new GenericsCallback<ResultClient<LicenceInfo>>(new JsonGenericsSerializator()) {

                    @Override
                    public void onBefore(Request request, int id) {
                        super.onBefore(request, id);
                        LoadingManager.showLoadingDialog(ImmuneSubmitActivity.this);
                    }

                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        LoadingManager.hideLoadingDialog(ImmuneSubmitActivity.this);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(ResultClient<LicenceInfo> response, int id) {
                        if (response.isSuccess() && response.getData() != null) {
//                            intiCertificate(response.getData());

                        } else {
//                            binding.dogCertificateView.binding.itemContent.setText("查无此犬信息");
//                            ToastUtils.showShort(getApplicationContext(), "获取犬证信息失败");
                        }
                    }
                });
    }

    public void onClickConfirm(View view) {
        openActivity(ImmuneInfoActivity.class);
    }
}