package com.dog.manage.yiyuan.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.base.BaseData;
import com.base.manager.LoadingManager;
import com.base.utils.CommonUtil;
import com.base.utils.GsonUtils;
import com.base.utils.ToastUtils;
import com.dog.manage.yiyuan.app.R;
import com.dog.manage.yiyuan.app.databinding.ActivityImmuneInfoBinding;
import com.dog.manage.yiyuan.app.databinding.ActivityImmuneSubmitBinding;
import com.dog.manage.yiyuan.app.model.LicenceInfo;
import com.okhttp.ResultClient;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * immuneName
     * string
     * immuneName
     * immuneBatch
     * string
     * 疫苗批次号
     * immuneFactory
     * string
     * immuneFactory
     * immuneNum
     * string
     * 免疫标识号
     *
     * @param view
     */

    public void onClickConfirm(View view) {
        openActivity(ImmuneInfoActivity.class);

        Map<String, String> paramsMap = new HashMap<>();

        String immuneName = binding.immuneNameView.binding.itemEdit.getText().toString();
        if (CommonUtil.isBlank(immuneName)) {
            ToastUtils.showShort(getApplicationContext(), "请输入疫苗名称");
            return;
        }
        String immuneBatch = binding.immuneBatchView.binding.itemEdit.getText().toString();
        if (CommonUtil.isBlank(immuneBatch)) {
            ToastUtils.showShort(getApplicationContext(), "请输入疫苗批号");
            return;
        }
        String immuneFactory = binding.immuneFactoryView.binding.itemEdit.getText().toString();
        if (CommonUtil.isBlank(immuneFactory)) {
            ToastUtils.showShort(getApplicationContext(), "请输入疫苗厂家");
            return;
        }
        String immuneNum = binding.immuneNumView.binding.itemEdit.getText().toString();
        if (CommonUtil.isBlank(immuneNum)) {
            ToastUtils.showShort(getApplicationContext(), "请输入免疫标识号");
            return;
        }


        paramsMap.put("immuneName", immuneName);
        paramsMap.put("immuneBatch", immuneBatch);
        paramsMap.put("immuneFactory", immuneFactory);
        paramsMap.put("immuneNum", immuneNum);

        SendRequest.updateImmuneLicenceLog(paramsMap, new GenericsCallback<BaseData>(new JsonGenericsSerializator()) {

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
            public void onResponse(BaseData response, int id) {
                if (response.isSuccess()) {
                    ToastUtils.showShort(ImmuneSubmitActivity.this, "提交成功");
                    finish();

                } else {
                    ToastUtils.showShort(getApplicationContext(), response.getMsg());

                }

            }
        });
    }
}