/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.epsi.manager;

import java.util.Date;

/**
 *
 * @author epsi
 */
public class Pdf {
    private int  id ; 
    private String name ; 
    private String path ; 
    private String description ; 
    private Double size ; 
    private Date dateUpload ; 

    public Pdf() {
    }

    public Pdf(String name, String path) {
        this.name = name;
        this.path = path;
    }

    
    
    public Pdf(String name, String path, String description, Double size, Date dateUpload) {
        this.name = name;
        this.path = path;
        this.description = description;
        this.size = size;
        this.dateUpload = dateUpload;
    }

    
    
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the size
     */
    public Double getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Double size) {
        this.size = size;
    }

    /**
     * @return the dateUpload
     */
    public Date getDateUpload() {
        return dateUpload;
    }

    /**
     * @param dateUpload the dateUpload to set
     */
    public void setDateUpload(Date dateUpload) {
        this.dateUpload = dateUpload;
    }
    
    
    
    
}
