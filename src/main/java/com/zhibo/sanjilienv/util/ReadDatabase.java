package com.zhibo.sanjilienv.util;

import com.zhibo.sanjilienv.SanjilienvApplication;
import com.zhibo.sanjilienv.data.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ReadDatabase extends Thread{

    private static Logger logger = LoggerFactory.getLogger(ReadDatabase.class);

    //	private final String serverName = "DESKTOP-B8UPRNV\\SQLEXPRESS";
//	private final String serverName = "192.168.1.100";
    private final String databaseName = "onecard";
    private final String tableName = "AreaListOfEmpl";

    private String URL;
    private static final String USER = "sa";
    private static final String PASSWORD = "1234";

    public static Map<Integer, Integer> mapAreaPeople = new HashMap<>();
    public static Map<Integer, Integer> mapPeopleDept = new HashMap<>();

    private Config config = SpringUtil.getBean(Config.class);

    public void connectDb() {
        URL = "jdbc:sqlserver://" + config.getDatabaseServerName() + ";DatabaseName=" + databaseName;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("connectDb: " + e.getMessage());
        }
    }

    public void queryPersopnnel() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select PersonnelID, DepartmentID from Personnel");
            while (rs.next()) {
                int PersonnelID = rs.getInt("PersonnelID");
                int deptId = rs.getInt("DepartmentID");
                mapPeopleDept.put(PersonnelID, deptId);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("queryPersopnnel: " + e.getMessage());
        } finally {
            try {
                if (null != stmt) {
                    stmt.close();
                }
                if (null != conn) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private int findDeptId(int personId) {
        return mapPeopleDept.get(personId);
    }

    public void query() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select PersonnelID from " + tableName + " where IsEntry = 1");
            while (rs.next()) {
                int PersonnelID = rs.getInt("PersonnelID");
//				int isEntry = rs.getInt("IsEntry");

                int deptId = findDeptId(PersonnelID);
                Integer mapAreaValue = mapAreaPeople.get(deptId);
                if (mapAreaValue == null) {
                    mapAreaPeople.put(deptId, 0);
                    mapAreaValue = 0;
                }
                mapAreaValue++;
                mapAreaPeople.put(deptId, mapAreaValue);
            }
            writeToFile();
            mapAreaPeople.clear();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("query: " + e.getMessage());
        } finally {
            try {
                if (null != conn) {
                    stmt.close();
                }
                if (null != conn) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private int getValue(int deptId) {
        Integer value = mapAreaPeople.get(deptId);
        if (value == null) {
            return 0;
        }
        return value;
    }

    private void writeToFile() {
        int all = 0;
        int deptCount = 0;
        int other = 0;
        for (Integer i : mapAreaPeople.keySet()) {
            int val = getValue(i);
            all += val;
            if (i == 13 || i == 26 || i == 14 || i == 24 || i == 22 || i == 27 || i == 32 || i == 16 || i == 15
                    || i == 29) {
                deptCount += val;
            }
        }
        other = all - deptCount;

        String str1 = String.format("进入厂区人员: %s人\r\n", all);
        String str2 = String.format("反应: %s   \t  \t\t电仪: %s\r\n", getValue(13), getValue(26));
        String str3 = String.format("分离: %s   \t  \t\t仓库: %s\r\n", getValue(14), getValue(24));
        String str4 = String.format("动力: %s   \t  \t\t管理: %s\r\n", getValue(22), getValue(27));
        String str5 = String.format("外协: %s   \t  \t\t综合: %s\r\n", getValue(32) + other, getValue(16));
        String str6 = String.format("机修: %s   \t  \t\t来宾: %s\r\n", getValue(15), getValue(29));
//		String str7 = String.format("             其他: %s", other);
        OutputStreamWriter isr = null;
        BufferedWriter br = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(str1);
            sb.append(str2);
            sb.append(str3);
            sb.append(str4);
            sb.append(str5);
            sb.append(str6);

            isr = new OutputStreamWriter(new FileOutputStream(".\\realtimeInfo.txt"), "GBK"); // 或GB2312,GB18030
            br = new BufferedWriter(isr); // 用FileReader为参数创建一个缓冲输入流
            br.write(sb.toString());
            br.flush();
            logger.info("write to file success: " + sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("writeToFile: " + e.getMessage());
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
    }

    @Override
    public void run() {
        connectDb();
        queryPersopnnel();
        while (SanjilienvApplication.RUNNING) {
            try {
                query();
                Thread.sleep(1000);
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
