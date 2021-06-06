package com.example.nurseapp.Registres_Acces;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.nurseapp.MainActivity;
import com.example.nurseapp.R;
import com.example.nurseapp.TractamentGenericToolBar.TractamentToolBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Classe que controla l'accés d'usuaris per registrar-se a Firebase.
 */
public class Registrarse extends TractamentToolBar {

    // Inicialització de les variables :
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private EditText editTextEmail;
    private EditText editTextContrassenya;
    private Button buttonAcces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        // Executem el mètode setUpToolBar
        setUpToolBar();

        // Executem el mètode customTitileTool, passant com a paràmetre el nom que volem indicar.
        customTitileToolBar("Login");

        // Vinculem les variables amb els corresponents objectes de l'apartat gràfic.
        editTextEmail = findViewById(R.id.idEmail);
        editTextContrassenya = findViewById(R.id.idContrassenya);
        buttonAcces = findViewById(R.id.idButtonAcces);

        // Obtenim l'instància de FirebaseAuth.
        mAuth = FirebaseAuth.getInstance();

        // Botó onClick per tal d'executar el mètode ComprovarLogin.
        buttonAcces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComprovarLogin();
            }
        });
    }

    /**
     * Mètode amb el que comprovem si el login a la base de dades Firebase s'ha realitzat correctament.
     */
    public void ComprovarLogin() {
        String email = editTextEmail.getText().toString();
        String contrassenya = editTextContrassenya.getText().toString();

        try {
            mAuth.signInWithEmailAndPassword(email, contrassenya)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();

                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);

                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Revisa les dades!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        catch (IllegalArgumentException | NullPointerException d) {
            Toast.makeText(getApplicationContext(), "Revisa les dades!", Toast.LENGTH_LONG).show();
        }
    }
}
