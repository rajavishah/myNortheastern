<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%--<%@taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core"%>--%>

<!doctype HTML>
<html>
<head>
    <title>Welcome MyNortheastern!</title>

</head>

<body>
<div class="container">
    <h1>Welcome Back Huskies!</h1>
    <div class="row">
        <form:form modelAttribute="user" class="form-horizontal" enctype="multipart/form-data">
            <form:input type="text" class="form-control" id="role"
                        path="role" placeholder="role" required="required" />

            <form:input type="text" class="form-control" id="email"
                        path="email" placeholder="email@gmail.com" required="required" />

            <form:input type="text" class="form-control" id="password"
                        path="password" placeholder="Password" required="required" />

            <div class="form-group">
                <input type="submit" id="submit" class="btn btn-success" value="Save My Information"  />
            </div>
        </form:form>

    </div>
</div>


</body>
</html>