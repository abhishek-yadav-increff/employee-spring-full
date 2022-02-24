
//HELPER METHOD
function toJson($form) {
    var serialized = $form.serializeArray();
    console.log(serialized);
    var s = '';
    var data = {};
    for (s in serialized) {
        data[serialized[s]['name']] = serialized[s]['value']
    }
    var json = JSON.stringify(data);
    return json;
}


function handleAjaxError(response) {
    var response = JSON.parse(response.responseText);
    if (response != null) {
        $.toast({
            heading: 'Failure',
            text: response.message,
            // showHideTransition: 'slide',
            hideAfter: false,
            allowToastClose: true,
            position: 'top-right',
            icon: 'error'
        });
    }
}

function readFileData(file, callback) {
    var config = {
        header: true,
        delimiter: "\t",
        skipEmptyLines: "greedy",
        complete: function (results) {
            callback(results);
        }
    }
    Papa.parse(file, config);
}


function writeFileData(arr, filename) {
    var config = {
        quoteChar: '',
        escapeChar: '',
        delimiter: "\t"
    };

    var data = Papa.unparse(arr, config);
    var blob = new Blob([data], { type: 'text/tsv;charset=utf-8;' });
    var fileUrl = null;

    if (navigator.msSaveBlob) {
        fileUrl = navigator.msSaveBlob(blob, filename);
    } else {
        fileUrl = window.URL.createObjectURL(blob);
    }
    var tempLink = document.createElement('a');
    tempLink.href = fileUrl;
    tempLink.setAttribute('download', filename);
    tempLink.click();
}
//INITIALIZATION CODE
function init() {
    $('#gotoBrand').click(function () {
        window.location.href = "http://localhost:9000/employee/ui/reports/brand";
    });
    $('#gotoInventory').click(function () {
        window.location.href = "http://localhost:9000/employee/ui/reports/inventory";

    });
    $('#gotoSales').click(function () {
        window.location.href = "http://localhost:9000/employee/ui/reports/sales";
    });
}

$(document).ready(init);