package com.matveev.physics.utils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.matveev.physics.repo.RepositoryImpl;
import com.matveev.physics.show_formulas.ShowFormulasActivityViewModel;
import com.matveev.physics.show_theorems.ShowLawsActivityViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public ViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ShowFormulasActivityViewModel.class)) {
            return (T) new ShowFormulasActivityViewModel(application, new RepositoryImpl(application));
        }
        if (modelClass.isAssignableFrom(ShowLawsActivityViewModel.class)) {
            return (T) new ShowLawsActivityViewModel(application, new RepositoryImpl(application));
        }
        return null;
    }
}
