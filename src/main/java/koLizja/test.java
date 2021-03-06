package koLizja;

import koLizja.encje.Ankieta;
import koLizja.encje.Instruktor;
import koLizja.encje.Kurs;
import koLizja.encje.Kursant;
import koLizja.generatory.*;

import java.util.ArrayList;
import java.util.List;

public class test {

    static int iloscKursantow = 10;
    static int iloscInstruktorow = 6;
    static int iloscUczen = 10;

    public static void main(String[] args) {

        GeneratorKursantow generatorKursantow = new GeneratorKursantow();
        GeneratorInstruktorow generatorInstruktorow = new GeneratorInstruktorow();
        GeneratorKursow generatorKursow = new GeneratorKursow();

        List<Kursant> kursanci = new ArrayList<Kursant>();
        List<Instruktor> instruktorzy = new ArrayList<Instruktor>();
        List<Kurs> kursy;
        List<Ankieta> ankiety;

        long start = System.currentTimeMillis();

        int i = 0;
        while(i < iloscKursantow) {
           kursanci.add(generatorKursantow.create(i));
            i+=1;
        }

        instruktorzy = generatorInstruktorow.createEveryType();

//        int j = 0;
//        while(j < iloscInstruktorow) {
//            instruktorzy.add(generatorInstruktorow.createBulk(j));
//            j++;
//        }
        kursy = generatorKursow.createEveryType();

//        OutputFile outputFile = new OutputFile();
        OutputFile.createBulk(kursy,"kursy");
        OutputFile.createBulk(instruktorzy,"instruktorzy");
        OutputFile.createBulk(kursanci,"kursanci");

        System.out.println("Czas generowania:\n"
                + iloscInstruktorow + " instruktorow\n"
                + iloscKursantow + " kursantow\n"
                + (System.currentTimeMillis()-start)/1000+ " s");
        System.out.println("Generowanie uczenia");
        start = System.currentTimeMillis();

        GeneratorUczenie generatorUczenie = new GeneratorUczenie(kursy, kursanci, instruktorzy);
        generatorUczenie.create(iloscUczen);

        System.out.println("Stworzone uczenia: " + generatorUczenie.getStworzoneUczenia());
        OutputFile.createBulk(generatorUczenie.getUczenie(), "uczenia");
        System.out.println("Czas generowania uczenia: " + (System.currentTimeMillis()-start)/1000 + " s");

        System.out.println("Generowanie ankiet");
        start = System.currentTimeMillis();
        GeneratorWynikowAnkiet generatorWynikowAnkiet =
                new GeneratorWynikowAnkiet(instruktorzy,kursy,kursanci,generatorUczenie.getUczenie());
        generatorWynikowAnkiet.create();
        ankiety = generatorWynikowAnkiet.getAnkiety();
        OutputFile.createCsv(ankiety,"ankiety");
        System.out.println("Czas generowania ankiet: " + (System.currentTimeMillis()-start)/1000 + " s");
    }


}
