package koLizja.generatory;

import koLizja.Kategoria;
import koLizja.Uprawnienia;
import koLizja.encje.Instruktor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


//trzeba napisac konstruktor z mozliwoscia wybrania posiadanych uprawnien (nasze T/P)

//instruktorzy maja od 22 do 38 lat
//uprawniena zdobywaja w wieku przynajmniej 21 lat + rand(5 lat)
//zatrudnieni w OSK najpóźniej
//max 15 lat stazu

public class GeneratorInstruktorow extends GeneratorAbstract{

    private Instruktor create(){

        Instruktor instruktor = new Instruktor();

        instruktor.setImie(faker.name().firstName());
        instruktor.setNazwisko(faker.name().lastName());
        instruktor.setKategorie(Kategoria.values()[random.nextInt(3)]);
        Date dataUrodzenia = faker.date().between(NAJSTARSZY_INSTR, NAJMLODSZY_INSTR);
        Date dataPelnoletnosci = (new Date(
                dataUrodzenia.getYear()+21,
                dataUrodzenia.getMonth(),
                dataUrodzenia.getDay()));
        Date dataUprawnien = faker.date().future(CZAS_ZDOBYCIA_UPRAWNIEN, TimeUnit.DAYS, dataPelnoletnosci);
        Date dataZatrudnienia = faker.date().future(CZAS_ZDOBYCIA_ZATRUDNIENIA,TimeUnit.DAYS,dataUprawnien);


        instruktor.setDataUrodzenia(df.format(dataUrodzenia));
        instruktor.setDataUprawnien(df.format(dataUprawnien));
        instruktor.setDataZatrudnienia(df.format(dataZatrudnienia));
        instruktor.setNumTel(faker.phoneNumber().cellPhone());
        instruktor.setAdres(faker.address().city()
                + " " + faker.address().streetAddress());

        return instruktor;
    }

    public Instruktor create (int id) {

        Instruktor instruktor = create();
        instruktor.setId(id);
        instruktor.setUprawnienia(Uprawnienia.values()[random.nextInt(2)]);

        return instruktor;
    }

    public Instruktor create (int id, Kategoria kat, Uprawnienia upr) {

        Instruktor instruktor = create();
        instruktor.setId(id);
        instruktor.setUprawnienia(upr);
        instruktor.setKategorie(kat);

        return instruktor;
    }

    public List<Instruktor> createEveryType() {
        List<Instruktor> instruktorzy = new ArrayList<>();
        int i = 0;
        for(Kategoria kat : Kategoria.values()) {
            for(Uprawnienia upr : Uprawnienia.values()) {
                instruktorzy.add(create(i,kat,upr));
                i++;
            }
        }
        return instruktorzy;

    }
}
