import java.util.Comparator;

public class ScoreComparator implements Comparator<String> {

	public int compare(String str1, String str2) {

		String temp1 = "";
		String temp2 = "";

		int str1Score = 0;
		int str2Score = 0;

		for (int x = 0; x < str1.length(); x++) {

			char c = str1.charAt(x);
			if (Character.isDigit(c)) {
				temp1 += c;
			} else {
				break;
			}
		}

		str1Score = Integer.parseInt(temp1);

		for (int x = 0; x < str2.length(); x++) {

			char c = str2.charAt(x);
			if (Character.isDigit(c)) {
				temp2 += c;
			} else {
				break;
			}
		}

		str2Score = Integer.parseInt(temp2);

		if (str1Score > str2Score) {
			return 1;
		} else if (str1Score == str2Score) {
			return 0;
		} else {
			return -1;
		}

	}

}
