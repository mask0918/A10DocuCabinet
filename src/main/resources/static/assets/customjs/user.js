$(document).on("click", ".mydel", function () {
    delUser(this);
})

function addUser() {
    var formData = new FormData();
    var name = $("#user_name").val();
    var smtp = $("#user_smtp").val();
    var pop3 = $("#user_pop3").val();
    var acc = $("#user_acc").val();
    var pwd = $("#user_pwd").val();
    var role = $(".roleadd").selectpicker("val");
    formData.append("name", name);
    formData.append("smtp", smtp);
    formData.append("pop3", pop3);
    formData.append("acc", acc);
    formData.append("pwd", pwd);
    formData.append("roleid", role);
    $.ajax({
        type: "POST",
        url: "adduser",
        data: formData,
        contentType: false,
        processData: false,
        datatype: "json",
        success: function (data) {
            if (data.success == true) {
                var content = {   //要插入的数据，这里要和table列名一致
                    id: data.result.toString(),
                    name: name,
                    smtp: smtp,
                    pop3: pop3,
                    acc: acc,
                    pwd: pwd,
                    role: $(".roleadd").find("option:selected").text(),
                    options: "<button onclick=\"modify(this)\" type=\"button\" class=\"btn btn-rounded waves-effect waves-light\" style=\"background-color: #73cab8; color: #FFFFFF\"> 修改\n" +
                    "                                        </button>\n" +
                    "<button type=\"button\" class=\"btn btn-rounded waves-effect waves-light mydel\" style=\"background-color: #6eadec; color: #FFFFFF\"> 删除\n" +
                    "                                        </button>"
                }
                //Bootstrap 异步添加
                $('#user_table').bootstrapTable('insertRow', {
                    index: $('#user_table').bootstrapTable('getData').length,
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

function delUser(obj) {
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
            url: "deluser",
            data: formData,
            contentType: false,
            processData: false,
            datatype: "text",
            success: function (data) {
                if (data == "success") {
                    alertMsg("删除成功", "success", 1000);
                    //Bootstrap 异步删除
                    $('#user_table').bootstrapTable('remove', {
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
    m.find("#modify_smtp").val(tmp.parent().parent().children().eq(2).text());
    m.find("#modify_pop3").val(tmp.parent().parent().children().eq(3).text());
    m.find("#modify_acc").val(tmp.parent().parent().children().eq(4).text());
    m.find("#modify_pwd").val(tmp.parent().parent().children().eq(5).text());
    m.find(".roleop").selectpicker("val", tmp.parent().parent().children().eq(6).attr("class"));
    m.attr("name", tmp.parent().parent().children().eq(0).text());
    m.attr("index", tmp.parent().parent().data("index"));
}

function modifyUser(obj) {
    var tmp = $(obj);
    var index = tmp.parent().parent().parent().attr("index");
    var userId = tmp.parent().parent().parent().attr("name");
    var userName = tmp.parent().find("#modify_name").val();
    var userSMTP = tmp.parent().find("#modify_smtp").val();
    var userPOP3 = tmp.parent().find("#modify_pop3").val();
    var userAcc = tmp.parent().find("#modify_acc").val();
    var userPwd = tmp.parent().find("#modify_pwd").val();
    var formData = new FormData();
    formData.append("id", userId);
    formData.append("name", userName);
    formData.append("smtp", userSMTP);
    formData.append("pop3", userPOP3);
    formData.append("acc", userAcc);
    formData.append("pwd", userPwd);
    formData.append("roleid", tmp.parent().find(".roleop").selectpicker("val"));
    $.ajax({
        type: "POST",
        url: "modifyuser",
        data: formData,
        contentType: false,
        processData: false,
        datatype: "json",
        success: function (data) {
            if (data.success == true) {
                var content = {   //要插入的数据，这里要和table列名一致
                    id: userId,
                    name: userName,
                    smtp: userSMTP,
                    pop3: userPOP3,
                    acc: userAcc,
                    pwd: userPwd,
                    role: $(".roleop").find("option:selected").text(),
                };
                //Bootstrap 异步修改
                $('#user_table').bootstrapTable('updateRow', {
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
