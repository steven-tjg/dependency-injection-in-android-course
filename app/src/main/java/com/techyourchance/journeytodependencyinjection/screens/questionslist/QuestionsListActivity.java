 package com.techyourchance.journeytodependencyinjection.screens.questionslist;

 import android.os.Bundle;
 import android.support.v4.app.FragmentManager;
 import android.support.v7.app.AppCompatActivity;
 import android.view.LayoutInflater;

 import com.techyourchance.journeytodependencyinjection.Constants;
 import com.techyourchance.journeytodependencyinjection.networking.QuestionsListResponseSchema;
 import com.techyourchance.journeytodependencyinjection.networking.StackoverflowApi;
 import com.techyourchance.journeytodependencyinjection.questions.FetchQuestionsListUseCase;
 import com.techyourchance.journeytodependencyinjection.questions.Question;
 import com.techyourchance.journeytodependencyinjection.screens.common.ServerErrorDialogFragment;
 import com.techyourchance.journeytodependencyinjection.screens.questiondetails.QuestionDetailsActivity;

 import java.util.List;

 import retrofit2.Call;
 import retrofit2.Callback;
 import retrofit2.Response;
 import retrofit2.Retrofit;
 import retrofit2.converter.gson.GsonConverterFactory;

 public class QuestionsListActivity extends AppCompatActivity implements
         QuestionsListViewMvc.Listener, FetchQuestionsListUseCase.Listener {

     private static final int NUM_OF_QUESTIONS_TO_FETCH = 20;

     private FetchQuestionsListUseCase mFetchQuestionsListUseCase;

     private QuestionsListViewMvc mViewMvc;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);

         mViewMvc = new QuestionsListViewMvcImpl(LayoutInflater.from(this), null);

         setContentView(mViewMvc.getRootView());

         mFetchQuestionsListUseCase = new FetchQuestionsListUseCase();

     }

     @Override
     protected void onStart() {
         super.onStart();
         mViewMvc.registerListener(this);
         mFetchQuestionsListUseCase.registerListener(this);

         mFetchQuestionsListUseCase.fetchLastActiveQuestionsAndNotify(NUM_OF_QUESTIONS_TO_FETCH);
     }

     @Override
     protected void onStop() {
         super.onStop();
         mViewMvc.unregisterListener(this);
         mFetchQuestionsListUseCase.unregisterListener(this);
     }

     @Override
     public void onFetchOfQuestionsSucceeded(List<Question> questions) {
         mViewMvc.bindQuestions(questions);
     }

     @Override
     public void onFetchOfQuestionsFailed() {
         FragmentManager fragmentManager = getSupportFragmentManager();
         fragmentManager.beginTransaction()
                 .add(ServerErrorDialogFragment.newInstance(), null)
                 .commitAllowingStateLoss();
     }

     @Override
     public void onQuestionClicked(Question question) {
         QuestionDetailsActivity.start(QuestionsListActivity.this, question.getId());
     }
 }
