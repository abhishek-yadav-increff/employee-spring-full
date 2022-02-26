function validateForm() {
    var quantity = document.getElementById("inputQuantity").value;
    if (document.getElementById("inputProductBarcode").value == "") {
        toast(false, 'Barcode must not be empty!');
    }
    else if ((quantity == "") || (quantity <= 0)) {
        toast(false, 'Quantity must be positive!');
    }
    else {
        return true;
    }
    return false;
}


function getOrderEditUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/orderItem";
}

function getProductUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/product";
}
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
//BUTTON ACTIONS
function addOrderEdit(event) {
    //Set the values to update
    var $form = $("#orderEdit-form");
    var json = toJson($form);
    var url = getOrderEditUrl();
    console.log(json)
    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            getOrderEditList();
        },
        error: handleAjaxError
    });

    return false;
}


function getOrderEditList() {
    var url = getOrderEditUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayOrderEditList(data);
        },
        error: handleAjaxError
    });
}

function deleteOrder() {
    var url = getOrderUrl() + "/" + orderId;
    // console.log('inside deleteOrder');
    $.ajax({
        url: url,
        type: 'DELETE',
        success: function (data) {
            window.location.replace("http://localhost:9000/employee/ui/order");

        },
        error: handleAjaxError
    });
}
function sendOrder() {
    var url = getOrderUrl() + "/sendOrder/" + orderId;

    $.ajax({
        url: url,
        type: 'PUT',
        success: function (data) {
            window.location.replace("http://localhost:9000/employee/ui/orderPreview/" + orderId + "#checkout");

        },
        error: handleAjaxError
    });
}
//UI DISPLAY METHODS


function deleteOrderItem(id, orderId) {
    console.log("inside deleteOrderItem");
    var url = getOrderEditUrl() + "/" + id;

    $.ajax({
        url: url,
        type: 'DELETE',
        success: function (data) {
            getOrder(orderId);
        },
        error: handleAjaxError
    });
}


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
            getOrderItemListByOrderId(data.id)
        },
        error: function (err) {
            window.location.replace("http://localhost:9000/employee/ui/order");
        }
    });
}
function displayOrder(data) {
    var $headingDate = $('#headingDate');
    // var $id = document.getElementById('order-form').querySelector('input[name="id"]');
    var $orderIdElement = document.getElementById('order-form').querySelector('input[name="orderId"]');
    // console.log($headingDate)
    // console.log($orderIdElement)
    // console.log(data)
    var options = { year: 'numeric', month: 'short', day: 'numeric', hour: 'numeric', minute: '2-digit' };
    var timeStr = new Date(data.time).toLocaleString(undefined, options);

    $headingDate.text(timeStr);
    // $id.setAttribute('value', data.id);
    $orderIdElement.setAttribute('value', data.id);
}
function addOrderItem() {
    if (!validateForm()) { return; }

    var $form = $("#order-form");
    var json = toJson($form);
    console.log("in addOrderItem")
    console.log(json)
    var url = getOrderEditUrl();

    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            resetInputOrderEdit()
            getOrder(orderId);
            toast(true, 'Successfully added order item!');
        },
        error: handleAjaxError
    });

    return false;
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
function editOrderItem(id, orderId) {
    var url = getOrderEditUrl() + "/" + id;
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayOrderItem(data);
        },
        error: handleAjaxError
    });
}
function displayOrderItem(data) {

    $("#orderEdit-edit-form input[name=quantity]").val(data.quantity);
    $("#orderEdit-edit-form input[name=sellingPrice]").val(data.sellingPrice);
    $("#orderEdit-edit-form input[name=id]").val(data.id);
    $("#orderEdit-edit-form input[name=name]").val(data.name);
    $("#orderEdit-edit-form input[name=orderId]").val(data.orderId);
    $("#orderEdit-edit-form input[name=productBarcode]").val(data.productBarcode);
    document.getElementById('inputProductBarcodeEdit').innerHTML = data.productBarcode;
    document.getElementById('inputNameEdit').innerHTML = data.name;

    $('#edit-orderEdit-modal').modal('toggle');
}
function displayOrderItemList(data) {
    // $tbody.appendChild();
    var $tbody = $('#orderEdit-table').find('tbody');
    $tbody.empty();
    for (var i in data) {
        var e = data[i];
        console.log(e.id)
        var buttonHtml = ` <button type="button" class="btn btn-sm" style="color: rgb(0, 0, 0); background-color: #ffffff; border-color: #d9534f;" onclick="deleteOrderItem(` + e.id + `,` + e.orderId + `)">Delete</button>`
        buttonHtml += ' <button type="button" class="btn btn-secondary btn-sm"  onclick="editOrderItem(' + e.id + ',' + e.orderId + ')">Edit</button>'

        var row = '<tr>'
            + '<td>' + e.name + '</td>'
            + '<td>' + e.productBarcode + '</td>'
            + '<td>' + e.mrp + '</td>'
            + '<td>' + e.quantity + '</td>'
            + '<td>' + e.sellingPrice + '</td>'
            + '<td>' + buttonHtml + '</td>'
            + '</tr>';
        $tbody.append(row);
    }

}
function getProduct(evt) {
    iQE = String(evt.currentTarget.inputQuantityElement);
    iSPE = String(evt.currentTarget.inputSellingPriceElement);
    iPBE = String(evt.currentTarget.inputProductBarcodeElement);
    console.log(iQE, iSPE, iPBE);
    var $barcodeElement = document.getElementById(iPBE);
    var url = getProductUrl() + "/byBarcode/" + $barcodeElement.value;
    // console.log($barcodeElement.value);
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayProduct(data, iQE, iSPE);
        },
        error: handleAjaxError
    });
}
function displayProduct(data, inputQE, inputSPE) {
    var $productQuantityElement = document.getElementById(inputQE);
    // console.log($productQuantityElement.value);
    if (!isNaN($productQuantityElement.value)) {
        var $sellingPriceElement = document.getElementById(inputSPE);
        $sellingPriceElement.value = parseInt(data.mrp) * parseInt($productQuantityElement.value);
    }
}
function updateOrderEdit() {
    //Get the ID
    var id = $("#orderEdit-edit-form input[name=id]").val();
    var orderId = $("#orderEdit-edit-form input[name=orderId]").val();
    var url = getOrderEditUrl() + "/" + id;

    //Set the values to update
    var $form = $("#orderEdit-edit-form");
    var json = toJson($form);
    console.log(json)

    $.ajax({
        url: url,
        type: 'PUT',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            $('#edit-orderEdit-modal').modal('toggle');
            getOrder(orderId);
            toast(true, "Successfully updated order item!!");
        },
        error: handleAjaxError
    });

    return false;
}

function refreshOrderItemList() {
    getOrder(orderId);
    resetInputOrderEdit();
    toast(true, 'Refreshed!');
}
function resetInputOrderEdit() {
    document.getElementById('inputProductBarcode').value = '';
    document.getElementById('inputQuantity').value = '';

}
function init() {
    orderId = getOrderId();

    console.log(orderId);
    if (isNaN(orderId)) {
        window.location.replace("http://localhost:9000/employee/ui/order");
    }
    getOrder(orderId);
    if (window.location.hash == '#success') {
        toast(true, "Order created!");
    }
    $('#add-order-item').click(addOrderItem);
    $('#refresh-data').click(refreshOrderItemList);
    $('#update-orderEdit').click(updateOrderEdit);
}
var orderId;
$(document).ready(init);


