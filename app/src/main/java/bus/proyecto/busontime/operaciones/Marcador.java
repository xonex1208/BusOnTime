package bus.proyecto.busontime.operaciones;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.internal.zzp;


/**
 * Created by masdz on 31/10/2017.
 */

public class Marcador{
    Marker marcador;
    String id;
    public Marcador(String id,Marker marcador) {
        this.marcador=marcador;
        this.id=id;
    };

    public Marker getMarcador() {
        return marcador;
    }

    public void setMarcador(Marker marcador) {
        this.marcador = marcador;
    }

    public void setPosicion(LatLng posicion){
        this.marcador.setPosition(posicion);
    }

    public void setPosicion(Cordenadas cor){
        this.marcador.setPosition(new LatLng(cor.getLatitud(),cor.getLongitud()));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
