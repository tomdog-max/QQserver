package severservice;

import java.util.HashMap;

/**
 * @Classname DBImitate
 * @Description 模仿数据库
 * @Version 1.0.0
 * @Date 2022/8/5 17:16
 * @Created by gyl
 */
public class DBImitate {
    public static HashMap hashMap=new HashMap();
    static {
        hashMap.put("100","张三");
        hashMap.put("200","李四");
        hashMap.put("300","王五");
    }
}
