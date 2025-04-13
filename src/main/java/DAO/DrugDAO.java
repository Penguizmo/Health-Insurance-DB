package DAO;

import Models.Drug;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DrugDAO {

    public void addDrug(Drug drug) throws SQLException {
        String sql = "INSERT INTO Drug (drugid, drugname, sideeffects, benefits) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, drug.getDrugId());
            stmt.setString(2, drug.getDrugName());
            stmt.setString(3, drug.getSideEffects());
            stmt.setString(4, drug.getBenefits());
            stmt.executeUpdate();
        }
    }

    public List<Drug> getAllDrugs() throws SQLException {
        List<Drug> drugs = new ArrayList<>();
        String sql = "SELECT * FROM Drug";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                drugs.add(new Drug(
                        rs.getInt("drugid"),
                        rs.getString("drugname"),
                        rs.getString("sideeffects"),
                        rs.getString("benefits")));
            }
        }
        return drugs;
    }

    public Drug getDrugById(int drugId) throws SQLException {
        String sql = "SELECT * FROM Drug WHERE drugid = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, drugId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Drug(
                            rs.getInt("drugid"),
                            rs.getString("drugname"),
                            rs.getString("sideeffects"),
                            rs.getString("benefits"));
                }
            }
        }
        return null;
    }

    public void updateDrug(Drug drug) throws SQLException {
        String sql = "UPDATE Drug SET drugname = ?, sideeffects = ?, benefits = ? WHERE drugid = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, drug.getDrugName());
            stmt.setString(2, drug.getSideEffects());
            stmt.setString(3, drug.getBenefits());
            stmt.setInt(4, drug.getDrugId());
            stmt.executeUpdate();
        }
    }

    public void deleteDrug(int drugId) throws SQLException {
        String sql = "DELETE FROM Drug WHERE drugid = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, drugId);
            stmt.executeUpdate();
        }
    }

    private List<Drug> getDrugsByColumn(String column, String value) throws SQLException {
        List<Drug> drugs = new ArrayList<>();
        String sql = "SELECT * FROM Drug WHERE " + column + " LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + value + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    drugs.add(new Drug(
                            rs.getInt("drugid"),
                            rs.getString("drugname"),
                            rs.getString("sideeffects"),
                            rs.getString("benefits")));
                }
            }
        }
        return drugs;
    }

    public List<Drug> getDrugsByName(String drugName) throws SQLException {
        return getDrugsByColumn("drugname", drugName);
    }

    public List<Drug> getDrugsBySideEffects(String sideEffects) throws SQLException {
        return getDrugsByColumn("sideeffects", sideEffects);
    }

    public List<Drug> searchDrugs(String searchText) throws SQLException {
        List<Drug> drugs = new ArrayList<>();
        String sql = "SELECT * FROM Drug WHERE drugname LIKE ? OR sideeffects LIKE ? OR benefits LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String searchPattern = "%" + searchText + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    drugs.add(new Drug(
                            rs.getInt("drugid"),
                            rs.getString("drugname"),
                            rs.getString("sideeffects"),
                            rs.getString("benefits")));
                }
            }
        }
        return drugs;
    }
}