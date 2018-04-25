package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Estudante;
import model.Emprestimo;
import model.Livro;
import util.ConnectionJDBC;

public class EmprestimoDAO {

    Connection connection;

    public EmprestimoDAO() throws Exception {
        // Obtem uma conexão
        connection = ConnectionJDBC.getConnection();
    }

    public void save(Emprestimo emprestimo) throws Exception {
        String SQL = "INSERT INTO EMPRESTIMO(LIVRO_ID, ESTUDANTE_ID, DATA_RETIRADA, DATA_DEVOLUCAO, DATA_ENTREGA, STATUS)"
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            p.setInt(1, emprestimo.getLivro_id().getLivro_id());
            p.setInt(2, emprestimo.getEstudante_id().getEstudante_id());
            p.setDate(3, new Date(emprestimo.getData_retirada().getTime()));
            p.setDate(4, new Date(emprestimo.getData_devolucao().getTime()));
            p.setDate(5, new Date(emprestimo.getData_entrega().getTime()));
            p.setString(6, emprestimo.getStatus());
            p.execute();
            p.close();
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
    }

    public void update(Emprestimo emprestimo) throws Exception {
        String SQL = "UPDATE EMPRESTIMO SET LIVRO_ID=?,ESTUDANTE_ID=?,DATA_RETIRADA=?,DATA_DEVOLUCAO=?,DATA_ENTREGA=?,STATUS=?"
                   + " WHERE EMPRESTIMO_ID=?";
        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            p.setInt(1, emprestimo.getLivro_id().getLivro_id());
            p.setInt(2, emprestimo.getEstudante_id().getEstudante_id());
            p.setDate(3, new Date(emprestimo.getData_retirada().getTime()));
            p.setDate(4, new Date(emprestimo.getData_devolucao().getTime()));
            p.setDate(5, new Date(emprestimo.getData_entrega().getTime()));
            p.setString(6, emprestimo.getStatus());
            p.setInt(7, emprestimo.getEmprestimo_id());
            p.execute();
            p.close();
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
    }

    public void delete(Emprestimo emprestimo) throws Exception {
        String SQL = "DELETE FROM EMPRESTIMO WHERE EMPRESTIMO_ID=?";
        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            p.setInt(1, emprestimo.getEmprestimo_id());
            p.execute();
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
    }
    
    public Emprestimo findById(int id) throws Exception{
        Emprestimo objeto = new Emprestimo();
        String SQL = "SELECT E.*, LIVRO.LIVRO_ID, LIVRO.TITULO, ESTUDANTE.ESTUDANTE_ID, ESTUDANTE.NOME FROM EMPRESTIMO E "
                   + "INNER JOIN LIVRO ON E.LIVRO_ID = LIVRO.LIVRO_ID"
                   + "INNER JOIN ESTUDANTE ON E.ESTUDANTE_ID = ESTUDANTE.ESTUDANTE_ID"
                   + "WHERE E.EMPRESTIMO_ID = ?";
        
        try{
            PreparedStatement p = connection.prepareStatement(SQL);
            p.setInt(1, id);
            //Executa a SQL e mantem os valores no ResultSet rs
            ResultSet rs = p.executeQuery();
            //Navega pelos registros no rs
            while (rs.next()) {
                //Instancia a classe e informa os valores do BD
                objeto = new Emprestimo();
                objeto.setEmprestimo_id(rs.getInt("emprestimo_id"));
                SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                objeto.setData_retirada(rs.getDate("data_retirada"));
                objeto.setData_devolucao(rs.getDate("data_devolucao"));
                objeto.setData_entrega(rs.getDate("data_entrega"));
                objeto.setStatus(rs.getString("status").substring(0, 1));
                
                Estudante estudante = new Estudante();
                estudante.setEstudante_id(rs.getInt("estudante_id"));
                estudante.setNome(rs.getString("nome"));
 
                Livro livro = new Livro();
                livro.setLivro_id(rs.getInt("livro_id"));
                livro.setTitulo(rs.getString("titulo"));
                
                //Inclui na lista
                objeto.setEstudante_id(estudante);
                objeto.setLivro_id(livro);
            }
            rs.close();
            p.close();
        } catch (SQLException ex) {
           throw new Exception(ex); 
        }
        return objeto;
    }

    public List<Emprestimo> findAll() throws Exception {
        // Lista para manter os valores do ResultSet
        List<Emprestimo> list = new ArrayList<>();
        Emprestimo objeto;
        String SQL = "SELECT E*, LIVRO.LIVRO_ID, LIVRO.TITULO, ESTUDANTE.ESTUDANTE_ID, ESTUDANTE.NOME FROM EMPRESTIMOS E"
                   + "INNER JOIN LIVRO ON E.LIVRO_ID = LIVRO.LIVRO_ID"
                   + "INNER JOIN ESTUDANTE ON E.ESTUDANTE_ID = ESTUDANTE.ESTUDANTE_ID"
                   + "ORDER BY E.EMPRESTIMO_ID";
        try {
            // Prepara a SQL
            PreparedStatement p = connection.prepareStatement(SQL);
            // Executa a SQL e mantem os valores no ResultSet rs
            ResultSet rs = p.executeQuery();
            // Navega pelos registros no rs
            while (rs.next()) {
                // Instancia a classe e informa os valores do BD
                objeto = new Emprestimo();
                objeto.setEmprestimo_id(rs.getInt("emprestimo_id"));
                SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                objeto.setData_retirada(rs.getDate("data_retirada"));
                objeto.setData_devolucao(rs.getDate("data_devolucao"));
                objeto.setData_entrega(rs.getDate("data_entrega"));
                objeto.setStatus(rs.getString("status").substring(0, 1));
                
                Estudante estudante = new Estudante();
                estudante.setEstudante_id(rs.getInt("estudante_id"));
                estudante.setNome(rs.getString("nome"));
 
                Livro livro = new Livro();
                livro.setLivro_id(rs.getInt("livro_id"));
                livro.setTitulo(rs.getString("titulo"));
                
                //Inclui na lista
                objeto.setEstudante_id(estudante);
                objeto.setLivro_id(livro);
                
                list.add(objeto);
            }
            rs.close();
            p.close();
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
        // Retorna a lista
        return list;
    }
}