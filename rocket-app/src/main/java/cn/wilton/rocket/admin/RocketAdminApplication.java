package cn.wilton.rocket.admin;
import cn.wilton.rocket.common.annotation.EnableRocketLettuceRedis;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
/**
 * 启动器
 * @Author: Ranger
 * @Email: wilton.icp@gmail.com
 */
@Slf4j
@EnableRocketLettuceRedis
@SpringBootApplication(scanBasePackages = {"cn.wilton.rocket.admin","cn.wilton.framework.security","cn.wilton.rocket.common"})
public class RocketAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(RocketAdminApplication.class, args);
    }
}
