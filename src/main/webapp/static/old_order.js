// import { getCollapsableContent, getOrderItemContent } from './orderUtil.js';
function getOrderItemContent(e) {
    var retval = `<form class="form-inline" id="order-form">
        <div class="form-group">
            <label for="inputProductId" class="col-sm-2 col-form-label">Product Id</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="productId" id="inputProductId" placeholder="enter product id">
            </div>
        </div>
        <div class="form-group">
            <label for="inputQuantity" class="col-sm-2 col-form-label">Quantity</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="quantity" id="inputQuantity" placeholder="enter quantity">
            </div>
        </div>
        <div class="form-group">
            <label for="inputSellingPrice" class="col-sm-2 col-form-label">SellingPrice</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="sellingPrice" id="inputSellingPrice" placeholder="enter sellingPrice">
            </div>
        </div>
        <input type="hidden" name="id">
        <input type="hidden" name="orderId" value="`+ e.id + `">
        
        <button type="button" class="btn btn-primary" id="add-order-item" onClick="addOrderItem(` + e.id + `)">Add</button>
        &nbsp;
        <button type="button" class="btn btn-primary" id="refresh-order-item">Refresh</button>
				</form>`;
    return retval;
}
function getCollapsableContent(e, buttonColor, timeStr, orderItemContent) {
    retval = `<div class="row">
            <button type="button" class="btn btn-danger" onClick="deleteOrder(` + e.id + `)">
            Delete Order</button>
            <button type="button" class="btn btn-primary" onClick="sendOrder(` + e.id + `)">
            Send Order</button>
            <button type="button" class="`+ buttonColor + `" onClick="collapseDetails(` + e.id + `)" >
            `+ 'OrderId: ' + e.id + ' | ' + timeStr + `
            </button>
            <div id="collapsed-order-`+ e.id + `" class="collapse">
                `+ orderItemContent + `  
                <div id="collapsed-order-`+ e.id + `-display" > 
                <table class="table table-striped" id="order-item-table">
                    <thead >
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Product Id</th>
                            <th scope="col">Quantity</th>
                            <th scope="col">Selling Price</th>
                        </tr>
                    </thead>
                    <tbody id="order-item-table-tbody">
                    </tbody>
                </table>
                </div>
            </div>
        </div>`;
    return retval;
}
function getOrderItemUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/orderItem";
}

//BUTTON ACTIONS
function addOrderItem(idd) {
    //Set the values to update
    // console.log(idd)
    var $form = $("#collapsed-order-" + idd + " form:first-child");
    var json = toJson($form);
    // console.log($form)
    var url = getOrderItemUrl();

    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            getOrderItemList();
        },
        error: handleAjaxError
    });

    return false;
}



function getOrderItemList() {
    var url = getOrderItemUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayOrderItemList(data);
        },
        error: handleAjaxError
    });
}
function getOrderItemListByOrderId(orderId) {
    var url = getOrderItemUrl() + "/orderId/" + orderId;
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayOrderItemList(data);
        },
        error: handleAjaxError
    });
}

function deleteOrderItem(id, orderId) {
    var url = getOrderItemUrl() + "/" + id;

    $.ajax({
        url: url,
        type: 'DELETE',
        success: function (data) {
            getOrderItemListByOrderId(orderId);
        },
        error: handleAjaxError
    });
}
function displayEditOrderItem(id) {
    var url = getOrderItemUrl() + "/" + id;
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
    $("#orderItem-edit-form input[name=quantity]").val(data.quantity);
    $("#orderItem-edit-form input[name=productId]").val(data.productId);
    $("#orderItem-edit-form input[name=orderId]").val(data.orderId);
    $("#orderItem-edit-form input[name=sellingPrice]").val(data.sellingPrice);
    $("#orderItem-edit-form input[name=id]").val(data.id);
    $('#edit-orderItem-modal').modal('toggle');
}
//UI DISPLAY METHODS

