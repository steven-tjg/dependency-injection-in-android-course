package com.techyourchance.journeytodependencyinjection.common.dependencyinjection.application;

import com.techyourchance.journeytodependencyinjection.common.dependencyinjection.presentation.PresentationComponent;
import com.techyourchance.journeytodependencyinjection.common.dependencyinjection.presentation.PresentationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    public PresentationComponent newPresentationComponent(PresentationModule presentationModule);
}
