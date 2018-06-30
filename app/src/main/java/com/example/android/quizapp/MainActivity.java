package com.example.android.quizapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //The user's Provided name. It falls back to "User" by default in case someone doesn't provide one
    private String mName = "User";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Checks the user's answers and displays a toast message with the results of the quiz
     *
     * @param view is the view that has been clicked
     */
    public void viewResult(View view) {
        //A toast message with the user's result. It calls the displayText() method to generate the string to be displayed
        Toast.makeText(this, displayText(), Toast.LENGTH_LONG).show();
    }

    /**
     * Checks the user's answers and composes an email with the results of the quiz
     * Uses an Implicit Intent to handle the sending of the mail
     */
    public void emailResult(View view) {
        //we want to add some credits to the Developer and All the partners
        String emailText = displayText();

        emailText += "\n\nDeveloped by: Ronald Mutegeki" +
                "\nAndroid Basics - Google Scolarship" +
                "\nThanks to Udacity and ALC";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("*/*");
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Quiz Results");
        intent.putExtra(Intent.EXTRA_TEXT, emailText);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * @return int score, the score of the user based on the questions that have been answered and passed.
     */
    private int getScore() {
        int score = 0;
        if (passedQuestion1())
            score += 1;
        if (passedQuestion2())
            score += 1;
        if (passedQuestion3())
            score += 1;
        if (passedQuestion4())
            score += 1;
        if (passedQuestion5())
            score += 1;

        return score;
    }

    /**
     * @return String dispalyText, the Text with the result of the user
     */

    private String displayText() {

        String q1 = "Failed";
        String q2 = "Failed";
        String q3 = "Failed";
        String q4 = "Failed";
        String q5 = "Failed";

        if (passedQuestion1())
            q1 = "Passed";
        if (passedQuestion2())
            q2 = "Passed";
        if (passedQuestion3())
            q3 = "Passed";
        if (passedQuestion4())
            q4 = "Passed";
        if (passedQuestion5())
            q5 = "Passed";

        //Retrieve the name provided by the user in the name text field
        EditText nameText = findViewById(R.id.name);
        if (nameText.getText().toString().length() >= 1)
            mName = nameText.getText().toString();

        RadioButton enjoyed = findViewById(R.id.enjoyed_yes);
        RadioButton enjoyedNot = findViewById(R.id.enjoyed_no);

        String displayText;

        //check whether the user enjoyed the Quiz or not and adjusts the displayed message accordingly
        if (enjoyed.isChecked()) {
            displayText = "Hello " + mName + ", \n\nYou Scored " + getScore() + "/5. \nI am glad that you enjoyed my Quiz.\n\nDetailed Score:\n" +
                    "Qn. 1: " + q1 + ".\n" +
                    "Qn. 2: " + q2 + ".\n" +
                    "Qn. 3: " + q3 + ".\n" +
                    "Qn. 4: " + q4 + ".\n" +
                    "Qn. 5: " + q5 + ".";
        } else if (enjoyedNot.isChecked()) {
            displayText = "Hello " + mName + ", \n\nYou Scored " + getScore() + "/5. \nI am sorry that you were not able to enjoy my Quiz. I'll Keep trying to improve it.\n\nDetailed Score:\n" +
                    "Qn. 1: " + q1 + ".\n" +
                    "Qn. 2: " + q2 + ".\n" +
                    "Qn. 3: " + q3 + ".\n" +
                    "Qn. 4: " + q4 + ".\n" +
                    "Qn. 5: " + q5 + ".";
        } else {
            displayText = "Hello " + mName + ", \n\nYou Scored " + getScore() + "/5. \nYou forgot to let me know how you feel about my Quiz though.\n\nDetailed Score:\n" +
                    "Qn. 1: " + q1 + ".\n" +
                    "Qn. 2: " + q2 + ".\n" +
                    "Qn. 3: " + q3 + ".\n" +
                    "Qn. 4: " + q4 + ".\n" +
                    "Qn. 5: " + q5 + ".";
        }

        return displayText;
    }

    /**
     * Checks whether user passed or failed question 1
     */
    private boolean passedQuestion1() {
        CheckBox q1_checkBox1 = findViewById(R.id.q1_checkbox1_correct);
        CheckBox q1_checkBox2 = findViewById(R.id.q1_checkbox2);
        CheckBox q1_checkBox3 = findViewById(R.id.q1_checkbox3_correct);
        CheckBox q1_checkBox4 = findViewById(R.id.q1_checkbox4);
        return q1_checkBox1.isChecked() && q1_checkBox3.isChecked() && !q1_checkBox2.isChecked() && !q1_checkBox4.isChecked();
    }

    /**
     * Checks whether user passed or failed question 2
     */
    private boolean passedQuestion2() {
        RadioButton q2RadioButton = findViewById(R.id.q2_option2_correct);
        return q2RadioButton.isChecked();
    }

    /**
     * Checks whether user passed or failed question 3
     */
    private boolean passedQuestion3() {
        RadioButton q3RadioButton = findViewById(R.id.q3_option4_correct);
        return q3RadioButton.isChecked();
    }

    /**
     * Checks whether user passed or failed question 4
     */
    private boolean passedQuestion4() {
        EditText q4Text = findViewById(R.id.q4);
        String q4Answer = q4Text.getText().toString();
        boolean a = false;
        if (q4Answer.contains("string"))
            a = false;
        else if (q4Answer.contains("String")) {
            if (q4Answer.toLowerCase().contains("s")) {
                if (q4Answer.contains("=")) {
                    if (q4Answer.toLowerCase().contains("\"i love java\"")) {
                        if (q4Answer.contains(";")) {
                            a = true;
                        }
                    }
                }
            }
        }

        return a;
    }

    /**
     * Checks whether user passed or failed question 5
     */
    private boolean passedQuestion5() {
        RadioButton q5RadioButton = findViewById(R.id.q5_option1_correct);
        return q5RadioButton.isChecked();
    }
}
