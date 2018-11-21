package com.best.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

import com.best.entity.PageResult;
import com.best.entity.Result;
import com.best.mapper.TbBrandMapper;
import com.best.pojo.TbBrand;
import com.best.pojo.TbBrandExample;
import com.best.sellergoods.service.BrandService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    @Autowired
    private TbBrandMapper brandMapper;

    @Override
    public List<TbBrand> getAllBrand() {

        return brandMapper.selectByExample(null);
    }

    @Override
    public PageResult getBrandByPage(int PageNum, int total) {

        PageHelper.startPage(PageNum, total);
        Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(null);


        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public Result addBrand(TbBrand brand) {

        try {
            brandMapper.insertSelective(brand);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败");
        }
    }

    @Override
    public Result delBrand(Long id) {
        try {
            brandMapper.deleteByPrimaryKey(id);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败，无该品牌");
        }
    }

    @Override
    public Result updateBrand(TbBrand brand) {
        try {
            brandMapper.updateByPrimaryKey(brand);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败，无该品牌");
        }

    }

    @Override
    public PageResult searchBrand(TbBrand brand, int PageNum, int total) {
        PageHelper.startPage(PageNum, total);

        TbBrandExample example = new TbBrandExample();
        TbBrandExample.Criteria criteria = example.createCriteria();

        if (brand != null) {

            if (brand.getName() != null && brand.getName().length() > 0) {
                criteria.andNameLike("%" + brand.getName() + "%");
            }

            if (brand.getFirstChar() != null && brand.getFirstChar().length() > 0) {
                criteria.andFirstCharLike("%" + brand.getFirstChar() + "%");
            }
        }
        Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(example);

        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void batchDelete(Long[] ids) {

            for (Long id:ids) {
                brandMapper.deleteByPrimaryKey(id);
            }
    }

    @Override
    public List<Map> selectOptionList() {
        return brandMapper.selectOptionList();
    }
}
