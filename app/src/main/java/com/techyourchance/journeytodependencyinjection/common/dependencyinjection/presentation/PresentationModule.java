package com.techyourchance.journeytodependencyinjection.common.dependencyinjection.presentation;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;

import com.techyourchance.journeytodependencyinjection.common.dependencyinjection.application.ApplicationComponent;
import com.techyourchance.journeytodependencyinjection.questions.FetchQuestionDetailsUseCase;
import com.techyourchance.journeytodependencyinjection.questions.FetchQuestionsListUseCase;
import com.techyourchance.journeytodependencyinjection.screens.common.ImageLoader;
import com.techyourchance.journeytodependencyinjection.screens.common.dialogs.DialogsManager;
import com.techyourchance.journeytodependencyinjection.screens.common.mvcviews.ViewMvcFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class PresentationModule {

    private final FragmentActivity mActivity;
    private final ApplicationComponent mApplicationComponent;

    public PresentationModule(FragmentActivity fragmentActivity, ApplicationComponent applicationComponent) {
        mActivity = fragmentActivity;
        mApplicationComponent = applicationComponent;
    }

    @Provides
    FragmentManager getFragmentManager() {
        return mActivity.getSupportFragmentManager();
    }

    @Provides
    LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mActivity);
    }

    @Provides
    Activity getActivity() {
        return mActivity;
    }

    @Provides
    DialogsManager getDialogsManager(FragmentManager fragmentManager) {
        return new DialogsManager(fragmentManager);
    }

    @Provides
    FetchQuestionDetailsUseCase getFetchQuestionDetailsUseCase() {
        return mApplicationComponent.getFetchQuestionDetailsUseCase();
    }

    @Provides
    FetchQuestionsListUseCase getFetchQuestionsListUseCase() {
        return mApplicationComponent.getFetchQuestionsListUseCase();
    }

    @Provides
    ViewMvcFactory getViewMvcFactory(LayoutInflater inflater, ImageLoader imageLoader) {
        return new ViewMvcFactory(inflater, imageLoader);
    }

    @Provides
    ImageLoader getImageLoader(Activity activity) {
        return new ImageLoader(activity);
    }
}
