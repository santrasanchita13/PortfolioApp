package com.santra.sanchita.portfolioapp.ui.checkout_form;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.santra.sanchita.portfolioapp.R;
import com.santra.sanchita.portfolioapp.ui.base.BaseActivity;
import com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form1.CheckoutForm1MvpView;
import com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form2.CheckoutForm2MvpView;
import com.santra.sanchita.portfolioapp.ui.custom_views.OptionalSwipeViewPager;
import com.santra.sanchita.portfolioapp.utils.EspressoIdlingResource;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sanchita on 19/12/17.
 */

public class CheckoutFormActivity extends BaseActivity implements CheckoutFormMvpView {

    @Inject
    CheckoutFormMvpPresenter<CheckoutFormMvpView> presenter;

    @Inject
    CheckoutFormPagerAdapter pagerAdapter;

    @BindView(R.id.view_pager_checkout_form)
    OptionalSwipeViewPager viewPagerCheckoutForm;

    @BindView(R.id.text_check1_checkout_form)
    TextView textCheck1CheckoutForm;

    @BindView(R.id.text_check2_checkout_form)
    TextView textCheck2CheckoutForm;

    @BindView(R.id.text_check3_checkout_form)
    TextView textCheck3CheckoutForm;

    @BindView(R.id.image_check1_checkout_form)
    ImageView imageCheck1CheckoutForm;

