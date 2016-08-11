var mas_request = "";
var win_api_signin = "/signin";
var win_api_ind = "/ind";
var win_api_find_book = "/find_book";
var win_api_add_book = "/add_book";
var win_api_redact_book = "/redact_book";
var win_api_delete_book = "/delete_book";
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////При инициализации страницы запускаем функцию Ind///////////////////////////////////////
document.addEventListener("DOMContentLoaded", Ind);
////////////////////////////////////////////Заполняем данными index.html////////////////////////////////////////////////
function Ind () {
    //debugger;
    //Формируем данные для отправки
    DelDataForSend();
    FPushForSend(0);

    //Отправляем данные (Запрос для проверки авторизации)
    SendForServer('POST', win_api_ind, mas_request);

}
//////////////////////////////////////////////////////АВТОРИЗАЦИЯ///////////////////////////////////////////////////////
function SignIn(k_action) {
    //k_action - ключ действия автавторизации (true - авторизация, false - выход)
    //Проверяем заполнение полей "Логин" и "Пароль"
    var t_login = document.getElementById("ihead_authorization_1_login").value;
    var t_pass = document.getElementById("ihead_authorization_1_pass").value;

    //Если выходим из авторизации, нам не надо проверять заполнение полей
    if (k_action == true) {
        if ((t_login == "") || (t_pass == "")) {
            alert('Необходимо заполнить поля "Логин" и "Пароль" для авторизации!');
            return false; // Завершаем выполнение функции, если поля пустые
        }
    }

    //Формируем данные для отправки
    DelDataForSend();
    PushDataForSend(0, "login", "\"" + t_login + "\"");
    //Если авторизация отправляем пароль
    if (k_action == true) { PushDataForSend(0, "pass", t_pass); }
    PushDataForSend(0, "status", k_action);
    FPushForSend(0);

    //Отправляем данные
    SendForServer('POST', win_api_signin, mas_request);
return false;
}
//////////////////////////////////////////////////////ПОИСК КНИГИ///////////////////////////////////////////////////////
function FindBook() {

    //Считываем данные с полей запроса
    var t_rasdel = document.getElementById("sblock_detal").options[document.getElementById("sblock_detal").selectedIndex].text;
    var t_name = document.getElementById("isblock_detal_name").value;
    var t_author = document.getElementById("isblock_detal_author").value;
    var t_release = document.getElementById("isblock_detal_release").value;

    //Проверяем введенные данные
    if (t_rasdel == "" || t_rasdel == "Все") {t_rasdel = -1;}
        //Убрать после проработки запросов с БД
        if (t_rasdel == "Экономика") {t_rasdel = 1;}
        if (t_rasdel == "Программирование") {t_rasdel = 2;}
        if (t_rasdel == "Политология") {t_rasdel = 3;}
        if (t_rasdel == "Математика") {t_rasdel = 4;}
        if (t_rasdel == "Философия") {t_rasdel = 5;}
    if (t_name == "") {t_name = -1;}
    if (t_author == "") {t_author = -1;}
    if (t_release == "") {t_release = -1;}

    //Формируем данные для отправки
    DelDataForSend();
    PushDataForSend(1, "book_id", -1);
    PushDataForSend(1, "rasdel", "\"" + t_rasdel + "\"");
    PushDataForSend(1, "book_name", "\"" + t_name + "\"");
    PushDataForSend(1, "book_author", "\"" + t_author + "\"");
    PushDataForSend(1, "book_release", "\"" + t_release + "\"");
    PushDataForSend(1, "zapis_begin", 1);
    PushDataForSend(1, "zapis_end", 10);
    PushDataForSend(1, "version_bd", -1);
    PushDataForSend(1, "book_datecorr", -1);
    PushDataForSend(1, "book_dateloadbd", -1);
    FPushForSend(1);

    //Отправляем данные
    SendForServer('GET', win_api_find_book + mas_request, null);

}
///////////////////////////////////////////////////ДОБАВЛЕНИЕ КНИГИ/////////////////////////////////////////////////////
function AddBook() {
    //Считываем данные с полей запроса
    var t_rasdel = document.getElementById("sblock_detal").options[document.getElementById("sblock_detal").selectedIndex].text;
    var t_name = document.getElementById("isblock_detal_name").value;
    var t_author = document.getElementById("isblock_detal_author").value;
    var t_release = document.getElementById("isblock_detal_release").value;

    //Проверяем введенные данные
    var flag_error = false;
    var t_error = 'Пожалуйста, введите:\n';
    if (t_rasdel == "" || t_rasdel == "Все") {flag_error = true; t_error +='- Раздел;\n';}
        //Убрать после проработки запросов с БД
        if (t_rasdel == "Экономика") {t_rasdel = 1;}
        if (t_rasdel == "Программирование") {t_rasdel = 2;}
        if (t_rasdel == "Политология") {t_rasdel = 3;}
        if (t_rasdel == "Математика") {t_rasdel = 4;}
        if (t_rasdel == "Философия") {t_rasdel = 5;}
    if (t_name == "") {flag_error = true; t_error +='- Название книги;\n';}
    if (t_author == "") {flag_error = true; t_error +='- Автора;\n';}
    if (t_release == "") {flag_error = true; t_error +='- Год издания;\n';}
    t_error += 'Книга не добавлена!';

    if (flag_error ==false) {
        //Добавляем книгу
        //Формируем данные для отправки
        DelDataForSend();
        PushDataForSend(0, "rasdel", "\"" + t_rasdel + "\"");
        PushDataForSend(0, "book_name", "\"" + t_name + "\"");
        PushDataForSend(0, "book_author", "\"" + t_author + "\"");
        PushDataForSend(0, "book_release", "\"" + t_release + "\"");
        FPushForSend(0);

        //Отправляем данные
        SendForServer('POST', win_api_add_book, mas_request);
    } else {
        //Ничего не отправляем на сервер, выдаем предупреждение пользователю
        alert(t_error);
    }

}
///////////////////////////////////////////////////ИЗМЕНЕНИЕ КНИГИ/////////////////////////////////////////////////////
function RedactBook() {
    //Считываем данные с полей запроса
    var t_rasdel = document.getElementById("sblock_detal").options[document.getElementById("sblock_detal").selectedIndex].text;
    var t_name = document.getElementById("isblock_detal_name").value;
    var t_author = document.getElementById("isblock_detal_author").value;
    var t_release = document.getElementById("isblock_detal_release").value;

    var flag_check_radio = false;
    var id_check_radio = -1;
    var id_input_radio_group = document.getElementsByName("radio_group1");

    for(var i = 0; i < id_input_radio_group.length; i++) {
        if(id_input_radio_group[i].checked){
            flag_check_radio = true;
            id_check_radio = id_input_radio_group[i].getAttribute('value');
            break;
        }
    }

    if(flag_check_radio == false) {alert('Выберете запись для изменения!');}

    //Проверяем введенные данные
    //Поля будут не заполнены, а радио баттон выбран, то никакие изменения не будут внесены
    if (t_rasdel == "" || t_rasdel == "Все") {t_rasdel = -1;}
        //Убрать после проработки запросов с БД
        if (t_rasdel == "Экономика") {t_rasdel = 1;}
        if (t_rasdel == "Программирование") {t_rasdel = 2;}
        if (t_rasdel == "Политология") {t_rasdel = 3;}
        if (t_rasdel == "Математика") {t_rasdel = 4;}
        if (t_rasdel == "Философия") {t_rasdel = 5;}
    if (t_name == "") {t_name = -1;}
    if (t_author == "") {t_author = -1;}
    if (t_release == "") {t_release = -1;}

    if (flag_check_radio == true) {
        //Если рабио баттон выбран
        //Формируем данные для отправки
        DelDataForSend();
        PushDataForSend(0, "book_id", id_check_radio);
        PushDataForSend(0, "rasdel", "\"" + t_rasdel + "\"");
        PushDataForSend(0, "book_name", "\"" + t_name + "\"");
        PushDataForSend(0, "book_author", "\"" + t_author + "\"");
        PushDataForSend(0, "book_release", "\"" + t_release + "\"");
        PushDataForSend(0, "book_datecorr", -1);
        PushDataForSend(0, "book_dateloadbd", -1);
        FPushForSend(0);

        //Отправляем данные
        SendForServer('POST', win_api_redact_book, mas_request);
    }

}
///////////////////////////////////////////////////УДАЛЕНИЕ КНИГИ/////////////////////////////////////////////////////
function DeleteBook() {
    var flag_check_radio = false;
    var id_check_radio = -1;
    var id_input_radio_group = document.getElementsByName("radio_group1");

    for(var i = 0; i < id_input_radio_group.length; i++) {
        if(id_input_radio_group[i].checked){
            flag_check_radio = true;
            id_check_radio = id_input_radio_group[i].getAttribute('value');
            break;
        }
    }

    if(flag_check_radio == false) {alert('Выберете запись для удаления!');}

    if (flag_check_radio == true) {
        //Если рабио баттон выбран
        //Формируем данные для отправки
        DelDataForSend();
        PushDataForSend(1, "book_id", id_check_radio);
        FPushForSend(1);

        //Отправляем данные
        SendForServer('DELETE', win_api_delete_book + mas_request, null);
    }
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////Формируем данные для отправки на сервер/////////////////////////////////////////
function PushDataForSend(k_get, str_key, str_value){
    //Ключ k_get - формируем данные для отправки GET запросом (0 - нет, 1 - да)

    if(k_get == "0") {
        //Готовим данные для отправки JSON-ом
        mas_request += str_key + ":" + str_value + ",";
    } else {
        //Готовим данные для GET запроса
        mas_request += str_key + "=" + str_value + "&";
    }

}
//////////////////////////////////////////Чистим данные для отправки на сервер//////////////////////////////////////////
function DelDataForSend(){

    mas_request = "";

}
//////////////////////////////////////////Чистим данные для отправки на сервер//////////////////////////////////////////
function FPushForSend(k_get){
    //Ключ k_get - формируем данные для отправки GET запросом (0 - нет, 1 - да)
    mas_request = mas_request.substring(0, mas_request.length - 1);
    if(k_get == "0") {
        mas_request = "{" + mas_request + "}";
    } else {
        mas_request = "?" + mas_request;
    }

}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////Отправка запроса на сервер на сервер//////////////////////////////////////////
function SendForServer(method, action, formData) {
    //Продумать завтра реализацию
    var zapros = new XMLHttpRequest();

    zapros.open(method, action, true);
    zapros.send(formData);

    console.group("Request");
        console.info("Method: " + method);
        console.info("URL: " + action);
        console.log("body: " + formData);
    console.groupEnd();

    zapros.onreadystatechange = function () {

        if (zapros.readyState != 4) { return;}

        console.group("Response");
        if (zapros.status != 200) {
            //Ошибка при выполнении запроса
            console.error("Status: " + zapros.status);
            console.error("Text: " + zapros.statusText);
            console.groupEnd();
        } else {
            //Запрос прошел обрабатываем данные
            console.info("Status: " + zapros.status);
            console.log("Text: " + zapros.responseText);
            console.groupEnd();
            WorkWithResponse(action, JSON.parse(zapros.responseText));
        }

    }

    return false;
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////Сервис для обработки респонсе//////////////////////////////////////////////
function WorkWithResponse(url, response) {

    if (url == win_api_signin) {
        console.info("Work with response. URL: " + url);
        ResponseSignIn(response);
    }
    if (url == win_api_ind) {
        console.info("Work with response. URL: " + url);
        ResponseInd(response);
    }
    if (url.substring(0,win_api_find_book.length) == win_api_find_book) {
        console.info("Work with response. URL: " + url);
        ResponseFindBook(response);
    }
    if (url == win_api_add_book) {
        console.info("Work with response. URL: " + url);
        ResponseAddBook(response);
    }
    if (url == win_api_redact_book) {
        console.info("Work with response. URL: " + url);
        ResponseRedactBook(response);
    }
    if (url.substring(0,win_api_delete_book.length) == win_api_delete_book) {
        console.info("Work with response. URL: " + url);
        ResponseDeleteBook(response);
    }


}
/////////////////////////////////////////////Обработка респонсе для SignIn//////////////////////////////////////////////
function ResponseSignIn(response) {
    //id = "shead_authorization_1_tlogin" - текст "Логин"
    //id = "ihead_authorization_1_login" - поле ввода "Логин"
    //id = "shead_authorization_1_tpass" - текст "Пароль"
    //id = "ihead_authorization_1_pass" - поле вовода "Пароль"
    //id = "shead_authorization_1_message" - сообщение "Авторизация"
    //id = "ihead_authorization_1_entrance" - кнопка "Войти/Выйти"
    //id = "ihead_authorization_1_registration" - кнопка "Регистрация"
    var json_resp = JSON.parse(response);
    console.log("User sign in =" + json_resp.user.online);

    var b_reg = document.getElementById("ihead_authorization_1_registration");
    var b_entr = document.getElementById("ihead_authorization_1_entrance");
    var b_mess = document.getElementById("shead_authorization_1_message");
    var e_login = document.getElementById("ihead_authorization_1_login");
    var t_login = document.getElementById("shead_authorization_1_tlogin");
    var e_pass = document.getElementById("ihead_authorization_1_pass");
    var t_pass = document.getElementById("shead_authorization_1_tpass");

    if (json_resp.user.online == true) {
        //Авторизация прошла успешно
        b_reg.style.display = "none";
        b_entr.style.width = (50 + 115) + 'px';
        b_entr.value = "Выйти";
        b_entr.setAttribute('onclick',"SignIn(false)");
        b_mess.style.opacity = 0;
        e_login.setAttribute('disabled',"disabled");
        t_pass.style.opacity = 0;
        e_pass.style.opacity = 0;
        e_pass.value = "";

    } else {
        //Авторизация не прошла или пользователь выходит
        //Проявляем предшествующее состояние пользователя
        if (b_entr.getAttribute('onclick') == "SignIn(false)") {
            //Если пользователь ранее был зарегистрирован
            b_reg.style.display = "";
            b_entr.style.width = '50px';
            b_entr.setAttribute('onclick',"SignIn(true)");
            b_entr.value = "Войти";
            b_mess.style.opacity = 1;
            e_login.removeAttribute('disabled');
            t_pass.style.opacity = 1;
            e_pass.style.opacity = 1;
        }
    }
//Временно логируем cookie
console.group("Cookie");
console.info(document.cookie);
console.groupEnd();
}
////////////////////////////////////////////////Обработка респонсе для Ind//////////////////////////////////////////////
function ResponseInd(response) {

    var json_resp = JSON.parse(response);

    /*console.group("Data for view");
    console.log("User =" + json_resp.user);
    console.log("Book =" + json_resp.book);
    console.log("Biblio =" + json_resp.biblio);
    console.log("News =" + json_resp.news);
    console.groupEnd();*/

    if (json_resp.user != null) {
        //Если данные о присутствуют в ответе передаем их на авторизацию
        ResponseSignIn(response);

    }

    if (json_resp.biblio != null) {
        //Если данные о присутствуют в ответе передаем их на отображение
        BiblioWriter(json_resp.biblio);
    }

    if (json_resp.news != null) {
        //Если данные о присутствуют в ответе передаем их на отображение
        NewsWriter(json_resp.news);
    }

    BookWriter(json_resp.book);

    //Временно логируем cookie
    /*console.group("Cookie");
    console.info(document.cookie);
    console.groupEnd;*/
}
////////////////////////////////////////////Обработка респонсе для FindBook/////////////////////////////////////////////
function ResponseFindBook(response) {
    var json_resp = JSON.parse(response);

    /*console.group("Data for view");
    console.log("User =" + json_resp.user);
    console.log("Book =" + json_resp.book);
    console.groupEnd();*/

    if (json_resp.user != null) {
        //Если данные о присутствуют в ответе передаем их на авторизацию
        ResponseSignIn(response);
    }

    BookWriter(json_resp.book);
}
/////////////////////////////////////////////Обработка респонсе для AddBook/////////////////////////////////////////////
function ResponseAddBook(response) {
    var json_resp = JSON.parse(response);

    /*console.group("Data for view");
    console.log("User =" + json_resp.user);
    console.log("Book =" + json_resp.book);
    console.groupEnd();*/

    if (json_resp.user != null) {
        //Если данные о присутствуют в ответе передаем их на авторизацию
        ResponseSignIn(response);
    }
    //Если статус false то просим авторизоваться для добавления книг
    if (json_resp.user.online == false) {
        alert('Книга не добавлена!\nДобавлять книги могут только авторизованные пользователи!');
    } else {
        BookWriter(json_resp.book);
    }

}
///////////////////////////////////////////Обработка респонсе для RedactBook////////////////////////////////////////////
function ResponseRedactBook(response) {
    var json_resp = JSON.parse(response);

    /*console.group("Data for view");
    console.log("User =" + json_resp.user);
    console.log("Book =" + json_resp.book);
    console.groupEnd();*/

    if (json_resp.user != null) {
        //Если данные о присутствуют в ответе передаем их на авторизацию
        ResponseSignIn(response);
    }

    //Если статус false то просим авторизоваться для редактирования книг
    if (json_resp.user.online == false) {
        alert('Книга не изменена!\nИзменять книги могут только авторизованные пользователи!');
    } else {
        BookWriter(json_resp.book);
    }

}
///////////////////////////////////////////Обработка респонсе для DeleteBook////////////////////////////////////////////
function ResponseDeleteBook(response) {
    var json_resp = JSON.parse(response);

    /*console.group("Data for view");
    console.log("User =" + json_resp.user);
    console.log("Book =" + json_resp.book);
    console.groupEnd();*/

    if (json_resp.user != null) {
        //Если данные о присутствуют в ответе передаем их на авторизацию
        ResponseSignIn(response);
    }

    //Если статус false то просим авторизоваться для редактирования книг
    if (json_resp.user.online == false) {
        alert('Книга не удалена!\nУдалять книги могут только авторизованные пользователи!');
    } else {
        BookWriter(json_resp.book);
    }
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////Выводим данные о библиотеке///////////////////////////////////////////////
function BiblioWriter(biblio) {
    //Выводим данные о библиотеке
    //Вставка в div с id = "biblio_block"
    //var json_biblio = JSON.parse(biblio);  При передаче одного объекта парсер не потребовался

    document.getElementById("biblio").style.opacity = 1;

    document.getElementById("biblio_block_e1").innerHTML = "<p>" + biblio[0].history_b + "</p>";
    document.getElementById("biblio_block_e2").innerHTML = "<p>" + biblio[0].adress_b + "</p>";
    document.getElementById("biblio_block_e3").innerHTML = "<p>" + biblio[0].director_b + "</p>";
    document.getElementById("biblio_block_e4").innerHTML = "<p>" + biblio[0].worktime_b + "</p>";

}
/////////////////////////////////////////////////////Выводим новости////////////////////////////////////////////////////
function NewsWriter(news) {
    //Выводим данные о библиотеке
    //Вставка в div с id = "news_block"

    document.getElementById("news").style.opacity = 1;

    //Динамически генерируем блок с новостями
    //Очищаем от предыдущих значений
    var del_el = document.getElementById("news_block");
        del_el.parentNode.removeChild(del_el);
    var new_el = document.createElement('div');
        new_el.id = "news_block";
        document.getElementById("news").appendChild(new_el);
    //Создаем новый на основание данных с сервера
    for (var i = 0; i <news.length; i++) {
        var div_v = document.createElement('div');
            div_v.className = "cnews_block";
            div_v.id = "news_block_n" + i;
            div_v.innerHTML = "<p>" + news[i].date_n + "  :  " + news[i].text_n + "</p>";
            new_el.appendChild(div_v);
    }

}
/////////////////////////////////////////////////Выводим данные о книгах////////////////////////////////////////////////
function BookWriter(book) {
    //Выводим данные о библиотеке
    //Вставка в table с id = "books_table"

    //Очищаем от предыдущих значений
    var tab_id = document.getElementById("books_table");
    var sl = tab_id.rows.length;
    for (var i = 1; i <sl; i++) {
        tab_id.removeChild(tab_id.rows[1]);
    }
    if (book != null){
        //Создаем новые строчки в таблице и заполняем их книгами
        for (var i = 0; i <book.length; i++) {
            var tr_v = document.createElement('tr');
            var td_v0 = document.createElement('td');
            var in_v0 = document.createElement('input');
                in_v0.id = "input_n" + i;
                in_v0.name = "radio_group1";
                in_v0.className = "inp_check";
                in_v0.type = "radio";
                in_v0.value = book[i].id_b;
                td_v0.appendChild(in_v0);
                tr_v.appendChild(td_v0);
            var td_v1 = document.createElement('td');
                td_v1.innerHTML = book[i].name_b;
                td_v1.className = "td_name";
                tr_v.appendChild(td_v1);
            var td_v2 = document.createElement('td');
                td_v2.innerHTML = book[i].author_b;
                tr_v.appendChild(td_v2);
            var td_v3 = document.createElement('td');
                td_v3.innerHTML = book[i].release_b;
                tr_v.appendChild(td_v3);
            var td_v4 = document.createElement('td');
                td_v4.innerHTML = book[i].type_b;     //Позже сделать замену, когда из базы будет возвращаться String
                tr_v.appendChild(td_v4);
                tab_id.appendChild(tr_v);
        }
    }

document.getElementById("books").style.opacity = 1;

}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////Нажали на кнопку "ПОИСК"/////////////////////////////////////////////////
function ButtonClickFind() {

    ButCheck("find");

}
/////////////////////////////////////////////Нажали на кнопку "ДОБАВИТЬ"////////////////////////////////////////////////
function ButtonClickAdd() {

    ButCheck("add");

}
/////////////////////////////////////////////Нажали на кнопку "ИЗМЕНИТЬ"////////////////////////////////////////////////
function ButtonClickRedact() {

    ButCheck("redact");

}
//////////////////////////////////////////////Нажали на кнопку "УДАЛИТЬ"////////////////////////////////////////////////
function ButtonClickDel() {

    ButCheck("del");

}
/////////////////////////////////////////////Нажали на кнопку "ОТМЕНИТЬ"////////////////////////////////////////////////
function ButtonClickOtmena() {

    ButCheck("clear");

}
////////////////////////////////////////////////Нажали на кнопку "ОК"///////////////////////////////////////////////////
function ButtonClickOK(id_action) {

    //Если не выбран раздел
    if (id_action == "") {
        alert('Выберете действие, которое необходимо совершить над книжками!');
        return;
    }

    //Раздел "Поиск"
    if (id_action == "1") {
        FindBook();
    }

    //Раздел "Добавление"
    if (id_action == "2") {
        AddBook();
    }

    //Раздел "Изменение"
    if (id_action == "3") {
        RedactBook();
    }

    //Раздел "Удаление"
    if (id_action == "4") {
        DeleteBook();
    }

}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////Изменить стиль нажатой кнопки///////////////////////////////////////////
function ButCheck(select_but) {

    //Настраиваем отображение блоков
    if (select_but == "clear") {
        //Делаем невидимым вспомагательный блок
        document.getElementById("midl_p").style.display = 'none';
        document.getElementById("sblock_detal").selectedIndex = 0;
        document.getElementById("isblock_detal_name").value = "";
        document.getElementById("isblock_detal_author").value = "";
        document.getElementById("isblock_detal_release").value = "";

    } else {
        //Делаем видимым вспомагательный блок
        document.getElementById("midl_p").style.display = 'block';
        //Делаем невидимыми блоки "Новостей" и "Информации о библиотеке"
        document.getElementById("biblio").style.display = 'none';
        document.getElementById("news").style.display = 'none';
    }

    //Настраиваем выделение кнопок
    if(select_but == "find") {
        document.getElementById("find").style.backgroundColor = '#FDC402';
        document.getElementById("block_detal_b_ok").setAttribute('onclick',"ButtonClickOK(1)");
        block_detal_b_ok
    } else {
        document.getElementById("find").style.backgroundColor = 'gainsboro';
    }
    if(select_but == "add") {
        document.getElementById("add").style.backgroundColor = '#FDC402';
        document.getElementById("block_detal_b_ok").setAttribute('onclick',"ButtonClickOK(2)");
    } else {
        document.getElementById("add").style.backgroundColor = 'gainsboro';
    }
    if(select_but == "redact") {
        document.getElementById("redact").style.backgroundColor = '#FDC402';
        document.getElementById("block_detal_b_ok").setAttribute('onclick',"ButtonClickOK(3)");
    } else {
        document.getElementById("redact").style.backgroundColor = 'gainsboro';
    }
    if(select_but == "del") {
        document.getElementById("del").style.backgroundColor = '#FDC402';
        document.getElementById("block_detal_b_ok").setAttribute('onclick',"ButtonClickOK(4)");
    } else {
        document.getElementById("del").style.backgroundColor = 'gainsboro';
    }
}