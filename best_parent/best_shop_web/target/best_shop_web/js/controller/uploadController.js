app.controller('uploadController', function ($scope, $controller,uploadService) {

    $controller('baseController', {$scope: $scope});//继承

    $scope.uploadFile = function () {


        uploadService.uploadFile().success(function (response) {
            if (response.success) {
                $scope.image_entity.url = response.message;//设置文件地址
            }else{
                alert(response.message);
            }
        }).error(function () {
            alert("上传错误")
        });

    }


});