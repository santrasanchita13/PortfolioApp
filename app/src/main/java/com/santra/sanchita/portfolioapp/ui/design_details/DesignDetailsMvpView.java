package com.santra.sanchita.portfolioapp.ui.design_details;

import com.santra.sanchita.portfolioapp.data.db.model.DesignItem;
import com.santra.sanchita.portfolioapp.ui.base.MvpView;

/**
 * Created by sanchita on 15/12/17.
 */

public interface DesignDetailsMvpView extends MvpView {
    void animateEnterTransitions();

    void animateExitTransitions();

    void shiftLeft();

    void shiftRight();

    void updateDetails(DesignItem designItem);
}
