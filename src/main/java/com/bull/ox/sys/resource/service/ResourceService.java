package com.bull.ox.sys.resource.service;

import com.bull.ox.sys.resource.dao.ResourceMapper;
import com.bull.ox.sys.resource.entity.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("resourceService")
public class ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    public void insert(Resource resource){
        resourceMapper.insert(resource);
    }

    public void delete(Long id){
        resourceMapper.delete(id);
    }

    public void update(Resource resource){
        resourceMapper.update(resource);
    }

    public Resource query(Long id){
        return resourceMapper.query(id);
    }
}