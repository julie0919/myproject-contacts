package com.julie.test.domain;

import java.sql.Date;

public class Project {
  private int id;
  private String name;
  private String content;
  private Date startDate;
  private Date endDate;
  private String leader;
  private String team;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public Date getStartDate() {
    return startDate;
  }
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  public Date getEndDate() {
    return endDate;
  }
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  public String getLeader() {
    return leader;
  }
  public void setLeader(String leader) {
    this.leader = leader;
  }
  public String getTeam() {
    return team;
  }
  public void setTeam(String team) {
    this.team = team;
  }
}
