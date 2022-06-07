package com.example.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.util.Constant;

/**
 * @author kkk
 */
public abstract class BaseFragment extends Fragment {

    public View mContent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mContent == null) {
            mContent = inflater.inflate(initLayout(), container, false);
            initView();
        }
        return mContent;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initData();
    }

    protected abstract int initLayout();

    protected abstract void initView();

    protected abstract void initData();


    public void setStringToSP(String key, String value) {
        SharedPreferences preferences = requireActivity().getSharedPreferences(Constant.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public String getStringToSP(String key) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(Constant.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    public void showSyncToast(String msg) {
        requireActivity().runOnUiThread(() -> {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        });
    }

    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public void jumpPage(Class clazz) {
        startActivity(new Intent(getActivity(), clazz));
    }

    public void jumpPageToIntent(Intent intent) {
        startActivity(intent);
    }

    public void jumpPageFlag(Class clazz, int flag) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.setFlags(flag);
        startActivity(intent);
    }
}
