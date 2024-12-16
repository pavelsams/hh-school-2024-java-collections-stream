package tasks;

import common.Person;
import common.PersonService;
import common.PersonWithResumes;
import common.Resume;

//import java.util.*;
import java.util.*;
import java.util.stream.Collectors;

/*
  Еще один вариант задачи обогащения
  На вход имеем коллекцию персон
  Сервис умеет по personId искать их резюме (у каждой персоны может быть несколько резюме)
  На выходе хотим получить объекты с персоной и ее списком резюме
 */
public class Task8 {
  private final PersonService personService;

  public Task8(PersonService personService) {
    this.personService = personService;
  }

  public Set<PersonWithResumes> enrichPersonsWithResumes(Collection<Person> persons) {
    Set<PersonWithResumes> personsWithResumes;

    List<Integer> personIds = persons.stream().map(Person::id).toList();
    Set<Resume> personsResumes = personService.findResumes(personIds);

    Map<Integer, PersonWithResumes> mapPersonsWithResumes = new HashMap<>();
    for(Person person : persons) {
      mapPersonsWithResumes.put(person.id(), new PersonWithResumes(person, new HashSet<>()));
    }

    for(Resume resume : personsResumes) {
      Integer personId = resume.personId();
      Set<Resume> personResumes = mapPersonsWithResumes.get(personId).resumes();
      personResumes.add(resume);
    }

    /*for(Integer personId : personIds) {
      PersonWithResumes personWithResumes = mapPersonsWithResumes.get(personId);
      personsWithResumes.add(personWithResumes);
    }*/
    personsWithResumes = new HashSet<>(mapPersonsWithResumes.values());
    return personsWithResumes;
  }
}
