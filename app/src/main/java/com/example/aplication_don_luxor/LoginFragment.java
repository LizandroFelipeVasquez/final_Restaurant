package com.example.aplication_don_luxor;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import  android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends AppCompatActivity  {
    private TextInputEditText Textusername;
    private TextInputEditText Textpassword;
    private Button ButtonMessage;
    private Button LoginButton;
    private FirebaseAuth mAuth;
    private  Button TextButton;
    private ProgressBar LoadingLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fragment);

        Textusername = findViewById(R.id.username_text_input);
        Textpassword= findViewById(R.id.password_edit_text);
        LoginButton= findViewById(R.id.button_Login_Get);
        ButtonMessage= findViewById(R.id.Login_text);
        LoadingLogin = findViewById(R.id.progress_bar_loadinginLogin);
        mAuth = FirebaseAuth.getInstance();



        ButtonMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(LoginFragment.this,RegisterUser.class);
                startActivity(i);
            }
        });



        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = Textusername.getText().toString();
                String password = Textpassword.getText().toString();
                if(TextUtils.isEmpty(username)&& TextUtils.isEmpty(password)){
                    Toast.makeText(LoginFragment.this,"Ingrese a su usuario",Toast.LENGTH_LONG).show();
                    return;
                }else {
                    mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                LoadingLogin.setVisibility(View.GONE);
                                Toast.makeText(LoginFragment.this,"Se ha Logueado",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(LoginFragment.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            }else {
                                Toast.makeText(LoginFragment.this,"Usario no encontrado", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            Intent i = new Intent(LoginFragment.this, MainActivity.class);
            startActivity(i);
            this.finish();


        }
    }
}
