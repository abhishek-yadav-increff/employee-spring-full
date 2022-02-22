
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

function updateOrder(event) {
    $('#edit-order-modal').modal('toggle');
    //Get the ID
    var id = $("#order-edit-form input[name=id]").val();
    var url = getOrderUrl() + "/" + id;

    //Set the values to update
    var $form = $("#order-edit-form");
    var json = toJson($form);

    $.ajax({
        url: url,
        type: 'PUT',
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
    window.location.replace("http://localhost:9000/employee/ui/orderEdit/" + orderId);

}
function displayOrderList(data) {
    var $tbody = $('#order-table').find('tbody');
    $tbody.empty();
    for (var i in data) {
        var e = data[i];
        console.log(e.id)
        var invoiceButtonHtml = '<button onclick="showInvoice(' + e.id + ')">invoice</button>'
        var editButtonHtml = ' <button onclick="editOrder(' + e.id + ')">edit</button>'
        var row = '<tr>'
            + '<td> <a href="http://localhost:9000/employee/ui/orderPreview/' + e.id + '">' + e.id + '</a> </td>'
            + '<td>' + e.time + '</td>'
            + '<td>' + e.cost + '</td>'
            + '<td>' + ((e.complete == 1) ? "Completed" : "In Progress") + '</td>'
            + '<td>' + ((e.complete == 1) ? invoiceButtonHtml : editButtonHtml) + '</td>'
            + '</tr>';
        $tbody.append(row);
    }
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
            window.location.replace("http://localhost:9000/employee/ui/orderEdit/" + data);

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

