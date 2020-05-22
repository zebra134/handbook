package com.matveev.physics.repo.database;

import androidx.room.Dao;
import androidx.room.Query;

import com.matveev.physics.repo.database.Entities.LawsEntity;

import java.util.List;

@Dao
public interface PhysicsDao {

    @Query("SELECT * FROM text WHERE section = :section")
    List<Formula> getFormulasBySection(String section);

    @Query("SELECT * FROM laws WHERE section = :section")
    List<LawsEntity> getLawsBySection(String section);
}
