package org.leanpoker.player;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.leanpoker.player.classes.Card;

public class Player {

    static final String VERSION = "Default Java folding player";

    public static int betRequest(JsonElement request) {
        Number playerIndex = request.getAsJsonObject().get("in_action").getAsNumber();
        JsonArray myHand = request.getAsJsonObject().get("players").getAsJsonArray().get(playerIndex.intValue())
                .getAsJsonObject().get("hole_cards").getAsJsonArray();
        JsonArray communityCardsJson = request.getAsJsonObject().get("community_cards").getAsJsonArray();
        Card firstCard = getFromJsonCard(myHand.get(0));
        Card secondCard = getFromJsonCard(myHand.get(1));
        ArrayList<Card> commCards = new ArrayList<>();
        communityCardsJson.forEach((element) -> {
            commCards.add(getFromJsonCard(element));
        });

        Number buyIn = request.getAsJsonObject().get("current_buy_in").getAsNumber();

// Number blind = request.get

//         Number pot = 

//         Number minRaise;

        int bet = 100; 
        if(firstCard.rank == secondCard.rank) {
            bet = 5000;
        }
        // return getBet(firstCard, secondCard, commCards, minimumBet);
        return bet;
    }

    public static Card getFromJsonCard(JsonElement element) {
        return new Card(element.getAsJsonObject().get("rank").getAsString(),
                element.getAsJsonObject().get("suit").getAsString());
    }

    public static Card getNumberFromJson(JsonElement element) {
        return new Card(element.getAsJsonObject().get("rank").getAsString(),
                element.getAsJsonObject().get("suit").getAsString());
    }

    // public static int getBet() {

        
    // }

    public static void showdown(JsonElement game) {
    }
}
