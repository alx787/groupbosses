package ru.hlynov.oit.alex.jira.webwork;

import com.atlassian.crowd.embedded.api.Group;
import com.atlassian.jira.bc.group.search.GroupPickerSearchService;
import com.atlassian.jira.component.ComponentAccessor;
//import com.atlassian.jira.user.util.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import ru.hlynov.oit.alex.dao.GroupBossesDAO;
import ru.hlynov.oit.alex.entity.GroupBosses;
import ru.hlynov.oit.alex.impl.GroupBossesDAOImpl;

import java.util.List;

public class GroupBossesSettingsAction extends JiraWebActionSupport
{
    private static final Logger log = LoggerFactory.getLogger(GroupBossesSettingsAction.class);

    private final GroupBossesDAO groupBossesDAO;

    // все доступные группы
    private List<Group> allGroups;

    public GroupBossesSettingsAction(GroupBossesDAO groupBossesDAO) {
        this.groupBossesDAO = groupBossesDAO;
    }

    @Override
    public String execute() throws Exception {
        super.execute(); //returns SUCCESS
        return SUCCESS;
    }

    public List<Group> getAllGroups() {
        GroupPickerSearchService groupSearch = ComponentAccessor.getComponent(GroupPickerSearchService.class);
        List<Group> groupList = groupSearch.findGroups("");
//        UserManager userManager = ComponentAccessor.getUserManager();
//        Set<Group> groupSet = userManager.getAllGroups();
        return groupList;
    }

    public String doAdd() throws Exception {
//        String bossName = request.getParameterValues("bossname")[0];
//        String goupName = request.getParameterValues("groupname")[0];

//        String bossName = request.getParameter("bossname");
//        String groupName = request.getParameter("groupname");

        String bossName = getHttpRequest().getParameter("bossname");
        String groupName = getHttpRequest().getParameter("groupname");

        log.warn(" ======= bossname:" + bossName);
        log.warn(" ======= groupname:" + groupName);

        GroupBosses groupBosses = new GroupBosses(groupName, bossName);

        Integer newID = groupBossesDAO.addGroupBosses(groupBosses);

        //getHttpResponse().getWriter()

        return NONE;
    }
}
