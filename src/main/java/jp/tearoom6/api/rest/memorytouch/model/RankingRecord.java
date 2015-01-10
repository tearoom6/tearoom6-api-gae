package jp.tearoom6.api.rest.memorytouch.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * RankingRecord
 * JSONにシリアライズする
 */
@XmlRootElement
public class RankingRecord {

    private String reqCode;
    private String category;
    private String name;
    private int rank;
    private int point;
    private Date createdAt;

    public String getReqCode() {
        return reqCode;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
