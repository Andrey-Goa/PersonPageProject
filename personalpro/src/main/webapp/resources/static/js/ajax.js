/**
 * Created by andrey-goa on 28.02.17.
 */

$(document).ready(function() {
    $('.createbutton').click(function() {
        $('#send-form').attr('id', 'save-form');
        $('#myModal').modal('show');
        $('#myModal').on('hidden.bs.modal', function (e) {
            $(this)
                .find("input,textarea,select")
                .val('');
            showpage();
        });
        $('#save-form').submit(function () {
            var strJson = getform();
            $.ajax({
                type: "POST",
                url: "/persons/new",
                data: strJson,
                success: function (data) {
                    if (JSON.parse(data).success == "true") {
                        $('#myModal').hide();
                        $('.modal-backdrop').remove();
                        showpage();
                    } else {
                        delError();
                        setError(data);
                    }
                },
                error: function (data) {
                    alert("Error" + ":" + JSON.stringify(data));
                }
            });
            return false;
        });
    })
});


function showpage() {
    $.ajax({
        url: '/persons',
        cache: false,
        success: function(html) {
            $('#content').html(html);
        }
    });
}

function setError(data) {
    $("#id_er").text(JSON.parse(data).id);
    $("#last_er").text(JSON.parse(data).last_name);
    $("#first_er").text(JSON.parse(data).first_name);
    $("#middle_er").text(JSON.parse(data).middle_name);
    $("#birth_er").text(JSON.parse(data).birth_date);

}

function delError() {
    $("#id_er").text('');
    $("#last_er").text('');
    $("#first_er").text('');
    $("#middle_er").text('');
    $("#birth_er").text('');

}

function fillform(data) {
    $("#myModal").on("show.bs.modal", function () {
        $("#id").val(data.id);
        $("#last_name").val(data.last_name);
        $("#first_name").val(data.first_name);
        $("#middle_name").val(data.middle_name);
        $("#birth_date").val(data.birth_date);
    }).modal('show');
}

function getform(data) {
    var id = $("#id").val();
    var last_name = $("#last_name").val();
    var firt_name = $("#first_name").val();
    var middle_name = $("#middle_name").val();
    var birth_date = $("#birth_date").val();
    var strJson = {"id": id, "last_name": last_name, "first_name": firt_name, "middle_name": middle_name,"birth_date" : birth_date };
    return strJson;
}

$(document).ready(function(){
    $('.delbutton').click(function(){
        var id = $(this).val();
        var strJson = {"id": id};
        $.ajax({
            type: "POST",
            url: "/persons/del",
            data: strJson,
            success: function (data) {
                showpage();
            },error: function (data) {
                alert(JSON.stringify(data));

            }
        });
    });
});

$(document).ready(function(){
    $('.editbutton').click(function(){
        var idN = $(this).val();
        var strJson = {"id": idN};
        $.ajax({
            type: "POST",
            url: "/persons/getperson",
            datetype:'json',
            data: strJson,
            success: function (data) {
                $('#send-form').attr('id', 'edit-form');
                fillform(data);
                $('#id').attr("readonly", "readonly");
                $('#myModal').on('hidden.bs.modal', function (e) {
                    $(this)
                        .find("input,textarea,select")
                        .val('');
                    showpage();
                });
                $(document).ready(function () {
                    $('#edit-form').submit(function () {
                        var strJson = getform();
                        $.ajax({
                            type: "POST",
                            url: "/persons/edit",
                            data: strJson,
                            success: function (data) {
                                if (JSON.parse(data).success == "true") {
                                    $('#myModal').hide();
                                    $('.modal-backdrop').remove();
                                    showpage();
                                } else {
                                    delError();
                                    setError(data);
                                }
                            },
                            error: function (data) {
                                alert(JSON.stringify(date));
                            }
                        });
                        return false;
                    });
                });

            }
        });
        return false;
    });
});

$(document).ready(function() {
    $('.getbutton').click(function () {
        $('#send-form').attr('id', 'get-form');
        $('.nr').removeAttr('required');
        $("#sub").text("Получить");
        $('.hideform').hide();
        $('#myModal').modal('show');
        $('#myModal').on('hidden.bs.modal', function (e) {
            $('.hideform').show();
            $(this)
                .find("input,textarea,select")
                .val('');
            showpage();
        });
        $(document).ready(function () {
            $('#get-form').submit(function () {
                var id = $("#id").val();
                var strJson = {"id": id};
                $.ajax({
                    type: "POST",
                    url: "/persons/getperson",
                    data: strJson,
                    success: function (data) {
                        if (data.id == "0") {
                            $("#id_er").text("Id нет в базе!");
                        } else{
                            $('#get-form').attr('id', 'edit-form');
                            $("#sub").text("Сохранить изменения");
                            $('.hideform').show();
                            $('.nr').attr("required", "required");
                            fillform(data);
                            $('#id').attr("readonly", "readonly");
                            $(document).ready(function () {
                                $('#edit-form').submit(function () {
                                    var strJson = getform();
                                    $.ajax({
                                        type: "POST",
                                        url: "/persons/edit",
                                        data: strJson,
                                        success: function (data) {
                                            if (JSON.parse(data).success == "true") {
                                                $('#myModal').hide();
                                                $('.modal-backdrop').remove();
                                                showpage();
                                            } else {
                                                delError();
                                                setError(data);
                                            }
                                        }, error: function (data) {
                                            alert(JSON.stringify(date));
                                        }
                                    });
                                    return false;
                                });

                            });
                        }
                    }
                });
                return false;

            })
        });

    });
});

$(document).ready(function() {
    var arr = [];

    $('input:checkbox').change(function () {
        var id = $(this).val();
        if ($(this).is(':checked')) {
            arr.push(id);
        } else {
            arr.splice(arr.indexOf(id), 1);
        }
    });

    $('.changebutton').click(function () {
        //<![CDATA[
        jsonObj = [];
        for (var i = 0; i < arr.length; i++) {
            item = {};
            item ["id"] = arr[i];
            jsonObj.push(item);

        }
        //]]>
        if (jsonObj.length === 0) {
            alert("Выберите значение!");
        }
        var jsString = JSON.stringify(jsonObj);
        $.ajax({
            type: "POST",
            url: "/persons/change",
            data:  JSON.stringify(jsonObj),
            contentType: "application/json",
            success: function (data) {
                setTimeout(showpage, 1000);
            },
            error: function (data) {
                alert(JSON.stringify(data));
            }
        });
        return false;
    })

});
