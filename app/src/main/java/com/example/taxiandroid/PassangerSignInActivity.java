package com.example.taxiandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PassangerSignInActivity extends AppCompatActivity {
    private TextInputLayout email;
    private TextInputLayout name;
    private TextInputLayout password;
    private TextInputLayout confPassword;
    private Button signUp;
    private TextView toggleLogin;
    private boolean loginMode;
    private FirebaseAuth auth;
    private final static String  TAG = "PassangerSignInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sign_in);

        loginMode = false;
        email = findViewById(R.id.textInputEmail);
        name = findViewById(R.id.textInputName);
        password = findViewById(R.id.textInputPassword);
        confPassword = findViewById(R.id.textInputConfPassword);
        signUp = findViewById(R.id.loginSignUpBtn);
        toggleLogin = findViewById(R.id.toggleLoginSignupTextView);
        auth = FirebaseAuth.getInstance();
    }

    private boolean validateEmail(){
        String emailInput = email.getEditText().getText().toString().trim();
        if(emailInput.isEmpty()) {
            email.setError("Input email");
            return false;
        }
        return true;
    }

    private boolean validateName(){
        String nameInput = name.getEditText().getText().toString().trim();
        if(nameInput.isEmpty()) {
            name.setError("Input name");
            return false;
        }else if(nameInput.length() > 15){
            name.setError("Name length more 15");
            return false;
        }
        return true;
    }

    private boolean validatePassword(){
        String passwordInput = password.getEditText().getText().toString().trim();
        String confPassInput = confPassword.getEditText().getText().toString().trim();

        if(passwordInput.isEmpty()) {
            password.setError("Input password");
            return false;
        }else if(passwordInput.length() < 6){
            password.setError("Password length");
            return false;
        }
        return true;
    }

    private boolean validateConfPassword(){
        String passwordInput = password.getEditText().getText().toString().trim();
        String confPassInput = confPassword.getEditText().getText().toString().trim();

        if(!passwordInput.equals(confPassInput)){
            password.setError("Passwords have to match");
            return false;
        }
        return true;
    }

    public void signUpUser(View view){
        if(!validateEmail() || !validatePassword()){
            return;
        }
        //firebase authentifications sign in
        if(loginMode){
            auth.signInWithEmailAndPassword(email.getEditText().getText().toString().trim(),
                    password.getEditText().getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = auth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(PassangerSignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        }
        if(!validateEmail() || !validateName() || !validatePassword()|| !validateConfPassword()){
            return;
        }
        //firebase authentification sign up
        auth.createUserWithEmailAndPassword(email.getEditText().getText().toString().trim(),
                password.getEditText().getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(PassangerSignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    public void toggleSignUp(View view){
        if(loginMode){
            loginMode = false;
            signUp.setText("Sign Up");
            toggleLogin.setText("Tap to Sign in");
            confPassword.setVisibility(View.VISIBLE);
            name.setVisibility(View.VISIBLE);
        }else{
            loginMode = true;
            signUp.setText("Sign In");
            toggleLogin.setText("Tap to Sign up");
            confPassword.setVisibility(View.GONE);
            name.setVisibility(View.GONE);
        }
    }
}