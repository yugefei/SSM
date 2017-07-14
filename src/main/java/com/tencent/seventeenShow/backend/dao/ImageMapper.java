package com.tencent.seventeenShow.backend.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Edward on 2017/2/8 008.
 */
public interface ImageMapper {
    List<String> getImage(@Param("thirdId")Long id, @Param("thirdType")String thirdType);

    int addImage(@Param("url")String url,@Param("thirdId")Long id,@Param("thirdType")String thirdType);
}
