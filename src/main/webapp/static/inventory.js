
function getInventoryUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/inventory";
}

//BUTTON ACTIONS
function addInventory(event) {
    //Set the values to update
    var $form = $("#inventory-form");
    var json = toJson($form);
    var url = getInventoryUrl();

    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            getInventoryList();
            $.toast({
                heading: 'Success',
                text: 'Successfully added inventory!',
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

function updateInventory(event) {
    //Get the ID
    var barcode = $("#inventory-edit-form input[name=barcode]").val();
    var url = getInventoryUrl() + "/" + barcode;

    //Set the values to update
    var $form = $("#inventory-edit-form");
    var json = toJson($form);

    $.ajax({
        url: url,
        type: 'PUT',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            getInventoryList();
            $.toast({
                heading: 'Success',
                text: 'Successfully updated inventory!',
                // showHideTransition: 'slide',
                hideAfter: 3000,
                allowToastClose: true,
                position: 'top-right',
                icon: 'success'
            });
            $('#edit-inventory-modal').modal('toggle');
        },
        error: handleAjaxError
    });

    return false;
}


function getInventoryList() {
    var url = getInventoryUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayInventoryList(data);
        },
        error: handleAjaxError
    });
}

function deleteInventory(barcode) {
    var url = getInventoryUrl() + "/" + barcode;
    console.log('inside deleteInventory');
    $.ajax({
        url: url,
        type: 'DELETE',
        success: function (data) {
            getInventoryList();
            $.toast({
                heading: 'Success',
                text: 'Successfully deleted inventory!',
                // showHideTransition: 'slide',
                hideAfter: 3000,
                allowToastClose: true,
                position: 'top-right',
                icon: 'success'
            });
        },
        error: handleAjaxError
    });
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData() {
    var file = $('#inventoryFile')[0].files[0];
    readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results) {
    fileData = results.data;
    uploadRows();
}

function uploadRows() {
    //Update progress
    updateUploadDialog();

    if (processCount == fileData.length) {
        if (errorData.length == 0) {
            displayUploadData();
            getInventoryList();
            $.toast({
                heading: 'Success',
                text: 'All files successfully added!',
                showHideTransition: 'slide',
                hideAfter: 3000,
                allowToastClose: true,
                position: 'top-right',
                icon: 'success'
            });
        } else if (errorData.length == processCount) {
            $.toast({
                heading: 'Error',
                text: 'No data was added!',
                // showHideTransition: 'slide',
                hideAfter: false,
                allowToastClose: true,
                position: 'top-right',
                icon: 'error'
            });
            getInventoryList();
            document.getElementById("download-errors").disabled = false;

        } else {
            $.toast({
                heading: 'Warning',
                text: 'Only some rows were added!',
                // showHideTransition: 'slide',
                hideAfter: false,
                allowToastClose: true,
                position: 'top-right',
                icon: 'warning'
            });
            document.getElementById("download-errors").disabled = false;
        }
        return;
    }
    //If everything processed then return
    if (processCount == fileData.length) {
        return;
    }

    //Process next row
    var row = fileData[processCount];
    var row2 = {
        barcode: row["Barcode"],
        quantity: row["Quantity"],
    }
    processCount++;

    var json = JSON.stringify(row2);
    var url = getInventoryUrl();

    //Make ajax call
    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            uploadRows();
        },
        error: function (response) {
            var jsonError = JSON.parse(response.responseText)
            row.Error = jsonError.message
            errorData.push(row);
            uploadRows();
        }
    });

}

function downloadErrors() {
    writeFileData(errorData, "inventory_error.tsv");
}

//UI DISPLAY METHODS

function displayInventoryList(data) {
    var $tbody = $('#inventory-table').find('tbody');
    $tbody.empty();
    for (var i in data) {
        var e = data[i];
        var buttonHtml = ' <button type="button" class="btn btn-secondary btn-sm" onclick="displayEditInventory(\'' + e.barcode + '\')">Edit</button>'
        var row = '<tr>'
            + '<td>' + e.barcode + '</td>'
            + '<td>' + e.name + '</td>'
            + '<td>' + e.quantity + '</td>'
            + '<td>' + buttonHtml + '</td>'
            + '</tr>';
        $tbody.append(row);
    }
}

function displayEditInventory(barcode) {
    var url = getInventoryUrl() + "/" + barcode;
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayInventory(data);
        },
        error: handleAjaxError
    });
}

function resetUploadDialog() {
    //Reset file name
    var $file = $('#inventoryFile');
    $file.val('');
    $('#inventoryFileName').html("Choose File");
    //Reset various counts
    processCount = 0;
    fileData = [];
    errorData = [];
    //Update counts	
    updateUploadDialog();
    document.getElementById("process-data").disabled = true;
    document.getElementById("download-errors").disabled = true;
}

function updateUploadDialog() {
    $('#rowCount').html("" + fileData.length);
    $('#processCount').html("" + processCount);
    $('#errorCount').html("" + errorData.length);
}

function updateFileName() {
    var $file = $('#inventoryFile');
    var fileName = $file.val();
    $('#inventoryFileName').html(fileName);
}

function displayUploadData() {
    resetUploadDialog();
    $('#upload-inventory-modal').modal('toggle');
}

function displayInventory(data) {
    console.log(data, "in displayInventory")
    $("#inventory-edit-form input[name=quantity]").val(data.quantity);
    $("#inventory-edit-form input[name=barcode]").val(data.barcode);
    document.getElementById('inputEditBarcode').innerHTML = data.barcode;

    $('#edit-inventory-modal').modal('toggle');
}
function refreshInventoryList() {
    getInventoryList();
    $.toast({
        heading: 'Success',
        text: 'Refreshed!',
        // showHideTransition: 'slide',
        hideAfter: 3000,
        allowToastClose: true,
        position: 'top-right',
        icon: 'success'
    });
    resetInputLabel();
}
function resetInputLabel() {
    document.getElementById('inputBarcode').value = '';
    document.getElementById('inputQuantity').value = '';
}
//INITIALIZATION CODE
function init() {
    $('#add-inventory').click(addInventory);
    $('#update-inventory').click(updateInventory);
    $('#refresh-data').click(refreshInventoryList);
    $('#upload-data').click(displayUploadData);
    $('#process-data').click(processData);
    $('#download-errors').click(downloadErrors);
    $('#inventoryFile').on('change', updateFileName)
    document.getElementById('inventoryFile').addEventListener('input', function (evt) {
        var file = $('#inventoryFile')[0].files[0];
        if (file.name != null) {
            document.getElementById("process-data").disabled = false;
        }
    });
}

$(document).ready(init);
$(document).ready(getInventoryList);

