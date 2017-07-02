<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<link rel="stylesheet" href="css\bootstrap.css">
<script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
<head>

</head>
<body>
<section class="page">
    <div class="container">
        <div style="overflow: hidden;">
            <div style="width:300px; display:inline-block;">
                Сортировать по:
                <select class="form-control" name="criteria" id="criteria" class="criteria" onchange="func()">
                    <option value="V">Все</option>
                    <option value="D">по дате</option>
                    <option value="A">по автору</option>
                    <option value="R">по количеству ретвитов</option>
                    <option value="L">по количеству лайков</option>
                </select>
            </div>

            <div style="width: 300px; display:inline-block;">Поиск по автору &nbsp
                <input class="form-control" type="text" name="name" id="name" oninput="func()">
            </div>
            <div style="width: 500px; display:inline-block;">Поиск по тексту &nbsp
                <input class="form-control" name="text" id="text" oninput="func()"></input>
            </div>
        </div>
        <div class="result" id="res">Список твитов</div>
    </div>
</section>

<script type="application/javascript">

    var func = function () {
        $.ajax({
            'contentType': 'application/javascript; charset=utf-8',
            'url': '/search',
            'data': {
                'name': $("#name").val(),
                'criteria': $("#criteria").val(),
                'text': $("#text").val()
            },
            'type': 'get',
            'success': function (obj) {
                var data = obj;
                var htmlText = "<table class=\"table table-bordered table-inverse\"> <tr> <th>text</th> " +
                    "<th>link</th> <th>date</th> <th>author</th> <th>retweets</th> <th>likes</th> </tr>";
                for (var key in data) {
                    htmlText += "<tr><td>" + data[key].text + "</td>";
                    htmlText += "<td>" + data[key].link + "</td>";
                    htmlText += "<td>" + data[key].date + "</td>";
                    htmlText += "<td>" + data[key].author + "</td>";
                    htmlText += "<td>" + data[key].retweetCount + "</td>";
                    htmlText += "<td>" + data[key].likeCount + "</td></tr>";
                }
                htmlText += "</table>";
                $("#res").html(htmlText);
            }
        })
    };
    $(document).ready()
    {
        func();
        var myVar = setInterval(func, 300000);
    }
</script>
</body>
</html>