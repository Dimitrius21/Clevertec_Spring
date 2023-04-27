package ru.clevertec.ecl.util;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс создают строку для добавления к запросу на сортировку данных при извлечении из DB по входным параметрам Http-запроса
 */
public class SortDataDB {
    private List<String[]> sortFieldsList = new ArrayList<>();
    private Map<String, String> accordance = Map.of("name", "name", "date", "create_date");

    public SortDataDB(List<String> params) {
        if (params != null) {
            sortFieldsList = params.stream().map(p -> p.split("-")).filter(this::isParamCorrect).toList();
        }
    }

    /**
     * Check correct of input request params and translate field for sort of request param to field name in DB
     *
     * @param rowParam - array with request param - sorting field [0] and type of sort [1]
     * @return true if params are correct
     */
    private boolean isParamCorrect(String[] rowParam) {
        boolean res = true;
        String param = accordance.get(rowParam[0]);
        if (Objects.isNull(param)) {
            res = false;
        } else {
            rowParam[0] = param;
            if (!("asc".equalsIgnoreCase(rowParam[1]) || "desc".equalsIgnoreCase(rowParam[1]))) {
                res = false;
            }
        }
        return res;
    }

    /**
     * Формирует строку сортировки
     * @return строка с условиями сортировки для добавления к sql-запросу
     */
    public String getOrderByString() {
        String orderString = "";
        if (sortFieldsList.size() > 0) {
            orderString = sortFieldsList.stream().map(item -> item[0] + " " + item[1])
                    .collect(Collectors.joining(", ", " ORDER BY ", ""));
        }
        return orderString;
    }
}
