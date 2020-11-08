package javaTest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.Test;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhaohq
 * @date 2020/11/8
 */
public class Java8Stream {
    List<String> strings = Arrays.asList("abc", "bc", "efg", "abcd", "", "jkl", "jkl");
    List<User> users = new ArrayList<>();

    @Before
    public void name() {
        users.add(new User(1, "zhaohq", 23));
        users.add(new User(1, "zhaohq", 23));
        users.add(new User(1, "zhaohq", 24));
        users.add(new User(2, "zhaohq", 23));
    }

    /**
     * filter 过滤元素
     */
    @Test
    public void filter() {
        List<String> filter = this.strings.stream().filter(s -> s.contains("f")).collect(Collectors.toList());
        filter.forEach(System.out::println);
    }

    /**
     * distinct 去除重复元素
     */
    @Test
    public void distinct() {
        List<String> filter = this.strings.stream().distinct().collect(Collectors.toList());
        filter.forEach(System.out::println);
    }

    /**
     * 去除重复对象
     */
    @Test
    public void distinctObject() {
        // 由于创建对象的内存地址不重复，所以不会去重（删除下面对象的equals and hashCode方法查看运行结果）
        // 重写User的equals and hashCode 方法，就可以实现对象的去重
        // Lombok 中的 @Data注解 里面包含 @EqualsAndHashCode 注解 ，可以直接解决此问题
        List<User> userList1 = users.stream().distinct().collect(Collectors.toList());
        userList1.forEach(System.out::println);
        System.out.println("=====================");
        List<User> users2 = new ArrayList<>();
        // 对象的去重也可以利用List.contains()方法实现,前提都需要重写equals 和 hashCode方法
        users.forEach(e -> {
            if (!users2.contains(e)) {
                users2.add(e);
            }
        });
        users2.forEach(System.out::println);
    }

    /**
     * limit 截取流中的前 n 个元素
     */
    @Test
    public void limit() {
        List<String> limit = this.strings.stream().limit(3).collect(Collectors.toList());
        limit.forEach(System.out::println);
        System.out.println("=====================");
        List<User> userList = users.stream().limit(2).collect(Collectors.toList());
        userList.forEach(System.out::println);
    }

    /**
     * skip 跳过流中的前 n 个元素
     */
    @Test
    public void skip() {
        List<String> skip = this.strings.stream().skip(3).collect(Collectors.toList());
        skip.forEach(System.out::println);
        System.out.println("=====================");
        List<User> userList = users.stream().skip(2).collect(Collectors.toList());
        userList.forEach(System.out::println);
    }

