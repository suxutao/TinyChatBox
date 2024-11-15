package com.example.tinychatbox.model.bean;

//群信息
public class GroupInfo {
    //群名称
    private String groupName;
    //群id
    private String groupId;
    //邀请人
    private String invatePerson;

    public GroupInfo() {
    }

    public GroupInfo(String groupName, String groupId, String invatePerson) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.invatePerson = invatePerson;
    }


    /**
     * 获取
     * @return groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * 设置
     * @param groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * 获取
     * @return groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * 设置
     * @param groupId
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取
     * @return invatePerson
     */
    public String getInvatePerson() {
        return invatePerson;
    }

    /**
     * 设置
     * @param invatePerson
     */
    public void setInvatePerson(String invatePerson) {
        this.invatePerson = invatePerson;
    }

    public String toString() {
        return "GroupInfo{groupName = " + groupName + ", groupId = " + groupId + ", invatePerson = " + invatePerson + "}";
    }
}
