package com.bst.pidms.entity.picture;

/**
 * @Author: BST
 * @Date: 2019/4/6 14:21
 */
public class PicInfo {
    private Integer height;
    private Integer width;
    //    器材
    private String modal;
    private Long createTime;
    //    光圈
    private String aperture;
    private String gpsLatitude;
    private String gpsLongitude;


    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getModal() {
        return modal;
    }

    public void setModal(String modal) {
        this.modal = modal;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }


    public String getAperture() {
        return aperture;
    }

    public void setAperture(String aperture) {
        this.aperture = aperture;
    }


    public String getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(String gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }


    public String getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(String gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    @Override
    public String toString() {
        return "PicInfo{" +
                "height=" + height +
                ", width=" + width +
                ", modal='" + modal + '\'' +
                ", createTime=" + createTime +
                ", aperture='" + aperture + '\'' +
                ", gpsLatitude='" + gpsLatitude + '\'' +
                ", gpsLongitude='" + gpsLongitude + '\'' +
                '}';
    }
}

