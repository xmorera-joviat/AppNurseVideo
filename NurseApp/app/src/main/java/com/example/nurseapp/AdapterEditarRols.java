package com.example.nurseapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe encarregada de donar l'aparença desitjada al recyclerView d'artistes mostrant les dades corresponents.
 */
public class AdapterEditarRols extends FirestoreRecyclerAdapter<UserInfo, AdapterEditarRols.ViewHolderUserInfo> {

    // Variables a utilitzar :
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    // Constructor que inicialitza l'Adapter del RecyclerView.
    // Li passarem l'ArrayList que conté les dades dels elements.
    public AdapterEditarRols(@NonNull FirestoreRecyclerOptions<UserInfo> options) {
        super(options);
    }

    // Crea les noves vistes dels elements del RecyclerView.
    // Aquest mètode és cridat pel LayoutManager.
    @NonNull
    @Override
    public ViewHolderUserInfo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.llista_rols, parent, false);

        return new ViewHolderUserInfo(view);
    }

    // Mètode que dóna contingut a les vistes dels elements del RecyclerView.
    // Aquest mètode el crida el LayoutManager.
    @Override
    protected void onBindViewHolder(@NonNull ViewHolderUserInfo holder, int position, @NonNull UserInfo userInfo) {
        //holder.userUid.setText(userInfo.getUid());

        ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(holder.itemView.getContext(),R.array.rols, android.R.layout.simple_spinner_item);
        holder.rolSpinner.setAdapter(adp);
        holder.rolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Canviem el rol de la compte.
                //DocumentReference df = fStore.collection("Users").document(userInfo.getUid());
                Map<String,Object> rol = new HashMap<>();
                rol.put("rol",userInfo.getRol());
                //df.set(rol);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // Retorna la quantitat de registres
    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    // Classe "holder" que proporciona una referència a les vistes de cada element
    // de la llista.
    public class ViewHolderUserInfo extends RecyclerView.ViewHolder {
        TextView userUid;
        Spinner rolSpinner;

        public ViewHolderUserInfo(@NonNull View itemView) {
            super(itemView);
            userUid = (TextView) itemView.findViewById(R.id.userEmail);
            rolSpinner = (Spinner) itemView.findViewById(R.id.rolSpinner);
        }
    }
}