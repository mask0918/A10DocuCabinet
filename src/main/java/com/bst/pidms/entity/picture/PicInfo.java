package com.bst.pidms.entity.picture;

import java.util.List;

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
    private String location;
    private String ocrInfo;
    private List<Color> colors;

    public static class Color {
        private Double score;
        private Integer red;
        private Integer green;
        private Integer blue;

        public Color(Double score, Integer red, Integer green, Integer blue) {
            this.score = score;
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        @Override
        public String toString() {
            return "Color{" +
                    "score=" + score +
                    ", red=" + red +
                    ", green=" + green +
                    ", blue=" + blue +
                    '}';
        }

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }

        public Integer getRed() {
            return red;
        }

        public void setRed(Integer red) {
            this.red = red;
        }

        public Integer getGreen() {
            return green;
        }

        public void setGreen(Integer green) {
            this.green = green;
        }

        public Integer getBlue() {
            return blue;
        }

        public void setBlue(Integer blue) {
            this.blue = blue;
        }
    }

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOcrInfo() {
        return ocrInfo;
    }

    public void setOcrInfo(String ocrInfo) {
        this.ocrInfo = ocrInfo;
    }

    public List<Color> getColors() {
        return colors;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }

    @Override
    public String toString() {
        return "PicInfo{" +
                "height=" + height +
                ", width=" + width +
                ", modal='" + modal + '\'' +
                ", createTime=" + createTime +
                ", aperture='" + aperture + '\'' +
                ", location='" + location + '\'' +
                ", ocrInfo='" + ocrInfo + '\'' +
                ", colors=" + colors +
                '}';
    }
}

