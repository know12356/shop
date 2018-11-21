app.service('typeTemplateService', function ($http) {
    //查询模板
    this.findTemplateById = function (id) {
        return $http.get('../template/fineOne.do?id='+id);
    };
    this.findSpecList=function (id) {
        return $http.get('../template/findSpecList.do?id='+id);
    }
});