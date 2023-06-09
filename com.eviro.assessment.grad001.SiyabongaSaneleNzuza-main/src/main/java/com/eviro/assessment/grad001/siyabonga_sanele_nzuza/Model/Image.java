package com.eviro.assessment.grad001.siyabonga_sanele_nzuza.Model;

public class Image {

    private String name;
    private String surname;
    private String imgFormat;
    private String imgEncode;

    public Image(String name, String surname, String imgFormat, String imgEncode) {
        this.name = name;
        this.surname = surname;
        this.imgFormat = imgFormat;
        this.imgEncode = imgEncode;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getImgFormat() {
        String[] getImgFormat = imgFormat.split("/");
        return "." + getImgFormat[1];
    }

    public String getImgEncode() {
        return imgEncode;
    }


}
