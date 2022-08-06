package severservice;


import entity.Message;
import entity.MsgType;
import entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class ServerConnect {
    ServerSocket serverSocket;
    boolean flag =false;
    public Message login(){
        try {
            serverSocket= new ServerSocket(10000);
            System.out.println("服务器在10000端口监听");
            new Thread(new SendToAll()).start();
            while (true){
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(inputStream);
                User user = null;
                try {
                    user = (User)ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println(user);
                //将客户端的登录信息和数据库进行比对，返回结果
                HashMap hashMap = DBImitate.hashMap;
                Set<String> set = hashMap.keySet();
                for (String s : set) {
                    if(s.equals(user.getUserId())){
                        String val =(String) hashMap.get(s);
                        if(val.equals(user.getPwd())){
                            //比对成功，将成功结果返回
                            write(socket,new Message("","", MsgType.LOGIN,"登录成功"));
                            //将此用户的离线消息发送出去
                            Hashtable<String, List<Message>> offLineMap = OffLineMessage.getOffLineMap();
                            Set<String> offlineids = offLineMap.keySet();
                            for (String offlineid : offlineids) {
                                if(user.getUserId().equals(offlineid)){
                                    for (Message message : offLineMap.get(offlineid)) {
                                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                                        oos.writeObject(message);
                                    }
                                    offLineMap.remove(offlineid);
                                }
                            }

                            //将登录成功的用户对应的socked创建线程，将线程加入到集合中
                            ServerThread serverThread = new ServerThread(socket, user.getUserId());
                            new Thread(serverThread).start();
                            SeverThreadPool.addThread(user.getUserId(),serverThread);
                            //循环遍历集合，匹配到后将flag设为真
                            flag=true;
                        }
                    }
                }
                //循环结束，flag为false。说明集合中没有匹配到，登录失败
                if(flag=false){
                    write(socket,new Message("","", MsgType.LOGIN,"登录失败"));
                    socket.close();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new Message();
    }

    public void write(Socket socket,Object object){

        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
