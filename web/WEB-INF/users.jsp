<%-- 
    Document   : users
    Created on : Oct 30, 2022, 1:02:50 PM
    Author     : Steven
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users</title>
        <style>
            table, th{
                border:1px solid black;
            }
        </style>
    </head>
    <body>
        <h1>Manage Users</h1>
        <c:choose>
            <c:when test="${users.size() le 0}">
                <h4>No users found. Please add a user.</h4>
            </c:when>
            <c:otherwise>
                <table>
                    <tr>
                        <th>Email</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Role</th>
                        <td></td>
                        <td></td>
                    </tr>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.email}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.role.roleName}</td>
                            <td>
                                <a href="
                                   <c:url value='/users'>
                                       <c:param name='email' value='${user.email}'/>
                                       <c:param name='action' value='edit'/>
                                   </c:url>
                                   ">
                                    Edit
                                </a>
                            </td>
                            <td>
                                <a href="
                                   <c:url value='/users'>
                                       <c:param name='email' value='${user.email}'/>
                                       <c:param name='action' value='delete'/>
                                   </c:url>
                                   ">
                                    Delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>

        <h2>${userSelected ? "Edit User" : "Add User"}</h2>
        
        <form action="users" method="post">
            <c:choose>
                <c:when test="${!userSelected}">
                    Email: <input type="text" name="email"> <br>
                    First name: <input type="text" name="fname"> <br>
                    Last name: <input type="text" name="lname"> <br>
                    Password: <input type="password" name="password"> <br>
                    Role: <select name="roles">
                        <option value="system admin">system admin</option>
                        <option value="regular user">regular user</option>
                    </select> <br>
                    <input type="hidden" name="action" value="add">
                    <input type="submit" value="Add user">
                </c:when>
                <c:otherwise>
                    <input type="hidden" name="email" value="${user.email}"> 
                    Email: ${user.email} <br>
                    First name: <input type="text" name="fname" value="${user.firstName}"> <br>
                    Last name: <input type="text" name="lname" value="${user.lastName}"> <br>
                    Password: <input type="password" name="password"> <br>
                    Role: <select name="roles">
                        <c:forEach var="role" items="${roles}">
                            <option value="${role.roleName}" ${role.roleName == user.role.roleName ? "selected" : ""}>${role.roleName}</option>
                        </c:forEach>
                    </select> <br>
                    <div id="edit--buttons">
                        <!--<input type="hidden" name="action" value="update">-->
                        <input type="submit" value="Update" name="action">
                        <!--<input type="hidden" name="action" value="cancel">-->
                        <input type="submit" value="Cancel" name="action">
                    </div>
                </c:otherwise>
            </c:choose>
        </form>
        ${error}
    </body>
</html>
