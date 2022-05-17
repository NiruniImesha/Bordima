package com.projectmad.bordima;

import android.net.Uri;

public class Service {
    String pid,sname,simage,location,contactno, price;

    public Service() {
    }

    public Service(String pid, String sname, String simage, String location, String contactno, String price) {
        this.pid = pid;
        this.sname = sname;
        this.simage = simage;
        this.location = location;
        this.contactno = contactno;
        this.price = price;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Uri getSimage() {
        return Uri.parse(this.simage = simage);
    }

    public void setSimage(String simage) {
        this.simage = simage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
