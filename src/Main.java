import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите ваше выражение: ");
        String input = scanner.nextLine();

        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("throws Exception");
        }
    }

    public static String calc(String input) throws Exception { //метод может использовать exception
        String[] parts = input.split("\\s+"); //разделяем строку на части и сохраняем в массив parts

        if (parts.length != 3) {
            throw new Exception("Неверный формат введения");
        }

        String operand1 = parts[0];
        String operator = parts[1];
        String operand2 = parts[2];

        boolean isArabic = isArabic(operand1) && isArabic(operand2); //проверяем числа на арабские и римские использую методы isArabic/Roman
        boolean isRoman = isRoman(operand1) && isRoman(operand2);

        if (!isArabic && !isRoman) {
            throw new Exception("Неверный формат введения");
        }

        int num1 = isRoman ? toArabic(operand1) : Integer.parseInt(operand1);//значение если true : значение если false
        int num2 = isRoman ? toArabic(operand2) : Integer.parseInt(operand2);

        if ((num1 < 1 || num1 > 10) || (num2 < 1 || num2 > 10)) { //проверяем условие от 1-10
            throw new Exception("Числа должны быть от 1-10");
        }

        int result; //запоминаем результат операции
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new Exception("Деление на ноль");
                }
                result = num1 / num2;
                break;
            default:
                throw new Exception("Неверный оператор");
        }

        return isRoman ? toRoman(result) : String.valueOf(result); //преобразуем в римскую запись, иначе в строку
    }

//проверяем является ли арабским или римским числом

    private static boolean isArabic(String input) {
        return input.matches("[0-9]+");
    }

    private static boolean isRoman(String input) {
        return input.matches("^[IVX]+$");//проверка символов
    }

    private static int toArabic(String input) {
        int result = 0;
        int prevValue = 0;

        for (int i = input.length() - 1; i >= 0; i--) { //цикл проходит справа налево, уменьшает на единицу так как 0 мы не считаем
            int curValue = romanToArabic(input.charAt(i));
            if (curValue < prevValue) { //чтобы понять вычитать или добавлять предыдущее значение
                result -= curValue;
            } else {
                result += curValue;
            }
            prevValue = curValue;
        }
        return result;
    }

    private static int romanToArabic(char roman) { //конвертируем из римских в арабские
        switch (roman) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            default:
                return 0;
        }
    }

    private static String toRoman(int number) { //конвертируем арабское в римское
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("Проверяем диапазон (1-3999)");
        }

        int[] values = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
        String[] numerals = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };

        StringBuilder result = new StringBuilder();
        int i = 0;
        while (number > 0) { //проверяем можно ли вычесть value так чтобы было ноль, если нет идем дальге по циклу
            if (number - values[i] >= 0) {
                result.append(numerals[i]);
                number -= values[i];
            } else {
                i++;
            }
        }
        return result.toString();
    }
}
