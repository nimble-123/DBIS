package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Created with IntelliJ IDEA.
 * User: ikarus
 * Date: 19.06.13
 * Package: model
 */
public class HtmlFormatter extends Formatter {
    // Diese Methode wird für jeden Logeintrag aufgerufen
    @Override
    public String format(LogRecord rec) {
        StringBuffer buffer = new StringBuffer(1000);
        // alle Level >= WARNING fett gedruckt
        buffer.append("<tr>\n");
        buffer.append("<td>");

        if (rec.getLevel().intValue() >= Level.WARNING.intValue()) {
            buffer.append("<b>");
            buffer.append(rec.getLevel());
            buffer.append("</b>");
        } else {
            buffer.append(rec.getLevel());
        }
        buffer.append("</td>");
        buffer.append("<td>");
        buffer.append(calcDate(rec.getMillis()));
        buffer.append("</td>");
        buffer.append("<td>");
        buffer.append(rec.getMessage());
        buffer.append("</td>\n");
        buffer.append("</tr>\n");
        return buffer.toString();
    }

    private String calcDate(long millisecs) {
        SimpleDateFormat date = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(millisecs);
        return date.format(resultdate);
    }

    // Diese Methode wird für jeden Logeintrag aufgerufen
    @Override
    public String getHead(Handler h) {
        return "<html>\n<head>\n</head>\n<body>\n<pre>\n"
                + "<table border>\n  " + "<tr><th>Level</th>"
                + "<th>Time</th>" + "<th>Log Message</th>" + "</tr>\n";
    }

    // Diese Methode wird für jeden Logeintrag aufgerufen
    @Override
    public String getTail(Handler h) {
        return "</table>\n</pre>\n</body>\n</html>\n";
    }
}
