<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Login Page</title>
    </head>
    <body>
      <center>
        <form action="./j_spring_security_check" method="post">
        <span style="color:red">
        <%
          Exception error = (Exception)request.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
          if(request.getParameter("authfailed")!=null)
            out.write("Bad credential,try again.");
          else if(request.getParameter("sessionInvalid")!=null)
            out.write("Sessiong is invalid,please login again.");
          else if(request.getParameter("sessionExpired")!=null)
            out.write("Sessiong had expired,please login again.");
          else if(error!=null)
          	out.write(error.getMessage());
         %>
        </span>
        <table class="mars">
            <thead>
              <th colspan="2">Input Your Credential</th>
            </thead>
            <tbody>
                <tr>
                    <th><label for="j_username">Email</label>:&nbsp;</th>
                    <td class="left"><input id="j_username" name="j_username" size="20" maxlength="50" type="text"/></td>
                </tr>
                <tr>
                    <th><label for="j_password">Password</label>:&nbsp;</th>
                    <td class="left"><input id="j_password" name="j_password" size="20" maxlength="50" type="password"/></td>
                </tr>
                <!-- tr>
                	  <th colSpan="2"><input type="checkbox" id="_spring_security_remember_me" name="_spring_security_remember_me">
                	      <label for="_spring_security_remember_me">Remeber me 2 weeks</label></th>
                </tr-->
            </tbody>
            <tfoot>
            	  <tr>
            	  	  <td align="center"><input type="submit"/></td>
            	  	  <td align="center"><input type="reset"/></td>
            	  </tr>
            </tfoot>
        </table>
        </form>   
        
      </center>
    </body>
</html>
