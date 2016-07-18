package com.cmcc.xmlParser;

public class Sms {
    
    private String address;
    private String body;
    private String date;
    
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    @Override
    public String toString() {
        return "Sms [address=" + address + ", body=" + body + ", date=" + date
                + "]";
    }
}
