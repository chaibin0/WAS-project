/*
 * Copyright (c) 1997-2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Modified by chaibin0
 */
package servlet;

import java.util.Enumeration;


public abstract class GenericServlet implements Servlet, ServletConfig, java.io.Serializable {

  private static final long serialVersionUID = 1L;

  private ServletConfig config;

  @Override
  public String getServletName() {

    return config.getServletName();
  }

  @Override
  public ServletContext getServletContext() {

    return config.getServletContext();
  }

  @Override
  public String getInitParameter(String name) {

    return config.getInitParameter(name);
  }

  @Override
  public Enumeration<String> getInitParameterNames() {

    return config.getInitParameterNames();
  }

  @Override
  public void init() {}

  public void init(ServletConfig config) {

    this.config = config;
    this.init();
  }

  @Override
  public void destory() {}

  @Override
  public void service() {

  }

}