    @BindView(R.id.image_check2_checkout_form)
    ImageView imageCheck2CheckoutForm;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, CheckoutFormActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_form);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);

        setUp();
    }

    @Override
    public void gotoForm1() {
        if(viewPagerCheckoutForm != null) {
            viewPagerCheckoutForm.setCurrentItem(0);
        }
    }

    @Override
    public void gotoForm2() {
        if(pagerAdapter != null && pagerAdapter.getCurrentFragment() instanceof CheckoutForm1MvpView) {
            if (!((CheckoutForm1MvpView) pagerAdapter.getCurrentFragment()).showErrors() && viewPagerCheckoutForm != null) {
                viewPagerCheckoutForm.setCurrentItem(1);
            }
        }
        else {
            viewPagerCheckoutForm.setCurrentItem(1);
        }
    }

    @Override
    public void gotoForm3() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.hideSoftInputFromWindow(findViewById(android.R.id.content).getWindowToken(), 0);
        }

        if(pagerAdapter != null && pagerAdapter.getCurrentFragment() instanceof CheckoutForm2MvpView) {
            if (!((CheckoutForm2MvpView) pagerAdapter.getCurrentFragment()).showErrors() && viewPagerCheckoutForm != null) {
                viewPagerCheckoutForm.setCurrentItem(2);
            }
        }
        else if(pagerAdapter != null && !(pagerAdapter.getCurrentFragment() instanceof CheckoutForm1MvpView)) {
            viewPagerCheckoutForm.setCurrentItem(2);
        }
    }

    @Override
    public void gotoForm4() {
        if(viewPagerCheckoutForm != null) {
            viewPagerCheckoutForm.setCurrentItem(3);
            makeTopBarInvisible();
        }
    }

    private void makeTopBarInvisible() {
        textCheck1CheckoutForm.setVisibility(View.INVISIBLE);
        textCheck2CheckoutForm.setVisibility(View.INVISIBLE);
        textCheck3CheckoutForm.setVisibility(View.INVISIBLE);
        imageCheck1CheckoutForm.setVisibility(View.INVISIBLE);
        imageCheck2CheckoutForm.setVisibility(View.INVISIBLE);
    }

    private void makeTopBarVisible() {
        textCheck1CheckoutForm.setVisibility(View.VISIBLE);
        textCheck2CheckoutForm.setVisibility(View.VISIBLE);
        textCheck3CheckoutForm.setVisibility(View.VISIBLE);
        imageCheck1CheckoutForm.setVisibility(View.VISIBLE);
        imageCheck2CheckoutForm.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setUp() {

        viewPagerCheckoutForm.setAdapter(pagerAdapter);

        viewPagerCheckoutForm.setPagingEnabled(false);

        textCheck1CheckoutForm.setOnClickListener(v -> gotoForm1());

        textCheck2CheckoutForm.setOnClickListener(v -> gotoForm2());

        textCheck3CheckoutForm.setOnClickListener(v -> gotoForm3());

        viewPagerCheckoutForm.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        textCheck1CheckoutForm.setText(getString(R.string.fa_icon_circle));
                        textCheck1CheckoutForm.setTextColor(ContextCompat.getColor(CheckoutFormActivity.this, R.color.light_grey));
                        textCheck2CheckoutForm.setText(getString(R.string.fa_icon_circle));
                        textCheck2CheckoutForm.setTextColor(ContextCompat.getColor(CheckoutFormActivity.this, R.color.light_grey));
                        textCheck3CheckoutForm.setText(getString(R.string.fa_icon_circle));
                        textCheck3CheckoutForm.setTextColor(ContextCompat.getColor(CheckoutFormActivity.this, R.color.light_grey));
                        imageCheck1CheckoutForm.setImageResource(R.color.light_grey);
                        imageCheck2CheckoutForm.setImageResource(R.color.light_grey);
                        break;
                    case 1:
                        textCheck1CheckoutForm.setText(getString(R.string.fa_icon_round_check));
                        textCheck1CheckoutForm.setTextColor(ContextCompat.getColor(CheckoutFormActivity.this, R.color.colorPrimary));
                        textCheck2CheckoutForm.setText(getString(R.string.fa_icon_circle));
                        textCheck2CheckoutForm.setTextColor(ContextCompat.getColor(CheckoutFormActivity.this, R.color.light_grey));
                        textCheck3CheckoutForm.setText(getString(R.string.fa_icon_circle));
                        textCheck3CheckoutForm.setTextColor(ContextCompat.getColor(CheckoutFormActivity.this, R.color.light_grey));
                        imageCheck1CheckoutForm.setImageResource(R.color.light_grey);
                        imageCheck2CheckoutForm.setImageResource(R.color.light_grey);
                        break;
                    case 2:
                        textCheck1CheckoutForm.setText(getString(R.string.fa_icon_round_check));
                        textCheck1CheckoutForm.setTextColor(ContextCompat.getColor(CheckoutFormActivity.this, R.color.colorPrimary));
                        textCheck2CheckoutForm.setText(getString(R.string.fa_icon_round_check));
                        textCheck2CheckoutForm.setTextColor(ContextCompat.getColor(CheckoutFormActivity.this, R.color.colorPrimary));
                        textCheck3CheckoutForm.setText(getString(R.string.fa_icon_circle));
                        textCheck3CheckoutForm.setTextColor(ContextCompat.getColor(CheckoutFormActivity.this, R.color.light_grey));
                        imageCheck1CheckoutForm.setImageResource(R.color.colorPrimary);
                        imageCheck2CheckoutForm.setImageResource(R.color.light_grey);
                        break;
                    default:
                        textCheck1CheckoutForm.setText(getString(R.string.fa_icon_round_check));
                        textCheck1CheckoutForm.setTextColor(ContextCompat.getColor(CheckoutFormActivity.this, R.color.colorPrimary));
                        textCheck2CheckoutForm.setText(getString(R.string.fa_icon_round_check));
                        textCheck2CheckoutForm.setTextColor(ContextCompat.getColor(CheckoutFormActivity.this, R.color.colorPrimary));
                        textCheck3CheckoutForm.setText(getString(R.string.fa_icon_round_check));
                        textCheck3CheckoutForm.setTextColor(ContextCompat.getColor(CheckoutFormActivity.this, R.color.colorPrimary));
                        imageCheck1CheckoutForm.setImageResource(R.color.colorPrimary);
                        imageCheck2CheckoutForm.setImageResource(R.color.colorPrimary);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        hideActionBar();
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();

        super.onDestroy();
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}