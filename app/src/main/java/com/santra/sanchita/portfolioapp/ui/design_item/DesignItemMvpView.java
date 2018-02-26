package com.santra.sanchita.portfolioapp.ui.design_item;

import com.santra.sanchita.portfolioapp.data.db.model.DesignItem;
import com.santra.sanchita.portfolioapp.ui.base.MvpView;

/**
 * Created by sanchita on 13/12/17.
 */

public interface DesignItemMvpView extends MvpView {
    void changeToPreviewMode();

    void changeToCardMode();

    void updateDetails(DesignItem designItem);

    void goToFormActivity(DesignItem designItem);

    void goToCustomViewActivity(DesignItem designItem);

    void goToIntroductionActivity(DesignItem designItem);

    void goToSkillsActivity(DesignItem designItem);

    void goToContactActivity(DesignItem designItem);
}
