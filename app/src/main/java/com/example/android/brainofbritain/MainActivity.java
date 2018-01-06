package com.example.android.brainofbritain;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final String STATE_USERSCORE = "score";
    static final String STATE_RESULTS_MESSAGE = "resultsMessage";

    int score;
    String resultsMessage;

    CheckBox checkbox1;
    CheckBox checkbox2;
    CheckBox checkbox3;
    CheckBox checkbox4;
    TextView resultsView;
    EditText userName;

    // create array to store View Id'd of correct answers
    RadioButton correctAnswers[] = new RadioButton[9];

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

        checkbox1 = findViewById(R.id.q9_checkBox1);
        checkbox2 = findViewById(R.id.q9_checkBox2);
        checkbox3 = findViewById(R.id.q9_checkBox3);
        checkbox4 = findViewById(R.id.q9_checkBox4);
        resultsView = findViewById(R.id.result_view);
        userName = findViewById(R.id.enter_name_view);
        resultsMessage = resultsView.getText().toString();

        if (resultsMessage == null || resultsMessage.isEmpty()) {
            resultsMessage = getString(R.string.initial_result_message);
        }
        resultsView.setText(resultsMessage);

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
    }

    /**
     * This method checks whether the correct Radio Button answers have been selected by the user,
     * and updates the score accordingly.
     */
    public void checkCorrectRadioButtonAnswers() {
        for (int i = 0; i < 9; i++) {
            RadioButton radioButton = correctAnswers[0];
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

        return score;
    }

    /**
     * This method is called when the submit answers button is clicked.
     */
    public void displayResult(View view) {
        int result = calculateScore();
        String name = userName.getText().toString();
        resultsMessage = name
                + getResources().getString(R.string.resultMessagePart1) + " "
                + result + " " + getResources().getString(R.string.resultMessagePart2);
        resultsView.setText(resultsMessage);
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
