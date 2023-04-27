package ru.clevertec.ecl.service;

import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dao.GiftCertificateDao;
import ru.clevertec.ecl.dao.TagDao;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.exception.RequestBodyIncorrectException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Класс реализующий операции из слоя Сервис для GiftCertificate
 */
@Service
public class CertificateService {
    GiftCertificateDao certDao;
    TagDao tagDao;

    public CertificateService(GiftCertificateDao certDao, TagDao tagDao) {
        this.certDao = certDao;
        this.tagDao = tagDao;
    }

    /**
     * Подготовка и сохранение в базе данных полученного в http-запросе GiftCertificate
     * @param certInRequest объект GiftCertificate извлеченный из тела запроса на сохранение
     * @return GiftCertificate сохраненный в DB c id
     */
    public GiftCertificate createCertificate(GiftCertificate certInRequest) {
        if (Objects.isNull(certInRequest)) {
            throw new RequestBodyIncorrectException();
        }
        GiftCertificate certificate = certInRequest;
        List<Tag> tags = certificate.getTags();
        LocalDateTime now = LocalDateTime.now();
        certificate.setLastUpdateDate(now);
        certificate.setCreateDate(ZonedDateTime.of(now, ZoneId.systemDefault()));
        List<Tag> tagsForSave = tags.stream().filter(t -> t.getId() < 1).toList();
        tagDao.saveAll(tagsForSave);
        certDao.save(certificate);
        return certificate;
    }

    /**
     * Подготовка и обновление в базе данных полученного в http-запросе GiftCertificate
     * @param certInRequest объект GiftCertificate извлеченный из тела запроса на обновление
     * @return GiftCertificate сохраненный в DB
     */
    public GiftCertificate updateCertAllFields(GiftCertificate certInRequest) {
        if (Objects.isNull(certInRequest)) {
            throw new RequestBodyIncorrectException();
        }
        GiftCertificate certInDB = certDao.findById(certInRequest.getId());
        if (certInRequest.getName() != null && !certInRequest.getName().isEmpty()) {
            certInDB.setName(certInRequest.getName());
        }
        if (certInRequest.getDescription() != null && !certInRequest.getDescription().isEmpty()) {
            certInDB.setDescription(certInRequest.getDescription());
        }
        if (certInRequest.getPrice() > 0) {
            certInDB.setPrice(certInRequest.getPrice());
        }
        if (certInRequest.getDuration() > 0) {
            certInDB.setDuration(certInRequest.getDuration());
        }
        certInDB.setLastUpdateDate(LocalDateTime.now());
        List<Tag> tagsInRequest = certInRequest.getTags();
        List<Tag> tagsInDb = certInDB.getTags();
        //обновляем тэги GiftCertificate из DB на имещиеся в запросе
        for (Tag tag : tagsInRequest) {
            long id = tag.getId();
            Optional<Tag> tagInDb = tagsInDb.stream().filter(t -> t.getId() == id).findFirst();
            if (tagInDb.isPresent()) {
                updateTagAllField(tagInDb.get(), tag);
            } else {
                certInDB.addTag(tag);
            }
        }
        certInDB = certDao.update(certInDB);
        return certInDB;
    }

    /**
     * метод обновляет Тэги GiftCertificate извлеченнгые из DB на пришедшие в запросе на обновление
     * @param tagInDB - Тэги GiftCertificate прочитанного из DB
     * @param tagInRequest - Тэги GiftCertificate из пришедшего запроса
     */
    private void updateTagAllField(Tag tagInDB, Tag tagInRequest) {
        if (!Objects.isNull(tagInRequest.getName()) || !"".equals(tagInRequest.getName())) {
            tagInDB.setName(tagInRequest.getName());
        }
    }
}
