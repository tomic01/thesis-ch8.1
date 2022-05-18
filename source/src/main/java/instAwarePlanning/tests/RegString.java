package instAwarePlanning.tests;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegString {

	public static void main(String[] args) {
//		String in = "Item(s): [item1.test],[item2.qa],[item3.production]";
//
//		Pattern p = Pattern.compile("\\[(.*?)\\]");
//		Matcher m = p.matcher(in);
//
//		while(m.find()) {
//		    System.out.println(m.group(1));
//		}
		
		String in = "Items: [(item1.test)(item2.qa)(item3.production)]";
		Pattern p = Pattern.compile("\\((.*?)\\)");
		Matcher m = p.matcher(in);
		
		while(m.find()) {
		    System.out.println(m.group(1));
		}
		
//		in = "Items: [{ (item1.test) (item2.qa) (item3.production) }]";
//		Pattern p = Pattern.compile("\\((.*?)\\)");
//		Matcher m = p.matcher(in);
//		
//		while(m.find()) {
//		    System.out.println(m.group(1));
//		}

		
	}
	
}
