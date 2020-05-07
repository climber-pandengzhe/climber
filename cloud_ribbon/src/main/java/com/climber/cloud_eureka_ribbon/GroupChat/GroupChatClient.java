package com.climber.cloud_eureka_ribbon.GroupChat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class GroupChatClient {
    //定义相关属性
    private final  String HOST ="127.0.0.1";
    private final  int PORT =6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String userName;

    public GroupChatClient() throws IOException{
        selector=Selector.open();

        socketChannel =SocketChannel.open(new InetSocketAddress(HOST,PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        userName= socketChannel.getLocalAddress().toString();

        System.out.println(userName+ "is ok...");

    }
    //向服务器发送信息
    public void sendInfo(String info){
        info=userName+"说:"+info;

        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
m

    //读取服务器信息
    public void readInfo(){


        try {
            int readChannels= selector.select(10000);
            if(readChannels>0){//有可用通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while(iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    if(selectionKey.isReadable()){
                        //得到相关的通道
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        //读取
                        channel.read(buffer);
                        //把读到缓冲区的数据转成字符串.
                        String msg= new String(buffer.array());
                        System.out.println(msg.trim());


                    }
                    iterator.remove();
                }

            }else {
                System.out.println("没有可用的通道..");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws IOException {
        //启动客户端:
        GroupChatClient groupChatClient = new GroupChatClient();

        //启动一个线程,每隔三秒读取服务器端可能发送的数据.
        new Thread(){
            @Override
            public void run(){
                while(true){
                    groupChatClient.readInfo();
                    try {
                        Thread.sleep(2000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        System.out.println("要发送给别人的信息:");
        // 从键盘接收数据
        Scanner scan = new Scanner(System.in);
        while(true){

            String str1 = scan.next();
            // 判断是否还有输入
            if (str1!=null) {
                groupChatClient.sendInfo(str1);

            }else{
                break;
            }

        }
        scan.close();




    }
}
