//控制层
app.controller('goodsController', function ($scope, $controller, $location, uploadService, goodsService, itemCatService, typeTemplateService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        goodsService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    };

    //分页
    $scope.findPage = function (page, rows) {
        goodsService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    };


    //查询实体
    $scope.findOne = function () {

        var id = $location.search()['id'];

        goodsService.findOne(id).success(
            function (response) {
                $scope.entity = response;
                editor.html($scope.entity.goodsDesc.introduction);


                $scope.entity.goodsDesc.itemImages =
                    JSON.parse($scope.entity.goodsDesc.itemImages);

                $scope.entity.goodsDesc.customAttributeItems =
                    JSON.parse($scope.entity.goodsDesc.customAttributeItems);
                //规格
                $scope.entity.goodsDesc.specificationItems=
                    JSON.parse($scope.entity.goodsDesc.specificationItems);


                //读取sku列表转换
                
                for (var it=0;it< $scope.entity.itemList.length;it++){
                    $scope.entity.itemList[it].spec=JSON.parse($scope.entity.itemList[it].spec);
                }
                
            }
        );
    };

    //保存
    $scope.save = function () {

        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = goodsService.update($scope.entity); //修改
        } else {
            serviceObject = goodsService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    //重新查询
                    alert("添加成功！")
                    //$scope.reloadList();//重新加载
                } else {
                    alert(response.message);
                }
            }
        );
    };


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        goodsService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    };
    $scope.status = ['未审核', '已审核', '审核未通过', '关闭'];//商品状态


    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        goodsService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    };

    $scope.add = function () {

        $scope.entity.goodsDesc.introduction = editor.html();
        goodsService.add($scope.entity).success(
            function (response) {
                if (response.success) {
                    alert('保存成功');
                    $scope.entity = {};
                    editor.html('');//清空富文本编辑器
                } else {
                    alert(response.message);
                }
            });
    };

    $scope.uploadFile = function () {

        uploadService.uploadFile().success(function (response) {
            if (response.success) {
                $scope.image_entity.url = response.message;//设置文件地址
                alert(response.message);
            } else {
                alert(response.message);
            }
        }).error(function () {
            alert("上传错误")
        });
    };


    $scope.entity = {goodsDesc: {itemImages: [], specificationItems: []}};


    $scope.add_image_entity = function () {
        $scope.entity.goodsDesc.itemImages.push($scope.image_entity);
    };

    $scope.remove_image = function (index) {
        $scope.entity.goodsDesc.itemImages.splice(index, 1);
    };


    //一级分类
    $scope.selectItemCatList = function () {


        itemCatService.findByParentId(0).success(
            function (response) {
                $scope.itemCat1list = response;
            })
    };
    //二级目录
    $scope.$watch('entity.goods.category1Id', function (newValue, oldValue) {

        //if newValue equal undefined return
        if (typeof(newValue) == "undefined") {
            return;
        }

        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.itemCat2list = response;
            })
    });

    $scope.$watch('entity.goods.category2Id', function (newValue, oldValue) {

        //if newValue equal undefined return
        if (typeof(newValue) == "undefined") {
            return;
        }

        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.itemCat3list = response;
            });


    });


    $scope.$watch('entity.goods.category3Id', function (newValue, oldValue) {
        if (typeof(newValue) == "undefined") {
            return;
        }

        itemCatService.findOne(newValue).success(
            function (response) {
                $scope.entity.goods.typeTemplateId = response.typeId;
            });


    });

    $scope.$watch('entity.goods.typeTemplateId', function (newValue, oldValue) {
        if (typeof(newValue) == "undefined") {
            return;
        }
        typeTemplateService.findSpecList(newValue).success(
            function (response) {
                $scope.SpecificationOptionList = response;

            });


        typeTemplateService.findTemplateById(newValue).success(
            function (response) {

                //$scope.TypeTemplate = response;
                $scope.typeTemplate = response;//获取类型模板
                //        			  $scope.typeTemplate.brandIds=
                $scope.typeTemplate.brandIds =
                    JSON.parse($scope.typeTemplate.brandIds);

                if ($location.search()['id'] == null) {
                    $scope.entity.goodsDesc.customAttributeItems =
                        JSON.parse($scope.typeTemplate.customAttributeItems);
                }

            })
    });


    $scope.checkAttributeValue = function (specName, specOption) {
        var items = $scope.entity.goodsDesc.specificationItems;
       //items=JSON.parse(items);
        var object = searchObjectByKey(items, "attributeName", specName);
        if (object == null) {
            return false;
        } else {
            if (object.attributeValue.indexOf(specOption) >= 0) {
                return true;
            } else {
                return false;
            }
        }

    };
//[{“attributeName”:”规格名称”,”attributeValue”:[“规格选项1”,“规格选项2”.... ]  } , ....  ]

    $scope.updateSpecAttribute = function ($event, name, value) {


        var object = searchObjectByKey(
            $scope.entity.goodsDesc.specificationItems, "attributeName", name);

        if (object != null) {
            if ($event.target.checked) {
                object.attributeValue.push(value);
            } else {
                object.attributeValue.splice(object.attributeValue.indexOf(value), 1);
                if (object.attributeValue.length == 0) {
                    $scope.entity.goodsDesc.specificationItems.splice(
                        $scope.entity.goodsDesc.specificationItems.indexOf(object), 1);
                }
            }

        } else {
            $scope.entity.goodsDesc.specificationItems.push({"attributeName": name, "attributeValue": [value]});

        }
    };

    var searchObjectByKey = function (list, key, keyValue) {
        for (var i = 0; i < list.length; i++) {

            if (list[i][key] == keyValue) {
                return list[i];

            }
        }
        return null;
    };


//[{“attributeName”:”规格名称”,”attributeValue”:[“规格选项1”,“规格选项2”.... ]  } , ....  ]
    $scope.createItemList = function () {
        $scope.entity.itemList = [{spec: {}, price: 0, num: 99999, status: '0', isDefault: '0'}];
        //初始
        var items = $scope.entity.goodsDesc.specificationItems;
        for (var i = 0; i < items.length; i++) {
            $scope.entity.itemList = addColumn($scope.entity.itemList, items[i].attributeName, items[i].attributeValue);
            // console.log($scope.entity.itemList+"|"+items[i].attributeName+"|"+items[i].attributeValue);
        }
    };
    var addColumn = function (list, ColumnName, ColumnValues) {
        var newitemList = [];


        for (var i = 0; i < list.length; i++) {
            var oldcol = list[i];
            for (var j = 0; j < ColumnValues.length; j++) {
                var newcol = JSON.parse(JSON.stringify(oldcol));//深克隆
                newcol.spec[ColumnName] = ColumnValues[j];
                newitemList.push(newcol);
            }
        }
        return newitemList;
    };
    $scope.itemCatList = [];

    $scope.findItemCatList = function () {
        itemCatService.findAll().success(
            function (response) {

                for (var i = 0; i < response.length; i++) {
                    $scope.itemCatList[response[i].id] = response[i].name;
                }

            }
        )
    }


});
