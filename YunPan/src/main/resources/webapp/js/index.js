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
            copyToClipboard: copyToClipboard,
            addNote: addNote,
            deleteNote: deleteNote
        }
    });

    showNoteList();
});

function showNoteList() {
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
