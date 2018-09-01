 package com.techyourchance.journeytodependencyinjection.screens.questionslist;

 import android.arch.lifecycle.ViewModelProviders;
 import android.os.Bundle;
 import android.widget.Toast;

 import com.techyourchance.journeytodependencyinjection.questions.FetchQuestionsListUseCase;
 import com.techyourchance.journeytodependencyinjection.questions.Question;
 import com.techyourchance.journeytodependencyinjection.screens.common.activities.BaseActivity;
 import com.techyourchance.journeytodependencyinjection.screens.common.dialogs.DialogsManager;
 import com.techyourchance.journeytodependencyinjection.screens.common.dialogs.ServerErrorDialogFragment;
 import com.techyourchance.journeytodependencyinjection.screens.common.mvcviews.ViewMvcFactory;
 import com.techyourchance.journeytodependencyinjection.screens.common.viewmodel.ViewModelFactory;
 import com.techyourchance.journeytodependencyinjection.screens.questiondetails.QuestionDetailsActivity;

 import java.util.List;

 import javax.inject.Inject;

 public class QuestionsListActivity extends BaseActivity implements
         QuestionsListViewMvc.Listener, FetchQuestionsListUseCase.Listener {

     private static final int NUM_OF_QUESTIONS_TO_FETCH = 20;

     @Inject FetchQuestionsListUseCase mFetchQuestionsListUseCase;
     @Inject DialogsManager mDialogsManager;
     @Inject ViewMvcFactory mViewMvcFactory;
     @Inject ViewModelFactory mViewModelFactory;

     private QuestionsListViewMvc mViewMvc;

     private QuestionsListViewModel mQuestionsListViewModel;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         getPresentationComponent().inject(this);

         mViewMvc = mViewMvcFactory.newInstance(QuestionsListViewMvc.class, null);

         mQuestionsListViewModel = ViewModelProviders.of(this, mViewModelFactory)
                 .get(QuestionsListViewModel.class);

         setContentView(mViewMvc.getRootView());

     }

     @Override
     protected void onStart() {
         super.onStart();
         mViewMvc.registerListener(this);
         mFetchQuestionsListUseCase.registerListener(this);

         if (mQuestionsListViewModel.getQuestions().isEmpty()) {
             mFetchQuestionsListUseCase.fetchLastActiveQuestionsAndNotify(NUM_OF_QUESTIONS_TO_FETCH);
             Toast.makeText(this, "fetching from use case", Toast.LENGTH_SHORT).show();
         } else {
             mViewMvc.bindQuestions(mQuestionsListViewModel.getQuestions());
             Toast.makeText(this, "from ViewModel", Toast.LENGTH_SHORT).show();
         }
     }

     @Override
     protected void onStop() {
         super.onStop();
         mViewMvc.unregisterListener(this);
         mFetchQuestionsListUseCase.unregisterListener(this);
     }

     @Override
     public void onFetchOfQuestionsSucceeded(List<Question> questions) {
         mQuestionsListViewModel.setQuestions(questions);
         mViewMvc.bindQuestions(questions);
     }

     @Override
     public void onFetchOfQuestionsFailed() {
         mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(), "");
     }

     @Override
     public void onQuestionClicked(Question question) {
         QuestionDetailsActivity.start(QuestionsListActivity.this, question.getId());
     }
 }
