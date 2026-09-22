import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    List<Patient> memory = new ArrayList<Patient>();

    while (true) {
        System.out.println("""
                1. Добавить нового пациента
                2. Вывести список информации о пациентах
                3. Добавить актуальную температуру пациента
                0. Выход""");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> {
                Patient newPatient = Patient.createNewPatient(scanner);
                System.out.println(newPatient);
                memory.add(newPatient);
            }
            case "2" -> {
                if (!memory.isEmpty()) {
                    for (Patient patient : memory) {
                        System.out.println(patient);
                    }
                } else {
                    System.out.println("Список пациентов пуст");
                }
            }
            case "3" -> {
                System.out.println("Выберите id пациента");
                try {
                    int targetId = scanner.nextInt();
                    for (Patient patient : memory) {
                        if (patient.getId() == targetId) {
                            System.out.println("Введите актуально значение температуры");
                            patient.setTemperature(scanner.nextDouble());
                            break;
                        }
                    }
                }
                catch (Exception e){
                    System.out.println("Температура указана некорректно");
                }

            }

            case "0" -> {
                return;
            }
            default -> System.out.println("Неверная команда");
        }
    }
}

