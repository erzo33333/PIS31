import model.Shape;
import parser.ShapeParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Path FILE_PATH = Path.of("src\\shapes.txt");

    private static final Scanner SCANNER = new Scanner(System.in);
    private static List<Shape> shapes = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("=== Shape CLI ===");
        System.out.println("Файл: " + FILE_PATH.toAbsolutePath());

        if (!loadShapes()) {
            System.out.println("(файл не загружен, попробуйте пункт 3)");
        }

        boolean running = true;
        while (running) {
            printMenu();
            String choice = SCANNER.nextLine().trim();
            switch (choice) {
                case "1" -> printShapes();
                case "2" -> printCount();
                case "3" -> loadShapes();
                case "0", "q", "Q" -> running = false;
                default -> System.out.println("Неизвестная команда: '" + choice + "'");
            }
        }
        System.out.println("Выход.");
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("Выберите действие:");
        System.out.println("  1 — print (напечатать список фигур)");
        System.out.println("  2 — count (количество фигур)");
        System.out.println("  3 — перечитать файл");
        System.out.println("  0 — выход");
        System.out.print("> ");
    }

    private static boolean loadShapes() {
        List<Shape> loaded = new ArrayList<>();
        List<String> lines;
        try {
            lines = Files.readAllLines(FILE_PATH, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Не удалось прочитать файл '" + FILE_PATH + "': " + e.getMessage());
            return false;
        }

        int ok = 0;
        int bad = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isBlank() || line.stripLeading().startsWith("//")) {
                continue;
            }
            try {
                loaded.add(ShapeParser.parse(line));
                ok++;
            } catch (IllegalArgumentException e) {
                bad++;
                System.err.println("Пропущена строка " + (i + 1) + ": " + e.getMessage());
            }
        }

        shapes = loaded;
        System.out.println("Загружено фигур: " + ok + ", пропущено строк: " + bad);
        return true;
    }

    private static void printShapes() {
        if (shapes.isEmpty()) {
            System.out.println("(список пуст)");
            return;
        }
        for (int i = 0; i < shapes.size(); i++) {
            System.out.println((i + 1) + ". " + shapes.get(i).describe());
        }
    }

    private static void printCount() {
        System.out.println("Количество фигур: " + shapes.size());
    }
}