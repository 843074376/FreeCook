package com.example.gaocehnwei.cook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deng on 2016/11/24.
 */
public class WeatherInfo {
    private String city;
    private String time;
    private String temperature;
    private String wind;
    private String humidity;
    private String tempRange;
    private String aqi;
    private List<SuggestInfo> suggestList;
    private List<DayInfo> dayInfoList;

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getTempRange() {
        return tempRange;
    }

    public void setTempRange(String tempRange) {
        this.tempRange = tempRange;
    }

    public WeatherInfo(List<String> list) {
        suggestList = new ArrayList<SuggestInfo>();
        dayInfoList = new ArrayList<DayInfo>();

        city = list.get(1);
        time = list.get(3).substring(10);

        // 今日天气实况：气温：3℃；风向/风力：西北风 1级；湿度：90%
        String[] todayWeather = list.get(4).split("；|：");
        temperature = todayWeather[2];
        wind = todayWeather[4];
        humidity = todayWeather[5] + ": " + todayWeather[6];

        // 紫外线强度：最弱。空气质量：良。
        String[] airQuality = list.get(5).split("。");
        aqi = airQuality[1]; // 空气质量：良

        // 紫外线指数：最弱，辐射弱，涂擦SPF8-12防晒护肤品。
        // 感冒指数：极易发，强降温，天气寒冷，极易发生感冒。穿衣指数：冷，建议着棉衣加羊毛衫等冬季服装。
        // 洗车指数：不宜，有雨，雨水和泥水会弄脏爱车。
        // 运动指数：较不宜，有降水，推荐您在室内进行休闲运动。
        // 空气污染指数：良，气象条件有利于空气污染物扩散。
        String[] suggestion = list.get(6).split("。|：");
        for (int i = 0; i < suggestion.length; i += 2)
            suggestList.add(new SuggestInfo(suggestion[i], suggestion[i + 1]));


        // 11月23日 小雨转阴
        // 5℃/8℃
        int offset = 7;
        for (int i = 0; i < 5; i++) {
            String[] future = list.get(offset + i * 5).split(" "); // 11月23日 小雨转阴
            dayInfoList.add(new DayInfo(future[0], future[1], list.get(offset + i * 5 + 1)));
            if (i == 0) tempRange = list.get(offset + i * 5 + 1);
        }

    }

    public WeatherInfo(String cityName) {
        city = cityName;
        time = temperature = wind = humidity = tempRange = aqi = "没有数据";
        suggestList = new ArrayList<SuggestInfo>();
        dayInfoList = new ArrayList<DayInfo>();
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public List<SuggestInfo> getSuggestList() {
        return suggestList;
    }

    public void setSuggestList(List<SuggestInfo> suggestList) {
        this.suggestList = suggestList;
    }

    public List<DayInfo> getDayInfoList() {
        return dayInfoList;
    }

    public void setDayInfoList(List<DayInfo> dayInfoList) {
        this.dayInfoList = dayInfoList;
    }

    @Override
    public String toString() {
        return "WeatherInfo: [city=" + city +
                ", time=" + time +
                ", temperature=" + temperature +
                ", wind=" + wind +
                ", humidity=" + humidity +
                ", aqi=" + aqi +
                ", List<SuggestInfo>=" + suggestList +
                ", List<DayInfo>=" + dayInfoList +
                "]";
    }
}
