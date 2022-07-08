package com.cjt2325.camera;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;

public class LoadingManager {

    private static Dialog loadingDialog = null;

    public static void showLoadingDialog(Activity activity) {
        showLoadingDialog(activity, null);
    }

    public static void showLoadingDialog(final Activity activity, final String msg) {
        try {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingDialog = null;
                    if (msg == null) {
                        loadingDialog = DialogManager.createLoadingDialog(activity);
                    } else {
                        loadingDialog = DialogManager.createLoadingDialog(activity, msg);
                    }
                    loadingDialog.show();
                    loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {

                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void hideLoadingDialog(Activity activity) {
        try {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (loadingDialog != null && loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                        loadingDialog = null;
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
