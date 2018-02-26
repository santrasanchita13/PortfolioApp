package com.santra.sanchita.portfolioapp.ui.design_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.santra.sanchita.portfolioapp.R;
import com.santra.sanchita.portfolioapp.di.component.ActivityComponent;
import com.santra.sanchita.portfolioapp.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sanchita on 13/12/17.
 */

public class DesignListFragment extends BaseFragment implements DesignListMvpView {

    @Inject
    DesignListMvpPresenter<DesignListMvpView> presenter;

    @BindView(R.id.view_pager_design_list)
    ViewPager viewPagerDesignList;

    @BindView(R.id.text_design_list)
    TextView textDesignList;

    @BindView(R.id.text_info_main)
    TextView textInfoMain;

    @Inject
    DesignListPagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_design_list, container, false);

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

        presenter.onViewPrepared();

        textInfoMain.setOnClickListener(v -> showPopUpExplanation(getString(R.string.main_activity_info), Gravity.BOTTOM));
    }

    @Override
    public void updateDetails(Long count) {

        pagerAdapter.setCount(count.intValue());

        textDesignList.setText(1 + "/" + count.intValue());

        viewPagerDesignList.setPageTransformer(false,
                new ZoomFadeTransformer(viewPagerDesignList.getPaddingLeft(),
                        0.9f,
                        0.5f));

        viewPagerDesignList.setAdapter(pagerAdapter);

        viewPagerDesignList.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                pagerAdapter.getCurrentFragment().changeToCardMode();
            }

            @Override
            public void onPageSelected(int position) {
                textDesignList.setText(position + 1 + "/" + count.intValue());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }
}
