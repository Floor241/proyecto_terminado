package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import modelo.Usuario;

import modelo.*;

public class paneladmin extends JFrame {

    private Instituto instituto;
    private JLabel titulo1;
    private JTabbedPane tabbedPane1;
    private JPanel usuarios;
    private JTextField buscador;
    private JTable tablauser;
    private JLabel Titulo7;
    private JLabel titulo8;
    private JTextField textField1;
    private JLabel nombre;
    private JTextField nom;
    private JLabel tipo;
    private JComboBox comboBox1;
    private JLabel estado;
    private JLabel aod;
    private JButton registrar;
    private JButton editar1;
    private JButton Resetear;
    private JButton deshabilitar;
    private JButton buscar;
    private JLabel totalU;

    private JLabel lista;
    private JTextField buscarcur;
    private JButton buscarcurso;
    private JTable table2;
    private JLabel nombre1;
    private JLabel descripcion;
    private JLabel docente;
    private JLabel estado1;
    private JLabel cupo;
    private JLabel requisito;
    private JLabel inscriptos;
    private JTextField nombretext;
    private JTextField doctext;
    private JLabel estadoacosus;
    private JLabel cupocant;
    private JLabel insccant;
    private JTextArea descrp;
    private JTextArea requi;
    private JButton aprobarCursoButton;
    private JButton cancelarCursoButton;
    private JButton verAlumnosButton;
    private JLabel total;
    private JLabel totalc;

    private JLabel titulo4;
    private JLabel nueva;
    private JLabel conf;
    private JPasswordField nuevac;
    private JPasswordField Confc;
    private JButton guardar;
    private JLabel admiactual;
    private JLabel documento;
    private JLabel doc;
    private JLabel admi;
    private JLabel roladmi;
    private JLabel rol;
    private JButton cerrarsesion;
    private JLabel selec;
    private JComboBox users;
    private JButton resetearContraseñaButton;
    private JButton deshabilitarCuentaButton;
    private JLabel msj;
    private JTextArea msjsancion;
    private JLabel titulo2;
    private JButton cerrar;
    private JButton habilitarCuenta;

    private JPanel panelPrincipal;
    private Registro ventanaRegistroActual = null;


