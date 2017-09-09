<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="title" value="Search Users" />
<%@include file="head.jsp"%>
<html>
<body>

<h2>User Display Exercise - Week 1</h2>
<form action="searchUser" method="GET" class="form-inline" >
   Search Last Name: <input type="text" name="searchTerm" class="form-control" />
    <input type="submit" name="submit" value="search" class="btn-primary" />
    <button type="submit"name="submit" value="allUsers" class="btn-primary" >Display All Users</button>
</form>
</body>
</html>