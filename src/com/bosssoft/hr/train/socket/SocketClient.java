/*
 * NIO 自码客户端
 * author：张璐思
 * 2020-02-24
 * */

package com.bosssoft.hr.train.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class SocketClient {
    public static void main(String[] args) {
        System.out.println("发送方启动中。。。");
        //1.使用DatagramSocket指定端口创建发送端
        DatagramSocket client;
        try {
            client = new DatagramSocket(8888);
            //2.准备数据，一定要转成字符数组
            String data = "阿妈爱崽子";
            byte[] datas = data.getBytes();
            //3.封装成DatagramPacket包裹，一定要指定目的地
            DatagramPacket packet = new DatagramPacket(datas, 0, datas.length, new InetSocketAddress("localhost",6666));
            try {
                //4.发送包裹
                client.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            client.close();
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }
}
