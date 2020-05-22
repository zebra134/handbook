package com.matveev.physics.show_theorems;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.matveev.physics.repo.Repository;
import com.matveev.physics.repo.database.Entities.LawsEntity;

import java.util.List;

public class ShowLawsActivityViewModel extends AndroidViewModel {

    private Repository repository;
    private MutableLiveData<List<LawsEntity>> lawsBySectionList = new MutableLiveData<>();

    public ShowLawsActivityViewModel(@NonNull Application application, Repository repository) {
        super(application);
        this.repository = repository;
    }

    public void fetchLawsBySection(String section) {
        repository.getLawsBySection(section)
                .thenAccept(laws -> lawsBySectionList.postValue(laws));
    }

    public LiveData<List<LawsEntity>> getLawsBySection() {
        return lawsBySectionList;
    }
}
