package com.company;

import javax.swing.*;

import static com.company.SettingsPanel.*;

public class AbleToMoveThread implements Runnable
{
    Runnable gameTimer;                                             // Zmienna przechowująca klasę licznika czasu gry
    JFrame window;                                                  // Zmienna przechowująca okno główne gry

    boolean ableToMove = false;                                     // Zmienna mówiąca o tym czy jest możliwość dalszego ruchu

    AbleToMoveThread(JFrame w, Runnable t)
    {
        gameTimer = t;          // Przypisanie wątku timer'a do zmiennej
        window = w;             // Przypisanie okna gry do zmiennej
    }

    @Override
    public void run()
    {


        for(int i = 0; i < nMapSize; i++) {                             // Przeszukiwanie tablicy pionów w celu znalezienia ruchu

            for (int j = 0; j < nMapSize; j++) {

                try
                {
                    if ((PawnMap[i][j] != -1 && PawnMap[i][j] != 0) &&              // Sprawdzenie czy na aktualnej pozycji znajduje się pion
                            (PawnMap[i][j + 1] != -1 && PawnMap[i][j + 1] != 0) &&  // Czy na pozycji na prawo od niego także jest pion
                            (PawnMap[i][j + 2] == 0) && Map[i][j + 2] != 2)         // Oraz czy na pozycji następnej jest puste pole i nie jest ono poza planszą
                    {
                        ableToMove = true;                                          // Jeżeli tak jest to ustalamy, że ruch jest mozliwy do wykonania
                        break;                                                      // Przerywamy sprawdzanie ponieważ zależy nam tylko na informacji o jakimkolwiek ruchu
                    }
                }
                catch(ArrayIndexOutOfBoundsException el)
                {

                }

                try
                {
                    if ((PawnMap[i][j] != -1 && PawnMap[i][j] != 0) &&                  // Jak powyżej tylko sprawdzamy pionki na lewo od atualnego
                            (PawnMap[i][j - 1] != -1 && PawnMap[i][j - 1] != 0) &&
                            (PawnMap[i][j - 2] == 0) &&  Map[i][j - 2] != 2)
                    {
                        ableToMove = true;
                        break;
                    }
                }
                catch(ArrayIndexOutOfBoundsException el)
                {

                }

                try {
                    if ((PawnMap[i][j] != -1 && PawnMap[i][j] != 0) &&                  // Jak powyżej tylko sprawdzamy pionki do góry
                            (PawnMap[i + 1][j] != -1 && PawnMap[i + 1][j] != 0) &&
                            (PawnMap[i + 2][j] == 0) && Map[i + 2][j] != 2)
                    {
                        ableToMove = true;
                        break;
                    }
                }
                catch(ArrayIndexOutOfBoundsException el)
                {

                }


                try {
                    if ((PawnMap[i][j] != -1 && PawnMap[i][j] != 0) &&                  // Jak powyżej tylko sprawdzamy pionki na dół
                            (PawnMap[i - 1][j] != -1 && PawnMap[i - 1][j] != 0) &&
                            (PawnMap[i - 2][j] == 0) && Map[i - 2][j] != 2)
                    {
                        ableToMove = true;
                        break;
                    }
                }
                catch(ArrayIndexOutOfBoundsException el)
                {

                }


                }

            if(ableToMove)                                                             // Jeżeli znaleźliśmy ruch to kończymy przeszukiwanie tablicy
                break;

            }


        if(!ableToMove) {                                                               // Jeżeli nie znaleźliśmy możliwościu ruchu to koniec gry
            ((GameTimerThread)gameTimer).pause();                                       // Zatrzymujemy wątek czasu
            LoseScreen ls = new LoseScreen(window);                                     // Uruchamiamy okno dialogowe informujące o zakończeniu gry
        }

    }
}
