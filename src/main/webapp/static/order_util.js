function alertMessage(message, color) {
    return '<div class="' + color + '" role="alert" id="alert-box">' + message + '</div>';
}
function runEffect() {
    var selectedEffect = 'blind';
    var options = {};
    $("#successMessage").hide(selectedEffect, options, 500);
};
function showToast(message, color) {

    // $('#alert-box').delay(5000).fadeOut('slow');
    // $.toaster({ settings: { 'timeout': 5000 } });
    // var toastElList = [].slice.call(document.querySelectorAll('.toast'))
    // var toastList = toastElList.map(function (toastEl) {
    //     return new bootstrap.Toast(toastEl)
    // })
    // toastList.forEach(toast => toast.show())



    showToastMessage("Guest added successfully test2")
        .delay(5000)
        .fadeOut(4000);
}
export { showToast };