package ufrn.edu.br.aula.persistencia;

import com.fasterxml.jackson.databind.cfg.CoercionAction;
import ufrn.edu.br.aula.dominio.Tarefa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {
    public static void cadastrar(Tarefa t){
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = Conexao.getConexao();
            ps = con.prepareStatement("insert into tarefas (texto, prioridade, datacadastro) values (?,?,?)");
            ps.setString(1, t.getTexto());
            ps.setInt(2,t.getPrioridade());
            ps.setDate(3, new java.sql.Date(t.getDataCadastro().getTime()));
            ps.executeUpdate();
            con.close();
            System.out.println("\nTarefa cadastrada!");
        }catch (Exception e){
            System.out.println("\nErro: " + e);
        }
    }

    public static List<Tarefa> listar(){
        Connection con = null;
        ResultSet rs = null;
        List<Tarefa> tarefas = new ArrayList<Tarefa>();
        try {
            con = Conexao.getConexao();
            rs = con.createStatement().executeQuery("select * from tarefas");
            while(rs.next()){
                tarefas.add(new Tarefa(rs.getString("texto"), rs.getInt("prioridade"), rs.getDate("dataCadastro"), rs.getInt("id")));
            }
            con.close();
        }catch (Exception e){
            System.out.println("\nErro: " + e);
        }
        return tarefas;
    }

    public static Tarefa buscarTarefa(Integer id){
        Tarefa t = null;
        Connection con = null;
        ResultSet rs = null;
        try{
            con = Conexao.getConexao();
            rs = con.createStatement().executeQuery("select * from tarefas where id = "+id);
            while(rs.next()){
                t = new Tarefa(rs.getString("texto"), rs.getInt("prioridade"), rs.getDate("dataCadastro"), rs.getInt("id"));
            }
            con.close();
        }catch (Exception e){
            System.out.println("Erro: " + e);
        }
        return t;
    }

    public static void editarTarefa(Tarefa tarefa){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexao.getConexao();
            ps = con.prepareStatement("update tarefas set texto=?, prioridade =?, datacadastro=? where id = ?");
            ps.setString(1, tarefa.getTexto());
            ps.setInt(2,tarefa.getPrioridade());
            ps.setDate(3,new java.sql.Date(tarefa.getDataCadastro().getTime()));
            ps.setInt(4, tarefa.getId());
            System.out.println("ID: "+tarefa.getId()+" - editado");
            ps.executeUpdate();
            ps.close();
        }catch (Exception e){
            System.out.println("Erro: " + e);
        }
    }

    public static void excluirTarefa(Integer id){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexao.getConexao();
            ps = con.prepareStatement("delete from tarefas where id = ?");
            ps.setInt(1, id);
            System.out.println("ID: "+id+" - deletado");
            ps.executeUpdate();
            ps.close();
        }catch (Exception e){
            System.out.println("Erro: " + e);
        }
    }
}
