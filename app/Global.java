
import models.Anunciante;
import models.Anuncio;
import play.*;

import models.dao.GenericDAO;
import play.db.jpa.JPA;

import java.sql.Timestamp;
import java.util.Date;


public class Global extends GlobalSettings {

    private static GenericDAO DAO = new GenericDAO();

    @Override
    public void onStart(Application app) {
        Logger.info("Aplicação inicializada...");

        JPA.withTransaction(new play.libs.F.Callback0() {
            @Override
            public void invoke() throws Throwable {
                for (int i = 1; i < 26; i++) {

                    Anunciante anunciante = new Anunciante("João Pessoa", "Intermares", new String[]{"sanfona"}, new String[]{"forró", "xaxado"}
                                                            , new String[]{"funk"}, "nome" + i + "@ccc.ufcg.edu.com.br", "http://facebook.com/" + i, (i % 2 == 0) ? true: false);

                    DAO.persist(anunciante);
                    Anuncio anuncio = new Anuncio(anunciante, "123", new Timestamp((new Date()).getTime()), "Simbora fazer um forró massa!", "Vamos fazer um forró? " + i);


                    DAO.persist(anuncio);
                    DAO.flush();
                }
            }
        });
    }
}