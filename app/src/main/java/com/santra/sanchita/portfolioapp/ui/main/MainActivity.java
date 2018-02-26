package com.santra.sanchita.portfolioapp.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.widget.FrameLayout;

import com.santra.sanchita.portfolioapp.R;
import com.santra.sanchita.portfolioapp.ui.base.BaseActivity;
import com.santra.sanchita.portfolioapp.ui.design_list.DesignListFragment;
import com.santra.sanchita.portfolioapp.utils.EspressoIdlingResource;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sanchita on 11/12/17.
 */

public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject
    MainMvpPresenter<MainMvpView> presenter;

    @BindView(R.id.fragment_container_main)
    FrameLayout fragmentContainerMain;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);

        if(savedInstanceState != null) {
            return;
        }

        setUp();
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

    @Override
    protected void setUp() {

        // Create a new Fragment to be placed in the activity layout
        DesignListFragment fragment = new DesignListFragment();

        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        fragment.setArguments(getIntent().getExtras());

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container_main, fragment).commit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setReturnTransition(null);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
