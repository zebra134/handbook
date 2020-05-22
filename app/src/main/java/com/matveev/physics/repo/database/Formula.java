package com.matveev.physics.repo.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.matveev.physics.repo.database.Entities.ImageEntity;
import com.matveev.physics.repo.database.Entities.TextEntity;

import java.util.List;

public class Formula {

    @Embedded
    private TextEntity textEntity;
    @Relation(parentColumn = "id", entityColumn = "text_id")
    private List<ImageEntity> imageEntities;

    public Formula(TextEntity textEntity, List<ImageEntity> imageEntities) {
        this.textEntity = textEntity;
        this.imageEntities = imageEntities;
    }

    public TextEntity getTextEntity() {
        return textEntity;
    }

    public void setTextEntity(TextEntity textEntity) {
        this.textEntity = textEntity;
    }

    public List<ImageEntity> getImageEntities() {
        return imageEntities;
    }

    public void setImageEntities(List<ImageEntity> imageEntities) {
        this.imageEntities = imageEntities;
    }
}
