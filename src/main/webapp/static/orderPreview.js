


function getOrderEditUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/orderItem";
}
//BUTTON ACTIONS


//UI DISPLAY METHODS



function getOrderId() {
    var tempArr = document.getElementById('orderIdSelect').innerHTML.split(" ");
    console.log(tempArr);
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


            // setTimesout(function () { alert("Order ID doesn't exist!"); }, 10000);
            // console.log(alertMessage("asd"));
            // alert("Order ID doesn't exist!")
            // $.toaster({ priority: 'danger', title: 'Redirected', message: "The given Order ID doesn't exist" });

        }
    });
}
function displayButton(data) {
    if (data.complete == 1) {
        document.getElementById('buttonElement').innerHTML = `
        <button type="button" class="btn btn-primary" id="invoiceButton">Invoice</button>`;
        document.getElementById("invoiceButton").onclick = function () {
            window.location.replace("http://localhost:9000/employee/ui/order");
        };

    } else {
        document.getElementById('buttonElement').innerHTML = `
        <button type="button" class="btn btn-warning" id="editButton">Edit</button>`;
        document.getElementById("editButton").onclick = function () {
            window.location.replace("http://localhost:9000/employee/ui/orderEdit/" + data.id);
        };
        console.log("http://localhost:9000/employee/ui/orderEdit/" + data.id);
    }
}
function displayOrder(data) {
    var options = { year: 'numeric', month: 'short', day: 'numeric', hour: 'numeric', minute: '2-digit' };
    var timeStr = new Date(data.time).toLocaleString(undefined, options);
    var $headingDate = $('#headingDate');
    $headingDate.text(timeStr);
}

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
    $label.innerHTML = "Cost: " + data.cost;
}


function displayOrderItemList(data) {
    var $tbody = $('#orderPreview-table').find('tbody');
    $tbody.empty();
    for (var i in data) {
        var e = data[i];
        console.log(e.id)
        var row = '<tr>'
            + '<td>' + e.productBarcode + '</td>'
            + '<td>' + e.quantity + '</td>'
            + '<td>' + e.sellingPrice + '</td>'
            + '</tr>';
        $tbody.append(row);
    }
}


function init() {
    orderId = getOrderId();

    // console.log(orderId);
    if (isNaN(orderId)) {
        window.location.replace("http://localhost:9000/employee/ui/order");
    }
    getOrder(orderId);

}
var orderId;
$(document).ready(init);
$(document).ready(getOrderItemListByOrderId);

