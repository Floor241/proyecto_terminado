package visual;

import modelo.Instituto;
import modelo.Docente;
import modelo.Curso;
import modelo.Sistema;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class docente extends JFrame {
    private Instituto instituto;
    private Docente docenteLogueado;

    private JTabbedPane cursoscance;
    private JPanel panel1;
    private JLabel titulo1;
    private JLabel titulo2;
    private JPanel cursosprop;
    private JPanel conf;
    private JLabel nombre;
    private JLabel bienvenido;
    private JLabel numdoc;
    private JLabel total;
    private JLabel totaldecursos;
    private JLabel titulo3;
    private JLabel habilitado;
    private JLabel canthabilitados;
    private JLabel cantcerrados;
    private JLabel cantfinalizados;
    private JLabel cantpropuestos;
    private JLabel cantcancelados;
    private JLabel titulo4;
    private JTabbedPane finalizados;
    private JPanel habilitados1;
    private JPanel cerraros1;
    private JTable tablahabilitados;
    private JTable tablacerrados;
    private JButton proponerCursoButton;
    private JButton rehabilitarCursoSoloCursosButton;
    private JButton cerrarInscripcionesButton;
    private JTable tablacursospropuestos;
    private JTable tablafinalizados;
    private JLabel titulo5;
    private JLabel cursoseleccionado;
    private JTextField textnombrec;
    private JTextField textdoc;
    private JTextField textcupo;
    private JTextField textinscrip;
    private JLabel nombrecurso;
    private JLabel docente;
    private JTextArea textAreadescrip;
    private JLabel descripcion;
    private JLabel cupomax;
    private JLabel cantinscrip;
    private JLabel estado1;
    private JTabbedPane alumnosinscrip;
    private JPanel aprobados;
    private JPanel desaprobados;
    private JTable tableinscriptos;
    private JTable tableaprobados;
    private JTable tabledesaprobados;
    private JLabel totaldeinscriptos;
    private JButton finalizarcurso;
    private JButton aprobado;
    private JButton desaprobado;
    private JPasswordField passwordactual;
    private JPasswordField passwordnueva;
    private JPasswordField passwordconfirmar;
    private JButton guardarcambios;
    private JLabel titulo6;
    private JLabel titulo7;
    private JLabel titulo8;
    private JLabel titulo9;
    private JLabel titulo10;
    private JLabel titulo11;
    private JLabel titulonombre;
    private JLabel titulodocente;
    private JLabel titulodoc;
    private JLabel estadocuenta;
    private JLabel rolsis;
    private JLabel activaodesac;
    private JButton buttoncerrar;
    private JTabbedPane tabbeestados;
    private JScrollPane espera;
    private JPanel cancelados;
    private JTable tablacancelados;



    public docente(Instituto instituto, Docente docenteLogueado) {

        $$$setupUI$$$();

        this.instituto = instituto;
        this.docenteLogueado = docenteLogueado;



        setContentPane(panel1);
        setTitle("Panel de Control del Docente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        DefaultTableModel vacio = new DefaultTableModel(new Object[]{"ID", "Nombre", "Cupo", "Estado"}, 0);
        if (tablacursospropuestos != null) tablacursospropuestos.setModel(vacio);
        if (tablahabilitados != null) tablahabilitados.setModel(vacio);
        if (tablacerrados != null) tablacerrados.setModel(vacio);
        if (tablafinalizados != null) tablafinalizados.setModel(vacio);
        if (tablacancelados != null) tablacancelados.setModel(vacio);
        if (tablacursospropuestos == null) tablacursospropuestos = new JTable();
        if (tablahabilitados == null) tablahabilitados = new JTable();
        if (tablacerrados == null) tablacerrados = new JTable();
        if (tablafinalizados == null) tablafinalizados = new JTable();
        if (tablacancelados == null) tablacancelados = new JTable();


        numdoc.setText(docenteLogueado.getDocumento());
        nombre.setText(docenteLogueado.getNombre() + " " + docenteLogueado.getApellido());
        titulonombre.setText(docenteLogueado.getNombre() + " " + docenteLogueado.getApellido());
        titulodoc.setText("Documento: " + docenteLogueado.getDocumento());


        actualizarTablasCursos();


        proponerCursoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cursopropuesto ventanaPropuesta = new cursopropuesto(instituto, docenteLogueado, docente.this);
                ventanaPropuesta.setVisible(true);
            }
        });

        if (tabbeestados != null) {
            tabbeestados.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    actualizarTablasCursos();
                }
            });
        }

        if (cursoscance != null) {
            cursoscance.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    actualizarTablasCursos();
                }
            });
        }

        final String[] alumnoSeleccionadoDNI = {""};

        tableinscriptos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int fila = tableinscriptos.getSelectedRow();
                    if (fila != -1) {
                        alumnoSeleccionadoDNI[0] = tableinscriptos.getValueAt(fila, 0).toString();
                    }
                }
            }
        });


        aprobado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int filaCurso = tablahabilitados.getSelectedRow();
                if (filaCurso == -1) {
                    filaCurso = tablacerrados.getSelectedRow();
                }

                if (filaCurso == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un curso en la tabla superior.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String idCurso = (tablahabilitados.getSelectedRow() != -1) ?
                        tablahabilitados.getValueAt(tablahabilitados.getSelectedRow(), 0).toString() :
                        tablacerrados.getValueAt(tablacerrados.getSelectedRow(), 0).toString();

                String dniAlumnoACalificar = "";

                if (tableinscriptos != null && tableinscriptos.getSelectedRow() != -1) {
                    dniAlumnoACalificar = tableinscriptos.getValueAt(tableinscriptos.getSelectedRow(), 0).toString().trim();
                } else if (tabledesaprobados != null && tabledesaprobados.getSelectedRow() != -1) {
                    dniAlumnoACalificar = tabledesaprobados.getValueAt(tabledesaprobados.getSelectedRow(), 0).toString().trim();
                }

                if (dniAlumnoACalificar.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, haga clic sobre un alumno en la lista de inscriptos o desaprobados.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                String notaStr = JOptionPane.showInputDialog(null, "Ingrese la nota final del alumno para aprobar (4 a 10):", "Rectificar a Aprobado", JOptionPane.QUESTION_MESSAGE);
                if (notaStr == null || notaStr.trim().isEmpty()) return;

                try {
                    int nota = Integer.parseInt(notaStr);
                    if (nota < 4 || nota > 10) {
                        JOptionPane.showMessageDialog(null, "La nota para aprobar debe estar entre 4 y 10.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }


                    if (instituto != null) {

                        boolean exitoSQL = instituto.registrarCalificacionAlumno(dniAlumnoACalificar, idCurso, "Aprobado", String.valueOf(nota));

                        if (exitoSQL) {
                            JOptionPane.showMessageDialog(null, "¡Acta rectificada! El alumno " + dniAlumnoACalificar + " ahora está APROBADO con " + nota + ".", "Éxito", JOptionPane.INFORMATION_MESSAGE);


                            if (tableinscriptos != null) tableinscriptos.clearSelection();
                            if (tabledesaprobados != null) tabledesaprobados.clearSelection();


                            java.util.ArrayList<Curso> cursosActualizados = instituto.obtenerTodosLosCursosDeBaseDeDatos();
                            for (Curso c : cursosActualizados) {
                                if (c.getId().equals(idCurso)) {
                                    actualizarTablasAlumnos(c);
                                    textinscrip.setText(String.valueOf(c.getAlumnosInscritos().size()));
                                    totaldeinscriptos.setText(String.valueOf(c.getAlumnosInscritos().size()));
                                    break;
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Error: No se pudo actualizar el estado.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        desaprobado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int filaCurso = tablahabilitados.getSelectedRow();
                if (filaCurso == -1) filaCurso = tablacerrados.getSelectedRow();

                if (filaCurso == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un curso en la tabla superior.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String idCurso = (tablahabilitados.getSelectedRow() != -1) ?
                        tablahabilitados.getValueAt(tablahabilitados.getSelectedRow(), 0).toString() :
                        tablacerrados.getValueAt(tablacerrados.getSelectedRow(), 0).toString();

                String dniAlumno = "";

                if (tableinscriptos.getSelectedRow() != -1) {
                    dniAlumno = tableinscriptos.getValueAt(tableinscriptos.getSelectedRow(), 0).toString().trim();
                } else if (tableaprobados.getSelectedRow() != -1) {
                    dniAlumno = tableaprobados.getValueAt(tableaprobados.getSelectedRow(), 0).toString().trim();
                }

                if (dniAlumno.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un alumno de la lista de inscriptos o aprobados para calificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String motivo = JOptionPane.showInputDialog(null, "Ingrese el motivo del desaprobado o la nota:", "Calificar Desaprobado / Rectificar", JOptionPane.QUESTION_MESSAGE);
                if (motivo == null || motivo.trim().isEmpty()) return;

                if (instituto != null) {
                    boolean exitoSQL = instituto.registrarCalificacionAlumno(dniAlumno, idCurso, "Desaprobado", motivo.trim());

                    if (exitoSQL) {
                        JOptionPane.showMessageDialog(null, "¡Acta rectificada! El alumno figura como DESAPROBADO.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                        tableinscriptos.clearSelection();
                        tableaprobados.clearSelection();

                        java.util.ArrayList<Curso> cursosActualizados = instituto.obtenerTodosLosCursosDeBaseDeDatos();
                        for (Curso c : cursosActualizados) {
                            if (c.getId().equals(idCurso)) {
                                actualizarTablasAlumnos(c);
                                break;
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: No se pudo modificar el registro en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


        tablahabilitados.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int filaSeleccionada = tablahabilitados.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        tablacerrados.clearSelection();
                        if (tablafinalizados != null) tablafinalizados.clearSelection();

                        String idCurso = tablahabilitados.getValueAt(filaSeleccionada, 0).toString();

                        for (Curso c : instituto.obtenerTodosLosCursosDeBaseDeDatos()) {
                            if (c.getId().equals(idCurso)) {

                                cursoseleccionado.setText(c.getNombre());
                                textnombrec.setText(c.getNombre());
                                textdoc.setText(c.getDocumentoDocente());
                                textAreadescrip.setText(c.getDescripcion() != null ? c.getDescripcion() : "");
                                textcupo.setText(String.valueOf(c.getCupoMaximo()));
                                estado1.setText(c.getEstado());


                                int cantInscriptos = (c.getAlumnosInscritos() != null) ? c.getAlumnosInscritos().size() : 0;
                                textinscrip.setText(String.valueOf(cantInscriptos));
                                totaldeinscriptos.setText(String.valueOf(cantInscriptos));

                                actualizarTablasAlumnos(c);
                                break;
                            }
                        }
                    }
                }
            }
        });

        tablacerrados.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int filaSeleccionada = tablacerrados.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        tablahabilitados.clearSelection();
                        if (tablafinalizados != null) tablafinalizados.clearSelection();

                        String idCurso = tablacerrados.getValueAt(filaSeleccionada, 0).toString();

                        for (Curso c : instituto.obtenerTodosLosCursosDeBaseDeDatos()) {
                            if (c.getId().equals(idCurso)) {

                                cursoseleccionado.setText(c.getNombre());
                                textnombrec.setText(c.getNombre());
                                textdoc.setText(c.getDocumentoDocente());
                                textAreadescrip.setText(c.getDescripcion() != null ? c.getDescripcion() : "");
                                textcupo.setText(String.valueOf(c.getCupoMaximo()));
                                estado1.setText(c.getEstado());
                                int cantInscriptos = (c.getAlumnosInscritos() != null) ? c.getAlumnosInscritos().size() : 0;
                                textinscrip.setText(String.valueOf(cantInscriptos));
                                totaldeinscriptos.setText(String.valueOf(cantInscriptos));

                                actualizarTablasAlumnos(c);
                                break;
                            }
                        }
                    }
                }
            }
        });


        cerrarInscripcionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tablahabilitados.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un curso de la tabla de Habilitados para cerrar sus inscripciones.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String idCurso = tablahabilitados.getValueAt(fila, 0).toString();

                boolean actualizado = instituto.actualizarEstadoCursoEnBaseDeDatos(idCurso, "Cerrado");

                if (actualizado) {

                    for (Curso c : instituto.obtenerTodosLosCursosDeBaseDeDatos()) {
                        if (c.getId().equals(idCurso)) {
                            JOptionPane.showMessageDialog(null, "Las inscripciones para el curso '" + c.getNombre() + "' han sido cerradas con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }
                    }

                    actualizarTablasCursos();
                    actualizarContadoresInicio();


                    cursoseleccionado.setText("");
                    textnombrec.setText("");
                    textdoc.setText("");
                    textAreadescrip.setText("");
                    textcupo.setText("");
                    estado1.setText("");
                    textinscrip.setText("0");

                } else {
                    JOptionPane.showMessageDialog(null, " Error: No se pudo actualizar el estado de inscripción .", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        finalizarcurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaHab = tablahabilitados.getSelectedRow();
                int filaCerr = tablacerrados.getSelectedRow();
                String idCurso = "";

                if (filaHab != -1) {
                    idCurso = tablahabilitados.getValueAt(filaHab, 0).toString();
                } else if (filaCerr != -1) {
                    idCurso = tablacerrados.getValueAt(filaCerr, 0).toString();
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un curso de Habilitados o Cerrados para finalizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                boolean actualizado = instituto.actualizarEstadoCursoEnBaseDeDatos(idCurso, "Finalizado");

                if (actualizado) {

                    for (Curso c : instituto.obtenerTodosLosCursosDeBaseDeDatos()) {
                        if (c.getId().equals(idCurso)) {
                            JOptionPane.showMessageDialog(null, "El curso '" + c.getNombre() + "' ha sido finalizado con éxito .");
                            break;
                        }
                    }


                    actualizarTablasCursos();
                    actualizarContadoresInicio();


                    cursoseleccionado.setText("");
                    textnombrec.setText("");
                    textdoc.setText("");
                    textAreadescrip.setText("");
                    textcupo.setText("");
                    estado1.setText("");
                    textinscrip.setText("0");
                } else {
                    JOptionPane.showMessageDialog(null, "Error: No se pudo actualizar el estado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        rehabilitarCursoSoloCursosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int filaHab = tablahabilitados.getSelectedRow();
                int filaCerr = tablacerrados.getSelectedRow();
                int filaFin = (tablafinalizados != null) ? tablafinalizados.getSelectedRow() : -1;

                String idCurso = "";

                if (filaHab != -1) {
                    idCurso = tablahabilitados.getValueAt(filaHab, 0).toString();
                } else if (filaCerr != -1) {
                    idCurso = tablacerrados.getValueAt(filaCerr, 0).toString();
                } else if (filaFin != -1) {
                    idCurso = tablafinalizados.getValueAt(filaFin, 0).toString();
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un curso de la tabla para poder rehabilitarlo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean actualizado = instituto.actualizarEstadoCursoEnBaseDeDatos(idCurso, "Habilitado");

                if (actualizado) {

                    for (Curso c : instituto.obtenerTodosLosCursosDeBaseDeDatos()) {
                        if (c.getId().equals(idCurso)) {
                            JOptionPane.showMessageDialog(null, "El curso '" + c.getNombre() + "' ha sido rehabilitado correctamente y volvió a estar activo.", "Curso Reactivado", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }
                    }

                    actualizarTablasCursos();
                    actualizarContadoresInicio();
                    cursoseleccionado.setText("");
                    textnombrec.setText("");
                    textdoc.setText("");
                    textAreadescrip.setText("");
                    textcupo.setText("");
                    estado1.setText("");
                    textinscrip.setText("0");
                } else {
                    JOptionPane.showMessageDialog(null, " Error: No se pudo actualizar el estado de rehabilitación.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        if (tablafinalizados != null) {
            tablafinalizados.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        int filaSeleccionada = tablafinalizados.getSelectedRow();
                        if (filaSeleccionada != -1) {
                            tablahabilitados.clearSelection();
                            tablacerrados.clearSelection();

                            String idCurso = tablafinalizados.getValueAt(filaSeleccionada, 0).toString();

                            for (Curso c : instituto.obtenerTodosLosCursosDeBaseDeDatos()) {
                                if (c.getId().equals(idCurso)) {

                                    cursoseleccionado.setText(c.getNombre());
                                    textnombrec.setText(c.getNombre());
                                    textdoc.setText(c.getDocumentoDocente());
                                    textAreadescrip.setText(c.getDescripcion() != null ? c.getDescripcion() : "");
                                    textcupo.setText(String.valueOf(c.getCupoMaximo()));
                                    estado1.setText(c.getEstado());


                                    int cantInscriptos = (c.getAlumnosInscritos() != null) ? c.getAlumnosInscritos().size() : 0;
                                    textinscrip.setText(String.valueOf(cantInscriptos));
                                    totaldeinscriptos.setText(String.valueOf(cantInscriptos));
                                    actualizarTablasAlumnos(c);
                                    break;
                                }
                            }
                        }
                    }
                }
            });
        }


        if (tablacancelados != null) {
            tablacancelados.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        int filaSeleccionada = tablacancelados.getSelectedRow();
                        if (filaSeleccionada != -1) {
                            String idCurso = tablacancelados.getValueAt(filaSeleccionada, 0).toString();
                            for (Curso c : instituto.getCursos()) {
                                if (c.getId().equals(idCurso)) {
                                    cursoseleccionado.setText(c.getNombre());
                                    textnombrec.setText(c.getNombre());
                                    textdoc.setText(c.getDocumentoDocente());
                                    textAreadescrip.setText(c.getDescripcion());
                                    textcupo.setText(String.valueOf(c.getCupoMaximo()));
                                    estado1.setText(c.getEstado());
                                    textinscrip.setText("0");
                                    totaldeinscriptos.setText("0");
                                    actualizarTablasAlumnos(c);
                                    break;
                                }
                            }
                        }
                    }
                }
            });
        }


        if (guardarcambios != null) {
            guardarcambios.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String nuevaContra = new String(passwordnueva.getPassword()).trim();
                    String confirmarContra = new String(passwordconfirmar.getPassword()).trim();

                    if (nuevaContra.isEmpty() || confirmarContra.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos de nueva contraseña.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (!nuevaContra.equals(confirmarContra)) {
                        JOptionPane.showMessageDialog(null, "Las contraseñas ingresadas no coinciden. Verifique los datos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String miDni = docenteLogueado.getDocumento();
                    modelo.Instituto institutoReal = (docente.this.instituto != null) ? docente.this.instituto : modelo.Sistema.getInstituto();
                    if (institutoReal == null) institutoReal = new modelo.Instituto();

                    boolean exito = institutoReal.actualizarContraseniaEnBaseDeDatos(miDni, nuevaContra);

                    if (exito) {
                        JOptionPane.showMessageDialog(null, "¡Contraseña de Docente modificada con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        passwordnueva.setText("");
                        passwordconfirmar.setText("");
                        if (passwordactual != null) passwordactual.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, " Error: No se pudo persistir la contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }

        buttoncerrar.addActionListener(new ActionListener() {
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

    }


    public void actualizarTablasCursos() {
        DefaultTableModel modeloPropuestos = new DefaultTableModel(new Object[]{"ID", "Nombre", "Cupo", "Estado"}, 0);
        DefaultTableModel modeloHabilitados = new DefaultTableModel(new Object[]{"ID", "Nombre", "Cupo", "Estado"}, 0);
        DefaultTableModel modeloCerrados = new DefaultTableModel(new Object[]{"ID", "Nombre", "Cupo", "Estado"}, 0);
        DefaultTableModel modeloFinalizados = new DefaultTableModel(new Object[]{"ID", "Nombre", "Cupo", "Estado"}, 0);
        DefaultTableModel modeloCancelados = new DefaultTableModel(new Object[]{"ID", "Nombre", "Cupo", "Estado"}, 0);

        String dniDocente = docenteLogueado.getDocumento().trim();

        if (this.instituto != null) {
            java.util.ArrayList<Curso> cursosBD = this.instituto.obtenerTodosLosCursosDeBaseDeDatos();

            if (cursosBD != null) {
                for (Curso c : cursosBD) {
                    String dniCurso = c.getDocumentoDocente() != null ? c.getDocumentoDocente().trim() : "";

                    if (dniCurso.equalsIgnoreCase(dniDocente)) {
                        Object[] fila = {c.getId(), c.getNombre(), c.getCupoMaximo(), c.getEstado()};
                        String estadoCurso = c.getEstado() != null ? c.getEstado().trim() : "";

                        if (estadoCurso.equalsIgnoreCase("Propuesto")) {
                            modeloPropuestos.addRow(fila);
                        } else if (estadoCurso.equalsIgnoreCase("Habilitado")) {
                            modeloHabilitados.addRow(fila);
                        } else if (estadoCurso.equalsIgnoreCase("Cerrado")) {
                            modeloCerrados.addRow(fila);
                        } else if (estadoCurso.equalsIgnoreCase("Finalizado")) {
                            modeloFinalizados.addRow(fila);
                        } else if (estadoCurso.equalsIgnoreCase("Cancelado")) {
                            modeloCancelados.addRow(fila);
                        }
                    }
                }
            }
        }

        if (tablacursospropuestos != null) { tablacursospropuestos.setModel(modeloPropuestos); tablacursospropuestos.revalidate(); tablacursospropuestos.repaint(); }
        if (tablahabilitados != null) { tablahabilitados.setModel(modeloHabilitados); tablahabilitados.revalidate(); tablahabilitados.repaint(); }
        if (tablacerrados != null) { tablacerrados.setModel(modeloCerrados); tablacerrados.revalidate(); tablacerrados.repaint(); }
        if (tablafinalizados != null) { tablafinalizados.setModel(modeloFinalizados); tablafinalizados.revalidate(); tablafinalizados.repaint(); }
        if (tablacancelados != null) { tablacancelados.setModel(modeloCancelados); tablacancelados.revalidate(); tablacancelados.repaint(); }

        actualizarContadoresInicio();
    }



    private void actualizarTablasAlumnos(Curso cursoSeleccionado) {
        DefaultTableModel modeloInscriptos = new DefaultTableModel(new Object[]{"DNI Alumno", "Nombre y Apellido", "Estado Académico"}, 0);
        DefaultTableModel modeloAprobados = new DefaultTableModel(new Object[]{"DNI Alumno", "Nombre y Apellido", "Nota"}, 0);
        DefaultTableModel modeloDesaprobados = new DefaultTableModel(new Object[]{"DNI Alumno", "Nombre y Apellido", "Nota"}, 0);

        modeloInscriptos.setRowCount(0);
        modeloAprobados.setRowCount(0);
        modeloDesaprobados.setRowCount(0);


        if (cursoSeleccionado.getAlumnosInscritos() != null) {
            for (String dniAlumno : cursoSeleccionado.getAlumnosInscritos()) {
                String nombreCompleto = dniAlumno;
                if (this.instituto != null) {
                    modelo.Usuario u = this.instituto.buscarUsuarioPorDni(dniAlumno);
                    if (u != null && u.getNombre() != null) {
                        nombreCompleto = u.getNombre() + " " + u.getApellido();
                    }
                }
                modeloInscriptos.addRow(new Object[]{dniAlumno, nombreCompleto, "Cursando"});
            }
        }


        if (cursoSeleccionado.getAlumnosAprobados() != null) {
            for (String registro : cursoSeleccionado.getAlumnosAprobados()) {
                String[] datos = registro.split(" \\(Nota: ");
                if (datos.length > 0) {
                    String dni = datos[0];
                    String nota = datos.length > 1 ? datos[1].replace(")", "") : "-";

                    String nombreCompleto = dni;
                    if (this.instituto != null) {
                        modelo.Usuario u = this.instituto.buscarUsuarioPorDni(dni);
                        if (u != null && u.getNombre() != null) nombreCompleto = u.getNombre() + " " + u.getApellido();
                    }
                    modeloAprobados.addRow(new Object[]{dni, nombreCompleto, nota});
                }
            }
        }


        if (cursoSeleccionado.getAlumnosDesaprobados() != null) {
            for (String registro : cursoSeleccionado.getAlumnosDesaprobados()) {
                String[] datos = registro.split(" \\(Motivo: ");
                if (datos.length > 0) {
                    String dni = datos[0];
                    String motivo = datos.length > 1 ? datos[1].replace(")", "") : "-";

                    String nombreCompleto = dni;
                    if (this.instituto != null) {
                        modelo.Usuario u = this.instituto.buscarUsuarioPorDni(dni);
                        if (u != null && u.getNombre() != null) nombreCompleto = u.getNombre() + " " + u.getApellido();
                    }
                    modeloDesaprobados.addRow(new Object[]{dni, nombreCompleto, motivo});
                }
            }
        }

        tableinscriptos.setModel(modeloInscriptos);
        tableaprobados.setModel(modeloAprobados);
        tabledesaprobados.setModel(modeloDesaprobados);
    }


    public void actualizarContadoresInicio() {
        int habilitados = 0;
        int cerrados = 0;
        int finalizados = 0;
        int propuestos = 0;
        int cancelados = 0;
        int totalCursosDocente = 0;

        String dniDocente = docenteLogueado.getDocumento().trim();


        if (this.instituto != null) {
            java.util.ArrayList<Curso> cursosBD = this.instituto.obtenerTodosLosCursosDeBaseDeDatos();

            if (cursosBD != null) {
                for (Curso c : cursosBD) {
                    String dniCurso = c.getDocumentoDocente() != null ? c.getDocumentoDocente().trim() : "";

                    if (dniCurso.equalsIgnoreCase(dniDocente)) {
                        totalCursosDocente++;

                        String estado = c.getEstado() != null ? c.getEstado().trim() : "";

                        if (estado.equalsIgnoreCase("Habilitado")) {
                            habilitados++;
                        } else if (estado.equalsIgnoreCase("Cerrado")) {
                            cerrados++;
                        } else if (estado.equalsIgnoreCase("Finalizado")) {
                            finalizados++;
                        } else if (estado.equalsIgnoreCase("Propuesto")) {
                            propuestos++;
                        } else if (estado.equalsIgnoreCase("Cancelado")) {
                            cancelados++;
                        }
                    }
                }
            }
        }

        canthabilitados.setText(String.valueOf(habilitados));
        cantcerrados.setText(String.valueOf(cerrados));
        cantfinalizados.setText(String.valueOf(finalizados));
        cantpropuestos.setText(String.valueOf(propuestos));
        cantcancelados.setText(String.valueOf(cancelados));
        totaldecursos.setText(String.valueOf(totalCursosDocente));

        panel1.revalidate();
        panel1.repaint();
    }



    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        titulo1 = new JLabel();
        titulo1.setText("Instituto Caminemos el Trayecto - ");
        panel1.add(titulo1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titulo2 = new JLabel();
        titulo2.setText("Bienvenido Docente");
        panel1.add(titulo2, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        total = new JLabel();
        total.setText("Total de cursos:");
        panel1.add(total, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        totaldecursos = new JLabel();
        totaldecursos.setText("0");
        panel1.add(totaldecursos, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cursoscance = new JTabbedPane();
        panel1.add(cursoscance, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        cursoscance.addTab("Inicio", panel2);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        bienvenido = new JLabel();
        bienvenido.setText("Bienvenido/a");
        panel3.add(bienvenido, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nombre = new JLabel();
        nombre.setText("Nombredoc");
        panel3.add(nombre, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Docente");
        panel3.add(label1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Tu número de documento:");
        panel3.add(label2, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        numdoc = new JLabel();
        numdoc.setText("000");
        panel3.add(numdoc, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        titulo3 = new JLabel();
        titulo3.setText("Resumen de tus cursos");
        panel4.add(titulo3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.add(panel5, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel5.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label3 = new JLabel();
        label3.setText("Cerrados");
        panel5.add(label3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cantcerrados = new JLabel();
        cantcerrados.setText("1");
        panel5.add(cantcerrados, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.add(panel6, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel6.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label4 = new JLabel();
        label4.setText("Finalizados");
        panel6.add(label4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cantfinalizados = new JLabel();
        cantfinalizados.setText("1");
        panel6.add(cantfinalizados, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.add(panel7, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel7.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label5 = new JLabel();
        label5.setText("Propuestos");
        panel7.add(label5, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cantpropuestos = new JLabel();
        cantpropuestos.setText("1");
        panel7.add(cantpropuestos, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.add(panel8, new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel8.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label6 = new JLabel();
        label6.setText("Cancelados");
        panel8.add(label6, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cantcancelados = new JLabel();
        cantcancelados.setText("1");
        panel8.add(cantcancelados, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titulo4 = new JLabel();
        titulo4.setText("En esta sección podés ver el estado de tus cursos, gestionar las inscripciones de tus alumnos y realizar las acciones correspondientes en cada etapa");
        panel4.add(titulo4, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.add(panel9, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel9.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-3025959)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        habilitado = new JLabel();
        habilitado.setText("Habilitado");
        panel9.add(habilitado, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        canthabilitados = new JLabel();
        canthabilitados.setText("1");
        panel9.add(canthabilitados, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        cursoscance.addTab("Mis Cursos", panel10);
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel10.add(panel11, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        finalizados = new JTabbedPane();
        finalizados.setTabLayoutPolicy(0);
        panel11.add(finalizados, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        habilitados1 = new JPanel();
        habilitados1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        finalizados.addTab("Habilitados", habilitados1);
        final JScrollPane scrollPane1 = new JScrollPane();
        habilitados1.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tablahabilitados = new JTable();
        scrollPane1.setViewportView(tablahabilitados);
        cerraros1 = new JPanel();
        cerraros1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        finalizados.addTab("Cerrados", cerraros1);
        final JScrollPane scrollPane2 = new JScrollPane();
        cerraros1.add(scrollPane2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tablacerrados = new JTable();
        scrollPane2.setViewportView(tablacerrados);
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        finalizados.addTab("Finalizados", panel12);
        final JScrollPane scrollPane3 = new JScrollPane();
        panel12.add(scrollPane3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tablafinalizados = new JTable();
        scrollPane3.setViewportView(tablafinalizados);
        final JPanel panel13 = new JPanel();
        panel13.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel10.add(panel13, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel13.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label7 = new JLabel();
        label7.setText("Acciones");
        panel13.add(label7, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        proponerCursoButton = new JButton();
        proponerCursoButton.setText("Proponer Curso");
        panel13.add(proponerCursoButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rehabilitarCursoSoloCursosButton = new JButton();
        rehabilitarCursoSoloCursosButton.setText("Rehabilitar curso (solo cursos finalizados)");
        panel13.add(rehabilitarCursoSoloCursosButton, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cerrarInscripcionesButton = new JButton();
        cerrarInscripcionesButton.setText("Cerrar Inscripciones");
        panel13.add(cerrarInscripcionesButton, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        finalizarcurso = new JButton();
        finalizarcurso.setText("Finalizar Curso");
        panel13.add(finalizarcurso, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel14 = new JPanel();
        panel14.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel10.add(panel14, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        titulo5 = new JLabel();
        titulo5.setText("Informacion del curso");
        panel14.add(titulo5, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel15 = new JPanel();
        panel15.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel14.add(panel15, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        nombrecurso = new JLabel();
        nombrecurso.setText("Nombre del curso:");
        panel15.add(nombrecurso, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        docente = new JLabel();
        docente.setText("Docente:");
        panel15.add(docente, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        descripcion = new JLabel();
        descripcion.setText("Descripcion:");
        panel15.add(descripcion, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cupomax = new JLabel();
        cupomax.setText("Cupo máximo:");
        panel15.add(cupomax, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cantinscrip = new JLabel();
        cantinscrip.setText("Inscriptos:");
        panel15.add(cantinscrip, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Estado:");
        panel15.add(label8, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textnombrec = new JTextField();
        panel15.add(textnombrec, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textdoc = new JTextField();
        panel15.add(textdoc, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textcupo = new JTextField();
        panel15.add(textcupo, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textinscrip = new JTextField();
        textinscrip.setText("");
        panel15.add(textinscrip, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        estado1 = new JLabel();
        estado1.setText("Habilitado");
        panel15.add(estado1, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textAreadescrip = new JTextArea();
        panel15.add(textAreadescrip, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JPanel panel16 = new JPanel();
        panel16.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel14.add(panel16, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        alumnosinscrip = new JTabbedPane();
        panel16.add(alumnosinscrip, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel17 = new JPanel();
        panel17.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        alumnosinscrip.addTab("Alumnos Inscriptos", panel17);
        final JScrollPane scrollPane4 = new JScrollPane();
        panel17.add(scrollPane4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableinscriptos = new JTable();
        scrollPane4.setViewportView(tableinscriptos);
        aprobados = new JPanel();
        aprobados.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        alumnosinscrip.addTab("Historial de Aprobados", aprobados);
        final JScrollPane scrollPane5 = new JScrollPane();
        aprobados.add(scrollPane5, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableaprobados = new JTable();
        scrollPane5.setViewportView(tableaprobados);
        desaprobados = new JPanel();
        desaprobados.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        alumnosinscrip.addTab("Historial de desaprobados", desaprobados);
        final JScrollPane scrollPane6 = new JScrollPane();
        desaprobados.add(scrollPane6, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tabledesaprobados = new JTable();
        scrollPane6.setViewportView(tabledesaprobados);
        final JLabel label9 = new JLabel();
        label9.setText("Total de inscriptos:");
        panel16.add(label9, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        totaldeinscriptos = new JLabel();
        totaldeinscriptos.setText("0");
        panel16.add(totaldeinscriptos, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel18 = new JPanel();
        panel18.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel14.add(panel18, new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("Acciones");
        panel18.add(label10, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        aprobado = new JButton();
        aprobado.setText("Aprobado");
        panel18.add(aprobado, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        desaprobado = new JButton();
        desaprobado.setText("Desaprobado");
        panel18.add(desaprobado, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cursoseleccionado = new JLabel();
        cursoseleccionado.setText("java");
        panel14.add(cursoseleccionado, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cursosprop = new JPanel();
        cursosprop.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        cursoscance.addTab("Cursos Propuestos", cursosprop);
        final JPanel panel19 = new JPanel();
        panel19.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        cursosprop.add(panel19, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tabbeestados = new JTabbedPane();
        panel19.add(tabbeestados, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        espera = new JScrollPane();
        tabbeestados.addTab("En espera", espera);
        tablacursospropuestos = new JTable();
        espera.setViewportView(tablacursospropuestos);
        cancelados = new JPanel();
        cancelados.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbeestados.addTab("Cancelados", cancelados);
        final JScrollPane scrollPane7 = new JScrollPane();
        cancelados.add(scrollPane7, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tablacancelados = new JTable();
        scrollPane7.setViewportView(tablacancelados);
        final JLabel label11 = new JLabel();
        label11.setText("Cursos Propuestos");
        panel19.add(label11, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        conf = new JPanel();
        conf.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        cursoscance.addTab("Configuración ", conf);
        final JPanel panel20 = new JPanel();
        panel20.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        conf.add(panel20, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel20.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel21 = new JPanel();
        panel21.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(9, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel20.add(panel21, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel21.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        titulo6 = new JLabel();
        titulo6.setText("Gestion de Contraseña");
        panel21.add(titulo6, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titulo7 = new JLabel();
        titulo7.setText("Modificá tu contraseña de acceso al sistema.");
        panel21.add(titulo7, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titulo8 = new JLabel();
        titulo8.setText("Contraseña actual");
        panel21.add(titulo8, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passwordactual = new JPasswordField();
        panel21.add(passwordactual, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        titulo9 = new JLabel();
        titulo9.setText("Nueva contraseña");
        panel21.add(titulo9, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passwordnueva = new JPasswordField();
        panel21.add(passwordnueva, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        titulo10 = new JLabel();
        titulo10.setText("Confirmar nueva contraseña");
        panel21.add(titulo10, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passwordconfirmar = new JPasswordField();
        panel21.add(passwordconfirmar, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        guardarcambios = new JButton();
        guardarcambios.setText("Guardar cambios");
        panel21.add(guardarcambios, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel22 = new JPanel();
        panel22.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel20.add(panel22, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        titulo11 = new JLabel();
        titulo11.setText("Información de la cuenta");
        panel22.add(titulo11, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titulonombre = new JLabel();
        titulonombre.setText("nombre doc");
        panel22.add(titulonombre, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titulodocente = new JLabel();
        titulodocente.setText("Docente");
        panel22.add(titulodocente, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titulodoc = new JLabel();
        titulodoc.setText("Documento: 77889900");
        panel22.add(titulodoc, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        estadocuenta = new JLabel();
        estadocuenta.setText("Estado de la cuenta");
        panel22.add(estadocuenta, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rolsis = new JLabel();
        rolsis.setText("Rol del sistema");
        panel22.add(rolsis, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        activaodesac = new JLabel();
        activaodesac.setText("Activa");
        panel22.add(activaodesac, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("Docente");
        panel22.add(label12, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttoncerrar = new JButton();
        buttoncerrar.setText("Cerrar Sesion");
        panel1.add(buttoncerrar, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
