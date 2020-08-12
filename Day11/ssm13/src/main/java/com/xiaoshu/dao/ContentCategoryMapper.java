package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.ContentCategory;
import com.xiaoshu.entity.ContentCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContentCategoryMapper extends BaseMapper<ContentCategory> {
    long countByExample(ContentCategoryExample example);

    int deleteByExample(ContentCategoryExample example);

    List<ContentCategory> selectByExample(ContentCategoryExample example);

    int updateByExampleSelective(@Param("record") ContentCategory record, @Param("example") ContentCategoryExample example);

    int updateByExample(@Param("record") ContentCategory record, @Param("example") ContentCategoryExample example);

	List<ContentCategory> queryDept();
}