package com.codeup.libronova.dao.implement;

import com.codeup.libronova.dao.ParnertDAO;
import com.codeup.libronova.db.ConnectionDB;
import com.codeup.libronova.domain.Partner;
import com.codeup.libronova.exception.PartnerDAOException;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartnerDAOImpl implements ParnertDAO {
    @Override
    public void addNewPartner(Partner partner) {
        String sql = "INSERT INTO partner (code_partner, fullname, phonenumber, statu_partner) VALUES (?, ?, ?, ?)";
        try(Connection conn = ConnectionDB.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, partner.getCode_partner());
            ps.setString(2, partner.getFullname());
            ps.setString(3, partner.getPhonenumber());
            ps.setBoolean(4, partner.isStatu_partner());

            int fila = ps.executeUpdate();
            if(fila > 0){
                System.out.println("The partner has been added correctly " + partner.getFullname());
            }
        } catch (SQLException e) {
            throw  new PartnerDAOException("Check the values recorded in the database, try again." + e.getMessage() + JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public Partner findById(int id) {
        String sql = "SELECT * FROM partner WHERE id = ?";
        Partner partner = null;
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, id);


            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String code = rs.getString("code_partner");
                    String fullname = rs.getString("fullname");
                    String phonenumber = rs.getString("phonenumber");
                    boolean status = rs.getBoolean("statu_partner");

                    partner = new Partner(code, fullname, phonenumber, status);
                    partner.setId(rs.getInt("id"));

                    return partner;
                }
            }
        } catch (SQLException e) {
            throw  new PartnerDAOException("Check the values recorded in the database, try again." + e.getMessage() + JOptionPane.WARNING_MESSAGE);
        }
        return null;
    }

    @Override
    public List<Partner> listAllPartner() {
        String sql = "SELECT * FROM partner";
        List<Partner> array = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String code = rs.getString("code_partner");
                String fullname = rs.getString("fullname");
                String phonenumber = rs.getString("phonenumber");
                boolean statu = rs.getBoolean("statu_partner");

                Partner partner = new Partner(code, fullname, phonenumber, statu);
                partner.setId(id);
                array.add(partner);
            }

        } catch (SQLException e) {
            throw  new PartnerDAOException("Check the values recorded in the database, try again." + e.getMessage() + JOptionPane.WARNING_MESSAGE);
        }
        return array;
    }

    @Override
    public boolean updatePartnerPhone(Partner partner) {
        String sql = "UPDATE partner SET phonenumber = ? WHERE id = ?";
        try(Connection conn = ConnectionDB.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);){

            ps.setString(1, partner.getPhonenumber());
            ps.setInt(2, partner.getId());

            int fila = ps.executeUpdate();
            if(fila > 0){
                System.out.println("The member's phone number has been updated correctly" + partner.getPhonenumber());
                return true;
            }
        } catch (SQLException e) {
            throw  new PartnerDAOException("Check the values recorded in the database, try again." + e.getMessage() + JOptionPane.WARNING_MESSAGE);
        }
        return false;
    }

    @Override
    public void deletePartner(int id) {
        String sql = "DELETE FROM partner WHERE id = ?";
        Partner partner = null;
        try(Connection conn = ConnectionDB.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setInt(1, id);

            int fila = ps.executeUpdate();
            if(fila > 0){
                System.out.println("The partner has been successfully removed" + partner.getCode_partner());
            }
        } catch (SQLException e) {
            throw  new PartnerDAOException("Check the values recorded in the database, try again." + e.getMessage() + JOptionPane.WARNING_MESSAGE);
        }
    }
}
