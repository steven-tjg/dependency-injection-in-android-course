 package com.techyourchance.journeytodependencyinjection.screens.questiondetails;

 import android.arch.lifecycle.ViewModelProviders;
 import android.content.Context;
 import android.content.Intent;
 import android.os.Bundle;

 import com.techyourchance.journeytodependencyinjection.questions.FetchQuestionDetailsUseCase;
 import com.techyourchance.journeytodependencyinjection.questions.QuestionDetails;
 import com.techyourchance.journeytodependencyinjection.screens.common.activities.BaseActivity;
 import com.techyourchance.journeytodependencyinjection.screens.common.dialogs.DialogsManager;
 import com.techyourchance.journeytodependencyinjection.screens.common.dialogs.ServerErrorDialogFragment;
 import com.techyourchance.journeytodependencyinjection.screens.common.mvcviews.ViewMvcFactory;
 import com.techyourchance.journeytodependencyinjection.screens.common.viewmodel.ViewModelFactory;

 import javax.inject.Inject;

 public class QuestionDetailsActivity extends BaseActivity implements
         QuestionDetailsViewMvc.Listener, QuestionDetailsViewModel.Listener {

     public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";

     public static void start(Context context, String questionId) {
         Intent intent = new Intent(context, QuestionDetailsActivity.class);
         intent.putExtra(EXTRA_QUESTION_ID, questionId);
         context.startActivity(intent);
     }

     @Inject DialogsManager mDialogsManager;
     @Inject ViewMvcFactory mViewMvcFactory;
     @Inject ViewModelFactory mViewModelFactory;

     private String mQuestionId;

     private QuestionDetailsViewMvc mViewMvc;

     private QuestionDetailsViewModel mQuestionDetailsViewModel;


     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         getPresentationComponent().inject(this);

         mViewMvc = mViewMvcFactory.newInstance(QuestionDetailsViewMvc.class, null);

         mQuestionDetailsViewModel = ViewModelProviders.of(this, mViewModelFactory)
                 .get(QuestionDetailsViewModel.class);

         setContentView(mViewMvc.getRootView());

         //noinspection ConstantConditions
         mQuestionId = getIntent().getExtras().getString(EXTRA_QUESTION_ID);
     }

     @Override
     protected void onStart() {
         super.onStart();
         mViewMvc.registerListener(this);
         mQuestionDetailsViewModel.registerListener(this);

         mQuestionDetailsViewModel.fetchQuestionDetailsAndNotify(mQuestionId);
     }

     @Override
     protected void onStop() {
         super.onStop();
         mViewMvc.unregisterListener(this);
         mQuestionDetailsViewModel.unregisterListener(this);
     }

     @Override
     public void onQuestionDetailsFetched(QuestionDetails questionDetails) {
         mViewMvc.bindQuestion(questionDetails);
     }

     @Override
     public void onQuestionDetailsFetchFailed() {
         mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(), "");
     }
 }
