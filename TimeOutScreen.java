package com.company;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class TimeOutScreen extends JDialog implements ActionListener,MouseListener
{

    JLabel loseLabel = new JLabel("Przegrałeś",SwingConstants.CENTER);      // Label z napisaem przegrałeś
    JLabel text = new JLabel("Koniec czasu",SwingConstants.CENTER);         // Label z napisame koniec czasu
    JButton exitButton = new JButton("Wyjście");                            // Przycisk wyjście
    JButton restartButton = new JButton("Reset");                           // Przycisk reset

    JFrame p;                                                                    // Przechowywanie okna gry

    TimeOutScreen(JFrame parent)
    {

        super(parent);                                                          // Konstrukto klasy bazowej

        try {
            setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("lose_background.png"))))); // Ustawienie tła menu
        } catch (IOException e) {
            e.printStackTrace();
        }

        p = parent;                                                      // przypisanie głównego okna do zmiennej pomocniczej

        setBounds(1368/2-300,768/2-300,600,300);    // ustawienie wymairów okna
        setAlwaysOnTop(true);                                           // ustawienie ze jest zawsze na wierzchu
        setModal(true);                                                 // ustawienie okna jako modalnego
        setUndecorated(true);                                           // wyłączenie przycisków sterujących
        setLayout(null);                                                // wyłączenie layout'a

        loseLabel.setFont(new Font("Summer in December", Font.BOLD, 70)); // Zdefiniowanie i ustawienie napisu
        loseLabel.setOpaque(false);                                                   //
        loseLabel.setBounds(0,10,600,100);                       //
        add(loseLabel);                                                              // Dodanie do okna

        text.setFont(new Font("Summer in December", Font.BOLD, 50)); // Zdefiniowanie i ustawienie napisu
        text.setOpaque(false);                                                  //
        text.setBounds(0,110,600,100);                      //
        add(text);                                                              // Dodanie do okna

        exitButton.setBounds(20,220,270,70);                        // Zdefiniowanie i ustawienie przycisku wyjścia
        exitButton.setFont(new Font("Summer in December", Font.PLAIN, 40));  //
        exitButton.addActionListener(this);                                           //
        exitButton.addMouseListener(this);                                            //
        exitButton.setContentAreaFilled(false);                                          //
        exitButton.setBorderPainted(false);                                              //
        exitButton.setOpaque(false);                                                     //
        add(exitButton);                                                                 // Dodanie do okna

        restartButton.setBounds(300,220,270,70);                        // Zdefiniowanie i ustawienie przycisku resetu
        restartButton.setFont(new Font("Summer in December", Font.PLAIN, 40));   //
        restartButton.addActionListener(this);                                            //
        restartButton.addMouseListener(this);                                             //
        restartButton.setContentAreaFilled(false);                                           //
        restartButton.setBorderPainted(false);                                               //
        restartButton.setOpaque(false);                                                      //
        add(restartButton);                                                                  // Dodanie do okna


        setVisible(true);                                                                   // Ustawienie widoczności

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        Object ob1 = e.getSource();

        if(ob1 == exitButton)                           // Co się stanie jeśli wciśniemy przycisk wyjścia z gry
        {
            p.setVisible(false);                        // Ustawienie widoczności okna dialogowego na false
            ((Game)p).closeGame();                      // Wywołanei metody closeGame() z klasy Game, która resetuje wszytkie zmienna statyczne
            p.dispose();                                // Usunięcie okna gry
            p = null;
            dispose();                                  // Usnięcie okna dialogowego
            GameMenu gm = new GameMenu();               // Utworzenie menu i przejście do niego
        }

        if(ob1 == restartButton)
        {
            p.setVisible(false);                        // Ustawienie widoczności okna dialogowego na false
            ((Game)p).closeGame();                      // Wywołanei metody closeGame() z klasy Game, która resetuje wszytkie zmienna statyczne
            p.dispose();                                // Usunięcie okna gry
            p = null;
            dispose();                                  // Usnięcie okna dialogowego
            SettingsPanel.Reset();
            Game game = new Game();                     // Utworzenie nowego okna z grą

        }


    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object ob1 = e.getSource();

        if(ob1 == restartButton) // Jeżeli najedziemy na któregokolwiek buttona do podkreślamy jego napis i pobrubiamy
        {
            restartButton.setText("<html><b><u>Reset</u></b></html>");
        }


        if(ob1 == exitButton)
        {
            exitButton.setText("<html><b><u>Wyjście</u></b></html>");
        }
    }



    @Override
    public void mouseExited(MouseEvent e) {
        Object ob1 = e.getSource();

        if(ob1 == restartButton)   // Jeżeli opuścimy któregokolwiek buttona to wracamy do normalnego stylu
        {
            restartButton.setText("Reset");
        }


        if(ob1 == exitButton)
        {
            exitButton.setText("Wyjście");
        }


    }
}

