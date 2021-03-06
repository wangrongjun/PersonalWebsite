var rootVm;

$(function () {

    initAxiosLoading();
    // $(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

    rootVm = new Vue({
        el: "#root",
        data: {
            noteList: [],
            newNoteContent: ""
        },
        methods: {
            copyToClipboard: copyToClipboard,
            addNote: addNote,
            deleteNote: deleteNote,
            toHtml: toHtml
        },
    });

    showNoteList();
});

function initAxiosLoading() {
    axios.interceptors.request.use((config) => {
        $.blockUI();
        return config;
    }, (err) => {
        $.unblockUI();
        return Promise.reject(err)
    });
    axios.interceptors.response.use((response) => {
        $.unblockUI();
        return response;
    }, (err) => {
        $.unblockUI();
        return Promise.reject(err);
    });
}

function toHtml(content) {
    return content.replace(/(http[s]?:\/\/[^ \r\n)]+)/g, "<a href='$1' target='_blank'>$1</a>");
}

function showNoteList() {
    axios.get('/note')
        .then(function (response) {
            // handle success
            console.log(JSON.stringify(response.data));
            rootVm.noteList = response.data;
        })
        .catch(function (error) {
            // handle error
            alert(error.response.data);
            // console.log(error.response.status);// 状态码
            // console.log(error.response.data);// 错误信息
        })
        .then(function () {
            // always executed
        });
    // $.ajax({
    //     url: "/note",
    //     type: "GET",
    //     success: function (result) {
    //         rootVm.noteList = result;
    //     },
    //     error: function (xhr) {
    //         alert(xhr.responseText);
    //     }
    // });
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
    // alert('复制成功');
    toastInfo("复制成功")
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

function toastInfo(text) {
    bootoast.toast({
        message: text,
        type: 'info',
        position: 'top-center',
        icon: undefined,
        timeout: 3,
        animationDuration: 300,
        dismissible: true
    });
}