package com.zhibo.sanjilienv.util;

import com.zhibo.sanjilienv.controller.PersonnelController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class PersonnelCountHelper {

    private static PersonnelCountHelper ins = new PersonnelCountHelper();

    private static Logger logger = LoggerFactory.getLogger(PersonnelCountHelper.class);
    private static boolean RUNNING = false;

    private PersonnelController personnelController = SpringUtil.getBean(PersonnelController.class);

    private PersonnelCountHelper(){}

    public static PersonnelCountHelper getIns(){
        return ins;
    }

    public void start() {
        if (!RUNNING) {
            RUNNING = true;
            ReadPersonnelCountThread thread = new ReadPersonnelCountThread();
            thread.start();
        }
    }

    public void stop() {
        RUNNING = false;
    }

    private String readinfo() {
        String record;
        BufferedReader br = null;
        InputStreamReader isr = null;
        StringBuilder info = null;
        try {
            info = new StringBuilder();
//			fr = new FileReader(".\\realtimeInfo.txt"); // 读取人员信息文件
            isr = new InputStreamReader(new FileInputStream("realtimeInfo.txt"), "GBK"); // 或GB2312,GB18030
            br = new BufferedReader(isr); // 用FileReader为参数创建一个缓冲输入流
            while ((record = br.readLine()) != null) {
                info.append("\n").append(record); // 按行读取、显示
            }
            logger.info("readinfo(): read text success");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("读取人员文件错误: " + e.getMessage());
        } finally {
            try {
                if (null != br) {
                    br.close();
                }
                if (null != isr) {
                    isr.close();
                }
            } catch (IOException e) {
                logger.error("文件关闭错误: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return info.toString();
    }

    class ReadPersonnelCountThread extends Thread {
        @Override
        public void run() {
            try {
                while (RUNNING) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        logger.error("刷新界面睡眠时间打断: " + e.getMessage());
                    }
                    String info = readinfo();
                    personnelController.refresh(info);
                    //logger.info("show info: " + info);
                }
            } catch (Exception e) {
                logger.error("刷新界面线程异常结束: " + e.getMessage());
            }
        }
    }
}
