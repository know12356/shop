package com.best.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.best.pojo.TbTypeTemplate;
import com.best.sellergoods.service.TypeTemplateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/template")
public class TypeTemplateController {

    @Reference
    private TypeTemplateService typeTemplateService;

    @RequestMapping(value = "/fineOne")
    public TbTypeTemplate findTemplateById(Long id) {
        return typeTemplateService.findOne(id);
    }


    @RequestMapping(value = "/findSpecList")
    public List<Map> findSpecList(Long id) {
        return typeTemplateService.findSpecList(id);
    }

}
