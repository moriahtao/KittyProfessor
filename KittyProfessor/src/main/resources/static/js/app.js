$(document).foundation();

$(function() {
    $('#login-form').submit(function(event) {
        event.preventDefault();

        var formEl = $(this);
        var submitButton = $('input[type=submit]', formEl);

        $.ajax({
            type: 'POST',
            url: formEl.prop('action'),
            accept: {
                javascript: 'application/javascript'
            },
            data: formEl.serialize(),
            beforeSend: function() {
                submitButton.prop('disabled', 'disabled');
            }
        }).done(function(data) {
            submitButton.prop('disabled', false);
            location.href = "new-course.html";
            console.log("sucess");
        });
    });
    $('#signup-form').submit(function(event) {
        event.preventDefault();

        var formEl = $(this);
        var submitButton = $('input[type=submit]', formEl);
        var username = $('input[name=username]').val();
        var password = $('input[name=password]').val();
        var email = $('input[name=email]').val();
        var firstname = $('input[name=firstName]').val();
        var lastname = $('input[name=lastName]').val();
        var university = $('input[name=university]').val();
        var joinas = $('select[name=joinAs]').val();
        console.log(joinas);

        $.ajax({
            type: 'POST',
            url: formEl.prop('action'),
            accept: {
                javascript: 'application/javascript'
            },
            data: JSON.stringify(
                {username: username, password: password, firstName: firstname, lastName: lastname, university: university,
                email: email, joinAs: joinas}),
            contentType: "application/json; charset=utf-8",
            dataType   : "json",
            beforeSend: function() {
                submitButton.prop('disabled', 'disabled');
            },
        }).done(function(data) {
            submitButton.prop('disabled', false);
            location.href = "login.html";
            console.log("back");
        });
    });
});