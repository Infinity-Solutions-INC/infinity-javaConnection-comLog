package school.sptech;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

public class ConexaoBD {
    private final DataSource dataSource;
    private String url = "jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    private String username = "root";
    private String passwd = "010652";

    LogSistema log = new LogSistema();

    public ConexaoBD() {
        criarBancoDeDados();
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/infinity_solutions?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(passwd);
        this.dataSource = basicDataSource;
    }

    public void criarBancoDeDados() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver"); // Carregar o driver explicitamente
            try (Connection con = DriverManager.getConnection(url, username, passwd);
                 Statement statement = con.createStatement()) {
                String sql = "CREATE DATABASE IF NOT EXISTS infinity_solutions;";
                statement.executeUpdate(sql);
            }

        } catch (SQLException | ClassNotFoundException e) {
            log.mandarMensagemParaLog("Erro ao obter arquivo do bucket: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public JdbcTemplate getConnection() {
        return new JdbcTemplate(dataSource);
    }
}
