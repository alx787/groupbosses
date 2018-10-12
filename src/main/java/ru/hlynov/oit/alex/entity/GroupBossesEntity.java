package ru.hlynov.oit.alex.entity;

import net.java.ao.Accessor;
import net.java.ao.Entity;
import net.java.ao.Mutator;
import net.java.ao.schema.NotNull;
import net.java.ao.schema.Table;

@Table("Groupbosses")
public interface GroupBossesEntity extends Entity {
    @Accessor("Groupname")
    @NotNull
    public String getGroupName();

    @Mutator("Groupname")
    @NotNull
    public void setGroupName(String groupName);

    @Accessor("Bossname")
    @NotNull
    public String getBossName();

    @Mutator("Bossname")
    @NotNull
    public void setBossName(String bossName);
}
