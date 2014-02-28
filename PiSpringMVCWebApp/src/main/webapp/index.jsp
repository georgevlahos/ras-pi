<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html
     PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<!--   <body background="/pi/images/pi.gif"> -->
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>Pi Index</title>
</head>

<body>
<div id="main_wrapper">

<h1>Pi Index</h1>
<img src="/pi/images/pi.gif" />
<br/><br/>
<a href="http://localhost:8080/pi/pi-serv/translateTif">Translate a image!</a>
</div>
<br/>


<form method="POST" action="<c:url value="/pi-serv/uploadFile" />" enctype="multipart/form-data">

    Please select a file to upload : <input type="file" name="file" />
    <input type="submit" value="upload" />

</form>



</body>

</html>