
function getOrderUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/order";
}

//BUTTON ACTIONS
function addOrder(event) {
    //Set the values to update
    var $form = $("#order-form");
    var json = toJson($form);
    var url = getOrderUrl();
    console.log(json)
    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            getOrderList();
        },
        error: handleAjaxError
    });

    return false;
}




function getOrderList() {
    var url = getOrderUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayOrderList(data);
        },
        error: handleAjaxError
    });
}

function deleteOrder(id) {
    var url = getOrderUrl() + "/" + id;

    $.ajax({
        url: url,
        type: 'DELETE',
        success: function (data) {
            getOrderList();
        },
        error: handleAjaxError
    });
}


//UI DISPLAY METHODS
function editOrder(orderId) {
    window.location.replace("http://localhost:9000/employee/ui/orderEdit/" + orderId + "#edit");

}
function displayOrderList(data) {
    var $tbody = $('#order-table').find('tbody');
    $tbody.empty();
    for (var i in data) {
        var e = data[i];
        console.log(e.cost);
        var invoiceButtonHtml = ' <button type="button" class="btn btn-secondary btn-sm" onclick="showInvoice(' + e.id + ')">Invoice</button>'
        var editButtonHtml = ' <button type="button" class="btn btn-secondary btn-sm" onclick="editOrder(' + e.id + ')">Edit</button>'
        var options = { year: 'numeric', month: 'short', day: 'numeric', hour: 'numeric', minute: '2-digit' };
        var timeStr = new Date(e.time).toLocaleString(undefined, options);
        var row = '<tr>'
            + '<td> <a href="http://localhost:9000/employee/ui/orderPreview/' + e.id + '">' + e.id + '</a> </td>'
            + '<td>' + timeStr + '</td>'
            + '<td>' + e.cost.toFixed(2) + '</td>'
            + '<td>' + ((e.complete == 1) ? "Completed" : "In Progress") + '</td>'
            + '<td>' + ((e.complete == 1) ? invoiceButtonHtml : editButtonHtml) + '</td>'
            + '</tr>';
        $tbody.append(row);
    }
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
function showInvoice(orderId) {
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
function createNewOrder() {
    var url = getOrderUrl();

    $.ajax({
        url: url,
        type: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (data) {
            console.log(data)
            window.location.replace("http://localhost:9000/employee/ui/orderEdit/" + data + "#success");
        },
        error: function (err) {
        }
    });

    return false;
}



//INITIALIZATION CODE
function init() {
    $('#create-new-order-button').click(createNewOrder);

    // $('#add-order').click(addOrder);
    // $('#update-order').click(updateOrder);
    // $('#refresh-data').click(getOrderList);
}

$(document).ready(init);
$(document).ready(getOrderList);

