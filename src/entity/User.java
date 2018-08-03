package entity;

public class User {
    private String userName;
    private long userId;
    private Guild guild = null;
    private boolean isBoss = false;
    private boolean isViceBoss = false;

    public String getUserName() {
        return userName;
    }

    public long getUserId() {return userId;}

    public Guild getGuild() {
        return guild;
    }

    public boolean isBoss() { return isBoss; }

    public boolean isViceBoss() { return isViceBoss; }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(long userId) { this.userId = userId; }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public void setBoss(boolean boss) { isBoss = boss; }

    public void setViceBoss(boolean viceBoss) { isViceBoss = viceBoss; }
}

