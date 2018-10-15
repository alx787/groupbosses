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

    @Accessor("Username")
    @NotNull
    public String getUserName();

    @Mutator("Username")
    @NotNull
    public void setUserName(String userName);
}
