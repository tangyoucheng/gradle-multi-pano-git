package cn.com.pano.pano.model.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PanoHotspotUrl implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_hotspot_url.hotspot_id
     *
     * @mbg.generated
     */
    public String hotspotId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_hotspot_url.hotspot_url_object_id
     *
     * @mbg.generated
     */
    public String hotspotUrlObjectId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_hotspot_url.sort_key
     *
     * @mbg.generated
     */
    public BigDecimal sortKey;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_hotspot_url.DELETE_FLAG
     *
     * @mbg.generated
     */
    public Boolean deleteFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_hotspot_url.CREATE_USER_ID
     *
     * @mbg.generated
     */
    public String createUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_hotspot_url.CREATE_DATE
     *
     * @mbg.generated
     */
    public LocalDateTime createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_hotspot_url.LAST_UPDATE_USER_ID
     *
     * @mbg.generated
     */
    public String lastUpdateUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_hotspot_url.LAST_UPDATE_DATE
     *
     * @mbg.generated
     */
    public LocalDateTime lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pano_hotspot_url
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;
}