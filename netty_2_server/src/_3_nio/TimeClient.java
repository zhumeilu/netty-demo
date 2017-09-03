package _3_nio;


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
        new Thread(new TimeClientHandle("127.0.0.1",port),"TimeClient-001").start();

    }
}
