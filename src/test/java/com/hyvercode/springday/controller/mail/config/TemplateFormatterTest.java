package com.hyvercode.springday.controller.mail.config;

import com.hyvercode.springday.mail.config.TemplateFormatter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
class TemplateFormatterTest {
  TemplateFormatter templateFormatter = new TemplateFormatter();

  @Test
  void dateTime12Test() {
    assertEquals("3:54:46 PM", templateFormatter.time12("2020-09-03 15:54:46.864", "id-ID"));
    assertEquals("3:54:46 AM", templateFormatter.time12("2020-09-03 03:54:46.864", "id-ID"));
  }

  @Test
  void dateTime24Test() {
    assertEquals("15:54:46", templateFormatter.time24("2020-09-03 15:54:46.864", "id-ID"));
    assertEquals("03:54:46", templateFormatter.time24("2020-09-03 03:54:46.864", "id-ID"));
  }

  @Test
  void dateLongTest() {
    assertEquals("3 Agu 2020", templateFormatter.dateLong("2020-08-03 15:54:46.864", "id-ID"));
    assertEquals("3 Aug 2020", templateFormatter.dateLong("2020-08-03 15:54:46.864", "en-ID"));
    assertEquals("3/8/2020", templateFormatter.dateShort("2020-08-03 15:54:46.864", "id-ID"));
  }

  @Test
  void currencyTest() {
    assertEquals("100,00", templateFormatter.currency(new BigDecimal("100.00")));
  }

  @Test
  void currencyStringTest() {
    assertEquals("1.000,00", templateFormatter.currency("1,000.00"));
  }

  @Test
  void addTest() {
    assertEquals("325", templateFormatter.add(new BigDecimal("100"), "200", (long) 25));
  }
}
