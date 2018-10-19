package ru.hlynov.oit.alex.impl;

import com.atlassian.crowd.embedded.api.Group;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.user.util.UserUtil;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import ru.hlynov.oit.alex.api.UserBossService;
import ru.hlynov.oit.alex.dao.GroupBossesDAO;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.SortedSet;

//@ExportAsService({UserBossService.class})
//@Named("userBossByUserName")
@ExportAsService
@Named
public class UserBossServiceImpl implements UserBossService
{
//    @ComponentImport
//    private final ApplicationProperties applicationProperties;

    private final GroupBossesDAO groupBossesDAO;

    @Inject
    public UserBossServiceImpl(GroupBossesDAO groupBossesDAO) {
        this.groupBossesDAO = groupBossesDAO;
    }

    @Override
    public String getUserBoss(String userName) {
        // сначала соберем все группы где есть этот пользователь
        UserUtil userUtil = ComponentAccessor.getUserUtil();
        SortedSet<Group> allGroups = userUtil.getGroupsForUser(userName);
        for (Group oneGroup : allGroups) {
            // сейчас ищем начальника каждой группы
            String groupName = groupBossesDAO.getBossNameByGroupName(oneGroup.getName());
            if (groupName != null) {
                return groupName;
            }
        }
        return "";
    }

//    @Inject
//    public UserBossServiceImpl(final ApplicationProperties applicationProperties)
//    {
//        this.applicationProperties = applicationProperties;
//    }


//    public String getName()
//    {
//        if(null != applicationProperties)
//        {
//            return "myComponent:" + applicationProperties.getDisplayName();
//        }
//
//        return "myComponent";
//    }
}