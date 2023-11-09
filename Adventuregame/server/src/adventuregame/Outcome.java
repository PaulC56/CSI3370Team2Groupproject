package adventuregame;

import java.util.ArrayList;
import java.util.Random;

public class Outcome {
    Condition[] conditions;

    public Outcome(Condition[] conditions) {
        this.conditions = conditions;
    }

    public Condition getSuccessCondition(Player p) {
        if (conditions.length < 1) {
            return null;
        }
        
        // if there is only a single condition, ignore the condition check.
        if (conditions.length == 1) {
            return this.conditions[0];
        }

        // create a list nextChoices for all the valid conditions that pass.
        // if multiple conditions pass, select a random passed condition to be selected.
        // if no conditions pass, choose a random one to pass as a failsafe.
        ArrayList<Condition> passedConditions = new ArrayList<Condition>();
        for (int i = 0; i < conditions.length; i++) {
            if (this.conditions[i].checkCondition(p.getScores())) {
                passedConditions.add(this.conditions[i]);
            }
        }
        Random rand = new Random();
        
        // To prevent the game from breaking from not having any conditions pass, we cause an intentional logic error by allowing a failed condition to pass
        if (passedConditions.size() < 1) {
            return this.conditions[(rand.nextInt(conditions.length))];
        }
        return passedConditions.get(rand.nextInt(passedConditions.size()));
    }
    public void applyResults(Player p) {
        Condition successCondition = getSuccessCondition(p);
        p.applyScores(successCondition.scoreResults);
    }
    public void setNextChoice(Player p) {
        int nextChoiceID = getSuccessCondition(p).getNextChoiceID();
        Choice nextChoice = Game.getChoice(nextChoiceID);
        p.setChoice(nextChoice);
        
    }
}