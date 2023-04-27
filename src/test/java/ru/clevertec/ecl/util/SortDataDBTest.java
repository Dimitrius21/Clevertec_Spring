package ru.clevertec.ecl.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;


class SortDataDBTest {

    @Test
    void getOrderByStringCorrectData() {
        SortDataDB sort = new SortDataDB(List.of("name-desc", "date-asc"));
        String res = sort.getOrderByString();
        Assertions.assertThat(res).isEqualTo(" ORDER BY name desc, create_date asc");
    }

    @Test
    void getOrderByStringWithInCorrectData() {
        SortDataDB sort = new SortDataDB(List.of("names-desc", "data-asc"));
        String res = sort.getOrderByString();
        Assertions.assertThat(res).isEqualTo("");
    }
}