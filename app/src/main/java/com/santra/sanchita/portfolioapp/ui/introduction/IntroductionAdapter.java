package com.santra.sanchita.portfolioapp.ui.introduction;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.santra.sanchita.portfolioapp.R;
import com.santra.sanchita.portfolioapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanchita on 25/12/17.
 */

public class IntroductionAdapter extends RecyclerView.Adapter<IntroductionAdapter.ViewHolder> {

    private List<IntroductionItem> listIntroduction;
    private Context context;
    private ViewGroup container;

    public ProfileViewHolder profileViewHolder;
    private AboutViewHolder aboutViewHolder;

    public IntroductionAdapter(Context context, List<IntroductionItem> listIntroduction, ViewGroup container) {
        this.listIntroduction = listIntroduction;
        this.context = context;
        this.container = container;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
        }
    }

    public class ProfileViewHolder extends ViewHolder {

        ImageView imageBgItemProfileIntroduction;
        ImageView imageItemProfileIntroduction;
        TextView textItemProfileIntroduction;
        TextView iconPhoneItemAboutIntroduction, iconEmailItemAboutIntroduction;

        public ProfileViewHolder(View view) {
            super(view);

            imageBgItemProfileIntroduction = view.findViewById(R.id.image_bg_item_profile_introduction);
            imageItemProfileIntroduction = view.findViewById(R.id.image_item_profile_introduction);
            textItemProfileIntroduction = view.findViewById(R.id.text_item_profile_introduction);
            iconPhoneItemAboutIntroduction = view.findViewById(R.id.text_phone_profile_introduction);
            iconEmailItemAboutIntroduction = view.findViewById(R.id.text_email_profile_introduction);

        }
    }

    public class AboutViewHolder extends ViewHolder {

        ConstraintLayout constraintLayoutItemAboutIntroduction;
        TextView textItemAboutIntroduction, textDescAboutIntroduction, textPercentageAboutIntroduction;
        ImageView imageItemProgressAboutIntroduction, imageItemProgressBgAboutIntroduction;

        public AboutViewHolder(View view) {
            super(view);

            constraintLayoutItemAboutIntroduction = view.findViewById(R.id.constraint_layout_item_about_introduction);
            textItemAboutIntroduction = view.findViewById(R.id.text_about_item_introduction);
            imageItemProgressAboutIntroduction = view.findViewById(R.id.image_progress_about_item_introduction);
            imageItemProgressBgAboutIntroduction = view.findViewById(R.id.image_progress_bg_about_item_introduction);
            textDescAboutIntroduction = view.findViewById(R.id.text_desc_about_item_introduction);
            textPercentageAboutIntroduction = view.findViewById(R.id.text_percentage_about_item_introduction);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == IntroductionMvpView.VIEW_TYPE_PROFILE) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_view_item_profile_introduction, parent, false);
            return new ProfileViewHolder(itemView);
        }
        else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_view_item_about_introduction, parent, false);
            return new AboutViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if(listIntroduction.get(position).getViewType() == IntroductionMvpView.VIEW_TYPE_PROFILE) {
            profileViewHolder = (ProfileViewHolder) holder;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                profileViewHolder.imageItemProfileIntroduction.setTransitionName(context.getString(R.string.transition_name_image_item_profile_introduction, position));
                profileViewHolder.imageBgItemProfileIntroduction.setTransitionName(context.getString(R.string.transition_name_image_bg_item_profile_introduction, position));
            }

            profileViewHolder.imageBgItemProfileIntroduction.setBackgroundColor(Color.WHITE);

            profileViewHolder.iconPhoneItemAboutIntroduction.setOnClickListener(v -> {
                List<String> permissionList = new ArrayList<>();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (context.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        permissionList.add(android.Manifest.permission.CALL_PHONE);
                    }
                    if (context.checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                        permissionList.add(android.Manifest.permission.READ_CONTACTS);
                    }
                    if (context.checkSelfPermission(Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                        permissionList.add(android.Manifest.permission.WRITE_CONTACTS);
                    }
                    if (context.checkSelfPermission(Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                        permissionList.add(android.Manifest.permission.WRITE_CALL_LOG);
                    }
                    if (context.checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                        permissionList.add(android.Manifest.permission.READ_CALL_LOG);
                    }
                    if (permissionList.size() > 0) {
                        ((IntroductionActivity) context).requestPermissions(permissionList.toArray(new String[permissionList.size()]), Constants.CALL_PERMISSION);
                        permissionList.clear();
                    }
                    else {
                        ((IntroductionActivity) context).callMe();
                    }
                }
                else {
                    ((IntroductionActivity) context).callMe();
                }
            });

            profileViewHolder.iconEmailItemAboutIntroduction.setOnClickListener(v -> {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((IntroductionActivity) context).requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.WRITE_EXTERNAL_STORAGE_PERMISSION);
                    }
                }
                else {
                    ((IntroductionActivity) context).checkSignIn();
                }
            });

            if(context instanceof IntroductionActivity) {
                ((IntroductionActivity) context).presenter.onViewPrepared(((IntroductionActivity) context).getIntent().getExtras());
            }
        }
        else if(listIntroduction.get(position).getViewType() == IntroductionMvpView.VIEW_TYPE_ABOUT) {
            aboutViewHolder = (AboutViewHolder) holder;

            aboutViewHolder.textItemAboutIntroduction.setText(listIntroduction.get(position).getText());
            aboutViewHolder.textDescAboutIntroduction.setText(listIntroduction.get(position).getDesc());
            aboutViewHolder.textPercentageAboutIntroduction.setText(((int) (listIntroduction.get(position).getPercentage() * 100f)) + "%");

            ConstraintLayout.LayoutParams layoutParamsImageProgress = (ConstraintLayout.LayoutParams) aboutViewHolder.imageItemProgressAboutIntroduction.getLayoutParams();

            int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels - (int) context.getResources().getDimension(R.dimen.less_largest_padding);
            layoutParamsImageProgress.width = (int) (screenWidth * listIntroduction.get(position).getPercentage());

            aboutViewHolder.imageItemProgressAboutIntroduction.setLayoutParams(layoutParamsImageProgress);
            aboutViewHolder.imageItemProgressAboutIntroduction.setPivotX(0);

            aboutViewHolder.constraintLayoutItemAboutIntroduction.setOnClickListener(v -> {

                int finalPos = 1;
                SpringAnimation springAnim = new SpringAnimation(v.findViewById(R.id.image_progress_about_item_introduction), DynamicAnimation.SCALE_X, finalPos)
                        .setStartVelocity(10);
                springAnim.start();
            });

            aboutViewHolder.constraintLayoutItemAboutIntroduction.setOnTouchListener((v, event) -> {

                TextView textPercentage = v.findViewById(R.id.text_percentage_about_item_introduction);
                switch(event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        textPercentage.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_UP:
                        v.performClick();
                        textPercentage.setVisibility(View.GONE);
                        break;
                    default:
                        if(textPercentage.getVisibility() == View.VISIBLE) {
                            textPercentage.setVisibility(View.GONE);
                        }
                }
                return true;
            });
        }
    }

    @Override
    public int getItemCount() {
        return listIntroduction.size();
    }

    @Override
    public int getItemViewType(int position) {
        return listIntroduction.get(position).getViewType();
    }
}
