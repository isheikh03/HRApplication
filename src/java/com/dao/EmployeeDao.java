/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.been.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author IBRAHIM SHEIKH
 */
public class EmployeeDao {

    public Employee getEmployeeById(Integer id) {
        Connection connection = null;
        Employee emp = null;
        try {
            DbConnection dbCon = new DbConnection();
            connection = dbCon.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select * from employee where id=" + id);
            rs.next();

            emp = new Employee(rs.getInt("id"), rs.getString("firstname"),
                    rs.getString("lastname"), rs.getString("title"), rs.getString("division"),
                    rs.getString("building"), rs.getString("room"));
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            this.closeConnection(connection);
        }
        return emp;
    }

    public boolean deleteEmployeeById(Integer id) {
        Connection connection = null;
        boolean isDelete;
        try {
            DbConnection dbCon = new DbConnection();
            connection = dbCon.getConnection();
            PreparedStatement ps = connection.prepareStatement("delete from employee where id=?");
            ps.setInt(1, id);

            ps.executeUpdate();
            isDelete = true;
        } catch (Exception e) {
            isDelete = false;
            System.out.println(e);
        } finally {
            this.closeConnection(connection);
        }
        return isDelete;
    }

    public int updateEmployee(Employee emp) {
        Connection connection = null;
        int isUpdate;
        try {
            DbConnection dbCon = new DbConnection();
            connection = dbCon.getConnection();
            PreparedStatement ps = connection.prepareStatement("update employee"
                    + " set firstname=?, lastname=?, title=?, division=?, building=?,"
                    + " room=? where id=?");

            ps.setString(1, emp.getFirstname());
            ps.setString(2, emp.getLastname());
            ps.setString(3, emp.getTitle());
            ps.setString(4, emp.getDivision());
            ps.setString(5, emp.getBuilding());
            ps.setString(6, emp.getRoom());
            ps.setInt(7, emp.getId());

            isUpdate = ps.executeUpdate();
        } catch (Exception e) {
            isUpdate = 0;
            System.out.println(e);
        } finally {
           this.closeConnection(connection);
        }
        return isUpdate;
    }

    public ArrayList<Employee> getGetAllEmployee() {
        ArrayList<Employee> empList = new ArrayList<>();
        Connection connection = null;
        try {
            DbConnection dbCon = new DbConnection();
            connection = dbCon.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select * from employee");
            while (rs.next()) {
                Employee emp = new Employee(rs.getInt("id"), rs.getString("firstname"),
                        rs.getString("lastname"), rs.getString("title"), rs.getString("division"),
                        rs.getString("building"), rs.getString("room"));

                empList.add(emp);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            this.closeConnection(connection);
        }
        return empList;
    }

    public Employee saveCategory(Employee emp) {
        Connection connection = null;
        try {
            DbConnection dbCon = new DbConnection();
            connection = dbCon.getConnection();
            PreparedStatement ps = connection.prepareStatement("insert into"
                    + " employee(id,firstname,lastname,title,division,building,room)"
                    + " value('" + emp.getId() + "', '" + emp.getFirstname() + "', '" 
                    + emp.getLastname() + "', '" + emp.getTitle() + "', '" 
                    + emp.getDivision() + "', '" + emp.getBuilding() + "', '" 
                    + emp.getRoom() + " ')");
            
            ps.executeUpdate();
        } catch (Exception e) {
            emp = null;
            System.out.println(e);
        } finally {
            this.closeConnection(connection);
        }
        return emp;
    }
    
    private void closeConnection(Connection connection) {
        try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
    }

}
