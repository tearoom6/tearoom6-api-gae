package jp.tearoom6.api.rest.memorytouch;

import com.google.appengine.api.datastore.*;
import jp.tearoom6.api.rest.MessageDigestAdapter;
import jp.tearoom6.api.rest.memorytouch.model.RankingRecord;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * MemoryTouch API - RankingRecord
 */
@Path("/memorytouch")
public class RankingRecordResource {

    private static final Logger logger = Logger.getLogger(RankingRecordResource.class.getName());

    @GET
    @Path("/rankingrecords/{category}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<RankingRecord> getRankingRecords(@PathParam("category") String category, @HeaderParam("App-Version") String version) {
        try {
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
        } catch (Exception e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @POST
    @Path("/rankingrecords")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response postRankingRecord(RankingRecord rankingRecord,
                                      @HeaderParam("Api-Token") String token, @HeaderParam("App-Version") String version) {
        try {
            if (rankingRecord.getReqCode() == null || token == null || rankingRecord.getReqCode().isEmpty()) {
                return Response.status(400).build(); // Bad Request
            }

            // 認証
            MessageDigestAdapter mdAdapter = new MessageDigestAdapter("MD5");
            String md5 = mdAdapter.digest(rankingRecord.getReqCode());
            if (!md5.equals(token)) {
                return Response.status(400).build(); // Bad Request
            }

            // Datastoreに保存
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            Key key = KeyFactory.createKey("category", rankingRecord.getCategory());
            Entity entity = new Entity("RankingRecord", key);
            entity.setProperty("reqCode", rankingRecord.getReqCode());
            entity.setProperty("category", rankingRecord.getCategory());
            entity.setProperty("name", rankingRecord.getName());
            entity.setProperty("point", rankingRecord.getPoint());
            entity.setProperty("createdAt", new Date());
            datastore.put(entity);

            return Response.ok(rankingRecord).build(); // Ok

        } catch (Exception e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
            return Response.serverError().build(); // Internal Server Error
        }
    }

}