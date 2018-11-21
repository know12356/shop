package com.best.sellergoods.service;

import java.util.List;

import com.best.entity.Good;
import com.best.entity.PageResult;
import com.best.entity.Result;
import com.best.pojo.TbGoods;

/**
 * 服务层接口
 *
 * @author Administrator
 */
public interface GoodsService {

    /**
     * 返回全部列表
     *
     * @return
     */
    public List<TbGoods> findAll();


    /**
     * 返回分页列表
     *
     * @return
     */
    public PageResult findPage(int pageNum, int pageSize);




    /**
     * 修改
     */
    public void update(Good goods);


    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    public Good findOne(Long id);


    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(Long[] ids);

    /**
     * 分页
     *
     * @param pageNum  当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    public PageResult findPage(TbGoods goods, int pageNum, int pageSize);

    /**
     * add
     * @param goods
     */
    public Result add(Good goods);


    public void upDataStatus(Long[] ids,String status);
}
