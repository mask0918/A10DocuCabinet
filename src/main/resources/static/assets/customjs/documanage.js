$(document).on("click", ".mydel", function () {
    delFile(this);
})

function delFile(obj) {
    var tmp = $(obj);
    var temp = tmp.parent().parent();
    var id = temp.children().eq(0).text();
    var formData = new FormData();
    formData.append("id", id);
    swal({
        title: "确认要删除吗？",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Yes, delete it!",
        closeOnConfirm: false
    }, function () {
        $.ajax({
            type: "POST",
            url: "deletefile",
            data: formData,
            contentType: false,
            processData: false,
            datatype: "text",
            success: function (data) {
                if (data.success == true) {
                    alertMsg("删除成功", "success", 1000);
                    //Bootstrap 异步删除
                    $('#per_table').bootstrapTable('remove', {
                        field: 'id',
                        values: [id]
                    });
                }
            },
            error: function () {
                alert("请求出错处理");
            }
        });
    });
}

function handleJSON(obj, str) {
    var tmp = $(obj);
    Custombox.open({
        target: "#custom-modify",
        effect: "fadein",
        overlaySpeed: "200",
        overlayColor: "#36404a"
    });
    var m = $("#custom-modify");
    var jdata = JSON.stringify(JSON.parse(str), null, 4);
    m.find("#tt").html("<pre>" + jdata + "</pre>");
}