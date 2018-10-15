package ru.hlynov.oit.alex.rest;

import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hlynov.oit.alex.dao.GroupBossesDAO;
import ru.hlynov.oit.alex.entity.GroupBosses;
import ru.hlynov.oit.alex.entity.GroupBossesEntity;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/groupbosses")
public class GroupBossesRest {

    private static final Logger log = LoggerFactory.getLogger(GroupBossesRest.class);

    private final GroupBossesDAO groupBossesDAO;

    public GroupBossesRest(GroupBossesDAO groupBossesDAO) {
        this.groupBossesDAO = groupBossesDAO;
    }

//    @AnonymousAllowed
    @GET
//    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/get/{id}")
    public Response getGroupBosses(@QueryParam("id") String id)  {
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();

        int int_id = Integer.parseInt(id);
        GroupBosses groupBosses = groupBossesDAO.getGroupBosses(int_id);

        if (groupBosses == null) {
            jsonObject.addProperty("id", "");
            jsonObject.addProperty("groupname", "");
            jsonObject.addProperty("bossname", "");
        } else {
            jsonObject.addProperty("id", groupBosses.getId());
            jsonObject.addProperty("groupname", groupBosses.getGroupName());
            jsonObject.addProperty("username", groupBosses.getUserName());
            jsonObject.addProperty("userdisplayname", groupBosses.getUserDisplayName());
        }

        return Response.ok(gson.toJson(jsonObject)).build();
    }


    // смотри тут если что
    // https://comsysto.github.io/kitchen-duty-plugin-for-atlassian-jira/tutorial/06-step-03-planning-page--user-search-js-controller/
    @POST
    @Path("/add")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addGroupBosses(String JSONRequest) {
//    public Response addGroupBosses(@Context HttpServletRequest req) {

        JsonParser jsonParser = new JsonParser();
        JsonObject json = jsonParser.parse(JSONRequest).getAsJsonObject();
//
        String groupname = json.get("groupname").getAsString();
        String bossname = json.get("bossname").getAsString();

//        log.warn("==== groupname " + groupname);
//        log.warn("==== bossname " + bossname);
//

        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();

        GroupBosses groupBosses = groupBossesDAO.addGroupBosses(new GroupBosses(groupname, bossname, bossname));

        jsonObject.addProperty("id", groupBosses.getId());
        jsonObject.addProperty("groupname", groupBosses.getGroupName());
        jsonObject.addProperty("username", groupBosses.getUserName());
        jsonObject.addProperty("userdisplayname", groupBosses.getUserDisplayName());

        return Response.ok(gson.toJson(jsonObject)).build();


    }


}
