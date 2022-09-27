package com.example.activity.takeoutactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.pojo.BaseParam;
import com.example.pojo.takeoutparam.TakeOutAddressDetailParam;
import com.example.util.AlertDialogUtils;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kkk
 */
public class TakeOutAddAddressActivity extends BaseActivity {

    private Toolbar takeoutToolbar;
    private ImageView finishBtn;
    private TextView addressTitle;
    private EditText addAddressName, addAddressPhone, addAddressDetail;
    private CardView addAddressTagHome, addAddressTagCompany, addAddressTagSchool, saveBtn, delBtn;
    private CardView[] tags;
    private String selectTag = null;
    private String[] addressTag = new String[]{"家", "公司", "学校"};
    private Gson gson = new Gson();

    @Override
    protected int initLayout() {
        return R.layout.activity_take_out_add_address;
    }

    @Override
    protected void initView() {
        takeoutToolbar = findViewById(R.id.take_out_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        addressTitle = findViewById(R.id.take_out_add_address_title);
        addAddressName = findViewById(R.id.take_out_add_address_name);
        addAddressPhone = findViewById(R.id.take_out_add_address_phone);
        addAddressDetail = findViewById(R.id.take_out_add_address_detail);
        addAddressTagHome = findViewById(R.id.take_out_add_address_tag_home);
        addAddressTagCompany = findViewById(R.id.take_out_add_address_tag_company);
        addAddressTagSchool = findViewById(R.id.take_out_add_address_tag_school);
        saveBtn = findViewById(R.id.take_out_add_address_save_btn);
        delBtn = findViewById(R.id.take_out_add_address_del_btn);
        tags = new CardView[]{addAddressTagHome, addAddressTagCompany, addAddressTagSchool};
    }

    @Override
    protected void initData() {
        takeoutToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });

        Bundle extras = getIntent().getExtras();
        String addressType = (String) extras.get("addressType");

        if ("edit".equals(addressType)) {
            addressTitle.setText("修改收货地址");
            delBtn.setVisibility(View.VISIBLE);

            //获取地址id
            Integer addressId = (Integer) extras.get("addressId");

            Api.config(Constant.NetWork.TAKE_OUT_ADDRESS, null, this).getRestfulRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    TakeOutAddressDetailParam addressDetailParam = gson.fromJson(result, TakeOutAddressDetailParam.class);
                    if (addressDetailParam.getCode() == HttpURLConnection.HTTP_OK) {
                        TakeOutAddressDetailParam.DataDTO detailParamData = addressDetailParam.getData();
                        runOnUiThread(() -> {
                            addAddressDetail.setText(detailParamData.getAddressDetail());
                            addAddressName.setText(detailParamData.getName());
                            addAddressPhone.setText(detailParamData.getPhone());

                            selectTag = detailParamData.getLabel();
                            toggleTagStyle();
                        });
                    } else {
                        showSyncToast(addressDetailParam.getMsg());
                    }
                }

