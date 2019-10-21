package com.zhibo.sanjilienv;

import com.zhibo.sanjilienv.comm.Client;
import com.zhibo.sanjilienv.comm.MyServer;
import com.zhibo.sanjilienv.data.Config;
import com.zhibo.sanjilienv.enums.UISceneEnum;
import com.zhibo.sanjilienv.util.PersonnelCountHelper;
import com.zhibo.sanjilienv.util.ReadDatabase;
import com.zhibo.sanjilienv.util.UIThread;
import com.zhibo.sanjilienv.view.Main;
import com.zhibo.sanjilienv.view.PersonnelCount;
import javafx.application.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    private UIThread uiThread = new UIThread();
    private UISceneEnum uiSceneEnum = UISceneEnum.PERSONNEL;

    @Autowired
    private Config config;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 容器启动完成后创建通信机server
        try {
            MyServer.PORT = config.getServerPort();
            MyServer myServer = new MyServer();
            myServer.run();

            new Thread(() -> {
                Client client = new Client();
                client.start();
            }).start();

            ReadDatabase readDatabase = new ReadDatabase();
            readDatabase.start();
            PersonnelCountHelper.getIns().start();

            uiThread.setTime(config.getPersonnelShowTime());
            uiThread.setTimeOverListener(() -> {
                switch (uiSceneEnum) {
                    case PERSONNEL:
                        showEnvironment();
                        break;
                    case ENVIRONMENT:
                        showHide();
                        break;
                    case HIDE:
                        showPersonnel();
                        break;
                    default:
                        break;
                }
            });
            uiThread.start();
            System.out.println(config.getPersonnelShowTime());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void showPersonnel() {
        uiSceneEnum = UISceneEnum.PERSONNEL;
        uiThread.setTime(config.getPersonnelShowTime());
        Platform.runLater(() -> {
            SanjilienvApplication.showView(PersonnelCount.class);
            SanjilienvApplication.getStage().setIconified(false);
        });
    }

    private void showEnvironment() {
        uiSceneEnum = UISceneEnum.ENVIRONMENT;
        uiThread.setTime(config.getEnvironmentShowTime());
        Platform.runLater(() -> SanjilienvApplication.showView(Main.class));
    }

    private void showHide() {
        uiSceneEnum = UISceneEnum.HIDE;
        uiThread.setTime(config.getAppHideTime());
        Platform.runLater(() -> SanjilienvApplication.getStage().setIconified(true));
    }
}
