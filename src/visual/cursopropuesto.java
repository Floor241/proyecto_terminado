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

                    modelo.Instituto institutoReal = (cursopropuesto.this.instituto != null) ?
                            cursopropuesto.this.instituto : modelo.Sistema.getInstituto();
                    if (institutoReal == null) institutoReal = new modelo.Instituto();

                    int tamañoListaCentral = institutoReal.obtenerTodosLosCursosDeBaseDeDatos().size();
                    String idAutomatico = "C" + (tamañoListaCentral + 1);

                    Curso nuevoCurso = new Curso(idAutomatico, nombreCurso, descCurso, cupoMax, docenteLogueado.getDocumento());
                    nuevoCurso.setEstado("Propuesto");

                    institutoReal.agregarCurso(nuevoCurso);


                    for (Curso corr : correlativasSeleccionadas) {
                        nuevoCurso.agregarCorrelativa(corr.getId());

                        institutoReal.agregarCorrelativaEnBaseDeDatos(idAutomatico, corr.getId());
                    }

                    JOptionPane.showMessageDialog(null, "Curso propuesto con éxito. Esperando aprobación del Administrador.", "Éxito", JOptionPane.INFORMATION_MESSAGE);


                    if (ventanaPadre != null) {
                        ventanaPadre.actualizarTablasCursos();
                    }


                    for (Frame frame : Frame.getFrames()) {
                        if (frame instanceof visual.paneladmin) {
                            visual.paneladmin adminVentana = (visual.paneladmin) frame;
                            adminVentana.actualizarTablasAdmin();
                            System.out.println(" ¡Grilla del Administrador forzada a actualizar con éxito!");
                        }
                    }

                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El cupo debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



    }


    private void cargarListaMaterias() {
        DefaultListModel<Curso> modeloLista = new DefaultListModel<>();

        modelo.Instituto institutoReal = null;
        if (cursopropuesto.this.instituto != null) {
            institutoReal = cursopropuesto.this.instituto;
        } else if (modelo.Sistema.getInstituto() != null) {
            institutoReal = modelo.Sistema.getInstituto();
        } else {
            institutoReal = new modelo.Instituto();
        }

        java.util.ArrayList<Curso> cursosBD = institutoReal.obtenerTodosLosCursosDeBaseDeDatos();

        if (cursosBD != null) {
            for (Curso c : cursosBD) {

                if (c.getEstado() != null && !c.getEstado().equalsIgnoreCase("Cancelado")) {
                    modeloLista.addElement(c);
                }
            }
        }

        listamaterias.setModel(modeloLista);
    } }

