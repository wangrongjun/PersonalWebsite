/**
 *  by wangrongjun on 2017/8/29.
 */

var nameSortType = "0";
var sizeSortType = "2";
var timeSortType = "4";

function btnSortByName() {
    var reverseSortType = nameSortType.indexOf("desc") != -1 ? "sort_by_name" : "sort_by_name_desc";
    window
}

function btnSortBySize() {

}

function btnSortByTime() {

}

function showSortIcon(sortType) {
    switch (sortType) {
        case "sort_by_name":
            setIcon()
            $("#ic_sort_by_name").html("<img src='/img/ic_arrow_down.png' style='width: 100%;height: 100%;transform:rotate(0deg);'/>");
            break;
        case "sort_by_name_desc":
            $("#ic_sort_by_name").html("<img src='/img/ic_arrow_down.png' style='width: 100%;height: 100%;transform:rotate(180deg);'/>");
            break;
        case "sort_by_size":
            $("#ic_sort_by_size").html("<img src='/img/ic_arrow_down.png' style='width: 100%;height: 100%;transform:rotate(0deg);'/>");
            break;
        case "sort_by_size_desc":
            $("#ic_sort_by_size").html("<img src='/img/ic_arrow_down.png' style='width: 100%;height: 100%;transform:rotate(180deg);'/>");
            break;
        case "sort_by_time":
            $("#ic_sort_by_time").html("<img src='/img/ic_arrow_down.png' style='width: 100%;height: 100%;transform:rotate(0deg);'/>");
            break;
        case "sort_by_time_desc":
            $("#ic_sort_by_time").html("<img src='/img/ic_arrow_down.png' style='width: 100%;height: 100%;transform:rotate(180deg);'/>");
            break;
    }
}

function setIcon(iconId, degree) {
    $("#" + iconId).html("<img src='/img/ic_arrow_down.png' " +
        "style='width: 100%;height: 100%;transform:rotate(" + degree + "deg);'/>");
}