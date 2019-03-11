package com.training.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by huai23 on 2019/3/11.
 */
public class TCPServer {

    public static void main(String[] args) {
        moreserver(9090);
    }

    //读取输入文本，返回响应文本（二级制，对象）
    public static void  server(int port) {
        try {
            //创建一个serversocket
            ServerSocket mysocket=new ServerSocket(port);
            //等待监听是否有客户端连接
            Socket sk = mysocket.accept();
            //接收文本信息
            BufferedReader in =new BufferedReader(new InputStreamReader(sk.getInputStream()));
            String data =in.readLine();
            System.out.println("客户端消息："+data);

//          //接收二进制字节流
//          DataInputStream dis =new DataInputStream(new BufferedInputStream(sk.getInputStream()));
//          byte[] enter =new byte[dis.available()];
//          dis.read(enter);
//
//          //接收对象信息
//          ObjectInput ois =new ObjectInputStream(new BufferedInputStream(sk.getInputStream()));
//          Object object=ois.readObject();

            //输出文本
            PrintWriter out =new PrintWriter(new BufferedWriter(new OutputStreamWriter(sk.getOutputStream())),true);
            out.println(new Date().toString()+":"+data);   //服务器返回时间和客户发送来的消息
            out.close();

//          //输出二进制
//          DataOutputStream dos=new DataOutputStream(sk.getOutputStream());
//          byte[] back="luanpeng".getBytes();
//          dos.write(back);
//
//          //输出对象
//          ObjectOutputStream oos=new ObjectOutputStream(sk.getOutputStream());
//          oos.writeObject(new Date());  //返回一个时间对象
//          oos.close();

        } catch (Exception e) {
            // TODO: handle exception
        }

    }



    //多线程服务器
    public static void moreserver(int port){
        try {
            System.out.println("服务器启动...............");
            ServerSocket server=new ServerSocket(port);
            while(true)
            {
                //阻塞，直到有客户连接
                Socket sk=server.accept();
                System.out.println("有新客户端接入...............");
                //启动服务线程
                new ServerThread(sk).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}


//使用线程为多个客户端服务
class ServerThread extends Thread
{
    private Socket sk;
    ServerThread(Socket sk)
    {
        this.sk=sk;
    }
    //线程运行实体
    public void run() {
        BufferedReader in=null;
        PrintWriter out=null;
        try {
            InputStreamReader isr;
            isr=new InputStreamReader(sk.getInputStream());
            in=new BufferedReader(isr);
            out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(sk.getOutputStream())),true);
            while(true)
            {
                //接收来自客户端的请求，根据不同的命令返回不同的信息
                String cmd=in.readLine();
                System.out.println(cmd);
                if (cmd==null) {
                    break;
                }
                out.println(new Date().toString()+":"+cmd);   //服务器返回时间和客户发送来的消息
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            try {
                if (in!=null) {
                    in.close();
                }
                if (out!=null) {
                    out.close();
                }
                if (sk!=null) {
                    sk.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
