package com.zvos.iothub.tzdata.export.mapper;

import com.zvos.iothub.tzdata.export.mapper.base.ZvosOracleMapper;
import com.zvos.iothub.tzdata.export.model.AlyWorkTime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

@Mapper
public interface AlyWorkTimeMapper extends ZvosOracleMapper<AlyWorkTime> {

    List<AlyWorkTime> searchSegmentedTable(@Param("date") String date);
}