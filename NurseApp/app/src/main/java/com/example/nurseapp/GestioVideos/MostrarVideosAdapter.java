package com.example.nurseapp.GestioVideos;

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
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe per tal de construir el RecyclerView.Adapter que utilitzem per a mostrar tot el llistat dels vídeos.
 */
public class MostrarVideosAdapter extends FirebaseRecyclerAdapter<Video, MostrarVideosAdapter.MostrarVideosViewHolder> {

    //Inicialització de les variables
    private DatabaseReference mDatabase;

    //Constructor.
    public MostrarVideosAdapter(@NonNull FirebaseRecyclerOptions<Video> options) {
        super(options);
    }

    /**
     * Classe LlistatVideosViewHolder
     */
    public class MostrarVideosViewHolder extends RecyclerView.ViewHolder {
        //Inicialització de les variables
        private TextView titol;
        private TextView desc;
        private Button btnPlayVideo;
        private  Button btnVisibilidad;
        private  Button btnVisibilidadOff;

        //Vinculem les variables amb els corresponents objectes de l'apartat gràfic.
        public MostrarVideosViewHolder(@NonNull View itemView) {
            super(itemView);
            titol = (TextView) itemView.findViewById(R.id.idTextVideos);
            desc = (TextView) itemView.findViewById(R.id.idDescripcioCa);
            btnPlayVideo =  (Button) itemView.findViewById(R.id.idBtnVideos);
            btnVisibilidad = (Button) itemView.findViewById(R.id.idBtnVisibility);
            btnVisibilidadOff = (Button) itemView.findViewById(R.id.idBtnVisibility_off);
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }

    }

    /**
     * Mètode per tal d'inflar la vista amb el Layrer layout.
     * @param parent ViewGroup
     * @param viewType int
     * @return LlistatVideosViewHolder
     */
    @NonNull
    @Override
    public MostrarVideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.llista_videos_mostrar, parent, false);
        return new MostrarVideosViewHolder(v);
    }

    /**
     * Amb aquest mètode obtenim la posició dels elements del List llistatVideo i amb el holder indiquem el que volem obtenir.
     * @param holder LlistatVideosViewHolder
     * @param position int
     */
    @Override
    protected void onBindViewHolder(@NonNull MostrarVideosViewHolder holder, int position, @NonNull Video video) {
        holder.titol.setText(video.getTitol());
        holder.desc.setText(video.getDescVideo());
        holder.btnPlayVideo.setOnClickListener(new TouchElement(video.getUrlVideo().toString()));

        // Visualitzem la situació dels vídeos per defecte.
        if (video.getMostrar() == 1){
            holder.btnVisibilidad.setVisibility(View.VISIBLE);
            holder.btnVisibilidadOff.setVisibility(View.GONE);
        }
        else if (video.getMostrar() == 0){
            holder.btnVisibilidadOff.setVisibility(View.VISIBLE);
            holder.btnVisibilidad.setVisibility(View.GONE);
        }

        holder.btnVisibilidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(video.getMostrar() == 1) {
                    holder.btnVisibilidad.setVisibility(View.GONE);
                    holder.btnVisibilidadOff.setVisibility(View.VISIBLE);
                    Map<String, Object> VideoMap = new HashMap<>();
                    VideoMap.put("mostrar", 0);
                    mDatabase.child("LlistatVideosCa/" + video.getNumId()).updateChildren(VideoMap);
                    mDatabase.child("LlistatVideosEn/" + video.getNumId()).updateChildren(VideoMap);
                    mDatabase.child("LlistatVideosEs/" + video.getNumId()).updateChildren(VideoMap);
                }
            }
        });

        holder.btnVisibilidadOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (video.getMostrar() == 0) {
                    holder.btnVisibilidadOff.setVisibility(View.GONE);
                    holder.btnVisibilidad.setVisibility(View.VISIBLE);
                    Map<String, Object> VideoMap = new HashMap<>();
                    VideoMap.put("mostrar", 1);
                    mDatabase.child("LlistatVideosCa/" + video.getNumId()).updateChildren(VideoMap);
                    mDatabase.child("LlistatVideosEn/" + video.getNumId()).updateChildren(VideoMap);
                    mDatabase.child("LlistatVideosEs/" + video.getNumId()).updateChildren(VideoMap);
                }
            }
        });
    }


    /**
     * Classe TouchElement que fem servir cada cop que seleccionem el botó corresponent "play"
     * per tal d'indicar quina url s'ha d'enviar a l'activity ApiYoutube.
     */
    class  TouchElement extends LlistatVideos implements View.OnClickListener{
        String url;

        public TouchElement(String url) {
            this.url =url;
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(v.getContext(), ApiYoutube.class);
            Bundle b = new Bundle();

            b.putString("url", url);
            i.putExtras(b);

            v.getContext().startActivity(i);
        }

    }

}
