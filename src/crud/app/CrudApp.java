package crud.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CrudApp {

    public static void main(String[] args) {

    }

    public static Connection conectar() {
        String driver = "org.postgresql.Driver";
        String usuario = "denilson";
        String senha = "123456";
        String url = "jdbc:postgresql://192.168.88.128/exemplo";

        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, usuario, senha);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao conectar!");
            return null;
        }
    }


    public static void criarProduto(String descricao, double preco) {
        String sql = "INSERT INTO produtos (descricao, preco) VALUES (?, ?)";

        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, descricao);
            ps.setDouble(2, preco);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto inserido!");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public List<Object[]> pesquisarProdutoNaTabela(String itemPesquisa) {
        
        String sql = "SELECT * FROM produtos WHERE produtos.descricao LIKE ?";
        
        List<Object[]> lista = new ArrayList<>();

        try (Connection con = conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, "%" + itemPesquisa + "%");

        try (ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("codigo"),
                    rs.getString("descricao"),
                    rs.getDouble("preco")
                });
            }
        }

    } catch (Exception ex) {
        ex.printStackTrace();
    }
        
        return lista;
    }
    
    
    public List<Object[]> listarProdutosTabela() {
    List<Object[]> lista = new ArrayList<>();

    String sql = "SELECT * FROM produtos";

    try (Connection con = conectar();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        while (rs.next()) {
            lista.add(new Object[]{
                rs.getInt("codigo"),
                rs.getString("descricao"),
                rs.getDouble("preco")
            });
        }

    } catch (Exception ex) {
        ex.printStackTrace();
    }

    return lista;
}


    public static void atualizarProduto(int codigo, String novaDescricao, double novoPreco) {
        String sql = "UPDATE produtos SET descricao = ?, preco = ? WHERE codigo = ?";

        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, novaDescricao);
            ps.setDouble(2, novoPreco);
            ps.setInt(3, codigo);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto atualizado!");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ============================
    // DELETE
    // ============================
    public static void deletarProduto(int codigo) {
        String sql = "DELETE FROM produtos WHERE codigo = ?";

        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codigo);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Produto removido!");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
