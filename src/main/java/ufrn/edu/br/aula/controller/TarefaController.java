package ufrn.edu.br.aula.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ufrn.edu.br.aula.dominio.Tarefa;
import ufrn.edu.br.aula.persistencia.TarefaDAO;

import java.io.IOException;
import java.text.SimpleDateFormat;

@Controller
public class TarefaController {

    @RequestMapping(method = RequestMethod.GET, value = "/cadastrar")
    public void getTarefas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("Acesse o formulário de cadastro");
    }

    @RequestMapping( method = RequestMethod.POST, value = "/cadastrar")
    public void cadastraTarefa(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var t = new Tarefa();

        response.setContentType("text/HTML");
        var writer = response.getWriter();

        try{
            var texto = request.getParameter("texto");
            var prioridade = Integer.parseInt(request.getParameter("prioridade"));
            t.setTexto(texto);
            t.setPrioridade(prioridade);
            TarefaDAO.cadastrar(t);

            writer.println("<html>" +
                    "<body>"+
                    "<h1> TAREFA CADASTRADA </h1>" +
                    "<h1> Texto: " + t.getTexto() + "</h1>"+
                    "<p> Prioridade: " + t.getPrioridade() + "</p>" +
                    "<p> Data: " + new java.sql.Date(t.getDataCadastro().getTime()) + "</p>"+
                    "<a href='cadastro.html'> VOLTAR </a>"+
                    "</body>"+
                    "</html>"
            );
        }catch (Exception e){
            writer.println("<html>" +
                    "<body>"+
                    "<h1> CAMPOS NÃO PREENCHIDOS </h1>" +
                    "<a href='cadastro.html'> VOLTAR </a>"+
                    "</body>"+
                    "</html>"
            );
        }
    }

    @RequestMapping( value = "/buscar", method = RequestMethod.GET)
    public void buscarTarefas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var writer = response.getWriter();
        writer.println("<html>" +
                "<body>"
        );
        try{
            var id = Integer.parseInt(request.getParameter("id"));
            Tarefa t = TarefaDAO.buscarTarefa(id);
            if(t == null){
                writer.println(
                        "<h1> TAREFA NÃO ENCONTRADA</h1>"+
                        "<a href='busca.html'> VOLTAR </a>"
                );
            }else{
                writer.println(
                        "<h1>" + t.getTexto() + "</h1>"+
                        "<p> ID: " + t.getId() + "</p>" +
                        "<p> Prioridade: " + t.getPrioridade() + "</p>" +
                        "<p> Data: " + /*new java.sql.Date(*/t.getDataCadastro()/*.getTime())*/ + "</p>"+
                        "<a href='busca.html'> VOLTAR </a>"
                );
            }
        }catch (Exception e){
            writer.println(
                    "<h1> CAMPOS NÃO PREENCHIDOS </h1>" +
                    "<a href='busca.html'> VOLTAR </a>"
            );
        }
        writer.println("</body>"+
                "</html>"
        );


    }
    @RequestMapping( method = RequestMethod.GET, value = "/listar")
    public void listarTarefas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/HTML");
        var tarefas = TarefaDAO.listar();
        var writer = response.getWriter();
        writer.println("<html>" +
                "<body>"
        );

        writer.println("<h1> LISTA DE TAREFAS </h1><a href='index.html'> VOLTAR </a>");
        for (var t : tarefas) {
            writer.println("<hr/> <h1>" + t.getTexto() + "</h1>"+
                    "<p var='tarefaId'> ID: " + t.getId() + "</p>" +
                    "<p> Prioridade: " + t.getPrioridade() + "</p>" +
                    "<p> Data: " + t.getDataCadastro() + "</p> "+
                    "<a href='editar?id="+t.getId()+"'>EDITAR</a> </br>"+
                    "<a href='deletar?id="+t.getId()+"'>DELETAR</a>"
                    );
        }
        writer.println("</body>"+
                "</html>"
        );
    }
    @RequestMapping(method = RequestMethod.GET, value = "/editar")
    public void editarTarefa(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var id = Integer.parseInt(request.getParameter("id"));
        var t = TarefaDAO.buscarTarefa(id);
        var writer = response.getWriter();

        writer.println("<html>"+
                "<body>" +
                "<h1> EDITAR TAREFA </h1>" +
                "<form action='atualizar' method='post'>" +
                "<input type='hidden' name='id' value='"+t.getId()+"'>" +
                "<input type='text' name='texto' value='"+ t.getTexto()+"'>" +
                "<input type='number' name='prioridade' value='"+t.getPrioridade()+"'>" +
                "<input type='hidden' name='dataCadastro' value='"+t.getDataCadastro()+"'>" +
                "<button type='submit'>ATUALIZAR</button> <a href='listar'> VOLTAR </a>" +
                "</form>" +
                "</form>" +
                "</html>");
    }
    @RequestMapping(method = RequestMethod.POST, value = "/atualizar")
    public void atualizarTarefa(HttpServletRequest request, HttpServletResponse response) throws Exception {
        var id = Integer.parseInt(request.getParameter("id"));
        var texto = request.getParameter("texto");
        var prioridade = Integer.parseInt(request.getParameter("prioridade"));
        var dataCadastro = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dataCadastro"));

        var t = new Tarefa(texto, prioridade, dataCadastro, id);
        TarefaDAO.editarTarefa(t);

        var writer = response.getWriter();
        writer.println("<html>" +
                "<body>"+
                "<h1> TAREFA ATUALIZADA </h1>" +
                "<a href='listar'> VOLTAR </a>" +
                "</body>"+
                "</html>");
    }
    @RequestMapping(method = RequestMethod.GET, value = "/deletar")
    public void deletarTarefa(HttpServletResponse response, HttpServletRequest request) throws IOException {
        var id = Integer.parseInt(request.getParameter("id"));
        TarefaDAO.excluirTarefa(id);

        var writer = response.getWriter();
        writer.println("<html>" +
                "<body>"+
                "<h1> TAREFA EXCLUIDA </h1>" +
                "<a href='listar'> VOLTAR </a>" +
                "</body>"+
                "</html>");
    }
}





