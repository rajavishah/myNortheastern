<%@taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <title>Admin-Header</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

</head>

<body>
<div class="container-fluid">
    <div class="navbar-header">
        <a class="navbar-brand" href="#">Admin-Dashboard</a>
    </div>
    <ul class="nav navbar-nav">
        <c:if test="${param.error == null && sessionScope['username'] != null}" >
            <li class="nav-item"><a href="${pageContext.request.contextPath}/admin.htm"> <c:out value="${sessionScope['username'].getFullname()}"></c:out> </a></li>
            <c:if test="${sessionScope['username'].getUsertype().equals('ADMIN')}">
                <li class="nav-item"><a href="${pageContext.request.contextPath}/admin/manage-student.htm">Manage-Student</a></li>
                <li class="nav-item"><a href="${pageContext.request.contextPath}/admin/manage-faculty.htm">Manage-Faculty</a></li>
                <li class="nav-item"><a href="${pageContext.request.contextPath}/admin/manage-course.htm">Manage-Course</a></li>
            </c:if>
            <c:if test="${sessionScope['username'].getUsertype().equals('STUDENT')}">
                <li class="nav-item"><a href="${pageContext.request.contextPath}/student/profile.htm">Profile</a></li>
                <li class="nav-item"><a href="${pageContext.request.contextPath}/student/stu-course.htm">Courses</a></li>
            </c:if>
            <c:if test="${sessionScope['username'].getUsertype().equals('FACULTY')}">
                <li class="nav-item"><a href="${pageContext.request.contextPath}/faculty/profile.htm">Profile</a></li>
                <li class="nav-item"><a href="${pageContext.request.contextPath}/faculty/fac-course.htm">Courses</a></li>
            </c:if>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/user/logout.htm">Logout</a></li>
        </c:if>
        <c:if test="${param.error != null || sessionScope['username'] == null}" >
            <li class="nav-item"><a href="${pageContext.request.contextPath}/user/login.htm">Login</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/user/add.htm">Register</a></li>
        </c:if>
    </ul>
</div>
</body>