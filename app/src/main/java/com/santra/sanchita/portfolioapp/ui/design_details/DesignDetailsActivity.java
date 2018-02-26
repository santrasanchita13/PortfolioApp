package com.santra.sanchita.portfolioapp.ui.design_details;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.constraint.ConstraintLayout;
import android.support.test.espresso.IdlingResource;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.santra.sanchita.portfolioapp.R;
import com.santra.sanchita.portfolioapp.data.db.model.DesignItem;
import com.santra.sanchita.portfolioapp.ui.base.BaseActivity;
import com.santra.sanchita.portfolioapp.utils.ColorCheck;
import com.santra.sanchita.portfolioapp.utils.Constants;
import com.santra.sanchita.portfolioapp.utils.EspressoIdlingResource;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sanchita on 15/12/17.
 */

public class DesignDetailsActivity extends BaseActivity implements DesignDetailsMvpView {

    @Inject
    DesignDetailsMvpPresenter<DesignDetailsMvpView> presenter;

    @BindView(R.id.constraint_layout_design_details)
    ViewGroup container;

    @BindView(R.id.image_design_details_bg)
    ImageView imageDesignDetailsBg;

    @BindView(R.id.text_design_details_title)
    TextView textDesignDetailsTitle;

    @BindView(R.id.text_design_details_heart)
    TextView textDesignDetailsHeart;

    @BindView(R.id.image_design_details_square_bg)
    ImageView imageDesignDetailsSquareBg;

    @BindView(R.id.text_design_details_left_arrow)
    TextView textDesignDetailsLeftArrow;

    @BindView(R.id.text_design_details_right_arrow)
    TextView textDesignDetailsRightArrow;

    @BindView(R.id.image_design_details_middle)
    ImageView imageDesignDetailsMiddle;

    @BindView(R.id.image_design_details_left)
    ImageView imageDesignDetailsLeft;

    @BindView(R.id.image_design_details_right)
    ImageView imageDesignDetailsRight;

    @BindView(R.id.image_design_details_content)
    ImageView imageDesignDetailsContent;

    @BindView(R.id.text_design_details_content_title)
    TextView textDesignDetailsContentTitle;

    @BindView(R.id.text_design_details_content_desc)
    TextView textDesignDetailsContentDesc;

