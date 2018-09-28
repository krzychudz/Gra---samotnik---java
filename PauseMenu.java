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


public class PauseMenu extends JDialog implements ActionListener, MouseListener
{

    JLabel pauseLabel = new JLabel("Pauza",SwingConstants.CENTER);  // Label z napisaem pauza
    JButton resumeButton = new JButton("Kontynuuj");                // Button do kontynuacji gry
    JButton exitButton = new JButton("Wyjście");                    // Button do wyjścia
    JButton restartButton = new JButton("Reset");                   // Button do resetu gry

    JFrame p;                                                            // okno na którym jest rysowana gra

    Runnable tThread;                                                    // Wątek zegara, który trzeba zatrzymać w związku z pauzą

    PauseMenu(JFrame parent, Runnable timerThread)
    {
        super(parent);                                                   // wywołanie metody klasy bazowej(JDialog)


        try {
            setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("pause_background1.png"))))); // Ustawienie tła menu
        } catch (IOException e) {
            e.printStackTrace();
        }


        p = parent;                                                      // przypisanie głównego okna do zmiennej pomocniczej

        setBounds(1368/2-240,768/2-150,480,200);    // ustawienie wymairów okna
        setAlwaysOnTop(true);                                           // ustawienie ze jest zawsze na wierzchu
        setModal(true);                                                 // ustawienie okna jako modalnego
        setUndecorated(true);                                           // wyłączenie przycisków sterujących
        setLayout(null);                                                // wyłączenie layout'a

        pauseLabel.setFont(new Font("Summer in December", Font.BOLD, 70)); // Zdefiniowanie i ustawienie napisu
        pauseLabel.setBounds(0,0,460,100);                        //
        add(pauseLabel);                                                              //

        resumeButton.setBounds(10,100,160,50);                        // Zdefiniowanie i usrawienie przycisku wznowienia gry
        resumeButton.addActionListener(this);                                          //
        resumeButton.addMouseListener(this);                                           //
        resumeButton.setFont(new Font("Summer in December", Font.PLAIN, 40)); //
        resumeButton.setContentAreaFilled(false);                                        //
        resumeButton.setBorderPainted(false);                                           //
        resumeButton.setOpaque(false);                                                   //
        add(resumeButton);                                                              // Dodanie do okna


        exitButton.setBounds(170,100,140,50);                            // Zdefiniowanie i ustawienie przycisku wyjścia
        exitButton.addActionListener(this);                                               //
        exitButton.addMouseListener(this);                                                //
        exitButton.setFont(new Font("Summer in December", Font.PLAIN, 40));      //
        exitButton.setContentAreaFilled(false);                                              //
        exitButton.setBorderPainted(false);                                                  //
        exitButton.setOpaque(false);                                                         //
        add(exitButton);                                                                     // Dodanie przycisku do okna

        restartButton.setBounds(330,100,140,50);                        // Zdefiniowanie i ustawienie przycisku resetu
        restartButton.addActionListener(this);                                           //
        restartButton.addMouseListener(this);                                            //
        restartButton.setFont(new Font("Summer in December", Font.PLAIN, 40));  //
        restartButton.setContentAreaFilled(false);                                          //
        restartButton.setBorderPainted(false);                                              //
        resumeButton.setOpaque(false);                                                      //
        add(restartButton);                                                                 // Dodanie przycisku do okna


        tThread = timerThread;                                           // przypisanie wątku zegara

        setVisible(true);                                                // ustawienie wydoczności okna dialogowego


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object ob1 = e.getSource();

        if(ob1 == resumeButton)                         // Co się stanie jeśli wciśniemy przcisk wznowienia gry
        {
            try {
                ((GameTimerThread)tThread).resume();   // Wznowienie wątku zegara aby mógł dalej liczyć
            }
            catch (NullPointerException el)
            {
                el.printStackTrace();                   // Wyświetlenie szczegółów ewentualnego wyjątku
            }
            tThread = null;
            dispose();                                  // Usunięcie okna
        }

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

        if(ob1 == restartButton) // Jeżeli opuścimy któregokolwiek buttona to wracamy do normalnego stylu
        {
            restartButton.setText("<html><b><u>Reset</u></b></html>");
        }

        if(ob1 == resumeButton)
        {
            resumeButton.setText("<html><b><u>Kontynuuj</u></b></html>");
        }

        if(ob1 == exitButton)
        {
            exitButton.setText("<html><b><u>Wyjście</u></b></html>");
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object ob1 = e.getSource();

        if(ob1 == restartButton)  // Jeżeli najedziemy na któregokolwiek buttona do podkreślamy jego napis i pobrubiamy
        {
            restartButton.setText("Reset");
        }

        if(ob1 == resumeButton)
        {
            resumeButton.setText("Kontynuuj");
        }

        if(ob1 == exitButton)
        {
            exitButton.setText("Wyjście");
        }



    }
}
