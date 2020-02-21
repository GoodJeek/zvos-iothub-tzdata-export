package com.zvos.iothub.tzdata.export.mapper.base;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @projectName: zvos-iothub-tzdata-export
 * @author: 00745775
 * @date: 2020-2-19
 * @description:
 */

@RegisterMapper
public interface ZvosOracleMapper<T> extends Mapper<T>, MySqlMapper<T>, IdsMapper<T> {
}


