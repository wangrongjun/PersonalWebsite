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
            alert(JSON.stringify(result.data));
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
