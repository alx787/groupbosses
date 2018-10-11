package ru.hlynov.oit.alex.entity;

import net.java.ao.Entity;
import net.java.ao.schema.NotNull;

public interface GroupBossesEntity extends Entity {
    @NotNull
    public String getGroupName();

    @NotNull
    public void setGroupName(String groupName);

    @NotNull
    public String getBossName();

    @NotNull
    public void setBossName(String bossName);
}
