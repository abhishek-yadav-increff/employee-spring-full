
// function getOrderUrl() {
//     var baseUrl = $("meta[name=baseUrl]").attr("content")
//     return baseUrl + "/api/order";
// }

// function createNewOrder() {
//     var url = getOrderUrl();
//     $.ajax({
//         url: url,
//         type: 'POST',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         success: function (data) {
//             console.log(data)
//             window.location.replace("http://localhost:9000/employee/ui/orderEdit/" + data);

//         },
//         error: function (err) {

//         }
//     });

//     return false;
// }
// //INITIALIZATION CODE
// function init() {
//     $('#create-new-order-button').click(createNewOrder);

//     // $('#update-orderEdit').click(updateOrderEdit);
//     // $('#refresh-data').click(getOrderEditList);
//     // $('#upload-data').click(displayUploadData);
//     // $('#process-data').click(processData);
//     // $('#download-errors').click(downloadErrors);
//     // $('#orderEditFile').on('change', updateFileName)
// }
// var orderId;
// $(document).ready(init);
// // $(document).ready();
// // $(document).ready(getOrderEditList);

