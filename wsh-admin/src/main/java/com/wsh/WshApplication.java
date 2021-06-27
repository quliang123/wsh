package com.wsh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 启动程序
 * 
 * @author wsh
 */
@EnableFeignClients
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class WshApplication
{
    public static void main(String[] args)
    {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(WshApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ  \\n\" +\n" +
                "                \".--,       .--,\\n\" +\n" +
                "                \" ( (  \\\\.---./  ) )\\n\" +\n" +
                "                \"  '.__/o   o\\\\__.'\\n\" +\n" +
                "                \"     {=  ^  =}\\n\" +\n" +
                "                \"      >  -  <\\n\" +\n" +
                "                \"     /       \\\\\\n\" +\n" +
                "                \"    //       \\\\\\\\\\n\" +\n" +
                "                \"   //|   .   |\\\\\\\\\\n\" +\n" +
                "                \"   \\\"'\\\\       /'\\\"_.-~^`'-.\\n\" +\n" +
                "                \"      \\\\  _  /--'         `\\n\" +\n" +
                "                \"    ___)( )(___\\n\" +\n" +
                "                \"   (((__) (__)))    高山仰止,景行行止.虽不能至,心向往之。");
    }
}
