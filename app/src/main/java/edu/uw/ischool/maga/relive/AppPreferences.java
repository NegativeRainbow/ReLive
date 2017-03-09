package edu.uw.ischool.maga.relive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AppPreferences extends AppCompatActivity {

    private Button button;
    private EditText quizLengthText;
    private EditText quizTimeLength;
    private MainApp app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_preferences);


        button = (Button) findViewById(R.id.settings_button);
        quizLengthText = (EditText) findViewById(R.id.quiz_length_answer);
        quizTimeLength = (EditText) findViewById(R.id.quiz_time_answer);
        quizLengthText.setText(MainApp.quizLength + "");
        quizTimeLength.setText(MainApp.quizTime / 1000 + "");
        app = new MainApp();
        quizLengthText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fires right as the text is being changed (even supplies the range of text)
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length()==0){return;}
                else{app.quizLength = (Integer.parseInt(quizLengthText.getText().toString()));}
            }
        });

        quizTimeLength.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fires right as the text is being changed (even supplies the range of text)
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length()==0){return;}
                else{app.quizTime = Integer.parseInt(quizTimeLength.getText().toString()) * 1000;
                }
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AppPreferences.this, MainActivity.class);
                app.quizLength = (Integer.parseInt(quizLengthText.getText().toString()));
                app.quizTime = Integer.parseInt(quizTimeLength.getText().toString()) * 1000;
                startActivity(i);
            }
        });
    }

}

