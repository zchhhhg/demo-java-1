/*
 * NIO 自码服务器端
 * author：张璐思
 * 2020-02-24
 * */

package com.bosssoft.hr.train.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class SocketServer {
    public static void main(String[] args) {
        System.out.println("接收方启动中。。。");
        try {
            //1.使用DatagramSocket指定端口创建接收端
            DatagramSocket server = new DatagramSocket(6666);
            //2.准备容器，封装成DatagramPacket包裹
            byte[] container = new byte[1024*60];
            DatagramPacket packet = new DatagramPacket(container, 0, container.length, new InetSocketAddress("localhost", 9999));
            try {
                //3.阻塞式接收包裹
                server.receive(packet);

            } catch (IOException e) {
                e.printStackTrace();
            }
            //4.分析数据
            byte[] datas = packet.getData();
            System.out.println(new String(datas,0,datas.length));
            server.close();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
