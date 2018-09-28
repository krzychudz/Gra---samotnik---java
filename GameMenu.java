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


public class GameMenu extends JFrame implements ActionListener, MouseListener
{
    JButton newGameButton = new JButton("Nowa gra");                    // przycisk nowa gra
    JButton settingsButton = new JButton("Ustawienia");                 // przycisk ustawienia
    JButton exitButton = new JButton("Wyjście");                        // przycisk wyjscie
    JLabel titleLabel = new JLabel("Samotnik",SwingConstants.CENTER);   // tytuł gry

    JPanel MainMenuPanel = new JPanel(new GridLayout(4,1,0,20));        // Panel menu głównego
    SettingsPanel settingsPanel; // Panel opcji



    JPanel choiceGameMode = new JPanel();                                           // Utworzenie panlu gdzie user wybiera tyb gry
    JButton playerGM = new JButton("Gracz");                                   // Utworzenie przycisku - tryb gry gracz
    JButton computerGM = new JButton("Komputer");                              // Utworzenie przycisku - tryb gry komputer
    JButton returnButton = new JButton("Powrót");                              // Utworzenie przycisku - powrót
    JLabel choiceGM = new JLabel("Wybierz tryb gry",SwingConstants.CENTER);    // Label informujący

    JRadioButton a = new JRadioButton(); // Pusty obiekt aby na nim znajdowało się obramowanie

