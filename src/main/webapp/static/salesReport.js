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
        error: handleAjaxError
    });

}

function displayResults(data) {
    tocsv = data;
    console.log(data);

    var $tbody = $('#sales-table').find('tbody');
    $tbody.empty();
    for (var i in data) {
        var e = data[i];
        console.log(e)
        var row = '<tr>'
            + '<td>' + e.category + '</td>'
            + '<td>' + e.quantity + '</td>'
            + '<td>' + e.revenue.toFixed(2) + '</td>'
            + '</tr>';
        $tbody.append(row);
    }
}
function downloadReport() {
    if (tocsv.length == 0) {
        $.toast({
            heading: 'Failure',
            text: 'No data to download!',
            // showHideTransition: 'slide',
            hideAfter: false,
            allowToastClose: true,
            position: 'top-right',
            icon: 'error'
        });
    } else {
        generateReport(tocsv, "sales_report.csv")
    }
}
function generateReport(items, filename) {
    console.log(items);
    let csv = "";
    let keysCounter = 0;
    let row = 0;
    let keysAmount = Object.keys(items[row]).length;
    for (let key in items[row]) {
        // This is to not add a comma at the last cell
        // The '\r\n' adds a new line
        csv += key + (keysCounter + 1 < keysAmount ? ',' : '\r\n')
        keysCounter++
    }
    keysCounter = 0

    // Loop the array of objects
    for (let row = 0; row < items.length; row++) {
        let keysAmount = Object.keys(items[row]).length;

        // If this is the first row, generate the headings
        for (let key in items[row]) {
            csv += items[row][key] + (keysCounter + 1 < keysAmount ? ',' : '\r\n')
            keysCounter++
        }
        keysCounter = 0
    }

    // Once we are done looping, download the .csv by creating a link
    let link = document.createElement('a')
    link.id = 'download-csv'
    link.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(csv));
    link.setAttribute('download', filename);
    document.body.appendChild(link)
    document.querySelector('#download-csv').click()
    document.getElementById("download-csv").remove();
}
function resetForm() {
    document.getElementById("startDate").value = "";
    document.getElementById("endDate").value = "";
    document.getElementById("inputBrand").value = "";
    document.getElementById("inputCategory").value = "";
    getResults();
    $.toast({
        heading: 'Success',
        text: 'Refreshed!',
        // showHideTransition: 'slide',
        hideAfter: 3000,
        allowToastClose: true,
        position: 'top-right',
        icon: 'success'
    });
}
function init() {

    $(".datepicker").datepicker({
        clearBtn: true,
        // onsele
        autoclose: true,
        format: "dd/mm/yyyy",
    });

    $('#search-data').click(getResults);
    $('#reset-data').click(resetForm);
    $('#download-data').click(downloadReport);
    getResults();

}
var tocsv;
$(document).ready(init);
