package com.techyourchance.journeytodependencyinjection.common.dependencyinjection.presentation;

import com.techyourchance.journeytodependencyinjection.questions.FetchQuestionDetailsUseCase;
import com.techyourchance.journeytodependencyinjection.screens.common.viewmodel.ViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelModule {

    @Provides
    ViewModelFactory viewModelFactory(FetchQuestionDetailsUseCase fetchQuestionDetailsUseCase) {
        return new ViewModelFactory(fetchQuestionDetailsUseCase);
    }
}
