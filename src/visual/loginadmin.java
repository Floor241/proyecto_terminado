package visual;
import javax.swing.*; //
import java.awt.*; //
import java.awt.event.ActionEvent; //
import java.awt.event.ActionListener; //
import modelo.Instituto; //
import modelo.Docente; //
import modelo.Alumno; //

public class loginadmin extends JFrame {
    private JLabel inst;
    private JLabel login;
    private JButton ingresar;
    private JPasswordField contracompl;
    private JTextField usuariocompl;
    private JLabel contraseña;
    private JLabel usuario;
    private JButton cambiarcont;
    private JPanel panelPrincipal;
    private static modelo.Instituto instituto = new modelo.Instituto();

    public static loginadmin instanciaLogin;

    public loginadmin() {
        instanciaLogin = this;

        $$$setupUI$$$();


        setTitle("Acceso al Sistema");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);


        ingresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuarioTexto = usuariocompl.getText().trim();
                String password = new String(contracompl.getPassword());


                if (usuarioTexto.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debes completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                modelo.Usuario usuarioEncontrado = instituto.buscarUsuarioPorDni(usuarioTexto);

                if (usuarioEncontrado != null) {

                    if (usuarioEncontrado.getPassword().equals(password)) {

                        if (!usuarioEncontrado.isActivo()) {
                            String motivoSancion = usuarioEncontrado.getMensajeSancion();
                            if (motivoSancion == null || motivoSancion.trim().isEmpty()) {
                                motivoSancion = "Cuenta deshabilitada por la administración del Instituto.";
                            }
                            JOptionPane.showMessageDialog(null,
                                    " ACCESO DENEGADO \n\n" +
                                            "Tu cuenta se encuentra temporalmente deshabilitada.\n" +
                                            "Motivo de la sanción:\n\"" + motivoSancion + "\"\n\n" +
                                            "Por favor, comunícate con el Administrador para regularizar tu situación.",
                                    "Cuenta Bloqueada",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (usuarioEncontrado.getPassword().equals(usuarioEncontrado.getDocumento())) {
                            JOptionPane.showMessageDialog(null, "Primer acceso detectado. Por seguridad, debe cambiar su contraseña.", "Cambio Obligatorio", JOptionPane.WARNING_MESSAGE);
                            loginadmin.this.setVisible(false);
                            forzado ventanaForzado = new forzado(usuarioEncontrado);
                            ventanaForzado.setVisible(true);
                            return;
                        }

                        if (usuarioTexto.equals("admin") || usuarioEncontrado.getNombre().equalsIgnoreCase("Administrador")) {
                            JOptionPane.showMessageDialog(null, "¡Bienvenido Administrador!", "Acceso Concedido", JOptionPane.INFORMATION_MESSAGE);
                            paneladmin ventanaAdmin = new paneladmin(instituto);
                            ventanaAdmin.setVisible(true);
                            ventanaAdmin.cargarUsuarios("");
                        }
                        else if (usuarioEncontrado instanceof modelo.Docente) {
                            JOptionPane.showMessageDialog(null, "¡Bienvenido Docente: " + usuarioEncontrado.getNombre() + "!", "Acceso Concedido", JOptionPane.INFORMATION_MESSAGE);
                            docente ventanaDocente = new docente(instituto, (modelo.Docente) usuarioEncontrado);
                            ventanaDocente.setVisible(true);
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "¡Bienvenido Alumno: " + usuarioEncontrado.getNombre() + "!", "Acceso Concedido", JOptionPane.INFORMATION_MESSAGE);
                            alumno ventanaAlumno = new alumno(instituto, (modelo.Alumno) usuarioEncontrado);
                            ventanaAlumno.setVisible(true);
                        }

                        loginadmin.this.setVisible(false);
                        return;

                    } else {
                        JOptionPane.showMessageDialog(null, "Contraseña incorrecta.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                JOptionPane.showMessageDialog(null, "El usuario con DNI/Código '" + usuarioTexto + "' no existe en el sistema.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
            }
        });






        cambiarcont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                visual.cambioContrasenia ventanaCambio = new visual.cambioContrasenia();
                ventanaCambio.setVisible(true);
            }
        });

    }

/**
 * Method generated by IntelliJ IDEA GUI Designer
 * >>> IMPORTANT!! <<<
 * DO NOT edit this method OR call it in your code!
 *
 * @noinspection ALL
 */
private void $$$setupUI$$$() {
    panelPrincipal = new JPanel();
    panelPrincipal.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 3, new Insets(0, 0, 0, 0), -1, -1));
    inst = new JLabel();
    inst.setText("instituto \"Caminemos el Trayecto\"");
    panelPrincipal.add(inst, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    login = new JLabel();
    login.setHorizontalAlignment(11);
    login.setHorizontalTextPosition(10);
    login.setText("Login");
    panelPrincipal.add(login, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    usuario = new JLabel();
    usuario.setText("Usuario:");
    panelPrincipal.add(usuario, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(59, 95), null, 0, false));
    ingresar = new JButton();
    ingresar.setText("Ingresar");
    panelPrincipal.add(ingresar, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    contracompl = new JPasswordField();
    panelPrincipal.add(contracompl, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    usuariocompl = new JTextField();
    panelPrincipal.add(usuariocompl, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    contraseña = new JLabel();
    contraseña.setText("Contraseña:");
    panelPrincipal.add(contraseña, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    cambiarcont = new JButton();
    cambiarcont.setText("Cambiar Contraseña");
    panelPrincipal.add(cambiarcont, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
}

/**
 * @noinspection ALL
 */
public JComponent $$$getRootComponent$$$() {
    return panelPrincipal;
}

}