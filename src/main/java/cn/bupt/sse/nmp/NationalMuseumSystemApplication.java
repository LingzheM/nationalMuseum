package cn.bupt.sse.nmp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "cn.bupt.sse.nmp")
@EntityScan(value = "cn.bupt.sse.nmp.entity")
public class NationalMuseumSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(NationalMuseumSystemApplication.class);
    }
}
