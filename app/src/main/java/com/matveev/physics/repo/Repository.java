package com.matveev.physics.repo;

import com.matveev.physics.repo.database.Entities.LawsEntity;
import com.matveev.physics.repo.database.Formula;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Repository {
    CompletableFuture<List<Formula>> getFormulasBySection(String section);
    CompletableFuture<List<LawsEntity>> getLawsBySection(String section);
}
