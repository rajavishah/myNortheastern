<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%--<%@taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core"%>--%>

<!doctype HTML>
<html>
<head>
    <title>TA Application</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7"
          crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
          integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r"
          crossorigin="anonymous">
</head>

<body>
<div class="container">

    <div class="row">
        <form:form modelAttribute="student" class="form-horizontal" enctype="multipart/form-data">
            <form:input type="text" class="form-control" id="grade"
                        path="grade" placeholder="A Grade.." required="required" />

            <form:input type="text" class="form-control" id="email"
                        path="email" placeholder="email@gmail.com" required="required" />

            <form:input type="text" class="form-control" id="first_name"
                        path="first_name" placeholder="First Name" required="required" />

            <form:input type="text" class="form-control" id="last_name"
                        path="last_name" placeholder="Last Name" required="required" />


            <form:input type="text" class="form-control" id="password"
                        path="password" placeholder="Password" required="required" />

            <form:input type="text" class="form-control" id="role"
                        path="role" placeholder="role" required="required" />








            <div class="form-group">
                <input type="submit" id="submit" class="btn btn-success" value="Save My Information"  />
            </div>
        </form:form>
    </div>
</div>


</body>
</html>