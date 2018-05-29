/**
 *  by wangrongjun on 2017/8/29.
 */

/**
 * 打开模态弹出窗并异步获取目录结构列表
 */
function chooseDirToMove(moveFilePath) {
    $.cookie("moveFilePath", moveFilePath);
    $("#choose_dir_modal").modal('toggle');
    $("#file_tree").html("正在加载目录<img src='/images/ajax-loader.gif'/>");
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState == 4) {
            if (xmlHttp.status == 200) {
                showFileTree(moveFilePath, JSON.parse(xmlHttp.responseText));
            } else {
                $("#file_tree").text("目录加载失败！错误代码：" + xmlHttp.status);
            }
        }
    };
    xmlHttp.open("GET", "getFolderList.do", true);
    setTimeout(function () {
        xmlHttp.send();
    }, 1000);
}

/**
 * 获取目录结构列表后使用jquery的treeview控件显示出来
 */
function showFileTree(moveFilePath, json) {
    $("#file_tree").html(getFileTreeHtml(json)).addClass("filetree");
    $(".filetree span").click(function () {
        $(".filetree span").css("background-color", "transparent");
        $(this).css("background-color", "#ddd");
        $("#choose_dir").text($(this).attr("data-path"))
    });
    $(".filetree").treeview();
}

/**
 * 用户选择完移动到的目录并点击确认后，执行该方法移动文件或目录
 */
function moveFileOrDir() {
    var moveFilePath = $.cookie("moveFilePath");
    if (moveFilePath == null || moveFilePath.length == 0) {
        alert("移动的文件未选择");
        return;
    }
    var chooseDir = $("#choose_dir").text();
    if (chooseDir.length == 0) {
        alert("请先选择目录");
        return;
    }

    $("#btn_confirm_move").attr("disabled", "disabled").html("移动中<img src='/images/ajax-loader.gif'/>");
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState == 4) {
            $("#btn_confirm_move").removeAttr("disabled").text("移动");// 恢复按钮原样
            $("#choose_dir_modal").modal('toggle');// 关闭模态弹出窗
            if (xmlHttp.status == 200) {
                if (xmlHttp.responseText == "succeed") {
                    window.location.reload();
                } else {
                    alert("移动失败！未知异常：" + xmlHttp.responseText);
                }
            } else {
                alert("移动失败！错误代码：" + xmlHttp.status);
            }
        }
    };
    var url = "moveFile.do?filePath=" + moveFilePath + "&moveToPath=" + chooseDir;
    xmlHttp.open("GET", url, true);
    setTimeout(function () {
        xmlHttp.send();
    }, 1000);
}