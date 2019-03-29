package com.app.form;

import lombok.Data;

import java.io.Serializable;

@Data
public class KhanhForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ID;

    private String Name;

    private String Decri;

    public KhanhForm(String ID, String name, String decri) {
        this.ID = ID;
        Name = name;
        Decri = decri;
    }
}
