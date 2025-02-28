package com.example.project;
import java.util.ArrayList;


public class Game{
    public static String determineWinner(Player p1, Player p2,String p1Hand, String p2Hand,ArrayList<Card> communityCards){
        if (Utility.getHandRanking(p1.playHand(communityCards)) > Utility.getHandRanking(p2.playHand(communityCards))) {
            return "Player 1 wins!";
        } else if (Utility.getHandRanking(p2.playHand(communityCards)) > Utility.getHandRanking(p1.playHand(communityCards))) {
            return "Player 2 wins!";
        } else {
            String result = checkTie(p1.playHand(communityCards), p1, p2, communityCards);
            return result;
        }
    }

    //check tie methods
    public static String checkTie(String tieArea, Player p1, Player p2, ArrayList<Card> communityCards) {
        int p1Highest = 0;
        int p2Highest = 0;
        switch (tieArea) {
            case "Straight Flush":
                for (int i = 0; i < p1.getAllCards().size(); i++) {
                    if (Utility.getRankValue(p1.getAllCards().get(i).getRank()) > p1Highest) {
                        p1Highest = Utility.getRankValue(p1.getAllCards().get(i).getRank());
                    }
                }
                for (int i = 0; i < p2.getAllCards().size(); i++) {
                    if (Utility.getRankValue(p2.getAllCards().get(i).getRank()) > p2Highest) {
                        p2Highest = Utility.getRankValue(p2.getAllCards().get(i).getRank());
                    }
                }
            case "Four of a Kind":
                for (int i = 0; i < p1.findRankingFrequency().size(); i++) {
                    if (p1.findRankingFrequency().get(i) == 4) {
                        p1Highest = i + 2;
                    }
                }
                for (int i = 0; i < p2.findRankingFrequency().size(); i++) {
                    if (p2.findRankingFrequency().get(i) == 4) {
                        p2Highest = i + 2;
                    }
                }
        }
        if (p1Highest > p2Highest) {
            return "Player 1 wins!";
        } else if (p2Highest > p1Highest) {
            return "Player 2 wins!";
        } else {
            return "Tie!";
        }
    }

    public static void play(){ //simulate card playing
    
    }
        
        

}