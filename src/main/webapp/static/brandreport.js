function getBrandUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/brand";
}
function getData(brand, category) {
    var url;
    if (!brand && !category) {
        console.log("byall")
        url = getBrandUrl();
    } else if (!brand) {
        console.log("bycat")
        url = getBrandUrl() + "/byCategory/" + category;
    } else if (!category) {
        console.log("bybrand")

        url = getBrandUrl() + "/byBrand/" + brand;
    } else {
        console.log("byboth")
        url = getBrandUrl() + "/search/" + brand + "/" + category;
    }
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayBrandCategoryList(data);
        },
        error: function (err) {
            getData(null, null)
        },
    });
}
function displayBrandCategoryList(data) {
    var $tbody = $('#brand-table').find('tbody');
    $tbody.empty();
    for (var i in data) {
        var e = data[i];
        var row = '<tr>'
            + '<td>' + e.brand + '</td>'
            + '<td>' + e.category + '</td>'
            + '</tr>';
        $tbody.append(row);
    }
}

function display() {
    brand = document.getElementById("inputBrand").value;
    category = document.getElementById("inputCategory").value;
    console.log("listening", brand, category);
    getData(brand, category);
}
function init() {
    document.getElementById('inputBrand').addEventListener('input', function (evt) {
        display();
    });
    document.getElementById('inputCategory').addEventListener('input', function (evt) {
        display();
    });
}
$(document).ready(init);