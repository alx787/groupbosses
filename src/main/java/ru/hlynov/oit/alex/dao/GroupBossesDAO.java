package ru.hlynov.oit.alex.dao;

import ru.hlynov.oit.alex.entity.GroupBossesEntity;
import ru.hlynov.oit.alex.entity.GroupBosses;

import java.util.List;

public interface GroupBossesDAO {
    public List<GroupBosses> getAllGroupBosses();
    public GroupBosses getGroupBosses(int id);
    public void updateGroupBosses(int id, GroupBosses groupBosses);
    public void deleteGroupBosses(int id);
    public GroupBosses addGroupBosses(GroupBosses groupBosses);
    public String getBossNameByGroupName(String groupName);
}
