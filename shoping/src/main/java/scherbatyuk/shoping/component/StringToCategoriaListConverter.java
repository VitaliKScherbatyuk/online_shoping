/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.component;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import scherbatyuk.shoping.domain.Categoria;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *class task to convert a string containing category names into a list of Categoria objects
 **/
@Component
public class StringToCategoriaListConverter implements Converter<String, List<Categoria>> {

    /**
     * method divides the string into categories using a comma and turns them into objects
     * @param source
     * @return
     */
    @Override
    public List<Categoria> convert(String source) {
        // Розбиваємо рядок на категорії за допомогою коми та перетворюємо їх на об'єкти Categoria
        return Arrays.stream(source.split(","))
                .map(categoryTitle -> Categoria.builder().categoryTitle(categoryTitle).build())
                .collect(Collectors.toList());
    }
}
