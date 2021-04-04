package com.julie.test.domain;

import java.io.Serializable;
import java.sql.Date;

public class Task implements Serializable {
  private static final long serialVersionUID = 1L;

  private int no;
  private String name;
  private Date endDate;
  private int progress;
  private String leader;

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
    result = prime * result + no;
    result = prime * result + ((leader == null) ? 0 : leader.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + progress;
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
    Task other = (Task) obj;
    if (endDate == null) {
      if (other.endDate != null)
        return false;
    } else if (!endDate.equals(other.endDate))
      return false;
    if (no != other.no)
      return false;
    if (leader == null) {
      if (other.leader != null)
        return false;
    } else if (!leader.equals(other.leader))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (progress != other.progress)
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
  public Date getEndDate() {
    return endDate;
  }
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  public int getProgress() {
    return progress;
  }
  public void setProgress(int progress) {
    this.progress = progress;
  }
  public String getLeader() {
    return leader;
  }
  public void setLeader(String leader) {
    this.leader = leader;
  }


}
