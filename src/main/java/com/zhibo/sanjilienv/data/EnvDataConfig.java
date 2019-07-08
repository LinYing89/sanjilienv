package com.zhibo.sanjilienv.data;

/**
 * 环境数据参数配置
 */
public class EnvDataConfig {
    //系数
    private double coefficient = 1;
    //小数位数
    private String format = "#";
    //单位
    private String unit = "";

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
