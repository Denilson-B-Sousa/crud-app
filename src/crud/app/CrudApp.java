package crud.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CrudApp {

    public static void main(String[] args) {

    }

    public static Connection conectar() {
        String driver = "org.postgresql.Driver";
        String usuario = "postgres";
        String senha = "developer";
        String url = "jdbc:postgresql://localhost:5432/exemplo";

        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, usuario, senha);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao conectar!");
            return null;
        }
    }


    public static void criarProduto(String nome, String descricao, double preco) {
        String sql = "INSERT INTO produtos (nome, descricao, preco) VALUES (?, ?, ?)";

        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nome);
            ps.setString(2, descricao);
            ps.setDouble(3, preco);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto inserido!");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Object[]> listarProdutosTabela() {
    List<Object[]> lista = new ArrayList<>();

    String sql = "SELECT * FROM produtos";

    try (Connection con = conectar();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        while (rs.next()) {
            lista.add(new Object[]{
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("descricao"),
                rs.getDouble("preco")
            });
        }

    } catch (Exception ex) {
        ex.printStackTrace();
    }

    return lista;
}


    public static void atualizarProduto(int id, String novoNome, String novaDescricao, double novoPreco) {
        String sql = "UPDATE produtos SET nome = ?, descricao = ?, preco = ? WHERE id = ?";

        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, novoNome);
            ps.setString(2, novaDescricao);
            ps.setDouble(3, novoPreco);
            ps.setInt(4, id);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto atualizado!");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ============================
    // DELETE
    // ============================
    public static void deletarProduto(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";

        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Produto removido!");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
