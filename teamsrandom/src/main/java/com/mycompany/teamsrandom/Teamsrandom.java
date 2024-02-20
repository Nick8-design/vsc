/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.teamsrandom;

/**
 *
 * @author Nick Dieda
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Teamsrandom {
    public static void main(String[] args) {
        int numTeams = 20;
        List<String> teams = new ArrayList<>();
        for (int i = 1; i <= numTeams; i++) {
            teams.add("Team " + i);
        }

        List<String> shuffledTeams = new ArrayList<>(teams);
        Collections.shuffle(shuffledTeams);

        for (int week = 1; week <= numTeams - 1; week++) {
            System.out.println("Week " + week + " Fixtures:");
            for (int i = 0; i < numTeams / 2; i++) {
                String teamA = shuffledTeams.get(i);
                String teamB = shuffledTeams.get(numTeams - 1 - i);
                System.out.println(teamA + " vs. " + teamB);
            }
            Collections.rotate(shuffledTeams.subList(1, numTeams - 1), 1);
        }
    }
}


