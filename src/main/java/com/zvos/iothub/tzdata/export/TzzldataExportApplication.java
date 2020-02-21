package com.zvos.iothub.tzdata.export;

import com.zvos.iothub.tzdata.export.service.GetDataFromOracle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TzzldataExportApplication {

    public static void main(String[] args) {
        String date=null;

        if(args.length<1){
            log.error("请输入你要查询的数据的具体日期，例如“201911,即导出2019年11月的数据");
        }else{

            date=args[0];
        }

        try {

            ApplicationContext applicationContext=SpringApplication.run(TzzldataExportApplication.class, args);
            GetDataFromOracle getDataFromOracle= (GetDataFromOracle) applicationContext.getBean("getDataFromOracle");
            getDataFromOracle.getAndLoadDataToHdfs(date);

        } catch (BeansException e) {
            e.printStackTrace();
        }
    }
}
