package helpers.cristian.com.ubiety.modelos;

public class Usuario {
    protected int id;
    protected String usuario;
    protected String pass;
    protected String nombres;
    protected String apellidos;
    protected String rol;

    public Usuario(int id, String usuario, String pass, String nombres, String apellidos, String rol) {
        this.id = id;
        this.usuario = usuario;
        this.pass = pass;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
