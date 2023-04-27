package ru.clevertec.ecl.util;

import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.clevertec.ecl.exception.RequestBodyIncorrectException;
import ru.clevertec.ecl.exception.ResourceNotFountException;
import java.util.function.Supplier;

/**
 * Класс с вспомогательными методами
 */
public class Utils {
    /**
     * метод для формирования объекта с ответом на полученный Http-запрос
     * @param responseCode - код ответа типа HttpStatus при нормаьлном завершении запроса
     * @param action - класс реализующий интрефейс Supplier с методом выолняющим логику по полученному http-запросу
     * @param id - id сущности для которой реализуется запрос. Любое значение, если параметр не участвует в запросе
     * @return - сформированный объект для возврата из запроса с соответсвтующим телом, кодом ответа.
     */
    public static ResponseEntity prepareResponse(HttpStatus responseCode, Supplier<Object> action, long id) {
        Object response;
        try {
            response = action.get();
            if (response == null) {
                throw new ResourceNotFountException();
            }
        } catch (HibernateException ex) {
            String mes = "Error of DB - " + ex.getMessage();
            response = new ErrorIfo(mes, 500);
            responseCode = HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (ResourceNotFountException ex) {
            responseCode = HttpStatus.NOT_FOUND;
            String mes = "Resource with id = " + id + " hasn't been found";
            response = new ErrorIfo(mes, id * 10000 + 404);
        }
        catch (RequestBodyIncorrectException ex){
            responseCode = HttpStatus.BAD_REQUEST;
            String mes = "Body of request is not correct";
            response = new ErrorIfo(mes, 400);
        }
        return new ResponseEntity<>(response, null, responseCode);
    }
}
