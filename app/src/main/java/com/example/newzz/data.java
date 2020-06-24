package com.example.newzz;

public class data {
    private String heading;
    private String description;
    private String imageurl;
    private String sourcename;

    public data(String heading, String description, String imageurl) {
        this.heading = heading;
        this.description = description;
        this.imageurl = imageurl;
    }

    public data() {
    }

    public String getSourcename() {
        return sourcename;
    }

    public void setSourcename(String sourcename) {
        this.sourcename = sourcename;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
