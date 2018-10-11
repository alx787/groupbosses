package ru.hlynov.oit.alex.entity;

public class GroupBosses {

    private String groupName;
    private String bossName;

    public GroupBosses(String groupName, String bossName) {
        this.groupName = groupName;
        this.bossName = bossName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }
}
