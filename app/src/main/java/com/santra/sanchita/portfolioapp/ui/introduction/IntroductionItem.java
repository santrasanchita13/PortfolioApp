package com.santra.sanchita.portfolioapp.ui.introduction;

/**
 * Created by sanchita on 25/12/17.
 */

public class IntroductionItem {

    private int viewType;
    private String text;
    private float percentage;
    private String desc;

    public IntroductionItem(int viewType, String text) {
        this.viewType = viewType;
        this.text = text;
    }

    public IntroductionItem(int viewType, String text, float percentage, String desc) {
        this.viewType = viewType;
        this.text = text;
        this.percentage = percentage;
        this.desc = desc;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
