package com.example.android.brainofbritain;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final String STATE_USERSCORE = "score";
    static final String STATE_RESULTS_MESSAGE = "resultsMessage";
    static final String USER_Q1_ANSWER = "userQ1Answer";
    // create array to store View Id'd of correct answers
    public RadioButton correctAnswers[] = new RadioButton[9];
    public RadioGroup radioGroupArray[] = new RadioGroup[9];
    int score;
    String resultsMessage;
    String feedback;
    String freeTextAnswer;
    CheckBox checkbox1;
    CheckBox checkbox2;
    CheckBox checkbox3;
    CheckBox checkbox4;
    TextView resultsView;
    EditText userName;
    EditText missingWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correctAnswers[0] = (findViewById(R.id.q1_a2));
        correctAnswers[1] = (findViewById(R.id.q2_a1));
        correctAnswers[2] = (findViewById(R.id.q3_a4));
        correctAnswers[3] = (findViewById(R.id.q4_a3));
        correctAnswers[4] = (findViewById(R.id.q5_a2));
        correctAnswers[5] = (findViewById(R.id.q7_a3));
        correctAnswers[6] = (findViewById(R.id.q6_a3));
        correctAnswers[7] = (findViewById(R.id.q8_a2));
        correctAnswers[8] = (findViewById(R.id.q10_a1));

        radioGroupArray[0] = findViewById(R.id.radioGroupQ1);
        radioGroupArray[1] = findViewById(R.id.radioGroupQ2);
        radioGroupArray[2] = findViewById(R.id.radioGroupQ3);
        radioGroupArray[3] = findViewById(R.id.radioGroupQ4);
        radioGroupArray[4] = findViewById(R.id.radioGroupQ5);
        radioGroupArray[5] = findViewById(R.id.radioGroupQ6);
        radioGroupArray[6] = findViewById(R.id.radioGroupQ7);
        radioGroupArray[7] = findViewById(R.id.radioGroupQ8);
        radioGroupArray[8] = findViewById(R.id.radioGroupQ10);

        checkbox1 = findViewById(R.id.q9_checkBox1);
        checkbox2 = findViewById(R.id.q9_checkBox2);
        checkbox3 = findViewById(R.id.q9_checkBox3);
        checkbox4 = findViewById(R.id.q9_checkBox4);
        resultsView = findViewById(R.id.result_view);
        userName = findViewById(R.id.enter_name_view);
        missingWord = findViewById(R.id.missing_word_view);

        // Find the View that shows the seeCorrectAnswersButton
        Button seeAnswersButton = findViewById(R.id.seeCorrectAnswers);

        // Set a click listener on that View
        seeAnswersButton.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent AnswersIntent = new Intent(MainActivity.this, CorrectAnswersActivity.class);
                startActivity(AnswersIntent);
            }
        });


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current score state
        savedInstanceState.putInt(STATE_USERSCORE, score);
        savedInstanceState.putString(STATE_RESULTS_MESSAGE, resultsView.getText().toString());

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        score = savedInstanceState.getInt(STATE_USERSCORE);
        resultsMessage = savedInstanceState.getString(STATE_RESULTS_MESSAGE);

        //finally display the message that we received from savedInstanceStae
        resultsView.setText(resultsMessage);
    }

    /**
     * This method removes focus from edit text views and hides the keyboard when user clicks out
     * of the edit text view
     */

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) { // if click is detected
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect); // create rectangle matching the EditText View
                // if co-ordinates of the click are outside the EditText view
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0); // hide keyboard
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * This method checks whether the correct Radio Button answers have been selected by the user,
     * and updates the score accordingly.
     */
    public void checkCorrectRadioButtonAnswers() {
        for (int i = 0; i < 9; i++) {
            RadioButton radioButton = correctAnswers[i];
            if (radioButton.isChecked()) {
                score++;
            }
        }
    }

    /**
     * This method checks to see if both correct answers have been entered for the checkbox question.
     */
    public void checkCorrectCheckBoxAnswer() {
        if ((checkbox1.isChecked() && checkbox3.isChecked())
                && (!checkbox2.isChecked() && !checkbox4.isChecked())) {
            score++;

        }
    }

    /**
     * This method checks to see if two answers have been entered for the checkbox question.
     */
    public Boolean hasAnsweredCheckBoxQuestion() {
        return ((checkbox1.isChecked() && checkbox2.isChecked()) || (checkbox1.isChecked() && checkbox3.isChecked()
                || (checkbox1.isChecked() && checkbox4.isChecked() || (checkbox2.isChecked() && checkbox3.isChecked()
                || (checkbox2.isChecked() && checkbox4.isChecked() || (checkbox3.isChecked() && checkbox4.isChecked()))))));
    }

    /**
     * This method checks to see if missing word is entered correctly.
     */
    public void checkMissingWordAnswer() {
        String correctWord = "victorious";
        if (missingWord.getText().toString().equalsIgnoreCase(correctWord)) {
            score++;
        }
    }

    /**
     * This method determines if the correct radio button has been selected for each question
     * and returns the final score.
     *
     * @return the users final score
     */
    private int calculateScore() {
        score = 0;
        // check if user selected correct answer to RadioButton Questions
        checkCorrectRadioButtonAnswers();
        // check if user selected correct answer to Q9
        checkCorrectCheckBoxAnswer();
        // check if user selected correct answer to Q11
        checkMissingWordAnswer();

        return score;
    }

    /**
     * This method is called when the submit answers button is clicked.
     */
    public void displayResult(View view) {
        // check if all the checkbox questions have been answered
        Boolean areAllRadioQsAnswered = CheckAllRadioQuestionsAnswered();
        // check is user has answered free text question
        freeTextAnswer = missingWord.getText().toString();
        String name = userName.getText().toString();
        if (!areAllRadioQsAnswered || freeTextAnswer.equals("") || !hasAnsweredCheckBoxQuestion()) {
            //inflate and show customized toast message
            displayErrorToast(getResources().getString(R.string.notAllQsAnswered));
            return;
        }
        if (TextUtils.isEmpty(name)) {
            displayErrorToast(getResources().getString(R.string.noNameEntered));
            return;
        } else {
            int result = calculateScore();

            if (result <= 3) {
                feedback = getResources().getString(R.string.feedbackBad);
            } else if (result > 3 && result <= 7) {
                feedback = getResources().getString(R.string.feedbackMedium);
            } else if (result > 7 && result <= 10) {
                feedback = getResources().getString(R.string.feedbackGood);
            } else if (result == 11) {
                feedback = getResources().getString(R.string.feedbackPerfect);
            }

            resultsMessage = name
                    + getResources().getString(R.string.resultMessagePart1) + " "
                    + result + " " + getResources().getString(R.string.resultMessagePart2) + "\n" + feedback;

            //inflate and show customized toast message
            displayResultsToast();
        }

    }

    /**
     * This method displays toast with customized message based on user's score.
     */
    private void displayResultsToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.results_toast,
                (ViewGroup) findViewById(R.id.results_toast_container));

        TextView text = (TextView) layout.findViewById(R.id.results_toast_view);
        text.setText(resultsMessage);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    /**
     * This method displays toast with error message if not all questions answered.
     *
     * @param errorMessage String containing error message to display in the toast
     */
    private void displayErrorToast(String errorMessage) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.error_toast,
                (ViewGroup) findViewById(R.id.error_toast_container));

        TextView text = layout.findViewById(R.id.results_toast_view);
        text.setText(errorMessage);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    /**
     * This method checks that the user has answered all Radio Button questions.
     */
    private boolean CheckAllRadioQuestionsAnswered() {
        for (int i = 0; i < radioGroupArray.length; i++) {
            if (radioGroupArray[i].getCheckedRadioButtonId() == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method resets all the Radio Buttons in a given Radio Group.
     *
     * @param radioGroupID the id of a radio group
     */
    private void resetQuestions(int radioGroupID) {
        RadioGroup radioGroup = findViewById(radioGroupID);
        radioGroup.clearCheck();
    }

    /**
     * This method resets all the CheckBoxes in the quiz.
     */
    private void resetCheckBoxes() {
        checkbox1.setChecked(false);
        checkbox2.setChecked(false);
        checkbox3.setChecked(false);
        checkbox4.setChecked(false);
    }

    /**
     * This method is called when the reset quiz button is clicked. Method resets all Radio Buttons
     * and resets score to 0.
     */
    public void resetQuiz(View view) {
        resetQuestions(R.id.radioGroupQ1);
        resetQuestions(R.id.radioGroupQ2);
        resetQuestions(R.id.radioGroupQ3);
        resetQuestions(R.id.radioGroupQ4);
        resetQuestions(R.id.radioGroupQ5);
        resetQuestions(R.id.radioGroupQ6);
        resetQuestions(R.id.radioGroupQ7);
        resetQuestions(R.id.radioGroupQ8);
        resetCheckBoxes();
        resetQuestions(R.id.radioGroupQ10);

        score = 0;

        resultsView.setText(getString(R.string.initial_result_message));
        userName.setText(getString(R.string.user_name));
    }

}
