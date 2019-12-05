$(function() {
    var now = new Date();
    var format_time= now.toLocaleTimeString("ru");
    var format_date = now.toLocaleDateString("ru");

    const appendTask = function(data) {

        var taskCode = '<a href="#" class="task-link" data-id="' + data.id + '">' + 'Задача: ' + data.name
            + '</a>  ' +
            '<a href="#" class="task-edit" data-id="' + data.id +'">edit</a>  ' +
            '<a href="#" class="task-del" data-id="' + data.id + '">delete</a>';
        $('#task-list')
            .append('<div class="record-' + data.id + '">' + taskCode + '</div>');
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

    //Adding task1
    $('#save-task').click(function() {
        let form = $('#form-task');
        let nameVal = form.find('input[name="name"]').val();
        let descriptionVal = form.find('input[name="description"]').val();
        let url = '/tasks/';
        let jsonString = JSON.stringify({name : nameVal, description: descriptionVal});
        $.ajax({
            method: "POST",
            url: url,
            data: jsonString,
            contentType: 'application/json',
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

    //open form edit-task
    $(document).on('click','.task-edit', function () {
        $('#replace-form').css('display','flex');
    });

    //close form edit-task
    $('.btn2').click(function () {
        $('.form-replace').css('display','none');
    });

    //Edit task
    $('#save-replaceTask').click(function() {
        let form = $('#form-task2');
        let nameVal = form.find('input[name="editName"]').val();
        let descriptionVal = form.find('input[name="editDescription"]').val();
        var taskId = $('.task-edit').data('id');
        var id = $('.record-'+ taskId +' .task-link').data("id");
        let url = '/tasks/' + id;
        let jsonString2 = JSON.stringify({name : nameVal, description : descriptionVal});
        debugger;
        $.ajax({
            method: "PUT",
            url: url,
            data: jsonString2,
            contentType: 'application/json',
            success: function(response) {
                var oldName = $('.record-' + taskId + ' .task-link');
                var oldDescription = $('.record-' + taskId +' .desc-id-' + taskId);
                oldName.text(nameVal);
                oldDescription.text(descriptionVal);
                appendTask(data);
            }
        });
        return false;
    });

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