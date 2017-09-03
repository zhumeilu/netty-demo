package _1_tradition;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by zhumeilu on 17/9/3.
 */
public class TimeClient {

    public static void main(String[] args) {

        int port = 8081;
        if(args!=null&&args.length>0){
            try{
                port = Integer.valueOf(args[0]);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out  = null;
        try{
            socket = new Socket("127.0.0.1",port);
            in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
            out.println("QUERY TIME ORDER");
            System.out.println("发送命令成功");
            String resp = in.readLine();
            System.out.println("收到的消息："+resp);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(out!=null){
                out.close();
                out = null;
            }
            if(in!= null){
                try{
                    in.close();
                }catch (Exception e2){
                    e2.printStackTrace();
                }
                in = null;
            }
            if(socket!= null){
                try{
                    socket.close();
                }catch (Exception e2){
                    e2.printStackTrace();
                }
                socket = null;
            }
        }
    }
}
