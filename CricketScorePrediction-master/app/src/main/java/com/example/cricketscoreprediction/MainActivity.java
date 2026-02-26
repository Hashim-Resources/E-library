package com.example.cricketscoreprediction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextCurrentScore, editTextOvers, editTextWickets;
    private Button buttonPredict;
    private TextView textViewPredictedScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCurrentScore = findViewById(R.id.editTextCurrentScore);
        editTextOvers = findViewById(R.id.editTextOvers);
        editTextWickets = findViewById(R.id.editTextWickets);
        buttonPredict = findViewById(R.id.buttonPredict);
        textViewPredictedScore = findViewById(R.id.textViewPredictedScore);

        buttonPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentScoreStr = editTextCurrentScore.getText().toString();
                String oversStr = editTextOvers.getText().toString();
                String wicketsStr = editTextWickets.getText().toString();

                if (currentScoreStr.isEmpty() || oversStr.isEmpty() || wicketsStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int currentScore = Integer.parseInt(currentScoreStr);
                    double overs = Double.parseDouble(oversStr);
                    int wickets = Integer.parseInt(wicketsStr);

                    if (overs <= 0 || overs > 20 || wickets < 0 || wickets > 10) {
                        Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double currentRunRate = currentScore / overs;
                    double remainingOvers = 20 - overs;

                    // Wickets penalty factor: reduce the predicted score by 5% for each wicket lost
                    double wicketPenalty = 1.0 - (wickets * 0.05);
                    if (wicketPenalty < 0) {
                        wicketPenalty = 0;
                    }

                    int predictedScore = (int) (currentScore + (remainingOvers * currentRunRate * wicketPenalty));

                    textViewPredictedScore.setText("Predicted Score: " + predictedScore);

                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid number format", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
