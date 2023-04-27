package ru.clevertec.ecl.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.service.CertificateService;
import ru.clevertec.ecl.util.SearchDataDB;
import ru.clevertec.ecl.util.SortDataDB;
import ru.clevertec.ecl.dao.GiftCertificateDao;
import ru.clevertec.ecl.dao.TagDao;
import ru.clevertec.ecl.entity.GiftCertificate;
import java.util.List;
import java.util.Optional;
import static ru.clevertec.ecl.util.Utils.prepareResponse;

/**
 * Класс реализующий слой контроллера для запросов по GiftCertificate
 */
@RestController
@RequestMapping(value = "/cert")
public class CertificateController {

    GiftCertificateDao certDao;
    TagDao tagDao;
    CertificateService certService;

    public CertificateController(GiftCertificateDao certDao, TagDao tagDao, CertificateService certService) {
        this.certDao = certDao;
        this.tagDao = tagDao;
        this.certService = certService;
    }

    @GetMapping("/{id}")
    public ResponseEntity findCertificate(@PathVariable long id) {
        return prepareResponse(HttpStatus.OK, ()->certDao.findById(id), id);
    }

    @GetMapping
    public ResponseEntity findAllCertificate(@RequestParam Optional<List<String>> sort) {
        List<String> sortParams = sort.orElse(null);
        SortDataDB sortOrder = new SortDataDB(sortParams);
        return prepareResponse(HttpStatus.OK, ()->certDao.findAll(null, sortOrder), 0);
    }

    @GetMapping("/has")
    public ResponseEntity findAllCertificateWithSearch(@RequestParam(defaultValue = "") String field,
                                                       @RequestParam(defaultValue = "") String text,
                                                       @RequestParam Optional<List<String>> sort) {
        SearchDataDB search = new SearchDataDB(field, text);
        List<String> sortParams = sort.orElse(null);
        SortDataDB sortOrder = new SortDataDB(sortParams);
        return prepareResponse(HttpStatus.OK, ()->certDao.findAll(search, sortOrder), 0);
    }

    @GetMapping("/tag/{name}")
    public ResponseEntity findCertificateWithTag(@PathVariable String name, @RequestParam Optional<List<String>> sort ) {
        SortDataDB sortOrder = new SortDataDB(sort.orElse(null));
        return prepareResponse(HttpStatus.OK, ()->certDao.findByTagName(name, sortOrder), 0);
    }

    @PostMapping
    public ResponseEntity testCertificate(@RequestBody Optional<GiftCertificate> certInRequest) {
        return prepareResponse(HttpStatus.CREATED, ()->certService.createCertificate(certInRequest.orElse(null)), 0);
    }

    @PatchMapping
    public ResponseEntity updateCertAllFields(@RequestBody Optional<GiftCertificate> certInRequest) {
        return prepareResponse(HttpStatus.OK, ()->certService.updateCertAllFields(certInRequest.orElse(null)), 0);
    }

    @DeleteMapping("/{id}")
    public void deleteCertificate(@PathVariable long id) {
        prepareResponse(HttpStatus.OK, ()->{
            certDao.delete(id);
            return id;
        }, id);
    }
}