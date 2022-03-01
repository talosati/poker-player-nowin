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

        int buyIn = getNumberFromJsonRoot(request, "current_buy_in");
        int blind = getNumberFromJsonRoot(request, "small_blind");
        int pot = getNumberFromJsonRoot(request, "pot");
        int minRaise = getNumberFromJsonRoot(request, "minimum_raise");
        int round = getNumberFromJsonRoot(request, "round");
        int currentBet = request.getAsJsonObject().get("players").getAsJsonArray().get(playerIndex.intValue())
                .getAsJsonObject().get("bet").getAsNumber().intValue();
        int stack = request.getAsJsonObject().get("players").getAsJsonArray().get(playerIndex.intValue())
                .getAsJsonObject().get("stack").getAsNumber().intValue();

        ArrayList<Card> allCards = new ArrayList<>();
        allCards.addAll(commCards);
        allCards.add(firstCard);
        allCards.add(secondCard);
        int minimumReturn = buyIn - currentBet + minRaise;

        if (round == 0) {
            if (firstCard.rank == secondCard.rank) {
                minimumReturn += stack * 0.8;
            }
        }
        return minimumReturn;
    }

    public static Card getFromJsonCard(JsonElement element) {
        return new Card(element.getAsJsonObject().get("rank").getAsString(),
                element.getAsJsonObject().get("suit").getAsString());
    }

    public static int getNumberFromJsonRoot(JsonElement request, String property) {
        return request.getAsJsonObject().get(property).getAsNumber().intValue();
    }

    public static void showdown(JsonElement game) {
    }
}
