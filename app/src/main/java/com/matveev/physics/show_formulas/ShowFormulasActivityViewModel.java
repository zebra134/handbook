package com.matveev.physics.show_formulas;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.matveev.physics.repo.Repository;
import com.matveev.physics.repo.database.Formula;

import java.util.List;

public class ShowFormulasActivityViewModel extends AndroidViewModel {

    private Repository repository;
    private MutableLiveData<List<Formula>> formulasBySectionList = new MutableLiveData<>();

    public ShowFormulasActivityViewModel(@NonNull Application application, Repository repository) {
        super(application);
        this.repository = repository;
    }

    public void fetchFormulasBySection(String section) {
        repository.getFormulasBySection(section)
                .thenAccept(formulas -> formulasBySectionList.postValue(formulas));
    }

    public LiveData<List<Formula>> getFormulasBySection() {
        return formulasBySectionList;
    }
}
