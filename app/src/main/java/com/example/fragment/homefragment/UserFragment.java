package com.example.fragment.homefragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.R;
import com.example.activity.useractivity.AllOrderActivity;
import com.example.activity.useractivity.FeedbackActivity;
import com.example.activity.useractivity.LoginActivity;
import com.example.activity.useractivity.RegisterActivity;
import com.example.activity.useractivity.UpdatePwdActivity;
import com.example.activity.useractivity.UserDetailsActivity;
import com.example.fragment.BaseFragment;
import com.example.pojo.User;
import com.example.pojo.UserInfoParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;

/**
 * @author kkk
 */
public class UserFragment extends BaseFragment {

    private static final String TAG = "UserFragment";
    private Button loginBtn, registerBtn, logoutBtn, updateBtn;
    private ConstraintLayout toOrderPage, toUpdatePege, toFeedback, userDetails, userLoginRegister;
    private TextView nickName, username;
    private ImageView userAuthor;

    public static UserFragment newInstance() {
        return new UserFragment();
    }


    @Override
    protected int initLayout() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView() {
        loginBtn = mContent.findViewById(R.id.to_login);
        registerBtn = mContent.findViewById(R.id.to_register);
        logoutBtn = mContent.findViewById(R.id.user_logout);
        updateBtn = mContent.findViewById(R.id.update_user_detail);
        nickName = mContent.findViewById(R.id.nick_name);
        username = mContent.findViewById(R.id.username);
        toOrderPage = mContent.findViewById(R.id.to_order_page);
        toUpdatePege = mContent.findViewById(R.id.to_update_password);
        toFeedback = mContent.findViewById(R.id.to_feedback);
        userAuthor = mContent.findViewById(R.id.user_author);
        userDetails = mContent.findViewById(R.id.user_details);
        userLoginRegister = mContent.findViewById(R.id.user_login_register);
    }

    @Override
    protected void initData() {
        loginBtn.setOnClickListener(view -> {
            jumpPage(LoginActivity.class);
        });
        registerBtn.setOnClickListener(view -> {
            jumpPage(RegisterActivity.class);
        });
        logoutBtn.setOnClickListener(view -> {
            new AlertDialog.Builder(getActivity())
                    .setTitle("提示")
                    .setMessage("确定要退出吗?")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d(TAG, "onClick: which => " + which);
                        }
                    })
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setStringToSP("token", "");
                            Api.config(Constant.NetWork.LOGOUT, null, requireActivity()).getRequest(new RequestCallback() {
                                @Override
                                public void success(String result) {
                                    Log.d(TAG, "success: result => " + result);
                                }

                                @Override
                                public void failure(Exception e) {

                                }
                            });
                            onStart();
                        }
                    }).create().show();
        });

        toUpdatePege.setOnClickListener(view -> {
            jumpPage(UpdatePwdActivity.class);
        });

        toFeedback.setOnClickListener(view -> {
            jumpPage(FeedbackActivity.class);
        });

        toOrderPage.setOnClickListener(view -> {
            jumpPage(AllOrderActivity.class);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        String token = getStringToSP("token");
        if (TextUtils.isEmpty(token)) {
            userDetails.setVisibility(View.GONE);
            logoutBtn.setVisibility(View.GONE);
            userLoginRegister.setVisibility(View.VISIBLE);
        } else {
            userDetails.setVisibility(View.VISIBLE);
            logoutBtn.setVisibility(View.VISIBLE);
            userLoginRegister.setVisibility(View.GONE);

            Api.config(Constant.NetWork.GETINFO, null, mContent.getContext()).getRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    Gson gson = new Gson();
                    UserInfoParam userInfoParam = gson.fromJson(result, UserInfoParam.class);
                    if (userInfoParam.getCode() == HttpURLConnection.HTTP_OK) {
                        User user = userInfoParam.getUser();
                        requireActivity().runOnUiThread(() -> {
                            if (!TextUtils.isEmpty(user.getAvatar())) {
                                Glide.with(mContent).load(user.getAvatar()).into(userAuthor);
                            }
                            setStringToSP("id", String.valueOf(user.getUserId()));
                            nickName.setText(user.getNickName());
                            username.setText(user.getUserName());
                        });
                        String userJson = gson.toJson(user);
                        setStringToSP("userInfo", userJson);
                    } else {
                        Log.d(TAG, "success: 加载失败");
                    }
                }

                @Override
                public void failure(Exception e) {
                    showSyncToast("网络异常");
                }
            });


            updateBtn.setOnClickListener(view -> {
                jumpPage(UserDetailsActivity.class);
            });
        }
    }
}