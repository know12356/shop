package com.best.content.services;

import com.best.entity.PageResult;

import java.util.List;

public interface baseService<T>{
    /**
     * 返回全部列表
     * @return
     */
    public List<T> findAll();

    /**
     * 返回分页列表
     * @return
     */
    public PageResult findPage(int pageNum, int pageSize);

    /**
     * 增加
     */
    public void add(T content);


    /**
     * 修改
     */
    public void update(T content);

    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    public T findOne(Long id);

    /**
     * 批量删除
     * @param ids
     */
    public void delete(Long[] ids);

    /**
     * 分页
     * @param pageNum 当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    public PageResult findPage(T content, int pageNum, int pageSize);



}

