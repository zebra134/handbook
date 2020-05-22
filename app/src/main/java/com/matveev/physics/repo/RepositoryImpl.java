package com.matveev.physics.repo;

import android.app.Application;

import com.matveev.physics.repo.database.Entities.LawsEntity;
import com.matveev.physics.repo.database.Formula;
import com.matveev.physics.repo.database.PhysicsDao;
import com.matveev.physics.repo.database.PhysicsDatabase;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RepositoryImpl implements Repository {

    private final ExecutorService EXECUTOR =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private PhysicsDao physicsDao;

    public RepositoryImpl(Application application) {
        physicsDao = PhysicsDatabase.getInstance(application).physicsDao();
    }

    @Override
    public CompletableFuture<List<Formula>> getFormulasBySection(String section) {
        return CompletableFuture.supplyAsync(() -> physicsDao.getFormulasBySection(section), EXECUTOR);
    }

    @Override
    public CompletableFuture<List<LawsEntity>> getLawsBySection(String section) {
        return CompletableFuture.supplyAsync(() -> physicsDao.getLawsBySection(section), EXECUTOR);
    }
}
