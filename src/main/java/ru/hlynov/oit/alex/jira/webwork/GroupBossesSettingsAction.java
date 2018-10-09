package ru.hlynov.oit.alex.jira.webwork;

import com.atlassian.crowd.embedded.api.Group;
import com.atlassian.jira.bc.group.search.GroupPickerSearchService;
import com.atlassian.jira.component.ComponentAccessor;
//import com.atlassian.jira.user.util.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.web.action.JiraWebActionSupport;

import java.util.List;

public class GroupBossesSettingsAction extends JiraWebActionSupport
{
    private static final Logger log = LoggerFactory.getLogger(GroupBossesSettingsAction.class);

    private List<Group> allGroups;

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
}
