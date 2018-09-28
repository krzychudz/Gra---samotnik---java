package com.company;

import javax.swing.*;
import java.util.Vector;
import static com.company.SettingsPanel.*; // Import zmiennych statycznych


public class ComputerGameThread implements Runnable
{

    private volatile boolean running = true;                                // Zmienna która określa czy wątek jest uruchomiony
    private volatile boolean paused = false;                                // Zmienna która określa czy wątek jest zapauzowany

    boolean ableToMove = false;                                             // Zmienna określająca zdlonośc do ruchu

    private final Object pauseLock = new Object();                          // Uworzenie obiektu na którym będzie synchronizowany wątek
    private final Object syn = new Object();                                // Utworzenie obiektu na ktróym będzie synchronizowany wait


    JFrame mainFrame;                                                       // Zmienna przechowująca główne okno gry
    Vector<Pawn> PawnsVector;                                               // Wektor przechowujący piony


    int newCordX;                   // Zmienna przchowująca nowe współrzędne przesuwanego piona
    int newCordY;                   //

    int cordToDeleteX;              // Zmienna przechowująca współrzędne piona do usunięcia
    int cordToDeleteY;              //

    int cordActualX;                // Zmienna przechowująca aktualne współrzędne piona
    int cordActualY;                //


    ComputerGameThread(JFrame f, Vector<Pawn> pawns)
    {
        mainFrame = f;              // Przypisanie głównego okna do zmiennej pomocniczej
        PawnsVector = pawns;        // Przypisanie wektora pionów do zmiennej pomocniczej
    }

