
function toast(successState, message) {
    if (successState == true) {
        $.toast({
            heading: 'Success',
            text: message,
            showHideTransition: 'slide',
            hideAfter: 3000,
            allowToastClose: true,
            position: 'top-right',
            icon: 'success'
        });
    } else {
        $.toast({
            heading: 'Failure',
            text: message,
            hideAfter: false,
            allowToastClose: true,
            position: 'top-right',
            icon: 'error'
        });
    }
}

function getOrderEditUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/orderItem";
}


//UI DISPLAY METHODS



function getOrderId() {
    var tempArr = document.getElementById('orderIdSelect').innerHTML.split(" ");

    return tempArr[tempArr.length - 1];
}
function getOrderUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/order";
}
function getOrder(orderId) {
    var url = getOrderUrl() + '/' + orderId;
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayOrder(data);
            displayCost(data);
            displayButton(data);
        },
        error: function (err) {
            window.location.replace("http://localhost:9000/employee/ui/order");
        }
    });
}
function displayButton(data) {
    if (data.complete == 1) {
        document.getElementById('buttonElement').innerHTML = `
        <button type="button" class="btn btn-primary" id="invoiceButton" onclick="getBytePdf(`+ orderId + `)">Invoice</button>`;


    } else {
        document.getElementById('buttonElement').innerHTML = `
        <button type="button" class="btn btn-warning" id="editButton">Edit</button>`;
        document.getElementById("editButton").onclick = function () {
            window.location.replace("http://localhost:9000/employee/ui/orderEdit/" + data.id);
        };

    }
}
function displayOrder(data) {
    var options = { year: 'numeric', month: 'short', day: 'numeric', hour: 'numeric', minute: '2-digit' };
    var timeStr = new Date(data.time).toLocaleString(undefined, options);
    var $headingDate = $('#headingDate');
    $headingDate.text(timeStr);
}
function base64ToArrayBuffer(base64) {
    var binaryString = window.atob(base64);
    var binaryLen = binaryString.length;
    var bytes = new Uint8Array(binaryLen);
    for (var i = 0; i < binaryLen; i++) {
        var ascii = binaryString.charCodeAt(i);
        bytes[i] = ascii;
    }
    return bytes;
}
function saveByteArray(reportName, byte) {
    var blob = new Blob([byte], { type: "application/pdf" });
    var link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    var fileName = reportName;
    link.download = fileName;
    link.click();
};
function getOrderItemListByOrderId() {
    var url = getOrderEditUrl() + "/orderId/" + orderId;
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayOrderItemList(data);
        },
        error: handleAjaxError
    });
}
function displayCost(data) {
    $label = document.getElementById("show-cost");
    $label.innerHTML = "<h4><b>Total: " + data.cost + "</b></h4>";
}


function displayOrderItemList(data) {
    var $tbody = $('#orderPreview-table').find('tbody');
    $tbody.empty();
    for (var i in data) {
        var e = data[i];

        var row = '<tr>'
            + '<td>' + e.name + '</td>'
            + '<td>' + e.productBarcode + '</td>'
            + '<td>' + e.mrp + '</td>'
            + '<td>' + e.quantity + '</td>'
            + '<td>' + e.sellingPrice + '</td>'
            + '</tr>';
        $tbody.append(row);
    }
}

function getBytePdf(orderId) {
    var url = getOrderUrl() + "/getPdf/" + orderId;
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            var sampleArr = base64ToArrayBuffer(data);
            saveByteArray("Invoice_" + orderId, sampleArr);
        },
        error: function (err) {
            window.location.replace("http://localhost:9000/employee/ui/order");
        }
    });
}
function init() {
    orderId = getOrderId();


    if (isNaN(orderId)) {
        window.location.replace("http://localhost:9000/employee/ui/order");
    }
    getOrder(orderId);
    if (window.location.hash == '#checkout') {
        toast(true, "Order completed!");
    }
}
var orderId;
$(document).ready(init);
$(document).ready(getOrderItemListByOrderId);

