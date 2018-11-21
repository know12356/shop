app.controller('indexControl',function (loginService,$scope,$controller) {
    $controller('baseController', {$scope: $scope});//继承

    $scope.showLoginName=function () {
        loginService.loginName().success(function (response) {
        $scope.loginName=response.loginName;
        });
    }
});