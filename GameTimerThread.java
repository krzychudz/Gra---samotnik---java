package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

import static com.company.SettingsPanel.timeValue;                          // Import zmiennej statycznych odpowiedającej za limit czasu



public class GameTimerThread implements ActionListener, Runnable
{

    Timer SimpleTimer = new Timer(1000,this);                 // Utowrzenie timer'a który wysyła sygnal co 1 sekundę
    JLabel Info = new JLabel("Czas gry: ");                            // Label z napisaem informującym

    SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");     // Formater daty

    int time = 0;                                                           // Zmienna czasu zwiękasza o 1000 co każdą sekundę

    private final Object pauseLock = new Object();                          // Uworzenie obiektu na którym będzie synchronizowany wątek


    private volatile boolean running = true;                                // Zmienna która określa czy wątek jest uruchomiony
    private volatile boolean paused = false;                                // Zmienna która określa czy wątek jest zapauzowany

    JFrame parent;

    GameTimerThread(JPanel panel, JFrame parent)
    {
        Info.setFont(new Font("Summer in December", Info.getFont().getStyle(), 70));                // Ustawienie fontu
        Info.setText("Czas gry: " + formatter.format(new Date(0)));                                              // Czas początkowy
        panel.add(Info);                                                                                         // Dodanie Labelu do panelu// Dodanie Labelu do panelu

        this.parent = parent;

        running = true;                                                                                          // Inicjalizacja
        paused = false;                                                                                          // Inicjalizacja
    }

    int getActualTime()
    {
        return time;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        time = time + 1000;                                                     // zwiększenie czasu o 1000 ms
        Info.setText("Czas gry: " + formatter.format(new Date(time)));          // ustawiaenie aktualnego czasu, odpowiednio sformatowanego, na Labelu

        if(time == timeValue)                                                   // Jeżeli czas gry jest równy limitowi
        {
            stop();                                                             // Zatrzymujemy licznik
            TimeOutScreen timeOut = new TimeOutScreen(parent);                  // Wyświetlamy okno informujące
        }

    }



    @Override
    public void run() {

        SimpleTimer.start();                                // Uruchomienie Timera

        while(running)                                      // Pętla główna wątku, wątek działa dopóki zmienan running == true
        {
            synchronized (pauseLock)                        // Sekcja krytyczna
            {
                if(!running)                                // Sprawdzenie warunku na działanie wątku
                    break;
                if(paused)
                {
                    try {
                        SimpleTimer.stop();                 // Jeśli wątek zapauzowany to zatrzyamnie timera
                        pauseLock.wait();                   // i zatrzymanie wątku
                    } catch (InterruptedException ex) {
                        break;
                    }
                    if (!running) {                         // Sprawdzenie zmiany runnig podczas pauzy
                        break;
                    }
                }

            }
        }

    }

    public void stop() {
        running = false;                // Ustawienie running na false, a co za tym idzie wyłączenie wątku
        resume();                       //
    }

    public void pause() {
        paused = true;                  // Zapauzowanie wątku
    }

    public void resume() {

        synchronized (pauseLock) {
            paused = false;             // Odpauzowanie wątku
            SimpleTimer.start();        // Uruchomienie timera
            pauseLock.notifyAll();      // Uruchomienie wątku
        }
    }

    public void restart()
    {
        pause();                       // Pauzyujemy wątek
        SimpleTimer.stop();            // Wyłączamy timer
        time = 0;                      // Resetujemy czas
        SimpleTimer.start();           // Uruchamiamy timer
        resume();                     // Utuchamiamy wątek
    }


}
