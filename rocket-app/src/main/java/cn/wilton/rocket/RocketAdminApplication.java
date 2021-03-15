package cn.wilton.rocket;
import cn.wilton.rocket.common.annotation.EnableRocketLettuceRedis;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动器
 * @author Ranger
 * @since 2021/2/15
 * @email wilton.icp@gmail.com
 */
@EnableRocketLettuceRedis
@SpringBootApplication(scanBasePackages = {"cn.wilton.rocket","cn.wilton.framework.security"})
public class RocketAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(RocketAdminApplication.class, args);
    }
}
