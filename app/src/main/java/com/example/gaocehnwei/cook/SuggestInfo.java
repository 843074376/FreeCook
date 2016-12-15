package com.example.gaocehnwei.cook;

/**
 * Created by deng on 2016/11/24.
 * ListView 的一项
 * 如: 紫外线指数: 弱....
 */
public class SuggestInfo {
    private String title;
    private String suggest;

    public SuggestInfo(String title, String suggest) {
        this.title = title;
        this.suggest = suggest;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    @Override
    public String toString() {
        return "SuggestInfo: [title=" + title +
                ", suggest=" + suggest +
                "]";
    }
}
