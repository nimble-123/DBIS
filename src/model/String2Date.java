package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ikarus
 * Date: 19.06.13
 * Package: model
 */
public class String2Date {
    private final String stringDate;
    private String dateString1;
    private String dateString2;
    private Date date;

    public String2Date(String stringDate) {
        this.stringDate = stringDate;
    }

    public String getDate() throws ParseException {
        dateString1 = stringDate;
        date = new SimpleDateFormat("dd.MM.yyyy").parse(dateString1);
        dateString2 = new SimpleDateFormat("yyyy-MM-dd").format(date);

        return dateString2;
    }
}
