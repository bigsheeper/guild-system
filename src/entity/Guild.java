package entity;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Guild{
    private String guildName;
    private final int maxUserNum = 50;
    private final int maxViceBossNum = 2;
    private List<User> userList = Collections.synchronizedList(new CopyOnWriteArrayList<>());
    private List<User> applyList = Collections.synchronizedList(new CopyOnWriteArrayList<>());

    public String getGuildName() {
        return guildName;
    }

    public int getMaxUserNum() { return maxUserNum; }

    public int getCurUserNum() { return userList.size(); }

    public int getCurViceBossNum(){
        int curViceBossNum = 0;
        for(User u:userList){
            if(u.isViceBoss()) curViceBossNum++;
        }
        return curViceBossNum;
    }

    public int getMaxViceBossNum() { return maxViceBossNum; }

    public List<User> getUserList() {
        return userList;
    }

    public List<User> getApplyList() { return applyList; }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }
}

/**
 *
 * 网上找一下tomcat教程，本机安装一个tomcat8（8.0系列的都行）
 *
 * 然后把spring mvc代码放到tomcat这个容器上跑起来
 *
 * 最后把J03作业集成到spring mvc这个框架上，举个例子: 就是说在浏览器输入
 * xxx.xxx.com/user=?&op=1 等同于user xxx 加入家族，
 * 输入 xxx.xxx.com/user=?&op=2 等同于user xxx 离开家族 ...
 *
 */
