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
        
        if (checkForRoyalFlush(rankFreq, suitFreq)) {
            return "Royal Flush";
        } else if (checkForStraightFlush(rankFreq, suitFreq)) {
            return "Straight Flush";
        } else if (checkForFourOfAKind(rankFreq, suitFreq)) {
            return "Four of a Kind";
        } else if (checkForFullHouse(rankFreq, suitFreq)) {
            return "Full House";
        } else if (checkForFlush(rankFreq, suitFreq)) {
            return "Flush";
        } else if (checkForStraight(rankFreq, suitFreq)) {
            return "Straight";
        } else if (checkForThreeOfAKind(rankFreq, suitFreq)) {
            return "Three of a Kind";
        } else if (checkForTwoPairs(rankFreq, suitFreq)) {
            return "Two Pair";
        } else if (checkForOnePair(rankFreq, suitFreq)) {
            return "A Pair";
        } else if (checkForHighCard(rankFreq, suitFreq, communityCards)) {
            return "High Card";
        }

        return "Nothing";
    }

    public boolean checkForHighCard(ArrayList<Integer> rank, ArrayList<Integer> suit, ArrayList<Card> commCards) {
        for (int i = rank.size() - 1; i >= 0; i--) {
            if (rank.get(i) == 1) {
                for (int j = 0; j < commCards.size(); j++) {
                    if (Utility.getRankValue(commCards.get(j).getRank()) == i + 2) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean checkForOnePair(ArrayList<Integer> rank, ArrayList<Integer> suit) {
        for (int i = 0; i < rank.size(); i++) {
            if (rank.get(i) == 2) {
                return true;
            }
        }
        return false;
    }

    public boolean checkForTwoPairs(ArrayList<Integer> rank, ArrayList<Integer> suit) {
        int count = 0;
        for (int i = 0; i < rank.size(); i++) {
            if (rank.get(i) == 2) {
                count++;
            }
        }
        if (count == 2) {
            return true;
        }
        return false;
    }

    public boolean checkForThreeOfAKind(ArrayList<Integer> rank, ArrayList<Integer> suit) {
        for (int i = 0; i < rank.size(); i++) {
            if (rank.get(i) == 3) {
                return true;
            } 
        }
        return false;
    }

    public boolean checkForStraight(ArrayList<Integer> rank, ArrayList<Integer> suit) {
        int count = 0;
        for (int i = 0; i < rank.size(); i++) {
            if (rank.get(i) == 1) {
                count++;
            } else if (count != 5) {
                count = 0;
            }       
            if (count == 5) {
                return true;
            }
        }

        return false;
    }

    public boolean checkForFlush(ArrayList<Integer> rank, ArrayList<Integer> suit) {
        for (int i = 0; i < suit.size(); i++) {
            if (suit.get(i) == 5) {
                return true;
            }
        }
        return false;
    }

    public boolean checkForFullHouse(ArrayList<Integer> rank, ArrayList<Integer> suit) {
        if (checkForThreeOfAKind(rank, suit) && checkForOnePair(rank, suit)) {
            return true;
        }
        return false;
    }

    public boolean checkForFourOfAKind(ArrayList<Integer> rank, ArrayList<Integer> suit) {
        for (int i = 0; i < rank.size(); i++) {
            if (rank.get(i) == 4) {
                return true;
            }
        }
        return false;
    }

    public boolean checkForStraightFlush(ArrayList<Integer> rank, ArrayList<Integer> suit) {
        if (checkForStraight(rank, suit) && checkForFlush(rank, suit)) {
            return true;
        }
        return false;
    }

    public boolean checkForRoyalFlush(ArrayList<Integer> rank, ArrayList<Integer> suit) {
        boolean isIn = true;
        for (int i = rank.size() - 1; i >= 9; i--) {
            if (rank.get(i) != 1) {
                isIn = false;
            }
        }
        if (isIn) {
            if (checkForFlush(rank, suit)) {
                return true;
            }
        }
        return false;
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
