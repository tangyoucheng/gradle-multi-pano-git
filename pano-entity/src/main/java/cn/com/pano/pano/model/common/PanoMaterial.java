package cn.com.pano.pano.model.common;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PanoMaterial implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_material.material_id
     *
     * @mbg.generated
     */
    public String materialId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_material.exposition_id
     *
     * @mbg.generated
     */
    public String expositionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_material.material_type_id
     *
     * @mbg.generated
     */
    public String materialTypeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_material.material_name
     *
     * @mbg.generated
     */
    public String materialName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_material.material_path
     *
     * @mbg.generated
     */
    public String materialPath;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_material.gif_width
     *
     * @mbg.generated
     */
    public String gifWidth;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_material.gif_height
     *
     * @mbg.generated
     */
    public String gifHeight;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_material.gif_frame_count
     *
     * @mbg.generated
     */
    public String gifFrameCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_material.gif_delay_time
     *
     * @mbg.generated
     */
    public String gifDelayTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_material.flow_text_info
     *
     * @mbg.generated
     */
    public String flowTextInfo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_material.DELETE_FLAG
     *
     * @mbg.generated
     */
    public Boolean deleteFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_material.CREATE_USER_ID
     *
     * @mbg.generated
     */
    public String createUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_material.CREATE_DATE
     *
     * @mbg.generated
     */
    public LocalDateTime createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_material.LAST_UPDATE_USER_ID
     *
     * @mbg.generated
     */
    public String lastUpdateUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_material.LAST_UPDATE_DATE
     *
     * @mbg.generated
     */
    public LocalDateTime lastUpdateDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_material.text_info
     *
     * @mbg.generated
     */
    public String textInfo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pano_material.notes
     *
     * @mbg.generated
     */
    public String notes;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pano_material
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;
}