package ru.clevertec.ecl.util;

import lombok.AllArgsConstructor;

/**
 *  Класс для создания строки условия поиска для добавления к запросу при извлечении из DB по входным параметрам Http-запроса
 */

@AllArgsConstructor
public class SearchDataDB {
    private String field;
    private String text;

    /**
     * Фрмирует строку
     * @return строка с условиями поиска для добавления в sql-запрос
     */
    public String getSearchString() {
        String likeString = "";
        if (("name".equals(field) || "description".equals(field)) && !text.isEmpty()) {
            likeString = " WHERE " + field + " LIKE '%" + text + "%'";
        }
        return likeString;
    }
}
