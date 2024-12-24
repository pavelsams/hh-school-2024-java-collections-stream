package tasks;

import common.Person;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  //не используемое поле

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй
  /*
  если в метод getNames будет передан неизменяемый лист, то попытка вызова у него remove(0) закончится исключением,
  и метод getNames не выполнит свою работу;

  поэтому используем для обработки подсписок, состоящий из элементов с первого (нумерация с нуля) по последний элемент
  исходного списка;
  обработка происходит если кол-во персон больше чем 1, если одна - то отбрасывается (фальшивая)
  */
  public List<String> getNames(List<Person> persons) {
    if (persons.size() <= 1) {
      return Collections.emptyList();
    }
    return persons.subList(1, persons.size()-1).stream().map(Person::firstName).collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    //оптимизация решения, при создании HashMap повторяющиеся имена не будут включены во множество
    return new HashSet<>(getNames(persons));
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    return Stream.of(person.secondName(),person.firstName(), person.middleName())
        .filter(Objects::nonNull)
        .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    /*
    оптимизация кода, toMap с функцией мержа на случай персон с одинаковым id;
    если применять toMap без функции мержа, то при добавлении персон с одинаковым id
    метод toMap выдаст ошибку
     */
    return persons
        .stream()
        .collect(Collectors.toMap(
            Person::id,
            this::convertPersonToString,
            (sameId1, sameId2) -> sameId1)
        );
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    /*
    оптимизация кода
    */
    return persons1.stream().anyMatch(persons2::contains);
  }

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    /*
    оптимизация кода, лишняя переменная count;
    так же предполагаю что использование поля count плохо при использовании одного и того же
    экземпляра класса Task9 и его метода countEven в разных потоках: получается общая переменная
    для вычисления результата в разных потоках, и при параллельном выполнении это будет приводить
    к неправильным результатам вычисления кол-ва четных чисел (например, один поток инкрементит count,
    делая неправильным результат другого потока, и наоборот)
     */
    return numbers.filter(num -> num % 2 == 0).count();
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  /*
  происходит следующее:
    при инициализации hashset списком integers создается map размером 16384;
    так как хэш целого числа равен самому числу, то эти числа складываются в map(на основе которого построен hashset) в корзины, номера которых
    вычисляются как остаток от деления по модулю самого числа на 16384 (число 1 - в корзину с номером 1, число 2 -в козину с адресом 2 и т.д. в порядке возрастания);
    при вызове toString элементы считываются из map последовательно в порядке возрастания из корзин начиная с самой
    младшей и таким образом при обходе всех корзин будут считаны числа в порядке возрастания

    если добавить в hashset число, большее чем 16384, например, 16386, то адрес корзины будет равен 16386 % 16384 = 2,
    и это число будет положено в корзину с номером 2, и при выводе будет следующее: 1, 2, 16386, 3, 4...
  */
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
