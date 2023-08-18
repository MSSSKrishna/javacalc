import java.util.Stack;

public class Expression {

    public static String evaluateExpression(String expression) {
        char[] tokens = expression.toCharArray();

        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ')
                continue;

            if (tokens[i] == '-' && (i == 0 || tokens[i - 1] == '(')) {
                StringBuilder sb = new StringBuilder("-");
                i++;

                while (i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.')) {
                    sb.append(tokens[i++]);
                }
                i--;
                values.push(Double.parseDouble(sb.toString()));
            } else if (tokens[i] >= '0' && tokens[i] <= '9') {
                StringBuilder sb = new StringBuilder();

                while (i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.')) {
                    sb.append(tokens[i++]);
                }
                i--;
                values.push(Double.parseDouble(sb.toString()));
            } else if (tokens[i] == '(') {
                operators.push(tokens[i]);
            } else if (tokens[i] == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    double val2 = values.pop();
                    double val1 = values.pop();
                    char op = operators.pop();

                    values.push(applyOperator(op, val1, val2));
                }

                if (!operators.isEmpty() && operators.peek() == '(')
                    operators.pop();
            } else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/') {
                while (!operators.isEmpty() && hasPrecedence(tokens[i], operators.peek())) {
                    double val2 = values.pop();
                    double val1 = values.pop();
                    char op = operators.pop();

                    values.push(applyOperator(op, val1, val2));
                }

                operators.push(tokens[i]);
            }
        }

        while (!operators.isEmpty()) {
            double val2 = values.pop();
            double val1 = values.pop();
            char op = operators.pop();

            values.push(applyOperator(op, val1, val2));
        }

        return values.pop().toString();
    }

    public static boolean hasPrecedence(char op1, char op2) {
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;

        return true;
    }

    public static double applyOperator(char operator, double operand1, double operand2) {
        if (operator == '+')
            return operand1 + operand2;
        else if (operator == '-')
            return operand1 - operand2;
        else if (operator == '*')
            return operand1 * operand2;
        else if (operator == '/') {
            if (operand2 == 0)
                throw new ArithmeticException("Division by zero");
            return operand1 / operand2;
        }

        throw new IllegalArgumentException("Invalid operator: " + operator);
    }
}
