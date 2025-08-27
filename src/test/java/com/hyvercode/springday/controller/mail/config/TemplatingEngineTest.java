package com.hyvercode.springday.controller.mail.config;

import com.hyvercode.springday.exception.BusinessException;
import com.hyvercode.springday.mail.config.TemplatingEngine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TemplatingEngineTest {

    TemplatingEngine templatingEngine = new TemplatingEngine();

    @Test
     void generateTemplateEmptyParameterTest() {
        final String RAW_TEMPLATE = "Dear customer, this is a testing email. Please don't mind this.";

        final String RAW_TEMPLATE_RESULT = "Dear customer, this is a testing email. Please don't mind this.";

        Map<String, Object> parameters = new HashMap<>();

        String actual = templatingEngine.transform(RAW_TEMPLATE, parameters);
        assertEquals(RAW_TEMPLATE_RESULT, actual);
    }

    @Test
     void generateTemplateFormatErrorTest() {
        final String RAW_TEMPLATE = "Dear $format.username(USERNAME), this is a testing email. Please don't mind this.";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("USERNAME", "John Mock");

        try {
            templatingEngine.transform(RAW_TEMPLATE, parameters);
        } catch (BusinessException e) {
            assertEquals("80000", e.getErrorCode());
        }
    }

    @Test
     void generateTemplateCurrencyAndDateLongFormatTest() {
        final String RAW_TEMPLATE = "" +
                "Dear $USER_NAME" +
                "<BR><BR>" +
                "You have applied for a loan application via Everest as per the following detail;" +
                "<BR><BR>" +
                "<table>" +
                "<tr>" +
                "<td>Loan Type: </td>" +
                "<td>$PRODUCT_TYPE</td>" +
                "</tr>" +
                "<tr>" +
                "<td>Credit Limit (IDR):</td>" +
                "<td>$format.amount($AMOUNT)</td>" +
                "</tr>" +
                "<tr>" +
                "<td>Date/time to apply and<br/>Consent to check your credit reports (NCB):</td>" +
                "<td><br/>$format.dateLong($DATETIME,\"id-ID\")</td>" +
                "</tr>" +
                "</table>" +
                "<BR><BR>" +
                "If you did not login to the Everest system at the specified time," +
                " please call at 02-777-7777 immediately.<BR><BR>Sincerely yours," +
                "<BR>PT Spring Days<BR><BR>" +
                "This email is auto-generated. Please do not reply. If you have further enquiries, please call xxxxxxxx.";

        final String RAW_TEMPLATE_RESULT = "" +
                "Dear John Doe" +
                "<BR><BR>" +
                "You have applied for a loan application via Everest as per the following detail;" +
                "<BR><BR>" +
                "<table>" +
                "<tr>" +
                "<td>Loan Type: </td><td>Personal</td>" +
                "</tr>" +
                "<tr>" +
                "<td>Credit Limit (IDR):</td>" +
                "<td>200,000.00</td>" +
                "</tr>" +
                "<tr>" +
                "<td>Date/time to apply and<br/>Consent to check your credit reports (NCB):</td>" +
                "<td><br/>9 Apr 2020</td>" +
                "</tr>" +
                "</table>" +
                "<BR><BR>" +
                "If you did not login to the Everest system at the specified time," +
                " please call at 02-777-7777 immediately.<BR><BR>Sincerely yours," +
                "<BR>PT Spring Days<BR><BR>" +
                "This email is auto-generated. Please do not reply. If you have further enquiries, please call xxxxxxxx.";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("USER_NAME", "John Doe");
        parameters.put("AMOUNT", "200000");
        parameters.put("PRODUCT_TYPE", "Personal");
        parameters.put("DATETIME", "2020-04-09 15:54:46.864");

        String actual = templatingEngine.transform(RAW_TEMPLATE, parameters);
        assertEquals(RAW_TEMPLATE_RESULT, actual);
    }

    @Test
     void generateTemplateSmsCurrencyAndMaskAndDateShortAndTime24FormatTest() {
        final String RAW_TEMPLATE = "" +
                "Dear $USER_NAME" +
                "<BR><BR>" +
                "You have applied for a temporary credit card limit increase via Everest as per the following detail;" +
                "<BR><BR>" +
                "<table>" +
                "<tr>" +
                "<td>Credit Card Number: </td>" +
                "<td>$format.mask($CREDIT_CARD_NUMBER)</td>" +
                "</tr>" +
                "<tr>" +
                "<td>Credit Limit (IDR):</td>" +
                "<td>$format.smsCurrency($AMOUNT)</td>" +
                "</tr>" +
                "<tr>" +
                "<td>New Credit Limit (IDR):</td>" +
                "<td>$format.smsCurrency($NEW_AMOUNT)</td>" +
                "</tr>" +
                "<tr>" +
                "<td>The new limit will take effect as of: </td>" +
                "<td><br/>$format.dateShort($DATETIME,\"id-ID\") $format.time24($DATETIME,\"id-ID\")</td>" +
                "</tr>" +
                "</table>" +
                "<BR><BR>" +
                "If you did not login to the Everest system at the specified time," +
                " please call at 02-777-7777 immediately.<BR><BR>Sincerely yours," +
                "<BR>PT Spring Days<BR><BR>" +
                "This email is auto-generated. Please do not reply. If you have further enquiries, please call xxxxxxxx.";

        final String RAW_TEMPLATE_RESULT = "" +
                "Dear John Wick" +
                "<BR><BR>" +
                "You have applied for a temporary credit card limit increase via Everest as per the following detail;" +
                "<BR><BR>" +
                "<table>" +
                "<tr>" +
                "<td>Credit Card Number: </td>" +
                "<td>****5678</td>" +
                "</tr>" +
                "<tr>" +
                "<td>Credit Limit (IDR):</td>" +
                "<td>200000.00</td>" +
                "</tr>" +
                "<tr>" +
                "<td>New Credit Limit (IDR):</td>" +
                "<td>300000.00</td>" +
                "</tr>" +
                "<tr>" +
                "<td>The new limit will take effect as of: </td>" +
                "<td><br/>9/4/2020 14:03:17</td>" +
                "</tr>" +
                "</table>" +
                "<BR><BR>" +
                "If you did not login to the Everest system at the specified time," +
                " please call at 02-777-7777 immediately.<BR><BR>Sincerely yours," +
                "<BR>PT Spring Days<BR><BR>" +
                "This email is auto-generated. Please do not reply. If you have further enquiries, please call xxxxxxxx.";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("NEW_AMOUNT", "300000");
        parameters.put("AMOUNT", "200000");
        parameters.put("CREDIT_CARD_NUMBER", "12345678");
        parameters.put("USER_NAME", "John Wick");
        parameters.put("DATETIME", "2020-04-09 14:03:17.864");

        String actual = templatingEngine.transform(RAW_TEMPLATE, parameters);
        assertEquals(RAW_TEMPLATE_RESULT, actual);
    }

    @Test
     void generateTemplateTime12Test() {
        final String RAW_TEMPLATE = "" +
                "As of $format.time12($DATETIME,\"id-ID\")" +
                "<BR><BR>" +
                "You have applied for a credit card via Everest as per the following detail;" +
                "<BR><BR>" +
                "<table>" +
                "<tr>" +
                "<td>Credit Card Number: </td>" +
                "<td>$CREDIT_CARD_NUMBER</td>" +
                "</tr>" +
                "<tr>" +
                "<td>Credit TrimLeft10: </td>" +
                "<td>$format.trimLeft10($CREDIT_CARD_NUMBER)</td>" +
                "</tr>" +
                "<tr>" +
                "<td>Credit TrimLeft11:</td>" +
                "<td>$format.trimLeft11($CREDIT_CARD_NUMBER)</td>" +
                "</tr>" +
                "<tr>" +
                "<td>Credit TrimLeft25: </td>" +
                "<td>$format.trimLeft25($CREDIT_CARD_NUMBER)</td>" +
                "</tr>" +
                "</table>" +
                "<BR><BR>" +
                "If you did not login to the Everest system at the specified time," +
                " please call at 02-777-7777 immediately.<BR><BR>Sincerely yours," +
                "<BR>PT Spring Days<BR><BR>" +
                "This email is auto-generated. Please do not reply. If you have further enquiries, please call xxxxxxxx.";

        final String RAW_TEMPLATE_RESULT = "" +
                "As of 2:03:17 PM" +
                "<BR><BR>" +
                "You have applied for a credit card via Everest as per the following detail;" +
                "<BR><BR>" +
                "<table>" +
                "<tr>" +
                "<td>Credit Card Number: </td>" +
                "<td>1234567891011121314151617181920</td>" +
                "</tr>" +
                "<tr>" +
                "<td>Credit TrimLeft10: </td>" +
                "<td>1234567891</td>" +
                "</tr>" +
                "<tr>" +
                "<td>Credit TrimLeft11:</td>" +
                "<td>12345678910</td>" +
                "</tr>" +
                "<tr>" +
                "<td>Credit TrimLeft25: </td>" +
                "<td>1234567891011121314151617</td>" +
                "</tr>" +
                "</table>" +
                "<BR><BR>" +
                "If you did not login to the Everest system at the specified time," +
                " please call at 02-777-7777 immediately.<BR><BR>Sincerely yours," +
                "<BR>PT Spring Days<BR><BR>" +
                "This email is auto-generated. Please do not reply. If you have further enquiries, please call xxxxxxxx.";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("CREDIT_CARD_NUMBER", "1234567891011121314151617181920");
        parameters.put("DATETIME", "2020-04-09 14:03:17.864");

        String actual = templatingEngine.transform(RAW_TEMPLATE, parameters);
        assertEquals(RAW_TEMPLATE_RESULT, actual);
    }
}
