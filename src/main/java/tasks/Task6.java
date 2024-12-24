package tasks;

import common.Area;
import common.Person;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 {

  private static String getDescriptionPerson(Person person, Area area) {
    return person.firstName() + " - " + area.getName();
  }

  public static Set<String> getPersonDescriptions(Collection<Person> persons,
                                                  Map<Integer, Set<Integer>> personAreaIds,
                                                  Collection<Area> areas) {
    Set<String> personsDescriptions = new HashSet<>();
    Map<Integer, Area> areasMap = areas.stream().collect(Collectors.toMap(Area::getId, area -> area));

    for(Person person : persons) {
      Set<Integer> areaIds = personAreaIds.get(person.id());
      for(Integer areaId : areaIds) {
        personsDescriptions.add(getDescriptionPerson(person, areasMap.get(areaId)));
      }
    }
    return personsDescriptions;
  }
}
