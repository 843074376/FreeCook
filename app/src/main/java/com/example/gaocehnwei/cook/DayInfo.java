package com.example.gaocehnwei.cook;

/**
 * Created by deng on 2016/11/24.
 * recyclerList 中一项的对象
 */
public class DayInfo {
    private String day;
    private String weather;
    private String tempRange;

    public DayInfo(String day, String weather, String tempRange) {
        this.day = day;
        this.weather = weather;
        this.tempRange = tempRange;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTempRange() {
        return tempRange;
    }

    public void setTempRange(String tempRange) {
        this.tempRange = tempRange;
    }

    @Override
    public String toString() {
        return "SuggestInfo: [day=" + day +
                ", weather=" + weather +
                ", tempRange=" + tempRange +
                "]";
    }
}
