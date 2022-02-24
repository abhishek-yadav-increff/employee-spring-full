
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
            $.toast({
                heading: 'Success',
                text: 'Successfully added product!',
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

async function updateProduct(event) {
    $('#edit-product-modal').modal('toggle');

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
            $.toast({
                heading: 'Success',
                text: 'Successfully updated product!',
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
            $.toast({
                heading: 'Success',
                text: 'Successfully deleted product!',
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
    if (processCount == fileData.length) {
        if (errorData.length == 0) {
            displayUploadData();
            getProductList();
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
            getProductList();
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
        brand: row["Brand"],
        category: row["Category"],
        mrp: row["MRP"],
        name: row["Name"]
    }
    processCount++;

    var json = JSON.stringify(row2);
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
            var jsonError = JSON.parse(response.responseText)
            row.Error = jsonError.message

            errorData.push(row);
            uploadRows();
        }
    });

}

function downloadErrors() {
    writeFileData(errorData, "product_error.tsv");
}

//UI DISPLAY METHODS

function displayProductList(data) {
    var $tbody = $('#product-table').find('tbody');
    $tbody.empty();
    for (var i in data) {
        var e = data[i];
        console.log(e);
        var buttonHtml = ' <button type="button" class="btn btn-secondary btn-sm" onclick="displayEditProduct(' + e.id + ')">Edit</button>'

        var row = '<tr>'
            + '<td>' + e.barcode + '</td>'
            + '<td id="brandForEdit' + e.id + '">' + e.brand + '</td>'
            + '<td id="categoryForEdit' + e.id + '">' + e.category + '</td>'
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
    document.getElementById("process-data").disabled = true;
    document.getElementById("download-errors").disabled = true;
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

async function displayOrderItem(data) {
    $("#product-edit-form input[name=barcode]").val(data.barcode);
    $("#product-edit-form input[name=brand]").val(data.brand);
    $("#product-edit-form input[name=category]").val(data.category);
    $("#product-edit-form input[name=name]").val(data.name);
    $("#product-edit-form input[name=mrp]").val(data.mrp);
    $("#product-edit-form input[name=id]").val(data.id);
    document.getElementById('inputEditBrand').innerHTML = data.brand;
    document.getElementById('inputEditCategory').innerHTML = data.category;

    // var $selectBrand = document.getElementById("inputBrand");
    // // var $selectCategory = document.getElementById("inputCategory");
    // // console.log("updating edit select");
    // await getBrandOptions("None of the options", "inputEditBrand", "inputEditCategory");
    // console.log("inting edit by brand" + $selectBrand.value)
    // document.getElementById("inputEditBrand").value = $selectBrand.value;
    // console.log("edit initial brand set as :" + document.getElementById("inputEditBrand").value);
    // getCategoryOptions($selectBrand.value, "inputEditBrand", "inputEditCategory");
    $('#edit-product-modal').modal('toggle');
}

async function getBrandOptions(category, brandElement, categoryElement) {
    console.log("in getBrandOptions :" + category)
    var which = "brand"
    if (category == "None of the options") {
        console.log("inside if")
        var url = getBrandUrl()
        which = "both"
    } else {
        var url = getBrandUrl() + "/byCategory/" + category;
    }
    await $.ajax({
        url: url,
        type: 'GET',
        success: async function (data) {
            await updateDropDowns(data, which, brandElement, categoryElement);
        },
        error: handleAjaxError
    });
}
async function getCategoryOptions(brand, brandElement, categoryElement) {
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
async function updateDropDowns(data, which, brandElement, categoryElement) {
    console.log(data)
    const categories = new Set();
    const brands = new Set();
    for (var i in data) {
        var e = data[i];
        brands.add(e.brand);
        categories.add(e.category);
    }
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
function refreshProductList() {
    getProductList();
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
async function resetInputLabel() {
    var $selectBrand = document.getElementById("inputBrand");

    await getBrandOptions("None of the options", "inputBrand", "inputCategory");
    // console.log("initing categories by brand: " + $selectBrand.value);
    getCategoryOptions($selectBrand.value, "inputBrand", "inputCategory");
    document.getElementById('inputMrp').value = '';
    document.getElementById('inputName').value = '';

}
//INITIALIZATION CODE
async function init() {
    $('#add-product').click(addProduct);
    $('#update-product').click(updateProduct);
    $('#refresh-data').click(refreshProductList);
    $('#upload-data').click(displayUploadData);
    $('#process-data').click(processData);
    $('#download-errors').click(downloadErrors);
    $('#productFile').on('change', updateFileName);

    var $selectBrand = document.getElementById("inputBrand");
    $selectBrand.addEventListener("change", function () {
        var strUser = $selectBrand.value;
        console.log(strUser)
        getCategoryOptions(strUser, "inputBrand", "inputCategory");
    });
    // $selectCategory.addEventListener("click", function () {
    //     var strUser = $selectCategory.value;
    //     getBrandOptions(strUser, "inputBrand", "inputCategory");
    //     // $selectCategory.value = strUser;
    // });
    await getBrandOptions("None of the options", "inputBrand", "inputCategory");
    console.log("initing categories by brand: " + $selectBrand.value);
    getCategoryOptions($selectBrand.value, "inputBrand", "inputCategory");

    var $selectEditBrand = document.getElementById("inputEditBrand");
    $selectEditBrand.addEventListener("change", function () {
        var strUser = $selectEditBrand.value;
        getCategoryOptions(strUser, "inputEditBrand", "inputEditCategory");
    });
    // $selectEditCategory.addEventListener("click", function () {
    //     var strUser = $selectEditCategory.value;
    //     getBrandOptions(strUser, "inputEditBrand", "inputEditCategory");
    // });
    document.getElementById('productFile').addEventListener('input', function (evt) {
        var file = $('#productFile')[0].files[0];
        if (file.name != null) {
            document.getElementById("process-data").disabled = false;
        }
    });
}

$(document).ready(init);

$(document).ready(getProductList);

