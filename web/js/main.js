var app = angular.module('app',[]);

app.controller('ctrlHead', function($scope, $rootScope) {
    $scope.ButtonPush = function (text) {
        $rootScope.razdel = text;
        console.log('$rootScope.razdel', $rootScope.razdel);
    };
});

app.controller('ctrlMid', function($scope, $rootScope, $http){

    $scope.sendDate = function () {
    $http.get('http://localhost:8080/library/find_book')
        .success(function(result){
            console.log('Запрос успешно отправлен');
            console.log('result = ',result);
        })
        .error(function(result){
            console.log('Запрос не прошел!');
        });
    };

    /*$http.get('http://localhost:8080/modify?typezaprosa=' + $rootScope.razdel + '&type_b=' + $scope.type_b + '&name_b=' + $scope.name_b + '&author_b=' + $scope.author_b + '&release_b=' + $scope.release_b);
    };*/
});

app.controller('ctrlFoot', function($scope,$http){
    $scope.s1 = function(){
    //----/index.html
    $http.post('/library/ind','{"login":"petia"}')
        .success(function(data){
                console.log('data = ',data);
                $scope.records = data;
            })
            .error(function(result){
                console.log('result = ', result);
                console.log('Запрос не прошел!');
            });
    };
    $scope.s2 = function(){
    $http.post('/library/signup','{"login":"masha","pass":"777","user_f":"Дудикова","user_n":"Маша","user_o":"Ивановна","user_dr":"2016.02.23 00:00:00"}')
        .success(function(data){
                    console.log('data = ',data);
                })
                .error(function(result){
                    console.log('result = ', result);
                    console.log('Запрос не прошел!');
                });

    };
    $scope.s3 = function(){
    $http.post('/library/signin','{"log":"3","privet":"get","bay":"456"}');
    };
    $scope.s4 = function(){
    $http.get('/library/find_book?param=4');
    };
    $scope.s5 = function(){
    $http.post('/library/add_book','5');
    };
    $scope.s6 = function(){
    $http.post('/library/redact_book','6');
    };
    $scope.s7 = function(){
    $http.delete('/library/delete_book','7');
    };
    $scope.s8 = function(){
    $http.post('/library/user.html','8');
    };
});