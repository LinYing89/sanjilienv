package com.zhibo.sanjilienv.controller;

import com.zhibo.sanjilienv.data.Config;
import com.zhibo.sanjilienv.data.Environment;
import com.zhibo.sanjilienv.util.Util;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

@FXMLController
public class MainController {

    public static Environment environment;

    @Autowired
    private Config config;

    @FXML
    private Label labelTime;

    //SO2二氧化硫
    @FXML
    private Label labelSO2;
    //NOx
    @FXML
    private Label labelNOx;
    //O2
    @FXML
    private Label labelO2;
    //烟尘
    @FXML
    private Label labelDust;
    //烟温
    @FXML
    private Label labelDustTem;
    //静压
    @FXML
    private Label labelPressure;
    //流量
    @FXML
    private Label labelFlow;
    //流速
    @FXML
    private Label labelSpeed;
    //湿度
    @FXML
    private Label labelHum;
    //HCl
    @FXML
    private Label labelHCI;
    //CO
    @FXML
    private Label labelCO;
    //CO2
    @FXML
    private Label labelCO2;
    //炉温
    @FXML
    private Label labelOvenTem;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("'现在时间是'：yyyy年MM月dd日HH时mm分ss秒");

    public MainController(){
        environment = new Environment();
    }

    void refreshTime(){
        Platform.runLater(() ->{
            if(null != labelTime){
                labelTime.setText(dateFormat.format(new Date()));
            }
        });
    }

    public void refresh(){
        Platform.runLater(() ->{
            if(null == labelSO2){
                return;
            }
            labelSO2.setText(Util.formatValue(config.getSo2Config().getFormat(), environment.getSo2()) + config.getSo2Config().getUnit());
            labelNOx.setText(Util.formatValue(config.getNoxConfig().getFormat(), environment.getNox()) + config.getNoxConfig().getUnit());
            labelO2.setText(Util.formatValue(config.getO2Config().getFormat(), environment.getO2()) + config.getO2Config().getUnit());
            labelDust.setText(Util.formatValue(config.getDustConfig().getFormat(), environment.getDust()) + config.getDustConfig().getUnit());
            labelDustTem.setText(Util.formatValue(config.getDustTemConfig().getFormat(), environment.getDustTem()) + config.getDustTemConfig().getUnit());
            labelPressure.setText(Util.formatValue(config.getPressureConfig().getFormat(), environment.getPressure()) + config.getPressureConfig().getUnit());
            labelFlow.setText(Util.formatValue(config.getFlowConfig().getFormat(), environment.getFlow()) + config.getFlowConfig().getUnit());
            labelSpeed.setText(Util.formatValue(config.getSpeedConfig().getFormat(), environment.getSpeed()) + config.getSpeedConfig().getUnit());
            labelHum.setText(Util.formatValue(config.getHumConfig().getFormat(), environment.getHum()) + config.getHumConfig().getUnit());
            labelHCI.setText(Util.formatValue(config.getHciConfig().getFormat(), environment.getHci()) + config.getHciConfig().getUnit());
            labelCO.setText(Util.formatValue(config.getCoConfig().getFormat(), environment.getCo()) + config.getCoConfig().getUnit());
            labelCO2.setText(Util.formatValue(config.getCo2Config().getFormat(), environment.getCo2()) + config.getCo2Config().getUnit());
            labelOvenTem.setText(Util.formatValue(config.getOvenTemConfig().getFormat(), environment.getOvenTem()) + config.getOvenTemConfig().getUnit());
        });
    }
}
