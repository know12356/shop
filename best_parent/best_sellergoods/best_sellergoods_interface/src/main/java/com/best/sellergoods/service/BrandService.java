package com.best.sellergoods.service;

import com.best.entity.PageResult;
import com.best.entity.Result;
import com.best.pojo.TbBrand;

import java.util.List;
import java.util.Map;

public interface BrandService {
    /**
     * get all brand information
     *
     * @return
     */
    public List<TbBrand> getAllBrand();

    public PageResult getBrandByPage(int PageNum, int total);

    public Result addBrand(TbBrand brand);

    public Result delBrand(Long id);

    public Result updateBrand(TbBrand brand);

    public PageResult searchBrand(TbBrand brand, int PageNum, int total);

    public void batchDelete(Long[] ids);

    //get brand list
    public List<Map> selectOptionList();
}
