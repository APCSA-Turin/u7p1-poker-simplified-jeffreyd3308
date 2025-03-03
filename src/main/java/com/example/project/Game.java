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

    public static int checkHighest(Player player, int amount) {
        int highest = 0;
        for (int i = 0; i < player.findRankingFrequency().size(); i++) {
            if (player.findRankingFrequency().get(i) >= amount) {
                highest = i + 2;
            }
        }
        return highest;
    }

    //check tie methods
    public static String checkTie(String tieArea, Player p1, Player p2, ArrayList<Card> communityCards) {
        String conclusion = tieArea;
        int p1Highest = 0;
        int p2Highest = 0;
        switch (conclusion) {
            case "Straight Flush":
                p1Highest = checkHighest(p1, 1);
                p2Highest = checkHighest(p2, 1);
                break;
            case "Four of a Kind":
                p1Highest = checkHighest(p1, 4);
                p2Highest = checkHighest(p2, 4);
                break;
            case "Full House":
                p1Highest = checkHighest(p1, 3);
                p2Highest = checkHighest(p2, 3);
                if (p1Highest == p2Highest) {
                    for (int i = 0; i < p1.findRankingFrequency().size(); i++) {
                        if (p1.findRankingFrequency().get(i) == 2) {
                            p1Highest = i + 2;
                        }
                    }
                    for (int i = 0; i < p2.findRankingFrequency().size(); i++) {
                        if (p2.findRankingFrequency().get(i) == 2) {
                            p2Highest = i + 2;
                        }
                    }
                }
                break;
            case "Flush":
                p1Highest = checkHighest(p1, 1);
                p2Highest = checkHighest(p2, 1);
                break;
            case "Straight":
                p1Highest = checkHighest(p1, 1);
                p2Highest = checkHighest(p2, 1);
                break;
            case "Three of a Kind":
                p1Highest = checkHighest(p1, 3);
                p2Highest = checkHighest(p2, 3);
                if (p1Highest == p2Highest) {
                    for (int i = 0; i < p1.getHand().size(); i++) {
                        if (Utility.getRankValue(p1.getHand().get(i).getRank()) != p1Highest - 2) {
                            p1Highest = Utility.getRankValue(p1.getHand().get(i).getRank());
                            break;
                        }
                    }
                    for (int i = 0; i < p2.getHand().size(); i++) {
                        if (Utility.getRankValue(p2.getHand().get(i).getRank()) != p2Highest - 2) {
                            p2Highest = Utility.getRankValue(p2.getHand().get(i).getRank());
                            break;
                        }
                    }
                }
                break;
            case "Two Pair":
                p1Highest = checkHighest(p1, 2);
                p2Highest = checkHighest(p2, 2);
                break;
            case "A Pair":
                p1Highest = checkHighest(p1, 2);
                p2Highest = checkHighest(p2, 2);
                if (p1Highest == p2Highest) {
                    p1Highest = Utility.getRankValue(p1.getHand().get(0).getRank());
                    p2Highest = Utility.getRankValue(p2.getHand().get(0).getRank());
                    for (int i = 1; i < p1.getHand().size(); i++) {
                        if (Utility.getRankValue(p1.getHand().get(i).getRank()) > p1Highest) {
                            p1Highest = Utility.getRankValue(p1.getHand().get(i).getRank());
                        }
                    }
                    for (int i = 1; i < p2.getHand().size(); i++) {
                        if (Utility.getRankValue(p2.getHand().get(i).getRank()) > p2Highest) {
                            p2Highest = Utility.getRankValue(p2.getHand().get(i).getRank());
                        }
                    }
                }
                break;
            case "High Card":
                p1Highest = checkHighest(p1, 1);
                p2Highest = checkHighest(p2, 1);
                break;
            case "Nothing":
                for (int i = 0; i < p1.getHand().size(); i++) {
                    if (Utility.getRankValue(p1.getHand().get(i).getRank()) > p1Highest) {
                        p1Highest = Utility.getRankValue(p1.getHand().get(i).getRank());
                    }
                }
                for (int i = 0; i < p2.getHand().size(); i++) {
                    if (Utility.getRankValue(p2.getHand().get(i).getRank()) > p2Highest) {
                        p2Highest = Utility.getRankValue(p2.getHand().get(i).getRank());
                    }
                }
                break;
        }
        System.out.println(p1Highest);
        System.out.println(p2Highest);
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
        
    public static void main(String[] args) {
        Player player1 = new Player();
        Player player2 = new Player();
        
        player1.addCard(new Card("7", "♠"));
        player1.addCard(new Card("10", "♠"));
  
        player2.addCard(new Card("A", "♠"));
        player2.addCard(new Card("3", "♠"));

        
        // Community cards that could help form the flush
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("J", "♠")); // Player 1 completes the flush with this card
        communityCards.add(new Card("J", "♥"));
        communityCards.add(new Card("Q", "♠"));
        
        // Player results after playing the hand
        String p1Result = player1.playHand(communityCards);
        String p2Result = player2.playHand(communityCards);
        
        // Determine the winner
        String winner = Game.determineWinner(player1, player2, p1Result, p2Result, communityCards);
        System.out.println(winner);
    }

}