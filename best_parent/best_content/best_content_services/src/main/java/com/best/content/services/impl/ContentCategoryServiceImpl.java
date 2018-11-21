package com.best.content.services.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.best.content.services.ContentCategoryService;
import com.best.entity.PageResult;
import com.best.mapper.TbContentCategoryMapper;
import com.best.pojo.TbContentCategory;
import com.best.pojo.TbContentCategoryExample;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;


    @Override
    public List<TbContentCategory> findAll() {
        return contentCategoryMapper.selectByExample(null);
    }

    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbContentCategory> page = (Page<TbContentCategory>) contentCategoryMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void add(TbContentCategory content) {
        contentCategoryMapper.insert(content);

    }

    @Override
    public void update(TbContentCategory content) {
        contentCategoryMapper.updateByPrimaryKey(content);
    }

    @Override
    public TbContentCategory findOne(Long id) {
        return contentCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            contentCategoryMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public PageResult findPage(TbContentCategory content, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        TbContentCategoryExample example=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria=example.createCriteria();
        if (content!=null){
            if(content.getName()!=null&&content.getName().length()>0){
                criteria.andNameLike("%"+content.getName()+"%");
            }

        }
        Page<TbContentCategory> page = (Page<TbContentCategory>) contentCategoryMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }
}
