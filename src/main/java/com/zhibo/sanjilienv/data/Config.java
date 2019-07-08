package com.zhibo.sanjilienv.data;

/**
 * 配置
 */

public class Config {

    //人员统计界面显示时间
    private int personnelShowTime = 15;
    //环境数据界面显示时间
    private int environmentShowTime = 15;
    //app隐藏时间
    private int appHideTime = 15;

    private String databaseServerName = "192.168.4.49";

    private int serverPort = 6006;

    private EnvDataConfig so2Config;
    private EnvDataConfig noxConfig;
    private EnvDataConfig o2Config;
    private EnvDataConfig dustConfig;
    private EnvDataConfig dustTemConfig;
    private EnvDataConfig pressureConfig;
    private EnvDataConfig flowConfig;
    private EnvDataConfig speedConfig;
    private EnvDataConfig humConfig;
    private EnvDataConfig hciConfig;
    private EnvDataConfig coConfig;
    private EnvDataConfig co2Config;
    private EnvDataConfig ovenTemConfig;

    public int getPersonnelShowTime() {
        return personnelShowTime;
    }

    public void setPersonnelShowTime(int personnelShowTime) {
        this.personnelShowTime = personnelShowTime;
    }

    public int getEnvironmentShowTime() {
        return environmentShowTime;
    }

    public void setEnvironmentShowTime(int environmentShowTime) {
        this.environmentShowTime = environmentShowTime;
    }

    public int getAppHideTime() {
        return appHideTime;
    }

    public void setAppHideTime(int appHideTime) {
        this.appHideTime = appHideTime;
    }

    public String getDatabaseServerName() {
        return databaseServerName;
    }

    public void setDatabaseServerName(String databaseServerName) {
        this.databaseServerName = databaseServerName;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public EnvDataConfig getSo2Config() {
        return so2Config;
    }

    public void setSo2Config(EnvDataConfig so2Config) {
        this.so2Config = so2Config;
    }

    public EnvDataConfig getNoxConfig() {
        return noxConfig;
    }

    public void setNoxConfig(EnvDataConfig noxConfig) {
        this.noxConfig = noxConfig;
    }

    public EnvDataConfig getO2Config() {
        return o2Config;
    }

    public void setO2Config(EnvDataConfig o2Config) {
        this.o2Config = o2Config;
    }

    public EnvDataConfig getDustConfig() {
        return dustConfig;
    }

    public void setDustConfig(EnvDataConfig dustConfig) {
        this.dustConfig = dustConfig;
    }

    public EnvDataConfig getDustTemConfig() {
        return dustTemConfig;
    }

    public void setDustTemConfig(EnvDataConfig dustTemConfig) {
        this.dustTemConfig = dustTemConfig;
    }

    public EnvDataConfig getPressureConfig() {
        return pressureConfig;
    }

    public void setPressureConfig(EnvDataConfig pressureConfig) {
        this.pressureConfig = pressureConfig;
    }

    public EnvDataConfig getFlowConfig() {
        return flowConfig;
    }

    public void setFlowConfig(EnvDataConfig flowConfig) {
        this.flowConfig = flowConfig;
    }

    public EnvDataConfig getSpeedConfig() {
        return speedConfig;
    }

    public void setSpeedConfig(EnvDataConfig speedConfig) {
        this.speedConfig = speedConfig;
    }

    public EnvDataConfig getHumConfig() {
        return humConfig;
    }

    public void setHumConfig(EnvDataConfig humConfig) {
        this.humConfig = humConfig;
    }

    public EnvDataConfig getHciConfig() {
        return hciConfig;
    }

    public void setHciConfig(EnvDataConfig hciConfig) {
        this.hciConfig = hciConfig;
    }

    public EnvDataConfig getCoConfig() {
        return coConfig;
    }

    public void setCoConfig(EnvDataConfig coConfig) {
        this.coConfig = coConfig;
    }

    public EnvDataConfig getCo2Config() {
        return co2Config;
    }

    public void setCo2Config(EnvDataConfig co2Config) {
        this.co2Config = co2Config;
    }

    public EnvDataConfig getOvenTemConfig() {
        return ovenTemConfig;
    }

    public void setOvenTemConfig(EnvDataConfig ovenTemConfig) {
        this.ovenTemConfig = ovenTemConfig;
    }
}
