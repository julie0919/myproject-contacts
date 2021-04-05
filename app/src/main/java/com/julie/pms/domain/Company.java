package com.julie.pms.domain;

import com.julie.util.CsvObject;

public class Company implements CsvObject {

  private int no;
  private String name;
  private String tel;
  private String mail;
  private String work;
  private String address;

  public Company() {}

  public Company(String csv) {
    String[] fields = csv.split(",");

    this.setNo(Integer.parseInt(fields[0]));
    this.setName(fields[1]);
    this.setTel(fields[2]);
    this.setMail(fields[3]);
    this.setWork(fields[4]);
    this.setAddress(fields[5]);
  }

  @Override
  public String toCsvString() {
    return String.format("%d,%s,%s,%s,%s,%s\n",
        this.getNo(),
        this.getName(),
        this.getTel(),
        this.getMail(),
        this.getWork(),
        this.getAddress());
  }

  public static Company valueOfCsv(String csv) {
    String[] fields = csv.split(",");

    Company c = new Company();
    c.setNo(Integer.parseInt(fields[0]));
    c.setName(fields[1]);
    c.setTel(fields[2]);
    c.setMail(fields[3]);
    c.setWork(fields[4]);
    c.setAddress(fields[5]);

    return c;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((mail == null) ? 0 : mail.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + no;
    result = prime * result + ((tel == null) ? 0 : tel.hashCode());
    result = prime * result + ((work == null) ? 0 : work.hashCode());
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
    Company other = (Company) obj;
    if (address == null) {
      if (other.address != null)
        return false;
    } else if (!address.equals(other.address))
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
    if (work == null) {
      if (other.work != null)
        return false;
    } else if (!work.equals(other.work))
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
  public String getWork() {
    return work;
  }
  public void setWork(String work) {
    this.work = work;
  }
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
}
