public class SeokjuStringCalculator {

    private String delimiter = ",:";
    private String expression;

    public void changeDelimiterAndExpression() {
        if (expression.startsWith("//") && expression.contains("\n")) {
            this.delimiter = expression.substring(2, expression.indexOf("\n"));
            expression = expression.substring(expression.indexOf("\n") + 1);
        }
    }

    public int cal(String expression) {

        this.expression = expression;

        changeDelimiterAndExpression();

        String format = String.format("[%s]", delimiter);
        String[] split = this.expression.split(format);

        int sum = 0;

        for (String s : split) {
            try {
                int i = Integer.parseInt(s);
                if (i < 0) {
                    throw new RuntimeException();
                }
                sum += i;
            } catch (NumberFormatException e) {
                sum += 0;
            }
        }

        return sum;
    }
}
