package com.bst.pidms.enums;

/**
 * @Author: BST
 * @Date: 2019/4/21 11:46
 */
public enum SearchEnum {
    PATH("路径搜索", 1), SENTIMENT("语义检索", 2), KEYTAG("关键词标签搜索", 3),
    COMMENT("评论搜索", 4), NAME("文件名搜索", 5), ALL("全部搜索", 6);
    private String name;
    private final int index;

    SearchEnum(String name, Integer index) {
        this.name = name;
        this.index = index;
    }

    public static SearchEnum getEnum(int code) {
        for (SearchEnum fileEnum : SearchEnum.values()) {
            if (code == fileEnum.getIndex()) {
                return fileEnum;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
}
