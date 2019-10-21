package com.zhibo.sanjilienv.comm;

import com.zhibo.sanjilienv.controller.MainController;
import com.zhibo.sanjilienv.data.Config;
import com.zhibo.sanjilienv.data.Environment;
import com.zhibo.sanjilienv.data.NetMessageAnalysisResult;
import com.zhibo.sanjilienv.data.OneMessageAnalysisResult;
import com.zhibo.sanjilienv.util.SpringUtil;
import com.zhibo.sanjilienv.util.Util;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private MessageAnalysiser messageAnalysiser = new MessageAnalysiser();
    private MainController mainController = SpringUtil.getBean(MainController.class);
    private Config config = SpringUtil.getBean(Config.class);

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

//    public ServerHandler() {
//        messageAnalysiser = new MessageAnalysiser();
////        messageAnalysiser.setAnalysiserResult(result -> {
////
////        });
//    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("new channel " + ctx.channel().id());
        logger.info("new channel " + ctx.channel().id());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf m = (ByteBuf) msg;
        try {
            byte[] req = new byte[m.readableBytes()];
            m.readBytes(req);
            String strMsg = Util.bytesToHexString(req);
            logger.info(strMsg);
            analysisMsg(req);
//            NetMessageAnalysisResult result = messageAnalysiser.analyisis(req);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            m.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        ctx.close();
        logger.info("channel " + ctx.channel().id().asShortText() + " is closed");
    }

    private void analysisMsg(byte[] msg) {
        NetMessageAnalysisResult result = messageAnalysiser.analyisis(msg);
        for (OneMessageAnalysisResult oneMsg : result.getListErrorResult()) {
            if(oneMsg.getCode() != 0){
                logger.error(oneMsg.getData());
                logger.error(oneMsg.getMessage());
                continue;
            }
            //long managerCode = oneMsg.getManagerCode();
            for (byte[] subOrder : oneMsg.getListSubOrder()) {
                // 解析子报文
                // 采集终端编号
                int index = 1;
                int bus = subOrder[0];
                int collectorCode = subOrder[index];
//                if(bus != 1 || collectorCode != 1){
//                    continue;
//                }
                // 数据长度
                index += 3;
                int dataLength = Util.bytesToInt(new byte[]{subOrder[index], subOrder[index + 1]});
                // 数据
                index += 2;
                byte[] byData = Arrays.copyOfRange(subOrder, index, subOrder.length);
                // 36 = 34(数据) + 类型 + 保留
                if (byData.length != dataLength) {
                    continue;
                }
                Client.sendData(Arrays.copyOfRange(byData, 0, byData.length - 2));

                Environment environment = MainController.environment;
                int dataIndex = 0;
                double so2 = Util.bytes2Float(new byte[]{byData[dataIndex], byData[dataIndex + 1], byData[dataIndex + 2], byData[dataIndex + 3]});
                environment.setSo2(so2 * config.getSo2Config().getCoefficient());
                dataIndex += 4;
                double nox = Util.bytes2Float(new byte[]{byData[dataIndex], byData[dataIndex + 1], byData[dataIndex + 2], byData[dataIndex + 3]});
                environment.setNox(nox * config.getNoxConfig().getCoefficient());
                dataIndex += 4;
                double o2 = Util.bytes2Float(new byte[]{byData[dataIndex], byData[dataIndex + 1], byData[dataIndex + 2], byData[dataIndex + 3]});
                environment.setO2(o2 * config.getO2Config().getCoefficient());
                dataIndex += 4;
                double dust = Util.bytes2Float(new byte[]{byData[dataIndex], byData[dataIndex + 1], byData[dataIndex + 2], byData[dataIndex + 3]});
                environment.setDust(dust * config.getDustConfig().getCoefficient());
                dataIndex += 4;
                double dustTem = Util.bytes2Float(new byte[]{byData[dataIndex], byData[dataIndex + 1], byData[dataIndex + 2], byData[dataIndex + 3]});
                environment.setDustTem(dustTem * config.getDustTemConfig().getCoefficient());
                dataIndex += 4;
                double pressure = Util.bytes2Float(new byte[]{byData[dataIndex], byData[dataIndex + 1], byData[dataIndex + 2], byData[dataIndex + 3]});
                environment.setPressure(pressure * config.getPressureConfig().getCoefficient());
                dataIndex += 4;
                double flow = Util.bytes2Float(new byte[]{byData[dataIndex], byData[dataIndex + 1], byData[dataIndex + 2], byData[dataIndex + 3]});
                environment.setFlow(flow * config.getFlowConfig().getCoefficient());
                dataIndex += 4;
                double speed = Util.bytes2Float(new byte[]{byData[dataIndex], byData[dataIndex + 1], byData[dataIndex + 2], byData[dataIndex + 3]});
                environment.setSpeed(speed * config.getSpeedConfig().getCoefficient());
                dataIndex += 4;
                double hum = Util.bytes2Float(new byte[]{byData[dataIndex], byData[dataIndex + 1], byData[dataIndex + 2], byData[dataIndex + 3]});
                environment.setHum(hum * config.getHumConfig().getCoefficient());
                dataIndex += 4;
                double hci = Util.bytes2Float(new byte[]{byData[dataIndex], byData[dataIndex + 1], byData[dataIndex + 2], byData[dataIndex + 3]});
                environment.setHci(hci * config.getHciConfig().getCoefficient());
                dataIndex += 4;
                double co = Util.bytes2Float(new byte[]{byData[dataIndex], byData[dataIndex + 1], byData[dataIndex + 2], byData[dataIndex + 3]});
                environment.setCo(co * config.getCoConfig().getCoefficient());
                dataIndex += 4;
                double co2 = Util.bytes2Float(new byte[]{byData[dataIndex], byData[dataIndex + 1], byData[dataIndex + 2], byData[dataIndex + 3]});
                environment.setCo2(co2 * config.getCo2Config().getCoefficient());
                dataIndex += 4;
                double ovenTem = Util.bytes2Float(new byte[]{byData[dataIndex], byData[dataIndex + 1], byData[dataIndex + 2], byData[dataIndex + 3]});
                environment.setOvenTem(ovenTem * config.getOvenTemConfig().getCoefficient());
                mainController.refresh();
            }
        }
    }
}
