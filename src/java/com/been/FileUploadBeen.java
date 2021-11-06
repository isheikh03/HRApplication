/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.been;

import com.dao.EmployeeDao;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.primefaces.model.UploadedFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author IBRAHIM SHEIKH
 */
@ManagedBean
@RequestScoped
public class FileUploadBeen {

    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void uploadFile() throws IOException, SAXException {

        if (file != null) {
            System.out.println("File Name: " + file.getFileName());

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            try {
                EmployeeDao empDao = new EmployeeDao();

                dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false);
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputStream inputStream = file.getInputstream();
                Document doc = db.parse(inputStream);
                doc.getDocumentElement().normalize();

                NodeList list = doc.getElementsByTagName("employee");
                for (int temp = 0; temp < list.getLength(); temp++) {

                    Node node = list.item(temp);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {

                        Element element = (Element) node;
                        String id = element.getAttribute("id");
                        String firstname = element.getElementsByTagName("firstname").item(0).getTextContent();
                        String lastname = element.getElementsByTagName("lastname").item(0).getTextContent();
                        String title = element.getElementsByTagName("title").item(0).getTextContent();
                        String division = element.getElementsByTagName("division").item(0).getTextContent();
                        String building = element.getElementsByTagName("building").item(0).getTextContent();
                        String room = element.getElementsByTagName("room").item(0).getTextContent();

                        if (id != null) {
                            Employee emp = new Employee(Integer.parseInt(id), firstname, lastname, title, division, building, room);
                            empDao.saveCategory(emp);
                        }
                    }
                }

            } catch (ParserConfigurationException ex) {
                Logger.getLogger(FileUploadBeen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    
}