function displayOrderItemList(data) {
    // $tbody.appendChild();
    console.log(data)
    for (var i in data) {
        var e = data[i];
        var tbody = document.getElementById('collapsed-order-' + e.orderId + '-display').querySelector("#order-item-table").querySelector("#order-item-table-tbody");
        tbody.innerHTML = "";
    }
    for (var i in data) {
        var e = data[i];
        var buttonHtml = '<button onclick="deleteOrderItem(' + e.id + ',' + e.orderId + ')">delete</button>';
        buttonHtml += ' <button onclick="displayEditOrderItem(' + e.id + ',' + e.orderId + ')">edit</button>';

        var rowHtml = `<tr>
            <td>`+ e.id + `</td>
            <td>`+ e.productId + `</td>
            <td>`+ e.quantity + `</td>
            <td>`+ e.sellingPrice + `</td>
            <td>`+ buttonHtml + `</td>
        </tr>`
        var tbody = document.getElementById('collapsed-order-' + e.orderId + '-display').querySelector("table").querySelector("tbody")
        tbody.insertAdjacentHTML('beforeend', rowHtml)
    }
    // $("table").each(function (i, v) {
    //     if ($(this).find("tbody").html().trim().length === 0) {
    //         $(this).hide()
    //     }
    // })
}
function updateOrderItem(event) {
    $('#edit-orderItem-modal').modal('toggle');
    //Get the ID
    var id = $("#orderItem-edit-form input[name=id]").val();
    var orderId = $("#orderItem-edit-form input[name=orderId]").val();
    var url = getOrderItemUrl() + "/" + id;

    //Set the values to update
    var $form = $("#orderItem-edit-form");
    var json = toJson($form);

    $.ajax({
        url: url,
        type: 'PUT',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            getOrderItemListByOrderId(orderId);
        },
        error: handleAjaxError
    });

    return false;
}
function getOrderUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/order";
}

//BUTTON ACTIONS
function addOrder(event) {
    //Set the values to update
    // var $form = $("#order-form");
    // var json = toJson($form);
    var url = getOrderUrl();

    $.ajax({
        url: url,
        type: 'POST',
        // data: json,
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
function collapseDetails(orderId) {
    $('#collapsed-order-' + orderId).collapse("toggle");
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
function sendOrder(id) {
    console.log("in sendOrder");
    var url = getOrderUrl() + "/sendOrder/" + id;
    var table = document
        .getElementById('collapsed-order-' + id + '-display')
        .querySelector("#order-item-table");
    // .querySelector("#order-item-table-tbody");
    var rCount = table.rows.length;
    // console.log('collapsed-order-' + id + '-display');
    console.log(rCount);
    for (var i = 1; i < rCount; i++) {

        console.log(table.tBodies[0])
        // var id = table.tBodies[0].rows[i].attr;
        // console.log(id)
        // var deptcode = table.rows[i].cells[1].children[0].value;
        // var name = table.rows[i].cells[2].children[0].value;
        // var role = table.rows[i].cells[3].children[0].value;
        // var desc = table.rows[i].cells[4].children[0].value;
        // var image = table.rows[i].cells[5].children[0].value;
        // var asdir = table.rows[i].cells[6].children[0].value;

    }

    $.ajax({
        url: url,
        type: 'PUT',
        success: function (data) {
            getOrderList();
        },
        error: handleAjaxError
    });
}
//UI DISPLAY METHODS

function displayOrderList(data) {
    var tbody = document.getElementById('collapsable-order')
    tbody.innerHTML = "";
    // $tbody.appendChild();
    for (var i in data) {
        var e = data[i];
        var timeStr = new Date(e.time).toLocaleString();
        console.log(e.complete)
        if (e.complete == 1) {
            buttonColor = 'btn btn-success';
            // $('#' + e.id + '').find('*').attr('disabled', true);
        }
        else {
            buttonColor = 'btn btn-warning';
        }
        orderItemContent = getOrderItemContent(e);
        collapsableContent = getCollapsableContent(e, buttonColor, timeStr, orderItemContent);
        tbody.insertAdjacentHTML('beforeend', collapsableContent);

        // $('#collapsed-order-' + e.id).click(getOrderItemListByOrderId(e.id));

        // if (e.complete == 1) {
        //     $('#' + e.id + '').find('*').attr('disabled', true);
        // }
        // $tbody.append(row);
    }
    $('.collapse').on('show.bs.collapse', function (e) {
        console.log(e.target.id);
        var orderId = e.target.id.split('-').at(-1);
        console.log(orderId);
        getOrderItemListByOrderId(orderId);
    });
    // getOrderItemList();
}



//INITIALIZATION CODE
function init() {
    $('#add-order').click(addOrder);
    $('#refresh-data').click(getOrderList);
    $('#update-orderItem').click(updateOrderItem);
    // $('#add-order-item').click(addOrderItem);
    // $('#refresh-order-item').click(getOrderItemList);
    // $('#collapsed-order-' + e.id).on('shown.bs.collapse', function () {
    //     getOrderItemListByOrderId(e.id);
    // });
}

$(document).ready(init);
$(document).ready(getOrderList);
// $('.collapse').on('show.bs.collapse', function (e) {
//     console.log(e.target.id);
// });
// $(document).ready(getOrderItemList);

