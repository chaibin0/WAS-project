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
package servlet.http;

import java.io.IOException;
import servlet.GenericServlet;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;

public class HttpServlet extends GenericServlet {

  private static final long serialVersionUID = 1L;

  public HttpServlet() {}

  public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {

    String method = request.getMethod();

    switch (method) {
      case "GET":
        doGet(request, response);
        break;
      case "POST":
        doPost(request, response);
        break;
      case "PUT":   
        break;
      case "DELETE":
        break;
      case "CONNECT":
        break;
      case "TRACE":
        break;
      default:
        doGet(request, response);
    }


  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {}

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {}

}
