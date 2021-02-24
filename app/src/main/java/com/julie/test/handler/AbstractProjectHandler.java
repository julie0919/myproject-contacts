package com.julie.test.handler;

import java.util.List;
import com.julie.test.domain.Project;

public abstract class AbstractProjectHandler implements Command {

  protected List<Project> projectList;

  public AbstractProjectHandler (List<Project> projectList) {
    this.projectList = projectList;
  }

  protected Project findById(int projectId) {
    Project[] list = projectList.toArray(new Project[projectList.size()]);
    for (Project p : list) {
      if (p.getId() == projectId) {
        return p;
      }
    }
    return null;
  }
}
