package com.dog.manage.yiyuan.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.base.BaseData;
import com.base.manager.LoadingManager;
import com.base.utils.CommonUtil;
import com.base.utils.FileUtils;
import com.base.utils.GlideLoader;
import com.base.utils.GsonUtils;
import com.base.utils.PermissionUtils;
import com.base.utils.ToastUtils;
import com.base.view.OnClickListener;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.dog.manage.yiyuan.app.Config;
import com.dog.manage.yiyuan.app.R;
import com.dog.manage.yiyuan.app.adapter.TypeAdapter;
import com.dog.manage.yiyuan.app.databinding.ActivityEnforcementSubmitBinding;
import com.dog.manage.yiyuan.app.media.MediaFile;
import com.dog.manage.yiyuan.app.media.MediaSelectActivity;
import com.dog.manage.yiyuan.app.media.MediaUtils;
import com.dog.manage.yiyuan.app.model.LicenceInfo;
import com.dog.manage.yiyuan.app.model.TypeBean;
import com.dog.manage.yiyuan.app.utils.UploadFileManager;
import com.dog.manage.yiyuan.app.view.GridItemDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.obs.services.model.ProgressListener;
import com.obs.services.model.ProgressStatus;
import com.obs.services.model.PutObjectRequest;
import com.obs.services.model.PutObjectResult;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class EnforcementSubmitActivity extends BaseActivity {

    private ActivityEnforcementSubmitBinding binding;
    private Map<String, String> paramsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewData(R.layout.activity_enforcement_submit);

        licenceInfo = (LicenceInfo) getIntent().getSerializableExtra("licenceInfo");
        String paramsJson = getIntent().getStringExtra("paramsJson");
        if (!TextUtils.isEmpty(paramsJson)) {
            Gson gson = new Gson();
            paramsMap = gson.fromJson(paramsJson, new TypeToken<Map<String, Object>>() {
            }.getType());
        }

        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(this);
        builder.color(R.color.transparent);
        builder.size(CommonUtil.dip2px(this, 10));
        binding.typeRecyclerView.addItemDecoration(new GridItemDecoration(builder));
        binding.typeRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        TypeAdapter typeAdapter = new TypeAdapter(this);
        binding.typeRecyclerView.setAdapter(typeAdapter);
        typeAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                illegalTypeId = (int) object;
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        //???????????? 1 ???????????? 2 ?????? 3 ???????????? 4 ??????
        typeAdapter.refreshData(Arrays.asList(new TypeBean(1, "????????????"),
                new TypeBean(2, "??????"),
                new TypeBean(3, "????????????"),
                new TypeBean(4, "??????")));

        binding.illegalTimeView.binding.itemContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTime();
            }
        });

    }

    /**
     * ????????????
     */
    private void getTime() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        //?????????????????? ??????????????????????????????
        SimpleDateFormat yearSimpleDateFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthSimpleDateFormat = new SimpleDateFormat("MM");
        SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat("dd");
        Date date = new Date(System.currentTimeMillis());
        startDate.set(2010, 11, 31);
        endDate.set(Integer.parseInt(yearSimpleDateFormat.format(date)), Integer.parseInt(monthSimpleDateFormat.format(date)) - 1, Integer.parseInt(dateSimpleDateFormat.format(date)));

        TimePickerView timePickerView = new TimePickerBuilder(EnforcementSubmitActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//??????????????????
                SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                illegalTime = sdr.format(date);
                binding.illegalTimeView.binding.itemContent.setText(sdr.format(date));
            }
        })
                .setType(new boolean[]{true, true, true, true, true, false})// ??????????????????
                .setCancelText("??????")//??????????????????
                .setSubmitText("??????")//??????????????????
                .setSubCalSize(16)//??????????????????
                .setContentTextSize(18)//??????????????????
                .setTitleSize(18)//??????????????????
                .setTitleText("????????????")//????????????
                .setOutSideCancelable(false)//???????????????????????????????????????????????????????????????
                .isCyclic(false)//??????????????????
                .setTitleColor(Color.BLACK)//??????????????????
                .setSubmitColor(Color.BLACK)//????????????????????????
                .setCancelColor(Color.BLACK)//????????????????????????
                .setTitleBgColor(getResources().getColor(R.color.white))//?????????????????? Night mode
                .setBgColor(getResources().getColor(R.color.white))//?????????????????? Night mode
                .setDate(selectedDate)// ?????????????????????????????????????????????*/
                .setRangDate(startDate, endDate)//???????????????????????????
                .setLabel("???", "???", "???", "???", "???", "???")//?????????????????????????????????
                .isCenterLabel(false) //?????????????????????????????????label?????????false?????????item???????????????label???
                .isDialog(false)//??????????????????????????????
                .setLineSpacingMultiplier(2.6f)//??????????????????,???????????????1.0-4.0f??????
                .build();
        timePickerView.show();
    }

    /**
     * illegalFileUrl
     * string
     * ??????????????????
     * illegalTypeId
     * string
     * ????????????
     * illegalDescribe
     * string
     * ????????????
     * illegalMeasure
     * string
     * ??????
     * illegalTime
     * string
     * ???????????? yyyy-MM-dd
     * dogLicenceNum
     * string
     * ????????????
     * orgName
     * string
     * ???????????????????????????????????????????????????
     * idNum
     * string
     * ????????????
     * phoneNum
     * string
     * ?????????
     * detailedAddress
     * string
     * ????????????
     * addressArea
     * string
     * ????????????
     * villageId
     * integer
     * ??????id
     * userId
     * string
     * ??????id
     */

    private String illegalFileUrl = "http://dogmanage.file.obs.cn-north-4.myhuaweicloud.com/IMG_20220601_081815.jpg";
    private int illegalTypeId = 0;
    private String illegalTime = null;
    //    private String dogLicenceNum = null;
    private LicenceInfo licenceInfo;

    public void onClickConfirm(View view) {

        if (CommonUtil.isBlank(illegalFileUrl)) {
            ToastUtils.showShort(getApplicationContext(), "?????????????????????");
            return;
        }

        if (illegalTypeId == 0) {
            ToastUtils.showShort(getApplicationContext(), "?????????????????????");
            return;
        }

        String illegalDescribe = binding.illegalDescribeEditText.getText().toString();
        if (CommonUtil.isBlank(illegalDescribe)) {
            ToastUtils.showShort(getApplicationContext(), "???????????????????????????");
            return;
        }

        String illegalMeasure = binding.illegalMeasureEditText.getText().toString();
        if (CommonUtil.isBlank(illegalDescribe)) {
            ToastUtils.showShort(getApplicationContext(), "?????????????????????");
            return;
        }

        if (CommonUtil.isBlank(illegalTime)) {
            ToastUtils.showShort(getApplicationContext(), "?????????????????????");
            return;
        }

        paramsMap.put("illegalFileUrl", GsonUtils.toJson(Arrays.asList(illegalFileUrl)));
        paramsMap.put("illegalTypeId", String.valueOf(illegalTypeId));
        paramsMap.put("illegalDescribe", illegalDescribe);
        paramsMap.put("illegalMeasure", illegalMeasure);
        paramsMap.put("illegalTime", illegalTime);
        if (licenceInfo != null) {
            paramsMap.put("dogLicenceNum", licenceInfo.getIdNum());
            paramsMap.put("userId", licenceInfo.getUserId() + "");
        }

        SendRequest.saveIllegal(paramsMap, new GenericsCallback<BaseData>(new JsonGenericsSerializator()) {

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                LoadingManager.showLoadingDialog(EnforcementSubmitActivity.this);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                LoadingManager.hideLoadingDialog(EnforcementSubmitActivity.this);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(BaseData response, int id) {
                if (response.isSuccess()) {
                    finishActivity(EnforcementActivity.class);
                    finishActivity(EnforcementDogInfoActivity.class);
                    ToastUtils.showShort(EnforcementSubmitActivity.this, "????????????");
                    finish();

                } else {
                    ToastUtils.showShort(getApplicationContext(), response.getMsg());

                }

            }
        });


    }

    public void onClickCancel(View view) {

    }

    public void onClickClear(View view) {

    }

    private final int request_IllegalFile = 100;

    /**
     * ??????????????????
     *
     * @param view
     */
    public void onClickPicture(View view) {
        if (checkPermissions(PermissionUtils.STORAGE, request_IllegalFile)) {
            Bundle bundle = new Bundle();
            bundle.putInt("mediaType", MediaUtils.MEDIA_TYPE_PHOTO);
            bundle.putInt("maxNumber", 1);
            openActivity(MediaSelectActivity.class, bundle, request_IllegalFile);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case request_IllegalFile:
                    compressImage(data, request_IllegalFile);

                    break;
            }
        }
    }

    private void compressImage(Intent data, int requestCode) {
        try {
            if (data != null) {
                String imageJson = data.getStringExtra("imageJson");
                if (!TextUtils.isEmpty(imageJson)) {
                    Gson gson = new Gson();
                    List<MediaFile> imageList = gson.fromJson(imageJson, new TypeToken<List<MediaFile>>() {
                    }.getType());
                    if (imageList != null && imageList.size() > 0) {
                        String path = imageList.get(0).getPath();
                        Luban.with(this)
                                .load(path)// ??????????????????????????????
                                .ignoreBy(500)// ??????????????????????????????
                                .setTargetDir(FileUtils.getMediaPath())// ?????????????????????????????????
                                .setCompressListener(new OnCompressListener() { //????????????
                                    @Override
                                    public void onStart() {
                                    }

                                    @Override
                                    public void onSuccess(File file) {
                                        if (requestCode == request_IllegalFile) {
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    uploadFile(file.getAbsolutePath());
                                                }
                                            }).start();
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                    }
                                }).launch();//????????????
                    }
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * ????????? ????????????
     *
     * @param filePath
     */
    private void uploadFile(String filePath) {
        Log.i(TAG, "uploadFile: filePath = " + filePath);
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        Log.i(TAG, "uploadFile: fileName = " + fileName);
        PutObjectRequest request = new PutObjectRequest();
        request.setBucketName(Config.huaweiBucketName);
        request.setObjectKey(fileName);
        request.setFile(new File(filePath));
        request.setProgressListener(new ProgressListener() {
            @Override
            public void progressChanged(ProgressStatus status) {
                // ????????????????????????
                Log.i(TAG, "uploadFile: AverageSpeed:" + status.getAverageSpeed());
                // ???????????????????????????
                Log.i(TAG, "uploadFile: TransferPercentage:" + status.getTransferPercentage());
            }
        });
        //?????????1MB????????????????????????
        request.setProgressInterval(1024 * 1024L);
        PutObjectResult result = UploadFileManager.getInstance().getObsClient().putObject(request);
        Log.i(TAG, "uploadFile: getObjectUrl = " + result.getObjectUrl());
        String url = "http://" + Config.huaweiBucketName + "." + Config.huaweiCloudEndPoint + "/" + fileName;
        Log.i(TAG, "uploadFile: url = " + url);
        //http://dogmanage.file.obs.cn-north-4.myhuaweicloud.com/54577243b9b38770.jpg

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                illegalFileUrl = url;
                GlideLoader.LoderImage(EnforcementSubmitActivity.this, url, binding.illegalFileUrlView, 6);


            }
        });


    }
}