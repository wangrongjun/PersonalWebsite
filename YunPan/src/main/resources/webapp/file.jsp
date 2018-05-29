<%--suppress ALL --%>
<%--
  Created by IntelliJ IDEA.
  User: wangrongjun
  Date: 2017/8/22
  Time: 22:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>我的文件</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min-3.2.0.css"/>
    <link rel="stylesheet" type="text/css" href="css/jquery.treeview.css"/>
    <link rel="stylesheet" type="text/css" href="css/screen.css"/>
    <link rel="stylesheet" type="text/css" href="css/file.css"/>
</head>

<body>

<%--导航栏--%>
<div class="navbar navbar-default navbar-fixed-top">
    <div class="navbar-header">
        <a href="file.jsp" class="navbar-brand">我的文件</a>
        <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".navbar-responsive-collapse">
            <span class="sr-only">Toggle Navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
    </div>
    <div class="collapse navbar-collapse navbar-responsive-collapse">
        <ul class="nav navbar-nav">
            <li class="dropdown">
                <a href="#" data-toggle="dropdown">
                    排序方式
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="showFileList.do?orderStyle=0&path=${encodedPath}" tabindex="-1">文件名</a>
                    </li>
                    <li>
                        <a href="showFileList.do?orderStyle=2&path=${encodedPath}" tabindex="-1">创建时间</a>
                    </li>
                    <li>
                        <a href="showFileList.do?orderStyle=4&path=${encodedPath}" tabindex="-1">文件大小</a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:void(0);" onclick="chooseAllFile()">全选</a>
            </li>
            <li>
                <a href="javascript:void(0);" onclick="chooseReverseFile()">反选</a>
            </li>
            <li class="dropdown">
                <a href="#" data-toggle="dropdown">
                    批量操作
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="javascript:void(0);" onclick="confirmDeleteSelectedFileOrDir('${encodedPath}')"
                           tabindex="-1">删除所有选中的文件</a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:void(0);" onclick="mkdir('${encodedPath}')">新建文件夹</a>
            </li>
        </ul>
        <form class="navbar-form" method="post" action="uploadFile.do" enctype="multipart/form-data">
            <div class="form-group">
                <input type="hidden" name="encodedPath" value="${encodedPath}">
                <input type="file" class="form-control" name="file">
                <input onclick="showProgress()" type="submit" class="btn btn-primary" value="上传"> <br/>
            </div>
        </form>
    </div>
</div>

<%--地址栏--%>
<div>
    <ol class="breadcrumb">
        <button class="btn btn-info" onclick="back()" style="margin-right: 10px">返回上一级</button>
        <button class="btn btn-default" onclick="window.location.reload()" style="margin-right: 30px">刷新</button>
        <li><a class="address" href="file.jsp">根目录</a></li>
        <c:set var="addressListSize" value="${fn:length(addressList)}"/>
        <%-- %2F就是反斜杠的URLEncoding编码 --%>
        <c:set var="encodedPath" value="%2f"/>
        <c:forEach var="address" items="${addressList}" varStatus="status">
            <c:set var="encodedPath" value="${encodedPath}${address}%2f"/>
            <c:if test="${status.index<addressListSize-1}">
                <li><a class="address" href="file.jsp?encodedPath=${encodedPath}">${address}</a></li>
            </c:if>
            <c:if test="${status.index==addressListSize-1}">
                <li class="active">${address}</li>
            </c:if>
        </c:forEach>
    </ol>
</div>

<%--进度条--%>
<div class="progress progress-striped active" id="progress" style="display: none">
    <div class="progress-bar progress-bar-success" id="progressBar" style="width: 100%">正在上传</div>
</div>

<%--表格--%>
<div class="table-responsive">
    <table class="table table-hover table-bordered">
        <thead>
        <tr>
            <th>选择</th>
            <th>序号</th>
            <th onclick="btnSortByName()">文件名<span id="ic_sort_by_name"></span></th>
            <th onclick="btnSortBySize()">文件大小<span id="ic_sort_by_size"></span></th>
            <th onclick="btnSortByTime()">修改时间<span id="ic_sort_by_time"></span></th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="fileItem" items="${fileItemList}" varStatus="status">
            <c:set var="name" value="${fileItem.name}"/>
            <c:set var="isDir" value="${fileItem.directory}"/>
            <c:set var="fileHref" value="${relativePath}${name}"/>
            <c:set var="dirHref" value="file.jsp?encodedPath=${encodedPath}${name}%2F"/>
            <tr onclick="chooseFile(${status.index})">
                    <%--多选按钮--%>
                <td>
                    <input type="checkbox" onclick="chooseFile(${status.index})" class="cbFile" fileName="${name}"/>
                </td>
                    <%--序号--%>
                <td>${status.index+1}</td>
                    <%--文件名和图标--%>
                <td>
                    <div class="img_type_box"><img id="img_type" src="${fileItem.iconUrl}"></div>
                    <a href="${isDir?dirHref:fileHref}" target="${isDir?'_self':'_blank'}">${name}</a>
                </td>
                    <%--文件大小--%>
                <td>${isDir?"-":fileItem.size}</td>
                    <%--创建时间--%>
                <td>${fileItem.lastModifiedDate}</td>
                    <%--操作按钮--%>
                <td>
                    <a href="${isDir?'#':fileHref}" target="_blank" class="btn btn-info btn-xs"
                       download="${name}">下载</a>
                    <a href="javascript:void(0);" onclick="confirmDeleteFileOrDir('${name}','${encodedPath}')"
                       class="btn btn-danger btn-xs">删除</a>
                    <a href="javascript:void(0);" onclick="chooseDirToMove('${encodedPath}${name}')"
                       class="btn btn-warning btn-xs">移动</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!--文件移动的模态弹出窗-->
<div class="modal fade" id="choose_dir_modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">移动到</h4>
            </div>
            <div class="modal-body">
                <div id="file_tree">模态弹出窗主体内容</div>
                <p>选择的目录：<span id="choose_dir"></span></p>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" data-dismiss="modal">取消</button>
                <button class="btn btn-primary" onclick="moveFileOrDir()" id="btn_confirm_move">移动</button>
            </div>
        </div>
    </div>
</div>
<!-- 文件移动的模态弹出窗 -->

<script src="js/jquery.min-1.9.0.js"></script>
<script src="js/jquery.treeview.js"></script>
<script src="js/jquery.cookie.js"></script>
<script src="js/bootstrap.min-3.2.0.js"></script>
<script src="js/canvas-nest.min.js"></script>
<script src="js/util/TextUtil.js"></script>
<script src="js/util/FolderUtil.js"></script>
<script src="js/file_main.js"></script>
<script src="js/file_move.js"></script>
<script src="js/file_sort.js"></script>

</body>

</html>