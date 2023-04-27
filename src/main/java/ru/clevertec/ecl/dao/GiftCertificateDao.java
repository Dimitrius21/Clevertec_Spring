package ru.clevertec.ecl.dao;

import ru.clevertec.ecl.util.SearchDataDB;
import ru.clevertec.ecl.util.SortDataDB;
import ru.clevertec.ecl.entity.GiftCertificate;

import java.util.List;

/**
 * Интерфейс описывающий операции над сущностью GiftCertificate
 */
public interface GiftCertificateDao {
    public GiftCertificate findById(long id);
    public List<GiftCertificate> findAll(SearchDataDB search, SortDataDB sortOrder);
    public List<GiftCertificate> findByTagName(String name, SortDataDB sortOrder);
    public GiftCertificate update(GiftCertificate cert);
    public GiftCertificate save(GiftCertificate cert);
    public void delete(long id);

}
