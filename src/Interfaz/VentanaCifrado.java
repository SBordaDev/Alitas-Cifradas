package Interfaz;

import Cifrado.Cesar;
import Cifrado.Vigenere;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class VentanaCifrado extends JFrame {
    // Componentes
    private JTextArea txtEntrada, txtSalida;
    private JTextField txtLlave;
    private JComboBox<String> comboAlgoritmo;
    private JButton btnCifrar, btnDescifrar, btnFuerzaBruta;
    private JMenuBar menuBar;

    // Lógica
    private Cesar cesar = new Cesar();
    private Vigenere vigenere = new Vigenere();

    public VentanaCifrado() {
        setTitle("Cifrador Pro v2.0 - Suite de Seguridad");
        setSize(600, 600); // Aumenté un poco el tamaño para ver mejor los resultados largos
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        inicializarMenu();
        inicializarComponentes();
        configurarEventos();
    }

    private void inicializarMenu() {
        menuBar = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(itemSalir);

        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcerca = new JMenuItem("Acerca de");
        itemAcerca.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Cifrador Pro\nSoporta César y Vigenère (Normal y Fuerza Bruta)"));
        menuAyuda.add(itemAcerca);

        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);

        setJMenuBar(menuBar);
    }

    private void inicializarComponentes() {
        // Panel Superior: Selección y Llave
        JPanel pnlSuperior = new JPanel(new GridLayout(2, 2, 5, 5));
        pnlSuperior.setBorder(BorderFactory.createTitledBorder("Configuración"));

        comboAlgoritmo = new JComboBox<>(new String[]{"César", "Vigenère"});
        txtLlave = new JTextField();
        // Tooltip para ayudar al usuario
        txtLlave.setToolTipText("Ingrese un número para César o una palabra para Vigenère (Ignorado en Fuerza Bruta)");

        pnlSuperior.add(new JLabel(" Algoritmo:"));
        pnlSuperior.add(comboAlgoritmo);
        pnlSuperior.add(new JLabel(" Llave/Clave:"));
        pnlSuperior.add(txtLlave);

        // Panel Central: Áreas de texto
        JPanel pnlCentral = new JPanel(new GridLayout(2, 1, 5, 5));
        txtEntrada = new JTextArea();
        txtEntrada.setBorder(BorderFactory.createTitledBorder("Mensaje de Entrada"));

        txtSalida = new JTextArea();
        txtSalida.setEditable(false);
        txtSalida.setBackground(new Color(240, 240, 240));
        txtSalida.setBorder(BorderFactory.createTitledBorder("Resultado / Salida de Consola"));
        txtSalida.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Fuente monoespaciada para listas

        pnlCentral.add(new JScrollPane(txtEntrada));
        pnlCentral.add(new JScrollPane(txtSalida));

        // Panel Inferior: Botones
        JPanel pnlInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnCifrar = new JButton("Cifrar");
        btnDescifrar = new JButton("Descifrar");

        // Botón nuevo destacado
        btnFuerzaBruta = new JButton("Fuerza Bruta 🔓");
        btnFuerzaBruta.setBackground(new Color(255, 230, 230));
        btnFuerzaBruta.setToolTipText("Intenta descifrar sin la clave");

        pnlInferior.add(btnCifrar);
        pnlInferior.add(btnDescifrar);
        pnlInferior.add(btnFuerzaBruta);

        add(pnlSuperior, BorderLayout.NORTH);
        add(pnlCentral, BorderLayout.CENTER);
        add(pnlInferior, BorderLayout.SOUTH);
    }

    private void configurarEventos() {
        btnCifrar.addActionListener(e -> procesarNormal(true));
        btnDescifrar.addActionListener(e -> procesarNormal(false));
        btnFuerzaBruta.addActionListener(e -> procesarFuerzaBruta());
    }

    private void procesarNormal(boolean esCifrado) {
        String msj = txtEntrada.getText();
        String llave = txtLlave.getText();
        String algoritmo = (String) comboAlgoritmo.getSelectedItem();

        if (msj.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un mensaje.");
            return;
        }

        String tipoOperacion = esCifrado ? "CIFRADO" : "DESCIFRADO";

        String encabezado = String.format("=== Ejecutando %s (%s) ===\n", tipoOperacion, algoritmo);

        try {
            String resultado = "";

            if (algoritmo.equals("César")) {
                int n = Integer.parseInt(llave);
                resultado = esCifrado ? cesar.cifrar(msj, n) : cesar.descifrar(msj, n);
            } else {
                resultado = esCifrado ? vigenere.cifrar(msj, llave) : vigenere.descifrar(msj, llave);
            }

            txtSalida.setText(encabezado + resultado);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Para César la llave debe ser un número entero.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void procesarFuerzaBruta() {
        String msj = txtEntrada.getText();
        String algoritmo = (String) comboAlgoritmo.getSelectedItem();

        if (msj.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese el mensaje cifrado.");
            return;
        }

        txtSalida.setText("Procesando fuerza bruta...\nPor favor espere.\n");

        new Thread(() -> {
            try {
                List<String> resultados;

                if (algoritmo.equals("César")) {
                    resultados = cesar.descifrarFuerzaBruta(msj);
                } else {
                    // Fuerza bruta Vigenère necesita diccionario
                    Path rutaDiccionario = Path.of("Data/rockyou.txt");

                    // Verificamos si existe el archivo por defecto
                    File archivoDefecto = rutaDiccionario.toFile();
                    if (!archivoDefecto.exists()) {
                        JOptionPane.showMessageDialog(this, "No se encontró 'Data/rockyou.txt'.\nPor favor seleccione un archivo de diccionario.");
                        JFileChooser fileChooser = new JFileChooser();
                        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                            rutaDiccionario = fileChooser.getSelectedFile().toPath();
                        } else {
                            txtSalida.append("\nOperación cancelada por el usuario.");
                            return;
                        }
                    }

                    resultados = vigenere.descifrarFuerzaBruta(msj, rutaDiccionario);
                }

                // Actualizar la UI con los resultados
                StringBuilder sb = new StringBuilder();
                sb.append("=== RESULTADOS FUERZA BRUTA (" + algoritmo.toUpperCase() + ") ===\n\n");

                if (resultados.isEmpty()) {
                    sb.append("No se encontraron resultados o coincidencias.");
                } else {
                    for (String linea : resultados) {
                        sb.append(linea).append("\n");
                    }
                }

                SwingUtilities.invokeLater(() -> {
                    txtSalida.setText(sb.toString());
                    // Hacer scroll al inicio
                    txtSalida.setCaretPosition(0);
                });

            } catch (Exception e) {
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(this, "Error en fuerza bruta: " + e.getMessage()));
            }
        }).start();
    }
}