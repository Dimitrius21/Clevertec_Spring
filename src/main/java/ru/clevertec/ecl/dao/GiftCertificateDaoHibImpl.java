package ru.clevertec.ecl.dao;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.util.SearchDataDB;
import ru.clevertec.ecl.util.SortDataDB;
import ru.clevertec.ecl.entity.GiftCertificate;
import java.util.*;


/**
 * Класс реализующий опрерации с базой данных для сущности GiftCertificate
 */
@Repository
@Transactional
public class GiftCertificateDaoHibImpl implements GiftCertificateDao {
    @PersistenceContext
    private Session session;
    private final String requestFindAll = "SELECT * FROM gift_certificate";
    private final String requestFindByTagName = "select distinct cert.* from gift_certificate cert " +
            "inner join certificate_tag ct " +
            "on cert.id = ct.certificate_id " +
            "inner join tag t " +
            "on ct.tag_id = t.id " +
            "where cert.id IN (select certificate_id from tag t " +
            "inner join certificate_tag ct " +
            "on t.id=ct.tag_id " +
            "where t.name= ? )";

    @Override
    public GiftCertificate findById(long id) {
        GiftCertificate cert = session.find(GiftCertificate.class, id);
        return cert;
    }

    @Override
    public List<GiftCertificate> findAll(SearchDataDB search, SortDataDB sortOrder) {
        String request = requestFindAll;
        if (search != null) {
            request += search.getSearchString();
        }
        request += sortOrder.getOrderByString();
        List<GiftCertificate> certificates = session.createNativeQuery(request, GiftCertificate.class)
                .getResultList();
        return certificates;
    }

    @Override
    public List<GiftCertificate> findByTagName(String name, SortDataDB sortOrder) {
        String request = requestFindByTagName;
        request += sortOrder.getOrderByString();
        Query query = session.createNativeQuery(request, GiftCertificate.class);
        List<GiftCertificate> res = query.setParameter(1, name).getResultList();
        return res;
    }

    @Override
    public GiftCertificate update(GiftCertificate cert) {
        return session.merge(cert);
    }

    @Override
    public GiftCertificate save(GiftCertificate cert) {
        session.persist(cert);
        return cert;
    }

    @Override
    public void delete(long id) {
        GiftCertificate cert = session.find(GiftCertificate.class, id);
        session.remove(cert);
    }
}
