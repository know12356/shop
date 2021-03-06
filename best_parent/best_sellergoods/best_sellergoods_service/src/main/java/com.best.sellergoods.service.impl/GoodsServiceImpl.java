package com.best.sellergoods.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.best.entity.Good;
import com.best.entity.PageResult;
import com.best.entity.Result;
import com.best.mapper.*;
import com.best.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.best.pojo.TbGoodsExample.Criteria;
import com.best.sellergoods.service.GoodsService;
import org.springframework.transaction.annotation.Transactional;


/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private TbGoodsMapper goodsMapper;

    @Autowired
    private TbGoodsDescMapper goodsDescMapper;

    @Autowired
    private TbBrandMapper brandMapper;

    @Autowired
    TbItemCatMapper ItemCatMapper;

    @Autowired
    TbSellerMapper sellerMapper;

    @Autowired
    TbItemMapper itemMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbGoods> findAll() {
        return goodsMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    private void saveItemList(Good goods) {
        if (goods.getGoods().getIsEnableSpec().equals("1")) {
            for (TbItem item : goods.getItemList()) {
                String title = goods.getGoods().getGoodsName();
                Map<String, Object> specMap = JSON.parseObject(item.getSpec());
                for (String key : specMap.keySet()) {
                    title += " " + specMap.get(key);
                }

                item.setTitle(title);
                item.setSellerId(goods.getGoods().getSellerId());
                item.setGoodsId(goods.getGoods().getId());
                TbBrand brand = brandMapper.selectByPrimaryKey(goods.getGoods().getBrandId());
                item.setBrand(brand.getName());
                item.setCategoryid(goods.getGoods().getCategory3Id());

                TbItemCat itemCat = ItemCatMapper.selectByPrimaryKey(goods.getGoods().getCategory3Id());
                item.setCategory(itemCat.getName());

                TbSeller seller = sellerMapper.selectByPrimaryKey(goods.getGoods().getSellerId());
                item.setSeller(seller.getNickName());

                item.setCreateTime(new Date());

                item.setUpdateTime(new Date());

                //获取图片地址
                List<Map> imageList = JSON.parseArray(goods.getGoodsDesc().getItemImages(), Map.class);


                if (imageList.size() > 0) {
                    item.setImage((String) imageList.get(0).get("url"));
                }
                itemMapper.insert(item);

            }

        }

    }

    /**
     * 修改
     */
    @Override
    public void update(Good goods) {
        goodsMapper.updateByPrimaryKey(goods.getGoods());

        goodsDescMapper.updateByPrimaryKey(goods.getGoodsDesc());


        //删除多余item
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(goods.getGoods().getId());

        itemMapper.deleteByExample(example);


        saveItemList(goods);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Good findOne(Long id) {

        Good goods = new Good();
        goods.setGoods(goodsMapper.selectByPrimaryKey(id));

        goods.setGoodsDesc(goodsDescMapper.selectByPrimaryKey(id));


        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(id);

        goods.setItemList(itemMapper.selectByExample(example));

        return goods;
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            goodsMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbGoodsExample example = new TbGoodsExample();
        Criteria criteria = example.createCriteria();

        if (goods != null) {
            if (goods.getSellerId() != null && goods.getSellerId().length() > 0) {
                criteria.andSellerIdEqualTo(goods.getSellerId());
                //criteria.andSellerIdLike("%" + goods.getSellerId() + "%");
            }
            if (goods.getGoodsName() != null && goods.getGoodsName().length() > 0) {
                criteria.andGoodsNameLike("%" + goods.getGoodsName() + "%");
            }
            if (goods.getAuditStatus() != null && goods.getAuditStatus().length() > 0) {
                criteria.andAuditStatusEqualTo(goods.getAuditStatus());
                //criteria.andAuditStatusLike("%" + goods.getAuditStatus() + "%");
            }
            if (goods.getIsMarketable() != null && goods.getIsMarketable().length() > 0) {
                criteria.andIsMarketableLike("%" + goods.getIsMarketable() + "%");
            }
            if (goods.getCaption() != null && goods.getCaption().length() > 0) {
                criteria.andCaptionLike("%" + goods.getCaption() + "%");
            }
            if (goods.getSmallPic() != null && goods.getSmallPic().length() > 0) {
                criteria.andSmallPicLike("%" + goods.getSmallPic() + "%");
            }
            if (goods.getIsEnableSpec() != null && goods.getIsEnableSpec().length() > 0) {
                criteria.andIsEnableSpecLike("%" + goods.getIsEnableSpec() + "%");
            }
            if (goods.getIsDelete() != null && goods.getIsDelete().length() > 0) {
                criteria.andIsDeleteLike("%" + goods.getIsDelete() + "%");
            }

        }

        Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public Result add(Good goods) {

        try {
            goods.getGoods().setAuditStatus("0");//设置未申请状态
            //插入商品
            goodsMapper.insert(goods.getGoods());

            //插入商品详情信息
            goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());
            goodsDescMapper.insert(goods.getGoodsDesc());
            //是否取用规格
            if (goods.getGoods().getIsEnableSpec().equals("1")) {
                for (TbItem item : goods.getItemList()) {
                    String title = goods.getGoods().getGoodsName();
                    Map<String, Object> specMap = JSON.parseObject(item.getSpec());

                    for (String key : specMap.keySet()) {
                        title += " " + specMap.get(key);
                    }
                    item.setTitle(title);
                    item.setSellerId(goods.getGoods().getSellerId());
                    item.setGoodsId(goods.getGoods().getId());
                    TbBrand brand = brandMapper.selectByPrimaryKey(goods.getGoods().getBrandId());
                    item.setBrand(brand.getName());
                    item.setCategoryid(goods.getGoods().getCategory3Id());

                    TbItemCat itemCat = ItemCatMapper.selectByPrimaryKey(goods.getGoods().getCategory3Id());
                    item.setCategory(itemCat.getName());

                    TbSeller seller = sellerMapper.selectByPrimaryKey(goods.getGoods().getSellerId());
                    item.setSeller(seller.getNickName());

                    item.setCreateTime(new Date());

                    item.setUpdateTime(new Date());

                    //获取图片地址
                    List<Map> imageList = JSON.parseArray(goods.getGoodsDesc().getItemImages(), Map.class);


                    if (imageList.size() > 0) {
                        item.setImage((String) imageList.get(0).get("url"));
                    }

                    itemMapper.insert(item);
                }
            }

            return new Result(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败");

        }

    }

    @Override
    public void upDataStatus(Long[] ids, String status) {
        for (Long id : ids) {
            TbGoods goods= goodsMapper.selectByPrimaryKey(id);
            goods.setAuditStatus(status);
            goodsMapper.updateByPrimaryKey(goods);
        }

    }

}
