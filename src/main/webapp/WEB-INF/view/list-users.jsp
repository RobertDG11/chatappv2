<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>

<head>
	<title>List Users</title>
	
</head>

<body>
    <div id=wrapper">
        <div id="header">
            <h2>URM - User Relationship Manager</h2>
        </div>
    </div>

    <div id="container">

        <div id="content">

            <table>
                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Username</th>
                    <th>Email address</th>
                    <th>Phone number</th>
                    <th>Notification type</th>
                    <th>Date created</th>
                </tr>

                <c:forEach var="tempUser" items="${users}">

                    <tr>
                        <td>${tempUser.firstName}</td>
                        <td>${tempUser.lastName}</td>
                        <td>${tempUser.username}</td>
                        <td>${tempUser.emailAddress}</td>
                        <td>${tempUser.phoneNumber}</td>
                        <td>${tempUser.notificationType}</td>
                        <td>${tempUser.dateCreated}</td>
                    </tr>

                </c:forEach>

            </table>

        </div>

    </div>


</body>

</html>









