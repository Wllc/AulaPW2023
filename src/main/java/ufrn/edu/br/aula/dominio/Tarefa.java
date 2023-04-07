package ufrn.edu.br.aula.dominio;

import java.util.Date;

public class Tarefa {
    private Integer id;
    private String texto;
    private Integer prioridade;
    private final Date dataCadastro;

    public Tarefa(){
        this.dataCadastro = new Date();
    }

    public Tarefa(String texto, Integer prioridade, Date dataCadastro, Integer id){
        this.texto = texto;
        this.prioridade = prioridade;
        this.dataCadastro = dataCadastro;
        this.id = id;
    }
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Integer getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Integer prioridade) {
        this.prioridade = prioridade;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public Integer getId() {
        return id;
    }
}
