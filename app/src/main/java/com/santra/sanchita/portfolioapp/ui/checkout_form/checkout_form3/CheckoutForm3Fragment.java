package com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santra.sanchita.portfolioapp.R;
import com.santra.sanchita.portfolioapp.di.component.ActivityComponent;
import com.santra.sanchita.portfolioapp.ui.base.BaseFragment;
import com.santra.sanchita.portfolioapp.ui.checkout_form.CheckoutFormMvpView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sanchita on 19/12/17.
 */

public class CheckoutForm3Fragment extends BaseFragment implements CheckoutForm3MvpView {

    @Inject
    CheckoutForm3MvpPresenter<CheckoutForm3MvpView> presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout_form3, container, false);

        ActivityComponent component = getActivityComponent();

        if(component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }

        return view;
    }

    @Override
    protected void setUp(View view) {
        //Nothing to implement yet
    }

    @OnClick(R.id.button_proceed_checkout_form3)
    void onProceedClick() {
        if(getActivity() instanceof CheckoutFormMvpView) {
            ((CheckoutFormMvpView) getActivity()).gotoForm4();
        }
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }
}
