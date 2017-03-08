package com.elis.ltm.todo.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by davide on 04/03/17.
 */

public class Nota {
    private String title;
    private String body;
    private int id;
    private String lastModifyDate;
    private String creationDate;
    private String expiryDate;
    private Boolean status;

    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Nota() {
        date = Calendar.getInstance().getTime();
        creationDate = dateFormat.format(date);
    }

    public Nota(String title, String body) {
        this.title = title;
        this.body = body;
        date = Calendar.getInstance().getTime();
        creationDate = dateFormat.format(date);
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(String lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
