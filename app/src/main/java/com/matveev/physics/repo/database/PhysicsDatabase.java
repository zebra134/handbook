package com.matveev.physics.repo.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.matveev.physics.repo.database.Entities.ImageEntity;
import com.matveev.physics.repo.database.Entities.LawsEntity;
import com.matveev.physics.repo.database.Entities.TextEntity;

@Database(entities = {ImageEntity.class, TextEntity.class, LawsEntity.class}, version = 1, exportSchema = false)
public abstract class PhysicsDatabase extends RoomDatabase {

    private static volatile PhysicsDatabase instance;

    public static PhysicsDatabase getInstance(final Context context){
        if (instance == null){
            synchronized (PhysicsDatabase.class){
                if (instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            PhysicsDatabase.class,
                            "physics.db")
                            .createFromAsset("physics.db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }

        return instance;
    }

    public abstract PhysicsDao physicsDao();
}
