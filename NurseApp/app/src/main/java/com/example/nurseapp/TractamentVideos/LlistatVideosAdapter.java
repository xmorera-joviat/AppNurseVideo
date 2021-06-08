package com.example.nurseapp.TractamentVideos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nurseapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe per tal de construir el RecyclerView.Adapter que utilitzem per a mostrar tot el llistat dels vídeos.
 */
public class LlistatVideosAdapter extends FirebaseRecyclerAdapter<Video, LlistatVideosAdapter.LlistatVideosViewHolder> {

    //Inicialització de les variables
    private List<Video> llistatsVideos;
    private DatabaseReference mDatabase;

    //Constructor.
    public LlistatVideosAdapter(Class<Video> modelClass, int modelLayout, Class<LlistatVideosViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    /**
     * Mètode per tal d'inflar la vista amb el Layrer layout.
     * @param parent ViewGroup
     * @param viewType int
     * @return LlistatVideosViewHolder
     */
    @NonNull
    @Override
    public LlistatVideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.llista_videos, parent, false);
        return new LlistatVideosViewHolder(v);
    }

    /**
     * Amb aquest mètode obtenim la posició dels elements del List llistatVideo i amb el holder indiquem el que volem obtenir.
     * @param holder LlistatVideosViewHolder
     * @param position int
     */
    @Override
    protected void populateViewHolder(LlistatVideosViewHolder holder, Video video, int position) {
        Video llistatVideo = llistatsVideos.get(position);

        holder.texTitolVideos.setText(llistatVideo.getTitol());
        holder.texDescVideos.setText(llistatVideo.getDescVideo());

        holder.btnPlayVideo.setOnClickListener(new TouchElement(position));
        holder.btnVisibilidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> VideoMap = new HashMap<>();
                VideoMap.put("Mostrar",0);
                mDatabase.child("Videos").updateChildren(VideoMap);
            }
        });

    }

    /**
     * @return size de la List videos
     */
    @Override
    public int getItemCount() {
        return llistatsVideos.size();
    }

    /**
     * Classe LlistatVideosViewHolder
     */
    public class LlistatVideosViewHolder extends RecyclerView.ViewHolder {
        //Inicialització de les variables
        private TextView texTitolVideos;
        private TextView texDescVideos;
        private Button btnPlayVideo;
        private  Button btnVisibilidad;

        //Vinculem les variables amb els corresponents objectes de l'apartat gràfic.
        public LlistatVideosViewHolder(@NonNull View itemView) {
            super(itemView);
            texTitolVideos = (TextView) itemView.findViewById(R.id.idTextVideos);
            texDescVideos = (TextView) itemView.findViewById(R.id.idDesc);
            btnPlayVideo =  (Button) itemView.findViewById(R.id.idBtnVideos);
            btnVisibilidad = (Button) itemView.findViewById(R.id.idBtnVisibility);
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }

    }

    /**
     * Classe TouchElement que fem servir cada cop que seleccionem el botó corresponent "play"
     * per tal d'indicar quina url s'ha d'enviar a l'activity ApiYoutube.
     */
    class  TouchElement extends LlistatVideos implements View.OnClickListener{
        int position;

        public TouchElement(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(v.getContext(), ApiYoutube.class);
            Bundle b = new Bundle();

            b.putString("url", llistatsVideos.get(position).getUrlVideo());
            i.putExtras(b);

            v.getContext().startActivity(i);
        }

    }

}