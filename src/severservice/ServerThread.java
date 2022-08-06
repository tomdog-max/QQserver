package severservice;


import entity.Message;
import entity.MsgType;
import entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

public class ServerThread implements Runnable{
    Socket socket;
    String userId;
    Hashtable<String, ServerThread> ht = SeverThreadPool.hashtable;


    public ServerThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void run() {
        //每个socked对应的线程不断的从自己的socked中读取数据
        while(true){
            try {
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(inputStream);
                Message message=(Message) ois.readObject();
                System.out.println(message);


                if(MsgType.GET_ONLINE_LIST.equals(message.getMsgType())){
                    Set<String> keys = ht.keySet();
                    StringBuilder sb = new StringBuilder("");
                    for (String key : keys) {
                        sb.append(key);
                        sb.append("\n");
                    }
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(new Message("","",MsgType.GET_ONLINE_LIST,sb.toString()));
                }else if(MsgType.SEND_TO_ONE.equals(message.getMsgType())){//私聊
                    Socket socket =null;
                    try {
                        socket= ht.get(message.getReceiveId()).socket;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        if(socket!=null){
                            ObjectOutputStream oos1 = new ObjectOutputStream(socket.getOutputStream());
                            oos1.writeObject(message);
                        }else {
                            OffLineMessage.add(message);
                        }
                    }
                }else if(MsgType.SEND_TO_ALL.equals(message.getMsgType())){//群发
                    Set<String> keySet = ht.keySet();
                    for (String key : keySet) {
                        if(!message.getSendId().equals(key)){
                            //ServerThread serverThread = ht.get(key);
                            ObjectOutputStream oos = new ObjectOutputStream(ht.get(key).socket.getOutputStream());
                            oos.writeObject(message);
                        }
                    }
                }else if(MsgType.SEND_TO_FILE.equals(message.getMsgType())){//私发文件
                    Socket socket =null;
                    try {
                        socket= ht.get(message.getReceiveId()).socket;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        if(socket!=null){
                            ObjectOutputStream oos1 = new ObjectOutputStream(socket.getOutputStream());
                            oos1.writeObject(message);
                        }else {
                            OffLineMessage.add(message);
                        }
                    }
                }else if(MsgType.EXIT.equals(message.getMsgType())){//下线
                    Set<String> keySet = ht.keySet();
                    for (String key : keySet) {
                        if(key.equals(message.getSendId())){
                            ht.remove(key);
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {

            }
        }
    }


}
