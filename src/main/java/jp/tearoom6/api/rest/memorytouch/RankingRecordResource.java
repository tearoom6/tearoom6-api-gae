package jp.tearoom6.api.rest.memorytouch;

import com.google.appengine.api.datastore.*;
import jp.tearoom6.api.rest.memorytouch.model.RankingRecord;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * MemoryTouch API - RankingRecord
 */
@Path("/memorytouch")
public class RankingRecordResource {

    @GET
    @Path("/rankingrecords/{category}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<RankingRecord> getRankingRecords(@PathParam("category") String category) {
        // Datastoreからポイントの降順に最大5件取得
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key key = KeyFactory.createKey("category", category);
        Query query = new Query("RankingRecord", key).addSort("point", Query.SortDirection.DESCENDING);
        List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));

        List<RankingRecord> records = new ArrayList<>();
        int rank = 1;
        int point = 0;
        for (Entity entity: entities) {
            RankingRecord record = new RankingRecord();
            record.setCategory((String)entity.getProperty("category"));
            record.setName((String)entity.getProperty("name"));
            record.setPoint(((Long) entity.getProperty("point")).intValue()); // Datastoreでは、intはlongとして保存される
            record.setRank(rank);
            records.add(record);
            if (point != record.getPoint()) {
                rank++;
            }
        }

        return records;
    }

    @POST
    @Path("/rankingrecords")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response postRankingRecord(RankingRecord rankingRecord) {
        // Datastoreに保存
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key key = KeyFactory.createKey("category", rankingRecord.getCategory());
        Entity entity = new Entity("RankingRecord", key);
        entity.setProperty("category", rankingRecord.getCategory());
        entity.setProperty("name", rankingRecord.getName());
        entity.setProperty("point", rankingRecord.getPoint());
        datastore.put(entity);

        return Response.ok(rankingRecord).build();
    }

}