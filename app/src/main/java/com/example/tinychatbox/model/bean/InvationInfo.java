package com.example.tinychatbox.model.bean;



public class InvationInfo {
    private UserInfo user;
    private GroupInfo group;
    private String reason;
    private InvationStatus status;

    /**
     * 获取
     * @return user
     */
    public UserInfo getUser() {
        return user;
    }

    /**
     * 设置
     * @param user
     */
    public void setUser(UserInfo user) {
        this.user = user;
    }

    /**
     * 获取
     * @return group
     */
    public GroupInfo getGroup() {
        return group;
    }

    /**
     * 设置
     * @param group
     */
    public void setGroup(GroupInfo group) {
        this.group = group;
    }

    /**
     * 获取
     * @return reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * 设置
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * 获取
     * @return status
     */
    public InvationStatus getStatus() {
        return status;
    }

    /**
     * 设置
     * @param status
     */
    public void setStatus(InvationStatus status) {
        this.status = status;
    }

    public String toString() {
        return "InvationInfo{user = " + user + ", group = " + group + ", reason = " + reason + ", status = " + status + "}";
    }

    public enum InvationStatus{
        //个人
        NEW_INVITE,
        INVITE_ACCEPT,
        INVITE_ACCEPT_BY_PEER,

        //群组
        NEW_GROUP_INVITE,
        NEW_GROUP_APPLICATION,
        GROUP_INVITE_ACCEPTED,
        GROUP_APPLICATION_ACCEPTED,
        GROUP_INVITE_DECLINED,
        GROUP_APPLICATION_DECLINED,
        GROUP_ACCEPT_INVITE,
        GROUP_ACCEPT_APPLICATION,
        GROUP_REJECT_APPLICATION,
        GROUP_REJECT_INVITE

    }
    public InvationInfo() {
    }

    public InvationInfo(UserInfo user, GroupInfo group, String reason, InvationStatus status) {
        this.user = user;
        this.group = group;
        this.reason = reason;
        this.status = status;
    }


}
