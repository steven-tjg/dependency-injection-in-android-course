package com.techyourchance.journeytodependencyinjection.screens.questionslist;

import com.techyourchance.journeytodependencyinjection.questions.Question;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModel;

public class QuestionsListViewModel extends ViewModel {

    private List<Question> mQuestions = new ArrayList<>();

    public List<Question> getQuestions() {
        return mQuestions;
    }

    public void setQuestions(List<Question> questions) {
        mQuestions = questions;
    }
}
