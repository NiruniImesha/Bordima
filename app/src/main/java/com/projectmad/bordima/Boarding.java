package com.projectmad.bordima;

public class Boarding {
    String bathrooms,bdescription, bedrooms, bimage, bname, bprice,contactno, location,kitchens,pid;

    public Boarding() {
    }

    public Boarding(String bathrooms, String bdescription, String bedrooms, String bimage, String bname, String bprice, String contactno, String location, String kitchens, String pid) {
        this.bathrooms = bathrooms;
        this.bdescription = bdescription;
        this.bedrooms = bedrooms;
        this.bimage = bimage;
        this.bname = bname;
        this.bprice = bprice;
        this.contactno = contactno;
        this.location = location;
        this.kitchens = kitchens;
        this.pid = pid;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getBdescription() {
        return bdescription;
    }

    public void setBdescription(String bdescription) {
        this.bdescription = bdescription;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getBimage() {
        return bimage;
    }

    public void setBimage(String bimage) {
        this.bimage = bimage;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getBprice() {
        return bprice;
    }

    public void setBprice(String bprice) {
        this.bprice = bprice;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getKitchens() {
        return kitchens;
    }

    public void setKitchens(String kitchens) {
        this.kitchens = kitchens;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
