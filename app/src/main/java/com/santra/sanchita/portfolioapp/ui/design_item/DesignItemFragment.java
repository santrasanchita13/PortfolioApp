package com.santra.sanchita.portfolioapp.ui.design_item;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.santra.sanchita.portfolioapp.R;
import com.santra.sanchita.portfolioapp.data.db.model.DesignItem;
import com.santra.sanchita.portfolioapp.di.component.ActivityComponent;
import com.santra.sanchita.portfolioapp.ui.base.BaseFragment;
import com.santra.sanchita.portfolioapp.ui.checkout_form.CheckoutFormActivity;
import com.santra.sanchita.portfolioapp.ui.contact.ContactActivity;
import com.santra.sanchita.portfolioapp.ui.design_details.DesignDetailsActivity;
import com.santra.sanchita.portfolioapp.ui.introduction.IntroductionActivity;
import com.santra.sanchita.portfolioapp.ui.skills.SkillsActivity;
import com.santra.sanchita.portfolioapp.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sanchita on 13/12/17.
 */

public class DesignItemFragment extends BaseFragment implements DesignItemMvpView {

    @Inject
    DesignItemMvpPresenter<DesignItemMvpView> presenter;

    @BindView(R.id.image_design_item_bg)
    ImageView imageDesignItemBg;

    @BindView(R.id.image_design_item_container)
    ImageView imageDesignItemContainer;

    @BindView(R.id.text_design_item_title)
    TextView textDesignItemTitle;

    @BindView(R.id.text_design_item_heart)
    TextView textDesignItemHeart;

    @BindView(R.id.text_design_item_desc)
    TextView textDesignItemDesc;

    ViewPager viewPager;

    ViewGroup container;

    Integer imageBg;

    private enum CurrentMode {
        CARD_MODE, PREVIEW_MODE
    }

