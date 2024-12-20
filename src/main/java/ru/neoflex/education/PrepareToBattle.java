package ru.neoflex.education;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

@Component
public class PrepareToBattle implements JavaDelegate {


    @Value("${maxWarriors}")
    private int maxWarriors;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int warriors = (int) delegateExecution.getVariable("warriors");
        int enemyWarriors = (int) (Math.random() * 100);
        boolean isWin = false;
        maxWarriors = maxWarriors == 0 ? 100 : maxWarriors;
        if (warriors < 1 || warriors > maxWarriors) {
            throw new BpmnError("warriorsError");
        }
        List<Boolean> army = new ArrayList<>(Collections.nCopies(warriors, true));
        System.out.println("Prepare to battle! Enemy army: " + enemyWarriors + " vs. our army: " + warriors);
        delegateExecution.setVariable("army", army);
        delegateExecution.setVariable("enemyWarriors", enemyWarriors);
    }
}