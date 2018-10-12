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

import javax.ws.rs.*;
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
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/get/{id}")
    public Response getGroupBosses(@QueryParam("id") String id)  {
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();

        int int_id = Integer.parseInt(id);
        GroupBossesEntity groupBossesEntity = groupBossesDAO.getGroupBosses(int_id);

        if (groupBossesEntity == null) {
            jsonObject.addProperty("id", "");
            jsonObject.addProperty("groupname", "");
            jsonObject.addProperty("bossname", "");
        } else {
            jsonObject.addProperty("id", groupBossesEntity.getID());
            jsonObject.addProperty("groupname", groupBossesEntity.getGroupName());
            jsonObject.addProperty("bossname", groupBossesEntity.getBossName());
        }

        return Response.ok(gson.toJson(jsonObject)).build();
    }

    @POST
    @Path("/add")
//    public Response addGroupBosses(@QueryParam("groupname") String groupname, @QueryParam("bossname") String bossname)  {
    public Response addGroupBosses(String request) {

        JsonParser jsonParser = new JsonParser();
        JsonObject json = jsonParser.parse(request).getAsJsonObject();

        String groupname = json.get("groupname").getAsString();
        String bossname = json.get("bossname").getAsString();

        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();

        GroupBossesEntity groupBossesEntity = groupBossesDAO.addGroupBosses(new GroupBosses(groupname, bossname));

        jsonObject.addProperty("id", groupBossesEntity.getID());
        jsonObject.addProperty("groupname", groupBossesEntity.getGroupName());
        jsonObject.addProperty("bossname", groupBossesEntity.getBossName());

        return Response.ok(gson.toJson(jsonObject)).build();


    }


}
