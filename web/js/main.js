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