package bus.proyecto.busontime.operaciones;

/**
 * Created by masdz on 28/10/17.
 */

public class Cordenadas {
    private String id;
    private double latitud;
    private double longitud;
    private double velocidad;

    public Cordenadas(String id, double latitud, double longitud, double vel) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.velocidad=vel;

    }

    public Cordenadas() {
        this.id="0";
        this.latitud = 0;
        this.longitud = 0;
        this.velocidad=0;
    }

    public Cordenadas(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double vel) {
        this.velocidad = vel;
    }

    public String desp(){
        return "ID: "+id+"\n" +
                "Latitud: "+latitud+"\n" +
                "Longitud: "+longitud+"\n" +
                "Velocidad: "+velocidad;
    }
}