    @BindView(R.id.text_info_design_details)
    TextView textInfoDesignDetails;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, DesignDetailsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_details);

        supportPostponeEnterTransition();

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);

        if(savedInstanceState != null) {
            animateEnterTransitions();
        }

        setUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideActionBar();
    }

    @Override
    protected void setUp() {

        textInfoDesignDetails.setOnClickListener(v -> showPopUpExplanation(getString(R.string.interests_activity_info), Gravity.BOTTOM));

        Bundle extras = getIntent().getExtras();

        presenter.onViewAttached(extras);

        textDesignDetailsContentDesc.setMovementMethod(new ScrollingMovementMethod());

        if (extras != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageDesignDetailsBg.setTransitionName(getString(R.string.transition_name_image_view_design_item, extras.getInt(Constants.POSITION)));
            textDesignDetailsTitle.setTransitionName(getString(R.string.transition_name_text_view_design_item_title, extras.getInt(Constants.POSITION)));
            //textDesignDetailsHeart.setTransitionName(getString(R.string.transition_name_text_view_design_item_heart, extras.getInt(Constants.POSITION)));
        }

        textDesignDetailsHeart.setVisibility(View.GONE);

        if(extras != null && extras.getInt(Constants.BG_COLOR) != 0) {
            imageDesignDetailsBg.setBackgroundColor(extras.getInt(Constants.BG_COLOR));

            if(ColorCheck.isColorDark(extras.getInt(Constants.BG_COLOR))) {
                textDesignDetailsTitle.setTextColor(Color.WHITE);
            }
            else {
                textDesignDetailsTitle.setTextColor(Color.BLACK);
            }
        }
        else {
            imageDesignDetailsBg.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        imageDesignDetailsMiddle.setOnClickListener(onMiddleImageClick);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Transition enterTransition = getWindow().getSharedElementEnterTransition();
                enterTransition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    transition.removeListener(this);
                    animateEnterTransitions();
                    setupExitTransitions();
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        }
        else {
            animateEnterTransitions();
        }

        supportStartPostponedEnterTransition();

    }

    @Override
    public void updateDetails(DesignItem designItem) {
        textDesignDetailsTitle.setText(designItem.getDesignItemName());

        /*if(designItem.getIsLiked()) {
            textDesignDetailsHeart.setTextColor(Color.RED);
        }
        else {
            textDesignDetailsHeart.setTextColor(Color.WHITE);
        }*/
    }

    public void setupExitTransitions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //TODO: Not working. Google bug
            //getWindow().getSharedElementReturnTransition().setStartDelay(Constants.MOVE_DEFAULT_TIME + Constants.MOVE_DELAY_TIME);

            getWindow().getReturnTransition().setDuration(0).addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                    transition.removeListener(this);
                    animateExitTransitions();
                }

                @Override
                public void onTransitionEnd(Transition transition) {

                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        }
    }

    @OnClick(R.id.text_design_details_heart)
    public void onHeartClick() {
        //Not yet implemented
    }

    @OnClick({R.id.text_design_details_left_arrow, R.id.image_design_details_left})
    public void onLeftArrowClick() {
        shiftLeft();
    }

    @OnClick({R.id.text_design_details_right_arrow, R.id.image_design_details_right})
    public void onRightArrowClick() {
        shiftRight();
    }

    View.OnClickListener onLeftArrowImageClick = v -> shiftLeft();

    View.OnClickListener onRightArrowImageClick = v -> shiftRight();

    View.OnClickListener onMiddleImageClick = v -> {
        //startActivity(CheckoutFormActivity.getStartIntent(DesignDetailsActivity.this));
    };

    @Override
    public void shiftLeft() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Transition transition = TransitionInflater.from(this).inflateTransition(android.R.transition.move);
            transition.setDuration(Constants.MOVE_DEFAULT_TIME);

            TransitionManager.beginDelayedTransition(container, transition);
        }

        ConstraintLayout.LayoutParams layoutParamsImageMiddle = (ConstraintLayout.LayoutParams) imageDesignDetailsMiddle.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParamsImageLeft = (ConstraintLayout.LayoutParams) imageDesignDetailsLeft.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParamsImageRight = (ConstraintLayout.LayoutParams) imageDesignDetailsRight.getLayoutParams();

        imageDesignDetailsMiddle.setLayoutParams(layoutParamsImageRight);

        imageDesignDetailsLeft.setLayoutParams(layoutParamsImageMiddle);

        imageDesignDetailsRight.setLayoutParams(layoutParamsImageLeft);

        if(imageDesignDetailsMiddle.getAlpha() > 0.5) {
            showDescription(2);
            imageDesignDetailsMiddle.animate().alpha(0.5f).setDuration(Constants.MOVE_DEFAULT_TIME).start();
            imageDesignDetailsLeft.animate().alpha(1.0f).setDuration(Constants.MOVE_DEFAULT_TIME).start();
            imageDesignDetailsRight.animate().alpha(0.5f).setDuration(Constants.MOVE_DEFAULT_TIME).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    animation.removeListener(this);
                    imageDesignDetailsLeft.setOnClickListener(onMiddleImageClick);
                    imageDesignDetailsMiddle.setOnClickListener(onRightArrowImageClick);
                    imageDesignDetailsRight.setOnClickListener(onLeftArrowImageClick);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }
        else if(imageDesignDetailsLeft.getAlpha() > 0.5) {
            showDescription(1);
            imageDesignDetailsMiddle.animate().alpha(0.5f).setDuration(Constants.MOVE_DEFAULT_TIME).start();
            imageDesignDetailsLeft.animate().alpha(0.5f).setDuration(Constants.MOVE_DEFAULT_TIME).start();
            imageDesignDetailsRight.animate().alpha(1.0f).setDuration(Constants.MOVE_DEFAULT_TIME).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    animation.removeListener(this);
                    imageDesignDetailsRight.setOnClickListener(onMiddleImageClick);
                    imageDesignDetailsMiddle.setOnClickListener(onLeftArrowImageClick);
                    imageDesignDetailsLeft.setOnClickListener(onRightArrowImageClick);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }
        else {
            showDescription(0);
            imageDesignDetailsMiddle.animate().alpha(1.0f).setDuration(Constants.MOVE_DEFAULT_TIME).start();
            imageDesignDetailsLeft.animate().alpha(0.5f).setDuration(Constants.MOVE_DEFAULT_TIME).start();
            imageDesignDetailsRight.animate().alpha(0.5f).setDuration(Constants.MOVE_DEFAULT_TIME).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    animation.removeListener(this);
                    imageDesignDetailsMiddle.setOnClickListener(onMiddleImageClick);
                    imageDesignDetailsLeft.setOnClickListener(onLeftArrowImageClick);
                    imageDesignDetailsRight.setOnClickListener(onRightArrowImageClick);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }
    }

    @Override
    public void shiftRight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Transition transition = TransitionInflater.from(this).inflateTransition(android.R.transition.move);
            transition.setDuration(Constants.MOVE_DEFAULT_TIME);

            TransitionManager.beginDelayedTransition(container, transition);
        }

        ConstraintLayout.LayoutParams layoutParamsImageMiddle = (ConstraintLayout.LayoutParams) imageDesignDetailsMiddle.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParamsImageLeft = (ConstraintLayout.LayoutParams) imageDesignDetailsLeft.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParamsImageRight = (ConstraintLayout.LayoutParams) imageDesignDetailsRight.getLayoutParams();

        imageDesignDetailsMiddle.setLayoutParams(layoutParamsImageLeft);

        imageDesignDetailsLeft.setLayoutParams(layoutParamsImageRight);

        imageDesignDetailsRight.setLayoutParams(layoutParamsImageMiddle);

        if(imageDesignDetailsMiddle.getAlpha() > 0.5) {
            showDescription(1);
            imageDesignDetailsMiddle.animate().alpha(0.5f).setDuration(Constants.MOVE_DEFAULT_TIME).start();
            imageDesignDetailsLeft.animate().alpha(0.5f).setDuration(Constants.MOVE_DEFAULT_TIME).start();
            imageDesignDetailsRight.animate().alpha(1.0f).setDuration(Constants.MOVE_DEFAULT_TIME).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    animation.removeListener(this);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    imageDesignDetailsRight.setOnClickListener(onMiddleImageClick);
                    imageDesignDetailsMiddle.setOnClickListener(onLeftArrowImageClick);
                    imageDesignDetailsLeft.setOnClickListener(onRightArrowImageClick);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }
        else if(imageDesignDetailsRight.getAlpha() > 0.5) {
            showDescription(2);
            imageDesignDetailsMiddle.animate().alpha(0.5f).setDuration(Constants.MOVE_DEFAULT_TIME).start();
            imageDesignDetailsLeft.animate().alpha(1.0f).setDuration(Constants.MOVE_DEFAULT_TIME).start();
            imageDesignDetailsRight.animate().alpha(0.5f).setDuration(Constants.MOVE_DEFAULT_TIME).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    animation.removeListener(this);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    imageDesignDetailsLeft.setOnClickListener(onMiddleImageClick);
                    imageDesignDetailsMiddle.setOnClickListener(onRightArrowImageClick);
                    imageDesignDetailsRight.setOnClickListener(onLeftArrowImageClick);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }
        else {
            showDescription(0);
            imageDesignDetailsMiddle.animate().alpha(1.0f).setDuration(Constants.MOVE_DEFAULT_TIME).start();
            imageDesignDetailsLeft.animate().alpha(0.5f).setDuration(Constants.MOVE_DEFAULT_TIME).start();
            imageDesignDetailsRight.animate().alpha(0.5f).setDuration(Constants.MOVE_DEFAULT_TIME).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    animation.removeListener(this);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    imageDesignDetailsMiddle.setOnClickListener(onMiddleImageClick);
                    imageDesignDetailsLeft.setOnClickListener(onLeftArrowImageClick);
                    imageDesignDetailsRight.setOnClickListener(onRightArrowImageClick);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }
    }

    private void showDescription(int item) {
        switch (item) {
            case 0:
                textDesignDetailsContentTitle.setAlpha(0f);
                textDesignDetailsContentTitle.setText("Creative arts");
                textDesignDetailsContentTitle.animate().alpha(1f).setDuration(Constants.FADE_DEFAULT_TIME).start();

                textDesignDetailsContentDesc.setAlpha(0f);
                textDesignDetailsContentDesc.setText(getString(R.string.interests_creative_arts));
                textDesignDetailsContentDesc.animate().alpha(1f).setDuration(Constants.FADE_DEFAULT_TIME).start();
                break;
            case 1:
                textDesignDetailsContentTitle.setAlpha(0f);
                textDesignDetailsContentTitle.setText("Travel");
                textDesignDetailsContentTitle.animate().alpha(1f).setDuration(Constants.FADE_DEFAULT_TIME).start();

                textDesignDetailsContentDesc.setAlpha(0f);
                textDesignDetailsContentDesc.setText(getString(R.string.interests_travel));
                textDesignDetailsContentDesc.animate().alpha(1f).setDuration(Constants.FADE_DEFAULT_TIME).start();
                break;
            case 2:
                textDesignDetailsContentTitle.setAlpha(0f);
                textDesignDetailsContentTitle.setText("Food");
                textDesignDetailsContentTitle.animate().alpha(1f).setDuration(Constants.FADE_DEFAULT_TIME).start();

                textDesignDetailsContentDesc.setAlpha(0f);
                textDesignDetailsContentDesc.setText(getString(R.string.interests_food));
                textDesignDetailsContentDesc.animate().alpha(1f).setDuration(Constants.FADE_DEFAULT_TIME).start();
                break;
            default:
                break;
        }
    }

    @Override
    public void animateEnterTransitions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Transition transition = TransitionInflater.from(this).inflateTransition(android.R.transition.move);
            transition.setDuration(Constants.MOVE_DEFAULT_TIME);

            TransitionManager.beginDelayedTransition(container, transition);
        }

        ConstraintLayout.LayoutParams layoutParamsImageContent = (ConstraintLayout.LayoutParams) imageDesignDetailsContent.getLayoutParams();
        layoutParamsImageContent.topMargin = (int) getResources().getDimension(R.dimen.less_largest_padding);

        imageDesignDetailsContent.setLayoutParams(layoutParamsImageContent);
        imageDesignDetailsContent.setBackgroundColor(Color.WHITE);

        textDesignDetailsContentTitle.setText("Creative arts");
        textDesignDetailsContentDesc.setText(getString(R.string.interests_creative_arts));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Transition transition = TransitionInflater.from(this).inflateTransition(android.R.transition.move);
            transition.setStartDelay(Constants.MOVE_DELAY_TIME);
            transition.setDuration(Constants.MOVE_DEFAULT_TIME);

            TransitionManager.beginDelayedTransition(container, transition);
        }

        ConstraintLayout.LayoutParams layoutParamsImageMiddle = (ConstraintLayout.LayoutParams) imageDesignDetailsMiddle.getLayoutParams();
        layoutParamsImageMiddle.topToBottom = -1;
        layoutParamsImageMiddle.topMargin = 0;
        layoutParamsImageMiddle.bottomToTop = R.id.guidelineHorizontal;
        layoutParamsImageMiddle.bottomMargin = (int) getResources().getDimension(R.dimen.double_medium_padding);

        imageDesignDetailsMiddle.setLayoutParams(layoutParamsImageMiddle);
        imageDesignDetailsMiddle.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.forms1));

        ConstraintLayout.LayoutParams layoutParamsImageLeft = (ConstraintLayout.LayoutParams) imageDesignDetailsLeft.getLayoutParams();
        layoutParamsImageLeft.topToBottom = -1;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutParamsImageLeft.topMargin = (int) getResources().getDimension(R.dimen.less_largest_padding);
        }
        else {
            layoutParamsImageLeft.topMargin = 0;
        }
        layoutParamsImageLeft.topToTop = 0;

        imageDesignDetailsLeft.setLayoutParams(layoutParamsImageLeft);
        imageDesignDetailsLeft.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.forms2));

        ConstraintLayout.LayoutParams layoutParamsImageRight = (ConstraintLayout.LayoutParams) imageDesignDetailsRight.getLayoutParams();
        layoutParamsImageLeft.topToBottom = -1;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutParamsImageRight.topMargin = (int) getResources().getDimension(R.dimen.less_largest_padding);
        }
        else {
            layoutParamsImageRight.topMargin = 0;
        }
        layoutParamsImageRight.topToTop = 0;

        imageDesignDetailsRight.setLayoutParams(layoutParamsImageRight);
        imageDesignDetailsRight.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.forms3));

        imageDesignDetailsSquareBg.setVisibility(View.VISIBLE);
        textDesignDetailsLeftArrow.setVisibility(View.VISIBLE);
        textDesignDetailsRightArrow.setVisibility(View.VISIBLE);

        textDesignDetailsRightArrow.animate().alpha(1.0f).setStartDelay(Constants.MOVE_DELAY_TIME)
                .setDuration(Constants.MOVE_DEFAULT_TIME).start();
        textDesignDetailsLeftArrow.animate().alpha(1.0f).setStartDelay(Constants.MOVE_DELAY_TIME)
                .setDuration(Constants.MOVE_DEFAULT_TIME).start();
        imageDesignDetailsSquareBg.animate().alpha(0.3f).setStartDelay(Constants.MOVE_DELAY_TIME)
                .setDuration(Constants.MOVE_DEFAULT_TIME).start();

    }

    @Override
    public void animateExitTransitions() {

        textDesignDetailsRightArrow.animate().alpha(0.0f).setDuration(Constants.MOVE_DEFAULT_TIME).start();
        textDesignDetailsLeftArrow.animate().alpha(0.0f).setDuration(Constants.MOVE_DEFAULT_TIME).start();
        imageDesignDetailsSquareBg.animate().alpha(0.0f).setDuration(Constants.MOVE_DEFAULT_TIME).start();

        imageDesignDetailsSquareBg.setVisibility(View.GONE);
        textDesignDetailsLeftArrow.setVisibility(View.GONE);
        textDesignDetailsRightArrow.setVisibility(View.GONE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            Transition transition = TransitionInflater.from(this).inflateTransition(android.R.transition.move);
            transition.setStartDelay(Constants.MOVE_DELAY_TIME);
            transition.setDuration(Constants.MOVE_DEFAULT_TIME);

            TransitionManager.beginDelayedTransition(container, transition);
        }

        ConstraintLayout.LayoutParams layoutParamsImageContent = (ConstraintLayout.LayoutParams) imageDesignDetailsContent.getLayoutParams();
        layoutParamsImageContent.topMargin = (int) getResources().getDimension(R.dimen.parent_size);

        imageDesignDetailsContent.setLayoutParams(layoutParamsImageContent);

        ConstraintLayout.LayoutParams layoutParamsImageMiddle = (ConstraintLayout.LayoutParams) imageDesignDetailsMiddle.getLayoutParams();
        layoutParamsImageMiddle.topToBottom = R.id.guidelineHorizontal;
        layoutParamsImageMiddle.topMargin = (int) getResources().getDimension(R.dimen.parent_size);
        layoutParamsImageMiddle.bottomToTop = -1;
        layoutParamsImageMiddle.bottomMargin = 0;

        imageDesignDetailsMiddle.setLayoutParams(layoutParamsImageMiddle);

        ConstraintLayout.LayoutParams layoutParamsImageLeft = (ConstraintLayout.LayoutParams) imageDesignDetailsLeft.getLayoutParams();
        layoutParamsImageLeft.topToBottom = R.id.guidelineHorizontal;
        layoutParamsImageLeft.topMargin = (int) getResources().getDimension(R.dimen.parent_size);
        layoutParamsImageLeft.topToTop = -1;

        imageDesignDetailsLeft.setLayoutParams(layoutParamsImageLeft);

        ConstraintLayout.LayoutParams layoutParamsImageRight = (ConstraintLayout.LayoutParams) imageDesignDetailsRight.getLayoutParams();
        layoutParamsImageLeft.topToBottom = R.id.guidelineHorizontal;
        layoutParamsImageRight.topMargin = (int) getResources().getDimension(R.dimen.parent_size);
        layoutParamsImageRight.topToTop = -1;

        imageDesignDetailsRight.setLayoutParams(layoutParamsImageRight);
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
