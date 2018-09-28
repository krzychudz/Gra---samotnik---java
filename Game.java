package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.company.SettingsPanel.*;          // Import zmiennych statycznych
import static com.company.DrawMapThread.tileSize;   //


public class Game extends JFrame implements MouseListener, KeyListener
{

    int oldX = 0;                           // Stare połozenie piona X (przed ruchem)
    int oldY = 0;                           // Stare połozenie piona Y
    int oldValue = 0;                       // ID pionka którego przemieszczamy

    static boolean mouseThread = false;     // Zmienna pomocnicza do obsługi wątku przenoszenia pionów

    Vector<Pawn> pawns = new Vector<Pawn>();    // Wektor do przechowywania pionów

    Runnable GameTimerRunnalbe;              // Klasa licznika czasu gry


    int movesCounter = 0;                                                                       // Licznik ruchów gracza
    JLabel StrMoves = new JLabel("Liczba ruchów: " + Integer.toString(movesCounter));      // Label wyświetlający liczbe ruchów gracza

    SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");                          // Formater czasu

    JLabel GameTimeLimit = new JLabel("Limit czasu: " + formatter.format(new Date(timeValue))); // JLabel informujący o limicie czasu gry

    Game()
    {
        super("Gra");                           // Wywołanie konstruktora klasy bazowej (JFrame)

        setExtendedState(JFrame.MAXIMIZED_BOTH);     // ustawienie trybu fullscreen
        setUndecorated(true);                        // wyłączenie kontlek okna
        setLayout(null);                             // wyłączenie layout'a


        Thread MapPainter = new Thread(new DrawMapThread(Map,PawnMap,nMapSize,xOffset,yOffset,this,pawns)); // Utowrzenie wątku do rysowania mapy
        MapPainter.start();                                                                                           // Uruchomienie wątku rysowania planszy

        addKeyListener(this);                     // Dodanie Key Listenera do okna


        StatsPanel statsPanel = new StatsPanel();   // Stworzenie Panelu ze Statystykami

        StrMoves.setFont(new Font("Summer in December", StrMoves.getFont().getStyle(), 70)); // Ustawienie czcionki

        statsPanel.add(StrMoves);                               // Dodanie inforamcji o liczbie ruchów do panelu
        statsPanel.add(GameTimeLimit);                          // Dodanie inforamcji o czasie do panelu
        add(statsPanel);                                        // Dodanie panelu do okna

        setVisible(true);                                       // Ustawianie widoczności okna

        GameTimerRunnalbe = new GameTimerThread(statsPanel, this);    // Utorzenie instancji klasy licznika czasu gry
        Thread GameTimer = new Thread(GameTimerRunnalbe);       // Utowrzenie wątku do liczenia czasu gry
        GameTimer.start();                                      // Uruchomienie wątku liczącego czas gry

        GameTimeLimit.setFont(new Font("Summer in December", GameTimeLimit.getFont().getStyle(),70)); // Ustawienie fonta

    }


