package ru.hlynov.oit.alex.impl;

import com.atlassian.crowd.embedded.api.Group;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.user.util.UserUtil;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
//import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hlynov.oit.alex.api.UserBossService;
import ru.hlynov.oit.alex.dao.GroupBossesDAO;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.SortedSet;

//@ExportAsService({UserBossService.class})
//@Named("userBossByUserName")
//@ExportAsService
//@Named
//@Component
@ExportAsService({UserBossService.class})
@Named("userBossService")
public class UserBossServiceImpl implements UserBossService
{
//    @ComponentImport
//    private final ApplicationProperties applicationProperties;

    private static final Logger log = LoggerFactory.getLogger(GroupBossesDAOImpl.class);

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

    @Override
    public void sayInLog() {
        log.warn("====================== imported ======================");
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