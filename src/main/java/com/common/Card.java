package com.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.common.CardSuit.CLUBS;
import static com.common.CardSuit.DIAMONDS;
import static com.common.CardSuit.HEARTS;
import static com.common.CardSuit.SPADES;

public enum Card {
    C2(CLUBS, CardRank.TWO),
    C3(CLUBS, CardRank.THREE),
    C4(CLUBS, CardRank.FOUR),
    C5(CLUBS, CardRank.FIVE),
    C6(CLUBS, CardRank.SIX),
    C7(CLUBS, CardRank.SEVEN),
    C8(CLUBS, CardRank.EIGHT),
    C9(CLUBS, CardRank.NINE),
    C10(CLUBS, CardRank.TEN),
    CJ(CLUBS, CardRank.JACK),
    CQ(CLUBS, CardRank.QUEEN),
    CK(CLUBS, CardRank.KING),
    CA(CLUBS, CardRank.ACE),
    D2(DIAMONDS, CardRank.TWO),
    D3(DIAMONDS, CardRank.THREE),
    D4(DIAMONDS, CardRank.FOUR),
    D5(DIAMONDS, CardRank.FIVE),
    D6(DIAMONDS, CardRank.SIX),
    D7(DIAMONDS, CardRank.SEVEN),
    D8(DIAMONDS, CardRank.EIGHT),
    D9(DIAMONDS, CardRank.NINE),
    D10(DIAMONDS, CardRank.TEN),
    DJ(DIAMONDS, CardRank.JACK),
    DQ(DIAMONDS, CardRank.QUEEN),
    DK(DIAMONDS, CardRank.KING),
    DA(DIAMONDS, CardRank.ACE),
    S2(SPADES, CardRank.TWO),
    S3(SPADES, CardRank.THREE),
    S4(SPADES, CardRank.FOUR),
    S5(SPADES, CardRank.FIVE),
    S6(SPADES, CardRank.SIX),
    S7(SPADES, CardRank.SEVEN),
    S8(SPADES, CardRank.EIGHT),
    S9(SPADES, CardRank.NINE),
    S10(SPADES, CardRank.TEN),
    SJ(SPADES, CardRank.JACK),
    SQ(SPADES, CardRank.QUEEN),
    SK(SPADES, CardRank.KING),
    SA(SPADES, CardRank.ACE),
    H2(HEARTS, CardRank.TWO),
    H3(HEARTS, CardRank.THREE),
    H4(HEARTS, CardRank.FOUR),
    H5(HEARTS, CardRank.FIVE),
    H6(HEARTS, CardRank.SIX),
    H7(HEARTS, CardRank.SEVEN),
    H8(HEARTS, CardRank.EIGHT),
    H9(HEARTS, CardRank.NINE),
    H10(HEARTS, CardRank.TEN),
    HJ(HEARTS, CardRank.JACK),
    HQ(HEARTS, CardRank.QUEEN),
    HK(HEARTS, CardRank.KING),
    HA(HEARTS, CardRank.ACE);

    private final CardSuit suit;
    private final CardRank value;

    Card(CardSuit suit, CardRank value) {
        this.suit = suit;
        this.value = value;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public CardRank getValue() {
        return value;
    }

    public static List<Card> generateDeck() {
        List<Card> deck = new ArrayList<Card>();

        for (Card card : Card.values()) {
            deck.add(card);
        }

        Collections.shuffle(deck);
        return deck;
    }

    public int returnNumberValue(Card i){
        int numberValue = 0;

        switch(i.getValue()){
            case TWO: numberValue = 2;
                break;
            case THREE: numberValue = 3;
                break;
            case FOUR: numberValue = 4;
                break;
            case FIVE: numberValue = 5;
                break;
            case SIX: numberValue = 6;
                break;
            case SEVEN: numberValue = 7;
                break;
            case EIGHT: numberValue = 8;
                break;
            case NINE: numberValue = 9;
                break;
            case TEN: numberValue = 10;
                break;
            case JACK: numberValue = 10;
                break;
            case QUEEN: numberValue = 10;
                break;
            case KING: numberValue = 10;
                break;
            case ACE: numberValue = 10;
                break;
        }
        return numberValue;
    }
}
