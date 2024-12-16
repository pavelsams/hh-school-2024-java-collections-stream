package tasks;

import common.Person;
import common.PersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
/*
асимптотика равна O(n)
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    List<Person> orderedPersons = new ArrayList<>();
    if (!personIds.isEmpty()) {
      Set<Person> personsSet = personService.findPersons(personIds);
      Map<Integer, Person> personsMap = personsSet.stream()
                                        .collect(Collectors.toMap(Person::id, person -> person));
      for (Integer id : personIds) {
        orderedPersons.add(personsMap.get(id));
      }
    }
    return orderedPersons;
  }
}