    GameMenu()
    {
        super("Samotnik");                  // Wywołanie konstruktora klasy bazowej (JFrame)
        setSize(300,300);          // Ustawienie wielkości okna
        setExtendedState(JFrame.MAXIMIZED_BOTH); // ustawienie trybu fullscreen
        setUndecorated(true);                    // wyłączenie kontlek okna
        setLayout(null);                         // Wyłączenie Layout'a okna

        try {
            setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("background_menu1.png"))))); // Ustawienie tła menu
        } catch (IOException e) {
            e.printStackTrace();
        }



        a.setBounds(-500,-500,10,10);       // Wywalenie pustego obiektu na ekrna
        add(a);                                                 //

        /// PANEL MENU ////

        MainMenuPanel.setBounds(1366/2-100,200,200,500); // Ustawienie położenia i rozmiarów Panelu

        newGameButton.addActionListener(this);  // dodanie actionListenera do button'a
        newGameButton.addMouseListener(this);   //
        settingsButton.addActionListener(this); //
        settingsButton.addMouseListener(this);  //
        exitButton.addActionListener(this);     //
        exitButton.addMouseListener(this);      //


        newGameButton.setFont(new Font("Summer in December", Font.PLAIN, 50));      // Sytlizacja buttona
        newGameButton.setBorder(null);                                                          //
        newGameButton.setBorderPainted(false);                                                  //
        newGameButton.setContentAreaFilled(false);                                              //
        newGameButton.setOpaque(false);                                                         //
        newGameButton.setSize(new Dimension(150,150));                            //

        settingsButton.setFont(new Font("Summer in December", Font.PLAIN, 50));     // Sytlizacja buttona
        settingsButton.setBorder(null);                                                         //
        settingsButton.setBorderPainted(false);                                                 //
        settingsButton.setContentAreaFilled(false);                                             //
        settingsButton.setOpaque(false);                                                        //


        exitButton.setFont(new Font("Summer in December", Font.PLAIN, 50));         // Sylizacja buttona
        exitButton.setBorder(null);                                                             //
        exitButton.setBorderPainted(false);                                                     //
        exitButton.setContentAreaFilled(false);                                                 //
        exitButton.setOpaque(false);                                                            //


        titleLabel.setBounds(683-150,50,300,100);                          // Ustawienie wielkości i położenia tytułu
        titleLabel.setFont(new Font("Summer in December", Font.PLAIN, 100));       // Ustawienie czcionki dla Label'a
        add(titleLabel);                                                                       // Dodanie do okna


        MainMenuPanel.add(newGameButton);                                       // Dodanie przycisku nowaGra do okna
        MainMenuPanel.add(settingsButton);                                      // Dodanie przycisku ustawienia do okna
        MainMenuPanel.add(exitButton);                                          // Dodanie przycisku wyjście do okna



        MainMenuPanel.setVisible(true);                                         // Ustawienie widoczności panelowi menu

        MainMenuPanel.setOpaque(false);                                         // Ustawienie przezroczystosci


        add(MainMenuPanel);                                                     // Dodanie panelu do okna


        /// PANEL OPCJI ///

        settingsPanel = new SettingsPanel(MainMenuPanel, titleLabel);           // Utworzenie panelu opcji
        add(settingsPanel);                                                     // Dodanie go do okna


        /// PANEL OPCJI ///

        /// PANEL WYBORU

        choiceGameMode.setLayout(null);                                                     // Sytlizacja panelu wyboru rytbu gry
        choiceGameMode.setVisible(false);                                                   //
        choiceGameMode.setOpaque(false);                                                    //
        choiceGameMode.setBounds(1368/2-400,10,800,600);                //

        choiceGM.setBounds(0,50,800,100);                               // Ustawienie wielkości i położenia tytułu
        choiceGM.setFont(new Font("Summer in December", Font.PLAIN, 100));       // Ustawienie czcionki dla Label'a

        playerGM.setFont(new Font("Summer in December", Font.PLAIN, 80));       // Ustawienie wielkości i położenia buttona
        playerGM.setBounds(40,300,200,100);                            //
        playerGM.addActionListener(this);                                               //
        playerGM.addMouseListener(this);                                                //
        playerGM.setBorder(null);                                                           //
        playerGM.setBorderPainted(false);                                                   //
        playerGM.setContentAreaFilled(false);                                               //
        playerGM.setOpaque(false);                                                          //

        computerGM.setFont(new Font("Summer in December", Font.PLAIN, 80));    // Stylizacja buttona oraz dodanie niezbędnych komponentów
        computerGM.setBounds(500,300,300,100);                        //
        computerGM.addActionListener(this);                                             //
        computerGM.addMouseListener(this);                                              //
        computerGM.setBorder(null);                                                        //
        computerGM.setBorderPainted(false);                                                //
        computerGM.setContentAreaFilled(false);                                            //
        computerGM.setOpaque(false);                                                       //

        returnButton.setFont(new Font("Summer in December", Font.PLAIN, 80));   // Stylizacja buttona oraz dodanie niezbędnych komponentów
        returnButton.setBounds(250,450,300,100);                       //
        returnButton.addActionListener(this);                                           //
        returnButton.addMouseListener(this);                                            //
        returnButton.setBorder(null);                                                      //
        returnButton.setBorderPainted(false);                                              //
        returnButton.setContentAreaFilled(false);                                          //
        returnButton.setOpaque(false);                                                     //

        choiceGameMode.add(choiceGM);                                                     // Dodanie tytulu do panelu
        choiceGameMode.add(playerGM);                                                     // Dodanie buttona do panelu
        choiceGameMode.add(computerGM);                                                   //
        choiceGameMode.add(returnButton);                                                 //

        add(choiceGameMode);                                                              // Dodanie panelu do okna


        ///


        setVisible(true);                                                       // Ustawianie widoczności okna



    }

    @Override
    public void actionPerformed(ActionEvent e) // Obsługa zdarzeń od przycisków
    {
        Object ob1 = e.getSource();            // pobranie źródła akcji

        if(ob1 == newGameButton)               // Sprawdzenie czy od pierwszego przycisku
        {
            MainMenuPanel.setVisible(false);    // Wyłączenie panelu menu
            titleLabel.setVisible(false);       // Usunięcie tytułu gry
            choiceGameMode.setVisible(true);    // Włączenie panelu wyboru


        }

        if(ob1 == settingsButton)              // Sprawdzenie czy od drugigo przycisku
        {
            MainMenuPanel.setVisible(false);    // WYłączenie panelu gry
            settingsPanel.setVisible(true);     // Włączenie panelu opcji
            titleLabel.setVisible(false);       // Wyłączenie tytułu
        }

        if(ob1 == exitButton)                  // Sprawdzenie czy od trzeciego przycisku
        {
            System.exit(0);             // Wyłącznie programu
        }

        if(ob1 == playerGM)
        {
            dispose();                          // Usunięcie okna
            Game g = new Game();                // Przejście do gry
        }

        if(ob1 == computerGM)
        {
            dispose();                            // Usunięcie okna
            ComputerGame cg = new ComputerGame(); // Przejscie do gry komputera
        }

        if(ob1 == returnButton)
        {
            choiceGameMode.setVisible(false);       // Powrót do menu
            MainMenuPanel.setVisible(true);         //
            titleLabel.setVisible(true);            //
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

        if(ob1 == newGameButton)  // Jeżeli najedziemy na któregokolwiek buttona do podkreślamy jego napis i pobrubiamy
        {
            newGameButton.setText("<html><b><u>Nowa gra</u></b></html>");
        }

        if(ob1 == settingsButton)
        {
            settingsButton.setText("<html><b><u>Ustawienia</u></b></html>");
        }

        if(ob1 == exitButton)
        {
            exitButton.setText("<html><b><u>Wyjście</u></b></html>");
        }


        if(ob1 == computerGM)
        {
            computerGM.setText("<html><b><u>Komputer</u></b></html>");
        }

        if(ob1 == playerGM)
        {
            playerGM.setText("<html><b><u>Gracz</u></b></html>");
        }

        if(ob1 == returnButton)
        {
            returnButton.setText("<html><b><u>Powrót</u></b></html>");
        }


    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object ob1 = e.getSource();

        if(ob1 == newGameButton) // Jeżeli opuścimy któregokolwiek buttona to wracamy do normalnego stylu
        {
            newGameButton.setText("Nowa gra");

        }

        if(ob1 == settingsButton)
        {
            settingsButton.setText("Ustawienia");

        }


        if(ob1 == exitButton)
        {
            exitButton.setText("Wyjście");

        }

        if(ob1 == computerGM)
        {
            computerGM.setText("Komputer");
        }

        if(ob1 == playerGM)
        {
            playerGM.setText("Gracz");
        }

        if(ob1 == returnButton)
        {
            returnButton.setText("Powrót");
        }


    }
}
