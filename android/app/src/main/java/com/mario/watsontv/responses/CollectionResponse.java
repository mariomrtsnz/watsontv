package com.mario.watsontv.responses;

import java.io.Serializable;
import java.util.List;

public class CollectionResponse implements Serializable {
    String id;
    String name;
    String description;
    List<String> collected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCollected() {
        return collected;
    }

    public void setCollected(List<String> collected) {
        this.collected = collected;
    }
}
