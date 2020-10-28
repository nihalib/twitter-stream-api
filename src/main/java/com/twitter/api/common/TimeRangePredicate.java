package com.twitter.api.common;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.function.Predicate;

@Component
public class TimeRangePredicate implements Predicate<String> {

    @Override
    public boolean test(String date) {
        Instant givenInstant = Instant.parse(date);
        LocalDateTime dateTime = LocalDateTime.ofInstant(givenInstant, ZoneId.of(ZoneOffset.UTC.getId()));

        Instant currentInstant = Instant.now();
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentInstant, ZoneId.of(ZoneOffset.UTC.getId()));

        long range = Math.abs(ChronoUnit.MINUTES.between(dateTime, currentDateTime));
        return range <= 30;
    }
}
