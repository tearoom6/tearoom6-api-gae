package jp.tearoom6.api.rest.memorytouch.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * RankingRecord
 * JSONにシリアライズする
 */
@XmlRootElement
public class RankingRecord {

    private String category;
    private String name;
    private int rank;
    private int point;

    public RankingRecord()
    {
        // デフォルトコンストラクタはJSONのデシリアライズに必要?
    }

    public RankingRecord(String category, String name, int rank, int point)
    {
        this.category = category;
        this.name = name;
        this.rank = rank;
        this.point = point;
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
}
