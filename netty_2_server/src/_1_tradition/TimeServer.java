package _1_tradition;
import _1_tradition.TimeServerHandler;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by zhumeilu on 17/9/3.
 */
public class TimeServer {

    public static void main(String[] args) {

        int port = 8081;
        if(args!=null&&args.length>0){
            try{
                port = Integer.valueOf(args[0]);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("服务器已经启动--端口号："+port);
            Socket socket = null;
            while (true){
                socket = serverSocket.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(serverSocket!=null){
                System.out.println("服务器关闭");
                try{
                    serverSocket.close();
                }catch (Exception e1){
                    e1.printStackTrace();
                }
                serverSocket = null;
            }
        }
    }
}
