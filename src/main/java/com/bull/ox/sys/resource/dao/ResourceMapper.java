package com.bull.ox.sys.resource.dao;

import com.bull.ox.sys.resource.entity.Resource;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ResourceMapper {
    @Insert("insert into resource(name,identifier,url,type,parent_id) values(#{resource.name},#{resource.identifier},#{resource.url},#{resource.type},#{resource.parentId})")
    void insert(@Param("resource") Resource resource);
    @Delete("delete from resource where id=#{id}")
    void delete(@Param("id") Long id);
    @Update("update resource r set r.name=#{resource.name},r.identifier=#{resource.identifier},r.url=#{resource.url},r.type=#{resource.type},r.parent_id=#{resource.parentId} where r.id=#{resource.id}")
    void update(@Param("resource") Resource resource);
    @Select("select * from resource r where r.id=#{id}")
    Resource query(@Param("id") Long id);
}
