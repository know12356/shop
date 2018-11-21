package com.best.entity;

import com.best.pojo.TbGoods;
import com.best.pojo.TbGoodsDesc;
import com.best.pojo.TbItem;

import java.io.Serializable;
import java.util.List;

public class Good implements Serializable {
    private TbGoods goods;//商品gpu

    private TbGoodsDesc goodsDesc;//商品扩展

    private List<TbItem> itemList;//商品SKU列表

    public TbGoods getGoods() {
        return goods;
    }

    public void setGoods(TbGoods goods) {
        this.goods = goods;
    }

    public TbGoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(TbGoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public List<TbItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<TbItem> itemList) {
        this.itemList = itemList;
    }
}
