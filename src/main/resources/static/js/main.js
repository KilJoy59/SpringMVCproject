$(function() {
    var now = new Date();
    var format_time= now.toLocaleTimeString("ru");
    var format_date = now.toLocaleDateString("ru");

    const appendTask = function(data) {

        var taskCode = '<a href="#" class="task-link" data-id="' + data.id + '">' + 'Задача: ' + data.name
            + '</a>    <a href="#" class="task-del" data-id="' + data.id + '">delete</a><br>';
        $('#task-list')
            .append('<div>' + taskCode + '</div>');
    };

    //get form
    $.get('/tasks/', function (response) {
        for (i in response) {
            appendTask(response[i]);
        }
    });

    //open form
    $('.open-button').click(function () {
        $('.form-popup').css('display','flex');
    });

    //close form
    $('.btn').click(function () {
        $('.form-popup').css('display','none');
    });

    //Adding task
    $('#save-task').click(function() {
        var data = $('#todo-form form').serialize();
        $.ajax({
            method: "POST",
            url: '/tasks/',
            data: data,
            success: function(response){
                $('.form-popup').css('display', 'none');
                var task = {};
                task.id = response;
                var dataArray = $('#todo-form form').serializeArray();
                for(i in dataArray) {
                    task[dataArray[i]['name']] = dataArray[i]['value'];
                }
                appendTask(task);
            }
        });
        return false;
    });


    var randomId = function () {
        return '_' + Math.random().toString(36).substr(2, 9);
    };


    //Getting task
    $(document).on('click','.task-link', function () {
            var link = $(this);
            var taskId = link.data('id');
        if(document.getElementsByClassName("desc-id-"+ taskId).length === 0) {
            $.ajax({
                method: "GET",
                url: "/tasks/" + taskId,
                success: function (response) {
                    var code = '<span class= "desc-id-' + taskId + '"> Описание:'
                        + response.description + " " + format_date + " " + format_time + '</span>';
                    link.parent().append(code);

                },
                error: function (response) {
                    if (response.status == 404) {
                        alert('Задача не найдена!');
                    }
                }

            });
        }
        return false
    });

    //Delete task
    $(document).on('click', '.task-del', function(){
        var link = $(this);
        var taskId = link.data('id');
        $.ajax({
            method: "DELETE",
            url: '/tasks/' + taskId,
            success: function(response){
                link.parent().remove();
            },
            error: function(response){
                if(response.status == 404) {
                    alert('Задача удалена!');
                }
            }
        });
        return true;
    });
});