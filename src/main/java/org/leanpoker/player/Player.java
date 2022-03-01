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

        int buyIn = getNumberFromJson(request, "current_buy_in");
        int blind = getNumberFromJson(request, "small_blind");
        int pot = getNumberFromJson(request, "pot");
        int minRaise = getNumberFromJson(request, "minimum_raise");

        return getBet(firstCard, secondCard, commCards, buyIn, blind, pot, minRaise);
    }

    public static Card getFromJsonCard(JsonElement element) {
        return new Card(element.getAsJsonObject().get("rank").getAsString(),
                element.getAsJsonObject().get("suit").getAsString());
    }

    public static int getNumberFromJson(JsonElement request, String property) {
        return request.getAsJsonObject().get(property).getAsNumber().intValue();
    }

    public static int getBet(Card firstCard, Card secondCard, ArrayList<Card> commCards, int buyIn, int blind,
            int pot, int minRaise) {
        return 100;
    }

    public static void showdown(JsonElement game) {
    }
}
