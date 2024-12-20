package ru.neoflex.education;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class EvaluateBattle implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int warriors = (int) delegateExecution.getVariable("warriors");
        int enemyWarriors = (int) delegateExecution.getVariable("enemyWarriors");

        if (warriors > enemyWarriors) {
            delegateExecution.setVariable("isWin", true);
            delegateExecution.setVariable("battleStatus", "Victory!");
        } else {
            delegateExecution.setVariable("isWin", false);
            delegateExecution.setVariable("battleStatus", "Defeat!");
        }
    }
}
