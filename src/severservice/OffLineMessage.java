package severservice;


import entity.Message;

import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 * @Classname OffLineMessage
 * @Description 存储离线消息
 * @Version 1.0.0
 * @Date 2022/8/6 13:18
 * @Created by gyl
 */
public class OffLineMessage {
    public  static Hashtable<String, List<Message>>ht =new Hashtable<>();
    public static void add(Message message){
       if(ht.containsKey(message.getReceiveId())){
           ht.get(message.getReceiveId()).add(message);
       }else {
           ArrayList<Message> messages = new ArrayList<>();
           messages.add(message);
           ht.put(message.getReceiveId(),messages);
       }
    }
    public static Hashtable<String,List<Message>> getOffLineMap(){
        return ht;
    }
}
