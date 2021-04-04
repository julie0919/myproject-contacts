package com.julie.domain;

import java.sql.Date;
import com.julie.util.CsvObject;

public class Family implements CsvObject {

  private int no;
  private String name;
  private String tel;
  private String mail;
  private String address; 
  private Date birth;

  public Family() {}

  public Family(String csv) {
    String[] fields = csv.split(",");

    this.setNo(Integer.parseInt(fields[0]));
    this.setName(fields[1]);
    this.setTel(fields[2]);
    this.setMail(fields[3]);
    this.setAddress(fields[4]);
    this.setBirth(Date.valueOf(fields[5]));
  }

  @Override
  public String toCsvString() {
    return String.format("%d,%s,%s,%s,%s,%s\n",
        this.getNo(),
        this.getName(),
        this.getTel(),
        this.getMail(),
        this.getAddress(),
        this.getBirth().toString());
  }

  public static Family valueOfCsv(String csv) {
    String[] fields = csv.split(",");

    Family f = new Family();
    f.setNo(Integer.parseInt(fields[0]));
    f.setName(fields[1]);
    f.setTel(fields[2]);
    f.setMail(fields[3]);
    f.setAddress(fields[4]);
    f.setBirth(Date.valueOf(fields[5]));

    return f;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((birth == null) ? 0 : birth.hashCode());
    result = prime * result + ((mail == null) ? 0 : mail.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + no;
    result = prime * result + ((tel == null) ? 0 : tel.hashCode());
    return result;
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Family other = (Family) obj;
    if (address == null) {
      if (other.address != null)
        return false;
    } else if (!address.equals(other.address))
      return false;
    if (birth == null) {
      if (other.birth != null)
        return false;
    } else if (!birth.equals(other.birth))
      return false;
    if (mail == null) {
      if (other.mail != null)
        return false;
    } else if (!mail.equals(other.mail))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (no != other.no)
      return false;
    if (tel == null) {
      if (other.tel != null)
        return false;
    } else if (!tel.equals(other.tel))
      return false;
    return true;
  }

  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getTel() {
    return tel;
  }
  public void setTel(String tel) {
    this.tel = tel;
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