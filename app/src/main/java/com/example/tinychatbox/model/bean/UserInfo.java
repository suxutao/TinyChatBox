package com.example.tinychatbox.model.bean;

public class UserInfo {
    private String name;
    private String hxid;
    private String nick;
    private String photo;

    public UserInfo() {
    }

    public UserInfo(String name) {
        this.name = name;
        this.hxid = name;
        this.nick = name;
    }


    public UserInfo(String name, String hxid, String nick, String photo) {
        this.name = name;
        this.hxid = hxid;
        this.nick = nick;
        this.photo = photo;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return hxid
     */
    public String getHxid() {
        return hxid;
    }

    /**
     * 设置
     * @param hxid
     */
    public void setHxid(String hxid) {
        this.hxid = hxid;
    }

    /**
     * 获取
     * @return nick
     */
    public String getNick() {
        return nick;
    }

    /**
     * 设置
     * @param nick
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * 获取
     * @return photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * 设置
     * @param photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String toString() {
        return "UserInfo{name = " + name + ", hxid = " + hxid + ", nick = " + nick + ", photo = " + photo + "}";
    }
}
