let rootVm;

$(function () {
    $(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

    rootVm = new Vue({
        el: "#root",
        data: {
            noteList: [
                {
                    noteId: 1,
                    content: "note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 note1 ",
                    createdOn: "2016-01-01"
                },
                {
                    noteId: 2,
                    content: "note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2note2",
                    createdOn: "2016-01-02"
                },
                {noteId: 3, content: "note3", createdOn: "2016-01-03"},
            ],
        }
    });
});

function addNote(content) {
    $.ajax({
        url: "/note",
        type: "POST",
        data: {content},
        success: function (result) {
            Vue.set(rootVm.noteList, rootVm.noteList.length, result);
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}
