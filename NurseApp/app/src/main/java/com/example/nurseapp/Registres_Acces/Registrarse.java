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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe que controla l'accés d'usuaris per registrar-se a Firebase.
 */
public class Registrarse extends TractamentToolBar {

    // Inicialització de les variables :
    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private EditText editTextEmail;
    private EditText editTextContrassenya;
    private EditText editTextComprovacio;
    private Button buttonAcces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        // Executem el mètode setUpToolBar
        setUpToolBar();

        // Executem el mètode customTitileTool, passant com a paràmetre el nom que volem indicar.
        customTitileToolBar(getResources().getString(R.string.register));

        // Vinculem les variables amb els corresponents objectes de l'apartat gràfic.
        editTextEmail = findViewById(R.id.idEmail);
        editTextContrassenya = findViewById(R.id.idContrassenya);
        editTextComprovacio = findViewById(R.id.idComprovacio);
        buttonAcces = findViewById(R.id.idButtonAcces);

        // Obtenim l'instància de FirebaseAuth.
        mAuth = FirebaseAuth.getInstance();

        // Botó onClick per tal d'executar el mètode ComprovarRegistre.
        buttonAcces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComprovarRegistre();
            }
        });
    }

    /**
     * Mètode amb el que comprovem si el registre a la base de dades Firebase s'ha realitzat correctament.
     */
    public void ComprovarRegistre() {
        String email = editTextEmail.getText().toString();
        String contrassenya = editTextContrassenya.getText().toString();
        String comprovacio = editTextComprovacio.getText().toString();

        if (contrassenya.equals(comprovacio)) {
            try {
                mAuth.createUserWithEmailAndPassword(email, contrassenya)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Agafem l'usuari creat.
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    // Afegim automàticament que la compte és un pacient.
                                    DocumentReference df = fStore.collection("Users").document(user.getUid());
                                    Map<String,Object> userInfo = new HashMap<>();
                                    userInfo.put("rol","pacient");
                                    df.set(userInfo);

                                    // Informem que la compte s'ha creat correctament.
                                    Toast.makeText(getApplicationContext(), "Compte creada.", Toast.LENGTH_LONG).show();

                                    // Tornem a la MainActivity i
                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(i);

                                    // finalitzem aquesta activitat.
                                    finish();
                                }
                                // Informem que no s'ha pogut crear el compte degut a que ja existeix.
                                else {
                                    Toast.makeText(getApplicationContext(), "Aquest compte ja existeix!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
            // Informem que hi ha hagut un error amb les dades introduïdes.
            catch (IllegalArgumentException | NullPointerException e) {
                Toast.makeText(getApplicationContext(), "Dades no vàlides!", Toast.LENGTH_LONG).show();
            }
        }
        else {
            // Informem que les contrasenyes no coincideixen.
            Toast.makeText(getApplicationContext(), "La contrasenya no coincideix!", Toast.LENGTH_LONG).show();
        }
    }
}
