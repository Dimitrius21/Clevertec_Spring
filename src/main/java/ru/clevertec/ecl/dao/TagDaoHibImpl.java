package ru.clevertec.ecl.dao;

import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entity.Tag;
import java.util.List;

/**
 * Класс реализующий опрерации с базой данных для сущности Tag
 */
@Repository
@Transactional
public class TagDaoHibImpl implements TagDao {
    @PersistenceContext
    private Session session;

    @Override
    public Tag findById(long id) {
        Tag tag = session.find(Tag.class, id);
        return tag;
    }

    public List<Tag> findAll() {
        List<Tag> tags = session.createQuery("FROM Tag t", Tag.class).getResultList();
        return tags;
    }

    @Override
    public Tag save(Tag tag) {
        session.persist(tag);
        return tag;
    }

    @Override
    public List<Tag> saveAll(List<Tag> tags) {
        tags.forEach(session::persist);
        return tags;
    }

    @Override
    public Tag update(Tag tag) {
        return session.merge(tag);
    }

    @Override
    public void delete(long id) {
        Tag tag = session.find(Tag.class, id);
        session.createNativeQuery("DELETE FROM certificate_tag WHERE tag_id = ?", Tag.class)
                .setParameter(1, id)
                .executeUpdate();
        session.remove(tag);
    }
}
