<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Self study guide - Login Page</title>
</head>
<body
	style="font-size: 14px; font-family: Arial; margin: 0; padding: 0; text-align: center;">

	<div style="width: 400px; text-align: left; margin: 0 auto;">
		<div id="header"
			style="text-align: center; font-size: 25px; margin-top: 20px;">Self
			Study Guide</div>
		<div id="container">
			<div id="content" style="padding: 15px 30px 10px 30px;">
				<form action="./j_spring_security_check" method="post">
					<span style="color: red"> <%
 	Exception error = (Exception) request.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
 	if (request.getParameter("authfailed") != null)
 		out.write("Bad credential,try again.");
 	else if (request.getParameter("sessionInvalid") != null)
 		out.write("Sessiong is invalid,please login again.");
 	else if (request.getParameter("sessionExpired") != null)
 		out.write("Sessiong had expired,please login again.");
 	else if (error != null)
 		out.write(error.getMessage());
 %> </span>
					<table style="margin-left: auto; margin-right: auto;">

						<tbody>
							<tr>
								<th><label for="j_username">Email</label>:&nbsp;</th>
								<td class="left"><input id="j_username" name="j_username"
									size="20" maxlength="50" type="text" /></td>
							</tr>
							<tr>
								<th><label for="j_password">Password</label>:&nbsp;</th>
								<td class="left"><input id="j_password" name="j_password"
									size="20" maxlength="50" type="password" /></td>
							</tr>

						</tbody>
						<tfoot>
							<tr>
								<td align="left"><input type="submit" value="Login" /></td>
							</tr>
						</tfoot>
					</table>
				</form>
			</div>
		</div>
	</div>





</body>
</html>
