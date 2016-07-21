var app = angular.module('app',[]);

app.controller('ctrlHead', function($scope, $rootScope) {
    $scope.ButtonPush = function (text) {
        $rootScope.razdel = text;
        console.log('$rootScope.razdel', $rootScope.razdel);
    };
});

app.controller('ctrlMid', function($scope, $rootScope, $http){

    $scope.sendDate = function () {
    $http.get('http://localhost:8080/library/modify')
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
    $http.post('/library/ind','1')//----/index.html
        .success(function(data, status, headers, config){
                console.log('data = ',data);
                console.log('status = ',status);
                console.log('headers = ',headers);
                console.log('config = ',config);
            })
            .error(function(result){
                console.log('Запрос не прошел!');
            });
    };
    $scope.s2 = function(){
    $http.post('/library/signup','2');
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