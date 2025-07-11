package com.keyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author keyu
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class KeYuApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(KeYuApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  科羽项目启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                "██╗  ██╗███████╗██╗   ██╗██╗   ██╗\n" +
                "██║ ██╔╝██╔════╝╚██╗ ██╔╝██║   ██║\n" +
                "█████╔╝ █████╗   ╚████╔╝ ██║   ██║\n" +
                "██╔═██╗ ██╔══╝    ╚██╔╝  ██║   ██║\n" +
                "██║  ██╗███████╗   ██║   ╚██████╔╝\n" +
                "╚═╝  ╚═╝╚══════╝   ╚═╝    ╚═════╝ \n" +
                "                          ");
    }
}