    /**
     * map 对流的元素做统一性的处理
     * concat 添加后缀函数
     */
    @Test
    public void map() {
        // 给流的元素加上前缀和后缀
        List<String> map = strings.stream().map(s -> s.concat("zhaohq")).collect(Collectors.toList());
        map.stream().map(s -> "大帅哥" + s).collect(Collectors.toList()).forEach(System.out::println);
        // peek 改变原来对象的状态
        List<User> userListPeek = users.stream().peek(user -> user.setName("zhaohq是大帅哥")).collect(Collectors.toList());
        System.out.println("=====================");
        userListPeek.forEach(System.out::println);
        // map 不改变原来对象的状态
        List<User> userListMap = users.stream().map(user -> {
            if (user.getAge() == 23) {
                return user;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        System.out.println("=====================");
        userListMap.forEach(System.out::println);
        List<User> userListFilter = users.stream().filter(user -> user.getAge() == 23).collect(Collectors.toList());
        System.out.println("=====================");
        userListFilter.forEach(System.out::println);
    }

    /**
     * flatmap 流的扁平化操作
     */
    @Test
    public void flotMap() {
        List<String> strListFlatmap = Arrays.asList("abc", "bc", "efg", "abcd", "", "jkl", "jkl");
        Stream<Character> flatted = strListFlatmap.stream().flatMap(Java8Stream::getCharacterByString);
        flatted.forEach(System.out::print);
        System.out.println("\n" + "=====================");
        List<String> strListMap = Arrays.asList("abc", "bc", "efg", "abcd", "", "jkl", "jkl");
        Stream<Stream<Character>> mapped = strListMap.stream().map(Java8Stream::getCharacterByString);
        mapped.forEach(e -> e.forEach(System.out::print));
    }

    /**
     * 将字符串转换成字节流
     *
     * @param str 字符串
     * @return Stream<Character>
     */
    private static Stream<Character> getCharacterByString(String str) {
        List<Character> characterList = new ArrayList<>();
        for (Character character : str.toCharArray()) {
            characterList.add(character);
        }
        return characterList.stream();
    }

    /**
     * 排序
     */
    @Test
    public void sorted() {
        List<String> strList = Arrays.asList("abc", "bc", "bcd", "bcf", "efg", "abcd", "", "jkl", "bjkl");
        strList.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).forEach(System.out::println);
        strList.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("===================");
        users.stream().sorted(Comparator.comparing(User::getAge)).collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("===================");
        // Collectors.groupingBy 是分组，不是排序
        Map<Integer, List<User>> collect = users.stream().collect(Collectors.groupingBy(User::getAge));
        collect.forEach((key, value) -> {
            System.out.println(key);
            System.out.println(value);
        });
        // 数字排序
        List<Integer> array = Arrays.asList(11, 30, 200, 1, 3, 4, 5);
        System.out.println("============================");
        array.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList()).forEach(System.out::println);
        // 汉字排序
        List<String> chineseList = Arrays.asList("赵三", "张三", "李四", "王五", "李哥", "张哥", "钱三");
        // 无法正确排序
        System.out.println("============================");
        chineseList.stream().sorted().collect(Collectors.toList()).forEach(System.out::println);
        // 正确排序
        System.out.println("============================");
        chineseList.stream().sorted(Collator.getInstance(Locale.CHINESE)).collect(Collectors.toList()).forEach(System.out::println);
    }

    /**
     * 集合中有一个元素满足消费函数 return true
     * return boolean
     */
    @Test
    public void antMatch() {
        System.out.println(strings.stream().anyMatch(s -> s.contains("b")));
    }

    /**
     * 集合中所有元素满足消费函数 return true
     * return boolean
     */
    @Test
    public void allMatch() {
        System.out.println(strings.stream().allMatch(s -> s.contains("b")));
    }

    /**
     * 判斷集合中所有元素都不满足消费函数 return true
     * return boolean
     */
    @Test
    public void noneMatch() {
        System.out.println(strings.stream().noneMatch(s -> s.contains("b")));
    }

    /**
     * 返回集合中的任意一个元素
     */
    @Test
    public void findAny() {
        System.out.println(strings.stream().findAny().orElse(null));
    }

    /**
     * 返回集合中的任第一个元素
     */
    @Test
    public void findFirst() {
        System.out.println(strings.stream().findFirst().orElse(null));
    }

    /**
     * 将流转换成Set
     */
    @Test
    public void collectToSet() {
        // strings.stream().collect(Collectors.toSet()).forEach(System.out::println);
        new HashSet<>(strings).forEach(System.out::println);
    }

    /**
     * 将流转换成Map
     * 后面的函数，处理重复 key 的问题
     */
    @Test
    public void collectToMap() {
        strings.stream().collect(Collectors.toMap(e -> e, e -> e, (v1, v2) -> v1)).forEach((key, value) -> System.out.println(key + ":" + value));
    }

    /**
     * 将流中的元素反复结合起来得到一个结果
     */
    @Test
    public void reduce() {
        Optional<String> optional1 = strings.stream().reduce((acc, item) -> acc + item);
        optional1.ifPresent(System.out::println);


        boolean seen = false;
        StringBuilder acc = null;
        for (String string : strings) {
            if (!seen) {
                seen = true;
                acc = new StringBuilder(string);
            } else {
                acc.append(string);
            }
        }
        Optional<String> optional2 = seen ? Optional.of(acc.toString()) : Optional.empty();
        optional2.ifPresent(System.out::println);
    }

}

@Data
@NoArgsConstructor
@AllArgsConstructor
class User {
    private Integer id;
    private String name;
    private Integer age;
/*    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(age, user.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }*/
}