    public paneladmin(Instituto instituto) {
        this.instituto = instituto;
        $$$setupUI$$$();

        setTitle("Panel de Control - Administrador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (panelPrincipal == null) {
            setContentPane(tabbedPane1);
        } else {
            setContentPane(panelPrincipal);
        }

        setSize(1100, 750);
        setLocationRelativeTo(null);
        actualizarTablasAdmin();

        DefaultTableModel modeloInicial = new DefaultTableModel();
        modeloInicial.addColumn("Documento");
        modeloInicial.addColumn("Nombre");
        modeloInicial.addColumn("Tipo");
        modeloInicial.addColumn("Estado");
        tablauser.setModel(modeloInicial);


        tablauser.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablauser.getSelectedRow() != -1) {
                int filaSeleccionada = tablauser.getSelectedRow();
                String dni = tablauser.getValueAt(filaSeleccionada, 0).toString();
                String nombreTxt = tablauser.getValueAt(filaSeleccionada, 1).toString();
                String apellidoTxt = tablauser.getValueAt(filaSeleccionada, 2).toString();
                String rolTxt = tablauser.getValueAt(filaSeleccionada, 3).toString();


                textField1.setText(dni);
                nom.setText(nombreTxt + " " + apellidoTxt);
                comboBox1.setSelectedItem(rolTxt);

                if (this.getInstituto() != null) {

                    Usuario u = this.getInstituto().buscarUsuarioPorDni(dni);

                    if (u != null) {
                        if (u.isActivo()) {
                            aod.setText("Activa");
                            aod.setForeground(new java.awt.Color(0, 150, 0)); // Verde
                        } else {
                            aod.setText("Deshabilitada");
                            aod.setForeground(java.awt.Color.RED); // Rojo
                        }
                    } else {
                        aod.setText("Sin seleccionar");
                        aod.setForeground(java.awt.Color.BLACK);
                    }
                }
            }
        });


        deshabilitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablauser.getSelectedRow();
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un usuario de la lista de la izquierda.");
                    return;
                }

                String dni = tablauser.getValueAt(filaSeleccionada, 0).toString();
                String nombreTxt = tablauser.getValueAt(filaSeleccionada, 1).toString();

                if (getInstituto() != null) {
                    boolean exito = getInstituto().cambiarEstadoUsuarioEnBaseDeDatos(dni, false);

                    if (exito) {
                        JOptionPane.showMessageDialog(null, "La cuenta de " + nombreTxt + " ha sido deshabilitada.");


                        aod.setText("Deshabilitada");
                        aod.setForeground(java.awt.Color.RED);

                        cargarUsuarios("");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo actualizar el estado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


        habilitarCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablauser.getSelectedRow();
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un usuario de la lista de la izquierda para reactivar su cuenta.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String dniSeleccionado = tablauser.getValueAt(filaSeleccionada, 0).toString();
                String nombreSeleccionado = tablauser.getValueAt(filaSeleccionada, 1).toString();


                if (getInstituto() != null) {
                    boolean exito = getInstituto().cambiarEstadoUsuarioEnBaseDeDatos(dniSeleccionado, true); // true = Activar cuenta

                    if (exito) {
                        JOptionPane.showMessageDialog(null, "La cuenta de " + nombreSeleccionado + " ha sido reactivada con éxito.", "Sanción Levantada", JOptionPane.INFORMATION_MESSAGE);
                        aod.setText("Activa");
                        aod.setForeground(new java.awt.Color(0, 150, 0));
                        cargarUsuarios("");

                        tablauser.setRowSelectionInterval(filaSeleccionada, filaSeleccionada);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo activar ", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoFiltrado = buscador.getText();

                cargarUsuarios(textoFiltrado);
            }
        });


        cerrarsesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int respuesta = JOptionPane.showConfirmDialog(null,
                        "¿Está seguro de que desea cerrar su sesión actual?",
                        "Cerrar Sesión", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (respuesta == JOptionPane.YES_OPTION) {


                    if (loginadmin.instanciaLogin != null) {
                        loginadmin.instanciaLogin.setVisible(true);
                    } else {

                        new loginadmin().setVisible(true);
                    }

                    dispose();
                }
            }
        });


        editar1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablauser.getSelectedRow();
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un usuario de la tabla para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String dniOriginal = tablauser.getValueAt(filaSeleccionada, 0).toString();
                String nombreCompleto = nom.getText().trim();
                if (nombreCompleto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar el nombre y apellido.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String nuevoNombre = "";
                String nuevoApellido = "";
                String[] partes = nombreCompleto.split("\\s+");

                if (partes.length >= 2) {
                    nuevoNombre = partes[0];
                    StringBuilder apellidoBuilder = new StringBuilder();
                    for (int i = 1; i < partes.length; i++) {
                        apellidoBuilder.append(partes[i]).append(" ");
                    }
                    nuevoApellido = apellidoBuilder.toString().trim();
                } else {
                    nuevoNombre = partes[0];
                    nuevoApellido = "";
                }
                String nuevoRol = comboBox1.getSelectedItem().toString();
                if (getInstituto() != null) {
                    boolean exito = getInstituto().modificarUsuarioEnBaseDeDatos(dniOriginal, nuevoNombre, nuevoApellido, nuevoRol);
                    if (exito) {
                        JOptionPane.showMessageDialog(null, "Usuario modificado correctamente ", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        nom.setText("");
                        cargarUsuarios("");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo actualizar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        Resetear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablauser.getSelectedRow();
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un usuario de la lista para restablecer su clave.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String dniSeleccionado = tablauser.getValueAt(filaSeleccionada, 0).toString();
                String nombreSeleccionado = tablauser.getValueAt(filaSeleccionada, 1).toString();
                if (getInstituto() != null) {
                    boolean exito = getInstituto().actualizarContraseniaEnBaseDeDatos(dniSeleccionado, dniSeleccionado);
                    if (exito) {
                        cargarUsuarios("");
                        textField1.setText("");
                        nom.setText("");
                        aod.setText("Sin seleccionar");
                        comboBox1.setSelectedIndex(0);
                        JOptionPane.showMessageDialog(null,
                                "La contraseña de " + nombreSeleccionado + " ha sido blanqueada.\n" +
                                        "Ahora es igual a su DNI (" + dniSeleccionado + ")",
                                "Blanqueo Exitoso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo actualizar la contraseña .", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaRegistroActual = new Registro(paneladmin.this);
                ventanaRegistroActual.setVisible(true);
            }
        });
        deshabilitarCuentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (users.getSelectedItem() == null || users.getSelectedIndex() <= 0) {
                    JOptionPane.showMessageDialog(null, "Seleccione un usuario de la lista desplegable.");
                    return;
                }
                String seleccionado = users.getSelectedItem().toString();
                String dniSeleccionado = seleccionado.split(" - ")[0].trim();
                String motivo = msjsancion.getText().trim();
                if (motivo.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Debe escribir obligatoriamente el motivo de la sanción en el cuadro de texto inferior.",
                            "Falta Mensaje de Sanción",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Instituto institutoReal = (modelo.Sistema.getInstituto() != null) ? modelo.Sistema.getInstituto() : getInstituto();
                if (institutoReal == null) institutoReal = new Instituto();
                boolean exito = institutoReal.cambiarEstadoUsuarioEnBaseDeDatos(dniSeleccionado, false, motivo);
                if (exito) {
                    cargarUsuarios("");
                    textField1.setText("");
                    nom.setText("");
                    aod.setText("Sin seleccionar");
                    msjsancion.setText("");
                    users.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(null, "Sanción personalizada aplicada con éxito.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo actualizar el estado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevaContra = new String(nuevac.getPassword()).trim();
                String confirmarContra = new String(Confc.getPassword()).trim();

                if (nuevaContra.isEmpty() || confirmarContra.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete ambos campos de contraseña.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!nuevaContra.equals(confirmarContra)) {
                    JOptionPane.showMessageDialog(null, "Las contraseñas ingresadas no coinciden. Verifique los datos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Instituto institutoReal = (modelo.Sistema.getInstituto() != null) ? modelo.Sistema.getInstituto() : getInstituto();
                if (institutoReal == null) institutoReal = new Instituto();

                boolean exito = institutoReal.actualizarContraseniaEnBaseDeDatos("admin", nuevaContra);

                if (exito) {
                    Sistema.passwordAdmin = nuevaContra;
                    JOptionPane.showMessageDialog(null,
                            "¡Su contraseña de Administrador ha sido modificada con éxito! Use su nueva clave en el próximo inicio de sesión.",
                            "Configuración Guardada",
                            JOptionPane.INFORMATION_MESSAGE);
                    nuevac.setText("");
                    Confc.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, " Error: No se pudo persistir la nueva contraseña", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        resetearContraseñaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (users.getSelectedItem() == null || users.getSelectedIndex() <= 0) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un usuario de la lista desplegable.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String seleccionado = users.getSelectedItem().toString();
                String dniSeleccionado = seleccionado.split(" - ")[0].trim();
                Instituto institutoReal = (modelo.Sistema.getInstituto() != null) ? modelo.Sistema.getInstituto() : getInstituto();
                if (institutoReal == null) institutoReal = new Instituto();
                boolean exito = institutoReal.actualizarContraseniaEnBaseDeDatos(dniSeleccionado, dniSeleccionado);
                if (exito) {
                    users.setSelectedIndex(0);
                    msjsancion.setText("");
                    textField1.setText("");
                    nom.setText("");
                    aod.setText("Sin seleccionar");
                    JOptionPane.showMessageDialog(null, "La contraseña del usuario con DNI " + dniSeleccionado + " ha sido blanqueada con éxito en la Base de Datos.", "Blanqueo Exitoso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo actualizar la contraseña en MySQL Workbench.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        DefaultTableModel modeloCursosInicial = new DefaultTableModel();
        modeloCursosInicial.addColumn("ID");
        modeloCursosInicial.addColumn("Nombre");
        modeloCursosInicial.addColumn("Cupo Máx.");
        modeloCursosInicial.addColumn("Docente");
        modeloCursosInicial.addColumn("Estado");
        table2.setModel(modeloCursosInicial);
        actualizarTablasAdmin();

        table2.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table2.getSelectedRow() != -1) {
                int filaSeleccionada = table2.getSelectedRow();
                String idCurso = table2.getValueAt(filaSeleccionada, 0).toString().trim();
                modelo.Instituto institutoReal = null;
                if (modelo.Sistema.getInstituto() != null) {
                    institutoReal = modelo.Sistema.getInstituto();
                } else if (this.instituto != null) {
                    institutoReal = this.instituto;
                } else {
                    institutoReal = new modelo.Instituto();
                }

                java.util.ArrayList listaCursosRaw = institutoReal.obtenerTodosLosCursosDeBaseDeDatos();
                if (listaCursosRaw != null) {
                    for (int i = 0; i < listaCursosRaw.size(); i++) {
                        Curso curso = (Curso) listaCursosRaw.get(i);

                        if (curso.getId() != null && curso.getId().trim().equalsIgnoreCase(idCurso)) {

                            nombretext.setText(curso.getNombre());
                            descrp.setText(curso.getDescripcion());
                            estadoacosus.setText(curso.getEstado());
                            cupocant.setText(String.valueOf(curso.getCupoMaximo()));

                            String nombreCompletoDocente = curso.getDocumentoDocente();
                            modelo.Usuario u = institutoReal.buscarUsuarioPorDni(curso.getDocumentoDocente());
                            if (u != null) {
                                nombreCompletoDocente = u.getNombre() + " " + u.getApellido();
                            }
                            doctext.setText(nombreCompletoDocente);

                            int cantInscriptos = curso.getAlumnosInscritos() != null ? curso.getAlumnosInscritos().size() : 0;
                            insccant.setText(String.valueOf(cantInscriptos));

                            if (curso.getIdsCorrelativas() != null && !curso.getIdsCorrelativas().isEmpty()) {
                                java.lang.StringBuilder sb = new java.lang.StringBuilder();

                                for (Object idCorrObj : curso.getIdsCorrelativas()) {
                                    String idCorr = idCorrObj.toString().trim();
                                    String nombreCorr = idCorr;

                                    for (int j = 0; j < listaCursosRaw.size(); j++) {
                                        Curso aux = (Curso) listaCursosRaw.get(j);
                                        if (aux.getId() != null && aux.getId().trim().equalsIgnoreCase(idCorr)) {
                                            nombreCorr = aux.getNombre();
                                            break;
                                        }
                                    }
                                    sb.append("- ").append(nombreCorr).append("\n");
                                }
                                requi.setText(sb.toString().trim());
                            } else {
                                requi.setText("Ninguno");
                            }
                            break;
                        }
                    }
                }
            }
        });
        aprobarCursoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = table2.getSelectedRow();
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un curso de la grilla izquierda para aprobar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String idCurso = table2.getValueAt(filaSeleccionada, 0).toString();
                String estadoActual = table2.getValueAt(filaSeleccionada, 4).toString();
                if (estadoActual.equalsIgnoreCase("Propuesto")) {
                    if (getInstituto() != null) {
                        boolean exito = getInstituto().actualizarEstadoCursoEnBaseDeDatos(idCurso, "Habilitado");
                        if (exito) {
                            JOptionPane.showMessageDialog(null, "El curso ha sido aprobado con éxito y ahora está HABILITADO.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            actualizarTablasAdmin();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Solo se pueden aprobar cursos en estado 'Propuesto'. Estado actual: " + estadoActual, "Acción Denegada", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        tabbedPane1.addChangeListener(e -> {
            int indice = tabbedPane1.getSelectedIndex();
            if (indice == -1) return;
            String nombrePestaña = tabbedPane1.getTitleAt(indice).trim().toLowerCase();
            if (nombrePestaña.contains("curso")) {
                actualizarTablasAdmin();
            } else if (nombrePestaña.contains("configuracion") || nombrePestaña.contains("config")) {
                cargarComboUsuariosConfig();
            }
        });
        cancelarCursoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = table2.getSelectedRow();
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un curso de la grilla izquierda para cancelar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String idCurso = table2.getValueAt(filaSeleccionada, 0).toString();
                if (getInstituto() != null) {
                    boolean exito = getInstituto().actualizarEstadoCursoEnBaseDeDatos(idCurso, "Cancelado");
                    if (exito) {
                        JOptionPane.showMessageDialog(null, "El curso ha sido marcado como CANCELADO.", "Curso Cancelado", JOptionPane.INFORMATION_MESSAGE);
                        actualizarTablasAdmin();
                    }
                }
            }
        });
        verAlumnosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = table2.getSelectedRow();
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un curso de la tabla izquierda para auditar sus estudiantes.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String idCurso = table2.getValueAt(filaSeleccionada, 0).toString();
                String nombreCurso = table2.getValueAt(filaSeleccionada, 1).toString();
                mostrarVentanaAlumnos(idCurso, nombreCurso);
            }
        });
        buscarcurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filtroCurso = buscarcur.getText().toLowerCase().trim();
                DefaultTableModel modeloCursos = (DefaultTableModel) table2.getModel();
                modeloCursos.setRowCount(0);
                Instituto institutoReal = (Sistema.getInstituto() != null) ? Sistema.getInstituto() : getInstituto();
                if (institutoReal == null) institutoReal = new Instituto();
                java.util.ArrayList cursosBD = institutoReal.obtenerTodosLosCursosDeBaseDeDatos();
                if (cursosBD != null) {

                    for (int i = 0; i < cursosBD.size(); i++) {
                        Curso curso = (Curso) cursosBD.get(i);
                        if (filtroCurso.isEmpty() || curso.getNombre().toLowerCase().contains(filtroCurso) || curso.getId().toLowerCase().contains(filtroCurso)) {
                            String nombreCompletoDocente = curso.getDocumentoDocente();
                            Usuario u = institutoReal.buscarUsuarioPorDni(curso.getDocumentoDocente());
                            if (u != null) {
                                nombreCompletoDocente = u.getNombre() + " " + u.getApellido();
                            }
                            modeloCursos.addRow(new Object[]{
                                    curso.getId(),
                                    curso.getNombre(),
                                    curso.getCupoMaximo(),
                                    nombreCompletoDocente,
                                    curso.getEstado()
                            });
                        }
                    }
                }
                totalc.setText(String.valueOf(modeloCursos.getRowCount()));
            }
        });
        actualizarTablasAdmin();
        cargarUsuarios("");
        cargarComboUsuariosConfig();
        users.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (users.getSelectedItem() == null || users.getSelectedIndex() <= 0) {
                    msjsancion.setText("");
                    return;
                }
                String seleccionado = users.getSelectedItem().toString();
                String[] partes = seleccionado.split(" - ");
                String dni = partes[0].trim();
                if (getInstituto() != null && getInstituto().getUsuarios() != null) {
                    java.util.ArrayList listaUsersRaw = getInstituto().getUsuarios();
                    for (int i = 0; i < listaUsersRaw.size(); i++) {
                        Usuario u = (Usuario) listaUsersRaw.get(i);
                        if (u.getDocumento().equals(dni)) {
                            if (!u.isActivo()) {
                                msjsancion.setText(u.getMensajeSancion());
                            } else {
                                msjsancion.setText("La cuenta seleccionada se encuentra activa y no registra mensajes de bloqueo.");
                            }
                            break;
                        }
                    }
                }
            }
        });
    }
    public Instituto getInstituto() {
        return this.instituto;
    }
    public void actualizarTablasAdmin() {
        DefaultTableModel modeloCursos = (DefaultTableModel) table2.getModel();
        modeloCursos.setRowCount(0);
        modelo.Instituto institutoReal = null;
        if (modelo.Sistema.getInstituto() != null) {
            institutoReal = modelo.Sistema.getInstituto();
        } else if (this.instituto != null) {
            institutoReal = this.instituto;
        } else {
            institutoReal = new modelo.Instituto();
        }
        java.util.ArrayList cursosBD = institutoReal.obtenerTodosLosCursosDeBaseDeDatos();
        if (cursosBD != null) {

            for (int i = 0; i < cursosBD.size(); i++) {
                Curso c = (Curso) cursosBD.get(i);
                String nombreCompletoDocente = c.getDocumentoDocente();
                modelo.Usuario u = institutoReal.buscarUsuarioPorDni(c.getDocumentoDocente());
                if (u != null) {
                    nombreCompletoDocente = u.getNombre() + " " + u.getApellido();
                }
                Object[] fila = {
                        c.getId(),
                        c.getNombre(),
                        c.getCupoMaximo(),
                        nombreCompletoDocente,
                        c.getEstado()
                };
                modeloCursos.addRow(fila);
            }
        }
        System.out.println(" Intento de carga completado. Cursos en la tabla: " + modeloCursos.getRowCount());
        if (table2 != null) {
            table2.revalidate();
            table2.repaint();
        }
    }
    public void mostrarVentanaAlumnos(String idCurso, String nombreCurso) {
        Window ventanaPadreActual = SwingUtilities.getWindowAncestor(this);
        JDialog ventanaFlotante;
        if (ventanaPadreActual instanceof Frame) {
            ventanaFlotante = new JDialog((Frame) ventanaPadreActual, "Alumnos Inscritos - " + nombreCurso, true);
        } else {
            ventanaFlotante = new JDialog(this, "Alumnos Inscritos - " + nombreCurso, true);
        }
        ventanaFlotante.setSize(500, 400);
        ventanaFlotante.setLocationRelativeTo(this);
        ventanaFlotante.setLayout(new BorderLayout(10, 10));
        JLabel encabezado = new JLabel(" Listado oficial de alumnos para: " + nombreCurso, JLabel.CENTER);
        encabezado.setFont(new Font("Arial", Font.BOLD, 14));
        ventanaFlotante.add(encabezado, BorderLayout.NORTH);
        DefaultTableModel modeloTablaAlumnos = new DefaultTableModel();
        modeloTablaAlumnos.addColumn("Documento / DNI");
        modeloTablaAlumnos.addColumn("Nombre y Apellido");
        modeloTablaAlumnos.addColumn("Rol");
        JTable tablaAlumnos = new JTable(modeloTablaAlumnos);
        JScrollPane scrollPanel = new JScrollPane(tablaAlumnos);
        ventanaFlotante.add(scrollPanel, BorderLayout.CENTER);
        Instituto institutoReal = (modelo.Sistema.getInstituto() != null) ? modelo.Sistema.getInstituto() : getInstituto();
        if (institutoReal == null) institutoReal = new Instituto();
        java.util.ArrayList cursosBD = institutoReal.obtenerTodosLosCursosDeBaseDeDatos();
        if (cursosBD != null) {

            for (int i = 0; i < cursosBD.size(); i++) {
                Curso c = (Curso) cursosBD.get(i);
                if (c.getId().equals(idCurso)) {
                    if (c.getAlumnosInscritos() != null) {
                        for (Object dniObj : c.getAlumnosInscritos()) {
                            String dniAlumno = (String) dniObj;
                            Usuario alumno = institutoReal.buscarUsuarioPorDni(dniAlumno);
                            if (alumno != null) {
                                modeloTablaAlumnos.addRow(new Object[]{
                                        alumno.getDocumento(),
                                        alumno.getNombre() + " " + alumno.getApellido(),
                                        "Alumno"
                                });
                            } else {
                                modeloTablaAlumnos.addRow(new Object[]{dniAlumno, "Usuario no registrado", "Alumno"});
                            }
                        }
                    }
                    break;
                }
            }
        }
        JButton botonCerrar = new JButton("Cerrar Lista");
        botonCerrar.addActionListener(e -> ventanaFlotante.dispose());
        ventanaFlotante.add(botonCerrar, BorderLayout.SOUTH);
        ventanaFlotante.setVisible(true);
    }
    public void cargarUsuarios(String filtro) {
        DefaultTableModel modeloTabla = new DefaultTableModel(
                new Object[]{"DNI / Documento", "Nombre", "Apellido", "Rol"}, 0
        );
        modeloTabla.setRowCount(0);
        if (this.getInstituto() != null) {
            java.util.ArrayList usuariosBD = this.getInstituto().obtenerTodosLosUsuariosDeBaseDeDatos();
            if (usuariosBD != null) {

                for (int i = 0; i < usuariosBD.size(); i++) {
                    Usuario u = (Usuario) usuariosBD.get(i);
                    if (u.getDocumento().equals("admin")) {
                        continue;
                    }
                    String rolTexto = "Usuario";
                    if (u instanceof Alumno) {
                        rolTexto = "Alumno";
                    } else if (u instanceof Docente) {
                        rolTexto = "Docente";
                    }
                    String textoBuscar = filtro.toLowerCase().trim();
                    if (textoBuscar.isEmpty() || u.getNombre().toLowerCase().contains(textoBuscar) ||
                            u.getApellido().toLowerCase().contains(textoBuscar) || u.getDocumento().contains(textoBuscar)) {
                        Object[] fila = {u.getDocumento(), u.getNombre(), u.getApellido(), rolTexto};
                        modeloTabla.addRow(fila);
                    }
                }
            }
        }
        if (tablauser != null) {
            tablauser.setModel(modeloTabla);
            tablauser.revalidate();
            tablauser.repaint();
        }
        int totalFilas = modeloTabla.getRowCount();
        if (totalU != null) {
            totalU.setText("Total de Usuarios: " + totalFilas);
        }
    }
    public void cargarComboUsuariosConfig() {
        if (users != null) {
            users.removeAllItems();
            users.addItem("Seleccione un usuario...");
            Instituto institutoReal = (modelo.Sistema.getInstituto() != null) ? modelo.Sistema.getInstituto() : getInstituto();
            if (institutoReal == null) institutoReal = new Instituto();
            java.util.ArrayList usuariosBD = institutoReal.obtenerTodosLosUsuariosDeBaseDeDatos();
            if (usuariosBD != null) {

                for (int i = 0; i < usuariosBD.size(); i++) {
                    Usuario u = (Usuario) usuariosBD.get(i);
                    if (u.getDocumento().equalsIgnoreCase("admin")) {
                        continue;
                    }
                    String item = u.getDocumento() + " - " + u.getNombre() + " " + u.getApellido();
                    users.addItem(item);
                }
            }
        }
    }

                    /**
                     * Method generated by IntelliJ IDEA GUI Designer
                     * >>> IMPORTANT!! <<<
                     * DO NOT edit this method OR call it in your code!
                     *
                     * @noinspection ALL
                     */
    private void $$$setupUI$$$() {
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        titulo1 = new JLabel();
        titulo1.setText("Administrador del Instituto \"Caminemos el Trayecto\"");
        panel1.add(titulo1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tabbedPane1 = new JTabbedPane();
        panel1.add(tabbedPane1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        usuarios = new JPanel();
        usuarios.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 4, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Usuarios", usuarios);
        buscador = new JTextField();
        usuarios.add(buscador, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 33), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        usuarios.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tablauser = new JTable();
        scrollPane1.setViewportView(tablauser);
        Titulo7 = new JLabel();
        Titulo7.setText("Listado de Usuario");
        usuarios.add(Titulo7, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titulo8 = new JLabel();
        titulo8.setText("Datos del Usuario");
        usuarios.add(titulo8, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(8, 3, new Insets(0, 0, 0, 0), -1, -1));
        usuarios.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Documento:");
        panel2.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        panel2.add(textField1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nombre = new JLabel();
        nombre.setText("Nombre:");
        panel2.add(nombre, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nom = new JTextField();
        panel2.add(nom, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tipo = new JLabel();
        tipo.setText("Tipo:");
        panel2.add(tipo, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Alumno");
        defaultComboBoxModel1.addElement("Docente");
        comboBox1.setModel(defaultComboBoxModel1);
        panel2.add(comboBox1, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        estado = new JLabel();
        estado.setText("Estado:");
        panel2.add(estado, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        aod = new JLabel();
        aod.setText("Sin seleccionar");
        panel2.add(aod, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        registrar = new JButton();
        registrar.setText("Registrar Usuario");
        panel2.add(registrar, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editar1 = new JButton();
        editar1.setText("Editar Usuario");
        panel2.add(editar1, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Resetear = new JButton();
        Resetear.setText("Resetear Contraseña");
        panel2.add(Resetear, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deshabilitar = new JButton();
        deshabilitar.setText("Deshabilitar Cuenta");
        panel2.add(deshabilitar, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        habilitarCuenta = new JButton();
        habilitarCuenta.setText("Habilitar cuenta");
        panel2.add(habilitarCuenta, new com.intellij.uiDesigner.core.GridConstraints(7, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buscar = new JButton();
        buscar.setText("Buscar");
        usuarios.add(buscar, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(90, 33), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Total de Usuarios:");
        usuarios.add(label2, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        totalU = new JLabel();
        totalU.setText("0");
        usuarios.add(totalU, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(7, 4, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Cursos", panel3);
        lista = new JLabel();
        lista.setText("Listado de Cursos");
        panel3.add(lista, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buscarcur = new JTextField();
        buscarcur.setText("");
        panel3.add(buscarcur, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        buscarcurso = new JButton();
        buscarcurso.setText("Buscar");
        panel3.add(buscarcurso, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel3.add(scrollPane2, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table2 = new JTable();
        scrollPane2.setViewportView(table2);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(8, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 3, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Detalle del Curso");
        panel4.add(label3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nombre1 = new JLabel();
        nombre1.setText("Nombre:");
        panel4.add(nombre1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        descripcion = new JLabel();
        descripcion.setText("Descripcion");
        panel4.add(descripcion, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        docente = new JLabel();
        docente.setText("Docente:");
        panel4.add(docente, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        estado1 = new JLabel();
        estado1.setText("Estado:");
        panel4.add(estado1, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cupo = new JLabel();
        cupo.setText("Cupo Maximo:");
        panel4.add(cupo, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        requisito = new JLabel();
        requisito.setText("Requisitos:");
        panel4.add(requisito, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        inscriptos = new JLabel();
        inscriptos.setText("Inscriptos:");
        panel4.add(inscriptos, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nombretext = new JTextField();
        panel4.add(nombretext, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        doctext = new JTextField();
        panel4.add(doctext, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        estadoacosus = new JLabel();
        estadoacosus.setText("Activo");
        panel4.add(estadoacosus, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cupocant = new JLabel();
        cupocant.setText("0");
        panel4.add(cupocant, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        insccant = new JLabel();
        insccant.setText("0");
        panel4.add(insccant, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        descrp = new JTextArea();
        panel4.add(descrp, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        requi = new JTextArea();
        panel4.add(requi, new com.intellij.uiDesigner.core.GridConstraints(7, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        aprobarCursoButton = new JButton();
        aprobarCursoButton.setText("Aprobar Curso");
        panel3.add(aprobarCursoButton, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelarCursoButton = new JButton();
        cancelarCursoButton.setText("Cancelar Curso");
        panel3.add(cancelarCursoButton, new com.intellij.uiDesigner.core.GridConstraints(4, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        verAlumnosButton = new JButton();
        verAlumnosButton.setText("Ver Alumnos");
        panel3.add(verAlumnosButton, new com.intellij.uiDesigner.core.GridConstraints(5, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        total = new JLabel();
        total.setText("Total de Cursos:");
        panel3.add(total, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        totalc = new JLabel();
        totalc.setText("0");
        panel3.add(totalc, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Configuracion", panel5);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel5.add(panel6, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        titulo4 = new JLabel();
        titulo4.setText("Gestión de Contraseñas");
        panel6.add(titulo4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nueva = new JLabel();
        nueva.setText("Nueva Contraseña:");
        panel6.add(nueva, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        conf = new JLabel();
        conf.setText("Confirmar Contraseña:");
        panel6.add(conf, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nuevac = new JPasswordField();
        panel6.add(nuevac, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        Confc = new JPasswordField();
        panel6.add(Confc, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        guardar = new JButton();
        guardar.setText("Guardar Cambios");
        panel6.add(guardar, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel5.add(panel7, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Información de Sistema");
        panel7.add(label4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Instituto Caminemos el Trayecto");
        panel7.add(label5, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        admiactual = new JLabel();
        admiactual.setText("Administrador Actual:");
        panel7.add(admiactual, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        documento = new JLabel();
        documento.setText("Documento:");
        panel7.add(documento, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        doc = new JLabel();
        doc.setText("0000");
        panel7.add(doc, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        admi = new JLabel();
        admi.setText("Administrador");
        panel7.add(admi, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        roladmi = new JLabel();
        roladmi.setText("Administrador");
        panel7.add(roladmi, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rol = new JLabel();
        rol.setText("Rol:");
        panel7.add(rol, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cerrarsesion = new JButton();
        cerrarsesion.setText("Cerrar Sesion");
        panel7.add(cerrarsesion, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel5.add(panel8, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Gestion de Cuentas");
        panel8.add(label6, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        selec = new JLabel();
        selec.setText("Seleccionar Usuario:");
        panel8.add(selec, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        users = new JComboBox();
        panel8.add(users, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resetearContraseñaButton = new JButton();
        resetearContraseñaButton.setText("Resetear Contraseña");
        panel8.add(resetearContraseñaButton, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deshabilitarCuentaButton = new JButton();
        deshabilitarCuentaButton.setText("Deshabilitar Cuenta");
        panel8.add(deshabilitarCuentaButton, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        msj = new JLabel();
        msj.setText("Mensaje de Sanción");
        panel8.add(msj, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        msjsancion = new JTextArea();
        panel8.add(msjsancion, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        titulo2 = new JLabel();
        titulo2.setText("Bienvenido Administrador");
        panel1.add(titulo2, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }
}
