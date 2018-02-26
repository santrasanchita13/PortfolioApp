package com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form4;

import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.santra.sanchita.portfolioapp.R;
import com.santra.sanchita.portfolioapp.di.component.ActivityComponent;
import com.santra.sanchita.portfolioapp.ui.base.BaseFragment;
import com.santra.sanchita.portfolioapp.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sanchita on 19/12/17.
 */

public class CheckoutForm4Fragment extends BaseFragment implements CheckoutForm4MvpView {

    @Inject
    CheckoutForm4MvpPresenter<CheckoutForm4MvpView> presenter;

    @BindView(R.id.text_checkout_form4)
    TextView textCheckoutForm4;

    ViewGroup container;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if(isResumed()) {
                startEnterAnimations();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout_form4, container, false);

        ActivityComponent component = getActivityComponent();

        if(component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }

        this.container = container;

        return view;
    }

    @Override
    protected void setUp(View view) {
        //Nothing to implement yet
    }

    private void startEnterAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Transition transition = TransitionInflater.from(getBaseActivity()).inflateTransition(android.R.transition.move);
            transition.setDuration(Constants.MOVE_DEFAULT_TIME);

            TransitionManager.beginDelayedTransition(container, transition);
        }

        ConstraintLayout.LayoutParams layoutParamsTextCheckoutForm4 = (ConstraintLayout.LayoutParams) textCheckoutForm4.getLayoutParams();

        ValueAnimator animator = ValueAnimator.ofFloat(getResources().getDimension(R.dimen.medium_icon_size), getResources().getDimension(R.dimen.page_icon_size));
        animator.setDuration(Constants.MOVE_DEFAULT_TIME);

        animator.addUpdateListener(valueAnimator -> {
            float animatedValue = (float) valueAnimator.getAnimatedValue();
            textCheckoutForm4.setTextSize(animatedValue);
        });

        animator.start();

        layoutParamsTextCheckoutForm4.leftToLeft = 0;
        layoutParamsTextCheckoutForm4.bottomToBottom = 0;
        layoutParamsTextCheckoutForm4.setMarginEnd(0);

        textCheckoutForm4.setLayoutParams(layoutParamsTextCheckoutForm4);
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }
}
