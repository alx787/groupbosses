package ru.hlynov.oit.alex.entity;

public class GroupBosses {

    int id;
    private String groupName;
    private String userName;
    private String userDisplayName;

    public GroupBosses(String groupName, String userName, String userDisplayName) {
        this.groupName = groupName;
        this.userName = userName;
        this.userDisplayName = userDisplayName;
    }

    public GroupBosses(int id, String groupName, String userName, String userDisplayName) {
        this.id = id;
        this.groupName = groupName;
        this.userName = userName;
        this.userDisplayName = userDisplayName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

 }
