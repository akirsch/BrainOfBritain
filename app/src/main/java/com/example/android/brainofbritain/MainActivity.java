package com.example.android.brainofbritain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int score;
    boolean isAnsweredCorrectlyQ1 = false;
    boolean isAnsweredCorrectlyQ2 = false;
    boolean isAnsweredCorrectlyQ3 = false;
    boolean isAnsweredCorrectlyQ4 = false;
    boolean isAnsweredCorrectlyQ5 = false;
    boolean isAnsweredCorrectlyQ6 = false;
    boolean isAnsweredCorrectlyQ7 = false;
    boolean isAnsweredCorrectlyQ8 = false;
    boolean isAnsweredCorrectlyQ9 = false;
    boolean isAnsweredCorrectlyQ10 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method checks whether the correct answer has been selected by the user, and updates the
     * score accordingly.
     *
     * @param viewId the id of a radio button
     */
    public void checkCorrectAnswer (int viewId, boolean isAnsweredCorrectly) {
        RadioButton radioButton = findViewById(viewId);
        if (radioButton.isChecked()) {
            if (!isAnsweredCorrectly) {
                score ++;
                isAnsweredCorrectly = true;
            }
        }
    }


    /**
     * This method checks to see if both correct answers have been entered for the checkbox question.
     */

    public void checkCorrectCheckBoxAnswer() {
        if (!isAnsweredCorrectlyQ9) {
            CheckBox checkbox1 =  findViewById(R.id.q9_checkBox1);
            CheckBox checkbox2 =  findViewById(R.id.q9_checkBox2);
            CheckBox checkbox3 =  findViewById(R.id.q9_checkBox3);
            CheckBox checkbox4 =  findViewById(R.id.q9_checkBox4);

            if ((checkbox1.isChecked() && checkbox3.isChecked())
                    && (!checkbox2.isChecked() && !checkbox4.isChecked())) {
                score++;
                isAnsweredCorrectlyQ9 = true;
            }
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
        // check if user selected correct answer to Q1
        checkCorrectAnswer(R.id.q1_a2, isAnsweredCorrectlyQ1);
        // check if user selected correct answer to Q2
        checkCorrectAnswer(R.id.q2_a1, isAnsweredCorrectlyQ2);
        // check if user selected correct answer to Q3
        checkCorrectAnswer(R.id.q3_a4, isAnsweredCorrectlyQ3);
        // check if user selected correct answer to Q4
        checkCorrectAnswer(R.id.q4_a3, isAnsweredCorrectlyQ4);
        // check if user selected correct answer to Q5
        checkCorrectAnswer(R.id.q5_a2, isAnsweredCorrectlyQ5);
        // check if user selected correct answer to Q6
        checkCorrectAnswer(R.id.q6_a3, isAnsweredCorrectlyQ6);
        // check if user selected correct answer to Q7
        checkCorrectAnswer(R.id.q7_a3, isAnsweredCorrectlyQ7);
        // check if user selected correct answer to Q8
        checkCorrectAnswer(R.id.q8_a2, isAnsweredCorrectlyQ8);
        // check if user selected correct answer to Q9
       checkCorrectCheckBoxAnswer();
        // check if user selected correct answer to Q10
        checkCorrectAnswer(R.id.q10_a1, isAnsweredCorrectlyQ10);

        return score;
    }

    /**
     * This method is called when the submit answers button is clicked.
     */
    public void displayResult (View view){
        TextView textView = findViewById(R.id.result_view);
        int result = calculateScore();
        EditText enterName = (EditText) findViewById(R.id.enter_name_view);
        String name = enterName.getText().toString();
        String resultMessage = name
                + getResources().getString(R.string.resultMessagePart1) + " " +
                + result + " " + getResources().getString(R.string.resultMessagePart2);
        textView.setText(resultMessage);
    }

    /**
     * This method resets all the Radio Buttons in a given Radio Group.
     *
     * @param radioGroupID the id of a radio group
     */
    private void resetQuestions (int radioGroupID) {
        RadioGroup radioGroup = findViewById(radioGroupID);
        radioGroup.clearCheck();
    }

    /**
     * This method resets all the CheckBoxes in the quiz.
     *
     */

    private void resetCheckBoxes(){
        CheckBox checkbox1 = findViewById(R.id.q9_checkBox1);
        CheckBox checkbox2 = findViewById(R.id.q9_checkBox2);
        CheckBox checkbox3 = findViewById(R.id.q9_checkBox3);
        CheckBox checkbox4 = findViewById(R.id.q9_checkBox4);

        checkbox1.setChecked(false);
        checkbox2.setChecked(false);
        checkbox3.setChecked(false);
        checkbox4.setChecked(false);

    }

    /**
     * This method is called when the reset quiz button is clicked. Method resets all Radio Buttons
     * and resets score to 0.
     */
    public void resetQuiz (View view) {
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

        TextView textView = findViewById(R.id.result_view);
        textView.setText(getString(R.string.initial_result_message));
        EditText editText = findViewById(R.id.enter_name_view);
        editText.setText(getString(R.string.user_name));
    }

}
