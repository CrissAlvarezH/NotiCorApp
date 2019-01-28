package helpers.cristian.com.ubiety.modelos;

public class Bloque {
    private int id;
    private String nombre, codigo, urlImg;
    private int cantSalones;


    public Bloque(int id, String nombre, String codigo, String urlImg, int cantSalones) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.urlImg = urlImg;
        this.cantSalones = cantSalones;
    }

    public int getCantSalones() {
        return cantSalones;
    }

    public void setCantSalones(int cantSalones) {
        this.cantSalones = cantSalones;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
