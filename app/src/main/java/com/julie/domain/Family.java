package com.julie.domain;

import java.sql.Date;

public class Family {
  private String name;
  private String num;
  private String mail;
  private String address; 
  private Date birth;
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getNum() {
    return num;
  }
  public void setNum(String num) {
    this.num = num;
  }
  public String getMail() {
    return mail;
  }
  public void setMail(String mail) {
    this.mail = mail;
  }
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public Date getBirth() {
    return birth;
  }
  public void setBirth(Date birth) {
    this.birth = birth;
  }
}
