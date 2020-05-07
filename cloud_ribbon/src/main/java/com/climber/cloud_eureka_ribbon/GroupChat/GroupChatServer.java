package com.climber.cloud_eureka_ribbon.GroupChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class GroupChatServer {

    //定义相关的属性:
    private static Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int  PORT=6667;


    //构造器
    //初始化工作
    public GroupChatServer(){

        try {
            //得到选择器:
            selector = Selector.open();
            //渠道
            listenChannel = ServerSocketChannel.open();
            //绑定端口号
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置为非阻塞
            listenChannel.configureBlocking(false);
            //渠道注册得到选择器上
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void listen(Selector selector){
        try {
            //循环处理
            while(true){
                int count = selector.select();
                if(count>0){
                    //有事件处理
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while(iterator.hasNext()){
                        //取出selectionkey
                        SelectionKey key = iterator.next();
                        if(key.isAcceptable()){
                            //监听accept
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            //将SC注册到selector
                            sc.register(selector,SelectionKey.OP_READ);
                            //提示
                            System.out.println(sc.getRemoteAddress()+":上线");
                        } else if(key.isReadable()){
                            //通道可读状态:
                            readData(key);
                        }
                        //当前的可以要删除,防止重复操作.
                        iterator.remove();
                    }

                }else{
                    System.out.println("暂时无事件发生");
                }
            }


        }catch (IOException e) {

            e.printStackTrace();
        }
    }



    public void readData(SelectionKey key){
        SocketChannel channel=null;
        try{
            channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            //根据count的值做处理
            if(count>0){
                //有数据要处理
               String msg = new String( buffer.array());
               System.out.println("from 客户端:"+msg);
               //向别的客户端发送信息:
                sendInfoToOtherCliens(msg,channel);


            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress()+"离线了");

                //取消注册:
                key.channel();
                //关闭通道:
                channel.close();;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }


    private void sendInfoToOtherCliens(String msg,SocketChannel self){
        System.out.println("服务器转发消息中");
        //遍历所有注册到Select
        for(SelectionKey key:selector.keys()){
            //通过key,取出对应的socketChannel
            Channel targetChannel = key.channel();
            if(targetChannel instanceof SocketChannel && targetChannel !=self){
                //转型
                SocketChannel channel = (SocketChannel) targetChannel;
                //将msg 存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                try {
                    channel.write(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    public static void main(String[] args) {
        GroupChatServer groupChatServer= new GroupChatServer();
        System.out.println("开始监听客户端:");
        groupChatServer.listen(selector);
    }


}
