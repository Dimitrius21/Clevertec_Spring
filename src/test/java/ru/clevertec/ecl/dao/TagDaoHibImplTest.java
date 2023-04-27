package ru.clevertec.ecl.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.clevertec.ecl.config.TestConfig;
import ru.clevertec.ecl.entity.Tag;

import java.util.List;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@ActiveProfiles("test")
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TagDaoHibImplTest {

    @Autowired
    TagDao tagDao;

    @Test
    @Order(10)
    void findById() {
        Tag exp = new Tag(2, "sport");
        Tag res = tagDao.findById(2);
        Assertions.assertThat(res).isEqualTo(exp);
    }

    @Test
    @Order(20)
    void findAll() {
        List<Tag> tags = tagDao.findAll();
        Assertions.assertThat(tags).hasSize(4);
    }

    @Test
    @Order(30)
    void save() {
        Tag tag  = new Tag(0, "water");
        Tag savedTag = tagDao.save(tag);
        Tag res = tagDao.findById(savedTag.getId());
        Assertions.assertThat(res.getName()).isEqualTo(tag.getName());
    }

    @Test
    @Order(40)
    void update() {
        Tag tag = tagDao.findById(1);
        tag.setName("together");
        tagDao.update(tag);
        Tag res = tagDao.findById(tag.getId());
        Assertions.assertThat(res).isEqualTo(tag);
    }

    @Test
    @Order(50)
    void delete() {
        tagDao.delete(3);
        Tag res = tagDao.findById(3);
        Assertions.assertThat(res).isNull();
    }
}
