package ufrn.edu.br.aula.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static Connection getConexao() throws SQLException {
        String localhost = System.getenv("DATABASE_HOST");
        String port = System.getenv("DATABASE_PORT");
        String dbName = System.getenv("DATABASE_NAME");
        String usuario = System.getenv("DATABASE_USERNAME");
        String senha = System.getenv("DATABASE_PASSWORD");

        String caminho = "jdbc:postgresql://"+localhost+":"+port+"/"+dbName;
        return DriverManager.getConnection(caminho, usuario, senha);
    }
}
