package com.bst.pidms.enums;

/**
 * @Author: BST
 * @Date: 2019/4/23 19:30
 */
public enum opEnum {
    DELETE_FILE("删除文档", 1), UPLOAD_FILE("上传文档", 2),
    ADD_TAG("添加标签", 2), DELETE_TAG("删除标签", 1),
    ADD_COMMENT("添加评论", 2), DELETE_COMMENT("删除评论", 1),
    SEND_MAIL("发送邮件", 4), SEARCH("查询", 3),
    ADD_CONTACT("添加联系人", 2), DELETE_CONTACT("删除联系人", 1),
    EDIT("编辑文档", 5),
    COLLECT("收藏文档", 7), ATTENTION("关注文档", 6),
    NOCOLLECT("取消收藏文档", 7), NOATTENTION("取消关注文档", 6);

    private String name;
    private Integer num;

    opEnum(String name, Integer num) {
        this.name = name;
        this.num = num;
    }

    public String getName() {
        String color = "";
        switch (this.num) {
            case 1:
                color = "#AD1411";
                break;
            case 2:
                color = "#4029CD";
                break;
            case 3:
                color = "#FF33CC";
                break;
            case 4:
                color = "#00CC66";
                break;
            case 5:
                color = "#203397";
                break;
            case 6:
                color = "#FF6633";
                break;
            case 7:
                color = "#660033";
                break;
        }

        return "<b><font style='color:" + color + "'>[" + name + "]</font></b> ";
    }
}
