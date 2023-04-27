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
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.util.SearchDataDB;
import ru.clevertec.ecl.util.SortDataDB;
import java.util.List;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@ActiveProfiles("test")
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GiftCertificateDaoHibImplTest {
    @Autowired
    GiftCertificateDao certDao;

    @Test
    @Order(10)
    void findById() {
        GiftCertificate res = certDao.findById(1);
        GiftCertificate exp = new GiftCertificate(1, "Rest1", "sauna", 1900, 10, null, null);
        Tag tag = new Tag(1, "relax");
        exp.addTag(tag);
        Assertions.assertThat(res).isEqualTo(exp);
    }

    @Test
    @Order(20)
    void findAll() {
        SortDataDB sort = new SortDataDB(null);
        List<GiftCertificate> certificates = certDao.findAll(null, sort);
        Assertions.assertThat(certificates).hasSize(7);
    }

    @Test
    @Order(30)
    void findAllWithSearch() {
        SearchDataDB search = new SearchDataDB("description", "spa");
        SortDataDB sort = new SortDataDB(null);
        List<GiftCertificate> certificates = certDao.findAll(search, sort);
        Assertions.assertThat(certificates).hasSize(2)
                .extracting(GiftCertificate::getName).contains("Rest2", "Rest3");
    }
    @Test
    @Order(31)
    void findAllWithSearchAndSort() {
        SearchDataDB search = new SearchDataDB("name", "Rest");
        SortDataDB sort = new SortDataDB(List.of("date-asc", "name-desc"));
        List<GiftCertificate> certificates = certDao.findAll(search, sort);
        Assertions.assertThat(certificates).hasSize(3)
                .extracting(GiftCertificate::getName).containsSequence("Rest2", "Rest1", "Rest3");
    }

    @Test
    @Order(40)
    void findByTagName() {
        SortDataDB sort = new SortDataDB(null);
        List<GiftCertificate> certs = certDao.findByTagName("motor", sort);
        Assertions.assertThat(certs).hasSize(2)
                .extracting(GiftCertificate::getName).contains("Auto", "Quad bike");
    }

    @Test
    @Order(50)
    void update() {
        GiftCertificate cert = certDao.findById(3);
        cert.setDescription("all inclusive");
        cert.getTags().get(0).setName("relax!!!");
        certDao.update(cert);
        GiftCertificate res = certDao.findById(3);
        Assertions.assertThat(res).isEqualTo(cert);
    }

    @Test
    @Order(60)
    void save() {
        GiftCertificate cert = new GiftCertificate(0, "Quad bike", "Road 10km, 2 person", 9500, 40, null, null);
        Tag tag1 = new Tag(4, "motor");
        cert.addTag(tag1);
        GiftCertificate savedCert = certDao.save(cert);
        cert.setId(savedCert.getId());

        GiftCertificate res = certDao.findById(savedCert.getId());
        Assertions.assertThat(res).isEqualTo(cert);
    }

    @Test
    @Order(70)
    void delete() {
        certDao.delete(3);
        GiftCertificate res = certDao.findById(3);
        Assertions.assertThat(res).isNull();
    }
}