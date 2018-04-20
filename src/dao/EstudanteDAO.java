package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Estudante;
import util.ConnectionJDBC;

public class EstudanteDAO {

    Connection connection;

    public EstudanteDAO() throws Exception {
        // Obtem uma conexão
        connection = ConnectionJDBC.getConnection();
    }

    public void save(Estudante estudante) throws Exception {
        String SQL = "INSERT INTO ESTUDANTE VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            p.setInt(1, estudante.getEstudante_id());
            p.setString(2, estudante.getNome());
            p.setString(3, estudante.getCurso());
            p.setTimestamp (4, estudante.getData_matricula());
            p.setString(5, estudante.getStatus());
            p.execute();
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
    }

    public void update(Estudante estudante) throws Exception {
        String SQL = "UPDATE ESTUDANTE SET NOME=?,CURSO=?,DATA_MATRICULA=?,STATUS=? WHERE ESTUDANTE_ID=?";
        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            p.setString(1, estudante.getNome());
            p.setString(2, estudante.getCurso());
            p.setTimestamp (3, estudante.getData_matricula());
            p.setString(4, estudante.getStatus());
            p.setInt(5, estudante.getEstudante_id());
            p.execute();
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
    }

    public void delete(Estudante estudante) throws Exception {
        String SQL = "DELETE FROM ESTUDANTE WHERE ESTUDANTE_ID=?";
        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            p.setInt(1, estudante.getEstudante_id());
            p.execute();
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
    }

    public Estudante findById(int id) {
        return new Estudante();
    }

    public List<Estudante> findAll() throws Exception {
        // Lista para manter os valores do ResultSet
        List<Estudante> list = new ArrayList<>();
        Estudante objeto;
        String SQL = "SELECT * FROM ESTUDANTE";
        try {
            // Prepara a SQL
            PreparedStatement p = connection.prepareStatement(SQL);
            // Executa a SQL e mantem os valores no ResultSet rs
            ResultSet rs = p.executeQuery();
            // Navega pelos registros no rs
            while (rs.next()) {
                // Instancia a classe e informa os valores do BD
                objeto = new Estudante();
                objeto.setEstudante_id(rs.getInt("estudante_id"));
                objeto.setNome(rs.getString("nome"));
                objeto.setCurso(rs.getString("curso"));
                objeto.setData_matricula(rs.getTimestamp("data_matricula"));
                objeto.setStatus(rs.getString("status"));
                // Inclui na lista
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