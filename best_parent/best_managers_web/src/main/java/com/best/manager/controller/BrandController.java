package com.best.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.best.entity.PageResult;
import com.best.entity.Result;
import com.best.pojo.TbBrand;
import com.best.sellergoods.service.BrandService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;


    @RequestMapping(value = "/findAll")
    public List<TbBrand> findAll() {
        return brandService.getAllBrand();
    }

    @RequestMapping(value = "/findPage")
    public PageResult findPage(int page, int rows) {
        return brandService.getBrandByPage(page, rows);
    }

    @RequestMapping(value = "/add")
    public Result BrandAdd(@RequestBody TbBrand brand) {
        return brandService.addBrand(brand);
    }

    @RequestMapping(value = "/del")
    public Result delBrand(Long id) {
        return brandService.delBrand(id);
    }

    @RequestMapping(value = "/update")
    public Result updateBrand(@RequestBody TbBrand brand) {
        return brandService.updateBrand(brand);
    }

    @RequestMapping(value = "/search")
    public PageResult search(@RequestBody TbBrand brand, int page, int rows) {
        return brandService.searchBrand(brand, page, rows);
    }


    @RequestMapping(value = "/batchdelete")
    public Result batchdelete(Long[] id ){
        try {
            brandService.batchDelete(id);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }
    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        return brandService.selectOptionList();
    }
}