package com.projectmad.bordima;

public class Package {
    String pid,image,price,pname;

    public Package() {
    }

    public Package(String pid, String image, String price, String pname) {
        this.pid = pid;
        this.image = image;
        this.price = price;
        this.pname = pname;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
