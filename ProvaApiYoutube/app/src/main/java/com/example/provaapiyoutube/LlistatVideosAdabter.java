package com.example.provaapiyoutube;


import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Classe que utilitzem per a fer tot el tema del RecyclerView.
 */
public class LlistatVideosAdabter extends RecyclerView.Adapter<LlistatVideosAdabter.LlistatVideosViewHolder> {

    //Variable list de tipus LlistatVideos (fa referència a la classe "LlistatVideos").

    private List<LlistatVideos> llistatsVideos;

    //Constructor

    public LlistatVideosAdabter(List<LlistatVideos> llistatsVideos){

    this.llistatsVideos = llistatsVideos;



    }



    //Inflem la vista amb el Layrer layout i la retornem.

    @NonNull
    @Override
    public LlistatVideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new LlistatVideosViewHolder(v);

    }

    //Amb aquest mètode obtenim la posició dels elements del llistatVideo,
    // amb el holder indiquem el que volem obtenir.

    @Override
    public void onBindViewHolder(@NonNull LlistatVideosViewHolder holder, int position) {

        LlistatVideos llistatVideo = llistatsVideos.get(position);

        holder.texTitolVideos.setText(llistatVideo.getTitol());

        //Amb la següent línia indiquem que per cada holder volem aplicar el setOnTouchisterner
        // per tal de poder tenir el canvi de text a descripció.

        holder.texTitolVideos.setOnTouchListener(new TouchElement());


    }

    //Retornem la mida de la llista.

    @Override
    public int getItemCount() {

        return llistatsVideos.size();

    }


    /**
     * Per tal d'aplicar el tema de canvi de text a descripció amb una acció de moviment
     * hem de crear una classe nova que extens de RecyclerView.ViewHolder.
     */
    public class LlistatVideosViewHolder extends RecyclerView.ViewHolder {

        //Variable de tipus TextView que fa referència al títol del vídeo.

        private TextView texTitolVideos;



        //Hem de relacionar el texTitolVideosamb l'itemView.

        public LlistatVideosViewHolder(@NonNull View itemView) {
            super(itemView);
            texTitolVideos = (TextView) itemView.findViewById(R.id.idTextVideos);
        }


    }

    /**
     *F inalment hem d'implementar la classe TouchElement.
     */
    class TouchElement implements View.OnTouchListener {

            //

        /**
         * Amb el mètode onTouch controlem les accions sobre el texTitolVideos.
         *
         *Per tal que doni temps a què l'usuari pugui veure la descripció del vídeo fem ús del Run,
         * ja que aquesta part necessita que sigui multitasca,
         * donant una espera de 2 segons, sense interrompre la navegació de l'usuari.
         *
         * @param v View
         * @param event l'event es l'acció que realitza l'usuari
         * @return boolea
         */
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if(event.getAction() == MotionEvent.ACTION_MOVE)
            {

             final   LlistatVideosViewHolder test = new LlistatVideosViewHolder(v);

                 test.texTitolVideos.setText("Descripció");

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        test.texTitolVideos.setText("Títol");
                    }
                }, 2000);



            }


            return true;
        }
    }

}
