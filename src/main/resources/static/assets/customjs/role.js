$(document).on("click", ".mydel", function () {
    delRole(this);
});

function modify(obj) {
    var tmp = $(obj);
    Custombox.open({
        target: "#custom-modify",
        effect: "fadein",
        overlaySpeed: "200",
        overlayColor: "#36404a"
    });
    var m = $("#custom-modify");
    //首先清空所选项
    $("#my_multi_select1").multiSelect("deselect_all");
    //根据后台数据进行回显
    var resultIndex = tmp.parent().parent().children().eq(3).text().replace(/\[|]|\s/g, "").split(',');
    for (i = 0; i < resultIndex.length; i++) {
        console.log(resultIndex[i].toString());
        $("#my_multi_select1").multiSelect("select", resultIndex[i].toString())
    }
    m.find("#modify_name").val(tmp.parent().parent().children().eq(1).text());
    m.find("#modify_storage").val(tmp.parent().parent().children().eq(2).text());
    m.attr("name", tmp.parent().parent().children().eq(0).text());
    m.attr("index", tmp.parent().parent().data("index"));
}

function addRole() {
    var tmp = $("#my_multi_select_add").find('option:selected');
    var perIndexs = [];
    for (i = 0; i < tmp.length; i++)
        perIndexs.push(tmp.eq(i).val())
    var formData = new FormData();
    var name = $("#role_name").val();
    var storage = $("#role_storage").val();
    formData.append("name", name);
    formData.append("storage", storage);
    formData.append("indexs", perIndexs);
    $.ajax({
        type: "POST",
        url: "addrole",
        data: formData,
        contentType: false,
        processData: false,
        datatype: "json",
        success: function (data) {
            if (data.success == true) {
                var content = {   //要插入的数据，这里要和table列名一致
                    id: data.result.toString(),
                    name: name,
                    storage: storage,
                    pers: JSON.stringify(perIndexs).replace(/,/g, ', '),
                    options: "<button onclick=\"modify(this)\" type=\"button\" class=\"btn btn-rounded waves-effect waves-light\" style=\"background-color: #73cab8; color: #FFFFFF\"> 修改\n" +
                    "                                        </button>\n" +
                    "<button type=\"button\" class=\"btn btn-rounded waves-effect waves-light mydel\" style=\"background-color: #6eadec; color: #FFFFFF\"> 删除\n" +
                    "                                        </button>"
                };
                //Bootstrap 异步添加
                $('#role_table').bootstrapTable('insertRow', {
                    index: $('#role_table').bootstrapTable('getData').length,
                    row: content
                });
                //关闭窗口
                Custombox.close();
                //成功弹窗
                alertMsg("添加成功", "success", 1000);
            }
        }
    })

}

function delRole(obj) {
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
            url: "delrole",
            data: formData,
            contentType: false,
            processData: false,
            datatype: "text",
            success: function (data) {
                if (data == "success") {
                    alertMsg("删除成功", "success", 1000);
                    //Bootstrap 异步删除
                    $('#role_table').bootstrapTable('remove', {
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