package com.santra.sanchita.portfolioapp.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;

import com.santra.sanchita.portfolioapp.R;
import com.santra.sanchita.portfolioapp.ui.base.BaseActivity;
import com.santra.sanchita.portfolioapp.ui.main.MainActivity;
import com.santra.sanchita.portfolioapp.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sanchita on 8/12/17.
 */

public class SplashActivity extends BaseActivity implements SplashMvpView {

    @Inject
    SplashMvpPresenter<SplashMvpView> presenter;

    @BindView(R.id.image_launcher)
    ImageView imageLauncher;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);
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
    public void openMainActivity() {
        new Handler().postDelayed(() -> {
            ViewCompat.setTransitionName(imageLauncher, getString(R.string.transition_name_image_launcher));

            Intent intent = MainActivity.getStartIntent(SplashActivity.this);

            Pair<View, String> sharedElement1 = Pair.create(imageLauncher, ViewCompat.getTransitionName(imageLauncher));

            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SplashActivity.this, sharedElement1);
            startActivity(intent, optionsCompat.toBundle());
            /*startActivity(MainActivity.getStartIntent(SplashActivity.this));*/
            finish();
        }, 2000);
    }

    @Override
    public void startSyncService() {
        //TODO: if we need to start a service
    }

    @Override
    protected void setUp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Fade().setDuration(Constants.FADE_DEFAULT_TIME));
            getWindow().setExitTransition(new Fade().setDuration(Constants.FADE_DEFAULT_TIME));
        }
    }
}
