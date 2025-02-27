package com.example.project;
import java.util.ArrayList;


public class Player{
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; //the current community cards + hand
    String[] suits  = Utility.getSuits();
    String[] ranks = Utility.getRanks();
    
    public Player(){
        hand = new ArrayList<>();
    }

    public ArrayList<Card> getHand(){return hand;}
    public ArrayList<Card> getAllCards(){return allCards;}

    public void addCard(Card c){
        hand.add(c);
    }

    public String playHand(ArrayList<Card> communityCards){  
        //setup
        allCards = new ArrayList<>();
        for (int i = 0; i < communityCards.size(); i++) {
            allCards.add(communityCards.get(i));
        }    
        for (int i = 0; i < hand.size(); i++) {
            allCards.add(hand.get(i));
        }
        sortAllCards();
        ArrayList<Integer> rankFreq = findRankingFrequency();
        ArrayList<Integer> suitFreq = findSuitFrequency();
        
        //royalflush
        boolean isIn = true;
        for (int i = rankFreq.size() - 1; i >= 9; i--) {
            if (rankFreq.get(i) != 1) {
                isIn = false;
            }
        }
        if (isIn) {
            for (int i = 0; i < suitFreq.size(); i++) {
                if (suitFreq.get(i) == 5) {
                    return "Royal Flush";
                }
            }
        }
        //straightflush
        int count = 0;
        for (int i = 0; i < rankFreq.size(); i++) {
            if (rankFreq.get(i) == 1) {
                count++;
            } else if (count != 5) {
                count = 0;
            }
        }
        if (count == 5) {
            for (int i = 0; i < suitFreq.size(); i++) {
                if (suitFreq.get(i) == 5) {
                    return "Straight Flush";
                }
            }
        }

        return "Nothing";
    }

    public void sortAllCards(){ //
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
        ArrayList<Integer> suitFreqArray = new ArrayList<Integer>();
        for (int i = 0; i < suits.length; i++) {
            suitFreqArray.add(0);
        }
        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < allCards.size(); j++) {
                if (allCards.get(j).getSuit().equals(suits[i])) {
                    suitFreqArray.set(i, suitFreqArray.get(i) + 1);
                }
            }
        }
        return suitFreqArray; 
    }

   
    @Override
    public String toString(){
        return hand.toString();
    }

    public static void main(String[] args) {
        Player player = new Player();
        player.addCard(new Card("10", "♠"));
        player.addCard(new Card("J", "♠"));
        
        // Community Cards
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("Q", "♠"));
        communityCards.add(new Card("K", "♠"));
        communityCards.add(new Card("A", "♠"));
        
        player.playHand(communityCards);
        String handResult = player.playHand(communityCards);
        System.out.println(player.findRankingFrequency());
        System.out.println(player.findSuitFrequency());
        System.out.println(handResult);
    }

}
