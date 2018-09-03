var rootVm;

$(function () {
    $(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

    rootVm = new Vue({
        el: "#root",
        data: {
            fileItemList: [],
            navigateList: [],
            encodedPath: "",
            relativePath: ""
        },
        methods: {
            chooseFile: chooseFile,
            confirmDeleteFileOrDir: confirmDeleteFileOrDir,
            chooseDirToMove: chooseDirToMove
        }
    });

    showFileList();
});

function showFileList() {
    $.ajax({
        url: "/showFileList",
        type: "GET",
        success: function (result) {
            rootVm.fileItemList = result.data.fileItemList;
            rootVm.navigateList = result.data.navigateList;
            rootVm.encodedPath = result.data.encodedPath;
            rootVm.relativePath = result.data.relativePath;
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function uploadNewFile() {
    var formData = new FormData($("#uploadFileForm")[0]);
    $.ajax({
        url: "/uploadFile",
        type: "POST",// 只要上传文件，就必须是POST。否则会报400 - Bad Request。说明：即使使用SpringMVC的HiddenHttpMethodFilter也无法实现PUT请求，因为在multipart/form-data下，request.getParameter("_method")返回null。
        data: formData,
        processData: false,// 不处理发送的数据。没有的话报400 - Bad Request。
        contentType: false,// 不能设置Content-Type请求头。注意！是false！不是null！否则报400 - Bad Request。
        cache: false,// 上传的文件不需要缓存
        success: function (result) {
            location.reload();
        },
        error: function (xhr) {
            alert("操作失败！错误信息：" + xhr.responseText);
        }
    });
}
