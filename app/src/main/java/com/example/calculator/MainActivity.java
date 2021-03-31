package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import org.mariuszgromada.math.mxparser.Expression;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText mainScreen, secondScreen;
    private Toast exitToast;
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainScreen = findViewById(R.id.main_screen);
        mainScreen.setShowSoftInputOnFocus(false);

        secondScreen = findViewById(R.id.second_screen);
        secondScreen.setShowSoftInputOnFocus(false);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            exitToast.cancel();
            super.onBackPressed();
            return;
        } else {
            exitToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            exitToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    private void updateText(String text) {
        // get current string
        String oldStr = Objects.requireNonNull(mainScreen.getText()).toString();
        // get current cursor position
        int cursorPosition = mainScreen.getSelectionStart();
        // get left side of string
        String leftStr = oldStr.substring(0, cursorPosition);
        // get right side of string
        String rightStr = oldStr.substring(cursorPosition);
        // update screen
        mainScreen.setText(String.format("%s%s%s", leftStr, text, rightStr));
        mainScreen.setSelection(cursorPosition + 1);
    }

    public void btnBackspace(View view) {
        // get current cursor position
        int cursorPosition = mainScreen.getSelectionStart();
        // get length of text
        int len = Objects.requireNonNull(mainScreen.getText()).length();

        if (cursorPosition != 0 && len != 0) {
            // initialize SpannableStringBuilder class
            SpannableStringBuilder selection = (SpannableStringBuilder) mainScreen.getText();
            // replace selection with empty string
            selection.replace(cursorPosition - 1, cursorPosition, "");
            // update screen
            mainScreen.setText(selection);
            mainScreen.setSelection(cursorPosition - 1);
        }
    }

    public void btnBracket(View view) {
        // get current cursor position
        int cursorPosition = mainScreen.getSelectionStart();
        // get length of text
        int len = Objects.requireNonNull(mainScreen.getText()).length();
        // determine total of parentheses
        int openParentheses = 0, closeParentheses = 0;

        for (int i = 0; i < cursorPosition; i++) {
            if (mainScreen.getText().toString().charAt(i) == '(') {
                openParentheses += 1;
            } else if (mainScreen.getText().toString().charAt(i) == ')') {
                closeParentheses += 1;
            }
        }

        if (openParentheses == closeParentheses || mainScreen.getText().toString().startsWith("(", len - 1)) {
            updateText("(");
        } else if (openParentheses > closeParentheses && !mainScreen.getText().toString().startsWith("(", len - 1)) {
            updateText(")");
        }

        // update selection (cursor position)
        mainScreen.setSelection(cursorPosition + 1);
    }

    public void btnEqual(View view) {
        String expression = Objects.requireNonNull(mainScreen.getText()).toString();
        secondScreen.setText(expression);

        expression = expression.replaceAll("×", "*");
        expression = expression.replaceAll("÷", "/");

        Expression exp = new Expression(expression);

        double result = exp.calculate();
        String resultToStr;

        if (result == (int) result) {
            resultToStr = String.valueOf((int) exp.calculate());
        } else {
            resultToStr = String.valueOf(exp.calculate());
        }

        mainScreen.setText(resultToStr);
        mainScreen.setSelection(resultToStr.length());
    }

    public void btnAc(View view) {
        mainScreen.setText("");
    }

    public void btnSquare(View view) {
        updateText(getString(R.string.square));
    }

    public void btnAdd(View view) {
        updateText(getString(R.string.add));
    }

    public void btnSeven(View view) {
        updateText(getString(R.string._7));
    }

    public void btnEight(View view) {
        updateText(getString(R.string._8));
    }

    public void btnNine(View view) {
        updateText(getString(R.string._9));
    }

    public void btnMinus(View view) {
        updateText(getString(R.string.minus));
    }

    public void btnFour(View view) {
        updateText(getString(R.string._4));
    }

    public void btnFive(View view) {
        updateText(getString(R.string._5));
    }

    public void btnSix(View view) {
        updateText(getString(R.string._6));
    }

    public void btnMultiply(View view) {
        updateText(getString(R.string.multiply));
    }

    public void btnOne(View view) {
        updateText(getString(R.string._1));
    }

    public void btnTwo(View view) {
        updateText(getString(R.string._2));
    }

    public void btnThree(View view) {
        updateText(getString(R.string._3));
    }

    public void btnDivide(View view) {
        updateText(getString(R.string.divisible));
    }

    public void btnPercentage(View view) {
        updateText("%");
    }

    public void btnZero(View view) {
        updateText(getString(R.string._0));
    }

    public void btnDecimal(View view) {
        updateText(getString(R.string.dot));
    }
}