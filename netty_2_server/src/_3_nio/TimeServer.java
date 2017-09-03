package _3_nio;

/**
 * Created by zhumeilu on 17/9/3.
 */
public class TimeServer {

    public static void main(String[] args) {

        int port = 8080;
        if(args!=null&&args.length>0){
            try{
                port = Integer.valueOf(args[0]);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new  Thread(timeServer,"NIO-MultiplexerTimeServer-001").start();
    }
}
