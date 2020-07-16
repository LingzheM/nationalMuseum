package cn.bupt.sse.nmp;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan(value = "cn.bupt.sse.nmp.dao")
@SpringBootApplication
@EnableScheduling
public class NationalMuseumSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(NationalMuseumSystemApplication.class);
    }
}
