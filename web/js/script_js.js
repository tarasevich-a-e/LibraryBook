var mas_request = "";
//////////////////////////////////////////////////////АВТОРИЗАЦИЯ///////////////////////////////////////////////////////
function SignIn() {
    //Проверяем заполнение полей "Логин" и "Пароль"
    var t_login = document.getElementById("ihead_authorization_1_login").value;
    var t_pass = document.getElementById("ihead_authorization_1_pass").value;
    if ((t_login == "") || (t_pass == "")) {
        alert('Необходимо заполнить поля "Логин" и "Пароль" для авторизации!');
        return false; // Завершаем выполнение функции, если поля пустые
    }

    //Формируем данные для отправки
    DelDataForSend();
    PushDataForSend(0, t_login, t_pass);
    PushDataForSend(0, "status", "false"); // Убрать после решения вопроса с авторизацией (ххх)
    FPushForSend(0);

    //Отправляем данные
    SendForServer(mas_request);

}
////////////////////////////////////////Формируем данные для отправки на сервер/////////////////////////////////////////
function PushDataForSend(k_get, str_key, str_value){
    //Ключ k_get - формируем данные для отправки GET запросом (0 - нет, 1 - да)

    if(k_get == "0") {
        //Готовим данные для отправки JSON-ом
        mas_request += "\"" + str_key + "\":\"" + str_value + "\";";
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
function SendForServer(formData) {
    //Продумать завтра реализацию
    var zapros = new XMLHttpRequest();

    zapros.open('POST', "/library/signin", true);
    zapros.send(formData);
    alert(zapros.responseText); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Отсутствует реквест

    return false;
}