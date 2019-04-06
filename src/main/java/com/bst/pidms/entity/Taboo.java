package com.bst.pidms.entity;

import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName = "pidms", type = "tb_taboo")
public class Taboo implements Serializable {
    private Integer id;

    private String word;

    private static final long serialVersionUID = 1L;

    public Taboo(Integer id, String word) {
        this.id = id;
        this.word = word;
    }

    public Taboo() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word == null ? null : word.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", word=").append(word);
        sb.append("]");
        return sb.toString();
    }
}