    @Override
    public void run() {

        while (running) {          // Pętla główna wątku

            synchronized (pauseLock)                        // Sekcja krytyczna
            {
                if(!running)                                // Sprawdzenie warunku na działanie wątku
                    break;
                if(paused)
                {
                    try {
                        pauseLock.wait();                   // i zatrzymanie wątku
                    } catch (InterruptedException ex) {
                        break;
                    }
                    if (!running) {                         // Sprawdzenie czy running nie zostało zmienione podczas pauzy
                        break;
                    }
                }

                synchronized (syn) {                        // Zmienna synchronizująca wait'a

                    try {
                        syn.wait(1000);             // Celowe nadanie 2s opóźnienia komputerowi aby grał bardziej naturalnie
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }




            for (int i = 0; i < nMapSize; i++)              // Przeszukiwanie mapy pionów w celu znalezienia i wykoniania ruchu
            {

                for (int j = 0; j < nMapSize; j++)
                {

                    try {
                        if ((PawnMap[i][j] != -1 && PawnMap[i][j] != 0) &&                  // Jeżeli na aktualnym polu jest pion
                                (PawnMap[i][j + 1] != -1 && PawnMap[i][j + 1] != 0) &&      // Z prawej stony tez
                                (PawnMap[i][j + 2] == 0 && Map[i][j + 2] != 2))             // Oraz 2 pola dalej jest pole puste i jest w obrebie planszy
                        {                                                                   // To wykonujemy ruch


                            cordActualX = i;                        // Pobieramy aktualna pozycje piona
                            cordActualY = j;                        //

                            newCordX = i;                           // Pobieramy nowa pozcyje piona
                            newCordY = j + 2;                       //

                            cordToDeleteX = i;                      // Pobieramy pozycje piona do usuniecia
                            cordToDeleteY = j + 1;                  //

                            ableToMove = true;                      // Ustawiamy flage mowiąca o mozliwości wykoniania ruchu
                            break;                                  // Przerywamy sprawdzanie

                        }
                        }
                        catch (ArrayIndexOutOfBoundsException el)
                        {

                        }


                    try {
                        if ((PawnMap[i][j] != -1 && PawnMap[i][j] != 0) &&              // TO samo co powyzej tylko sprawdzamy piony na lewo
                                (PawnMap[i][j - 1] != -1 && PawnMap[i][j - 1] != 0) &&
                                (PawnMap[i][j - 2] == 0) && Map[i][j - 2] != 2)
                        {
                            cordActualX = i;
                            cordActualY = j;

                            newCordX = i;
                            newCordY = j - 2;

                            cordToDeleteX = i;
                            cordToDeleteY = j - 1;



                            ableToMove = true;
                            break;

                        }
                    }
                    catch (ArrayIndexOutOfBoundsException el)
                    {

                    }


                    try {
                        if ((PawnMap[i][j] != -1 && PawnMap[i][j] != 0) &&               // TO samo co powyzej tylko sprawdzamy piony do góry
                                (PawnMap[i + 1][j] != -1 && PawnMap[i + 1][j] != 0) &&
                                (PawnMap[i + 2][j] == 0) && Map[i + 2][j] != 2)
                        {
                            cordActualX = i;
                            cordActualY = j;

                            newCordX = i + 2;
                            newCordY = j;

                            cordToDeleteX = i + 1;
                            cordToDeleteY = j;


                            ableToMove = true;
                            break;

                        }
                    }
                    catch (ArrayIndexOutOfBoundsException el)
                    {

                    }


                    try {
                        if ((PawnMap[i][j] != -1 && PawnMap[i][j] != 0) &&               // TO samo co powyzej tylko sprawdzamy piony w dół
                                (PawnMap[i - 1][j] != -1 && PawnMap[i - 1][j] != 0) &&
                                (PawnMap[i - 2][j] == 0) && Map[i - 2][j] != 2)
                        {
                            cordActualX = i;
                            cordActualY = j;

                            newCordX = i - 2;
                            newCordY = j;

                            cordToDeleteX = i - 1;
                            cordToDeleteY = j;


                            ableToMove = true;
                            break;

                        }
                    }
                    catch (ArrayIndexOutOfBoundsException el)
                    {

                    }


                }






                if (ableToMove)                                                 // Jeżeli została ustawiona flaga to nalezy wykonac ruch
                {

                    for(int z = 0; z < PawnsVector.size(); z++)                 // Przeszukujemy wektor w celu znalezienia odpowiedniego piona do przemieszczenia
                    {
                        if(PawnsVector.get(z).getID() == PawnMap[cordActualX][cordActualY])                          // Jeżeli znaleźlismy to
                        {
                            PawnMap[newCordX][newCordY] = PawnMap[cordActualX][cordActualY];                         // Zamieniamy wartości w tablicy
                            PawnMap[cordActualX][cordActualY] = 0;                                                   // Starą pozycje ustawiamy na pusatą

                            PawnsVector.get(z).setBounds(newCordX*80+xOffsetCom,newCordY*80+yOffsetCom,80,80); // Rysujemy piona na nowej pozycji

                        }

                    }

                    for(int z = 0; z < PawnsVector.size(); z++)         // Przeszukujemy wektor w celu znaleznienia piona do usunięcia
                    {
                        if(PawnsVector.get(z).getID() == PawnMap[cordToDeleteX][cordToDeleteY])     // Jeżeli znajdziemy
                        {
                            try {
                                PawnsVector.get(z).setVisible(false);                              // ustawienie jego widoczności na false
                                PawnMap[cordToDeleteX][cordToDeleteY] = 0;                        // Ustawienie pola w tablicy PawnMap na 0 - puste
                                mainFrame.remove(PawnsVector.get(z));                             // usunięcie grafiki z okna
                                PawnsVector.remove(z);                                            // usunięcie piona z wektora
                            }
                            catch(ArrayIndexOutOfBoundsException el)
                            {

                            }

                            if(PawnsVector.size() == 1)                                         // Sprawdzenie warunku na wygraną
                            {
                                WinComputerScreen ws = new WinComputerScreen(mainFrame,this); // Wyświtlenie inforamcji o wygranej komputera

                            }

                        }

                    }

                    break;      // Wyjście z pętli przeszukującej możliwość ruchu
                }

            }


            if (!ableToMove) {                                                                 // Sprawdzenie warunku na przegraną
                LoseComputerScreen ls = new LoseComputerScreen(mainFrame,this); // Wyświetlenie informacji o przegranej komputera
            }

            ableToMove = false;         // Wyzerowanie zmiennych

        }

    }

    public void stop() {
        running = false;                // Ustawienie running na false, a co za tym idzie wyłączenie wątku
        resume();                       // Wznowienie wątku jeżeli był on w stanie pauzy aby mógł się zakończyć
    }

    public void pause() {
        paused = true;                  // Zapauzowanie wątku
    }

    public void resume() {

        synchronized (pauseLock) {
            paused = false;             // Odpauzowanie wątku
            pauseLock.notifyAll();      // Pobudzenie wątku
        }
    }

    public void restart()
    {
        pause();                        // Zapauzowanie wątku
        resume();                       // Wznowienie go
    }

    }

