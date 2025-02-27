package com.example.project;
import java.util.ArrayList;


public class Player{
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; //the current community cards + hand
    String[] suits  = Utility.getSuits();
    String[] ranks = Utility.getRanks();
    
    public Player(){
        hand = new ArrayList<>();
        allCards = new ArrayList<>();
    }

    public ArrayList<Card> getHand(){return hand;}
    public ArrayList<Card> getAllCards(){return allCards;}

    public void addCard(Card c){
        hand.add(c);
    }

    public String playHand(ArrayList<Card> communityCards){  
        for (int i = 0; i < communityCards.size(); i++) {
            allCards.add(communityCards.get(i));
        }    
        for (int i = 0; i < hand.size(); i++) {
            allCards.add(hand.get(i));
        }

        return "Nothing";
    }

    public void sortAllCards(){
        for (int i = 0; i < allCards.size(); i++) {
            int j = i;
            while (j > 0 && Utility.getRankValue(allCards.get(j).getRank()) < Utility.getRankValue(allCards.get(j - 1).getRank())) {
                allCards.set(j, allCards.set(j - 1, allCards.get(j)));
                j--;
            }
        }
    } 

    public ArrayList<Integer> findRankingFrequency(){
        ArrayList<Integer> rankFreqArray = new ArrayList<Integer>();
        for (int i = 0; i < ranks.length; i++) {
            rankFreqArray.add(0);
        }
        for (int i = 0; i < ranks.length; i++) {
            for (int j = 0; j < allCards.size(); j++) {
                if (allCards.get(j).getRank().equals(ranks[i])) {
                    rankFreqArray.set(i, rankFreqArray.get(i) + 1);
                }
            }
        }
        return rankFreqArray; 
    }

    public ArrayList<Integer> findSuitFrequency(){
        return new ArrayList<>(); 
    }

   
    @Override
    public String toString(){
        return hand.toString();
    }

    public static void main(String[] args) {
        Player player = new Player();
        player.addCard(new Card("3", "♠"));
        player.addCard(new Card("5", "♣"));
        
        // Add community cards (3 cards in total for this example)
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("4", "♠"));
        communityCards.add(new Card("8", "♣"));
        communityCards.add(new Card("A", "♦"));
        
        player.playHand(communityCards);
        
        // Now the player should have 5 cards: 2 player cards + 3 community cards
        player.sortAllCards();
        System.out.println(player.getAllCards());
        System.out.println(player.findRankingFrequency());
    }

}
