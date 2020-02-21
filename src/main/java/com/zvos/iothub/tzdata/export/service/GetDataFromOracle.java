package com.zvos.iothub.tzdata.export.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zvos.iothub.tzdata.export.mapper.AlyWorkTimeMapper;
import com.zvos.iothub.tzdata.export.model.AlyWorkTime;
import com.zvos.iothub.tzdata.export.utils.HdfsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @projectName: zvos-iothub-tzdata-export
 * @author: 00745775
 * @date: 2020-2-20
 * @description:
 */
@Slf4j
@Service
public class GetDataFromOracle {

    @Autowired
    AlyWorkTimeMapper alyWorkTimeMapper;

    public void getAndLoadDataToHdfs(String date){

        Integer pageNo=1;
        boolean lookup = true;

        while(lookup){
            //分批查询，每次查询1000条
            PageHelper.startPage(pageNo, 1000);
            List<AlyWorkTime> alyWorkTimeList = alyWorkTimeMapper.selectAll();
            PageInfo<AlyWorkTime> pageInfo = new PageInfo<>(alyWorkTimeList);
            if (alyWorkTimeList.size() < 1000) {
                lookup = false;
            }
            pageNo++;

            Map<Integer, List<String>> tzdataMap = new ConcurrentHashMap<>();
            for (int i = 1; i <= 31; i++) {
                tzdataMap.put(i, new ArrayList<String>());
            }
            for (AlyWorkTime alyWorkTime : alyWorkTimeList) {

                String yyMMdd = DateFormat.getDateInstance().format(alyWorkTime.getWtLasttime());
                Integer key = alyWorkTime.getWtLasttime().getDay();
                List<String> record = new ArrayList<>();
                record.add(alyWorkTime.getCdVpUniqueno());
                record.add(String.valueOf(alyWorkTime.getWtNormalworktime()));
                record.add(String.valueOf(alyWorkTime.getWtNormaloffsetworktime()));
                record.add(String.valueOf(alyWorkTime.getWtTotalworktime()));
                record.add(String.valueOf(alyWorkTime.getWtLasttime()));
                record.add(String.valueOf(alyWorkTime.getWtInserttime()));
                record.add(String.valueOf(alyWorkTime.getWtDay()));
                record.add(alyWorkTime.getProvince());
                record.add(alyWorkTime.getCity());
                record.add(alyWorkTime.getArea());
                record.add(yyMMdd);
                String recordStr = StringUtils.join(record, "\t");

                if (tzdataMap.containsKey(key)) {
                    tzdataMap.get(key).add(recordStr);
                }
            }
            tzdataMap.keySet().forEach(key -> {
                String dateTime=date.substring(0,4)+"-"+date.substring(4,6)+"-"+key;
                try {
                    HdfsUtil hdfsUtil = new HdfsUtil();
                    hdfsUtil.write(String.join("\n", tzdataMap.get(key)), "hdfs://dev01:8020//warehouse/tablespace/external/hive/zvos_data_iothub.db/zvos_worktime/" +
                            dateTime +
                            "/" +
                            dateTime +
                            ".txt");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            log.info("目前导出数据的条数据是："+pageNo*1000+",数据导出完成的进度是："+pageNo*1000.0/pageInfo.getTotal()*100+"%");
        }
    }
}
