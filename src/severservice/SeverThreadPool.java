package severservice;

import entity.User;

import java.util.Hashtable;

public class SeverThreadPool {
   public static Hashtable<String, ServerThread> hashtable=new Hashtable();
   //添加一个线程到集合
   public static void addThread(String userId,ServerThread serverThread){
      hashtable.put(userId,serverThread);
   }
   //从集合获取一个线程
   public static ServerThread getThread(String userId){
      return hashtable.get(userId);
   }
}
