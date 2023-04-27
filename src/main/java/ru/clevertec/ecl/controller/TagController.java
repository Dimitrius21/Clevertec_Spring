package ru.clevertec.ecl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dao.TagDao;
import ru.clevertec.ecl.entity.Tag;
import static ru.clevertec.ecl.util.Utils.prepareResponse;

/**
 * Класс реализующий слой контроллера для запросов по Tag
 */
@RestController
@RequestMapping(value = "/tag")
public class TagController {
    @Autowired
    TagDao dao;

    @GetMapping("/{id}")
    public ResponseEntity getTestData(@PathVariable long id) {
        return prepareResponse(HttpStatus.OK, () -> dao.findById(id), id);
    }

    @GetMapping
    public ResponseEntity getAll() {
        return prepareResponse(HttpStatus.OK, () -> dao.findAll(), 0);
    }

    @PostMapping
    public ResponseEntity createTag(@RequestBody Tag tag) {
        return prepareResponse(HttpStatus.CREATED, () -> dao.save(tag), 0);
    }

    @PutMapping
    public ResponseEntity updateTag(@RequestBody Tag tag) {
        return prepareResponse(HttpStatus.OK, () -> dao.update(tag), 0);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTag(@PathVariable long id) {
        return prepareResponse(HttpStatus.OK, () -> {
            dao.delete(id);
            return id;
        }, id);
    }
}
