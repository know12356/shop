package com.best.content.services;
import com.best.pojo.TbContent;

import java.util.List;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface ContentService extends baseService<TbContent>{



	/**
	 * 根据广告分类ID查询广告列表
	 * @param categoryId
	 * @return
	 */
	public List<TbContent> findByCategoryId(Long categoryId);


	
}
