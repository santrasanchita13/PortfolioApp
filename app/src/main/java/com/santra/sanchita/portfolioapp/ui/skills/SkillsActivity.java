package com.santra.sanchita.portfolioapp.ui.skills;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.widget.TextView;

import com.santra.sanchita.portfolioapp.R;
import com.santra.sanchita.portfolioapp.data.db.model.DesignItem;
import com.santra.sanchita.portfolioapp.ui.base.BaseActivity;
import com.santra.sanchita.portfolioapp.ui.custom_views.PieChart;
import com.santra.sanchita.portfolioapp.utils.Constants;
import com.santra.sanchita.portfolioapp.utils.EspressoIdlingResource;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sanchita on 2/1/18.
 */

public class SkillsActivity extends BaseActivity implements SkillsMvpView {

    @Inject
    SkillsMvpPresenter<SkillsMvpView> presenter;

    @BindView(R.id.text_skills_topic)
    TextView textSkillsTopic;

    @BindView(R.id.pie_chart_skills)
    PieChart pieChartSkills;

    @BindView(R.id.text_skills_title)
    TextView textSkillsTitle;

    @BindView(R.id.text_skills_desc)
    TextView textSkillsDesc;

    @BindView(R.id.text_info_skills)
    TextView textInfoSkills;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SkillsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);

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

        textInfoSkills.setOnClickListener(v -> showPopUpExplanation(getString(R.string.skills_activity_info), Gravity.BOTTOM));

        Bundle extras = getIntent().getExtras();

        presenter.onViewAttached(extras);

        textSkillsDesc.setMovementMethod(new ScrollingMovementMethod());

        Resources res = getResources();

        pieChartSkills.addItem("Android", 5f, res.getColor(R.color.colorPrimary));
        pieChartSkills.addItem("Node.js", 3f, res.getColor(R.color.colorPrimary));
        pieChartSkills.addItem("Others", 2f, res.getColor(R.color.colorPrimary));

        textSkillsTitle.setText("Android development");
        textSkillsDesc.setText(getString(R.string.skills_android));

        pieChartSkills.setOnCurrentItemChangedListener((source, currentItem) -> {
            switch (currentItem) {
                case 0:
                    textSkillsTitle.setText("Android development");
                    textSkillsDesc.setText(getString(R.string.skills_android));
                    break;
                case 1:
                    textSkillsTitle.setText("Node.js");
                    textSkillsDesc.setText(getString(R.string.skills_node));
                    break;
                case 2:
                    textSkillsTitle.setText("Other skills");
                    textSkillsDesc.setText(getString(R.string.skills_others));
                    break;
                default:
                    break;
            }
        });

        new Handler().postDelayed(() -> pieChartSkills.setCurrentItem(0), Constants.MOVE_DELAY_TIME);
    }

    @Override
    public void updateDetails(DesignItem designItem) {
        textSkillsTopic.setText(designItem.getDesignItemName());
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
