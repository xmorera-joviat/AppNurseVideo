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

import java.util.List;

/**
 * Classe per tal de construir el RecyclerView.Adapter que utilitzem per a mostrar tot el llistat dels vídeos.
 */
public class LlistatVideosAdapter extends RecyclerView.Adapter<LlistatVideosAdapter.LlistatVideosViewHolder>{

    //Inicialització de les variables
    private List<LlistatVideos> llistatsVideos;

    //Constructor.
    public LlistatVideosAdapter(List<LlistatVideos> llistatsVideos){

        this.llistatsVideos = llistatsVideos;

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.llista_items, parent, false);
        return new LlistatVideosViewHolder(v);
    }

    //

    /**
     * Amb aquest mètode obtenim la posició dels elements del List llistatVideo i amb el holder indiquem el que volem obtenir.
     * @param holder LlistatVideosViewHolder
     * @param position int
     */
    @Override
    public void onBindViewHolder(@NonNull LlistatVideosViewHolder holder, int position) {
        LlistatVideos llistatVideo = llistatsVideos.get(position);

        holder.texTitolVideos.setText(llistatVideo.getTexTitolVideos());
        holder.texDescVideos.setText(llistatVideo.getDescVideo());

        holder.btnPlayVideo.setOnClickListener(new TouchElement(position));
    }

    /**
     * @return size de la List llistatVideos
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

        //Vinculem les variables amb els corresponents objectes de l'apartat gràfic.
        public LlistatVideosViewHolder(@NonNull View itemView) {
            super(itemView);
            texTitolVideos = (TextView) itemView.findViewById(R.id.idTextVideos);
            texDescVideos = (TextView) itemView.findViewById(R.id.idDesc);
            btnPlayVideo =  (Button) itemView.findViewById(R.id.idBtnVideos);
        }

    }


    /**
     * Classe TouchElement que fem servir cada cop que seleccionem el botó corresponent "play"
     * per tal d'indicar quina url s'ha d'enviar a l'activity ApiYoutube.
     */
    class  TouchElement extends LlistatVideosPrincipal implements View.OnClickListener{
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