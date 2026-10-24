package visual;

import modelo.Sistema;
import modelo.Docente;
import modelo.Curso;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class cursopropuesto extends JFrame {
    private JPanel JPanel;
    private JTextArea textAreadescrip;
    private JLabel titulo;
    private JLabel nombre;
    private JLabel descripcion;
    private JLabel cupo;
    private JLabel cursoprevio;
    private JTextField textnombre;
    private JTextField textcupo;
    private JList<Curso> listamaterias; // Le asignamos el tipo <Curso>
    private JButton guardarButton;

    // Variables de control del modelo
    private Sistema sistema;
    private Docente docenteLogueado;

    // Constructor completo
    public cursopropuesto(Sistema sistema, Docente docenteLogueado) {
        this.sistema = sistema;
        this.docenteLogueado = docenteLogueado;

        // Configuración de la interfaz visual
        setContentPane(JPanel);
        setTitle("Proponer Nuevo Curso");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        // Cargamos los cursos previos existentes en la lista
        cargarListaMaterias();

        // Comportamiento del botón guardar
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

                    // Validamos que el docente no elija más de 2 correlativas en el JList
                    List<Curso> correlativasSeleccionadas = listamaterias.getSelectedValuesList();
                    if (correlativasSeleccionadas.size() > 2) {
                        JOptionPane.showMessageDialog(null, "Un curso no puede tener más de 2 cursos previos.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Generamos un ID automático basado en el tamaño de la lista de cursos
                    String idAutomatico = "C" + (sistema.getCursos().size() + 1);

                    // Instanciamos el curso con los parámetros exactos de tu modelo.Curso
                    Curso nuevoCurso = new Curso(idAutomatico, nombreCurso, descCurso, cupoMax, docenteLogueado.getDni());

                    // Vinculamos los IDs de las materias previas elegidas
                    for (Curso corr : correlativasSeleccionadas) {
                        nuevoCurso.agregarCorrelativa(corr.getId());
                    }

                    // Guardamos el curso propuesto dentro de la lista de tu modelo Sistema
                    sistema.getCursos().add(nuevoCurso);

                    JOptionPane.showMessageDialog(null, "Curso propuesto con éxito. Esperando aprobación del Administrador.");
                    dispose(); // Cierra la ventana flotante

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

        // Recorremos los cursos de tu clase Sistema para llenar el JList
        for (Curso c : sistema.getCursos()) {
            if (c.getEstado() != null && !c.getEstado().equalsIgnoreCase("Cancelado")) {
                modeloLista.addElement(c);
            }
        }

        listamaterias.setModel(modeloLista);
    }
}
