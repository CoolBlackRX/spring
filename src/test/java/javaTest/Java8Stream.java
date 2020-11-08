package javaTest;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhaohq
 * @date 2020/11/8
 */
public class Java8Stream {
    List<String> strings = Arrays.asList("abc","","bc","efg","abcd","","jkl");

    @Test
    public void filter() {
        List<String> filter = this.strings.stream().filter(s -> s.contains("f")).collect(Collectors.toList());
        filter.forEach(System.out::println);
    }
}
