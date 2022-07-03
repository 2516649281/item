package com.chunfeng;

import com.chunfeng.entity.CreateWork;
import com.chunfeng.service.IWorkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class LogMapperTest {

    @Autowired(required = false)
    private IWorkService workService;

    /**
     * 时间格式
     */
    @Value("${date.format}")
    private String dateFormat;

    @Test
    void selectAll() {
        System.out.println(workService.selectWork(1, 10));
    }

    @Test
    void insert() {
        CreateWork createWork = new CreateWork("java", "111", 1L, 1L);
        System.out.println(workService.addWork(createWork));
    }

    @Test
    void update() {
        CreateWork createWork = new CreateWork(1511157057750380545L, "python", "222", 1L, 1L);
        System.out.println(workService.updateWorkById(createWork));
    }

    @Test
    void delete() {
        //System.out.println(workService.deleteWork(1511157057750380546L, true));
    }

    @Test
    void dateTest() {
        System.out.println(new SimpleDateFormat(dateFormat).format(new Date()));
    }
}
