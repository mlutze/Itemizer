import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
	public static String readFileToString(File file) throws IOException {
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		StringBuilder sb = new StringBuilder();
		String line;
		boolean notFirstLine = false;
		while ((line = br.readLine()) != null) {
			if (notFirstLine) {
				sb.append("\n");
			}
			sb.append(line);
			notFirstLine = true;
		}
		br.close();
		return sb.toString();
	}

	public static void writeStringToFile(String string, File file) throws IOException {
		FileWriter fw = new FileWriter(file);
		fw.write(string);
		fw.close();
	}

	public static String fillTemplate(String info, String template) {
		String var, value;
		String filled = template;
		int equals;
		for (String line : info.split("\n")) {
			equals = line.indexOf('=');
			if (equals == -1) {
				continue;
			} else {
				var = line.substring(0, equals).trim();
				value = line.substring(equals + 1).trim();
			}
			filled = filled.replace("{" + var + "}", value);
		}
		return filled;
	}

	public static String minecraftCodeToHtml(String code) {
		return HTMLBuilder.convert(code);
	}

	public static Set<String> getVariables(String template) {
		Set<String> variables = new HashSet<>();
		Matcher m = Pattern.compile("\\{([^\\{\\}]*)\\}").matcher(template);
		while (m.find()) {
			variables.add(m.group(1));
		}
		return variables;
	}

	public static String wrapCode(String string, int width) {
		StringBuilder sb = new StringBuilder();
		int lineLength;
		String[] words;
		boolean firstLine = true;
		for (String line : string.split("\n")) {
			if (!firstLine) {
				sb.append('\n');
			}
			if (line.startsWith("&*")) {
				sb.append(line.substring(2));
				continue;
			}
			lineLength = 0;
			words = line.split(" ");
			for (String word : words) {
				if (lineLength + stripMCFormat(word).length() + 1 > width) {
					sb.append('\n');
					lineLength = 0;
				}
				sb.append(word);
				sb.append(' ');
				lineLength += word.length() + 1;
			}
			firstLine = false;
		}
		return sb.toString();
	}

	private static String stripMCFormat(String string) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == '&' && i < string.length() - 1
					&& "0123456789abcdefklmno".contains("" + string.charAt(i + 1))) {
				i++;
			} else {
				sb.append(string.charAt(i));
			}
		}
		return sb.toString();
	}

	private static class HTMLBuilder {
		private StringBuilder sb = new StringBuilder();
		private int depth = -1;
		private String color = "";

		private Map<Character, String> colorCodes = new HashMap<>();
		private Map<Character, String> formatCodes = new HashMap<>();

		private HTMLBuilder() {
			colorCodes.put('0', "000000");
			colorCodes.put('1', "0000AA");
			colorCodes.put('2', "00AA00");
			colorCodes.put('3', "00AAAA");
			colorCodes.put('4', "AA0000");
			colorCodes.put('5', "AA00AA");
			colorCodes.put('6', "FFAA00");
			colorCodes.put('7', "AAAAAA");
			colorCodes.put('8', "555555");
			colorCodes.put('9', "5555FF");
			colorCodes.put('a', "55FF55");
			colorCodes.put('b', "55FFFF");
			colorCodes.put('c', "FF5555");
			colorCodes.put('d', "FF55FF");
			colorCodes.put('e', "FFFF55");
			colorCodes.put('f', "FFFFFF");
			formatCodes.put('k', "text-shadow:0px 0px 10px #000000");
			formatCodes.put('l', "font-weight:bold");
			formatCodes.put('m', "text-decoration:line-through");
			formatCodes.put('n', "text-decoration:underline");
			formatCodes.put('o', "font-style:italic");
			sb.append("<!DOCTYPE html><html><body>");
			addColor("FFFFFF");
		}

		private void addStyle(String style) {
			sb.append("<span style=\"" + style + ";\">");
			depth++;
		}

		private void addColor(String color) {
			addStyle("color:#" + color);
			this.color = color;
		}

		private void setColor(String color) {
			clearStyle();
			addColor(color);
		}

		private void addCode(char code) {
			if (code == 'r') {
				reset();
			} else if (colorCodes.containsKey(code)) {
				addColor(colorCodes.get(code));
			} else if (formatCodes.containsKey(code)) {
				addStyle(formatCodes.get(code));
			} else {
				addCharacter('&');
				addCharacter(code);
			}
		}

		private void clearStyle() {
			for (; depth > 0; depth--) {
				sb.append("</span>");
			}
		}

		private void reset() {
			setColor(color);
		}

		private void finish() {
			clearStyle();
			sb.append("</span></body></html>");
		}

		private void newLine() {
			clearStyle();
			sb.append("<br>");
		}

		private void addCharacter(char c) {
			sb.append(c);
		}

		public static String convert(String code) {
			HTMLBuilder me = new HTMLBuilder();
			for (int i = 0; i < code.length(); i++) {
				char c = code.charAt(i);
				switch (c) {
				case '\n':
					me.newLine();
					break;
				case '&':
					try {
						me.addCode(code.charAt(++i));
					} catch (IndexOutOfBoundsException e) {
						me.addCharacter(c);
					}
					break;
				default:
					me.addCharacter(c);
					break;
				}
			}
			me.finish();
			return me.sb.toString();
		}
	}
}
