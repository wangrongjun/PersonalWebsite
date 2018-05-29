<%--
  Created by IntelliJ IDEA.
  User: wangrongjun
  Date: 2017/8/28
  Time: 12:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="css/jquery.treeview.css">
    <link rel="stylesheet" href="css/screen.css">
    <script src="js/jquery.min-1.9.0.js"></script>
    <script src="js/jquery.treeview.js"></script>
</head>
<body>

<div id="show"></div>

<script type="text/javascript">
    function toList(json) {

    }
</script>

<script type="text/javascript">
    var show = document.getElementById("show");
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        show.innerText = "读取完成";
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
            show.innerText = xmlHttp.responseText;
        }
    };
    xmlHttp.open("GET", "http://localhost:8080/getFolderList.do?t=" + Math.random(), true);
    show.innerText = "正在读取";
    xmlHttp.send();
</script>

</body>
</html>
