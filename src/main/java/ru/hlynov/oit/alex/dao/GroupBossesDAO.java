package ru.hlynov.oit.alex.dao;

import ru.hlynov.oit.alex.entity.GroupBossesEntity;
import ru.hlynov.oit.alex.entity.GroupBosses;

import java.util.List;

public interface GroupBossesDAO {
    public List<GroupBossesEntity> getAllGroupBosses();
    public GroupBossesEntity getGroupBosses(long id);
    public void updateGroupBosses(long id, GroupBosses groupBosses);
    public void deleteGroupBosses(long id);
    public Integer addGroupBosses(GroupBosses groupBosses);
}
