function getBrandReportUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/brandreport";
}

function genBrandReport() {
    var url = getBrandReportUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            generateReport(data, 'brand_report.csv');
        },
        error: handleAjaxError
    });
}
function getInventoryReportUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/inventoryreport";
}
function genInventoryReport() {
    var url = getInventoryReportUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            generateReport(data, 'inventory_report.csv');
        },
        error: handleAjaxError
    });
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