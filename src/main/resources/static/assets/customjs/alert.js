function alertMsg(title, type, time) {
    swal({
        title: title,
        text: "",
        timer: time,
        showConfirmButton: false,
        type: type
    });
}