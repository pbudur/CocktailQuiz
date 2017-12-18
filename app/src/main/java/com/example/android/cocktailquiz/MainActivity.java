/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.cocktailquiz;
 */

package com.example.android.cocktailquiz;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays a Quiz: 6 questions, each with 5 answers.
 */
public class MainActivity extends AppCompatActivity {

    int question_nb = 1;
    String name = "";
    String ageStr = "";
    int age = 1;
    int nbQuestions = 6;
    int nbCorrectAnswers = 0;
    String summaryMessage = "";
    String answerButtonMessage = "";
    int lastAnswer = 0;
    boolean buttonClicked = false;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the Start QUIZ button is clicked.
     */
    public void startQuiz(View view) {

        EditText personsName = (EditText) findViewById(R.id.name);
        name = personsName.getText().toString();
        EditText personsAge = (EditText) findViewById(R.id.age);
        ageStr = personsAge.getText().toString();

        /**
         * The summary message that will be displayed at the end is constructed starting from the Login screen
         */

        String[] allQuestions = getResources().getStringArray(R.array.questions);
        String[] answers1 = getResources().getStringArray(R.array.answer1);

        /**
         * Add a check for Name not to be empty AND the Age to be between 18 and 100; if not so, display a corresponding Toast
         */
        if (TextUtils.isEmpty(ageStr)) {
            Toast myToast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.min_conditions), Toast.LENGTH_SHORT);
            myToast.show();
            myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

        } else {
            age = Integer.parseInt(ageStr);
            if (age < 18 | age > 100 | TextUtils.isEmpty(name)) {

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.min_conditions), Toast.LENGTH_SHORT).show();
            } else {

                /**
                 * Only in case the checks pass then remove (set them to GONE) the current edit text fields
                 * and the Start Quiz button.
                 */
                summaryMessage = getString(R.string.result_tester) + "\n";
                summaryMessage += getString(R.string.name_tester, name) + "\n";
                summaryMessage += getString(R.string.age_tester, age) + "\n\n";

                TextView questionText = (TextView) findViewById(R.id.question_text);
                questionText.setText(allQuestions[question_nb - 1]);

                personsName.setVisibility(View.GONE);
                personsAge.setVisibility(View.GONE);

                Button startQuiz = (Button) findViewById(R.id.start);
                startQuiz.setVisibility(View.GONE);

                /**
                 * Here are the check boxes for the 1st Question and the Submit button are displayed (made VISIBLE)
                 * Also the check buttons are set default to unchecked.
                 */

                CheckBox cbAnswerA = (CheckBox) findViewById(R.id.cb_answer_a);
                cbAnswerA.setText(answers1[0]);
                cbAnswerA.setChecked(false);
                cbAnswerA.setVisibility(View.VISIBLE);
                CheckBox cbAnswerB = (CheckBox) findViewById(R.id.cb_answer_b);
                cbAnswerB.setText(answers1[1]);
                cbAnswerB.setChecked(false);
                cbAnswerB.setVisibility(View.VISIBLE);
                CheckBox cbAnswerC = (CheckBox) findViewById(R.id.cb_answer_c);
                cbAnswerC.setText(answers1[2]);
                cbAnswerC.setChecked(false);
                cbAnswerC.setVisibility(View.VISIBLE);
                CheckBox cbAnswerD = (CheckBox) findViewById(R.id.cb_answer_d);
                cbAnswerD.setText(answers1[3]);
                cbAnswerD.setChecked(false);
                cbAnswerD.setVisibility(View.VISIBLE);
                CheckBox cbAnswerE = (CheckBox) findViewById(R.id.cb_answer_e);
                cbAnswerE.setText(answers1[4]);
                cbAnswerE.setChecked(false);
                cbAnswerE.setVisibility(View.VISIBLE);

                Button submitResults = (Button) findViewById(R.id.submit_results);
                submitResults.setVisibility(View.VISIBLE);

                RadioGroup rbAnswerGroup = (RadioGroup) findViewById(R.id.rb_group);
                rbAnswerGroup.clearCheck();

                Button sendEmail = (Button) findViewById(R.id.send_email);
                Button restartQuiz = (Button) findViewById(R.id.restart_quiz);
                sendEmail.setVisibility(View.GONE);
                restartQuiz.setVisibility(View.GONE);

                Button bAnswerA = (Button) findViewById(R.id.b_answer_a);
                bAnswerA.getBackground().clearColorFilter();
                Button bAnswerB = (Button) findViewById(R.id.b_answer_b);
                bAnswerB.getBackground().clearColorFilter();
                Button bAnswerC = (Button) findViewById(R.id.b_answer_c);
                bAnswerC.getBackground().clearColorFilter();
                Button bAnswerD = (Button) findViewById(R.id.b_answer_d);
                bAnswerD.getBackground().clearColorFilter();
                Button bAnswerE = (Button) findViewById(R.id.b_answer_e);
                bAnswerE.getBackground().clearColorFilter();
            }
        }
    }

    /**
     * This method is called to submit the answer for each question.
     */

    public void submitResults(View view) throws InterruptedException {

        String[] allQuestions = getResources().getStringArray(R.array.questions);
        String[] answers2 = getResources().getStringArray(R.array.answer2);
        String[] answers3 = getResources().getStringArray(R.array.answer3);
        String[] answers4 = getResources().getStringArray(R.array.answer4);
        String[] answers5 = getResources().getStringArray(R.array.answer5);
        String[] answers6 = getResources().getStringArray(R.array.answer6);

        TextView questionText = (TextView) findViewById(R.id.question_text);

        CheckBox cbAnswerA = (CheckBox) findViewById(R.id.cb_answer_a);
        CheckBox cbAnswerB = (CheckBox) findViewById(R.id.cb_answer_b);
        CheckBox cbAnswerC = (CheckBox) findViewById(R.id.cb_answer_c);
        CheckBox cbAnswerD = (CheckBox) findViewById(R.id.cb_answer_d);
        CheckBox cbAnswerE = (CheckBox) findViewById(R.id.cb_answer_e);

        Button submitResults = (Button) findViewById(R.id.submit_results);
        Button restartQuiz = (Button) findViewById(R.id.restart_quiz);

        RadioGroup rbAnswerGroup = (RadioGroup) findViewById(R.id.rb_group);

        RadioButton rbAnswerA = (RadioButton) findViewById(R.id.rb_answer_a);
        RadioButton rbAnswerB = (RadioButton) findViewById(R.id.rb_answer_b);
        RadioButton rbAnswerC = (RadioButton) findViewById(R.id.rb_answer_c);
        RadioButton rbAnswerD = (RadioButton) findViewById(R.id.rb_answer_d);
        RadioButton rbAnswerE = (RadioButton) findViewById(R.id.rb_answer_e);

        Button bAnswerA = (Button) findViewById(R.id.b_answer_a);
        Button bAnswerB = (Button) findViewById(R.id.b_answer_b);
        Button bAnswerC = (Button) findViewById(R.id.b_answer_c);
        Button bAnswerD = (Button) findViewById(R.id.b_answer_d);
        Button bAnswerE = (Button) findViewById(R.id.b_answer_e);


        Button bSendEmail = (Button) findViewById(R.id.send_email);

        switch (question_nb) {
            case 1:

                cbAnswerA.setVisibility(View.GONE);
                cbAnswerB.setVisibility(View.GONE);
                cbAnswerC.setVisibility(View.GONE);
                cbAnswerD.setVisibility(View.GONE);
                cbAnswerE.setVisibility(View.GONE);

                boolean isCbAnswerA = cbAnswerA.isChecked();
                boolean isCbAnswerB = cbAnswerB.isChecked();
                boolean isCbAnswerC = cbAnswerC.isChecked();
                boolean isCbAnswerD = cbAnswerD.isChecked();
                boolean isCbAnswerE = cbAnswerE.isChecked();

                if (isCbAnswerA & isCbAnswerB & isCbAnswerC & !isCbAnswerD & isCbAnswerE) {
                    summaryMessage += getString(R.string.correct_answer, question_nb) + "\n";
                    nbCorrectAnswers = nbCorrectAnswers + 1;

                } else {
                    summaryMessage += getString(R.string.wrong_answer, question_nb) + "\n";
                }

                question_nb = question_nb + 1;

                questionText.setText(allQuestions[question_nb - 1]);

                submitResults.setVisibility(View.VISIBLE);

                rbAnswerA.setText(answers2[0]);
                rbAnswerA.setVisibility(View.VISIBLE);

                rbAnswerB.setText(answers2[1]);
                rbAnswerB.setVisibility(View.VISIBLE);

                rbAnswerC.setText(answers2[2]);
                rbAnswerC.setVisibility(View.VISIBLE);

                rbAnswerD.setText(answers2[3]);
                rbAnswerD.setVisibility(View.VISIBLE);

                rbAnswerE.setText(answers2[4]);
                rbAnswerE.setVisibility(View.VISIBLE);

                break;
            case 2:
                if (rbAnswerGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), getString(R.string.select_one), Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    if (rbAnswerD.isChecked()) {
                        // is checked
                        summaryMessage += getString(R.string.correct_answer, question_nb) + "\n";
                        nbCorrectAnswers = nbCorrectAnswers + 1;

                    } else {
                        summaryMessage += getString(R.string.wrong_answer, question_nb) + "\n";


                    }
                }
                question_nb = question_nb + 1;
                questionText.setText(allQuestions[question_nb - 1]);

                rbAnswerGroup.clearCheck();
                rbAnswerA.setText(answers3[0]);
                rbAnswerB.setText(answers3[1]);
                rbAnswerC.setText(answers3[2]);
                rbAnswerD.setText(answers3[3]);
                rbAnswerE.setText(answers3[4]);

                break;
            case 3:
                Log.i("MainActivity.java", (rbAnswerGroup.getCheckedRadioButtonId() + ""));
                if (rbAnswerGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), getString(R.string.select_one), Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    if (rbAnswerB.isChecked()) {
                        // is checked
                        summaryMessage += getString(R.string.correct_answer, question_nb) + "\n";
                        nbCorrectAnswers = nbCorrectAnswers + 1;
                    } else {
                        summaryMessage += getString(R.string.wrong_answer, question_nb) + "\n";

                    }
                }

                question_nb = question_nb + 1;
                questionText.setText(allQuestions[question_nb - 1]);

                rbAnswerGroup.clearCheck();
                rbAnswerA.setText(answers4[0]);
                rbAnswerB.setText(answers4[1]);
                rbAnswerC.setText(answers4[2]);
                rbAnswerD.setText(answers4[3]);
                rbAnswerE.setText(answers4[4]);
                break;
            case 4:

                if (rbAnswerGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), getString(R.string.select_one), Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    if (rbAnswerB.isChecked()) {
                        // is checked
                        summaryMessage += getString(R.string.correct_answer, question_nb) + "\n";
                        nbCorrectAnswers = nbCorrectAnswers + 1;

                    } else {
                        summaryMessage += getString(R.string.wrong_answer, question_nb) + "\n";

                    }
                }

                question_nb = question_nb + 1;
                questionText.setText(allQuestions[question_nb - 1]);

                rbAnswerGroup.clearCheck();
                rbAnswerA.setText(answers5[0]);
                rbAnswerB.setText(answers5[1]);
                rbAnswerC.setText(answers5[2]);
                rbAnswerD.setText(answers5[3]);
                rbAnswerE.setText(answers5[4]);
                break;
            case 5:

                if (rbAnswerGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), getString(R.string.select_one), Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    if (rbAnswerD.isChecked()) {
                        // is checked
                        summaryMessage += getString(R.string.correct_answer, question_nb) + "\n";
                        nbCorrectAnswers = nbCorrectAnswers + 1;

                    } else {
                        summaryMessage += getString(R.string.wrong_answer, question_nb) + "\n";

                    }
                }

                question_nb = question_nb + 1;
                questionText.setText(allQuestions[question_nb - 1]);

                rbAnswerGroup.clearCheck();
                rbAnswerA.setVisibility(View.GONE);
                rbAnswerB.setVisibility(View.GONE);
                rbAnswerC.setVisibility(View.GONE);
                rbAnswerD.setVisibility(View.GONE);
                rbAnswerE.setVisibility(View.GONE);


                bAnswerA.setText(answers6[0]);
                bAnswerA.setVisibility(View.VISIBLE);

                bAnswerB.setText(answers6[1]);
                bAnswerB.setVisibility(View.VISIBLE);

                bAnswerC.setText(answers6[2]);
                bAnswerC.setVisibility(View.VISIBLE);

                bAnswerD.setText(answers6[3]);
                bAnswerD.setVisibility(View.VISIBLE);

                bAnswerE.setText(answers6[4]);
                bAnswerE.setVisibility(View.VISIBLE);
                if (age <= Integer.parseInt(answers6[0])) {
                    bAnswerA.setText(ageStr);
                } else if (age <= Integer.parseInt(answers6[1])) {
                    bAnswerB.setText(ageStr);
                } else if (age <= Integer.parseInt(answers6[2])) {
                    bAnswerC.setText(ageStr);
                } else if (age <= Integer.parseInt(answers6[3])) {
                    bAnswerD.setText(ageStr);
                } else {
                    bAnswerE.setText(ageStr);
                }
                break;
            case 6:
                if (!buttonClicked) {
                    Toast.makeText(getApplicationContext(), getString(R.string.select_one), Toast.LENGTH_SHORT).show();
                    break;
                }
                bAnswerA.setVisibility(View.GONE);
                bAnswerB.setVisibility(View.GONE);
                bAnswerC.setVisibility(View.GONE);
                bAnswerD.setVisibility(View.GONE);
                bAnswerE.setVisibility(View.GONE);
                submitResults.setVisibility(View.GONE);

                bSendEmail.setVisibility(View.VISIBLE);
                summaryMessage += answerButtonMessage;
                nbCorrectAnswers = nbCorrectAnswers + lastAnswer;
                summaryMessage += "\n" + getString(R.string.result_answers, nbCorrectAnswers, nbQuestions);
                questionText.setVisibility(View.VISIBLE);
                questionText.setText(summaryMessage);

                restartQuiz.setVisibility(View.VISIBLE);
                question_nb = 1;
                nbCorrectAnswers = 0;
                answerButtonMessage = "";
                lastAnswer = 0;
                buttonClicked = false;

                break;

            default:
                break;
        }
    }


    /**
     * This method is called when the - buttonA is clicked.
     */
    public void answerButtonA(View view) {
        buttonClicked = true;

        Button bAnswerA = (Button) findViewById(R.id.b_answer_a);
        onClick(bAnswerA);

        if (bAnswerA.getText() == ageStr) {
            answerButtonMessage = getString(R.string.correct_answer, question_nb) + "\n";
            lastAnswer = 1;
        } else {
            answerButtonMessage = getString(R.string.wrong_answer, question_nb) + "\n";
            lastAnswer = 0;

        }
    }

    /**
     * This method is called when the - buttonB is clicked.
     */
    public void answerButtonB(View view) {
        buttonClicked = true;
        Button bAnswerB = (Button) findViewById(R.id.b_answer_b);
        onClick(bAnswerB);

        if (bAnswerB.getText() == ageStr) {
            answerButtonMessage = getString(R.string.correct_answer, question_nb) + "\n";
            lastAnswer = 1;
        } else {
            answerButtonMessage = getString(R.string.wrong_answer, question_nb) + "\n";
            lastAnswer = 0;

        }
    }

    /**
     * This method is called when the - buttonC is clicked.
     */
    public void answerButtonC(View view) {
        buttonClicked = true;
        Button bAnswerC = (Button) findViewById(R.id.b_answer_c);
        onClick(bAnswerC);

        if (bAnswerC.getText() == ageStr) {
            answerButtonMessage = getString(R.string.correct_answer, question_nb) + "\n";
            lastAnswer = 1;
        } else {
            answerButtonMessage = getString(R.string.wrong_answer, question_nb) + "\n";
            lastAnswer = 0;

        }
    }

    /**
     * This method is called when the - buttonD is clicked.
     */
    public void answerButtonD(View view) {
        buttonClicked = true;
        Button bAnswerD = (Button) findViewById(R.id.b_answer_d);
        onClick(bAnswerD);

        if (bAnswerD.getText() == ageStr) {
            answerButtonMessage = getString(R.string.correct_answer, question_nb) + "\n";
            lastAnswer = 1;
        } else {
            answerButtonMessage = getString(R.string.wrong_answer, question_nb) + "\n";
            lastAnswer = 0;

        }
    }

    /**
     * This method is called when the - buttonE is clicked.
     */
    public void answerButtonE(View view) {
        buttonClicked = true;
        Button bAnswerE = (Button) findViewById(R.id.b_answer_e);
        onClick(bAnswerE);

        if (bAnswerE.getText() == ageStr) {
            answerButtonMessage = getString(R.string.correct_answer, question_nb) + "\n";
            lastAnswer = 1;
        } else {
            answerButtonMessage = getString(R.string.wrong_answer, question_nb) + "\n";
            lastAnswer = 0;

        }
    }

    /**
     * This method is used to highlight in a different colour a pressed button.
     */
    public void onClick(View v) {

        Drawable dr = getResources().getDrawable(R.drawable.button_pressed);
        dr.setColorFilter(Color.parseColor("#00E676"), PorterDuff.Mode.SRC_ATOP);

        switch (v.getId()) {
            case R.id.b_answer_a:

                if (button == null) {
                    button = (Button) findViewById(v.getId());
                } else {
                    button.setBackgroundResource(R.drawable.button_pressed);
                    button = (Button) findViewById(v.getId());
                }
                button.setBackgroundDrawable(dr);

                break;

            case R.id.b_answer_b:
                if (button == null) {
                    button = (Button) findViewById(v.getId());
                } else {
                    button.setBackgroundResource(R.drawable.button_pressed);
                    button = (Button) findViewById(v.getId());
                }
                button.setBackgroundDrawable(dr);

                break;

            case R.id.b_answer_c:
                if (button == null) {
                    button = (Button) findViewById(v.getId());
                } else {
                    button.setBackgroundResource(R.drawable.button_pressed);
                    button = (Button) findViewById(v.getId());
                }
                button.setBackgroundDrawable(dr);

                break;

            case R.id.b_answer_d:
                if (button == null) {
                    button = (Button) findViewById(v.getId());
                } else {
                    button.setBackgroundResource(R.drawable.button_pressed);
                    button = (Button) findViewById(v.getId());
                }
                button.setBackgroundDrawable(dr);

                break;

            case R.id.b_answer_e:
                if (button == null) {
                    button = (Button) findViewById(v.getId());
                } else {
                    button.setBackgroundResource(R.drawable.button_pressed);
                    button = (Button) findViewById(v.getId());
                }
                button.setBackgroundDrawable(dr);

                break;

            default:
                break;
        }
    }

    /**
     * This method is called when the the button Send Email is pressed
     */
    public void sendEmail(View view) {

        String subjectEmail = getString(R.string.summary_email_subject, name);
        String toEmail = "CocktailQuiz@gmail.com";

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{toEmail});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subjectEmail);
        emailIntent.putExtra(Intent.EXTRA_TEXT, summaryMessage);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }

    }

}