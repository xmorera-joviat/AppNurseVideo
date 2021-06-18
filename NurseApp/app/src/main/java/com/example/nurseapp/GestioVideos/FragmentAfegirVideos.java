package com.example.nurseapp.GestioVideos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nurseapp.R;

public class FragmentAfegirVideos extends Fragment {

    public FragmentAfegirVideos() { super(R.layout.fragment_afegir_videos); }

    EditText idTitolCa, idDescCa, idCategoriaCa, idTituloEs, idDescEs, idCategoriaEs, idTitleEn, idDescEn, idCategoryEn,urlTot;
    Button btnAfegir;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_afegir_videos, container, false);

        idTitolCa = v.findViewById(R.id.idTitolCa);
        idDescCa = v.findViewById(R.id.idDescripcioCa);
        idCategoriaCa = v.findViewById(R.id.idCategoriaCa);
        idTituloEs = v.findViewById(R.id.idTituloEs);
        idDescEs = v.findViewById(R.id.idDescripcionEs);
        idCategoriaEs = v.findViewById(R.id.idCategoriaEs);
        idTitleEn = v.findViewById(R.id.idTitleEn);
        idDescEn = v.findViewById(R.id.idDescriptionEn);
        idCategoryEn = v.findViewById(R.id.idCategoryEn);
        btnAfegir = v.findViewById(R.id.idButtonAfegir);
        urlTot = v.findViewById(R.id.idUrl);

        // Botó onClick per tal de passar al mètode Afegir de la classe AfegirVideos les dades.
        btnAfegir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AfegirVideos a = (AfegirVideos) getActivity();
                a.Afegir(idTitolCa.getText().toString(), idDescCa.getText().toString(), idCategoriaCa.getText().toString(),
                        idTituloEs.getText().toString(), idDescEs.getText().toString(), idCategoriaEs.getText().toString(),
                        idTitleEn.getText().toString(), idDescEn.getText().toString(), idCategoryEn.getText().toString()
                        , urlTot.getText().toString());
            }
        });

        return v;
    }

}
