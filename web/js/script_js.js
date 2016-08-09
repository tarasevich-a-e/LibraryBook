var mas_request = "";
var win_api_signin = "/signin";
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

}
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
        } else {
            //Запрос прошел обрабатываем данные
            console.info("Status: " + zapros.status);
            console.log("Text: " + zapros.responseText);
            WorkWithResponse(action, JSON.parse(zapros.responseText));
        }
        console.groupEnd();
    }

    return false;
}
/////////////////////////////////////////////Сервис для обработки респонсе//////////////////////////////////////////////
function WorkWithResponse(url, response) {

    if (url == win_api_signin) {
        console.info("Work with response. URL: " + url);
        ResponseSignIn(response);
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
        e_pass.value = "";                      //После решения вопроса с авторизацией снять комментарии со строки (ххх)

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
console.groupEnd;
}

/*
//Испытания
function ready () {

}
document.addEventListener("DOMContentLoaded", ready);
*/