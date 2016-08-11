var app = angular.module('app',[]);

app.controller('ctrlFoot', function($scope,$http){

    $scope.s2 = function(){
    $http.post('/library/signup','{"login":"masha","pass":"777","user_f":"Дудикова","user_n":"Маша","user_o":"Ивановна","user_dr":"2016.02.23 00:00:00"}')
        .success(function(data){
                    console.log('data = ',data);
                })
        .error(function(data){
                    console.log('data = ', data);
                    console.log('Запрос не прошел!');
                });

    };

    $scope.s7 = function(){
    $http.delete('/library/delete_book?book_id=24')
                .success(function(data){
                        console.log('data = ',data);
                })
                .error(function(data){
                        console.log('data = ', data);
                        console.log('Запрос не прошел!');
                });
    };
    $scope.s8 = function(){
    $http.post('/library/user.html','8');
    };
});