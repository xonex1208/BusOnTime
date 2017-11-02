package bus.proyecto.busontime.operaciones;

import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by masdz on 31/10/2017.
 */

public class Marcador{
    private MarkerOptions marcador;
    private int id;

    public Marcador(int id){
        marcador=new MarkerOptions();
       // marcador.icon(imagen);
        this.id=id;
    }

    public MarkerOptions getMarcador() {
        return marcador;
    }

    public int getId(){
        return id;
    }

    public void eliminar(){
        marcador=null;
    }

    public void setPosicion(Cordenadas cor){
        LatLng pos=new LatLng(cor.getLatitud(),cor.getLongitud());

        marcador.position(pos);
    }

    public void setTitle(String titulo){
        marcador.title(titulo);
    }
}