    private CurrentMode currentMode = CurrentMode.CARD_MODE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_design_item, container, false);

        this.container = container;

        ActivityComponent component = getActivityComponent();

        if(component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }

        viewPager = (ViewPager) container;

        return view;
    }

    @Override
    protected void setUp(View view) {

        Bundle args = getArguments();

        setFragmentSwipeListener(new FragmentSwipeListener() {
            @Override
            public void onSwipeUp() {
                changeToPreviewMode();
            }

            @Override
            public void onSwipeDown() {
                changeToCardMode();
            }
        });

        presenter.onViewPrepared(args);
    }

    @Override
    public void updateDetails(DesignItem designItem) {

        imageDesignItemBg.setImageDrawable(ContextCompat.getDrawable(getBaseActivity(), Integer.parseInt(designItem.getImagePath())));

        imageBg = Integer.parseInt(designItem.getImagePath());

        textDesignItemTitle.setText(designItem.getDesignItemName());

        if(designItem.getIsLiked()) {
            textDesignItemHeart.setTextColor(Color.RED);
        }
        else {
            textDesignItemHeart.setTextColor(Color.WHITE);
        }

        textDesignItemDesc.setText(getString(Integer.parseInt(designItem.getDescription())));

        setFragmentClickListener(view1 -> {
            if(currentMode == CurrentMode.CARD_MODE) {
                changeToPreviewMode();
            }
            else {
                switch (designItem.getDesignItemName()) {
                    case "Who am I?":
                        goToIntroductionActivity(designItem);
                        break;
                    case "My skills":
                        goToSkillsActivity(designItem);
                        break;
                    case "My interests":
                        goToCustomViewActivity(designItem);
                        break;
                    default:
                        goToContactActivity(designItem);
                        break;
                }
            }
        });
    }

    @Override
    public void changeToPreviewMode() {

        if(currentMode == CurrentMode.CARD_MODE) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                Transition transition = TransitionInflater.from(getBaseActivity()).inflateTransition(android.R.transition.move);
                transition.setDuration(Constants.MOVE_DEFAULT_TIME);

                TransitionManager.beginDelayedTransition(container, transition);
            }

            ConstraintLayout.LayoutParams layoutParamsImageBg = (ConstraintLayout.LayoutParams) imageDesignItemBg.getLayoutParams();
            layoutParamsImageBg.bottomToBottom = -1;

            imageDesignItemBg.setLayoutParams(layoutParamsImageBg);

            ConstraintLayout.LayoutParams layoutParamsImageContainer = (ConstraintLayout.LayoutParams) imageDesignItemContainer.getLayoutParams();
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                layoutParamsImageContainer.width = (int) getResources().getDimension(R.dimen.parent_size);
                layoutParamsImageContainer.topMargin = (int) getResources().getDimension(R.dimen.large_padding);
            }
            else {
                layoutParamsImageContainer.width = (int) getResources().getDimension(R.dimen.parent_width);
                layoutParamsImageContainer.topMargin = (int) getResources().getDimension(R.dimen.large_padding);
            }

            imageDesignItemContainer.setLayoutParams(layoutParamsImageContainer);

            textDesignItemDesc.animate().alpha(1.0f).setDuration(Constants.MOVE_DEFAULT_TIME).start();

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                imageDesignItemBg.animate().translationZ(getResources().getDimension(R.dimen.small_elevation)).setDuration(Constants.MOVE_DEFAULT_TIME).start();

                textDesignItemTitle.animate().translationZ(getResources().getDimension(R.dimen.small_elevation)).setDuration(Constants.MOVE_DEFAULT_TIME).start();

                textDesignItemHeart.animate().translationZ(getResources().getDimension(R.dimen.small_elevation)).setDuration(Constants.MOVE_DEFAULT_TIME).start();
            }
            currentMode = CurrentMode.PREVIEW_MODE;
        }
    }

    @Override
    public void changeToCardMode() {

        if(currentMode == CurrentMode.PREVIEW_MODE) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                Transition transition = TransitionInflater.from(getBaseActivity()).inflateTransition(android.R.transition.move);
                transition.setDuration(Constants.MOVE_DEFAULT_TIME);

                TransitionManager.beginDelayedTransition(container, transition);
            }

            ConstraintLayout.LayoutParams layoutParamsImageContainer = (ConstraintLayout.LayoutParams) imageDesignItemContainer.getLayoutParams();
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                layoutParamsImageContainer.width = (int) getResources().getDimension(R.dimen.large_height);
                layoutParamsImageContainer.topMargin = 0;
            }
            else {
                layoutParamsImageContainer.width = (int) getResources().getDimension(R.dimen.large_width);
                layoutParamsImageContainer.topMargin = 0;
            }

            imageDesignItemContainer.setLayoutParams(layoutParamsImageContainer);

            ConstraintLayout.LayoutParams layoutParamsImageBg = (ConstraintLayout.LayoutParams) imageDesignItemBg.getLayoutParams();
            layoutParamsImageBg.bottomToBottom = 0;

            imageDesignItemBg.setLayoutParams(layoutParamsImageBg);

            textDesignItemDesc.animate().alpha(0.0f).setDuration(Constants.MOVE_DEFAULT_TIME).start();

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                imageDesignItemBg.animate().translationZ(getResources().getDimension(R.dimen.reduce_elevation)).setDuration(Constants.MOVE_DEFAULT_TIME).start();

                textDesignItemTitle.animate().translationZ(getResources().getDimension(R.dimen.reduce_elevation)).setDuration(Constants.MOVE_DEFAULT_TIME).start();

                textDesignItemHeart.animate().translationZ(getResources().getDimension(R.dimen.reduce_elevation)).setDuration(Constants.MOVE_DEFAULT_TIME).start();
            }

            currentMode = CurrentMode.CARD_MODE;
        }
    }

    @Override
    public void goToFormActivity(DesignItem designItem) {
        Intent intent = CheckoutFormActivity.getStartIntent(getBaseActivity());
        intent.putExtra(Constants.POSITION, designItem.getId().intValue());
        startActivity(intent);
    }

    @Override
    public void goToContactActivity(DesignItem designItem) {

        ViewCompat.setTransitionName(textDesignItemTitle, getString(R.string.transition_name_text_view_contact));

        Intent intent = ContactActivity.getStartIntent(getBaseActivity());
        intent.putExtra(Constants.POSITION, designItem.getId().intValue());

        Pair<View, String> sharedElement2 = Pair.create(textDesignItemTitle, ViewCompat.getTransitionName(textDesignItemTitle));

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getBaseActivity(), sharedElement2);
        startActivity(intent, optionsCompat.toBundle());
    }

    @Override
    public void goToSkillsActivity(DesignItem designItem) {
        ViewCompat.setTransitionName(imageDesignItemBg, getString(R.string.transition_name_image_view_skills));

        ViewCompat.setTransitionName(textDesignItemTitle, getString(R.string.transition_name_text_view_skills));

        ViewCompat.setTransitionName(imageDesignItemContainer, getString(R.string.transition_name_image_view_skills_container));

        Intent intent = SkillsActivity.getStartIntent(getBaseActivity());
        intent.putExtra(Constants.POSITION, designItem.getId().intValue());

        Pair<View, String> sharedElement1 = Pair.create(imageDesignItemBg, ViewCompat.getTransitionName(imageDesignItemBg));
        Pair<View, String> sharedElement2 = Pair.create(textDesignItemTitle, ViewCompat.getTransitionName(textDesignItemTitle));
        Pair<View, String> sharedElement3 = Pair.create(imageDesignItemContainer, ViewCompat.getTransitionName(imageDesignItemContainer));

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getBaseActivity(), sharedElement1, sharedElement2, sharedElement3);
        startActivity(intent, optionsCompat.toBundle());
    }

    @Override
    public void goToIntroductionActivity(DesignItem designItem) {

        ViewCompat.setTransitionName(imageDesignItemBg, getString(R.string.transition_name_image_item_profile_introduction, 0));

        ViewCompat.setTransitionName(imageDesignItemContainer, getString(R.string.transition_name_image_bg_item_profile_introduction, 0));

        Intent intent = IntroductionActivity.getStartIntent(getBaseActivity());
        intent.putExtra(Constants.POSITION, designItem.getId().intValue());

        Pair<View, String> sharedElement1 = Pair.create(imageDesignItemBg, ViewCompat.getTransitionName(imageDesignItemBg));
        Pair<View, String> sharedElement3 = Pair.create(imageDesignItemContainer, ViewCompat.getTransitionName(imageDesignItemContainer));

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getBaseActivity(), sharedElement1, sharedElement3);
        startActivity(intent, optionsCompat.toBundle());
    }

    @Override
    public void goToCustomViewActivity(DesignItem designItem) {

        Bundle args = getArguments();

        ViewCompat.setTransitionName(imageDesignItemBg, getString(R.string.transition_name_image_view_design_item, args.getInt(Constants.POSITION)));

        ViewCompat.setTransitionName(textDesignItemTitle, getString(R.string.transition_name_text_view_design_item_title, args.getInt(Constants.POSITION)));

        //ViewCompat.setTransitionName(textDesignItemHeart, getString(R.string.transition_name_text_view_design_item_heart, args.getInt(Constants.POSITION)));

        int imageDrawable = Integer.parseInt(designItem.getImagePath());

        Intent intent = DesignDetailsActivity.getStartIntent(getBaseActivity());
        intent.putExtra(Constants.POSITION, designItem.getId().intValue());

        Pair<View, String> sharedElement1 = Pair.create(imageDesignItemBg, ViewCompat.getTransitionName(imageDesignItemBg));
        Pair<View, String> sharedElement2 = Pair.create(textDesignItemTitle, ViewCompat.getTransitionName(textDesignItemTitle));
        //Pair<View, String> sharedElement3 = Pair.create(textDesignItemHeart, ViewCompat.getTransitionName(textDesignItemHeart));

        /*Palette.from(BitmapFactory.decodeResource(getResources(), imageDrawable)).generate(palette -> {
            Palette.Swatch vibrantSwatch = palette.getDominantSwatch();
            if(vibrantSwatch != null) {
                int bgColor = vibrantSwatch.getRgb();
                intent.putExtra(Constants.BG_COLOR, bgColor);
            }
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getBaseActivity(), sharedElement1, sharedElement2, sharedElement3);
            startActivity(intent, optionsCompat.toBundle());
        });*/

        intent.putExtra(Constants.BG_COLOR, ContextCompat.getColor(getBaseActivity(),R.color.colorAccent));
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getBaseActivity(), sharedElement1, sharedElement2);
        startActivity(intent, optionsCompat.toBundle());
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }
}
