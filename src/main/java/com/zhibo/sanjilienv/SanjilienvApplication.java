package com.zhibo.sanjilienv;

import com.zhibo.sanjilienv.view.PersonnelCount;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.event.EventType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations= {"file:./config.xml"})
public class SanjilienvApplication extends AbstractJavaFxApplicationSupport {

    private static Logger logger = LoggerFactory.getLogger(SanjilienvApplication.class);

    public static boolean RUNNING = true;

    public static void main(String[] args) {
//		SpringApplication.run(SanjilienvApplication.class, args);
		launch(SanjilienvApplication.class, PersonnelCount.class, new CustomSplash(), args);

        //未捕获的异常, 统一捕获
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> logger.error(e.getMessage()));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        super.start(primaryStage);
        primaryStage.setOnCloseRequest(this::handle);
        primaryStage.setResizable(false);
        primaryStage.setX(0);
        primaryStage.setY(0);
    }

    private void handle(WindowEvent e) {
        EventType<WindowEvent> type = e.getEventType();
        if (type == WindowEvent.WINDOW_CLOSE_REQUEST) {
            System.exit(0);
        }
    }
}
