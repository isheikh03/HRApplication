/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.been;

import com.dao.EmployeeDao;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author IBRAHIM SHEIKH
 */
@ManagedBean(name = "employee")
@RequestScoped
public class Employee {

    private Integer id;
    private String firstname;
    private String lastname;
    private String title;
    private String division;
    private String building;
    private String room;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Employee() {
    }

    public Employee(Integer id, String firstname, String lastname, String title, String division, String building, String room) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.title = title;
        this.division = division;
        this.building = building;
        this.room = room;
    }

    private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

    public String editEmployee(Integer empId) {
        EmployeeDao empDao = new EmployeeDao();
        Employee emp = empDao.getEmployeeById(empId);
        if (emp != null) {
            sessionMap.put("employee", empDao.getEmployeeById(empId));
        } else {
            sessionMap.put("employee", new Employee());
        }
        return "/edit.xhtml?faces-redirect=true";
    }

    public String deleteEmployee(Integer empId) {
        EmployeeDao empDao = new EmployeeDao();
        empDao.deleteEmployeeById(empId);
        return "/index.xhtml?faces-redirect=true";
    }

    public String updateEmployee() {
        EmployeeDao empDao = new EmployeeDao();
        int emp = empDao.updateEmployee(this);
        if (emp == 1) {
            this.clearEmp();
        }
        return "/index.xhtml?faces-redirect=true";
    }

    public void searchByEmployeeId(Integer empId) {
        EmployeeDao empDao = new EmployeeDao();
        Employee emp = empDao.getEmployeeById(empId);
        if (emp != null) {
            sessionMap.put("employee", empDao.getEmployeeById(empId));
        } else {
            this.clearEmp();
        }
    }

    public ArrayList<Employee> listEmployees() {
        EmployeeDao empDao = new EmployeeDao();
        return empDao.getGetAllEmployee();
    }

    public void saveCategory() {
        EmployeeDao empDao = new EmployeeDao();
        Employee emp = empDao.saveCategory(this);
        if (emp != null) {
            this.clearEmp();
        }
    }

    private void clearEmp() {
        this.setId(null);
        this.setFirstname(null);
        this.setLastname(null);
        this.setTitle(null);
        this.setDivision(null);
        this.setBuilding(null);
        this.setRoom(null);
    }

    public String getToPage(String pageName) {
        this.clearEmp();
        return pageName != null ? pageName : "index";
    }

}
