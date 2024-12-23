package ru.neoflex.education;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.connect.Connectors;
import org.camunda.connect.httpclient.HttpConnector;
import org.camunda.connect.httpclient.HttpRequest;
import org.camunda.connect.httpclient.HttpResponse;
import org.camunda.spin.SpinList;
import org.camunda.spin.json.SpinJsonNode;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.camunda.spin.Spin.JSON;

@Component
public class PrepareToBattle implements JavaDelegate {

    @Value("${maxWarriors}")
    private int maxWarriors;

    @Value("${url}")
    private String url;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int warriors = (int) delegateExecution.getVariable("warriors");
        int enemyWarriors = (int) (Math.random() * 100);
        boolean isWin = false;
        maxWarriors = maxWarriors == 0 ? 100 : maxWarriors;
        if (warriors < 1 || warriors > maxWarriors) {
            throw new BpmnError("warriorsError");
        }
        List<Warrior> army = new ArrayList<>();

        for(int i = 0; i <= warriors; i++) {
            army.add(create());
        }
        System.out.println("Prepare to battle! Enemy army: " + enemyWarriors + " vs. our army: " + warriors);
        String jsonArmy = JSON(army).toString();
        delegateExecution.setVariable("jsonArmy", jsonArmy);
        delegateExecution.setVariable("enemyWarriors", enemyWarriors);
    }
    private Warrior create() {
        Warrior warrior = new Warrior();
        HttpConnector httpConnector = Connectors.getConnector(HttpConnector.ID);
        HttpRequest request = httpConnector.createRequest()
                .url(url)
                .get();
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");

        request.setRequestParameter("headers", headers);

        HttpResponse response = request.execute();
        if (response.getStatusCode() == 200 || !response.getResponse().isEmpty()) {
            SpinJsonNode jsonWarrior = JSON(response.getResponse()).prop("results").elements().get(0);
            System.out.println(jsonWarrior.toString());
            warrior.setFirstName(jsonWarrior.prop("name").prop("first").stringValue());
            System.out.println(warrior.getFirstName());
            warrior.setLastName(jsonWarrior.prop("name").prop("last").stringValue());
        }
        response.close();

        return warrior;
    }
}