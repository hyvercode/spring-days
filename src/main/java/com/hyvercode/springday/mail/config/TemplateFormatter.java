package com.hyvercode.springday.mail.config;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Log4j2
public class TemplateFormatter {

    public String dateShort(String date,String locale) {
        return formatDate(date,"d/M/yyyy",locale);
    }

    public String dateLong(String date,String locale) {
        return formatDate(date,"d MMM yyyy",locale).replace("Agt","Agu");
    }

    public String time12(String date,String locale) {
        return formatDate(date, "h:mm:ss a",locale);
    }

    public String time24(String date,String locale) {
        return formatDate(date, "HH:mm:ss",locale);
    }

    public String formatDate(String input, String dateFormat, String localeFormat) {
        String response = input;

        String[] localeArray = localeFormat.split("-");
        Locale locale = new Locale(localeArray[0],localeArray[0]);
        try {
            Date date  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(input);
            response = new SimpleDateFormat(dateFormat,locale).format(date);
        } catch (ParseException e) {
            log.error("Error Parsing Timestamp to "+ dateFormat +" format");
        }
        return response;
    }

    public String amount(String input) {
        BigDecimal amount = new BigDecimal(input);
        DecimalFormat decimalFormatter = new DecimalFormat("#,##0.00");
        return decimalFormatter.format(amount);
    }

    public String smsCurrency(String input) {
        BigDecimal amount = new BigDecimal(input);
        DecimalFormat decimalFormatter = new DecimalFormat("###0.00");
        return decimalFormatter.format(amount);
    }

    public String trimLeft10(String input) {
        return StringUtils.left(input, 10);
    }

    public String trimLeft11(String input) {
        return StringUtils.left(input, 11);
    }

    public String trimLeft25(String input) {
        String trimmedInput = StringUtils.left(input, 25);
        return StringUtils.trim(trimmedInput);
    }

    public String mask(String input) {
        String output = StringUtils.right(input, 8);
        return StringUtils.overlay(output, "****", 0, 4);
    }

    public String currency(String input) {
        input = input.replaceAll(",+", "");
        Locale locale = new Locale("id","ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        return numberFormat.format(new BigDecimal(input)).substring(2);
    }

    public String currency(Number input) {
        Locale locale = new Locale("id","ID");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        return currencyFormatter.format(input).substring(2);
    }

    public String add(Object... nums) {
        Number total = 0;
        for(Object num : nums) {
            total = new BigDecimal(total.toString()).add(new BigDecimal(num.toString()));
        }
        return total.toString();
    }
}