                @Override
                public void failure(Exception e) {
                    showSyncToast("网络异常");
                }
            }, addressId);

            //点击修改地址
            saveBtn.setOnClickListener(view -> {
                String addressDetail = addAddressDetail.getText().toString();
                String addressName = addAddressName.getText().toString();
                String addressPhone = addAddressPhone.getText().toString();
                if (TextUtils.isEmpty(addressDetail)) {
                    showSyncToast("请填写地址");
                    return;
                }
                if (TextUtils.isEmpty(addressName)) {
                    showSyncToast("请填写收货人");
                    return;
                }
                if (TextUtils.isEmpty(addressPhone)) {
                    showSyncToast("请填写电话号码");
                    return;
                }
                if (TextUtils.isEmpty(selectTag)) {
                    showSyncToast("请选择标签");
                    return;
                }

                Map<String, Object> param = new HashMap<>();
                param.put("addressDetail", addressDetail);
                param.put("label", selectTag);
                param.put("name", addressName);
                param.put("phone", addressPhone);
                param.put("id", addressId);

                Api.config(Constant.NetWork.TAKE_OUT_ADDRESS, param, this).putRequest(new RequestCallback() {
                    @Override
                    public void success(String result) {
                        BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                        if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                            finish();
                            showSyncToast("修改成功");
                        } else {
                            showSyncToast(baseParam.getMsg());
                        }
                    }

                    @Override
                    public void failure(Exception e) {
                        showSyncToast("网络异常");
                    }
                });
            });

            //点击删除地址
            delBtn.setOnClickListener(view -> {
                //弹窗
                AlertDialogUtils.getInstance();
                AlertDialogUtils.showConfirmDialog(this, "提示", "确定要删除?", new AlertDialogUtils.OnDialogButtonClickListener() {
                    @Override
                    public void clickOk() {
                        Api.config(Constant.NetWork.TAKE_OUT_ADDRESS, null, TakeOutAddAddressActivity.this).deleteRestfulRequest(new RequestCallback() {
                            @Override
                            public void success(String result) {
                                BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                                if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                                    finish();
                                    showSyncToast("删除成功");
                                } else {
                                    showSyncToast(baseParam.getMsg());
                                }
                            }

                            @Override
                            public void failure(Exception e) {
                                showSyncToast("网络异常");
                            }
                        }, addressId);
                    }

                    @Override
                    public void clickCancel() {

                    }
                });
            });
        } else {
            addAddressDetail.setFocusable(true);
            addAddressDetail.requestFocus();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

            saveBtn.setOnClickListener(view -> {
                String addressDetail = addAddressDetail.getText().toString();
                String addressName = addAddressName.getText().toString();
                String addressPhone = addAddressPhone.getText().toString();
                if (TextUtils.isEmpty(addressDetail)) {
                    showSyncToast("请填写地址");
                    return;
                }
                if (TextUtils.isEmpty(addressName)) {
                    showSyncToast("请填写收货人");
                    return;
                }
                if (TextUtils.isEmpty(addressPhone)) {
                    showSyncToast("请填写电话号码");
                    return;
                }
                if (TextUtils.isEmpty(selectTag)) {
                    showSyncToast("请选择标签");
                    return;
                }

                Map<String, Object> param = new HashMap<>();
                param.put("addressDetail", addressDetail);
                param.put("label", selectTag);
                param.put("name", addressName);
                param.put("phone", addressPhone);

                Api.config(Constant.NetWork.TAKE_OUT_ADDRESS, param, this).postRequest(new RequestCallback() {
                    @Override
                    public void success(String result) {
                        BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                        if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                            finish();
                            showSyncToast("添加成功");
                        } else {
                            showSyncToast(baseParam.getMsg());
                        }
                    }

                    @Override
                    public void failure(Exception e) {
                        showSyncToast("网络异常");
                    }
                });
            });
        }

        addAddressTagHome.setOnClickListener(view -> {
            selectTag = "家";
            toggleTagStyle();
        });
        addAddressTagCompany.setOnClickListener(view -> {
            selectTag = "公司";
            toggleTagStyle();
        });
        addAddressTagSchool.setOnClickListener(view -> {
            selectTag = "学校";
            toggleTagStyle();
        });
    }

    //切换选中标签样式
    private void toggleTagStyle() {
        int currTag = -1;
        for (int i = 0; i < addressTag.length; i++) {
            if (addressTag[i].equals(selectTag)) {
                currTag = i;
            }
        }
        if (currTag == -1 || selectTag == null) {
            return;
        }
        for (int i = 0; i < tags.length; i++) {
            if (i == currTag) {
                tags[i].setCardBackgroundColor(Color.parseColor("#FFF8E7"));
            } else {
                tags[i].setCardBackgroundColor(Color.WHITE);
            }
        }
    }
}