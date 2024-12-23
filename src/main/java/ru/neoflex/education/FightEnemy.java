package ru.neoflex.education;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.stereotype.Component;
import static org.camunda.spin.Spin.JSON;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class FightEnemy implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int enemyWarriors = (int) delegateExecution.getVariable("enemyWarriors");
        int warriors = (int) delegateExecution.getVariable("warriors");
        String jsonArmy = (String) delegateExecution.getVariable("jsonArmy");
        System.out.println(jsonArmy);
        List<Warrior> army = JSON(jsonArmy).mapTo("java.util.ArrayList<ru.neoflex.education.Warrior>");
        System.out.println(army.get(0).getFirstName());
        Thread.sleep(100);

        for (int i=0; i<army.size(); i++)  {
            if (!army.get(i).getAlive()) {
                continue;
            }
            if ( new Random().nextBoolean() ) {
                enemyWarriors--;
                System.out.println("Enemy warrior killed!");
                if (enemyWarriors == 0) {
                    break;
                }
            } else {
                army.get(i).setAlive(false);
                warriors--;
                System.out.println("Our warrior killed!");
                if (warriors == 6) {
                    jsonArmy = JSON(army).toString();
                    delegateExecution.setVariable("enemyWarriors", enemyWarriors);
                    delegateExecution.setVariable("warriors", warriors);
                    delegateExecution.setVariable("jsonArmy", jsonArmy);
                    throw new BpmnError("escape");
                }
            }
        }
        jsonArmy = JSON(army).toString();
        delegateExecution.setVariable("enemyWarriors", enemyWarriors);
        delegateExecution.setVariable("warriors", warriors);
        delegateExecution.setVariable("jsonArmy", jsonArmy);
    }
}
