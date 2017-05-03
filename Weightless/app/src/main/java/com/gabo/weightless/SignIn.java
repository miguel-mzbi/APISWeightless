package com.gabo.weightless;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gabo.weightless.DB.DBHelper;

public class SignIn extends AppCompatActivity {
    private EditText userInput, emailInput, passwordInput, passwordConfirmationInput;
    private DBHelper dbHelper;
    private TextView validateEmail;
    private boolean validEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        userInput = (EditText) findViewById(R.id.userInput);
        emailInput = (EditText) findViewById(R.id.mailInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        passwordConfirmationInput = (EditText) findViewById(R.id.passwordConfirmation);

        dbHelper = new DBHelper(this);

        validEmail = false;

        validateEmail = (TextView) findViewById(R.id.validateEmail);

        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validEmail = Patterns.EMAIL_ADDRESS.matcher(emailInput.getText().toString()).matches();
                updateValidateEmail();
            }
        });

        Button signInButton = (Button) findViewById(R.id.signInButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser(v);
            }
        });
    }

    public void createUser(View v){
        String userS = userInput.getText().toString(),
                emailS = emailInput.getText().toString(),
                psdS = passwordInput.getText().toString(),
                psdCS = passwordConfirmationInput.getText().toString();
        if(userS.length() > 0 && emailS.length() > 0 && psdS.length() > 0 && psdCS.length() > 0){
            if(!dbHelper.userExists(userS)){
                if(validEmail) {
                    if (!dbHelper.emailExists(emailS)) {
                        if (psdS.compareTo(psdCS) == 0) {
                            dbHelper.createUser(userS, emailS, psdS);
                            finish();
                        } else {
                            Toast.makeText(this, "the password doesn't match", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "This email is already in use", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Introduce a valid email", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "This user is already in use", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "You have to fill every input box to create user", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateValidateEmail(){
        if(emailInput.getText().toString().length() == 0){
            validateEmail.setText("");
            return;
        }
        if(validEmail){
            validateEmail.setText("Valid Email");
        }else{
            validateEmail.setText("Invalid Email");
        }
    }
}
