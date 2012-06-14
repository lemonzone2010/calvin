package com.apusic.ebiz.framework.core;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

public class DummyAuthor {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Field(index = Index.YES, store = Store.YES)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
