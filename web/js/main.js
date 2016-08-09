var app = angular.module('app',[]);

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
    $http.post('/ind','{"login":"masha"}')
        .success(function(data){
                console.log('data = ',data);
                $scope.records = data;
            })
            .error(function(data){
                console.log('data = ', data);
                console.log('Запрос не прошел!');
            });
    };
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

    $scope.s4 = function(){
    $http.get('/library/find_book?book_id=-1&rasdel=rasdel&book_name=Конек-горбунок&book_author=-1&book_release=1940&zapis_begin=1&zapis_end=10&version_bd=-1&book_datecorr=-1&book_dateloadbd=-1')
            .success(function(data){
                            console.log('data = ',data);
                        })
            .error(function(data){
                            console.log('data = ', data);
                            console.log('Запрос не прошел!');
                        });
    };
    $scope.s5 = function(){//pass:777 - когда будет разрабатываться авторизация убрать пароль из пересылки
    $http.post('/library/add_book','{login:masha,pass:777,rasdel:1,book_name:"Название книги",book_author:"Автор книги",book_release:1955}')
                .success(function(data){
                            console.log('data = ',data);
                        })
                .error(function(data){
                            console.log('data = ', data);
                            console.log('Запрос не прошел!');
                        });
    };
    $scope.s6 = function(){
    $http.post('/library/redact_book','{login:masha,pass:777,rasdel:1,book_name:"Отредактированное название",book_author:"Автор 777",book_release:1960,book_id:24}')
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