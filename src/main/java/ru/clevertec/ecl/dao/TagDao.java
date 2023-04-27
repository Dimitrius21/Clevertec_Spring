package ru.clevertec.ecl.dao;

import ru.clevertec.ecl.entity.Tag;
import java.util.List;

/**
 * Интерфейс описывающий операции над сущностью Tag
 */
public interface TagDao {
    public Tag findById(long id);
    public List<Tag> findAll();
    public Tag  update(Tag tag);
    public Tag save(Tag tag);
    public List<Tag> saveAll(List<Tag> tags);
    public void delete(long id);
}
