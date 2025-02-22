// Validation
function validateForm() {
    if (document.getElementById("inputBrand").value == "") {
        toast(false, 'Brand must not be empty!');
    }
    else if (document.getElementById("inputCategory").value == "") {
        toast(false, 'Category must not be empty!');
    }
    else {
        return true;
    }
    return false;
}

// Utility



//BUTTON ACTIONS
function addBrand() {


    var $form = $("#brand-form");
    var json = toJson($form);
    var url = getBrandUrl();

    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            getBrandList();
            toast(true, "Successfully added!");
            resetInputBrand();
        },
        error: handleAjaxError
    });

    return false;
}
function resetInputBrand() {
    document.getElementById('inputBrand').value = '';
    document.getElementById('inputCategory').value = '';
}
function updateBrand(event) {
    //Get the ID
    var id = $("#brand-edit-form input[name=id]").val();
    var url = getBrandUrl() + "/" + id;

    //Set the values to update
    var $form = $("#brand-edit-form");
    var json = toJson($form);

    $.ajax({
        url: url,
        type: 'PUT',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            $('#edit-brand-modal').modal('toggle');
            toast(true, "Successfully pdated!");
            getBrandList();

        },
        error: handleAjaxError
    });

    return false;
}

function refreshBrandList() {
    getBrandList();
    toast(true, 'Refreshed!');
    resetInputBrand();
}

function getBrandList() {

    var url = getBrandUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayBrandList(data);
        },
        error: handleAjaxError
    });
}

function deleteBrand(id) {
    var url = getBrandUrl() + "/" + id;

    $.ajax({
        url: url,
        type: 'DELETE',
        success: function (data) {
            getBrandList();
        },
        error: handleAjaxError
    });
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData() {
    var file = $('#brandFile')[0].files[0];
    readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results) {
    fileData = results.data;
    uploadRows();
}

function uploadRows() {
    //Update progress
    updateUploadDialog();
    //If everything processed then return
    if (processCount == fileData.length) {
        if (errorData.length == 0) {
            displayUploadData();
            getBrandList();
            toast(true, 'All files successfully added!');
        } else if (errorData.length == processCount) {
            getBrandList();
            document.getElementById("download-errors").disabled = false;
            toast(false, "No data was added!");

        } else {
            getBrandList();
            document.getElementById("download-errors").disabled = false;
            toast(false, "Only some rows were added!");
        }
        return;
    }

    //Process next row
    var row = fileData[processCount];
    var row2 = {
        brand: row["Brand"],
        category: row["Category"]
    }
    processCount++;

    var json = JSON.stringify(row2);
    var url = getBrandUrl();

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
    writeFileData(errorData, "brand_error.tsv");
}

//UI DISPLAY METHODS

function displayBrandList(data) {
    var $tbody = $('#brand-table').find('tbody');
    $tbody.empty();
    data.sort(function (a, b) { return a.id - b.id; });
    data.reverse();
    for (var i in data) {
        var e = data[i];

        var buttonHtml = ' <button type="button" class="btn btn-secondary btn-sm" onclick="displayEditBrand(' + e.id + ')">Edit</button>'
        var row = '<tr>'
            + '<td>' + e.brand + '</td>'
            + '<td>' + e.category + '</td>'
            + '<td>' + buttonHtml + '</td>'
            + '</tr>';
        $tbody.append(row);
    }
}

function displayEditBrand(id) {
    var url = getBrandUrl() + "/" + id;
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayBrand(data);
        },
        error: handleAjaxError
    });
}

function resetUploadDialog() {
    //Reset file name
    var $file = $('#brandFile');
    $file.val('');
    $('#brandFileName').html("Choose File");
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
    var $file = $('#brandFile');
    var fileName = $file.val();
    fileName = fileName.substring(fileName.lastIndexOf('\\') + 1);
    $('#brandFileName').html(fileName);
}

function displayUploadData() {
    resetUploadDialog();
    $('#upload-brand-modal').modal('toggle');
}

function displayBrand(data) {
    $("#brand-edit-form input[name=brand]").val(data.brand);
    $("#brand-edit-form input[name=category]").val(data.category);
    $("#brand-edit-form input[name=id]").val(data.id);
    $('#edit-brand-modal').modal('toggle');
}


//INITIALIZATION CODE
function init() {

    $('#brand-form').submit(addBrand);
    $('#brand-edit-form').submit(updateBrand);


    $('#refresh-data').click(refreshBrandList);
    $('#upload-data').click(displayUploadData);
    $('#process-data').click(processData);
    $('#download-errors').click(downloadErrors);
    $('#brandFile').on('change', updateFileName);
    document.getElementById('brandFile').addEventListener('input', function (evt) {
        var file = $('#brandFile')[0].files[0];
        if (file.name != null) {
            document.getElementById("process-data").disabled = false;
        }
    });
}

$(document).ready(init);
$(document).ready(getBrandList);