    public void closeGame()                                 // Metoda uruchamiana przy zamknięciu gry poprzez menu pauzy w celu
    {                                                       // wyzerowania zmiennych statycznych tak aby można było grać ponownie
                                                            // bez konieczności włączania programu od początku
        mouseThread = false;                                //
        Pawn.id = 1;                                        // Reset zmiennych statycznych
        pawns.clear();                                      //
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        Object ob1 = e.getSource();                      // Pobranie zródła zdarzenia

        if(ob1 instanceof Pawn) {

            PointerInfo a = MouseInfo.getPointerInfo(); // Pobranie informacji o myszy
            Point b = a.getLocation();                  // Zapisanie ich w klasie Point
            oldX = (int) b.getX();                      // Zapisanie ich jako oldX
            oldY = (int) b.getY();                      // oraz oldY


            oldValue = PawnMap[(oldX - xOffset) / tileSize][(oldY - yOffset) / tileSize];  // Zapisania ID piona do zmiennej oldValue

            Thread MouseThread = new Thread(new MouseThread((JLabel) ob1));     // Utowrzenie wątku do przyklejania piona do myszy
            mouseThread = true;                                                 // Ustawienie zmiennej pomocniczej przy obsłudze wątku
            MouseThread.start();                                                // Uruchomienie wątku

        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

            Object ob1 = e.getSource();         // Pobranie zródła zdarzenia

            mouseThread = false;                // Ustawienie zmiennej sterującej wątkiem na false, a tym samym zakończenie wątku
                                                // Czyli grafika piona odkleja się od kursora myszy

            PointerInfo a = MouseInfo.getPointerInfo();                       // Pobranie informacji o myszy
            Point b = a.getLocation();                                        // Zapisanie ich jako punktu
            int newCordX = ((int) b.getX() - xOffset) / tileSize;             // Przeliczenie pozycji myszy na koordynaty tablicy pionów
            int newCordY = ((int) b.getY() - yOffset) / tileSize;             //


            int oldCordX = (oldX - xOffset) / tileSize;                       // Stara pozycja przeliczona na koordynaty w tablicy
            int oldCordY = (oldY - yOffset) / tileSize;                       //


            if(oldCordX == newCordX && oldCordY == newCordY)    // Warunek sprawdzający czy pionek został odłożony
                                                                // na to samo miejsce z którego został podniesiony
            {
                ((JLabel) ob1).setBounds(oldCordX * tileSize + xOffset, oldCordY * tileSize + yOffset, tileSize, tileSize); // Cofnięcie piona na starą pozycję
            }
            else
        {
            try {
                if (PawnMap[newCordX][newCordY] == 0 && Map[newCordX][newCordY] == 1             // Sprawdzenie czy pozycja na którą chcemy przesunąć piona jest pusta i czy jest to część planszy
                        && ((Math.abs(oldCordX - newCordX) == 2 && oldCordY - newCordY == 0)     // Czy robimy ruch o 2 pola w pionie
                        || (Math.abs(oldCordY - newCordY) == 2 && oldCordX - newCordX == 0))     // Lub poziomie
                        && (PawnMap[(oldCordX + newCordX) / 2][(oldCordY + newCordY) / 2] != 0   // Czy pomiędzy starą pozycją a nową nie ma putego pola
                        && PawnMap[(oldCordX + newCordX) / 2][(oldCordY + newCordY) / 2] != -1)) // Lub czy nie jest to poza planszą
                {

                    ((JLabel) ob1).setBounds(newCordX * tileSize + xOffset, newCordY * tileSize + yOffset, tileSize, tileSize); // Ustawienie piona na nowej pozycji
                    PawnMap[newCordX][newCordY] = PawnMap[oldCordX][oldCordY];                                              // Ustawianie w tablicy MapPawn wartości id piona na nowej pozycji
                    PawnMap[oldCordX][oldCordY] = 0;                                                                        // Ustawienie starej pozycji jako pusta

                    movesCounter++;                                                         // Zwiększenie o 1 liczniak ruchów
                    StrMoves.setText("Liczba ruchów: " + Integer.toString(movesCounter));   // Zaktualizowanie labela wyświtlającego informacje o liczbie ruchów

                    for (int i = 0; i < pawns.size(); i++) {                                // Usuwanie zbitego piona

                        if (pawns.get(i).getID() == (PawnMap[(oldCordX + newCordX) / 2][(oldCordY + newCordY) / 2]))  // zlokalizowanie piona do usunięcia
                        {
                            pawns.get(i).setVisible(false);                                                           // ustawienie jego widoczności na false
                            PawnMap[(oldCordX + newCordX) / 2][(oldCordY + newCordY) / 2] = 0;                        // Ustawienie pola w tablicy PawnMap na 0 - puste
                            remove(pawns.get(i));                                                                     // usunięcie grafiki z okna
                            pawns.remove(i);                                                                          // usunięcie piona z wektora
                            break;                                                                                    // Jeżli znaleźliśmy piona to przerywamy pętle for
                        }
                    }

                    if(pawns.size() == 1)                                               // Jeżeli został jeden pionek to gracz wygrał
                    {
                        ((GameTimerThread)GameTimerRunnalbe).pause();                   // Wyłączenie zegara

                        WinScreen ws = new WinScreen(this,GameTimerRunnalbe,movesCounter);  // Uruchomienie okna dialogowego - ekran wygranej
                    }

                }
                else                                                // Jeżeli nie można wykonać ruchu
                    {
                    ((JLabel) ob1).setBounds(oldCordX * tileSize + xOffset, oldCordY * tileSize + yOffset, tileSize, tileSize);  // Cofnięcie piona na starą pozycję
                     PawnMap[oldCordX][oldCordY] = oldValue;                                                                           // Oznaczenie starej pozycji jako zajęta
                }
            }
            catch (ArrayIndexOutOfBoundsException e1) // Obsługa wyjątku wyrzucenia piona poza planszę
            {
                ((JLabel) ob1).setBounds(oldCordX * tileSize + xOffset, oldCordY * tileSize + yOffset, tileSize, tileSize); // Cofnięcie piona na starą pozycję
                PawnMap[oldCordX][oldCordY] = oldValue;                                                                           // Oznaczenie starej pozycji jako zajęta
                }
        }

           Thread gameState = new Thread(new AbleToMoveThread(this,GameTimerRunnalbe));  // Uruchomienie wątku sprawdzjącego czy mozna grać dalej
           gameState.start();                                                                //

            repaint();          // Przerysowanie okna

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)                            // Jeżeli został wciśnięty klawisz escape
        {
            ((GameTimerThread)GameTimerRunnalbe).pause();                   // Zapauzowanie zegara

            PauseMenu pm = new PauseMenu(this,GameTimerRunnalbe);   // Uruchomienie okna dialogowego - menu pazuy

        }

    }
}
