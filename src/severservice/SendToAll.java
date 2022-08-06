package severservice;

import entity.Message;
import entity.MsgType;
import util.GetInput;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Hashtable;

/**
 * @Classname SendToAll
 * @Description 推送消息给所有在线的服务器
 * @Version 1.0.0
 * @Date 2022/8/6 11:31
 * @Created by gyl
 */
public class SendToAll extends Thread{
    @Override
    public void run() {
        boolean flag =true;
        while(flag){
            Hashtable<String, ServerThread> threadHashtable = SeverThreadPool.hashtable;
            Collection<ServerThread> values = threadHashtable.values();
            System.out.println("请输入要推送的消息内容【输入exit退出推送服务】");
            String input = GetInput.getInput();
            if(input.equals("exit")){
                flag=false;
            }
            Message message = new Message("服务器", "", MsgType.PUSH, input);
            try {
                for (ServerThread value : values) {
                    ObjectOutputStream oos = new ObjectOutputStream(value.socket.getOutputStream());
                    oos.writeObject(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
