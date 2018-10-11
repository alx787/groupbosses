package ru.hlynov.oit.alex.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.transaction.TransactionCallback;
import net.java.ao.DBParam;
import net.java.ao.Query;
import ru.hlynov.oit.alex.dao.GroupBossesDAO;
import ru.hlynov.oit.alex.entity.GroupBossesEntity;
import ru.hlynov.oit.alex.entity.GroupBosses;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;

@Named
public class GroupBossesDAOImpl implements GroupBossesDAO {

    private final ActiveObjects ao;

    @Inject
    public GroupBossesDAOImpl(@ComponentImport ActiveObjects ao) {
        this.ao = ao;
    }

    @Override
    public List<GroupBossesEntity> getAllGroupBosses() {
        GroupBossesEntity[] grArray = ao.find(GroupBossesEntity.class, Query.select());
        List<GroupBossesEntity> grList = Arrays.asList(grArray);
        return grList;
    }

    @Override
    public GroupBossesEntity getGroupBosses(long id) {
        GroupBossesEntity[] grArray = ao.find(GroupBossesEntity.class, Query.select().where("ID=?", id));
        if (grArray.length == 1) {
            return grArray[0];
        }

        return null;
    }

    @Override
    public void updateGroupBosses(long id, GroupBosses groupBosses) {

        ao.executeInTransaction(new TransactionCallback<GroupBossesEntity>() {
            @Override
            public GroupBossesEntity doInTransaction() {
                GroupBossesEntity grEntity = ao.find(GroupBossesEntity.class, Query.select().where("ID=?", id))[0];
                grEntity.setBossName(groupBosses.getBossName());
                grEntity.setGroupName(groupBosses.getGroupName());
                grEntity.save();
                return null;
            }
        });

    }

    @Override
    public void deleteGroupBosses(long id) {
        ao.executeInTransaction(new TransactionCallback<GroupBossesEntity>() {
            @Override
            public GroupBossesEntity doInTransaction() {
                GroupBossesEntity grEntity = ao.find(GroupBossesEntity.class, Query.select().where("ID=?", id))[0];
                ao.delete(grEntity);
                return null;
            }
        });

    }

    @Override
    public Integer addGroupBosses(GroupBosses groupBosses) {

        return ao.executeInTransaction(new TransactionCallback<Integer>(){
            @Override
            public Integer doInTransaction() {
                GroupBossesEntity newGrEntity = ao.create(GroupBossesEntity.class, new DBParam("GROUPNAME", groupBosses.getGroupName()), new DBParam("BOSSNAME", groupBosses.getBossName()));

//                newGrEntity.setGroupName(groupBosses.getGroupName());
//                newGrEntity.setBossName(groupBosses.getBossName());

                newGrEntity.save();

                return newGrEntity.getID();
            }
        });
    }
}
