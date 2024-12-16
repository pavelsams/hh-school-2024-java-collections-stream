package tasks;

import common.Company;
import common.Vacancy;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/*
Из коллекции компаний необходимо получить всевозможные различные названия вакансий
 */
public class Task7 {

  public static Set<String> vacancyNames(Collection<Company> companies) {
    Set<String> vacancyNames;
    Set<Vacancy> vacancies = new HashSet<>();
    for(Company company : companies) {
      vacancies.addAll(company.getVacancies());
    }
    vacancyNames = vacancies.stream().map(Vacancy::getTitle).collect(Collectors.toSet());
    return vacancyNames;
  }

}
