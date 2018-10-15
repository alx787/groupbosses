package ru.hlynov.oit.alex.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.activeobjects.tx.Transactional;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.util.UserManager;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.transaction.TransactionCallback;
import net.java.ao.DBParam;
import net.java.ao.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hlynov.oit.alex.dao.GroupBossesDAO;
import ru.hlynov.oit.alex.entity.GroupBossesEntity;
import ru.hlynov.oit.alex.entity.GroupBosses;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Transactional
@Named
public class GroupBossesDAOImpl implements GroupBossesDAO {

    private static final Logger log = LoggerFactory.getLogger(GroupBossesDAOImpl.class);


    private final ActiveObjects ao;
    UserManager userManager = ComponentAccessor.getUserManager();
    @Inject
    public GroupBossesDAOImpl(@ComponentImport ActiveObjects ao) {
        this.ao = ao;
    }

//    @Override
//    public List<GroupBossesEntity> getAllGroupBosses() {
//        GroupBossesEntity[] grArray = ao.find(GroupBossesEntity.class, Query.select());
//        List<GroupBossesEntity> grList = Arrays.asList(grArray);
//        return grList;
//    }

    @Override
    public List<GroupBosses> getAllGroupBosses() {
        String username;
        GroupBossesEntity[] grArray = ao.find(GroupBossesEntity.class, Query.select());
        List<GroupBosses> grList = new ArrayList<GroupBosses>();
        for (GroupBossesEntity grOne : grArray) {
            ApplicationUser applicationUser = userManager.getUserByKey(grOne.getUserName());
            if (applicationUser == null) {
                username = "";
            } else {
                username = applicationUser.getDisplayName();
            }
            grList.add(new GroupBosses(grOne.getID(), grOne.getGroupName(), grOne.getUserName(), username));
        }
        return grList;
    }

    @Override
    public GroupBosses getGroupBosses(int id) {
        String username;
        GroupBossesEntity[] grArray = ao.find(GroupBossesEntity.class, Query.select().where("ID=?", id));
        if (grArray.length == 1) {
            GroupBossesEntity grOne = grArray[0];
            ApplicationUser applicationUser = userManager.getUserByKey(grOne.getUserName());

            if (applicationUser == null) {
                username = "";
            } else {
                username = applicationUser.getDisplayName();
            }

            return new GroupBosses(grOne.getID(), grOne.getGroupName(), grOne.getUserName(), applicationUser.getDisplayName());
        }

        return null;
    }

    @Override
    public void updateGroupBosses(int id, GroupBosses groupBosses) {
        GroupBossesEntity grEntity = ao.find(GroupBossesEntity.class, Query.select().where("ID=?", id))[0];
        grEntity.setUserName(groupBosses.getUserName());
        grEntity.setGroupName(groupBosses.getGroupName());
        grEntity.save();
    }

    @Override
    public void deleteGroupBosses(int id) {
        GroupBossesEntity grEntity = ao.find(GroupBossesEntity.class, Query.select().where("ID = ?", id))[0];
        ao.delete(grEntity);
    }

    @Override
    public GroupBosses addGroupBosses(GroupBosses groupBosses) {
        String username;

        final GroupBossesEntity newGrEntity = ao.create(GroupBossesEntity.class, new DBParam("GROUPNAME", groupBosses.getGroupName()), new DBParam("USERNAME", groupBosses.getUserName()));
//        newGrEntity.setGroupName(groupBosses.getGroupName());
//        newGrEntity.setBossName(groupBosses.getBossName());


        newGrEntity.save();
//        ApplicationUser applicationUser = userManager.getUserByKey(newGrEntity.getUserName());

//        log.warn(String.valueOf(newGrEntity.getID()));
//        log.warn(newGrEntity.getGroupName());
//        log.warn(newGrEntity.getUserName());
//        log.warn(applicationUser.getDisplayName());

        ApplicationUser applicationUser = userManager.getUserByKey(newGrEntity.getUserName());

        if (applicationUser == null) {
            username = "";
        } else {
            username = applicationUser.getDisplayName();
        }

        GroupBosses newGrBosses = new GroupBosses(newGrEntity.getID(), newGrEntity.getGroupName(), newGrEntity.getUserName(), username);
        return newGrBosses;
    }
}
