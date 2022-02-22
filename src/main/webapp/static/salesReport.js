function getSalesReportUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/reportsSales";
}
function convertToDate(dateString) {
    //  Convert a "dd/MM/yyyy" string into a Date object
    let d = dateString.split("/");
    let dat = new Date(d[2] + '/' + d[1] + '/' + d[0]);
    return dat;
}
function getResults() {
    let startDate = $("#startDate").val();
    let endDate = $("#endDate").val();
    let inputBrand = $("#inputBrand").val();
    let inputCategory = $("#inputCategory").val();
    var startDateMomentObject = convertToDate(startDate).getTime().toString();
    var endDateMomentObject = convertToDate(endDate).getTime().toString();
    var json = {
        startDate: startDateMomentObject,
        endDate: endDateMomentObject,
        brand: inputBrand,
        category: inputCategory,
    }
    console.log(JSON.stringify(json))
    url = getSalesReportUrl() + "/" + JSON.stringify(json);
    $.ajax({
        url: url,
        type: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            displayResults(response);
        },
        error: function () { }
    });

}
function displayResults(data) {
    console.log(data);
}
function init() {

    $(".datepicker").datepicker({
        clearBtn: true,
        // onsele
        autoclose: true,
        format: "dd/mm/yyyy",
    });
    $("#startDate").on("change", getResults);
    $('#endDate').on("change", getResults);
    $("#inputBrand").on("input", getResults);
    $('#inputCategory').on("input", getResults);
}
$(document).ready(init);
