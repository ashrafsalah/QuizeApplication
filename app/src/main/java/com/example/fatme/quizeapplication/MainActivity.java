package com.example.fatme.quizeapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    TextView QuestionNo,Score,Question;
    EditText Answer;
    Button Submit;
    ProgressBar Progressbar;
    ArrayList<QuestionModel>QuestionModels;
    int CurrentPosition=0;
    int NumOfCorrect=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        QuestionNo= (TextView) findViewById(R.id.questionNo);
        Score= (TextView) findViewById(R.id.score);
        Question= (TextView) findViewById(R.id.question);
        Answer= (EditText) findViewById(R.id.answer);
        Submit= (Button) findViewById(R.id.submit);
        Progressbar= (ProgressBar) findViewById(R.id.progressbar);
        QuestionModels=new ArrayList<>();
        SetQuestion();
        SetData();
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAnswer();
            }
        });

    }
    public void SetQuestion(){
        QuestionModels.add(new QuestionModel(" 5 + 2 = ?" , 7 ));
        QuestionModels.add(new QuestionModel(" 12 * 3 = ? ",36));
        QuestionModels.add(new QuestionModel(" 12 / 4 = ? ",3));
        QuestionModels.add(new QuestionModel(" 25 - 15 = ? ",10));
        QuestionModels.add(new QuestionModel(" 6 * 6 = ? ",36));

    }
    public void SetData(){
        if (CurrentPosition<QuestionModels.size()){
            Question.setText(QuestionModels.get(CurrentPosition).Question);
            Score.setText("Score : "+ NumOfCorrect+" / " + QuestionModels.size());
            QuestionNo.setText("Question No : "+ (CurrentPosition+1));
            Progressbar.setProgress((CurrentPosition+1)*100/QuestionModels.size());
            Answer.setText("");
        }
        else{
            new SweetAlertDialog(MainActivity.this,SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("You completed The Quize Successfully ! ")
                    .setContentText("Your score is : "+NumOfCorrect+ " / " + QuestionModels.size() )
                    .setConfirmText("Restart")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            CurrentPosition=0;
                            NumOfCorrect=0;
                            sweetAlertDialog.dismissWithAnimation();
                            SetData();
                        }
                    })
                    .setCancelText("Close")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            finish();
                        }
                    })
                    .show();


        }


    }
    public void CheckAnswer(){
        if(Answer.getText().toString().equalsIgnoreCase(String.valueOf(QuestionModels.get(CurrentPosition).Answer))){
            NumOfCorrect++;
            CurrentPosition++;

            new SweetAlertDialog(MainActivity.this,SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Right Answer")
                    .setConfirmText("Ok ")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            SetData();

                        }

                    }

                    )
                    .show();



        }
        else {
            CurrentPosition++;
            new SweetAlertDialog(MainActivity.this,SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Wrong answer")
                    .setContentText("Right answer is : " + QuestionModels.get(CurrentPosition-1).Answer)
                    .setConfirmText("Ok")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            SetData();
                        }
                    })
                    .show();


        }
    }

}
