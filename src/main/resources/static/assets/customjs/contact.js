$(document).on("click", ".mydel", function () {
    delConcact(this);
})

function delConcact(obj) {
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
            type: "DELETE",
            url: "delcontact",
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
    m.find("#modify_name").val(tmp.parent().parent().children().eq(1).text());
    m.find("#modify_mail").val(tmp.parent().parent().children().eq(2).text());
    m.find("#modify_userid").val(tmp.parent().parent().children().eq(3).text());
    m.attr("name", tmp.parent().parent().children().eq(0).text());
    m.attr("index", tmp.parent().parent().data("index"));
}

function modifyContact(obj) {
    var tmp = $(obj);
    var index = tmp.parent().parent().parent().attr("index");
    var contactId = tmp.parent().parent().parent().attr("name");
    var contactName = tmp.parent().find("#modify_name").val();
    var contactMail = tmp.parent().find("#modify_mail").val();
    var formData = new FormData();
    formData.append("id", contactId);
    formData.append("name", contactName);
    formData.append("mail", contactMail);
    $.ajax({
        type: "POST",
        url: "modifycontact",
        data: formData,
        contentType: false,
        processData: false,
        datatype: "json",
        success: function (data) {
            if (data.success == true) {
                var content = {
                    id: contactId,
                    name: contactName,
                    mail: contactMail
                };
                //Bootstrap 异步修改
                $('#contact_table').bootstrapTable('updateRow', {
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
