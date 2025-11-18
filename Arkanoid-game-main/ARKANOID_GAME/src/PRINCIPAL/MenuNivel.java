package PRINCIPAL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import CLASES.Juego;

public class MenuNivel {

    public static void main(String[] args) {
        JFrame menu = new JFrame("Arkanoid - Selección de Nivel");
        menu.setSize(500, 400);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, Color.BLACK, 0, getHeight(), Color.DARK_GRAY);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new GridBagLayout());
        menu.add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel titulo = new JLabel("ARKANOID");
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        gbc.gridy = 0;
        panel.add(titulo, gbc);

        for (int i = 1; i <= 3; i++) {
            int nivel = i;
            JButton boton = new JButton("Nivel " + nivel);
            boton.setFont(new Font("Arial", Font.BOLD, 20));
            boton.addActionListener(e -> {
                JFrame ventana = new JFrame("Arkanoid");
                Juego juego = new Juego();
                juego.setNivelActual(nivel);
                ventana.add(juego);
                ventana.setSize(800, 600);
                ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ventana.setResizable(false);
                ventana.setLocationRelativeTo(null);
                ventana.setVisible(true);
                juego.iniciarPartida();
                menu.dispose();
            });
            gbc.gridy = i;
            panel.add(boton, gbc);
        }

        JButton salir = new JButton("Salir");
        salir.setFont(new Font("Arial", Font.BOLD, 20));
        salir.addActionListener(e -> System.exit(0));
        gbc.gridy = 4;
        panel.add(salir, gbc);

        menu.setVisible(true);
    }
}
