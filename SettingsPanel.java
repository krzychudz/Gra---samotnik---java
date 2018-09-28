package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class SettingsPanel extends JPanel implements ActionListener, ChangeListener, MouseListener
{
    JLabel settingLabel = new JLabel("Ustawienia",SwingConstants.CENTER);               // Utworznie labeli informujących oraz przyisków do osługi
    JLabel mapSize = new JLabel("Wielkość mapy",SwingConstants.CENTER);                 //
    JLabel gameTimeLimit = new JLabel("Limit czasu gry",SwingConstants.CENTER);         //
    JLabel pawnChoice = new JLabel("Wybór pionka",SwingConstants.CENTER);               //
    JRadioButton tradMap = new JRadioButton("Wersja tradycyjna 37 pionów");             // Radio button odpowidający na wybór piona
    JRadioButton nowMap = new JRadioButton("Wersja nowoczesna 33 pionów", true);              //
    JRadioButton diamMap = new JRadioButton("Diament 40 pionów");                       //

    JRadioButton black_pawn = new JRadioButton(new ImageIcon("black_pawn.png"),true);       // Ustawienie grafiki RButtona
    JRadioButton white_pawn = new JRadioButton(new ImageIcon("white_pawn.png"), false);     //
    JRadioButton ball_pawn = new JRadioButton(new ImageIcon("ball_pawn.png"),false);        //

    JSlider timeLimitSlider = new JSlider(JSlider.HORIZONTAL,0, 300, 0);                    // Utworzenie slidera do ustawiania czasu gry
    JLabel timeLimitLabel = new JLabel();                                                                    // Label informujący

    SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");     // Formater daty

    static int timeValue = 0;                                               // Zmienna przechowująca czas rozgrywki w ms

    JButton applyButton = new JButton("Zastosuj");                     // Utworzenie przycisku zastosuj
    JButton returnButton = new JButton("Powrót");                      // Utworzenie przycisku powrót

    static int [][] Map;                                                    // Tablica pokazująca planszę
    static int [][] PawnMap;                                                // Tablica pokazująca piony
    static int nMapSize;                                                    // Zmienna określająca rozmiar planszy
    static String graphicsPath = "black_pawn.png";                          // Scieżka do grafiki piona
    static int xOffset;                                                     // Przesunięcie planszy X
    static int yOffset;                                                     // Przesunięcie planszy X

    static int xOffsetCom;                                                  // Przesunięcie planszy X dla komputera
    static int yOffsetCom;                                                  // Przesunięcie planszy Y dla komputera

    static int whichMap = 1;                                                // Zmienna określająca który rodzaj mapy jest wybrany

    JPanel MainMenuPanel;                                                   // Zmienna przechowująca panel gry
    JLabel title;                                                           // Zmienna przechowująca tytuł gy

    SettingsPanel(JPanel mmPanel, JLabel title)
    {

        this.title = title;                                                 // Przypisanie zmiennych początkowych
        MainMenuPanel = mmPanel;                                            //
        nMapSize = 7;                                                       //
        xOffset = 100;                                                      //
        yOffset = 100;                                                      //

        xOffsetCom = 1368/2-280;                                            //
        yOffsetCom = 768/2-280;                                             //

        int [][] MapS =                                                     // Ustalenie domyślnej mapy początkowej
                {
                        {2,2,1,1,1,2,2},
                        {2,2,1,1,1,2,2},
                        {1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1},
                        {2,2,1,1,1,2,2},
                        {2,2,1,1,1,2,2}
                };

        Map = MapS;                                                         // Przypisanie do zmiennej statycznej

        int [][] PawnMapS =                                                 // Ustalenie domyślnej mapy pionów początkowej
                {
                        {-1,-1,1,2,3,-1,-1},
                        {-1,-1,4,5,6,-1,-1},
                        {7,8,9,10,11,12,13},
                        {14,15,16,0,17,18,19},
                        {20,21,22,23,24,25,26},
                        {-1,-1,27,28,29,-1,-1},
                        {-1,-1,30,31,32,-1,-1}
                };

        PawnMap = PawnMapS;                                                 // Przypisanie do zmiennej statycznej

        setOpaque(false);                                                   // Ustawienie przzroczystości

        setLayout(null);                                                    // Wyłącznie layouta
        setBounds(0,0,1368,768);                        // Ustawienie wymiarów i położenia Layout'a



        settingLabel.setBounds(1366/2-150,50,300,100);                       // Ustawienie wielkości i położenia tytułu
        settingLabel.setFont(new Font("Summer in December", Font.PLAIN, 100));       // Ustawienie Font'a
        settingLabel.setOpaque(false);                                                           // Ustawienie przezroczystosci
        add(settingLabel);                                                                       // Dodanie do okna

        mapSize.setBounds(0,100,300,100);                                 // Ustawienie wielkości i położenia tytułu
        mapSize.setFont(new Font("Summer in December", Font.PLAIN, 60));           // Ustawienie Font'a
        mapSize.setOpaque(false);                                                             //
        add(mapSize);                                                                         // Dodanie do okna


        tradMap.setOpaque(false);                                                             // Ustawienie przezroczystosc RButtonow
        nowMap.setOpaque(false);                                                              //
        diamMap.setOpaque(false);                                                             //

        ButtonGroup group = new ButtonGroup();                                  // Utworzenie grpy przycisków tak aby dało się wcisnąć tylko jeden z nich
        group.add(tradMap);                                                     // Dodanie RadioButtoów do grupy
        group.add(nowMap);                                                      //
        group.add(diamMap);                                                     //

        tradMap.setBounds(50,150,400,100);                                // Ustawienie wspolrzednych i rozmirow
        tradMap.setFont(new Font("Summer in December", Font.PLAIN, 40));           // Ustawienie Font'a

        add(tradMap);                                                                          // Dodanie do okna

        nowMap.setBounds(50,210,400,100);                                  // Ustawienie wspolrzednych i rozmirow
        nowMap.setFont(new Font("Summer in December", Font.PLAIN, 40));           // Ustawienie Font'a

        add(nowMap);                                                                           // Dodanie do okna

        diamMap.setBounds(50,270,400,100);                                 // Ustawienie wspolrzednych i rozmirow
        diamMap.setFont(new Font("Summer in December", Font.PLAIN, 40));           // Ustawienie Font'a

        add(diamMap);                                                                          // Dodanie do okna

        pawnChoice.setBounds(0,330,300,100);                                // Ustawienie wspolrzednych i rozmirow
        pawnChoice.setFont(new Font("Summer in December", Font.PLAIN, 60));          // Ustawienie Font'a

        add(pawnChoice);                                                                        // Dodanie do okna

        /// PIONY W LINI

        ButtonGroup group1 = new ButtonGroup();                                  // Utworzenie grpy przycisków tak aby dało się wcisnąć tylko jeden z nich
        group1.add(black_pawn);                                                  // Dodanie RadioButtoów do grupy
        group1.add(white_pawn);                                                  //
        group1.add(ball_pawn);

        white_pawn.setBounds(40,420,80,80);                  // Ustawienie przycisków
        add(white_pawn);                                                         // Dodanie do okna

        black_pawn.setBounds(130,420,80,80);                // Ustawienie przycisków
        add(black_pawn);                                                        // Dodanie do okna

        ball_pawn.setBounds(220,420,80,80);                  // Ustawienie przycisków
        add(ball_pawn);                                                          // Dodanie do okna

        black_pawn.setOpaque(false);                                            // Stylizacja przycisków
        white_pawn.setOpaque(false);                                            //
        ball_pawn.setOpaque(false);                                             //

        black_pawn.setBorderPainted(true);                                      //
        white_pawn.setBorderPainted(true);                                      //
        ball_pawn.setBorderPainted(true);                                       //

        black_pawn.addMouseListener(this);                                   //
        white_pawn.addMouseListener(this);                                   //
        ball_pawn.addMouseListener(this);                                    //


        gameTimeLimit.setBounds(0,500,300,100);                             // Ustawienie wielkości i położenia tytułu
        gameTimeLimit.setFont(new Font("Summer in December", Font.PLAIN, 60));      // Ustawienie Font'a

        add(gameTimeLimit);                                                                     // Dodanie do okna

        timeLimitSlider.setBounds(10,580,300,50);                           // Stylizacaja slidera
        timeLimitSlider.setOpaque(false);                                                       //
        timeLimitSlider.setMinimum(0);                                                          //
        timeLimitSlider.setMaximum(300);                                                        //
        timeLimitSlider.getValue();                                                             //
        timeLimitSlider.addChangeListener(this);                                             //
        add(timeLimitSlider);                                                                   // Dodanie do okna

        timeLimitLabel.setFont(new Font("Summer in December", Font.PLAIN, 40));           // Ustawienie Font'a
        timeLimitLabel.setText(formatter.format(new Date(0)));                                // Sformatowanie ms na mm:ss
        timeLimitLabel.setBounds(320,550,100,100);                               // Ustawienie label'a
        add(timeLimitLabel);                                                                         // Dodanie do okna

        ///SUWAK I CZAS

        JPanel buttonPanel = new JPanel();                                                          // Panel na przycisk potwierdzenia
        JPanel buttonPanel1 = new JPanel();                                                         // Panel na przycisk powrotu
        buttonPanel.setOpaque(false);                                                               //
        buttonPanel.setBounds(20,650,150,200);                                  //
        buttonPanel1.setOpaque(false);                                                              //
        buttonPanel1.setBounds(170,650,150,200);                                //

        applyButton.addActionListener(this);                                                     //
        applyButton.addMouseListener(this);                                                      //
        applyButton.setFont(new Font("Summer in December", Font.PLAIN, 40));            // Ustawienie Font'a
        applyButton.setContentAreaFilled(false);                                                    // Przezroczystosc

        returnButton.addActionListener(this);                                                    //
        returnButton.addMouseListener(this);                                                     //
        returnButton.setFont(new Font("Summer in December", Font.PLAIN, 40));           // Ustawienie Font'a
        returnButton.setContentAreaFilled(false);                                                   //

        buttonPanel.add(returnButton);              // Dodanie do panelu
        buttonPanel1.add(applyButton);              //

        add(buttonPanel);                           // Dodanie panelu do okna
        add(buttonPanel1);                          // Dodanie panelu do okna

        black_pawn.setBorder(BorderFactory.createLineBorder(Color.black,2)); // Ustawienie obwódki czarnego piona jako domyślnego


        setVisible(false);                          // Ustawienie widoczności na false

    }


    static void Reset()                           // Metoda resetująca położenia pionów na poszczególnych mapach
    {
        if(whichMap == 1)
        {
            int [][] PawnMapS =
                    {
                            {-1,-1,1,2,3,-1,-1},
                            {-1,-1,4,5,6,-1,-1},
                            {7,8,9,10,11,12,13},
                            {14,15,16,0,17,18,19},
                            {20,21,22,23,24,25,26},
                            {-1,-1,27,28,29,-1,-1},
                            {-1,-1,30,31,32,-1,-1}
                    };

            PawnMap = PawnMapS;

        }

        if(whichMap == 2)
        {
            int [][] PawnMapS =
                    {
                            {-1,-1,1,2,3,-1,-1},
                            {-1,4,5,6,7,8,-1},
                            {9,10,11,12,13,14,15},
                            {16,17,0,18,19,20,21},
                            {22,23,24,25,26,27,28},
                            {-1,29,30,31,32,33,-1},
                            {-1,-1,34,35,36,-1,-1}
                    };

            PawnMap = PawnMapS;

        }

        if(whichMap == 3)
        {
            int [][] PawnMapS =
                    {
                            {-1,-1,-1,-1,1,-1,-1,-1,-1},
                            {-1,-1,-1,2,3,4,-1,-1,-1},
                            {-1,-1,5,6,7,8,9,-1,-1},
                            {-1,10,11,12,13,14,15,16,-1},
                            {17,18,19,20,0,21,22,23,24},
                            {-1,25,26,27,28,29,30,31,-1},
                            {-1,-1,32,33,34,35,36,-1,-1},
                            {-1,-1,-1,37,38,39,-1,-1,-1},
                            {-1,-1,-1,-1,40,-1,-1,-1,-1}
                    };

            PawnMap = PawnMapS;
        }



    }


    @Override
    public void actionPerformed(ActionEvent e) {

        Object ob1 = e.getSource();

        if(ob1 == applyButton)        // Jeżeli naciśnieto przycisk zasostuj to przypisaujemy wszytkie ustawione wartości do zmiennych statycznych
        {
            if(tradMap.isSelected())    // Jeżeli tradycyjna mapa
            {
                whichMap = 2;
                nMapSize = 7;
                xOffset = 100;
                yOffset = 100;

                xOffsetCom = 1368/2-280;
                yOffsetCom = 768/2-280;

                int [][] MapS =
                        {
                                {2,2,1,1,1,2,2},
                                {2,1,1,1,1,1,2},
                                {1,1,1,1,1,1,1},
                                {1,1,1,1,1,1,1},
                                {1,1,1,1,1,1,1},
                                {2,1,1,1,1,1,2},
                                {2,2,1,1,1,2,2}

                        };

                Map = MapS;

                int [][] PawnMapS =
                        {
                                {-1,-1,1,2,3,-1,-1},
                                {-1,4,5,6,7,8,-1},
                                {9,10,11,12,13,14,15},
                                {16,17,0,18,19,20,21},
                                {22,23,24,25,26,27,28},
                                {-1,29,30,31,32,33,-1},
                                {-1,-1,34,35,36,-1,-1}
                        };

                PawnMap = PawnMapS;

            }

            if(nowMap.isSelected())  // Jeżeli nowa mapa
            {
                whichMap = 1;
                nMapSize = 7;
                xOffset = 100;
                yOffset = 100;

                xOffsetCom = 1368/2-280;
                yOffsetCom = 768/2-280;

                int [][] MapS =
                        {
                                {2,2,1,1,1,2,2},
                                {2,2,1,1,1,2,2},
                                {1,1,1,1,1,1,1},
                                {1,1,1,1,1,1,1},
                                {1,1,1,1,1,1,1},
                                {2,2,1,1,1,2,2},
                                {2,2,1,1,1,2,2}

                        };

                Map = MapS;

                int [][] PawnMapS =
                        {
                                {-1,-1,1,2,3,-1,-1},
                                {-1,-1,4,5,6,-1,-1},
                                {7,8,9,10,11,12,13},
                                {14,15,16,0,17,18,19},
                                {20,21,22,23,24,25,26},
                                {-1,-1,27,28,29,-1,-1},
                                {-1,-1,30,31,32,-1,-1}
                        };

                PawnMap = PawnMapS;

            }

            if(diamMap.isSelected())  // Jeżeli diamentowa mapa
            {
                nMapSize = 9;
                whichMap = 3;
                xOffset = 60;
                yOffset = 20;

                xOffsetCom = 1368/2-360;
                yOffsetCom = 768/2-360;

                int [][] MapS =
                        {
                                {2,2,2,2,1,2,2,2,2},
                                {2,2,2,1,1,1,2,2,2},
                                {2,2,1,1,1,1,1,2,2},
                                {2,1,1,1,1,1,1,1,2},
                                {1,1,1,1,1,1,1,1,1},
                                {2,1,1,1,1,1,1,1,2},
                                {2,2,1,1,1,1,1,2,2},
                                {2,2,2,1,1,1,2,2,2},
                                {2,2,2,2,1,2,2,2,2},

                        };

                Map = MapS;



                int [][] PawnMapS =
                        {
                                {-1,-1,-1,-1,1,-1,-1,-1,-1},
                                {-1,-1,-1,2,3,4,-1,-1,-1},
                                {-1,-1,5,6,7,8,9,-1,-1},
                                {-1,10,11,12,13,14,15,16,-1},
                                {17,18,19,20,0,21,22,23,24},
                                {-1,25,26,27,28,29,30,31,-1},
                                {-1,-1,32,33,34,35,36,-1,-1},
                                {-1,-1,-1,37,38,39,-1,-1,-1},
                                {-1,-1,-1,-1,40,-1,-1,-1,-1}
                        };

                PawnMap = PawnMapS;

            }

            if(black_pawn.isSelected())              // Ustawienie ścieżki do grafiki wybranego piona
            {
                graphicsPath = "black_pawn.png";
            }

            if(white_pawn.isSelected())
            {
                graphicsPath = "white_pawn.png";
            }

            if(ball_pawn.isSelected())
            {
                graphicsPath = "ball_pawn.png";
            }

            setVisible(false);                                  // Powrót do menu
            MainMenuPanel.setVisible(true);                     //
            title.setVisible(true);                             //
        }

        if(ob1 == returnButton)                                 // Jeżeli naciśnieto powrót
        {
            setVisible(false);                                  // Powrót do menu
            MainMenuPanel.setVisible(true);                     //
            title.setVisible(true);                             //
        }


    }

    @Override
    public void stateChanged(ChangeEvent e) {
        Object ob1 = e.getSource();

        if(ob1 == timeLimitSlider)                                             // Jeżeli zmieniamy slider
        {
            timeValue = timeLimitSlider.getValue() * 1000;                     // Dodajem 1s do czasu
            timeLimitLabel.setText(formatter.format(new Date(timeValue)));     // Pokazujemy czas na labelu

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object ob1 = e.getSource();

        if(ob1 == black_pawn)                          // Jeżęli wybraliśmy grafikę piona to bierzemy go w obwódkę
        {
            black_pawn.setBorder(BorderFactory.createLineBorder(Color.black,2));
            white_pawn.setBorder(null);
            ball_pawn.setBorder(null);

        }

        if(ob1 == white_pawn)
        {
            white_pawn.setBorder(BorderFactory.createLineBorder(Color.black,2));
            black_pawn.setBorder(null);
            ball_pawn.setBorder(null);
        }

        if(ob1 == ball_pawn)
        {
            ball_pawn.setBorder(BorderFactory.createLineBorder(Color.black,2));
            black_pawn.setBorder(null);
            white_pawn.setBorder(null);
        }

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

        if(ob1 == applyButton)  // Jeżeli opuścimy któregokolwiek buttona to wracamy do normalnego stylu
        {
            applyButton.setText("<html><b><u>Zastosuj</u></b></html>");
        }

        if(ob1 == returnButton)
        {
            returnButton.setText("<html><b><u>Powrót</u></b></html>");
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {

        Object ob1 = e.getSource();

        if(ob1 == applyButton)  // Jeżeli najedziemy na któregokolwiek buttona do podkreślamy jego napis i pobrubiamy
        {
            applyButton.setText("Zastosuj");

        }

        if(ob1 == returnButton)
        {
            returnButton.setText("Powrót");

        }
    }
}
