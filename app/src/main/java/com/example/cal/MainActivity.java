package com.example.cal;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText num1 = findViewById(R.id.num1);
        EditText num2 = findViewById(R.id.num2);
        Spinner operationSpinner = findViewById(R.id.operation_spinner);
        Button calculateButton = findViewById(R.id.calculate_button);
        TextView resultText = findViewById(R.id.result_text);
        LinearLayout historyContainer = findViewById(R.id.history_container);

        // Populate spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.operations,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operationSpinner.setAdapter(adapter);

        // Handle operation selection
        operationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String operation = parent.getItemAtPosition(position).toString();
                if (operation.equals("Square Root") || operation.equals("Sine") || operation.equals("Cosine") || operation.equals("Tangent")) {
                    num2.setEnabled(false);
                    num2.setHint("Not required");
                } else {
                    num2.setEnabled(true);
                    num2.setHint("Enter second number (if required)");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Handle calculate button click
        calculateButton.setOnClickListener(v -> {
            resultText.setText(""); // Clear previous result

            String number1Str = num1.getText().toString();
            String number2Str = num2.getText().toString();

            if (number1Str.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter the first number", Toast.LENGTH_SHORT).show();
                return;
            }

            double number1 = Double.parseDouble(number1Str);
            double number2 = 0;
            if (!number2Str.isEmpty()) {
                number2 = Double.parseDouble(number2Str);
            }

            String operation = operationSpinner.getSelectedItem().toString();
            double result = 0;

            try {
                switch (operation) {
                    case "Addition":
                        result = number1 + number2;
                        break;
                    case "Subtraction":
                        result = number1 - number2;
                        break;
                    case "Multiplication":
                        result = number1 * number2;
                        break;
                    case "Division":
                        if (number2 == 0) {
                            Toast.makeText(MainActivity.this, "Cannot divide by zero", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        result = number1 / number2;
                        break;
                    case "Square Root":
                        if (number1 < 0) {
                            Toast.makeText(MainActivity.this, "Cannot calculate square root of a negative number", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        result = Math.sqrt(number1);
                        break;
                    case "Power":
                        result = Math.pow(number1, number2);
                        break;
                    case "Percentage":
                        result = (number1 / 100) * number2;
                        break;
                    case "Sine":
                        result = Math.sin(Math.toRadians(number1));
                        break;
                    case "Cosine":
                        result = Math.cos(Math.toRadians(number1));
                        break;
                    case "Tangent":
                        result = Math.tan(Math.toRadians(number1));
                        break;
                }

                resultText.setText("Result: " + result); // Show only the result
                addToHistory("Result: " + result, historyContainer);

            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Error in calculation", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToHistory(String calculation, LinearLayout historyContainer) {
        TextView historyItem = new TextView(this);
        historyItem.setText(calculation);
        historyItem.setPadding(8, 8, 8, 8);
        historyItem.setBackgroundResource(android.R.color.holo_purple);
        historyItem.setTextSize(14);
        historyContainer.addView(historyItem, 0); // Add to the top
    }
}
