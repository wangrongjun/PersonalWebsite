var rootVm;

$(function () {
    $(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

    rootVm = new Vue({
        el: "#root",
        data: {
            noteList: [],
            newNoteContent: ""
        },
        methods: {
            copyToClipboard: copyToClipboard,
            addNote: addNote,
            deleteNote: deleteNote
        },
        watch: {
            noteList: function () {
                for (var note of rootVm.noteList) {
                    note.content = note.content.replace(/(http[s]?:\/\/.+)/g, "<a href='$1' target='_blank'>$1</a>");
                }
            }
        }
    });

    showNoteList();
});

function showNoteList() {
    $.ajax({
        url: "/note",
        type: "GET",
        success: function (result) {
            rootVm.noteList = result;
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function addNote() {
    if (rootVm.newNoteContent === "") {
        alert("不能为空");
        return;
    }
    $.ajax({
        url: "/note",
        type: "POST",
        data: {content: rootVm.newNoteContent},
        success: function (result) {
            rootVm.newNoteContent = "";
            rootVm.noteList.splice(0, 0, result);
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function copyToClipboard(content) {
    var input = document.createElement("textarea");
    input.value = content;
    document.body.appendChild(input);
    input.select(); // 选择对象
    document.execCommand("Copy"); // 执行浏览器复制命令
    document.body.removeChild(input);
    alert('复制成功');
}

function deleteNote(noteId) {
    if (confirm("确实要删除该笔记吗？")) {
        $.ajax({
            url: "/note/" + noteId,
            type: "DELETE",
            success: function (result) {
                for (var i = 0; i < rootVm.noteList.length; i++) {
                    var note = rootVm.noteList[i];
                    if (note.noteId === noteId) {
                        rootVm.noteList.splice(i, 1);
                        break;
                    }
                }
                rootVm.noteList.splice(0, 0);
            },
            error: function (xhr) {
                alert(xhr.responseText);
            }
        });
    }
}