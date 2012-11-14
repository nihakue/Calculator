package west.gabriel;

import java.util.Vector;

public class Expression {
	private static final String operators = "()^*/+-.";
	private String expressionString;
	private int argCount;
	private int opCount;
	private double result;
	private Vector<Double> arguments;

	
	
	Expression(String str){
		expressionString = str;
		argCount = countArgs(expressionString);
		opCount = countOps(expressionString);
	}
	
	private int countOps(String str) {
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			if (operators.contains(str.substring(i, i))){
				count++;
			}
		}
		return count;
	}

	private int countArgs(String str) {
		int count = 0;
		String argument = "";
		for (int i = 0; i<str.length(); i++){
			if (Character.isDigit(str.charAt(i))){
				argument.concat(String.valueOf(str.charAt(i)));
				if (Character.isDigit(str.charAt(i+1)))
					continue;
				else{
					count++;
					arguments.add(Double.parseDouble(argument));
					argument = "";
				}
			}
		}
		return count;
	}

	private void parseExp(){
		
	}
	
	private double evalualte(String str){
		return 1.0;
	}
	
	public String getExpression(){
		return expressionString;
	}
	
	public double getResult(){
		return result;
	}
	
	public static void main(String[] args){
		Expression test = new Expression("2+2");
		System.out.println(test.getExpression());
		System.out.println(test.countArgs("2+2") + " " + test.countOps("2+2"));
	}
	
	
}
