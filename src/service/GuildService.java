package service;

import entity.*;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.List;

public class GuildService {
    private static Logger logger = Logger.getLogger(GuildService.class);

    public synchronized void createGuild(User user, String guildName){
        if(user.getGuild() != null){
            logger.debug("[已有公会] 玩家已有公会");
        }
        else {
            Guild guild = new Guild();
            guild.setGuildName(guildName);
            user.setBoss(true);
            guild.getUserList().add(user);
        }
    }

    public synchronized void apply(Guild guild,User user){
        boolean applyRepeated = false;
        for(User u:guild.getApplyList()){
            if(u.getUserId() == user.getUserId()){
                applyRepeated = true;
                break;
            }
        }
        if(user.getGuild() != null){
            logger.debug("[已有公会] 玩家已有公会");
        }
        else if(applyRepeated){
            logger.debug("[申请重复] 不可重复申请同一个公会");
        }
        else {
            guild.getApplyList().add(user);
        }
    }

    public synchronized void quit(Guild guild,long userId){
        Iterator<User> iterator = guild.getUserList().iterator();
        while(iterator.hasNext()){
            User u = iterator.next();
            if(u.getUserId() == userId){
                if(u.isBoss()){
                    logger.debug("[您是会长] 无法退出，请直接注销公会");
                }
                else {
                    iterator.remove();
                }
                break;
            }
        }
    }

    public synchronized void dismissUser(Guild guild,User manager,long userId){
        if(!manager.isBoss() | !manager.isViceBoss()){
            logger.debug("[权限限制] 无法踢除会员");
        }
        else {
            Iterator<User> iterator = guild.getUserList().iterator();
            while(iterator.hasNext()){
                User u = iterator.next();
                if(u.getUserId() == userId){
                    iterator.remove();
                    break;
                }
            }
        }
    }

    public synchronized List<User> applyListView(Guild guild, User manager){
        if(!manager.isBoss() | !manager.isViceBoss()){
            logger.debug("[权限限制] 无法查看申请列表");
            return null;
        }
        else {
            return guild.getApplyList();
        }
    }

    public synchronized void applyListCheck(Guild guild,User manager,long userId,boolean op){
        if(!manager.isBoss() | !manager.isViceBoss()){
            logger.debug("[权限限制] 无法审核申请列表");
        }
        else if(guild.getCurUserNum() >= guild.getMaxUserNum()){
            logger.debug("[公会满员] 无法增加成员");
        }
        else {
            Iterator<User> iterator = guild.getUserList().iterator();
            while(iterator.hasNext()){
                User u = iterator.next();
                if(u.getUserId() == userId){
                    if(op){//  op=1:同意申请,  op=0:拒绝申请
                        guild.getUserList().add(u);
                        u.setGuild(guild);
                    }
                    iterator.remove();
                    break;
                }
            }
        }
    }

    public synchronized void upUserLevel(Guild guild,User manager,long userId){
        if(!manager.isBoss()){
            logger.debug("[权限限制] 无法升级会员");
        }
        else if(guild.getCurViceBossNum() >= guild.getMaxViceBossNum()){
            logger.debug("[升级失败] 公会已有" + guild.getCurViceBossNum() + "个副会长");
        }
        else {
            for(User u:guild.getUserList()){
                if(u.getUserId() == userId){
                    u.setViceBoss(true);
                    break;
                }
            }
        }
    }

    public synchronized void viceBossLevelOp(Guild guild,User manager,long userId,boolean op){
        if(!manager.isBoss()){
            logger.debug("[权限限制] 无法升级副会长");
        }
        else{
            for(User u:guild.getUserList()){
                if(u.getUserId() == userId){//  找出当前成员
                    if(!u.isViceBoss()){
                        logger.debug("[操作失败] 当前成员不是副会长，无法降级或升级");
                    }
                    else {
                        if(op){//  op=1:升级副会长，会长降为副会长,  op=0:降级副会长为普通会员
                            u.setBoss(true);
                            manager.setBoss(false);
                            manager.setViceBoss(true);
                        }
                        u.setViceBoss(false);
                    }
                }
            }
        }
    }
}
