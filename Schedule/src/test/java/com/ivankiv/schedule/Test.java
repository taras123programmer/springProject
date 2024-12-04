package com.ivankiv.schedule;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {

    public static void main(String[] args){

//        LocalDate date = LocalDate.now();
//
//        int dayOfWeek = date.getDayOfWeek().getValue();
//        LocalDate beginWeek = date.minusDays(dayOfWeek - 1);
//        LocalDate[] days = new LocalDate[7];
//        for(int i = 0; i < 7; i++){
//            System.out.println(beginWeek);
//             beginWeek.plusDays(1);
//             days[i] = beginWeek.plusDays(1);
//        }

        String rawPassword = "password123"; // Текстовий пароль
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(rawPassword); // Хешований пароль

        System.out.println("Hashed password: " + hashedPassword);


    }

}
