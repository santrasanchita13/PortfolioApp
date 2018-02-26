package com.santra.sanchita.portfolioapp.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.View;

import com.santra.sanchita.portfolioapp.di.component.ActivityComponent;
import com.santra.sanchita.portfolioapp.utils.SwipeGestureDetector;

import butterknife.Unbinder;

/**
 * Created by sanchita on 6/12/17.
 */

public abstract class BaseFragment extends Fragment implements MvpView {
    private BaseActivity mActivity;
    private Unbinder mUnBinder;
    private FragmentClickListener fragmentClickListener;
    private FragmentSwipeListener fragmentSwipeListener;
    private GestureDetector gestureDetector;
    private ProgressDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gestureDetector = new GestureDetector(mActivity, new SwipeGestureDetector() {
            @Override
            public void onSwipeUp() {
                if(fragmentSwipeListener != null) {
                    fragmentSwipeListener.onSwipeUp();
                }
            }

            @Override
            public void onSwipeDown() {
                if(fragmentSwipeListener != null) {
                    fragmentSwipeListener.onSwipeDown();
                }
            }
        });

        view.setOnClickListener(v -> {
            if(fragmentClickListener != null) {
                fragmentClickListener.onFragmentClick(view);
            }
        });

        view.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));

        dialog = new ProgressDialog(getBaseActivity());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        setUp(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void hideActionBar() {
        if (mActivity != null) {
            mActivity.hideActionBar();
        }
    }

    @Override
    public void showPopUpExplanation(String message, int gravity) {
        if (mActivity != null) {
            mActivity.showPopUpExplanation(message, gravity);
        }
    }

    @Override
    public void showLoading() {
        dialog.setMessage("Just a moment...");
        dialog.show();
    }

    @Override
    public void hideLoading() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onError(String message) {
        if (mActivity != null) {
            mActivity.onError(message);
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        if (mActivity != null) {
            mActivity.onError(resId);
        }
    }

    @Override
    public void showMessage(String message) {
        if (mActivity != null) {
            mActivity.showMessage(message);
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        if (mActivity != null) {
            mActivity.showMessage(resId);
        }
    }

    @Override
    public boolean isNetworkConnected() {
        if (mActivity != null) {
            return mActivity.isNetworkConnected();
        }
        return false;
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    public ActivityComponent getActivityComponent() {
        if (mActivity != null) {
            return mActivity.getActivityComponent();
        }
        return null;
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    public void setFragmentClickListener(FragmentClickListener f) {
        fragmentClickListener = f;
    }

    public void setFragmentSwipeListener(FragmentSwipeListener f) {
        fragmentSwipeListener = f;
    }

    protected abstract void setUp(View view);

    @Override
    public void onDestroy() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }

    public interface FragmentClickListener {
        void onFragmentClick(View view);
    }

    public interface FragmentSwipeListener {

        void onSwipeUp();

        void onSwipeDown();
    }
}
