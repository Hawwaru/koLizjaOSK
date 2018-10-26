package koLizja;

import koLizja.encje.Instruktor;
import koLizja.encje.Kurs;
import koLizja.encje.Kursant;
import koLizja.encje.Uczenie;
import koLizja.generatory.GeneratorInstruktorow;
import koLizja.generatory.GeneratorKursantow;
import koLizja.generatory.GeneratorKursow;
import koLizja.generatory.GeneratorUczenie;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class test {

    static int iloscKursantow = 300000;
    static int iloscInstruktorow = 1000;
    static int iloscUczen = 1000000;



    public static void main(String[] args) {

        GeneratorKursantow generatorKursantow = new GeneratorKursantow();
        GeneratorInstruktorow generatorInstruktorow = new GeneratorInstruktorow();
        GeneratorKursow generatorKursow = new GeneratorKursow();

        List<Kursant> kursanci = new ArrayList<Kursant>();
        List<Instruktor> instruktorzy = new ArrayList<Instruktor>();
        List<Kurs> kursy;
        List<Uczenie> uczenie = new ArrayList<>();

        long start = System.currentTimeMillis();

        int i = 0;
        while(i < iloscKursantow) {
           kursanci.add(generatorKursantow.create(i));
            i+=1;
        }
        int j = 0;
        while(j < iloscInstruktorow) {
            instruktorzy.add(generatorInstruktorow.create(j));
            j++;
        }
        kursy = generatorKursow.createEveryType();

        OutputFile outputFile = new OutputFile();
        outputFile.create(kursy,"lista kursow");
        outputFile.create(instruktorzy,"lista instuktorów");
        outputFile.create(kursanci,"lista kursantów");

        System.out.println("Czas generowania:\n"
                + iloscInstruktorow + " instruktorow\n"
                + iloscKursantow + " kursantow\n"
                + (System.currentTimeMillis()-start)/1000+ " s");
        System.out.println("Generowanie uczenia");
        start = System.currentTimeMillis();

        GeneratorUczenie generatorUczenie = new GeneratorUczenie(kursy, kursanci, instruktorzy);
        generatorUczenie.create(iloscUczen);
        outputFile.create(generatorUczenie.getUczenie(), "lista uczenie");

        System.out.println("Czas generowania uczenia: " + (System.currentTimeMillis()-start)/1000 + " s");
    }





}
