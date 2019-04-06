$(document).on("click", ".mydel", function () {
    delPermission(this);
})

function addPermission() {
    var formData = new FormData();
    var name = $("#per_name").val();
    var url = $("#per_url").val();
    formData.append("name", name);
    formData.append("url", url);
    $.ajax({
        type: "POST",
        url: "addper",
        data: formData,
        contentType: false,
        processData: false,
        datatype: "json",
        success: function (data) {
            if (data.success == true) {
                var content = {   //要插入的数据，这里要和table列名一致
                        id: data.result.toString(),
                        url: url,
                        name: name,
                        options: "<button onclick=\"modify(this)\" type=\"button\" class=\"btn btn-rounded waves-effect waves-light\" style=\"background-color: #73cab8; color: #FFFFFF\"> 修改\n" +
                        "                                        </button>\n" +
                        "<button type=\"button\" class=\"btn btn-rounded waves-effect waves-light mydel\" style=\"background-color: #6eadec; color: #FFFFFF\"> 删除\n" +
                        "                                        </button>"
                    }
                ;
                //Bootstrap 异步添加
                $('#per_table').bootstrapTable('insertRow', {
                    index: $('#per_table').bootstrapTable('getData').length,
                    row: content
                });
                //关闭窗口
                Custombox.close();
                //成功弹窗
                alertMsg("添加成功", "success", 1000);
            }
            else {
                //失败弹窗
                alertMsg(data.msg, "warning", 1000);
            }
        },
        error: function () {
            alertMsg("请求出错处理", "warning", 1000);
        }
    });
}

function delPermission(obj) {
    var tmp = $(obj);
    // tmp.click(function () {
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
            type: "DELETE",
            url: "delper",
            data: formData,
            contentType: false,
            processData: false,
            datatype: "text",
            success: function (data) {
                if (data == "success") {
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
    // })
}

function modify(obj) {
    var tmp = $(obj);
    Custombox.open({
        target: "#custom-modify",
        effect: "fadein",
        overlaySpeed: "200",
        overlayColor: "#36404a"
    });
    var m = $("#custom-modify");
    m.find("#modify_url").val(tmp.parent().parent().children().eq(1).text());
    m.find("#modify_name").val(tmp.parent().parent().children().eq(2).text());
    m.attr("name", tmp.parent().parent().children().eq(0).text());
    m.attr("index", tmp.parent().parent().data("index"));
}

function modifyPermission(obj) {
    var tmp = $(obj);
    var index = tmp.parent().parent().parent().attr("index");
    var perId = tmp.parent().parent().parent().attr("name");
    var perName = tmp.parent().find("#modify_name").val();
    var perUrl = tmp.parent().find("#modify_url").val();
    var formData = new FormData();
    formData.append("id", perId);
    formData.append("name", perName);
    formData.append("url", perUrl);
    $.ajax({
        type: "POST",
        url: "modifyper",
        data: formData,
        contentType: false,
        processData: false,
        datatype: "json",
        success: function (data) {
            if (data.success == true) {
                var content = {   //要插入的数据，这里要和table列名一致
                    id: perId,
                    url: perUrl,
                    name: perName,
                };
                //Bootstrap 异步修改
                $('#per_table').bootstrapTable('updateRow', {
                    index: index,
                    row: content
                });
                //关闭窗口
                Custombox.close();
                //成功弹窗
                alertMsg("修改成功", "success", 1000);
            }
            else {
                alertMsg(data.msg, "warning", 1000);
            }
        },
        error: function () {
            alertMsg("请求出错处理", "warning", 1000);
        }
    })
}
