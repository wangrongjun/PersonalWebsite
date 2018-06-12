let rootVm;

$(function () {
    $(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

    rootVm = new Vue({
        el: "#root",
        data: {
            noteList: [],
        },
        methods: {
            copyToClipboard,
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

function addNote(content) {
    $.ajax({
        url: "/note",
        type: "POST",
        data: {content},
        success: function (result) {
            Vue.set(rootVm.noteList, -1, result);
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function copyToClipboard(content) {
    let input = document.createElement("input");
    input.value = content;
    document.body.appendChild(input);
    input.select(); // 选择对象
    document.execCommand("Copy"); // 执行浏览器复制命令
    document.body.removeChild(input);
    alert('复制成功');
}
