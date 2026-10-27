package modelo;

import java.util.ArrayList;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.regex.Pattern;

public class Instituto {

    private ArrayList<Usuario> usuarios;
    private ArrayList<Curso> cursos;
    private final HttpClient client;

    public Instituto() {
        usuarios = new ArrayList<>();
        cursos = new ArrayList<>();
        this.client = HttpClient.newHttpClient();
    }

    private String enviarPeticionHttpGet(String urlString) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void agregarUsuario(Usuario usuario) {
        try {
            String rol = "Administrador";
            if (usuario instanceof Alumno) rol = "Alumno";
            if (usuario instanceof Docente) rol = "Docente";

            String url = "http://localhost/instituto_api/guardar_usuario.php?"
                    + "documento=" + URLEncoder.encode(usuario.getDocumento(), "UTF-8")
                    + "&nombre=" + URLEncoder.encode(usuario.getNombre(), "UTF-8")
                    + "&apellido=" + URLEncoder.encode(usuario.getApellido(), "UTF-8")
                    + "&rol=" + URLEncoder.encode(rol, "UTF-8")
                    + "&password=" + URLEncoder.encode(usuario.getPassword(), "UTF-8");

            String r = enviarPeticionHttpGet(url);
            if (r.contains("¡Éxito!")) {
                System.out.println("API Usuario registrado correctamente.");
                this.usuarios.add(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Usuario> getUsuarios() { return usuarios; }
    public int cantidadUsuarios() { return usuarios.size(); }

    public void agregarCurso(Curso nuevoCurso) {
        try {
            String urlString = "http://localhost/instituto_api/guardar_curso.php?"
                    + "id=" + URLEncoder.encode(nuevoCurso.getId(), "UTF-8")
                    + "&nombre=" + URLEncoder.encode(nuevoCurso.getNombre(), "UTF-8")
                    + "&descripcion=" + URLEncoder.encode(nuevoCurso.getDescripcion(), "UTF-8")
                    + "&cupo_maximo=" + nuevoCurso.getCupoMaximo()
                    + "&estado=" + URLEncoder.encode(nuevoCurso.getEstado(), "UTF-8")
                    + "&documento_docente=" + URLEncoder.encode(nuevoCurso.getDocumentoDocente(), "UTF-8");

            String r = enviarPeticionHttpGet(urlString);
            System.out.println(" [API PHP Response]: " + r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Curso> getCursos() { return this.cursos; }
    public void cargarCursosDePrueba() { this.cursos.clear(); }

    public Usuario buscarUsuarioPorDni(String dni) {
        ArrayList<Usuario> todos = obtenerTodosLosUsuariosDeBaseDeDatos();
        for (Usuario u : todos) {
            if (u.getDocumento().trim().equalsIgnoreCase(dni.trim())) {
                return u;
            }
        }
        return null;
    }

    public boolean actualizarContraseniaEnBaseDeDatos(String dniUsuario, String nuevaPassword) {
        try {
            String dniLimpio = dniUsuario.trim().equals("0000") ? "admin" : dniUsuario.trim();
            String url = "http://localhost/instituto_api/actualizar_usuario.php?"
                    + "documento=" + java.net.URLEncoder.encode(dniLimpio, "UTF-8")
                    + "&password=" + java.net.URLEncoder.encode(nuevaPassword.trim(), "UTF-8");

            String respuesta = enviarPeticionHttpGet(url).trim();
            System.out.println("RESPUESTA CRUDA DE PHP: " + respuesta);

            return respuesta.equalsIgnoreCase("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public ArrayList<Usuario> obtenerTodosLosUsuariosDeBaseDeDatos() {
        ArrayList<Usuario> lista = new ArrayList<>();
        String json = enviarPeticionHttpGet("http://localhost/instituto_api/obtener_usuarios.php");
        if (json.equals("[]") || json.isEmpty()) return lista;

        try {
            json = json.substring(1, json.length() - 1);
            String[] objetos = json.split(Pattern.quote("},{"));
            for (String obj : objetos) {
                obj = obj.replace("{", "").replace("}", "");
                String[] pares = obj.split(Pattern.quote("\",\""));
                String dni = "", nom = "", ape = "", rol = "", pass = "", msj = "";
                boolean act = true;

                for (String par : pares) {
                    par = par.replace("\"", "");
                    String[] cv = par.split(":", 2);
                    if (cv.length < 2) continue;
                    String k = cv[0].trim(), v = cv[1].trim();

                    if (k.equalsIgnoreCase("documento")) dni = v;
                    else if (k.equalsIgnoreCase("nombre")) nom = v;
                    else if (k.equalsIgnoreCase("apellido")) ape = v;
                    else if (k.equalsIgnoreCase("rol")) rol = v;
                    else if (k.equalsIgnoreCase("password")) pass = v;
                    else if (k.equalsIgnoreCase("activo")) act = v.equals("1");
                    else if (k.equalsIgnoreCase("mensaje_sancion")) msj = v.equalsIgnoreCase("null") ? null : v;
                }

                if (!dni.isEmpty()) {
                    Usuario u = rol.equalsIgnoreCase("Alumno") ? new Alumno(nom, ape, dni) : new Docente(nom, ape, dni);
                    u.setPassword(pass);
                    u.setActivo(act);
                    u.setMensajeSancion(msj);
                    lista.add(u);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean modificarUsuarioEnBaseDeDatos(String dniOriginal, String nuevoNombre, String nuevoApellido, String nuevoRol) {
        try {
            String url = "http://localhost/instituto_api/actualizar_usuario.php?"
                    + "documento=" + java.net.URLEncoder.encode(dniOriginal.trim(), "UTF-8")
                    + "&nombre=" + java.net.URLEncoder.encode(nuevoNombre.trim(), "UTF-8")
                    + "&apellido=" + java.net.URLEncoder.encode(nuevoApellido.trim(), "UTF-8")
                    + "&rol=" + java.net.URLEncoder.encode(nuevoRol.trim(), "UTF-8");

            String respuesta = enviarPeticionHttpGet(url).trim();
            return respuesta.equalsIgnoreCase("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean cambiarEstadoUsuarioEnBaseDeDatos(String dni, boolean nuevoEstado, String motivoSancion) {
        try {
            String msj = (motivoSancion == null || motivoSancion.trim().isEmpty()) ? "Cuenta deshabilitada" : motivoSancion.trim();
            String url = "http://localhost/instituto_api/actualizar_usuario.php?"
                    + "documento=" + URLEncoder.encode(dni, "UTF-8")
                    + "&activo=" + (nuevoEstado ? "1" : "0")
                    + "&mensaje_sancion=" + URLEncoder.encode(nuevoEstado ? "" : msj, "UTF-8");
            return enviarPeticionHttpGet(url).contains("¡Éxito!");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean cambiarEstadoUsuarioEnBaseDeDatos(String dni, boolean nuevoEstado) {
        return cambiarEstadoUsuarioEnBaseDeDatos(dni, nuevoEstado, "Cuenta deshabilitada por la administración del Instituto.");
    }

    public ArrayList<Curso> obtenerTodosLosCursosDeBaseDeDatos() {
        ArrayList<Curso> listaCursos = new ArrayList<>();
        String jsonCursos = enviarPeticionHttpGet("http://localhost/instituto_api/obtener_cursos.php");
        if (jsonCursos.equals("[]") || jsonCursos.isEmpty()) return listaCursos;

        try {
            jsonCursos = jsonCursos.substring(1, jsonCursos.length() - 1);
            String[] objetos = jsonCursos.split(Pattern.quote("},{"));

            String jsonCorr = enviarPeticionHttpGet("http://localhost/instituto_api/obtener_relaciones.php?tipo=correlativas");
            String jsonInsc = enviarPeticionHttpGet("http://localhost/instituto_api/obtener_relaciones.php?tipo=inscripciones");

            for (String obj : objetos) {
                obj = obj.replace("{", "").replace("}", "");
                String[] pares = obj.split(Pattern.quote("\",\""));
                String id = "", nombre = "", descripcion = "", estado = "", doc = "";
                int cupo = 0;

                for (String par : pares) {
                    par = par.replace("\"", "");
                    String[] cv = par.split(":", 2);
                    if (cv.length < 2) continue;
                    String k = cv[0].trim(), v = cv[1].trim();

                    if (k.equalsIgnoreCase("id")) id = v;
                    else if (k.equalsIgnoreCase("nombre")) nombre = v;
                    else if (k.equalsIgnoreCase("descripcion")) descripcion = v;
                    else if (k.equalsIgnoreCase("cupo_maximo")) cupo = Integer.parseInt(v);
                    else if (k.equalsIgnoreCase("estado")) estado = v;
                    else if (k.equalsIgnoreCase("documento_docente")) doc = v.equalsIgnoreCase("null") ? "" : v;
                }

                if (!id.isEmpty()) {
                    Curso c = new Curso(id, nombre, descripcion, cupo, doc);
                    c.setEstado(estado);

                    if (jsonCorr.contains(id)) {
                        String[] items = jsonCorr.split(Pattern.quote("}"));
                        for (String item : items) {
                            if (item.contains("\"id_curso\":\"" + id + "\"")) {
                                String[] cv = item.split("\"id_correlativa\":\"");
                                if (cv.length > 1) {
                                    c.getIdsCorrelativas().add(cv[1].split("\"")[0].trim());
                                }
                            }
                        }
                    }

                    if (jsonInsc.contains(id)) {
                        String[] items = jsonInsc.split(Pattern.quote("}"));
                        for (String item : items) {
                            if (item.contains("\"id_curso\":\"" + id + "\"")) {
                                String[] partAlum = item.split("\"documento_alumno\":\"");
                                String[] partEst = item.split("\"estado_inscripcion\":\"");

                                if (partAlum.length > 1 && partEst.length > 1) {
                                    String alum = partAlum[1].split("\"")[0].trim();
                                    String estIns = partEst[1].split("\"")[0].trim();

                                    if (estIns.equalsIgnoreCase("Inscripto")) {
                                        c.getAlumnosInscritos().add(alum);
                                    } else if (estIns.startsWith("Aprobado")) {
                                        String notaLimpia = estIns.replace("Aprobado (", "Nota: ");
                                        c.getAlumnosAprobados().add(alum + " (" + notaLimpia);
                                    } else if (estIns.startsWith("Desaprobado")) {
                                        String motivoLimpio = estIns.replace("Desaprobado (", "Motivo: ");
                                        c.getAlumnosDesaprobados().add(alum + " (" + motivoLimpio);
                                    }
                                }
                            }
                        }
                    }
                    listaCursos.add(c);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCursos;
    }

    public boolean actualizarEstadoCursoEnBaseDeDatos(String idCurso, String nuevoEstado) {
            try {
                String url = "http://localhost/instituto_api/guardar_relacion.php?tipo=estado_curso"
                        + "&id_curso=" + URLEncoder.encode(idCurso, "UTF-8")
                        + "&estado=" + URLEncoder.encode(nuevoEstado, "UTF-8");
                return enviarPeticionHttpGet(url).contains("¡Éxito!");
            } catch (Exception e) {
                return false;
            }
        }
        public boolean inscribirAlumnoEnBaseDeDatos(String dniAlumno, String idCurso) {
            try {
                String url = "http://localhost/instituto_api/guardar_relacion.php?tipo=inscripcion"
                        + "&documento_alumno=" + URLEncoder.encode(dniAlumno, "UTF-8")
                        + "&id_curso=" + URLEncoder.encode(idCurso, "UTF-8");
                return enviarPeticionHttpGet(url).contains("¡Éxito!");
            } catch (Exception e) {
                return false;
            }
        }
        public boolean registrarCalificacionAlumno(String dniAlumno, String idCurso, String estadoAcademico, String detalleNota) {
            try {
                String valorEstado = estadoAcademico + " (" + detalleNota + ")";
                String url = "http://localhost/instituto_api/guardar_relacion.php?tipo=calificacion"
                        + "&documento_alumno=" + URLEncoder.encode(dniAlumno, "UTF-8")
                        + "&id_curso=" + URLEncoder.encode(idCurso, "UTF-8")
                        + "&estado_inscripcion=" + URLEncoder.encode(valorEstado, "UTF-8");
                return enviarPeticionHttpGet(url).contains("¡Éxito!");
            } catch (Exception e) {
                return false;
            }
        }
        public boolean agregarCorrelativaEnBaseDeDatos(String idCurso, String idCorrelativa) {
            try {
                String url = "http://localhost/instituto_api/guardar_relacion.php?tipo=correlativa"
                        + "&id_curso=" + URLEncoder.encode(idCurso, "UTF-8")
                        + "&id_correlativa=" + URLEncoder.encode(idCorrelativa, "UTF-8");
                return enviarPeticionHttpGet(url).contains("¡Éxito!");
            } catch (Exception e) {
                return false;
            }
        }
    }