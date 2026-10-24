package modelo;

public abstract class Usuario {

    protected String nombre;
    protected String apellido;
    protected String documento;

    protected String password;
    protected boolean activo;
    protected boolean debeCambiarPassword;
    protected String msjsancion;

    public Usuario(String nombre, String apellido, String documento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;

        this.password = documento;
        this.activo = true;
        this.debeCambiarPassword = true;
        this.msjsancion = "";
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDocumento() {
        return documento;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActivo() {
        return activo;
    }

    public boolean isDebeCambiarPassword() {
        return debeCambiarPassword;
    }


    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setMensajeSancion(String mensajeSancion) {
        this.msjsancion = mensajeSancion;
    }

    public String getMensajeSancion() {
        return msjsancion;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setDebeCambiarPassword(boolean debeCambiarPassword) {
        this.debeCambiarPassword = debeCambiarPassword;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}

