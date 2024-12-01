package com.ivankiv.schedule;

import java.time.LocalDate;

public class Test {

    public static void main(String[] args){

        LocalDate date = LocalDate.now();

        int dayOfWeek = date.getDayOfWeek().getValue();
        LocalDate beginWeek = date.minusDays(dayOfWeek - 1);
        LocalDate[] days = new LocalDate[7];
        for(int i = 0; i < 7; i++){
            System.out.println(beginWeek);
             beginWeek.plusDays(1);
             days[i] = beginWeek.plusDays(1);
        }


    }

}
