package com.example.finalprojectpam;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    EditText email, password, confirmPassword;
    TextView login;

    Button registerBtn;

    private FirebaseAuth mAuth;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        confirmPassword = findViewById(R.id.confirm_password);
        registerBtn = findViewById(R.id.button);
        login = findViewById(R.id.login_text);

        registerBtn.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void onClick(View v) {
        if (registerBtn.getId()==v.getId()){
            register(email.getText().toString(), password.getText().toString(), confirmPassword.getText().toString());
        }
        else if (login.getId()==v.getId()){
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void register(String email, String password, String confirmPassword){
        if(!cekform()){
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            //  signup success
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
                            alertDialogBuilder.setTitle("Signup");
                            alertDialogBuilder.setMessage("Your account has been registered. Please sign in use your username and password.");
                            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            alertDialogBuilder.show();
                        } else {
                            task.getException().printStackTrace();
                            //  signup fail
                            final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "registered has been failed! Please try again.", Snackbar.LENGTH_INDEFINITE);
                            snackbar.setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    snackbar.dismiss();
                                }
                            });
                            snackbar.setActionTextColor(getResources().getColor(R.color.maroon));
                            snackbar.show();
                        }
                    }
                });
    }

    public boolean cekform(){
        boolean result = true;
        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Required");
            result = false;
        } else {
            email.setError(null);
        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("Required");
            result = false;
        } else {
            password.setError(null);
        }
        if (password.getText().toString()==confirmPassword.getText().toString()){
            confirmPassword.setError("Password doesn't match");
            result = false;
        } else {
            confirmPassword.setError(null);
        }
        return result;
    }
}
