package com.santra.sanchita.portfolioapp.ui.skills;

import com.santra.sanchita.portfolioapp.data.db.model.DesignItem;
import com.santra.sanchita.portfolioapp.ui.base.MvpView;

/**
 * Created by sanchita on 2/1/18.
 */

public interface SkillsMvpView extends MvpView {
    void updateDetails(DesignItem designItem);
}
