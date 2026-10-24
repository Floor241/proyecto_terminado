package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Usuario;
import modelo.Alumno;
import modelo.Sistema;
import modelo.Curso;
import modelo.Docente;

public class paneladmin extends JFrame {

    // 1. COMPONENTES SOLAPA USUARIOS
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

    // 2. COMPONENTES SOLAPA CURSOS
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

    // 3. COMPONENTES SOLAPA CONFIGURACIÓN
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

    // Constructor de la ventana
    public paneladmin() {
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

        // ==========================================
        // CONFIGURACIÓN DE USUARIO Y TABLAUSER
        // ==========================================
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
                String tipoTxt = tablauser.getValueAt(filaSeleccionada, 2).toString();
                String estadoTxt = tablauser.getValueAt(filaSeleccionada, 3).toString();

                textField1.setText(dni);
                nom.setText(nombreTxt);
                comboBox1.setSelectedItem(tipoTxt);
                aod.setText(estadoTxt);
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

                for (Usuario u : Sistema.getInstituto().getUsuarios()) {
                    if (u.getDocumento().equals(dni)) {
                        u.setActivo(false);
                        u.setMensajeSancion("Cuenta deshabilitada por la administración del Instituto.");
                        break;
                    }
                }

                cargarUsuarios("");


                textField1.setText("");
                nom.setText("");
                aod.setText("Sin seleccionar");

                JOptionPane.showMessageDialog(null, "La cuenta de " + nombreTxt + " ha sido deshabilitada.");
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

                for (Usuario u : Sistema.getInstituto().getUsuarios()) {
                    if (u.getDocumento().equals(dniSeleccionado)) {
                        u.setActivo(true);
                        u.setMensajeSancion("");
                        break;
                    }
                }
                cargarUsuarios("");

                textField1.setText("");
                nom.setText("");
                aod.setText("Sin seleccionar");

                JOptionPane.showMessageDialog(null, "La cuenta de " + nombreSeleccionado + " ha sido reactivada con éxito.", "Sanción Levantada", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoFiltrado = buscador.getText();

                cargarUsuarios(textoFiltrado);
            }
        });

        // ==========================================
        // BOTÓN CERRAR SESIÓN
        // ==========================================
        cerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginadmin ventanaLogin = new loginadmin();
                ventanaLogin.setVisible(true);
                dispose();
            }
        });


        editar1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dniParaEditar = textField1.getText().trim();

                if (dniParaEditar.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un usuario de la tabla para que se carguen sus datos en el formulario antes de editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String textoNombreCompleto = nom.getText().trim();
                String rolSeleccionado = comboBox1.getSelectedItem().toString();

                if (textoNombreCompleto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El campo de nombre no puede quedar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String nuevoNombre = textoNombreCompleto;
                String nuevoApellido = "";

                if (textoNombreCompleto.contains(" ")) {
                    String[] partes = textoNombreCompleto.split(" ", 2);
                    nuevoNombre = partes[0];
                    nuevoApellido = partes[1];
                }

                boolean encontrado = false;
                Usuario usuarioAEditar = null;

                for (Usuario u : Sistema.getInstituto().getUsuarios()) {
                    if (u.getDocumento().equals(dniParaEditar)) {
                        usuarioAEditar = u;
                        encontrado = true;
                        break;
                    }
                }

                if (encontrado && usuarioAEditar != null) {
                    String rolActual = (usuarioAEditar instanceof Alumno) ? "Alumno" : "Docente";
                    if (!rolActual.equals(rolSeleccionado)) {
                        Sistema.getInstituto().getUsuarios().remove(usuarioAEditar);
                        Usuario usuarioTransformado;
                        if (rolSeleccionado.equals("Alumno")) {
                            usuarioTransformado = new Alumno(nuevoNombre, nuevoApellido, dniParaEditar);
                        } else {
                            usuarioTransformado = new Docente(nuevoNombre, nuevoApellido, dniParaEditar);
                        }

                        Sistema.getInstituto().agregarUsuario(usuarioTransformado);

                    } else {
                        usuarioAEditar.setNombre(nuevoNombre);
                        usuarioAEditar.setApellido(nuevoApellido);
                    }

                    cargarUsuarios("");

                    textField1.setText("");
                    nom.setText("");
                    aod.setText("Sin seleccionar");
                    comboBox1.setSelectedIndex(0);

                    JOptionPane.showMessageDialog(null, "¡Los datos y el rol del usuario han sido modificados con éxito!", "Edición Exitosa", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró ningún usuario con el documento especificado.", "Error", JOptionPane.ERROR_MESSAGE);
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

                for (Usuario u : Sistema.getInstituto().getUsuarios()) {
                    if (u.getDocumento().equals(dniSeleccionado)) {
                        u.setPassword(dniSeleccionado);
                        u.setDebeCambiarPassword(true);


                        break;
                    }
                }

                JOptionPane.showMessageDialog(null, "La contraseña de " + nombreSeleccionado + " ha sido blanqueada.\n" +
                                "Ahora es igual a su DNI (" + dniSeleccionado + ") y se le exigirá cambiarla en su próximo inicio de sesión.",
                        "Blanqueo Exitoso", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        // BOTONES DE CONFIGURACIÓN


        registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registro ventanaRegistro = new Registro(paneladmin.this);
                ventanaRegistro.setVisible(true);
            }
        });


        deshabilitarCuentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Comprobamos que el combo tenga un usuario seleccionado
                if (users.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Seleccione un usuario de la lista desplegable.");
                    return;
                }

                String dniSeleccionado = users.getSelectedItem().toString(); // Recuperamos el DNI del combo
                String motivo = msjsancion.getText();

                if (motivo.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe escribir el motivo de la sanción en el cuadro de texto.");
                    return;
                }

                for (Usuario u : Sistema.getInstituto().getUsuarios()) {
                    if (u.getDocumento().equals(dniSeleccionado)) {
                        u.setActivo(false);
                        u.setMensajeSancion(motivo);
                        break;
                    }
                }

                cargarUsuarios("");

                msjsancion.setText("");

                JOptionPane.showMessageDialog(null, "Sanción aplicada de forma exitosa con su respectivo mensaje de bloqueo.");
            }
        });


        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevaContra = new String(nuevac.getPassword());
                String confirmarContra = new String(Confc.getPassword());

                if (nuevaContra.isEmpty() || confirmarContra.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete ambos campos de contraseña.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!nuevaContra.equals(confirmarContra)) {
                    JOptionPane.showMessageDialog(null, "Las contraseñas ingresadas no coinciden. Verifique los datos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(null, "¡Su contraseña de Administrador ha sido modificada con éxito!", "Configuración Guardada", JOptionPane.INFORMATION_MESSAGE);
                nuevac.setText("");
                Confc.setText("");
            }
        });




        resetearContraseñaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (users.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un usuario de la lista desplegable.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String dniSeleccionado = users.getSelectedItem().toString();

                for (Usuario u : Sistema.getInstituto().getUsuarios()) {
                    if (u.getDocumento().equals(dniSeleccionado)) {
                        u.setPassword(dniSeleccionado);
                        u.setDebeCambiarPassword(true);

                        JOptionPane.showMessageDialog(null, "La contraseña del usuario con DNI " + dniSeleccionado + " ha sido blanqueada de manera correcta.", "Blanqueo Exitoso", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                }
            }
        });
        cerrarsesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginadmin ventanaLogin = new loginadmin();
                ventanaLogin.setVisible(true);
                dispose(); // Cierra el panel administrador de manera limpia
            }
        });


        //BOTONES DE CURSOS

        DefaultTableModel modeloCursosInicial = new DefaultTableModel();
        modeloCursosInicial.addColumn("ID");
        modeloCursosInicial.addColumn("Nombre");
        modeloCursosInicial.addColumn("Estado");
        modeloCursosInicial.addColumn("Cupo Máx.");
        table2.setModel(modeloCursosInicial);

        buscarcurso.doClick();

        table2.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table2.getSelectedRow() != -1) {
                int filaSeleccionada = table2.getSelectedRow();
                String idCurso = table2.getValueAt(filaSeleccionada, 0).toString();


                for (Curso curso : Sistema.getInstituto().getCursos()) {
                    if (curso.getId().equals(idCurso)) {

                        nombretext.setText(curso.getNombre());
                        descrp.setText(curso.getDescripcion());
                        doctext.setText(curso.getDocumentoDocente());
                        estadoacosus.setText(curso.getEstado());
                        cupocant.setText(String.valueOf(curso.getCupoMaximo()));
                        insccant.setText("0");
                        requi.setText("Ninguno");
                        break;
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

                for (Curso curso : Sistema.getInstituto().getCursos()) {
                    if (curso.getId().equals(idCurso)) {
                        if (curso.getEstado().equalsIgnoreCase("Propuesto")) {
                            curso.setEstado("Habilitado");
                            estadoacosus.setText("Habilitado");
                            JOptionPane.showMessageDialog(null, "El curso '" + curso.getNombre() + "' ha sido aprobado y ahora se encuentra HABILITADO.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Solo se pueden aprobar cursos en estado 'Propuesto'. Estado actual: " + curso.getEstado(), "Acción Denegada", JOptionPane.WARNING_MESSAGE);
                        }
                        break;
                    }
                }
                buscarcurso.doClick();
            }
        });


        tabbedPane1.addChangeListener(e -> {
            if (tabbedPane1.getSelectedIndex() == 1) {

                String filtroCurso = buscarcur.getText().toLowerCase().trim();
                DefaultTableModel modeloCursos = (DefaultTableModel) table2.getModel();
                modeloCursos.setRowCount(0);

                for (Curso curso : Sistema.getInstituto().getCursos()) {
                    if (filtroCurso.isEmpty() || curso.getNombre().toLowerCase().contains(filtroCurso) || curso.getId().toLowerCase().contains(filtroCurso)) {
                        modeloCursos.addRow(new Object[]{
                                curso.getId(),
                                curso.getNombre(),
                                curso.getEstado(),
                                curso.getCupoMaximo()
                        });
                    }
                }
                totalc.setText(String.valueOf(modeloCursos.getRowCount()));
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

                for (Curso curso : Sistema.getInstituto().getCursos()) {
                    if (curso.getId().equals(idCurso)) {

                        if (curso.getEstado().equalsIgnoreCase("Propuesto") || curso.getEstado().equalsIgnoreCase("Finalizado")) {
                            curso.setEstado("Cancelado");
                            estadoacosus.setText("Cancelado");
                            JOptionPane.showMessageDialog(null, "El curso '" + curso.getNombre() + "' ha sido marcado como Cancelado.", "Curso Cancelado", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Solo se pueden cancelar cursos en estado 'Propuesto' o 'Finalizado'.", "Restricción de Negocio", JOptionPane.WARNING_MESSAGE);
                        }
                        break;
                    }
                }
                buscarcurso.doClick();
            }
        });

        // BOTÓN: Ver Alumnos (Inscritos en la materia)




        verAlumnosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = table2.getSelectedRow();
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un curso de la lista izquierda para auditar a sus alumnos.", "Aviso", JOptionPane.WARNING_MESSAGE);
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

                for (Curso curso : Sistema.getInstituto().getCursos()) {
                    if (filtroCurso.isEmpty() || curso.getNombre().toLowerCase().contains(filtroCurso) || curso.getId().toLowerCase().contains(filtroCurso)) {

                        modeloCursos.addRow(new Object[]{
                                curso.getId(),
                                curso.getNombre(),
                                curso.getEstado(),
                                curso.getCupoMaximo()
                        });
                    }
                }

                totalc.setText(String.valueOf(modeloCursos.getRowCount()));
            }
        });
    }


    // Método para abrir una ventana emergente y listar los alumnos del curso seleccionado
    public void mostrarVentanaAlumnos(String idCurso, String nombreCurso) {

        JDialog ventanaFlotante = new JDialog(this, "Alumnos Inscritos - " + nombreCurso, true);
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

        for (Curso c : Sistema.getInstituto().getCursos()) {
            if (c.getId().equals(idCurso)) {

                // Si la lista de alumnos del curso está vacía, metemos alumnos de prueba
                if (c.getAlumnosInscritos().isEmpty()) {
                    // Semillas de prueba locales automáticas para que el docente vea filas cargadas
                    modeloTablaAlumnos.addRow(new Object[]{"44556677", "Juan Pérez", "Alumno"});
                    modeloTablaAlumnos.addRow(new Object[]{"11223344", "Lucas Fernández", "Alumno"});
                } else {

                    for (String dniAlumno : c.getAlumnosInscritos()) {
                        Usuario alumno = Sistema.getInstituto().buscarUsuarioPorDni(dniAlumno);
                        if (alumno != null) {
                            modeloTablaAlumnos.addRow(new Object[]{
                                    alumno.getDocumento(),
                                    alumno.getNombre() + " " + alumno.getApellido(),
                                    "Alumno"
                            });
                        }
                    }
                }
                break;
            }
        }
        JButton botonCerrar = new JButton("Cerrar Lista");
        botonCerrar.addActionListener(e -> ventanaFlotante.dispose());
        ventanaFlotante.add(botonCerrar, BorderLayout.SOUTH);

        ventanaFlotante.setVisible(true);
    }


    public void cargarUsuarios(String filtro) {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablauser.getModel();
        modeloTabla.setRowCount(0);

        String filtroMinuscula = filtro.toLowerCase().trim();
        for (Usuario usuario : Sistema.getInstituto().getUsuarios()) {
            String tipoUsuario = (usuario instanceof Alumno) ? "Alumno" : "Docente";
            String nombreCompleto = (usuario.getNombre() + " " + usuario.getApellido()).toLowerCase();
            String dni = usuario.getDocumento();

            if (filtroMinuscula.isEmpty() || dni.contains(filtroMinuscula) || nombreCompleto.contains(filtroMinuscula)) {
                modeloTabla.addRow(new Object[]{
                        usuario.getDocumento(),
                        usuario.getNombre() + " " + usuario.getApellido(),
                        tipoUsuario,
                        usuario.isActivo() ? "Activo" : "Deshabilitado"
                });
            }
        }
        users.removeAllItems();
        for (Usuario u : Sistema.getInstituto().getUsuarios()) {
            users.addItem(u.getDocumento());
        }
        totalU.setText(String.valueOf(modeloTabla.getRowCount()));
        doc.setText("admin");
        roladmi.setText("Administrador General");
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
        cerrar = new JButton();
        cerrar.setText("Cerrar Sesion");
        panel1.add(cerrar, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }
}
