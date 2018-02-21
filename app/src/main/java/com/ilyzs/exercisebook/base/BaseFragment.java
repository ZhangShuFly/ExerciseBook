package com.ilyzs.exercisebook.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhangshu on 2018/2/20.
 */

public abstract class BaseFragment extends Fragment implements BaseView{

    protected View mView;

    public abstract int getContentViewId();

    public abstract void initAllMembersView(Bundle saveInstanceState);

    private Context context;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getContentViewId(),container,false);
       this.context = getActivity();
       initAllMembersView(savedInstanceState);
       return mView;
    }

    @Override
    public void showLoading() {
        checkActivityAttached();
        ((BaseActivity)getActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        checkActivityAttached();
        ((BaseActivity)getActivity()).hideLoading();
    }

    @Override
    public void showToast(String msg) {
        checkActivityAttached();
        ((BaseActivity)getActivity()).showToast(msg);
    }

    @Override
    public void showError() {
        checkActivityAttached();
        ((BaseActivity)getActivity()).showError();
    }

    @Override
    public Context getContext() {
        checkActivityAttached();
        return context;
    }

    /**
     * 检查activity连接情况
     */
    public void checkActivityAttached() {
        if (getActivity() == null) {
            throw new ActivityNotAttachedException();
        }
    }
    public static class ActivityNotAttachedException extends RuntimeException {
        public ActivityNotAttachedException() {
            super("Fragment has disconnected from Activity ! - -.");
        }
    }
}
