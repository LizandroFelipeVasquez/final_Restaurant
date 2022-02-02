package com.example.aplication_don_luxor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements  View.OnClickListener{
    private EditText usernameText;
    private EditText passwordText;
    private Button registerButton;
    private ProgressBar loading;
    private TextView LoginInf,banner;
    private FirebaseAuth mAuth;
    private EditText nameText,numero,direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        mAuth = FirebaseAuth.getInstance();

        banner =(TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerButton =(Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);


        nameText =(EditText) findViewById(R.id.tiet_register_name);
        numero =(EditText) findViewById(R.id.tiet_register_phone);
        direccion =(EditText) findViewById(R.id.tiet_register_direction);
        usernameText =(EditText) findViewById(R.id.tiet_register_user);
        passwordText =(EditText) findViewById(R.id.tiet_register_password);
        loading=(ProgressBar) findViewById(R.id.progress_bar_loading);


        }

        @Override
        public  void onClick(View v){
            switch (v.getId()){
                case R.id.banner:
                    startActivity(new Intent(this,MainActivity.class));
                    break;
                case R.id.registerButton:
                    registerButton();
                    break;
            }
        }



        private void registerButton() {
        String tiet_register_user =  usernameText.getText().toString().trim();
        String tiet_register_password =  passwordText.getText().toString().trim();
        String tiet_register_name =  nameText.getText().toString().trim();
        String tiet_register_phone =  numero.getText().toString().trim();
        String tiet_register_direction =  direccion.getText().toString().trim();

        if (tiet_register_name.isEmpty()){
            nameText.setError("Se requiere  Su  Nombre completo");
        }
        if (tiet_register_phone.isEmpty()){
            numero.setError("Se requiere llenar su nro telefonico");
        }
        if (tiet_register_direction.isEmpty()){
            direccion.setError("Se requiere su direccion");
        }
        if (tiet_register_user.isEmpty()){
            usernameText.setError("Se requiere llenar este campo");
        }
        if (tiet_register_password.isEmpty()){
            passwordText.setError("Se requiere  crear una contraseña");
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(tiet_register_user).matches()){
            usernameText.setError("Por favor valide el Email");
            usernameText.requestFocus();
            return;
        }

        if(tiet_register_password.isEmpty()){
            passwordText.setError("Por favor valide la contraseña");
            passwordText.requestFocus();
            return;
        }
        if(passwordText.length()<6){
            passwordText.setError("Minimo son 6 caracteristicas");
            passwordText.requestFocus();
            return;
        }

        loading.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(tiet_register_user,tiet_register_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            user user= new user(tiet_register_name,tiet_register_phone,tiet_register_direction,tiet_register_user);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this,"Usuario registrado correctamente",Toast.LENGTH_LONG).show();
                                        loading.setVisibility(View.VISIBLE);
                                    }else {
                                        Toast.makeText(RegisterUser.this,"Fallo en registrar",Toast.LENGTH_LONG).show();
                                        loading.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterUser.this,"Fallo en registrar",Toast.LENGTH_LONG).show();
                            loading.setVisibility(View.GONE);
                        }
                    }
                });

        }
    }