import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class BalancedParenthesesChecker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string of parentheses: ");
        String input = scanner.nextLine();

        if (isBalanced(input)) {
            System.out.println("The parentheses are balanced and paired.");
        } else {
            System.out.println("The parentheses are NOT balanced or paired.");
        }
    }

    private static boolean isBalanced(String str) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char ch : str.toCharArray()) {
            if (isOpenBracket(ch)) {
                stack.push(ch);
            } else if (isCloseBracket(ch)) {
                if (stack.isEmpty()) {
                    return false; // 没有对应的开括号
                }
                char top = stack.pop();
                if (!isMatchingPair(top, ch)) {
                    return false; // 括号不匹配
                }
            }
            // 忽略其他字符（如有）
        }
        return stack.isEmpty(); // 所有括号都匹配完毕
    }

    private static boolean isOpenBracket(char ch) {
        return ch == '(' || ch == '[' || ch == '{';
    }

    private static boolean isCloseBracket(char ch) {
        return ch == ')' || ch == ']' || ch == '}';
    }

    private static boolean isMatchingPair(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '[' && close == ']') ||
               (open == '{' && close == '}');
    }
}