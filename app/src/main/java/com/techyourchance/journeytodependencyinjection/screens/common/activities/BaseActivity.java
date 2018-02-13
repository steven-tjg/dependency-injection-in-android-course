package com.techyourchance.journeytodependencyinjection.screens.common.activities;

import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;

import com.techyourchance.journeytodependencyinjection.MyApplication;
import com.techyourchance.journeytodependencyinjection.common.dependencyinjection.CompositionRoot;
import com.techyourchance.journeytodependencyinjection.common.dependencyinjection.Injector;
import com.techyourchance.journeytodependencyinjection.common.dependencyinjection.PresentationCompositionRoot;

public class BaseActivity extends AppCompatActivity {

    private boolean mIsInjectorUsed;

    @UiThread
    protected Injector getInjector() {
        if (mIsInjectorUsed) {
            throw new RuntimeException("there is no need to use injector more than once");
        }
        mIsInjectorUsed = true;
        return new Injector(getCompositionRoot());
    }

    private PresentationCompositionRoot getCompositionRoot() {
        return new PresentationCompositionRoot(getAppCompositionRoot(),this);
    }

    private CompositionRoot getAppCompositionRoot() {
        return ((MyApplication) getApplication()).getCompositionRoot();
    }
}
