package com.colman.zerphonefinall;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.colman.zerphonefinall.Model.Constants;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class SignUpActivity extends ActionBarActivity {

    protected EditText email;
    protected EditText password;
    protected TextView login;
    protected Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = (EditText)findViewById(R.id.editTextEmail);
        password = (EditText)findViewById(R.id.editTextPassword);
        login = (TextView) findViewById(R.id.buttonLogin);
        signup = (Button)findViewById(R.id.buttonRegister);

        final Firebase ref = new Firebase(Constants.FIREBASE_URL);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
                progressDialog.setTitle("Signup");
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                String passwordString = password.getText().toString();
                String emailString = email.getText().toString();

                passwordString = passwordString.trim();
                emailString = emailString.trim();

                if (passwordString.isEmpty() || emailString.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setMessage(R.string.signup_error_message)
                            .setTitle(R.string.signup_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    progressDialog.hide();
                } else {

                    // signup
                    ref.createUser(emailString, passwordString, new Firebase.ResultHandler() {
                        @Override
                        public void onSuccess() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                            builder.setMessage(R.string.signup_success)
                                    .setPositiveButton(R.string.login_button_label, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                            builder.setMessage(firebaseError.getMessage())
                                    .setTitle(R.string.signup_error_title)
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            progressDialog.hide();
                        }
                    });
                }
            }
        });
    }
}
