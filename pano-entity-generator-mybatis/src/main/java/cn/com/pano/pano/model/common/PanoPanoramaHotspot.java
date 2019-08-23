package cn.com.pano.pano.model.common;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PanoPanoramaHotspot implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_panorama_hotspot.hotspot_id
     *
     * @mbg.generated
     */
    public String hotspotId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_panorama_hotspot.panorama_id
     *
     * @mbg.generated
     */
    public String panoramaId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_panorama_hotspot.hotspot_scale
     *
     * @mbg.generated
     */
    public String hotspotScale;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_panorama_hotspot.hotspot_type
     *
     * @mbg.generated
     */
    public String hotspotType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_panorama_hotspot.hotspot_image_id
     *
     * @mbg.generated
     */
    public String hotspotImageId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_panorama_hotspot.external_link_address
     *
     * @mbg.generated
     */
    public String externalLinkAddress;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_panorama_hotspot.hotspot_url_type
     *
     * @mbg.generated
     */
    public String hotspotUrlType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_panorama_hotspot.next_recommend_hotspot_id
     *
     * @mbg.generated
     */
    public String nextRecommendHotspotId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_panorama_hotspot.recommend_info
     *
     * @mbg.generated
     */
    public String recommendInfo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_panorama_hotspot.hotspot_tooltip
     *
     * @mbg.generated
     */
    public String hotspotTooltip;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_panorama_hotspot.hotspot_ath
     *
     * @mbg.generated
     */
    public String hotspotAth;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_panorama_hotspot.hotspot_atv
     *
     * @mbg.generated
     */
    public String hotspotAtv;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_panorama_hotspot.panorama_hlookat
     *
     * @mbg.generated
     */
    public String panoramaHlookat;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_panorama_hotspot.panorama_vlookat
     *
     * @mbg.generated
     */
    public String panoramaVlookat;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_panorama_hotspot.polygon_fillcolor
     *
     * @mbg.generated
     */
    public String polygonFillcolor;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_panorama_hotspot.polygon_fillalpha
     *
     * @mbg.generated
     */
    public String polygonFillalpha;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_panorama_hotspot.DELETE_FLAG
     *
     * @mbg.generated
     */
    public Boolean deleteFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_panorama_hotspot.CREATE_USER_ID
     *
     * @mbg.generated
     */
    public String createUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_panorama_hotspot.CREATE_DATE
     *
     * @mbg.generated
     */
    public LocalDateTime createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_panorama_hotspot.LAST_UPDATE_USER_ID
     *
     * @mbg.generated
     */
    public String lastUpdateUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_panorama_hotspot.LAST_UPDATE_DATE
     *
     * @mbg.generated
     */
    public LocalDateTime lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pano_panorama_hotspot
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;
}