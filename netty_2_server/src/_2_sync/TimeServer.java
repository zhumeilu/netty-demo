package _2_sync;

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
            TimeServerHandlerExecutePool  singleExecutor = new TimeServerHandlerExecutePool(50,10000);//创建IO任务线程池
            while (true){
                socket = serverSocket.accept();
                singleExecutor.execute(new TimeServerHandler(socket));

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
