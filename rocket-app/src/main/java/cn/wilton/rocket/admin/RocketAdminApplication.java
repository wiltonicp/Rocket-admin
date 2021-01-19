package cn.wilton.rocket.admin;
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
@MapperScan("cn.wilton.rocket.admin.mapper")
@SpringBootApplication
public class RocketAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(RocketAdminApplication.class, args);
    }
}
