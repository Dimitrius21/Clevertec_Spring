package ru.clevertec.ecl.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchDataDBTest {

    @Test
    void getSearchString() {
        SearchDataDB search = new SearchDataDB("description", "sauna");
        String res = search.getSearchString();
        Assertions.assertThat(res).isEqualTo(" WHERE description LIKE '%sauna%'");
    }
}