package _3_nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zhumeilu on 17/9/3.
 */
public class MultiplexerTimeServer implements Runnable{

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean stop;
    public MultiplexerTimeServer(int port){
        try{
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务器启动-端口："+port);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }
    public void stop(){
        this.stop = true;
    }
    @Override
    public void run() {

        while (!stop){
            try{
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()){
                    key = it.next();
                    it.remove();
                    try{
                        handleInput(key);
                    }catch (Exception e){
                        if(key!=null){
                            key.cancel();
                            if(key.channel()!=null)
                                key.channel().close();
                        }
                    }
                }
            }catch (Throwable t){
                t.printStackTrace();
            }

        }
        //多路复用器关闭之后，所有注册在上面的Chanel和Pipe等资源都会被自动去注册并关闭，所以不需要重复释放资源
        if(selector!=null){
            try{
                selector.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    private void handleInput(SelectionKey key) throws IOException{
        if(key.isValid()){
            //处理新接入的请求消息
            if(key.isAcceptable()){
                //接入一个新的连接
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                //添加一个新连接到selector
                socketChannel.register(selector,SelectionKey.OP_READ);
            }
            if(key.isReadable()){
                //读取数据
                SocketChannel socketChannel = (SocketChannel) key.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int read = socketChannel.read(byteBuffer);
                if(read>0){
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    String body = new String (bytes,"utf-8");
                    System.out.println("服务器收到数据："+body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)? new Date(System.currentTimeMillis()).toString():"BAD ORDER";
                    doWrite(socketChannel,currentTime);
                }else if(read<0){
                    //对端连读关闭
                    key.cancel();
                    socketChannel.close();
                }else{
                    //读取到0字节
                }


            }
        }
    }

    private void doWrite(SocketChannel socketChannel, String response) throws IOException{

        if(response!=null&&response.trim().length()>0){
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            socketChannel.write(writeBuffer);
        }
    }
}
