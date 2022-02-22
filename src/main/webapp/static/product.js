
function getProductUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/product";
}

function getBrandUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/brand";
}
//BUTTON ACTIONS
function addProduct(event) {
    //Set the values to update
    var $form = $("#product-form");
    var json = toJson($form);
    var url = getProductUrl();
    console.log(json)
    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            getProductList();
        },
        error: handleAjaxError
    });

    return false;
}

function updateProduct(event) {
    $('#edit-product-modal').modal('toggle');
    var $selectBrand = document.getElementById("inputBrand");
    var $selectCategory = document.getElementById("inputCategory");
    // console.log("updating edit select");
    getBrandOptions($selectBrand.value, "inputEditBrand", "inputEditCategory");
    getBrandOptions($selectCategory.value, "inputEditBrand", "inputEditCategory");
    //Get the ID
    var id = $("#product-edit-form input[name=id]").val();
    var url = getProductUrl() + "/" + id;

    //Set the values to update
    var $form = $("#product-edit-form");

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
            getProductList();
        },
        error: handleAjaxError
    });

    return false;
}


function getProductList() {
    var url = getProductUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayProductList(data);
        },
        error: handleAjaxError
    });
}

function deleteProduct(id) {
    var url = getProductUrl() + "/" + id;

    $.ajax({
        url: url,
        type: 'DELETE',
        success: function (data) {
            getProductList();
        },
        error: handleAjaxError
    });
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData() {
    var file = $('#productFile')[0].files[0];
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
        return;
    }

    //Process next row
    var row = fileData[processCount];
    processCount++;

    var json = JSON.stringify(row);
    var url = getProductUrl();

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
            row.error = response.responseText
            errorData.push(row);
            uploadRows();
        }
    });

}

function downloadErrors() {
    writeFileData(errorData);
}

//UI DISPLAY METHODS

function displayProductList(data) {
    var $tbody = $('#product-table').find('tbody');
    $tbody.empty();
    for (var i in data) {
        var e = data[i];
        console.log(e);
        var buttonHtml = '<button onclick="deleteProduct(' + e.id + ')">delete</button>'
        buttonHtml += ' <button onclick="displayEditProduct(' + e.id + ')">edit</button>'
        var row = '<tr>'
            + '<td>' + e.barcode + '</td>'
            + '<td>' + e.brand + '</td>'
            + '<td>' + e.category + '</td>'
            + '<td>' + e.mrp + '</td>'
            + '<td>' + e.name + '</td>'
            + '<td>' + buttonHtml + '</td>'
            + '</tr>';
        $tbody.append(row);
    }
}

function displayEditProduct(id) {
    var url = getProductUrl() + "/" + id;
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayOrderItem(data);
        },
        error: handleAjaxError
    });
}

function resetUploadDialog() {
    //Reset file name
    var $file = $('#productFile');
    $file.val('');
    $('#productFileName').html("Choose File");
    //Reset various counts
    processCount = 0;
    fileData = [];
    errorData = [];
    //Update counts	
    updateUploadDialog();
}

function updateUploadDialog() {
    $('#rowCount').html("" + fileData.length);
    $('#processCount').html("" + processCount);
    $('#errorCount').html("" + errorData.length);
}

function updateFileName() {
    var $file = $('#productFile');
    var fileName = $file.val();
    $('#productFileName').html(fileName);
}

function displayUploadData() {
    resetUploadDialog();
    $('#upload-product-modal').modal('toggle');
}

function displayOrderItem(data) {
    $("#product-edit-form input[name=barcode]").val(data.barcode);
    $("#product-edit-form input[name=brand_category]").val(data.brand_category);
    $("#product-edit-form input[name=name]").val(data.name);
    $("#product-edit-form input[name=mrp]").val(data.mrp);
    $("#product-edit-form input[name=id]").val(data.id);
    $('#edit-product-modal').modal('toggle');
}

function getBrandOptions(category, brandElement, categoryElement) {
    console.log("in getBrandOptions :" + category)
    var which = "brand"
    if (category == "None of the options") {
        console.log("inside if")
        var url = getBrandUrl()
        which = "both"
    } else {
        var url = getBrandUrl() + "/byCategory/" + category;
    }
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            updateDropDowns(data, which, brandElement, categoryElement);
        },
        error: handleAjaxError
    });
}
function getCategoryOptions(brand, brandElement, categoryElement) {
    console.log("in getCategoryOptions :" + brand)

    var which = "category"
    if (brand == "None of the options") {
        var url = getBrandUrl()
        which = "both"
    } else {
        var url = getBrandUrl() + "/byBrand/" + brand;
    }
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            updateDropDowns(data, which, brandElement, categoryElement);
        },
        error: handleAjaxError
    });
}
function updateDropDowns(data, which, brandElement, categoryElement) {
    console.log(data)
    const categories = new Set();
    const brands = new Set();
    for (var i in data) {
        var e = data[i];
        brands.add(e.brand);
        categories.add(e.category);
    }
    brands.add("None of the options");
    categories.add("None of the options");
    if ((which == 'brand') || (which == "both")) {
        var selectBrand = document.getElementById(brandElement);
        removeOptions(selectBrand);
        brands.forEach(function (value) {
            var el = document.createElement("option");
            el.textContent = value;
            el.value = value;
            selectBrand.appendChild(el);
        })

    }
    if ((which == 'category') || (which == "both")) {
        var selectCategory = document.getElementById(categoryElement);
        removeOptions(selectCategory);
        categories.forEach(function (value) {
            var el = document.createElement("option");
            el.textContent = value;
            el.value = value;
            selectCategory.appendChild(el);
        })
    }


}
function removeOptions(selectElement) {
    var i, L = selectElement.options.length - 1;
    for (i = L; i >= 0; i--) {
        selectElement.remove(i);
    }
}
//INITIALIZATION CODE
function init() {
    $('#add-product').click(addProduct);
    $('#update-product').click(updateProduct);
    $('#refresh-data').click(getProductList);
    $('#upload-data').click(displayUploadData);
    $('#process-data').click(processData);
    $('#download-errors').click(downloadErrors);
    $('#productFile').on('change', updateFileName);

    var $selectBrand = document.getElementById("inputBrand");
    var $selectCategory = document.getElementById("inputCategory");
    $selectBrand.addEventListener("click", function () {
        var strUser = $selectBrand.value;
        console.log(strUser)
        getCategoryOptions(strUser, "inputBrand", "inputCategory");
        // $selectBrand.value = "None of the options";
    });
    $selectCategory.addEventListener("click", function () {
        var strUser = $selectCategory.value;
        getBrandOptions(strUser, "inputBrand", "inputCategory");
        // $selectCategory.value = strUser;
    });
    getBrandOptions($selectBrand.value, "inputBrand", "inputCategory");


    var $selectEditBrand = document.getElementById("inputEditBrand");
    var $selectEditCategory = document.getElementById("inputEditCategory");
    $selectEditBrand.addEventListener("click", function () {
        var strUser = $selectEditBrand.value;
        getCategoryOptions(strUser, "inputEditBrand", "inputEditCategory");
    });
    $selectEditCategory.addEventListener("click", function () {
        var strUser = $selectEditCategory.value;
        getBrandOptions(strUser, "inputEditBrand", "inputEditCategory");
    });
}

$(document).ready(init);

$(document).ready(getProductList);

