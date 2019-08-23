package cn.com.pano.pano.model.common;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PanoPolygonHotspot implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_polygon_hotspot.polygon_point_id
     *
     * @mbg.generated
     */
    public String polygonPointId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_polygon_hotspot.polygon_id
     *
     * @mbg.generated
     */
    public String polygonId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_polygon_hotspot.polygon_point_ath
     *
     * @mbg.generated
     */
    public String polygonPointAth;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_polygon_hotspot.polygon_point_atv
     *
     * @mbg.generated
     */
    public String polygonPointAtv;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_polygon_hotspot.DELETE_FLAG
     *
     * @mbg.generated
     */
    public Boolean deleteFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_polygon_hotspot.CREATE_USER_ID
     *
     * @mbg.generated
     */
    public String createUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_polygon_hotspot.CREATE_DATE
     *
     * @mbg.generated
     */
    public LocalDateTime createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_polygon_hotspot.LAST_UPDATE_USER_ID
     *
     * @mbg.generated
     */
    public String lastUpdateUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_polygon_hotspot.LAST_UPDATE_DATE
     *
     * @mbg.generated
     */
    public LocalDateTime lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pano_polygon_hotspot
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;
}