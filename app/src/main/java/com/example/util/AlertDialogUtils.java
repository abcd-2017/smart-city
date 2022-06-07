package com.example.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;

import com.example.R;

/**
 * @author kkk
 */
public class AlertDialogUtils {

    private static AlertDialog alertDialog;
    private static View alertView;
    private static OnDialogButtonClickListener buttonClickListener;

    public static AlertDialogUtils getInstance() {
        return new AlertDialogUtils();
    }

    public static void showConfirmDialog(Context context, String title, String message) {
        showConfirmDialog(context, title, message, null);
    }

    public static void showConfirmDialog(Context context, String title, String message, OnDialogButtonClickListener buttonClickListener) {
        alertView = LayoutInflater.from(context)
                .inflate(R.layout.alert_dialog_detail, null, false);

        alertDialog = new AlertDialog.Builder(context)
                .setView(alertView)
                .create();

        alertDialog.show();

        TextView titleView = alertView.findViewById(R.id.alert_dialog_title);
        TextView messageView = alertView.findViewById(R.id.alert_dialog_message);
        CardView okBtn = alertView.findViewById(R.id.alert_dialog_ok);
        CardView cancelBtn = alertView.findViewById(R.id.alert_dialog_cancel);

        titleView.setText(title);
        messageView.setText(message);

        okBtn.setOnClickListener(view -> {
            alertDialog.dismiss();
            buttonClickListener.clickOk();
        });

        cancelBtn.setOnClickListener(view -> {
            alertDialog.dismiss();
            buttonClickListener.clickCancel();
        });

        //点击空白地方不关闭弹窗
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(AppCompatResources.getDrawable(context, R.drawable.alert_bg_translate));
    }

    public static void setOnDialogButtonClickListener(OnDialogButtonClickListener buttonClickListener) {
        AlertDialogUtils.buttonClickListener = buttonClickListener;
    }

    public interface OnDialogButtonClickListener {
        void clickOk();

        void clickCancel();
    }
}
