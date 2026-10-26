package visual;

import modelo.Instituto;
import modelo.Docente;
import modelo.Curso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class cursopropuesto extends JFrame {
    private JTextField textnombre;
    private JTextField textcupo;
    private JTextArea textAreadescrip;
    private JList<Curso> listamaterias;
    private JButton guardarButton;
    private Instituto instituto;
    private Docente docenteLogueado;
    private visual.docente ventanaPadre;

    public cursopropuesto(modelo.Instituto instituto, modelo.Docente docenteLogueado, visual.docente ventanaPadre) {
        this.instituto = instituto;
        this.docenteLogueado = docenteLogueado;
        this.ventanaPadre = ventanaPadre;

        setTitle("Proponer Nuevo Curso");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));


        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panelFormulario.add(new JLabel("Nombre del Curso:"));
        textnombre = new JTextField();
        panelFormulario.add(textnombre);

        panelFormulario.add(new JLabel("Cupo Máximo:"));
        textcupo = new JTextField();
        panelFormulario.add(textcupo);

        panelFormulario.add(new JLabel("Descripción:"));
        textAreadescrip = new JTextArea(3, 20);
        textAreadescrip.setLineWrap(true);
        textAreadescrip.setWrapStyleWord(true);
        panelFormulario.add(new JScrollPane(textAreadescrip));

        panelFormulario.add(new JLabel("Cursos Previos (Máx 2):"));
        listamaterias = new JList<>();
        listamaterias.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        panelFormulario.add(new JScrollPane(listamaterias));

        add(panelFormulario, BorderLayout.CENTER);


        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        guardarButton = new JButton("Guardar Propuesta");
        panelBoton.add(guardarButton);
        add(panelBoton, BorderLayout.SOUTH);
        cargarListaMaterias();


        // ---------------------------------------------------------------------
        // ACCIÓN DEL BOTÓN: GUARDAR PROPUESTA (Corregido y Cerrado)
        // ---------------------------------------------------------------------
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreCurso = textnombre.getText().trim();
                String descCurso = textAreadescrip.getText().trim();
                String cupoTexto = textcupo.getText().trim();

                if (nombreCurso.isEmpty() || descCurso.isEmpty() || cupoTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos obligatorios.");
                    return;
                }

                try {
                    int cupoMax = Integer.parseInt(cupoTexto);

                    List<Curso> correlativasSeleccionadas = listamaterias.getSelectedValuesList();
                    if (correlativasSeleccionadas.size() > 2) {
                        JOptionPane.showMessageDialog(null, "Un curso no puede tener más de 2 cursos previos.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // 🌟 UNIFICACIÓN 1: Calculamos el ID desde el motor central real del Sistema
                    int tamañoListaCentral = 0;
                    if (modelo.Sistema.getInstituto() != null && modelo.Sistema.getInstituto().getCursos() != null) {
                        tamañoListaCentral = modelo.Sistema.getInstituto().getCursos().size();
                    }
                    String idAutomatico = "C" + (tamañoListaCentral + 1);

                    Curso nuevoCurso = new Curso(idAutomatico, nombreCurso, descCurso, cupoMax, docenteLogueado.getDocumento());
                    nuevoCurso.setEstado("Propuesto");

                    for (Curso corr : correlativasSeleccionadas) {
                        nuevoCurso.agregarCorrelativa(corr.getId());
                    }

                    // 🌟 UNIFICACIÓN 2: Guardamos directo en el motor central real para que lo vean Admin y Alumno
                    if (modelo.Sistema.getInstituto() != null && modelo.Sistema.getInstituto().getCursos() != null) {
                        modelo.Sistema.getInstituto().getCursos().add(nuevoCurso);
                    }


                    if (cursopropuesto.this.instituto != null && cursopropuesto.this.instituto.getCursos() != null) {
                        if (!cursopropuesto.this.instituto.getCursos().contains(nuevoCurso)) {
                            cursopropuesto.this.instituto.getCursos().add(nuevoCurso);
                        }
                    }
                    // =====================================================================


                    // Un solo cartel limpio de confirmación
                    JOptionPane.showMessageDialog(null, "Curso propuesto con éxito. Esperando aprobación del Administrador.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    // Refrescamos visualmente la ventana de tu profesor de fondo
                    if (ventanaPadre != null) {
                        ventanaPadre.actualizarTablasCursos();
                    }

                    dispose(); // Cerramos la ventana flotante

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El cupo debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


    }

    // ---------------------------------------------------------------------
    // MÉTODO: CARGAR LISTA MATERIAS
    // ---------------------------------------------------------------------
    private void cargarListaMaterias() {
        DefaultListModel<Curso> modeloLista = new DefaultListModel<>();
        // Apuntamos al Sistema global por consistencia
        if (modelo.Sistema.getInstituto() != null && modelo.Sistema.getInstituto().getCursos() != null) {
            for (Curso c : modelo.Sistema.getInstituto().getCursos()) {
                if (c.getEstado() != null && !c.getEstado().equalsIgnoreCase("Cancelado")) {
                    modeloLista.addElement(c);
                }
            }
        }
        listamaterias.setModel(modeloLista);
    }   }
