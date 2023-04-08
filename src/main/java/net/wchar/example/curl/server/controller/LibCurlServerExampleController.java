package net.wchar.example.curl.server.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * LibCurlServerExampleController
 *
 * @author wchar.net
 */
@Log4j2
@RestController
public class LibCurlServerExampleController {
    @Data
    @ToString
    @Builder
    public static class User {
        private String name;
        private String address;
        private Integer age;
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createTime;
    }

    @GetMapping("/getUser")
    public User getUserInfo(String name,
                            String address,
                            @RequestHeader(name = "key1") String key1,
                            @RequestHeader(name = "key2") String key2) {
        log.info("name: {} address: {} key1:{} key2:{}", name, address, key1, key2);
        return User.builder().age(18).name(name).address(address).createTime(LocalDateTime.now()).build();
    }



    @PostMapping("/updateUser")
    public Boolean updateUser(
            User user,
            @RequestHeader(name = "token1") String token1,
            @RequestHeader(name = "token2") String token2,
            MultipartFile[] photoList
    ) throws IOException {

        log.info("\nuser: {} \n token1:{} token2:{}", user, token1, token2);

        if (null != photoList) {
            for (MultipartFile file : photoList) {
                log.info(file.getOriginalFilename());
                file.transferTo(new File("D:\\temp\\" + file.getOriginalFilename()));
            }
        }
        return Boolean.TRUE;
    }

    @PostMapping("/addUser")
    public Boolean addUser(@RequestBody User user,
                           @RequestHeader(name = "token1") String token1,
                           @RequestHeader(name = "token2") String token2) {
        log.info("\nuser:{} \n token1:{} token2:{}", user, token1, token2);
        return Boolean.TRUE;
    }


}
