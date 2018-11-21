app.controller('ItemCatController', function ($scope, $controller, itemCatService) {

    $controller('baseController', {$scope: $scope});

    //一级分类
    $scope.selectItemCatList = function () {
        itemCatService.findByParentId(0).success(
            function (response) {
                $scope.itemCat1list = response;
            })
    };
   //二级目录
    $scope.$watch('entity.goods.category1Id', function (newValue, oldValue) {
        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.itemCat2list = response;
            })
    });

    $scope.$watch('entity.goods.category2Id', function (newValue, oldValue) {
        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.itemCat3list = response;
            })
    });

});