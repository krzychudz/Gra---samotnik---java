package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import static com.company.SettingsPanel.Map;                // Import zmiennych statycznich
import static com.company.SettingsPanel.PawnMap;            //
import static com.company.SettingsPanel.nMapSize;           //

import static com.company.SettingsPanel.*;

public class ComputerGame extends JFrame implements MouseListener, ActionListener
{

    Vector<Pawn> pawns = new Vector<Pawn>();                            // Wektor do przechowywania pionów

    ImageIcon fieldIcon = new ImageIcon("field.png");       // Grafika pola mapy
    ImageIcon pawnIcon = new ImageIcon(graphicsPath);                // Grafika wybranego pionka

    Runnable computerGameThread;                                     // Zmienna przechoująca wątek sterujący komputerem

    JButton returnButton = new JButton("Powrót");               // Utworzenie przycisku powrotu

    ComputerGame()
    {
        setExtendedState(JFrame.MAXIMIZED_BOTH);                    // ustawienie trybu fullscreen
        setUndecorated(true);                                       // wyłączenie kontlek okna
        setLayout(null);                                            // wyłączenie layout'a


        returnButton.setBounds(10,700,150,50);                                  // Ustawienie i wyedytowanie pzyczisku
        returnButton.addActionListener(this);                                                    // Dodanie action Listenera
        returnButton.addMouseListener(this);                                                     // Dodanie Mouse Listenera
        returnButton.setFont(new Font("Summer in December", Font.PLAIN, 50));           // Ustawienie Font'a
        returnButton.setContentAreaFilled(false);                                                   // Ustawienie przezroczystości
        returnButton.setOpaque(false);                                                              // Ustawienie przezroczystości

        add(returnButton);                                                                          // Dodanie przycisku do okna


        for(int i = 0; i < nMapSize; i++)                                                           // Rysowanie pionów
        {
            for (int j = 0; j < nMapSize; j++)
            {
                if(PawnMap[i][j] != 0 && PawnMap[i][j] != -1) {                                         // Jeżeli w tablicy na danej pozycji nie ma 0 (nie ma piona) lub -1(poza planszą) to rysujemy piona
                    Pawn pawn = new Pawn((i*80)+xOffsetCom, (j*80)+yOffsetCom,this);      // Tworzymy obiekt piona
                    pawns.add(pawn);                                                                   // Dodajemy piona do okna
                }
            }

        }

        for(int i = 0; i < nMapSize; i++)                                                               // Rysowanie planszy
        {
            for (int j = 0; j < nMapSize; j++)
            {
                if(Map[i][j] == 1) {                             // Jeżeli w tablicy na danej pozycji jest 1(pole planszy) to rysujemy
                    JLabel f = new JLabel(fieldIcon);            // Tworzymy label z grafiką planszy
                    f.setBounds((i*80)+xOffsetCom,(j*80)+yOffsetCom,80,80); // Ustawiamy jesgo połozenie
                    f.setVisible(true);                                                         // Ustawiamy widoczność na true
                    add(f);                                                                     // Dodajemy do okna
                }
            }
        }


        computerGameThread = new ComputerGameThread(this,pawns);                            // Utworzenie wątku do sterowania komputerem
        Thread cg = new Thread(computerGameThread);                                            //
        cg.start();                                                                            // Uruchomienie wątku


        setVisible(true);                                                                      // Ustawienie widoczności okna

    }

    public void closeGame()                                 // Metoda uruchamiana przy zamknięciu gry poprzez menu pauzy w celu
    {                                                       // wyzerowania zmiennych statycznych tak aby można było grać ponownie
                                                            // bez konieczności włączania programu od początku
        Pawn.id = 1;                                        // Reset zmiennej statycznej
        pawns.clear();                                      // Wyczyszczenie wektora pionów
    }


    @Override
    public void mouseClicked(MouseEvent e) {                // Obsługa kliknięca myszy

        Object ob1 = e.getSource();                         // Pobranie zrodla sygnału

        if(ob1 == returnButton)                             // Obsługa zdarzaenie kliknieca na przycisk powrotu
        {
            ((ComputerGameThread)computerGameThread).stop(); // Zatrzymanie wątku komputera
            setVisible(false);                               // Ustawienie widoczności okna dialogowego na false
            closeGame();                                     // Wywołanei metody closeGame() z klasy Game, która resetuje wszytkie zmienna statyczne
            dispose();                                       // Usunięcie okna gry
            GameMenu gm = new GameMenu();                    // Utworzenie menu i przejście do niego
        }


    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {                            // Obsługa zdarzenia najechania na obiekt

        Object ob1 = e.getSource();

        if(ob1 == returnButton)                                         // Jeżeli najechaliśmy na przycisk pwrotu
        {
            returnButton.setText("<html><b><u>Powrót</u></b></html>"); // Pogrubiomy i podkreślamy napis
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {                            // Obsługa zdarzenia opuszczenia obiektu
        Object ob1 = e.getSource();

        if(ob1 == returnButton)                                        // Jeżeli opuszczamy przycisk
        {
            returnButton.setText("Powrót");                            // Zmieniamy na domyślny styl
        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
