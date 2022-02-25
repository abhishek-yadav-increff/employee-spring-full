


function getOrderEditUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/orderItem";
}

function getProductUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/product";
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
    // console.log('inside sendOrder');

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

// function displayOrderEditList(data) {
//     var $tbody = $('#orderEdit-table').find('tbody');
//     $tbody.empty();
//     for (var i in data) {
//         var e = data[i];
//         var buttonHtml = '<button onclick="deleteOrderEdit(' + e.id + ')">delete</button>'
//         // buttonHtml += ' <button onclick="displayEditOrderEdit(' + e.id + ')">edit</button>'
//         var row = '<tr>'
//             + '<td>' + e.id + '</td>'
//             + '<td>' + e.barcode + '</td>'
//             + '<td>' + e.brand_category + '</td>'
//             + '<td>' + e.mrp + '</td>'
//             + '<td>' + e.name + '</td>'
//             + '<td>' + buttonHtml + '</td>'
//             + '</tr>';
//         $tbody.append(row);
//     }
// }


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
            // $.toast({
            //     heading: 'Failed to load Edit Order Page',
            //     text: "The provided Order ID: " + orderId + ", doesn't exists",
            //     showHideTransition: 'slide',
            //     hideAfter: false,
            //     allowToastClose: true,
            //     position: 'top-right',
            //     icon: 'error'
            // });
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
    var timeStr = new Date(data.time).toLocaleString();

    $headingDate.text(timeStr);
    // $id.setAttribute('value', data.id);
    $orderIdElement.setAttribute('value', data.id);
}
function addOrderItem() {
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
            getOrder(orderId);
            $.toast({
                heading: 'Success',
                text: 'Successfully added order item!',
                // showHideTransition: 'slide',
                hideAfter: 3000,
                allowToastClose: true,
                position: 'top-right',
                icon: 'success'
            });
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
    // console.log(data, "in displayInventory")
    $("#orderEdit-edit-form input[name=quantity]").val(data.quantity);
    $("#orderEdit-edit-form input[name=sellingPrice]").val(data.sellingPrice);
    $("#orderEdit-edit-form input[name=id]").val(data.id);
    $("#orderEdit-edit-form input[name=orderId]").val(data.orderId);
    $("#orderEdit-edit-form input[name=productBarcode]").val(data.productBarcode);
    document.getElementById('inputProductBarcodeEdit').innerHTML = data.productBarcode;

    $('#edit-orderEdit-modal').modal('toggle');
}
function displayOrderItemList(data) {
    // $tbody.appendChild();
    var $tbody = $('#orderEdit-table').find('tbody');
    $tbody.empty();
    for (var i in data) {
        var e = data[i];
        console.log(e.id)
        var buttonHtml = ' <button type="button" class="btn btn-warning btn-sm"  onclick="deleteOrderItem(' + e.id + ',' + e.orderId + ')">Delete</button>'
        buttonHtml += ' <button type="button" class="btn btn-secondary btn-sm"  onclick="editOrderItem(' + e.id + ',' + e.orderId + ')">Edit</button>'

        var row = '<tr>'
            + '<td>' + e.productBarcode + '</td>'
            + '<td>' + e.quantity.toFixed(2) + '</td>'
            + '<td>' + e.sellingPrice + '</td>'
            + '<td>' + buttonHtml + '</td>'
            + '</tr>';
        $tbody.append(row);
    }
    // $("table").each(function (i, v) {
    //     if ($(this).find("tbody").html().trim().length === 0) {
    //         $(this).hide()
    //     }
    // })
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
    $('#edit-orderEdit-modal').modal('toggle');
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
            getOrder(orderId);
        },
        error: handleAjaxError
    });

    return false;
}
//INITIALIZATION CODE
// function setEventListeners(inputProductBarcode, inputQuantity, inputSellingPrice) {
//     document.getElementById(inputProductBarcode).addEventListener('change', getProduct);
//     document.getElementById(inputProductBarcode).inputQuantityElement = inputQuantity;
//     document.getElementById(inputProductBarcode).inputSellingPriceElement = inputSellingPrice;
//     document.getElementById(inputProductBarcode).inputProductBarcodeElement = inputProductBarcode;
//     document.getElementById(inputQuantity).addEventListener('change', getProduct);
//     document.getElementById(inputQuantity).inputQuantityElement = inputQuantity;
//     document.getElementById(inputQuantity).inputSellingPriceElement = inputSellingPrice;
//     document.getElementById(inputQuantity).inputProductBarcodeElement = inputProductBarcode;
// }
function refreshOrderItemList() {
    getOrder(orderId);
    resetInputLabel();
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
function resetInputLabel() {
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
        $.toast({
            heading: 'Success',
            text: "Order created!",
            showHideTransition: 'slide',
            hideAfter: 3000,
            allowToastClose: true,
            position: 'top-right',
            icon: 'success'
        });
    }
    $('#add-order-item').click(addOrderItem);
    $('#refresh-data').click(refreshOrderItemList);
    // setEventListeners('inputProductBarcode', 'inputQuantity', 'inputSellingPrice');
    // setEventListeners('inputProductBarcodeEdit', 'inputQuantityEdit', 'inputSellingPriceEdit');
    $('#update-orderEdit').click(updateOrderEdit);
    // $('#refresh-data').click(getOrderEditList);
    // $('#upload-data').click(displayUploadData);
    // $('#process-data').click(processData);
    // $('#download-errors').click(downloadErrors);
    // $('#orderEditFile').on('change', updateFileName)
}
var orderId;
$(document).ready(init);


