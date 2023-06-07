package com.example.finalprojectpam;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email, password;
    TextView register;

    Button login;

    private FirebaseAuth mAuth;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        register = findViewById(R.id.register);
        login = findViewById(R.id.button);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void login(String email, String password){
        if(!cekform()){
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent le = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(le);
                        } else {
                            Toast.makeText(LoginActivity.this,"Login failed. Your username " + "and password is not matched",Toast.LENGTH_SHORT ).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (login.getId()==v.getId()){
            login(email.getText().toString().trim(),password.getText().toString().trim());
        }
        else if (register.getId()==v.getId()){
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
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
        return result;
    }
}
