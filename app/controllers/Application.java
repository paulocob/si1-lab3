package controllers;

import models.Anunciante;
import models.Anuncio;
import models.dao.GenericDAO;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;
import views.html.criaranuncio;
import views.html.index;
import views.html.removido;
import views.html.visitaranuncio;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

import static play.data.Form.form;

public class Application extends Controller {

    private static GenericDAO dao = new GenericDAO();

    @Entity
    @Table(name = "CONTAGEM")
    private static class contadorAnuncios {

        @Id
        @GeneratedValue
        @Column(name = "id")
        private int id;

        @Column(name = "num")
        private static int numAnuncios = 0;

        public void contadorAnuncios() {}

        public int getNumAnuncios() {
            return numAnuncios;
        }

        public void incrementaNumAnuncios() {
            numAnuncios++;
        }
    }

    @OneToOne
    @JoinColumn(name = "controller")
    private static contadorAnuncios numAnuncios = new contadorAnuncios();
    private List<Anuncio> anuncios;

    @Transactional
    public static Result index() {
        return redirect(controllers.routes.Application.verAnuncios());
    }

    public static Result criarAnuncio() {
        return ok(criaranuncio.render("The Good"));
    }

    @Transactional
    public static Result verAnuncios() {
        List<Anuncio> anuncios = dao.findAllByClass(Anuncio.class);

        Collections.sort(anuncios, new Comparator<Anuncio>() {
            public int compare(Anuncio a1, Anuncio a2) {
                return a1.getData().getTime() < a2.getData().getTime() ? 1 : -1;
            }
        });
        return ok(index.render(anuncios, numAnuncios.getNumAnuncios()));
    }

    @Transactional
    public static Result contabilizarFeedback() {
        DynamicForm dynamicForm = form().bindFromRequest();
        contadorAnuncios c;
        if(dynamicForm.get("ok") != null && dynamicForm.get("ok").equals("true")) {
            numAnuncios.incrementaNumAnuncios();
            dao.persist(numAnuncios);
        }
        return redirect(controllers.routes.Application.verAnuncios());
    }


    @Transactional
    public static Result novoAnuncio() {
        DynamicForm dynamicForm = form().bindFromRequest();

        Form<Anunciante> anuncianteForm = form(Anunciante.class);
        Form<Anuncio> anuncioForm = form(Anuncio.class);

        Anunciante anunciante = anuncianteForm.bindFromRequest().get();
        anunciante.setContatos(dynamicForm.get("email"), dynamicForm.get("fb"));
        anunciante.setInstrumentos(Arrays.asList(dynamicForm.get(("instrumentos")).split("\\s*,\\s*")));
        anunciante.setGosta(Arrays.asList(dynamicForm.get(("gosta")).split("\\s*,\\s*")));
        anunciante.setDesgosta(Arrays.asList(dynamicForm.get(("desgosta")).split("\\s*,\\s*")));
        dao.persist(anunciante);
        
        
        Anuncio anuncio = anuncioForm.bindFromRequest().get();
        anuncio.setAnunciante(anunciante);
        anuncio.setData(new Timestamp((new Date()).getTime()));
        dao.persist(anuncio);
        
        
        return redirect(controllers.routes.Application.verAnuncios());
    }

    @Transactional
    public static Result fazerBusca() {
        DynamicForm dynamicForm = form().bindFromRequest();
        String[] chave = dynamicForm.get("busca").split("\\s*,\\s*");

        List<Anuncio> resultadoBusca = new ArrayList<Anuncio>();
        List<Anuncio> anuncios = dao.findAllByClass(Anuncio.class);
        for (Anuncio anuncio : anuncios) {
            for (String c: chave) {
                switch (c) {
                    case "ocasional":
                        if (anuncio.getAnunciante().getOcasional())
                            resultadoBusca.add(anuncio);
                        break;
                    case "banda":
                        if (!anuncio.getAnunciante().getOcasional())
                            resultadoBusca.add(anuncio);
                        break;
                    default:
                        if (anuncio.getDescricao().contains(c) ||
                                anuncio.getAnunciante().getInstrumentos().contains(c) ||
                                anuncio.getAnunciante().getGosta().contains(c))
                            resultadoBusca.add(anuncio);
                        break;
                }
            }
        }
        return ok(index.render(resultadoBusca, numAnuncios.getNumAnuncios()));

    }

    @Transactional
    public static Result visitarAnuncio(String anuncio) {
        String titulo = anuncio.substring(0, anuncio.indexOf("+"));
        List<Anuncio> resultado = dao.findByAttributeName("Anuncio", "titulo", titulo);
        return ok(visitaranuncio.render("", resultado.get(0)));
    }

    @Transactional
    public static Result deletarAnuncio(String titulo) {
        DynamicForm dynamicForm = form().bindFromRequest();
        Anuncio anuncio = getAnuncioByTitulo(titulo);

        if(dynamicForm.get("codigo").equals(anuncio.getCodigo())) {
            dao.remove(anuncio);
            return ok(removido.render("Removido!"));
        }
        return forbidden(visitaranuncio.render("Código incorreto!", anuncio));
    }

    @Transactional
    public static Result responderPergunta(String titulo, int index) {
        DynamicForm dynamicForm = form().bindFromRequest();
        Anuncio anuncio = getAnuncioByTitulo(titulo);
        try {
            anuncio.adicionarResposta(index, dynamicForm.get("resposta"), dynamicForm.get("codigoResposta"));
        } catch (Exception e) {
            return forbidden(visitaranuncio.render("Código incorreto!", anuncio));
        }
        dao.persist(anuncio);
        return ok(visitaranuncio.render("", anuncio));
    }

    @Transactional
    public static Result responderOuApagarPergunta(String titulo, int index) {
        String[] postAction = request().body().asFormUrlEncoded().get("acao");
        if("Responder Pergunta".equals(postAction[0])) {
            return responderPergunta(titulo, index);
        }
        else {
            return apagarPergunta(titulo, index);
        }
    }

    @Transactional
    public static Result apagarPergunta(String titulo, int index) {
        DynamicForm dynamicForm = form().bindFromRequest();
        Anuncio anuncio = getAnuncioByTitulo(titulo);
        try {
            anuncio.apagarPergunta(index, dynamicForm.get("codigoResposta"));
        } catch (Exception e) {
            return forbidden(visitaranuncio.render("Código incorreto!", anuncio));

        }
        dao.persist(anuncio);
        return ok(visitaranuncio.render("", anuncio));

    }

    @Transactional
    public static Result criarPergunta(String titulo) {
        DynamicForm dynamicForm = form().bindFromRequest();
        Anuncio anuncio = getAnuncioByTitulo(titulo);
        anuncio.adicionarPergunta(dynamicForm.get("pergunta"));
        dao.persist(anuncio);
        return ok(visitaranuncio.render("", anuncio));
    }

    @Transactional
    public static Anuncio getAnuncioByTitulo(String titulo) {
        List<Anuncio> anuncio = dao.findByAttributeName("Anuncio", "titulo", titulo);
        return anuncio.get(0);
    }
}
