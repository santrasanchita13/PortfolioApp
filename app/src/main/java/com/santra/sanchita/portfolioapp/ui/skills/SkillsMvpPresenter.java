package com.santra.sanchita.portfolioapp.ui.skills;

import android.os.Bundle;

import com.santra.sanchita.portfolioapp.ui.base.MvpPresenter;

/**
 * Created by sanchita on 2/1/18.
 */

public interface SkillsMvpPresenter<V extends SkillsMvpView> extends MvpPresenter<V> {
    void onViewAttached(Bundle extras);
}
