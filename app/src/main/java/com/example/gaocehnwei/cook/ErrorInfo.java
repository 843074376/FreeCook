package com.example.gaocehnwei.cook;

/**
 * Created by deng on 2016/11/24.
 */
public class ErrorInfo {
    private String errorMessage;

    public ErrorInfo(String str) {
        String[] split = str.split("。");
        switch (split[0]) {
            case "查询结果为空" :
                errorMessage = "当前城市不存在, 请重新输入";
                break;
            case "发现错误：免费用户不能使用高速访问" :
                errorMessage = "您的点击速度过快, 二次查询间隔<600ms";
                break;
            case "发现错误：免费用户24小时内访问超过规定数量" :
                errorMessage = "免费用户24小时内访问超过规定数量50次";
                break;
            default:
                errorMessage = split[0];
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "SuggestInfo: [errorMessage=" + errorMessage + "]";
    }
}
