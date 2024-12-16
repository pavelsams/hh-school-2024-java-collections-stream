package tasks;

import common.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
  мне кажется исключение фальшивой персоны должно происходить в коде,
  обрабатывающем результат выдачи эластика, производить удаление здесь - некорректно,
  так как, например, методу может быть передан список персон без фальшивой персоны
  при использовании без эластика
  */
  public List<String> getNames(List<Person> persons) {
    if (persons.isEmpty()) {
      return Collections.emptyList();
    }
    persons.remove(0);
    return persons.stream().map(Person::firstName).collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    //оптимизация решения, при создании HashMap повторяющиеся имена не будут включены во множество
    return new HashSet<>(getNames(persons));
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    String result = "";
    if (person.secondName() != null) {
      result += person.secondName() + " ";
    }

    if (person.firstName() != null) {
      result += person.firstName() + " ";
    }

    //здесь нужно отчество, а не фамилия
    if (person.middleName() != null) {
      result += person.middleName();
    }
    return result;
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    /*
    оптимизация кода
     */
    return persons.stream().collect(Collectors.toMap(Person::id, Person::firstName));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    /*
    чтобы сократить временную сложность алгоритма c O(n2) до O(n) -
    применяем стрим с методом filter(выполняется за O(n))
    и множества HashSet с методом contains (выполняется за O(1))
    */
    Set<Person> persons1Set = new HashSet<>(persons1);
    Set<Person> persons2Set = new HashSet<>(persons2);
    Set<Person> samePersons = persons1Set.stream()
                                         .filter(persons2Set::contains)
                                         .collect(Collectors.toSet());
    if (samePersons.isEmpty()) {
      return false;
    } else {
      return true;
    }
  }

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    /*
    оптимизация кода, лишняя переменная count
     */
    return numbers.filter(num -> num % 2 == 0).count();
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  /*
  причиной этого служит то, что хэш целых чисел равен этому же числу, поэтому числа складываются во множество
  в порядке возрастания
  */
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
