package cn.bupt.sse.nmp;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan(value = "cn.bupt.sse.nmp.dao")
@SpringBootApplication
@EnableScheduling
public class NationalMuseumSystemApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(NationalMuseumSystemApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(NationalMuseumSystemApplication.class);
    }


}
