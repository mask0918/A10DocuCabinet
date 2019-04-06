function upload() {
    var f = $("#ownfile").get(0).files[0];
    var formData = new FormData();
    formData.append("uploadfile", f);
    // //名称
    // alert(f.name);
    // //大小 字节
    // alert(f.size);
    // //类型
    // alert(f.type);
    $.ajax({
        type: "POST",
        url: "upload",
        data: formData,
        contentType: false,
        processData: false,
        success: function (data) {
            alert(data);
        }
    })
}
