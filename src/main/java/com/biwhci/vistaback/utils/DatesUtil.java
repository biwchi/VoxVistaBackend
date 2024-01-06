package com.biwhci.vistaback.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class DatesUtil {
  public static ZonedDateTime tryToParseISO(String stringDate) {
    try {
      return ZonedDateTime.parse(stringDate);
    } catch (DateTimeParseException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong date format. Use ISO 8601 format");
    }
  }
}
