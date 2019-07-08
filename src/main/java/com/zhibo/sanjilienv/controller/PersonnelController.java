package com.zhibo.sanjilienv.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

@FXMLController
public class PersonnelController {

    @Autowired
    private MainController mainController;
    @FXML
    private Label labelTime;
    @FXML
    private Label labelData;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("'现在时间是'：yyyy年MM月dd日HH时mm分ss秒");

    public void refresh(String data){

        Platform.runLater(() -> {
            if(null != labelTime && null != labelData) {
                labelTime.setText(dateFormat.format(new Date()));
                labelData.setText(data);
            }
        });
        mainController.refreshTime();
    }
}
