package _1_tradition;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * Created by zhumeilu on 17/9/3.
 */
public class TimeServerHandler implements Runnable {

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }


    private Socket socket;


    @Override
    public void run() {

        BufferedReader in = null;
        PrintWriter out = null;
        try{
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(),true);
            String currentTime = null;
            String body = null;
            while(true){
                body = in.readLine();
                if(body == null){
                    break;
                }
                System.out.println("服务器收到消息："+body);
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)? new Date(System.currentTimeMillis()).toString():"BAD ORDER";
                out.println(currentTime);
            }

        }catch (Exception e){
            e.printStackTrace();
            if(in!=null){
                try{
                    in.close();
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
            if(out!=null){
                out.close();
                out = null;
            }
            if(this.socket!=null){
                try{
                    this.socket.close();
                }catch (Exception e1){
                    e.printStackTrace();
                }
                this.socket = null;
            }
        }

    }
}
