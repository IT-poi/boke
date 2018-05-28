package com.cuit.boke.label.dao;

import com.cuit.boke.label.beans.entry.Label;
import java.util.List;
import java.util.Map;

public interface LabelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Label record);

    Label selectByPrimaryKey(Integer id);

    List<Label> selectAll();

    int updateByPrimaryKey(Label record);

    Label getByName(String name);

    List<Label> listBy(Map<String, Object> map);

    /**
     * 标签下的文章数量加一
     *
     * @param id 标签id
     * @return 是否修改成功
     */
    int plusArticle(Integer id);

    /**
     * 标签下的文章数量减一
     *
     * @param id 标签id
     * @return 是否修改成功
     */
    int minusArticle(Integer id);


}