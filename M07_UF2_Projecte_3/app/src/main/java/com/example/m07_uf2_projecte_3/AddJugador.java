package com.example.m07_uf2_projecte_3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class AddJugador extends AppCompatActivity implements RecognitionListener {

    //Declaració de les variables necessàries.
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private EditText textaEditar;

    private EditText et_nom;
    private EditText et_pg;
    private EditText et_pp;
    private EditText et_pt;

    private TextView num1;
    private TextView num2;
    private TextView num3;
    private TextView num4;

    private Intent cameraIntent;
    private ImageView mImageView;
    private String foto = "";

    private String valorDb = "8";

    private static final int PERMISSION_CODE = 1000;

    TextView prova2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addjugador);

        //Realitzem la conexió al Firebase i busquem i assignem les variables de l'entorn gràfic.
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("jugadors");

        et_nom = findViewById(R.id.et_setNom);
        et_pg = findViewById(R.id.et_setPartidesGuanyades);
        et_pp = findViewById(R.id.et_setPartidesPerdudes);
        et_pt = findViewById(R.id.et_setPartidesTotals);

        num1 = findViewById(R.id.tv_numNom);
        num2 = findViewById(R.id.tv_numPartidesG);
        num3 = findViewById(R.id.tv_numPartidesP);
        num4 = findViewById(R.id.tv_numPartidesT);

        mImageView = findViewById(R.id.iv_camera);
        prova2 = findViewById(R.id.tv_prova2);

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizer.setRecognitionListener(this);

        //OnClick de l'imatge de la càmera.
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else {
                        openCamera();
                    }
                }
                else {
                    openCamera();
                }
            }
        });

        //OnFocus de cada un dels elements.
        View.OnFocusChangeListener focusElement = new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {

                    ((EditText) v).setBackgroundResource(R.drawable.rectangle2);
                }

                else if (!hasFocus){

                    ((EditText) v).setBackgroundResource(R.drawable.rectangle);

                }

            }
        };

        et_nom.setOnFocusChangeListener(focusElement);
        et_pg.setOnFocusChangeListener(focusElement);
        et_pp.setOnFocusChangeListener(focusElement);
        et_pt.setOnFocusChangeListener(focusElement);

        //OnTouch de cada un dels elements.
        View.OnTouchListener listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                TextView element;
                element = (TextView) v;
                textaEditar = findViewById(element.getLabelFor());

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        textaEditar.requestFocus();
                        element.setBackgroundResource(R.drawable.oval2);
                        element.setTextColor(Color.rgb(0, 0, 0));

                        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ca");
                        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "ca");
                        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());

                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        textaEditar.setBackgroundResource(R.drawable.rectangle3);
                        break;

                    case MotionEvent.ACTION_UP:
                        element.setBackgroundResource(R.drawable.oval);
                        element.setTextColor(Color.rgb(255, 255, 255));
                        textaEditar.setBackgroundResource(R.drawable.rectangle2);

                        mSpeechRecognizer.stopListening();

                }
                return true;
            }
        };

        num1.setOnTouchListener(listener);
        num2.setOnTouchListener(listener);
        num3.setOnTouchListener(listener);
        num4.setOnTouchListener(listener);
    }

    //Quan la càmera acabi de fer la foto agafem el resultat de l'intent i el passem el mètode per transformar l'imatge a Base64.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode == RESULT_OK){

            //mImageView.setImageURI(image_uri);
            Bundle extres = data.getExtras();
            Bitmap imatgeBitmap = (Bitmap) extres.get("data");
            mImageView.setImageBitmap(imatgeBitmap);
            bitmapEncode(imatgeBitmap);

        }

    }

    //Agafem l'imatge per convertir-la a Base64..
    private void bitmapEncode(Bitmap imatgeBitmap) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imatgeBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] byteArray = outputStream.toByteArray();

        foto = Base64.encodeToString(byteArray, Base64.DEFAULT);

    }

    //Mètode que farà el push a la base de dades del Firebase del objecte nou.
    public void afegirJugBD(View v) {

        String nom = et_nom.getText().toString();
        String partidesGuany = et_pg.getText().toString();
        String partidesPerd = et_pp.getText().toString();
        String partidesTot = et_pt.getText().toString();

        //prova2.setText("Nom: " + nom + "  PG:" + partidesGuany  + "  PP:" + partidesPerd  + "  PT:" + partidesTot);

        Jugadors j = new Jugadors(nom, partidesGuany, partidesPerd, partidesTot, foto);
        String id = dbRef.push().getKey();
        dbRef.child(id).setValue(j);

        Toast.makeText(this, "Jugador afegit correctament", Toast.LENGTH_SHORT).show();

    }

    //Mètode que capturarà l'imatge de la càmera.
    private void openCamera() {

        cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 1);

    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {

    }

    //Mètode que ens transforma el reconeixament de veu i ho assigna al camp de text corresponent.
    @Override
    public void onResults(Bundle results) {
        ArrayList<String> res = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        if (res != null) {
            textaEditar.setText(res.get(0));
            Log.e("TEXT: ", res.get(0));
        }
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }

    //Retorn a l'activitat principal.
    public void retornar (View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
