package com.citrine.services;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import com.citrine.entities.UnitEnum;
import com.citrine.viewmodels.Unit;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;


@Component
@Configurable
public class SiConversionService {

    public Unit convertToSi(String expression) throws RuntimeException {
        String tokenizer = "()/*";
        ExpressionBuilder expressionBuilder = new ExpressionBuilder(expression);
        StringTokenizer multiTokenizer = new StringTokenizer(expression, tokenizer, true);
        Map<String, UnitEnum> variables = new HashMap<>();
        List<String> unitNameList = new LinkedList<>();

        while (multiTokenizer.hasMoreTokens()){
            String token = multiTokenizer.nextToken().trim();
            if (tokenizer.contains(token)){
                unitNameList.add(token);
            } else {
                boolean foundMatch = false;
                for (UnitEnum u : UnitEnum.values()) {
                    if (token.equals(u.getName())) {
                        expressionBuilder.variable(u.getName());
                        variables.put(u.getName(), u);
                        foundMatch = true;
                    } else if (token.equals(u.getSymbol())) {
                        expressionBuilder.variable(u.getSymbol());
                        variables.put(u.getSymbol(), u);
                        foundMatch = true;
                    }
                    if (foundMatch){
                        unitNameList.add(u.getSiName());
                        break;
                    }
                }
                if (!foundMatch) {
                    throw new RuntimeException("Unknown value: " + token);
                }
            }
        }

        Expression exp;
        try {
            exp = expressionBuilder.build();
            for (String variableName : variables.keySet()) {
                exp.setVariable(variableName, variables.get(variableName).getSiConversion());
            }
        } catch (Exception e){
            // rethrowing, since library just throws null pointer
            throw new RuntimeException("Cannot evaluate expression: " + expression);
        }

        ValidationResult validationResult = exp.validate();

        if (validationResult.isValid()) {
            double result = exp.evaluate();
            BigDecimal factor = new BigDecimal(result).setScale(14, BigDecimal.ROUND_HALF_UP);
            String unitName = String.join("", unitNameList);
            return new Unit(unitName, factor);
        } else {
            throw new RuntimeException("Invalid condition: " +
                String.join("," , validationResult.getErrors()));
        }
    }
}