package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Instituto;
import modelo.Curso;
import modelo.Usuario;

public class alumno extends JFrame {

    private JLabel titulo1;
    private JLabel titulo2;
    private JTabbedPane inicio;
    private JPanel configuracion;
    private JTable cursosinscripto;
    private JLabel titulo3;
    private JTextField textbuscar;
    private JButton buscar;
    private JTable tablacursosdisponibles;
    private JButton inscribirse;
    private JLabel titulo5;
    private JTable tablacursosaprobados;
    private JTabbedPane desaprobados;
    private JScrollPane aprobados;
    private JTable tabladesaprobados;
    private JLabel titulo7;
    private JPasswordField contraactual;
    private JPasswordField nuevacontra;
    private JPasswordField confirmarcontra;
    private JButton guardar;
    private JPanel jpanel;
    private JButton cerrar;
    private JTextField textnombre;
    private JTextField textapellido;
    private JTextField textemail;
    private JButton guardarcambios;

    private Instituto instituto;
    private Usuario alumnoLogueado;

    public alumno(Instituto instituto, Usuario alumnoLogueado) {
        this.instituto = instituto;
        this.alumnoLogueado = alumnoLogueado;

        $$$setupUI$$$();

        setTitle("Panel de Control - Alumno");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (inicio != null && inicio.getParent() != null) {
            setContentPane((Container) inicio.getParent());
        }

        setSize(1100, 750);
        setLocationRelativeTo(null);

        if (titulo2 != null) {
            titulo2.setText("Bienvenido/a, " + alumnoLogueado.getNombre() + " " + alumnoLogueado.getApellido());
        }
        textnombre.setText(alumnoLogueado.getNombre());
        textapellido.setText(alumnoLogueado.getApellido());

        actualizarTablasAlumno();

        cerrar.addActionListener(new ActionListener() {
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

        buscar.addActionListener(e -> actualizarTablasAlumno());

        inscribirse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSel = tablacursosdisponibles.getSelectedRow();
                if (filaSel == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un curso de la lista para inscribirse.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String idCurso = tablacursosdisponibles.getValueAt(filaSel, 0).toString();
                String miDni = alumnoLogueado.getDocumento();

                if (instituto != null) {
                    java.util.ArrayList<Curso> cursosBD = instituto.obtenerTodosLosCursosDeBaseDeDatos();
                    for (Curso c : cursosBD) {
                        if (c.getId().equals(idCurso)) {

                            int inscritosAct = (c.getAlumnosInscritos() != null) ? c.getAlumnosInscritos().size() : 0;
                            if (inscritosAct >= c.getCupoMaximo()) {
                                try {
                                    throw new excepciones.CupoMaximoException("Lo sentimos, el cupo para este curso está completo.");
                                } catch (excepciones.CupoMaximoException ex) {
                                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Cupo Lleno", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }

                            if (c.getIdsCorrelativas() != null && !c.getIdsCorrelativas().isEmpty()) {
                                for (String idCorr : c.getIdsCorrelativas()) {
                                    boolean correlativaAprobadaReal = false;
                                    String idCorrLimpio = idCorr.trim();

                                    for (Curso cAux : cursosBD) {
                                        if (cAux.getId().trim().equalsIgnoreCase(idCorrLimpio) && cAux.getAlumnosAprobados() != null) {
                                            for (String reg : cAux.getAlumnosAprobados()) {
                                                if (reg != null && reg.trim().startsWith(miDni)) {
                                                    correlativaAprobadaReal = true;
                                                    break;
                                                }
                                            }
                                        }
                                        if (correlativaAprobadaReal) break;
                                    }

                                    if (!correlativaAprobadaReal) {
                                        try {
                                            throw new excepciones.CorrelativasNoCumplidasException("No cumples con los cursos previos requeridos para anotarte.");
                                        } catch (excepciones.CorrelativasNoCumplidasException ex) {
                                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Requisitos Insuficientes", JOptionPane.WARNING_MESSAGE);
                                            return;
                                        }
                                    }
                                }
                            }

                            if (c.getAlumnosInscritos() == null || !c.getAlumnosInscritos().contains(alumnoLogueado.getDocumento())) {
                                boolean exitoSQL = instituto.inscribirAlumnoEnBaseDeDatos(alumnoLogueado.getDocumento(), idCurso);

                                if (exitoSQL) {
                                    JOptionPane.showMessageDialog(null, "¡Inscripción procesada con éxito !", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                    actualizarTablasAlumno();
                                } else {
                                    JOptionPane.showMessageDialog(null, "⚠️ Error: No se pudo registrar la inscripción .", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Ya te encuentras inscripto en esta materia.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;
                        }
                    }
                }
            }
        });

        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevaContra = new String(nuevacontra.getPassword()).trim();
                String confirmarContra = new String(confirmarcontra.getPassword()).trim();

                if (nuevaContra.isEmpty() || confirmarContra.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete los campos de nueva contraseña.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!nuevaContra.equals(confirmarContra)) {
                    JOptionPane.showMessageDialog(null, "Las contraseñas ingresadas no coinciden. Verifique los datos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String miDni = alumnoLogueado.getDocumento();
                modelo.Instituto institutoReal = (alumno.this.instituto != null) ? alumno.this.instituto : modelo.Sistema.getInstituto();
                if (institutoReal == null) institutoReal = new modelo.Instituto();

                boolean exito = institutoReal.actualizarContraseniaEnBaseDeDatos(miDni, nuevaContra);

                if (exito) {
                    JOptionPane.showMessageDialog(null, "¡Tu contraseña ha sido modificada con éxito en la Base de Datos!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    nuevacontra.setText("");
                    confirmarcontra.setText("");
                    if (contraactual != null) contraactual.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "⚠️ Error: No se pudo actualizar la contraseña en MySQL Workbench.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        if (guardarcambios != null) {
            guardarcambios.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String nuevoNombre = textnombre.getText().trim();
                    String nuevoApellido = textapellido.getText().trim();

                    if (nuevoNombre.isEmpty() || nuevoApellido.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "El Nombre y el Apellido son campos obligatorios.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    String sql = "UPDATE usuarios SET nombre = ?, apellido = ? WHERE TRIM(documento) = ?";
                    try (java.sql.Connection con = basededatos.ConexionDB.getConexion();
                         java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

                        ps.setString(1, nuevoNombre);
                        ps.setString(2, nuevoApellido);
                        ps.setString(3, alumnoLogueado.getDocumento().trim());

                        int filasAfectadas = ps.executeUpdate();
                        if (filasAfectadas > 0) {
                            alumnoLogueado.setNombre(nuevoNombre);
                            alumnoLogueado.setApellido(nuevoApellido);
                            if (titulo2 != null) {
                                titulo2.setText("Bienvenido/a, " + alumnoLogueado.getNombre() + " " + alumnoLogueado.getApellido());
                            }
                            JOptionPane.showMessageDialog(null, "¡Datos personales actualizados correctamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, " No se detectaron cambios o el usuario no existe .", "Aviso", JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (java.sql.SQLException ex) {
                        JOptionPane.showMessageDialog(null, " Error crítico al intentar guardar los cambios.", "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            });
        }

        if (inicio != null) {
            inicio.addChangeListener(e -> actualizarTablasAlumno());
        }
    }



    public void actualizarTablasAlumno() {
        DefaultTableModel modelInscripto = new DefaultTableModel(new Object[]{"ID", "Nombre", "Docente", "Estado"}, 0);
        DefaultTableModel modelDisponible = new DefaultTableModel(new Object[]{"ID", "Nombre", "Cupo Disp.", "Docente"}, 0);
        DefaultTableModel modelAprobado = new DefaultTableModel(new Object[]{"ID", "Nombre", "Resultado"}, 0);
        DefaultTableModel modelDesaprobado = new DefaultTableModel(new Object[]{"ID", "Nombre", "Resultado"}, 0);

        String busqueda = textbuscar.getText().toLowerCase().trim();
        String miDni = alumnoLogueado.getDocumento();

        if (this.instituto != null) {
            java.util.ArrayList<Curso> cursosBD = this.instituto.obtenerTodosLosCursosDeBaseDeDatos();
            if (cursosBD != null) {
                for (Curso c : cursosBD) {
                    String profe = c.getDocumentoDocente();
                    Usuario u = this.instituto.buscarUsuarioPorDni(c.getDocumentoDocente());
                    if (u != null) profe = u.getNombre() + " " + u.getApellido();

                    boolean yaInscripto = false;
                    if (c.getAlumnosInscritos() != null && c.getAlumnosInscritos().contains(miDni)) {
                        modelInscripto.addRow(new Object[]{c.getId(), c.getNombre(), profe, c.getEstado()});
                        yaInscripto = true;
                    }

                    boolean esAprobado = false;
                    if (c.getAlumnosAprobados() != null) {
                        for (String reg : c.getAlumnosAprobados()) {
                            if (reg != null && reg.trim().startsWith(miDni)) {
                                String notaFinal = "Aprobado";
                                String[] splitNota = reg.split(java.util.regex.Pattern.quote(" (Nota: "));
                                if (splitNota.length > 1) {
                                    notaFinal = "Aprobado (Nota: " + splitNota[1].replace(")", "") + ")";
                                }
                                modelAprobado.addRow(new Object[]{c.getId(), c.getNombre(), notaFinal});
                                esAprobado = true;
                                break;
                            }
                        }
                    }

                    boolean esDesaprobado = false;
                    if (!esAprobado && c.getAlumnosDesaprobados() != null) {
                        for (String reg : c.getAlumnosDesaprobados()) {
                            if (reg != null && reg.trim().startsWith(miDni)) {
                                String motivoFinal = "Desaprobado";
                                String[] splitMotivo = reg.split(java.util.regex.Pattern.quote(" (Motivo: "));
                                if (splitMotivo.length > 1) {
                                    motivoFinal = "Desaprobado (" + splitMotivo[1].replace(")", "") + ")";
                                }
                                modelDesaprobado.addRow(new Object[]{c.getId(), c.getNombre(), motivoFinal});
                                esDesaprobado = true;
                                break;
                            }
                        }
                    }

                    if (!yaInscripto && !esAprobado && !esDesaprobado) {
                        String estadoCurso = c.getEstado() != null ? c.getEstado().trim() : "";
                        if (estadoCurso.equalsIgnoreCase("Habilitado")) {
                            if (busqueda.isEmpty() || c.getNombre().toLowerCase().contains(busqueda) || c.getId().toLowerCase().contains(busqueda)) {
                                boolean cumpleCorrelativas = true;
                                if (c.getIdsCorrelativas() != null && !c.getIdsCorrelativas().isEmpty()) {
                                    for (String idCorr : c.getIdsCorrelativas()) {
                                        boolean correlativaPasada = false;
                                        for (Curso cAux : cursosBD) {
                                            if (cAux.getId().equals(idCorr) && cAux.getAlumnosAprobados() != null) {
                                                for (String reg : cAux.getAlumnosAprobados()) {
                                                    if (reg != null && reg.trim().startsWith(miDni)) {
                                                        correlativaPasada = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if (correlativaPasada) break;
                                        }
                                        if (!correlativaPasada) {
                                            cumpleCorrelativas = false;
                                            break;
                                        }
                                    }
                                }
                                if (cumpleCorrelativas) {
                                    int inscritosAct = c.getAlumnosInscritos() != null ? c.getAlumnosInscritos().size() : 0;
                                    int vacantes = c.getCupoMaximo() - inscritosAct;
                                    modelDisponible.addRow(new Object[]{c.getId(), c.getNombre(), vacantes + " / " + c.getCupoMaximo(), profe});
                                }
                            }
                        }
                    }
                }
            }
        }
        if (cursosinscripto != null) cursosinscripto.setModel(modelInscripto);
        if (tablacursosdisponibles != null) tablacursosdisponibles.setModel(modelDisponible);
        if (tablacursosaprobados != null) tablacursosaprobados.setModel(modelAprobado);
        if (tabladesaprobados != null) tabladesaprobados.setModel(modelDesaprobado);
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
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        titulo1 = new JLabel();
        titulo1.setText("Instituto Caminemos el Trayecto - ");
        panel1.add(titulo1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titulo2 = new JLabel();
        titulo2.setText("Bienvenido/a");
        panel1.add(titulo2, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        inicio = new JTabbedPane();
        panel1.add(inicio, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        inicio.addTab("Inicio", panel2);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Cursos en los que estoy Inscripto");
        panel4.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel4.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        cursosinscripto = new JTable();
        scrollPane1.setViewportView(cursosinscripto);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel5, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        titulo3 = new JLabel();
        titulo3.setText("Cursos Disponibles para inscripción");
        panel5.add(titulo3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel5.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        textbuscar = new JTextField();
        panel5.add(textbuscar, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        buscar = new JButton();
        buscar.setText("Buscar");
        panel5.add(buscar, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel5.add(scrollPane2, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tablacursosdisponibles = new JTable();
        scrollPane2.setViewportView(tablacursosdisponibles);
        inscribirse = new JButton();
        inscribirse.setText("Inscribirse");
        panel5.add(inscribirse, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titulo5 = new JLabel();
        titulo5.setText("*Cursos filtrados por correlativas y cupo completo. Si no lo ves no cumples con los requisitos");
        panel5.add(titulo5, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel6, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel7, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        desaprobados = new JTabbedPane();
        panel7.add(desaprobados, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        desaprobados.addTab("Aprobados", panel8);
        aprobados = new JScrollPane();
        panel8.add(aprobados, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tablacursosaprobados = new JTable();
        aprobados.setViewportView(tablacursosaprobados);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        desaprobados.addTab("Desaprobados", panel9);
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel9.add(panel10, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane3 = new JScrollPane();
        panel10.add(scrollPane3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tabladesaprobados = new JTable();
        scrollPane3.setViewportView(tabladesaprobados);
        final JLabel label2 = new JLabel();
        label2.setText("Mi historial de Cursos");
        panel7.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        configuracion = new JPanel();
        configuracion.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        inicio.addTab("Configuración ", configuracion);
        jpanel = new JPanel();
        jpanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        configuracion.add(jpanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(9, 2, new Insets(0, 0, 0, 0), -1, -1));
        jpanel.add(panel11, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        titulo7 = new JLabel();
        titulo7.setText("Gestion de Contraseña");
        panel11.add(titulo7, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panel11.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Modificá tu contraseña de acceso al sistema.");
        panel11.add(label3, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Contraseña actual:");
        panel11.add(label4, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        contraactual = new JPasswordField();
        panel11.add(contraactual, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Nueva contraseña:");
        panel11.add(label5, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nuevacontra = new JPasswordField();
        panel11.add(nuevacontra, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        confirmarcontra = new JPasswordField();
        panel11.add(confirmarcontra, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Confirmar nueva contraseña:");
        panel11.add(label6, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        guardar = new JButton();
        guardar.setText("Guardar ");
        panel11.add(guardar, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        jpanel.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        jpanel.add(panel12, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Informacion");
        panel12.add(label7, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        panel12.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Nombre:");
        panel12.add(label8, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Apellido:");
        panel12.add(label9, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("Email:");
        panel12.add(label10, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textnombre = new JTextField();
        panel12.add(textnombre, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textapellido = new JTextField();
        panel12.add(textapellido, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textemail = new JTextField();
        panel12.add(textemail, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        guardarcambios = new JButton();
        guardarcambios.setText("Guardar Cambios");
        jpanel.add(guardarcambios, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cerrar = new JButton();
        cerrar.setText("Cerrar Sesion");
        panel1.add(cerrar, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }
}


