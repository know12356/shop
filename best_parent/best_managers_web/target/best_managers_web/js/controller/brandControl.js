//define controller of brand
app.controller('brandControl', function ($scope,$controller,brandService) {
    // $scope.findAllBrand=function () {
    //     $http.get("../brand/findAll.do").success(
    //         function (response) {
    //             $scope.BrandList=response;
    //     });
    // };

        //extend super
    $controller('baseController', {$scope: $scope});//继承

    $scope.getEntity = function (entity) {
        $scope.entity = entity;
    };

    $scope.findPage = function (page, size) {
        brandService.findPage(page, size).success(
            function (response) {
                $scope.BrandList = response.rows;
                $scope.paginationConf.totalItems = response.total;
            })
    };

    $scope.add = function () {
        brandService.add($scope.entity).success(
            function (response) {
                if (response.success) {
                    //重新查询
                    $scope.reloadList();//重新加载
                } else {
                    alert("失败消息" + response.success);
                }
            })

    };

    $scope.update = function (entity) {

        brandService.update($scope.entity).success(
            function (response) {
                if (response.success) {
                    //重新查询
                    $scope.reloadList();//重新加载
                } else {
                    alert("失败消息" + response.success);
                }
            })

    };

    $scope.save = function () {
        if ($scope.entity.id == null) {
            $scope.add();
        } else {
            $scope.update();
        }

    };

    $scope.del = function (id) {

        if (confirm("是否删除！")) {
            brandService.del(id).success(function (response) {
                if (response.success) {
                    $scope.reloadList();//重新加载
                } else {
                    alert("失败消息" + response.success);
                }
            })
        }

    };
    $scope.searchEntity = {};
    $scope.search = function (page, size) {

        brandService.search(page, size, $scope.searchEntity).success(
            function (response) {
                $scope.BrandList = response.rows;
                $scope.paginationConf.totalItems = response.total;
            });
    };


    $scope.batchRemove = function () {


        if (confirm("是否删除选中！")) {
            brandService.batchRemove($scope.selectIds).success(
                function (response) {
                    if (response.success) {
                        $scope.reloadList();//重新加载
                    } else {
                        alert("失败消息" + response.success);
                    }
                }
            );
        }
    };

    //下拉列表
    this.selectOptionList = function () {
        return $http.get('../brand/selectOptionList.do');
    }

});

