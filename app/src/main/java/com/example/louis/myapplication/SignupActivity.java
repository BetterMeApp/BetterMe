package com.example.louis.myapplication;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText mEmail;
    private EditText mUsername;
    private EditText mPassword;
    private Button mSignup;
    private TextView mGotoLogin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        final Context ctx = this;

        mEmail = (EditText) findViewById(R.id.signup_email);
        mUsername = (EditText) findViewById(R.id.signup_username);
        mPassword = (EditText) findViewById(R.id.signup_password);
        mSignup = (Button) findViewById(R.id.signup_submit_btn);
        mGotoLogin = (TextView) findViewById(R.id.signup_goto_login_textView);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //user is logged in, go back to Login (and from there go to HomeActy)
                    finish();
                }
            }
        };
        attachListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    private void attachListeners(){
        final Context ctx = this;
        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissKeyboard(view);

                String email = mEmail.getText().toString();
                String un = mUsername.getText().toString();
                String pw = mPassword.getText().toString();
                if (email.length() < 7 || un.length() < 7 || pw.length() < 7){
                    Toast.makeText(SignupActivity.this, "Signup Failed. Ensure you are using a valid email and your username and password are both 8 characters or longer.", Toast.LENGTH_LONG).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, pw)
                        .addOnCompleteListener((Activity) ctx, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(SignupActivity.this, "Oops! Something went wrong in the Sign-Up process.\nPlease try again or contact support.", Toast.LENGTH_LONG).show();
                                } else {
                                    //user?

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    UserProfileChangeRequest req = new UserProfileChangeRequest.Builder().setDisplayName(mUsername.getText().toString()).build();
                                    user.updateProfile(req)
                                            .addOnCompleteListener((Activity) ctx, new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (!task.isSuccessful()) {
                                                        Log.d(TAG, "onComplete: displayname update failed");
                                                    }
                                                }
                                            });


                                }
                            }
                        });

            }
        });

        mGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go back to Login
                finish();
            }
        });

        mEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) {
                    dismissKeyboard(view);
                }

            }
        });

        mUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) {
                    dismissKeyboard(view);
                }
            }
        });

        mPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) {
                    dismissKeyboard(view);
                }
            }
        });
    }

    private void dismissKeyboard(View view){
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

}
