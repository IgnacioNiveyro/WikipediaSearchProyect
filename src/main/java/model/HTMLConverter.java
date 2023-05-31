package model;

public class HTMLConverter {

    public static String textToHtml(String text) {

        StringBuilder builder = new StringBuilder();

        builder.append("<font face=\"arial\">");

        String fixedText = text.replace("'", "`"); //Replace to avoid SQL errors, we will have to find a workaround..

        builder.append(fixedText);

        builder.append("</font>");

        return builder.toString();
    }

}
