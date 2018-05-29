/**
 *  by wangrongjun on 2017/8/24.
 */
var cbFileList = document.getElementsByClassName("cbFile");

function chooseFile(index) {
    if (cbFileList != null && index < cbFileList.length) {
        if (!cbFileList[index].checked) {
            cbFileList[index].checked = "true";
        } else {
            cbFileList[index].checked = "";
        }
    }
}

function chooseAllFile() {
    if (cbFileList != null && cbFileList.length > 0) {
        for (var i = 0; i < cbFileList.length; i++) {
            if (!cbFileList[i].checked) {
                cbFileList[i].checked = "true";
            }
        }
    }
}

function chooseReverseFile() {
    if (cbFileList != null && cbFileList.length > 0) {
        for (var i = 0; i < cbFileList.length; i++) {
            if (cbFileList[i].checked) {
                cbFileList[i].checked = "";
            } else {
                cbFileList[i].checked = "true";
            }
        }
    }
}

function mkdir(encodedPath) {
    var dirName = prompt("新建文件夹的名字", "新建文件夹");
    if (dirName.length == 0) {
        alert("不能为空");
        return;
    }
    showProgress();
    window.location.href = "createDirectory.do?dirName=" + dirName + "&encodedPath=" + encodedPath;
}

function showProgress() {
    var progress = document.getElementById("progress");
    progress.style.display = "";
}

function confirmDeleteFileOrDir(fileName, encodedPath) {
    var isDelete = confirm("确实要删除 “" + fileName + "” 吗？");
    // var password = prompt("请输入口令以删除文件 “" + fileName + "”", "");
    // if (password != null) {
    if (isDelete) {
//            window.location.href = "/deleteFileOrDir.jsp?fileNameList=" + fileName;
        deleteFileOrDir(fileName, "2143", encodedPath);
    }
}

function confirmDeleteSelectedFileOrDir(encodedPath) {
    var isDelete = confirm("确实要删除选中的所有文件吗？");
    // var password = prompt("请输入口令以删除选中的所有文件", "");
    // if (password != null) {
    if (isDelete) {
        if (cbFileList != null && cbFileList.length > 0) {
            var fileNameList = "";
            for (var i = 0; i < cbFileList.length; i++) {
                if (cbFileList[i].checked) {
                    var fileName = cbFileList[i].getAttribute("fileName");
                    console.log(fileName);
                    fileNameList += fileName + "_d_i_v_i_d_e_r_";//多个文件名之间的分隔符
                }
            }
            deleteFileOrDir(fileNameList, "2143", encodedPath);
        }
    }
}

function deleteFileOrDir(fileNameList, password, encodedPath) {
    showProgress();
    window.location.href = "deleteFileOrDir.do?fileNameList=" + fileNameList +
        "&password=" + password +
        "&encodedPath=" + encodedPath;
}

// 返回上一级
function back() {
    $("a.address:last").get(0).click();
}