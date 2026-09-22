import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Patient {
    private static int counter = 0;
    private int id;
    private String passport;
    private String name;
    private LocalDate birthDate;
    private String phone;
    private double temperature;
    private Color color;

    public Patient() {}

    public Patient(String passport, String name, LocalDate birthDate, String phone, double temperature, Color color) {
        this.id = ++counter;
        this.passport = passport;
        this.name = name;
        this.birthDate = birthDate;
        this.phone = phone;
        this.temperature = temperature;
        this.color = color;
    }

    public static Patient createNewPatient(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        System.out.println("Введите паспортные данные в формате 'XXXX XXXXXX'");
        String passport;
        while (true) {
            passport = scanner.nextLine().trim();
            if (passport.length() == 11) break;
            System.out.println("Паспортные данные указаны некорректно");
        }

        System.out.println("Введите имя");
        String name = scanner.nextLine().trim();

        System.out.print("Введите дату рождения в формате ДД.ММ.ГГГГ: ");
        LocalDate birthDate;
        while (true) {
            try {
                birthDate = LocalDate.parse(scanner.nextLine().trim(), formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат даты");
            }
        }

        System.out.println("Введите номер телефона без международного кода");
        String phone = "+7" + scanner.nextLine().trim();

        System.out.println("Введите текущую температуру");
        double temperature;
        while (true) {
            try {
                temperature = Double.parseDouble(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Температура указана некорректно");
            }
        }

        System.out.println("Введите цвет кожи");
        Color color;
        while (true) {
            try {
                List<Integer> colorList = Arrays
                        .stream(scanner.nextLine()
                        .split("\\s+"))
                        .map(Integer::parseInt)
                        .toList();
                color = new Color(colorList.get(0), colorList.get(1), colorList.get(2));
                break;
            } catch (Exception e) {
                System.out.println("Цвет указан некорректно");
            }
        }

        return new Patient(passport, name, birthDate, phone, temperature, color);
    }

    public void setTemperature(double newTemperature){
        temperature = newTemperature;
    }

    @Override
    public String toString() {
        return "Patient(id = %d, passport = %s, name = %s, birth_date = %s, phone = %s, temperature = %.1f, color = %s)"
                .formatted(id, passport, name, birthDate, phone, temperature, color);
    }

    public int getId() {
        return id;
    }
}