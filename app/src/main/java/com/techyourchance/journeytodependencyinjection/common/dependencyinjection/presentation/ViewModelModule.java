package com.techyourchance.journeytodependencyinjection.common.dependencyinjection.presentation;

import com.techyourchance.journeytodependencyinjection.questions.FetchQuestionDetailsUseCase;
import com.techyourchance.journeytodependencyinjection.screens.common.viewmodel.ViewModelFactory;
import com.techyourchance.journeytodependencyinjection.screens.questiondetails.QuestionDetailsViewModel;
import com.techyourchance.journeytodependencyinjection.screens.questionslist.QuestionsListViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelModule {

    @Provides
    ViewModelFactory viewModelFactory(QuestionDetailsViewModel questionDetailsViewModel,
                                      QuestionsListViewModel questionsListViewModel) {
        return new ViewModelFactory(questionDetailsViewModel, questionsListViewModel);
    }

    @Provides
    QuestionDetailsViewModel questionDetailsViewModel(FetchQuestionDetailsUseCase fetchQuestionDetailsUseCase) {
        return new QuestionDetailsViewModel(fetchQuestionDetailsUseCase);
    }

    @Provides
    QuestionsListViewModel questionsListViewModel() {
        return new QuestionsListViewModel();
    }
}
