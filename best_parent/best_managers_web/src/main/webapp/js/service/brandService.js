//define service of brand
app.service('brandService', function ($http) {
    this.findPage = function (page, size) {
        return $http.get("../brand/findPage.do?page=" + page + "&rows=" + size);
    };

    this.add = function (entity) {
        return $http.post("../brand/add.do", entity);
    };
    this.update = function (entity) {
        return $http.post("../brand/update.do", entity);
    };

    this.del = function (id) {
        return $http.get("../brand/del.do?id=" + id);
    };

    this.search = function (page, size, searchEntity) {
        return $http.post('../brand/search.do?page=' + page + '&rows=' + size, searchEntity);
    };

    this.batchRemove = function (ids) {
        return $http.get('../brand/batchdelete.do?id=' + ids)
    };
    //下拉列表
    this.selectOptionList = function () {
        return $http.get('../brand/selectOptionList.do');
    }
});
