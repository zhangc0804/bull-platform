package com.bull.ox.sys.resource.controller;

import com.bull.ox.sys.resource.entity.Resource;
import com.bull.ox.sys.resource.service.ResourceService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping(path = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map insert(Resource resource){
        Map<String,Object> result = new HashMap<>();
        resourceService.insert(resource);
        result.put("msg","保存成功");
        return result;
    }

    @DeleteMapping(path = "/delete/{id}")
    public Map delete(@PathVariable("id") Long id){
        Map<String,Object> result = new HashMap<>();
        resourceService.delete(id);
        result.put("msg","删除成功");
        return result;
    }

    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map update(Resource resource){
        Map<String,Object> result = new HashMap<>();
        resourceService.update(resource);
        result.put("msg","修改成功");
        return result;
    }

    @GetMapping(path = "/query/{id}")
    public Map query(@PathVariable("id") Long id){
        Map<String,Object> result = new HashMap<>();
        Resource resource = resourceService.query(id);
        result.put("msg","查询成功");
        result.put("data",resource);
        return result;
    }
}
